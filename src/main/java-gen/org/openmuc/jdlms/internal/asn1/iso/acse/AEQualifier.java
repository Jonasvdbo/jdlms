/*
 * This class file was automatically generated by ASN1bean v1.13.0 (http://www.beanit.com)
 */

package org.openmuc.jdlms.internal.asn1.iso.acse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.beanit.asn1bean.ber.BerTag;
import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;
import com.beanit.asn1bean.ber.types.BerOctetString;
import com.beanit.asn1bean.ber.types.BerType;

public class AEQualifier implements BerType, Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] code = null;
    private AEQualifierForm2 aeQualifierForm2 = null;

    public AEQualifier() {
    }

    public AEQualifier(byte[] code) {
        this.code = code;
    }

    public void setAeQualifierForm2(AEQualifierForm2 aeQualifierForm2) {
        this.aeQualifierForm2 = aeQualifierForm2;
    }

    public AEQualifierForm2 getAeQualifierForm2() {
        return aeQualifierForm2;
    }

    @Override
    public int encode(OutputStream reverseOS) throws IOException {

        if (code != null) {
            reverseOS.write(code);
            return code.length;
        }

        int codeLength = 0;
        if (aeQualifierForm2 != null) {
            codeLength += aeQualifierForm2.encode(reverseOS, true);
            return codeLength;
        }

        throw new IOException("Error encoding CHOICE: No element of CHOICE was selected.");
    }

    @Override
    public int decode(InputStream is) throws IOException {
        return decode(is, null);
    }

    public int decode(InputStream is, BerTag berTag) throws IOException {

        int tlvByteCount = 0;
        boolean tagWasPassed = (berTag != null);

        if (berTag == null) {
            berTag = new BerTag();
            tlvByteCount += berTag.decode(is);
        }

        if (berTag.equals(BerOctetString.tag)) {
            aeQualifierForm2 = new AEQualifierForm2();
            tlvByteCount += aeQualifierForm2.decode(is, false);
            return tlvByteCount;
        }

        if (tagWasPassed) {
            return 0;
        }

        throw new IOException("Error decoding CHOICE: Tag " + berTag + " matched to no item.");
    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream reverseOS = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(reverseOS);
        code = reverseOS.getArray();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendAsString(sb, 0);
        return sb.toString();
    }

    public void appendAsString(StringBuilder sb, int indentLevel) {

        if (aeQualifierForm2 != null) {
            sb.append("aeQualifierForm2: ").append(aeQualifierForm2);
            return;
        }

        sb.append("<none>");
    }

}