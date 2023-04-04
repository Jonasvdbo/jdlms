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
package org.openmuc.jdlms.sessionlayer.hdlc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openmuc.jdlms.TestHelper.setField;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openmuc.jdlms.sessionlayer.hdlc.FrameRejectReason.RejectReason;

public class FrameRejectReasonTest {
    @Test
    public void testSymmetry() throws Exception {
        RejectReason[] rejectReasonsArray = new RejectReason[] { RejectReason.CONTOL_FIELD_UNDEFINED,
                RejectReason.INVALID_INFORMATION };

        final List<RejectReason> rejectReasons = Arrays.asList(rejectReasonsArray);

        FrameRejectReason frameRejectReason = new FrameRejectReason((byte) 0);
        setField(frameRejectReason, FrameRejectReason.class, "rejectReasons", rejectReasons);

        byte[] encode = frameRejectReason.encode();
        List<RejectReason> rejectReasons2 = FrameRejectReason.decode(encode).rejectReasons();

        assertEquals(rejectReasons, rejectReasons2);
    }

}
