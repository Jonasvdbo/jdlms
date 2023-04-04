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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;

public class LogicalDeviceTest {
    static Object testAddRestrictionsParam() {
        Object[] p1 = { 0, SecuritySuite.builder().build() };
        Object[] p2 = { 1, null };
        return new Object[] { p1, p2 };
    }

    @ParameterizedTest
    @MethodSource("testAddRestrictionsParam")
    public void testAddRestrictions(int clientId, SecuritySuite securitySuite) {
        assertThrows(IllegalArgumentException.class, () -> {
            LogicalDevice ld = mock(LogicalDevice.class);
            when(ld.addRestriction(ArgumentMatchers.anyInt(), ArgumentMatchers.any())).thenCallRealMethod();
            ld.addRestriction(clientId, securitySuite);
        });
    }

    static Object testLdConstructorParam() {
        Object[] p1 = { 1, new String(new byte[17]).replaceAll("\0", "a"), "ISE", 9 };
        Object[] p2 = { 0, new String(new byte[16]).replaceAll("\0", "a"), "ISE", 9 };
        Object[] p3 = { 1, new String(new byte[16]).replaceAll("\0", "a"), "I", 9 };
        Object[] p4 = { 1, new String(new byte[16]).replaceAll("\0", "a"), "ISEW", 9 };
        return new Object[] { p1, p2, p3, p4 };
    }

    @ParameterizedTest
    @MethodSource("testLdConstructorParam")
    public void testLdConstructor(int logicalDeviceId, String logicalDeviceName, String manufacturerId, long deviceId) {
        assertThrows(IllegalArgumentException.class, () -> {
            new LogicalDevice(logicalDeviceId, logicalDeviceName, manufacturerId, deviceId);
        });
    }

    static Object testSetMasterKeyParam() {
        Object[] p1 = { new byte[100] };
        Object[] p2 = { new byte[128] };
        Object[] p3 = { new byte[15] };
        Object[] p4 = { new byte[17] };
        return new Object[] { p1, p2, p3, p4 };
    }

    @ParameterizedTest
    @MethodSource("testSetMasterKeyParam")
    public void testSetMasterKey(byte[] masterKey) {
        assertThrows(IllegalArgumentException.class, () -> {
            LogicalDevice ld = setupMKeyLd();
            ld.setMasterKey(masterKey);
        });
    }

    @Test
    public void testSetMasterKeySuccess() {
        LogicalDevice ld = setupMKeyLd();
        byte[] generateAES128Key = SecurityUtils.generateAES128Key();

        ld.setMasterKey(generateAES128Key.clone());

        assertArrayEquals(generateAES128Key, ld.getMasterKey());
    }

    private LogicalDevice setupMKeyLd() {
        LogicalDevice ld = mock(LogicalDevice.class);
        when(ld.setMasterKey(ArgumentMatchers.any(byte[].class))).thenCallRealMethod();
        when(ld.getMasterKey()).thenCallRealMethod();
        return ld;
    }
}
