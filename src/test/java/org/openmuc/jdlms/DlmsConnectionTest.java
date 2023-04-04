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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.openmuc.jdlms.settings.client.Settings;

public class DlmsConnectionTest {

    @Test
    public void testChangeClientGlobalAuthenticationKey() throws Exception {
        Settings settings = mock(Settings.class);

        @SuppressWarnings("resource")
        BaseDlmsConnection con = new DlmsLnConnectionImpl(settings, null);
        con.changeClientGlobalAuthenticationKey(new byte[10]);
        verify(settings, Mockito.times(1)).updateAuthenticationKey(ArgumentMatchers.any(byte[].class));

    }

}
