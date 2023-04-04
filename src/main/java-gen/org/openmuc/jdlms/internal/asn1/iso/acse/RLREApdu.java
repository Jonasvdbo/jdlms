/*
 * This class file was automatically generated by ASN1bean v1.13.0 (http://www.beanit.com)
 */

package org.openmuc.jdlms.internal.asn1.iso.acse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.beanit.asn1bean.ber.BerLength;
import com.beanit.asn1bean.ber.BerTag;
import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;
import com.beanit.asn1bean.ber.types.BerType;

public class RLREApdu implements BerType, Serializable {

    private static final long serialVersionUID = 1L;

    public static final BerTag tag = new BerTag(BerTag.APPLICATION_CLASS, BerTag.CONSTRUCTED, 3);

    private byte[] code = null;
    private ReleaseResponseReason reason = null;
    private AssociationInformation userInformation = null;

    public RLREApdu() {
    }

    public RLREApdu(byte[] code) {
        this.code = code;
    }

    public void setReason(ReleaseResponseReason reason) {
        this.reason = reason;
    }

    public ReleaseResponseReason getReason() {
        return reason;
    }

    public void setUserInformation(AssociationInformation userInformation) {
        this.userInformation = userInformation;
    }

    public AssociationInformation getUserInformation() {
        return userInformation;
    }

    @Override
    public int encode(OutputStream reverseOS) throws IOException {
        return encode(reverseOS, true);
    }

    public int encode(OutputStream reverseOS, boolean withTag) throws IOException {

        if (code != null) {
            reverseOS.write(code);
            if (withTag) {
                return tag.encode(reverseOS) + code.length;
            }
            return code.length;
        }

        int codeLength = 0;
        int sublength;

        if (userInformation != null) {
            sublength = userInformation.encode(reverseOS, true);
            codeLength += sublength;
            codeLength += BerLength.encodeLength(reverseOS, sublength);
            // write tag: CONTEXT_CLASS, CONSTRUCTED, 30
            reverseOS.write(0xBE);
            codeLength += 1;
        }

        if (reason != null) {
            codeLength += reason.encode(reverseOS, false);
            // write tag: CONTEXT_CLASS, PRIMITIVE, 0
            reverseOS.write(0x80);
            codeLength += 1;
        }

        codeLength += BerLength.encodeLength(reverseOS, codeLength);

        if (withTag) {
            codeLength += tag.encode(reverseOS);
        }

        return codeLength;

    }

    @Override
    public int decode(InputStream is) throws IOException {
        return decode(is, true);
    }

    public int decode(InputStream is, boolean withTag) throws IOException {
        int tlByteCount = 0;
        int vByteCount = 0;
        BerTag berTag = new BerTag();

        if (withTag) {
            tlByteCount += tag.decodeAndCheck(is);
        }

        BerLength length = new BerLength();
        tlByteCount += length.decode(is);
        int lengthVal = length.val;
        if (lengthVal == 0) {
            return tlByteCount;
        }
        vByteCount += berTag.decode(is);

        if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.PRIMITIVE, 0)) {
            reason = new ReleaseResponseReason();
            vByteCount += reason.decode(is, false);
            if (lengthVal >= 0 && vByteCount == lengthVal) {
                return tlByteCount + vByteCount;
            }
            vByteCount += berTag.decode(is);
        }

        if (berTag.equals(BerTag.CONTEXT_CLASS, BerTag.CONSTRUCTED, 30)) {
            vByteCount += length.decode(is);
            userInformation = new AssociationInformation();
            vByteCount += userInformation.decode(is, true);
            vByteCount += length.readEocIfIndefinite(is);
            if (lengthVal >= 0 && vByteCount == lengthVal) {
                return tlByteCount + vByteCount;
            }
            vByteCount += berTag.decode(is);
        }

        if (lengthVal < 0) {
            if (!berTag.equals(0, 0, 0)) {
                throw new IOException("Decoded sequence has wrong end of contents octets");
            }
            vByteCount += BerLength.readEocByte(is);
            return tlByteCount + vByteCount;
        }

        throw new IOException(
                "Unexpected end of sequence, length tag: " + lengthVal + ", bytes decoded: " + vByteCount);

    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream reverseOS = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(reverseOS, false);
        code = reverseOS.getArray();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendAsString(sb, 0);
        return sb.toString();
    }

    public void appendAsString(StringBuilder sb, int indentLevel) {

        sb.append("{");
        boolean firstSelectedElement = true;
        if (reason != null) {
            sb.append("\n");
            for (int i = 0; i < indentLevel + 1; i++) {
                sb.append("\t");
            }
            sb.append("reason: ").append(reason);
            firstSelectedElement = false;
        }

        if (userInformation != null) {
            if (!firstSelectedElement) {
                sb.append(",\n");
            }
            for (int i = 0; i < indentLevel + 1; i++) {
                sb.append("\t");
            }
            sb.append("userInformation: ").append(userInformation);
        }

        sb.append("\n");
        for (int i = 0; i < indentLevel; i++) {
            sb.append("\t");
        }
        sb.append("}");
    }

}
