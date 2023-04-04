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

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openmuc.jdlms.datatypes.DataObject;

public class SelectivAccessServerTest {

    @ParameterizedTest
    @MethodSource("paramsT1")
    @DisplayName("Testing error case with {0} {1}")
    public void test_exception_handling(CosemInterfaceObject... cosemInterfaceObject) throws Exception {
        assertThrows(IllegalPametrizationError.class, () -> execute(cosemInterfaceObject)); //
    }

    static Object paramsT1() {
        return Stream.of(Arguments.of((Object) new CosemInterfaceObject[] { new DemoClass() }),
                Arguments.of((Object) new CosemInterfaceObject[] { new DemoClass2() }),
                Arguments.of((Object) new CosemInterfaceObject[] { new DemoClass3(), new DemoClass3() }),
                Arguments.of((Object) new CosemInterfaceObject[] { new DemoClass4() }),
                Arguments.of((Object) new CosemInterfaceObject[] { new DemoClass8() }));
    }

    private static LogicalDevice newDemoLD() {
        return new LogicalDevice(1, "", "ISE", 1);
    }

    @CosemClass(id = 2)
    static class DemoClass extends CosemObj0 {
        @CosemAttribute(id = 1)
        private DataObject d1;
    }

    @CosemClass(id = 2)
    static class DemoClass2 extends CosemObj0 {
        @CosemAttribute(id = 2)
        private DataObject d1;
        @CosemAttribute(id = 2)
        private DataObject d2;
    }

    @CosemClass(id = 2)
    static class DemoClass3 extends CosemObj0 {
        @CosemAttribute(id = 2)
        private DataObject d1;
    }

    @CosemClass(id = 2)
    static class DemoClass4 extends CosemObj0 {
        @CosemAttribute(id = 2, selector = 1)
        private DataObject d1;
    }

    @ParameterizedTest
    @MethodSource("paramsT2")
    @DisplayName("test valid case with {0}")
    public void test_valid_case(CosemInterfaceObject cosemInterfaceObject) throws Exception {
        execute(cosemInterfaceObject);
    }

    private static void execute(CosemInterfaceObject... cosemInterfaceObject) throws Exception {
        LogicalDevice logicalDevice = newDemoLD();
        logicalDevice.registerCosemObject(cosemInterfaceObject);
        DlmsServer.tcpServerBuilder(0).registerLogicalDevice(logicalDevice).build().close();
    }

    static Object paramsT2() {
        return new Object[][] { { new DemoClass5() }, { new DemoClass6() }, { new DemoClass7() },
                { new DemoClass9() } };
    }

    @CosemClass(id = 2)
    static class DemoClass5 extends CosemObj0 {
        @CosemAttribute(id = 2)
        private DataObject d1;

        public DataObject getD1() {
            return d1;
        }

        public void setD1(DataObject d1) {
            this.d1 = d1;
        }
    }

    @CosemClass(id = 2)
    static class DemoClass6 extends CosemObj0 {
        @CosemAttribute(id = 2, selector = 1)
        private DataObject d1;

        public DataObject getD1(SelectiveAccessDescription selectiveAccessDescription) {
            return d1;
        }

        public void setD1(DataObject d1, SelectiveAccessDescription selectiveAccessDescription) {
            this.d1 = d1;
        }
    }

    @CosemClass(id = 2)
    static class DemoClass7 extends CosemObj0 {
        @CosemAttribute(id = 2)
        private DataObject d1;

        public DataObject getD1(Long id) {
            return d1;
        }

        public void setD1(DataObject d1) {
            this.d1 = d1;
        }
    }

    @CosemClass(id = 2)
    static class DemoClass8 extends CosemObj0 {
        @CosemAttribute(id = 2)
        private DataObject d1;

        public DataObject getD1(SelectiveAccessDescription selectiveAccessDescription, Long id) {
            return d1;
        }

        public void setD1(DataObject d1) {
            this.d1 = d1;
        }
    }

    @CosemClass(id = 2)
    static class DemoClass9 extends CosemObj0 {

        @CosemAttribute(id = 2, selector = { 1, 2 }, accessMode = AttributeAccessMode.READ_ONLY)
        private DataObject d1;

        public DataObject getD1(SelectiveAccessDescription selectiveAccessDescription, Long id) {
            return d1;
        }

        public void setD1(DataObject d1, SelectiveAccessDescription selectiveAccessDescription, Long id) {
            this.d1 = d1;
        }
    }

    static abstract class CosemObj0 extends CosemInterfaceObject {

        public CosemObj0() {
            super("99.0.0.0.99.0");
        }

    }
}
