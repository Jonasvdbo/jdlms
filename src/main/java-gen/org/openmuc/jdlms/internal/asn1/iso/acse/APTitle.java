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

public class APTitle implements BerType, Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] code = null;
    private APTitleForm2 apTitleForm2 = null;

    public APTitle() {
    }

    public APTitle(byte[] code) {
        this.code = code;
    }

    public void setApTitleForm2(APTitleForm2 apTitleForm2) {
        this.apTitleForm2 = apTitleForm2;
    }

    public APTitleForm2 getApTitleForm2() {
        return apTitleForm2;
    }

    @Override
    public int encode(OutputStream reverseOS) throws IOException {

        if (code != null) {
            reverseOS.write(code);
            return code.length;
        }

        int codeLength = 0;
        if (apTitleForm2 != null) {
            codeLength += apTitleForm2.encode(reverseOS, true);
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
            apTitleForm2 = new APTitleForm2();
            tlvByteCount += apTitleForm2.decode(is, false);
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

        if (apTitleForm2 != null) {
            sb.append("apTitleForm2: ").append(apTitleForm2);
            return;
        }

        sb.append("<none>");
    }

}
