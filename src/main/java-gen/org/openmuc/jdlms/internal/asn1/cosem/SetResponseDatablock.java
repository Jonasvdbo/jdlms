/**
 * This class file was automatically generated by the AXDR compiler that is part of jDLMS (http://www.openmuc.org)
 */

package org.openmuc.jdlms.internal.asn1.cosem;

import java.io.IOException;
import java.io.InputStream;

import org.openmuc.jdlms.internal.asn1.axdr.AxdrType;

import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;

public class SetResponseDatablock implements AxdrType {

    public byte[] code = null;
    public InvokeIdAndPriority invokeIdAndPriority = null;

    public Unsigned32 blockNumber = null;

    public SetResponseDatablock() {
    }

    public SetResponseDatablock(byte[] code) {
        this.code = code;
    }

    public SetResponseDatablock(InvokeIdAndPriority invokeIdAndPriority, Unsigned32 blockNumber) {
        this.invokeIdAndPriority = invokeIdAndPriority;
        this.blockNumber = blockNumber;
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
            codeLength += blockNumber.encode(axdrOStream);

            codeLength += invokeIdAndPriority.encode(axdrOStream);

        }

        return codeLength;

    }

    @Override
    public int decode(InputStream iStream) throws IOException {
        int codeLength = 0;

        invokeIdAndPriority = new InvokeIdAndPriority();
        codeLength += invokeIdAndPriority.decode(iStream);

        blockNumber = new Unsigned32();
        codeLength += blockNumber.decode(iStream);

        return codeLength;
    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream axdrOStream = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(axdrOStream);
        code = axdrOStream.getArray();
    }

    @Override
    public String toString() {
        return "sequence: {" + "invokeIdAndPriority: " + invokeIdAndPriority + ", blockNumber: " + blockNumber + "}";
    }

}
