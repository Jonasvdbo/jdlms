/**
 * This class file was automatically generated by the AXDR compiler that is part of jDLMS (http://www.openmuc.org)
 */

package org.openmuc.jdlms.internal.asn1.cosem;

import java.io.IOException;
import java.io.InputStream;

import org.openmuc.jdlms.internal.asn1.axdr.AxdrType;

import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;

public class AccessRequestGet implements AxdrType {

    public byte[] code = null;
    public CosemAttributeDescriptor cosemAttributeDescriptor = null;

    public AccessRequestGet() {
    }

    public AccessRequestGet(byte[] code) {
        this.code = code;
    }

    public AccessRequestGet(CosemAttributeDescriptor cosemAttributeDescriptor) {
        this.cosemAttributeDescriptor = cosemAttributeDescriptor;
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
            codeLength += cosemAttributeDescriptor.encode(axdrOStream);

        }

        return codeLength;

    }

    @Override
    public int decode(InputStream iStream) throws IOException {
        int codeLength = 0;

        cosemAttributeDescriptor = new CosemAttributeDescriptor();
        codeLength += cosemAttributeDescriptor.decode(iStream);

        return codeLength;
    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream axdrOStream = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(axdrOStream);
        code = axdrOStream.getArray();
    }

    @Override
    public String toString() {
        return "sequence: {" + "cosemAttributeDescriptor: " + cosemAttributeDescriptor + "}";
    }

}
