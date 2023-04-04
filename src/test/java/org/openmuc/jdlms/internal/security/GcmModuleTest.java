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
package org.openmuc.jdlms.internal.security;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.openmuc.jdlms.internal.security.crypto.DecryptionException;
import org.openmuc.jdlms.internal.security.crypto.GcmModule;

public class GcmModuleTest {

    @Test
    public void decryptionExceptionTest() {
        assertThrows(DecryptionException.class, () -> {
            final byte[] cipheredApdu = new byte[9]; // Too long
            final byte[] systemTitle = new byte[5];
            GcmModule.decrypt(systemTitle, cipheredApdu, null);
        });
    }

    @Test
    public void decryptionExceptionTest2() {
        assertThrows(DecryptionException.class, () -> {
            final byte[] cipheredApdu = new byte[8];
            final byte[] systemTitle = new byte[4]; // Too short
            GcmModule.decrypt(systemTitle, cipheredApdu, null);
        });
    }

    @Test
    public void decryptionExceptionTest3() {
        assertThrows(DecryptionException.class, () -> {
            final byte[] systemTitle = new byte[5];
            GcmModule.decrypt(systemTitle, null, null);
        });
    }

    @Test
    public void decryptionExceptionTest4() {
        assertThrows(DecryptionException.class, () -> {
            final byte[] cipheredApdu = new byte[8];
            GcmModule.decrypt(null, cipheredApdu, null);
        });
    }
}
