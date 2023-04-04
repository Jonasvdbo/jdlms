/**
 * This class file was automatically generated by the AXDR compiler that is part of jDLMS (http://www.openmuc.org)
 */

package org.openmuc.axdr.compiler.axdrexample.generated;

import java.io.IOException;
import java.io.InputStream;

import org.openmuc.jdlms.internal.asn1.axdr.AxdrType;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrEnum;

import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;

public class ChildInformation implements AxdrType {

    public byte[] code = null;
    public Name name = null;

    public Date dateOfBirth = null;

    public AxdrEnum gender = null;

    public ChildInformation() {
    }

    public ChildInformation(byte[] code) {
        this.code = code;
    }

    public ChildInformation(Name name, Date dateOfBirth, AxdrEnum gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
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
            codeLength += gender.encode(axdrOStream);

            codeLength += dateOfBirth.encode(axdrOStream);

            codeLength += name.encode(axdrOStream);

        }

        return codeLength;

    }

    @Override
    public int decode(InputStream iStream) throws IOException {
        int codeLength = 0;

        name = new Name();
        codeLength += name.decode(iStream);

        dateOfBirth = new Date();
        codeLength += dateOfBirth.decode(iStream);

        gender = new AxdrEnum();
        codeLength += gender.decode(iStream);

        return codeLength;
    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream axdrOStream = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(axdrOStream);
        code = axdrOStream.getArray();
    }

    @Override
    public String toString() {
        return "sequence: {" + "name: " + name + ", dateOfBirth: " + dateOfBirth + ", gender: " + gender + "}";
    }

}
