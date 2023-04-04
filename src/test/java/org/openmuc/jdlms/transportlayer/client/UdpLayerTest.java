/*
 * Copyright 2012-2022 Fraunhofer ISE
 *
 * This file is part of jDLMS.
 * For more information visit http://www.openmuc.org
 *
 * jDLMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jDLMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jDLMS.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.jdlms.transportlayer.client;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.openmuc.jdlms.TestHelper.getField;
import static org.openmuc.jdlms.TestHelper.setField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InterruptedIOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.openmuc.jdlms.settings.client.TcpSettings;

public class UdpLayerTest {

    private DataOutputStream clientOStream;
    private DataInputStream serverIStream;
    private UdpLayer client;
    private UdpLayer server;

    @BeforeEach
    public void setUp() throws Exception {
        final int port = 9999;
        TcpSettings clientconf = confFor(port);

        client = new UdpLayer(clientconf);
        client.open();

        DatagramSocket ds = (DatagramSocket) getField(client, UdpLayer.class, "socket");

        TcpSettings serverInetConf = confFor(ds.getLocalPort());

        server = spy(new UdpLayer(serverInetConf));
        server.open();
        ((DatagramSocket) getField(server, UdpLayer.class, "socket")).close();
        setField(server, UdpLayer.class, "socket", new DatagramSocket(port));

        this.clientOStream = client.getOutpuStream();

        this.serverIStream = server.getInputStream();
    }

    @AfterEach
    public void tearDown() throws Exception {
        try {
            this.clientOStream.close();
        } finally {
            this.serverIStream.close();
        }

    }

    @Test
    public void test_send_receive_data() throws Exception {

        SecureRandom random = new SecureRandom();
        final byte[] bytes = new byte[20];
        random.nextBytes(bytes);

        clientOStream.write(bytes);
        clientOStream.flush();

        assertEquals(bytes.length, serverIStream.available());
        byte[] b2 = new byte[bytes.length];
        serverIStream.readFully(b2);

        assertArrayEquals(bytes, b2);

    }

    @Test
    public void test_send_receive_data_fragments() throws Exception {

        SecureRandom random = new SecureRandom();
        final byte[] bytes = new byte[20];
        random.nextBytes(bytes);

        clientOStream.write(bytes, 0, 10);
        clientOStream.flush();
        clientOStream.write(bytes, 10, 10);
        clientOStream.flush();

        byte[] b2 = new byte[bytes.length];
        serverIStream.readFully(b2);

        assertArrayEquals(bytes, b2);

    }

    @Test
    public void test_send_receive_byte() throws Exception {

        SecureRandom random = new SecureRandom();
        boolean nextBoolean = random.nextBoolean();

        clientOStream.writeBoolean(nextBoolean);
        clientOStream.flush();

        assertEquals(1, serverIStream.available());
        assertEquals(nextBoolean, serverIStream.readBoolean());
    }

    @Test
    public void test_send_receive_max_msg() throws Exception {

        SecureRandom random = new SecureRandom();
        byte[] data = new byte[65507 * 2];
        random.nextBytes(data);

        clientOStream.write(data);
        clientOStream.flush();

        byte[] rData = new byte[data.length];
        this.serverIStream.readFully(rData);

        assertArrayEquals(data, rData);
    }

    @Test
    public void test_timeout1() {
        assertThrows(InterruptedIOException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                server.setTimeout(100);

                serverIStream.read();
            }
        });
    }

    @Test
    public void test_timeout2() throws Exception {
        final int timeOut = 100;
        server.setTimeout(timeOut);
        long t0 = System.currentTimeMillis();
        try {
            serverIStream.read();
            fail("Read did not time out: should hav timed out.");
        } catch (InterruptedIOException e) {
            // ignore
        }
        long actualTime = System.currentTimeMillis() - t0;
        double delta = 1.0;
        assertEquals(timeOut, actualTime, delta);

    }

    private static TcpSettings confFor(final int port) throws UnknownHostException {
        TcpSettings clientConf = mock(TcpSettings.class);

        when(clientConf.port()).thenReturn(port);
        when(clientConf.inetAddress()).thenReturn(InetAddress.getByName("127.0.0.1"));
        return clientConf;
    }

}
