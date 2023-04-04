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
package org.openmuc.jdlms.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.openmuc.jdlms.HexConverter;

public class MessageFragmentTest {

    @Test
    public void testReadAll() throws Exception {
        String byteAsString = readFirstLineOfDemoMessage();

        byte[] bytes = HexConverter.fromShortHexString(byteAsString);

        final int fragmentSize = 2;
        MessageFragment fragment = new MessageFragment(bytes, fragmentSize);

        StringBuilder fragmentString = new StringBuilder(byteAsString.length());

        while (fragment.hasNext()) {
            byte[] nextFragment = fragment.next();

            assertTrue(nextFragment.length <= fragmentSize, "Fragment size larger than specified.");

            fragmentString.append(HexConverter.toShortHexString(nextFragment));
        }

        assertEquals(byteAsString.toUpperCase(), fragmentString.toString());
    }

    private String readFirstLineOfDemoMessage() throws FileNotFoundException {
        String pathname = "src/test/resources/kamsprupDataBlockGObjectListGet.txt";
        File file = new File(pathname);

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder shortHexString = new StringBuilder();

            String message = MessageFormat.format("File ''{0}'' can''t be empty.", pathname);
            assertTrue(scanner.hasNextLine(), message);

            shortHexString.append(scanner.nextLine());

            return shortHexString.toString();
        }
    }
}
