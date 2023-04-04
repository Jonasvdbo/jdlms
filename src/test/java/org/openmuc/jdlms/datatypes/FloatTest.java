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
package org.openmuc.jdlms.datatypes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;
import org.openmuc.jdlms.internal.asn1.cosem.GetResponse;

public class FloatTest {

    @Test
    public void decode32() throws IOException {
        GetResponse getResponse = new GetResponse();
        InputStream inputStream = new InputStream() {
            byte[] values = new byte[] { 1, 66, 0, 23, 68, -102, 82, 43 };
            public int pos = 0;

            @Override
            public int read() throws IOException {
                if (pos >= values.length) {
                    return -1;
                }
                int value = values[pos];
                pos++;
                return value >= 0 ? value : 256 + value;
            }
        };

        assertEquals(8, getResponse.decode(inputStream));
        assertEquals(1234.5678f,
                ByteBuffer.wrap(getResponse.getResponseNormal.result.data.float32.getValue()).getFloat(), 0);
    }

    @Test
    public void decode64() throws IOException {
        GetResponse getResponse = new GetResponse();
        InputStream inputStream = new InputStream() {
            byte[] values = new byte[] { 1, 66, 0, 24, -62, 65, -96, 118, 112, 43, 126, 107 };
            public int pos = 0;

            @Override
            public int read() throws IOException {
                if (pos >= values.length) {
                    return -1;
                }
                int value = values[pos];
                pos++;
                return value >= 0 ? value : 256 + value;
            }
        };

        assertEquals(12, getResponse.decode(inputStream));
        assertEquals(-151413121110.987654321,
                ByteBuffer.wrap(getResponse.getResponseNormal.result.data.float64.getValue()).getDouble(), 0);
    }
}
