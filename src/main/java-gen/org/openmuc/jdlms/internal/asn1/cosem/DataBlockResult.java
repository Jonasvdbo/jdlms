/**
 * This class file was automatically generated by the AXDR compiler that is part of jDLMS (http://www.openmuc.org)
 */

package org.openmuc.jdlms.internal.asn1.cosem;

import java.io.IOException;
import java.io.InputStream;

import org.openmuc.jdlms.internal.asn1.axdr.AxdrType;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrBoolean;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrOctetString;

import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;

public class DataBlockResult implements AxdrType {

    public byte[] code = null;
    public AxdrBoolean lastBlock = null;

    public Unsigned16 blockNumber = null;

    public AxdrOctetString rawData = null;

    public DataBlockResult() {
    }

    public DataBlockResult(byte[] code) {
        this.code = code;
    }

    public DataBlockResult(AxdrBoolean lastBlock, Unsigned16 blockNumber, AxdrOctetString rawData) {
        this.lastBlock = lastBlock;
        this.blockNumber = blockNumber;
        this.rawData = rawData;
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
            codeLength += rawData.encode(axdrOStream);

            codeLength += blockNumber.encode(axdrOStream);

            codeLength += lastBlock.encode(axdrOStream);

        }

        return codeLength;

    }

    @Override
    public int decode(InputStream iStream) throws IOException {
        int codeLength = 0;

        lastBlock = new AxdrBoolean();
        codeLength += lastBlock.decode(iStream);

        blockNumber = new Unsigned16();
        codeLength += blockNumber.decode(iStream);

        rawData = new AxdrOctetString();
        codeLength += rawData.decode(iStream);

        return codeLength;
    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream axdrOStream = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(axdrOStream);
        code = axdrOStream.getArray();
    }

    @Override
    public String toString() {
        return "sequence: {" + "lastBlock: " + lastBlock + ", blockNumber: " + blockNumber + ", rawData: " + rawData
                + "}";
    }

}
