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
import com.beanit.asn1bean.ber.types.BerType;

public class ACSEApdu implements BerType, Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] code = null;
    private AARQApdu aarq = null;
    private AAREApdu aare = null;
    private RLRQApdu rlrq = null;
    private RLREApdu rlre = null;

    public ACSEApdu() {
    }

    public ACSEApdu(byte[] code) {
        this.code = code;
    }

    public void setAarq(AARQApdu aarq) {
        this.aarq = aarq;
    }

    public AARQApdu getAarq() {
        return aarq;
    }

    public void setAare(AAREApdu aare) {
        this.aare = aare;
    }

    public AAREApdu getAare() {
        return aare;
    }

    public void setRlrq(RLRQApdu rlrq) {
        this.rlrq = rlrq;
    }

    public RLRQApdu getRlrq() {
        return rlrq;
    }

    public void setRlre(RLREApdu rlre) {
        this.rlre = rlre;
    }

    public RLREApdu getRlre() {
        return rlre;
    }

    @Override
    public int encode(OutputStream reverseOS) throws IOException {

        if (code != null) {
            reverseOS.write(code);
            return code.length;
        }

        int codeLength = 0;
        if (rlre != null) {
            codeLength += rlre.encode(reverseOS, true);
            return codeLength;
        }

        if (rlrq != null) {
            codeLength += rlrq.encode(reverseOS, true);
            return codeLength;
        }

        if (aare != null) {
            codeLength += aare.encode(reverseOS, true);
            return codeLength;
        }

        if (aarq != null) {
            codeLength += aarq.encode(reverseOS, true);
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

        if (berTag.equals(AARQApdu.tag)) {
            aarq = new AARQApdu();
            tlvByteCount += aarq.decode(is, false);
            return tlvByteCount;
        }

        if (berTag.equals(AAREApdu.tag)) {
            aare = new AAREApdu();
            tlvByteCount += aare.decode(is, false);
            return tlvByteCount;
        }

        if (berTag.equals(RLRQApdu.tag)) {
            rlrq = new RLRQApdu();
            tlvByteCount += rlrq.decode(is, false);
            return tlvByteCount;
        }

        if (berTag.equals(RLREApdu.tag)) {
            rlre = new RLREApdu();
            tlvByteCount += rlre.decode(is, false);
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

        if (aarq != null) {
            sb.append("aarq: ");
            aarq.appendAsString(sb, indentLevel + 1);
            return;
        }

        if (aare != null) {
            sb.append("aare: ");
            aare.appendAsString(sb, indentLevel + 1);
            return;
        }

        if (rlrq != null) {
            sb.append("rlrq: ");
            rlrq.appendAsString(sb, indentLevel + 1);
            return;
        }

        if (rlre != null) {
            sb.append("rlre: ");
            rlre.appendAsString(sb, indentLevel + 1);
            return;
        }

        sb.append("<none>");
    }

}
