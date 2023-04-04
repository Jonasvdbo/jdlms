/**
 * This class file was automatically generated by the AXDR compiler that is part of jDLMS (http://www.openmuc.org)
 */

package org.openmuc.jdlms.internal.asn1.cosem;

import java.io.IOException;
import java.io.InputStream;

import org.openmuc.jdlms.internal.asn1.axdr.AxdrType;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrEnum;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrOptional;

import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;

public class ActionResponseWithOptionalData implements AxdrType {

    public byte[] code = null;
    public AxdrEnum result = null;

    public AxdrOptional<GetDataResult> returnParameters = new AxdrOptional<>(new GetDataResult(), false);

    public ActionResponseWithOptionalData() {
    }

    public ActionResponseWithOptionalData(byte[] code) {
        this.code = code;
    }

    public ActionResponseWithOptionalData(AxdrEnum result, GetDataResult returnParameters) {
        this.result = result;
        this.returnParameters.setValue(returnParameters);
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
            codeLength += returnParameters.encode(axdrOStream);

            codeLength += result.encode(axdrOStream);

        }

        return codeLength;

    }

    @Override
    public int decode(InputStream iStream) throws IOException {
        int codeLength = 0;

        result = new AxdrEnum();
        codeLength += result.decode(iStream);

        returnParameters = new AxdrOptional<>(new GetDataResult(), false);
        codeLength += returnParameters.decode(iStream);

        return codeLength;
    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream axdrOStream = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(axdrOStream);
        code = axdrOStream.getArray();
    }

    @Override
    public String toString() {
        return "sequence: {" + "result: " + result + ", returnParameters: " + returnParameters + "}";
    }

}
