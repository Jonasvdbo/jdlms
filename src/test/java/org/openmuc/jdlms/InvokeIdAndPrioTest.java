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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.openmuc.jdlms.TestHelper.setField;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.openmuc.jdlms.internal.asn1.cosem.InvokeIdAndPriority;

public class InvokeIdAndPrioTest {

    @ParameterizedTest
    @CsvSource({ "false, 0x81, true", "true, 0xC1, true", "true, 0x41, false" })
    @DisplayName("invokeIdAndPriorityFor(prioHigh = {2}, confirmedMode = {0}) = {1}, invokeId = 1")
    public void testInvoke_Id_And_Priority(boolean confirmedModeEnabled, String expectedRes, boolean prio)
            throws Exception {
        BaseDlmsConnection connection = mock(BaseDlmsConnection.class);

        when(connection.invokeIdAndPriorityFor(ArgumentMatchers.anyBoolean())).thenCallRealMethod();
        when(connection.confirmedModeEnabled()).thenReturn(confirmedModeEnabled);

        setField(connection, BaseDlmsConnection.class, "invokeId", 1);

        InvokeIdAndPriority idAndPriority = connection.invokeIdAndPriorityFor(prio);

        byte expectedResByte = (byte) Integer.parseInt(expectedRes.substring(2), 16);
        assertEquals(expectedResByte, idAndPriority.getValue()[0]);
    }
}
