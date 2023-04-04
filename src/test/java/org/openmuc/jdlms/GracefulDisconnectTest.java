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
package org.openmuc.jdlms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.openmuc.jdlms.TestHelper.setField;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.openmuc.jdlms.SecuritySuite.EncryptionMechanism;
import org.openmuc.jdlms.internal.APdu;
import org.openmuc.jdlms.internal.ReleaseRespReason;
import org.openmuc.jdlms.internal.asn1.iso.acse.ACSEApdu;
import org.openmuc.jdlms.internal.asn1.iso.acse.RLREApdu;
import org.openmuc.jdlms.internal.asn1.iso.acse.RLRQApdu;
import org.openmuc.jdlms.internal.asn1.iso.acse.ReleaseResponseReason;
import org.openmuc.jdlms.sessionlayer.client.SessionLayer;
import org.openmuc.jdlms.settings.client.Settings;

public class GracefulDisconnectTest {

    @Test
    public void test1() throws Exception {
        BaseDlmsConnection connection = BaseDlmsConnection.getTestInstance();

        setField(connection, BaseDlmsConnection.class, "buffer", new byte[200]);
        Settings settings = mock(Settings.class);

        SecuritySuite secSuite = mock(SecuritySuite.class);
        when(secSuite.getEncryptionMechanism()).thenReturn(EncryptionMechanism.NONE);

        when(settings.securitySuite()).thenReturn(secSuite);

        setField(connection, BaseDlmsConnection.class, "settings", settings);
        SessionLayer sessionLayer = mock(SessionLayer.class);

        final APduBlockingQueue blockingQueue = new APduBlockingQueue();

        doAnswer((Answer<Void>) invocation -> {
            byte[] dataBytes = invocation.getArgument(0);
            int off = invocation.getArgument(1);
            int len = invocation.getArgument(2);
            dataBytes = Arrays.copyOfRange(dataBytes, off, off + len);

            APdu data = APdu.decode(dataBytes, null);
            ACSEApdu acseAPdu2 = data.getAcseAPdu();

            assertNotNull(acseAPdu2);
            RLRQApdu rlrq = acseAPdu2.getRlrq();
            assertNotNull(rlrq);
            assertEquals(0, rlrq.getReason().value.intValue());

            ReleaseRespReason responseReason = ReleaseRespReason.NORMAL;
            ReleaseResponseReason reason = responseReason.toDlmsReason();
            RLREApdu rlre = new RLREApdu();
            rlre.setReason(reason);
            ACSEApdu acseAPdu = new ACSEApdu();
            acseAPdu.setRlre(rlre);
            APdu aPdu = new APdu(acseAPdu, null);
            blockingQueue.put(aPdu);
            return null;
        }).when(sessionLayer).send(any(), anyInt(), anyInt(), any());

        doReturn(100).when(settings).responseTimeout();

        setField(connection, BaseDlmsConnection.class, "settings", settings);
        setField(connection, BaseDlmsConnection.class, "sessionLayer", sessionLayer);
        setField(connection, BaseDlmsConnection.class, "incomingApdQueue", blockingQueue);

        connection.disconnect();
    }

    @Test()
    public void test2() throws Exception {
        // bytes from E350:
        // 0001 0001 0010 0005 6303800100
        byte[] releaseBytes = HexConverter.fromShortHexString("6303800100");

        APdu aPdu = APdu.decode(releaseBytes, RawMessageData.builder());

        ACSEApdu acseAPdu = aPdu.getAcseAPdu();
        assertNotNull(acseAPdu);

        RLREApdu rlre = acseAPdu.getRlre();
        assertNotNull(rlre);

        assertEquals(0, rlre.getReason().value.intValue());
    }
}
