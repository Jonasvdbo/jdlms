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
package org.openmuc.jdlms.internal.transportlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openmuc.jdlms.FatalJDlmsException;
import org.openmuc.jdlms.JDlmsException.Fault;
import org.openmuc.jdlms.transportlayer.client.Iec21Layer;

public class LocalDataExchangeTest {

    private static final String METHOD_NAME = "baudRateFor";

    private static Iec21Layer dataExchangeClient;

    @BeforeAll
    public static void setupTest() {
        dataExchangeClient = new Iec21Layer(null);
    }

    public static Collection<Object[]> data() {
        Object[] o1 = { (char) 0x30, 300 };
        Object[] o2 = { '1', 600 };
        Object[] o3 = { '2', 1200 };
        Object[] o4 = { (char) 0x33, 2400 };
        return Arrays.asList(new Object[][] { o1, o2, o3, o4 });
    }

    /*
     * Testing conditions of IEC 62056-21 6.3.14 13c
     */
    @ParameterizedTest
    @MethodSource("data")
    public void testBaudRateTransformation0(char option, int expectedBaudRate) throws Exception {
        int baudRate = invokeMethodWith(option);
        assertEquals(expectedBaudRate, baudRate);
    }

    @Test
    public void testBaudRateTransformationError2() {
        assertThrows(FatalJDlmsException.class, () -> {
            char option = 0xFF;

            try {
                invokeMethodWith(option);
            } catch (FatalJDlmsException e) {
                assertEquals(Fault.SYSTEM, e.getAssumedFault());
                throw e;
            }
        });
    }

    private int invokeMethodWith(char option) throws Exception {
        try {
            Method method = Iec21Layer.class.getDeclaredMethod(METHOD_NAME, char.class);
            method.setAccessible(true);
            return (int) method.invoke(dataExchangeClient, option);
        } catch (InvocationTargetException e) {
            throw (Exception) e.getCause();
        }
    }
}
