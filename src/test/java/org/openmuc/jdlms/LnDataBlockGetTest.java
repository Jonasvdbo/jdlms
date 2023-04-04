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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.openmuc.jdlms.internal.PduHelper.invokeIdFrom;

import java.io.File;
import java.util.Scanner;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openmuc.jdlms.datatypes.DataObject;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrBoolean;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrOctetString;
import org.openmuc.jdlms.internal.asn1.cosem.COSEMpdu;
import org.openmuc.jdlms.internal.asn1.cosem.DataBlockG;
import org.openmuc.jdlms.internal.asn1.cosem.DataBlockG.SubChoiceResult;
import org.openmuc.jdlms.internal.asn1.cosem.GetRequest;
import org.openmuc.jdlms.internal.asn1.cosem.GetRequestNext;
import org.openmuc.jdlms.internal.asn1.cosem.GetRequestNormal;
import org.openmuc.jdlms.internal.asn1.cosem.GetResponse;
import org.openmuc.jdlms.internal.asn1.cosem.GetResponseWithDatablock;
import org.openmuc.jdlms.internal.asn1.cosem.InvokeIdAndPriority;
import org.openmuc.jdlms.internal.asn1.cosem.Unsigned32;
import org.openmuc.jdlms.settings.client.Settings;

public class LnDataBlockGetTest {

    private static Scanner scanner;

    @BeforeAll
    public static void setUp() throws Exception {
        File file = new File("src/test/resources/kamsprupDataBlockGObjectListGet.txt");
        scanner = new Scanner(file);
    }

    @AfterAll
    public static void shutdown() throws Exception {
        scanner.close();
    }

    @Test
    public void getTest1() throws Exception {
        Settings setting = mock(Settings.class);
        when(setting.frameCounter()).thenReturn(1l);

        DlmsLnConnectionWrapper connection = spy(new DlmsLnConnectionWrapper(setting, null));
        DlmsLnConnectionImpl wCon = spy(connection.getWrappedConnection());

        when(wCon.proposedConformance()).thenCallRealMethod();
        Set<ConformanceSetting> proposedConformance = wCon.proposedConformance();
        when(wCon.negotiatedFeatures()).thenReturn(proposedConformance);

        doAnswer(new Answer<GetResponse>() {
            private int blockNumCounter = 1;

            private InvokeIdAndPriority initalInvokeIdAndPrio;

            @Override
            public GetResponse answer(InvocationOnMock invocation) throws Throwable {
                COSEMpdu pdu = invocation.getArgument(0);
                GetRequest getRequest = pdu.getRequest;
                InvokeIdAndPriority invokeidandpriority;
                if (getRequest.getChoiceIndex() == GetRequest.Choices.GET_REQUEST_NORMAL) {
                    GetRequestNormal getRequestNormal = getRequest.getRequestNormal;
                    invokeidandpriority = getRequestNormal.invokeIdAndPriority;
                    initalInvokeIdAndPrio = invokeidandpriority;
                }
                else if (getRequest.getChoiceIndex() == GetRequest.Choices.GET_REQUEST_NEXT) {
                    GetRequestNext getRequestNext = getRequest.getRequestNext;
                    invokeidandpriority = getRequestNext.invokeIdAndPriority;

                    assertEquals(invokeIdFrom(initalInvokeIdAndPrio), invokeIdFrom(invokeidandpriority),
                            "Client Send a new invoke ID.");
                }
                else {
                    fail("Client send wrong request type..");
                    return null;
                }
                if (!scanner.hasNextLine()) {
                    fail("EOF");
                }

                GetResponse response = new GetResponse();

                SubChoiceResult subChoiceresult = new SubChoiceResult();
                subChoiceresult.rawData = new AxdrOctetString(HexConverter.fromShortHexString(scanner.nextLine()));

                DataBlockG dataBlockG = new DataBlockG(new AxdrBoolean(!scanner.hasNextLine()),
                        new Unsigned32(blockNumCounter++), subChoiceresult);

                response.setGetResponseWithDatablock(new GetResponseWithDatablock(invokeidandpriority, dataBlockG));

                return response;
            }
        }).when(wCon).send(any(COSEMpdu.class));

        when(connection.getWrappedConnection()).thenReturn(wCon);

        GetResult result = connection.get(new AttributeAddress(15, "0.0.40.0.0.255", 2));

        assertNotNull(result);

        DataObject resultData = result.getResultData();

        assertEquals(DataObject.Type.ARRAY, resultData.getType());
    }

}
