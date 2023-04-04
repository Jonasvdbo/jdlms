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
package org.openmuc.jdlms.itest;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Random;

public class ITestHelper {

    private static final int MIN_PORT_NUMBER = 10000;
    private static final int MAX_PORT_NUMBER = 65000;
    private static Random random = new Random();

    public static int getAvailablePort() {
        int port = MIN_PORT_NUMBER;
        boolean isAvailable = false;

        while (!isAvailable) {
            port = random.nextInt((MAX_PORT_NUMBER - MIN_PORT_NUMBER) + 1) + MIN_PORT_NUMBER;
            DatagramSocket ds = null;
            try (ServerSocket ss = new ServerSocket(port);) {
                ss.setReuseAddress(true);
                ds = new DatagramSocket(port);
                ds.setReuseAddress(true);
                isAvailable = true;
            } catch (IOException e) {
                // port is not available
            } finally {
                if (ds != null) {
                    ds.close();
                }
            }
        }
        return port;
    }

    private ITestHelper() {
        // hidden
    }
}
