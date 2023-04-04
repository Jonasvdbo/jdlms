/**
 * This class file was automatically generated by the AXDR compiler that is part of jDLMS (http://www.openmuc.org)
 */

package org.openmuc.jdlms.internal.asn1.cosem;

import java.io.IOException;
import java.io.InputStream;

import org.openmuc.jdlms.internal.asn1.axdr.AxdrType;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrSequenceOf;

import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;

public class ActionRequestWithListAndFirstPblock implements AxdrType {

    public static class SubSeqOfCosemMethodDescriptorList extends AxdrSequenceOf<CosemMethodDescriptor> {

        @Override
        protected CosemMethodDescriptor createListElement() {
            return new CosemMethodDescriptor();
        }

        protected SubSeqOfCosemMethodDescriptorList(int length) {
            super(length);
        }

        public SubSeqOfCosemMethodDescriptorList() {
        } // Call empty base constructor

    }

    public byte[] code = null;
    public InvokeIdAndPriority invokeIdAndPriority = null;

    public SubSeqOfCosemMethodDescriptorList cosemMethodDescriptorList = null;

    public DataBlockSA pblock = null;

    public ActionRequestWithListAndFirstPblock() {
    }

    public ActionRequestWithListAndFirstPblock(byte[] code) {
        this.code = code;
    }

    public ActionRequestWithListAndFirstPblock(InvokeIdAndPriority invokeIdAndPriority,
            SubSeqOfCosemMethodDescriptorList cosemMethodDescriptorList, DataBlockSA pblock) {
        this.invokeIdAndPriority = invokeIdAndPriority;
        this.cosemMethodDescriptorList = cosemMethodDescriptorList;
        this.pblock = pblock;
    }

    @Override
    public int encode(ReverseByteArrayOutputStream axdrOStream) throws IOException {

        int codeLength;

        if (code != null) {
            codeLength = code.length;
            for (int i = code.length - 1; i >= 0; i--) {
                axdrOStream.write(code[i]);
            }
        }
        else {
            codeLength = 0;
            codeLength += pblock.encode(axdrOStream);

            codeLength += cosemMethodDescriptorList.encode(axdrOStream);

            codeLength += invokeIdAndPriority.encode(axdrOStream);

        }

        return codeLength;

    }

    @Override
    public int decode(InputStream iStream) throws IOException {
        int codeLength = 0;

        invokeIdAndPriority = new InvokeIdAndPriority();
        codeLength += invokeIdAndPriority.decode(iStream);

        cosemMethodDescriptorList = new SubSeqOfCosemMethodDescriptorList();
        codeLength += cosemMethodDescriptorList.decode(iStream);

        pblock = new DataBlockSA();
        codeLength += pblock.decode(iStream);

        return codeLength;
    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream axdrOStream = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(axdrOStream);
        code = axdrOStream.getArray();
    }

    @Override
    public String toString() {
        return "sequence: {" + "invokeIdAndPriority: " + invokeIdAndPriority + ", cosemMethodDescriptorList: "
                + cosemMethodDescriptorList + ", pblock: " + pblock + "}";
    }

}
