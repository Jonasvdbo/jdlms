/**
 * This class file was automatically generated by the AXDR compiler that is part of jDLMS (http://www.openmuc.org)
 */

package org.openmuc.jdlms.internal.asn1.cosem;

import java.io.IOException;
import java.io.InputStream;

import org.openmuc.jdlms.internal.asn1.axdr.AxdrType;
import org.openmuc.jdlms.internal.asn1.axdr.types.AxdrEnum;

import com.beanit.asn1bean.ber.ReverseByteArrayOutputStream;

public class VariableAccessSpecification implements AxdrType {

    public byte[] code = null;

    public static enum Choices {
        _ERR_NONE_SELECTED(-1),
        VARIABLE_NAME(2),
        PARAMETERIZED_ACCESS(4),
        BLOCK_NUMBER_ACCESS(5),
        READ_DATA_BLOCK_ACCESS(6),
        WRITE_DATA_BLOCK_ACCESS(7),;

        private int value;

        private Choices(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static Choices valueOf(long tagValue) {
            Choices[] values = Choices.values();

            for (Choices c : values) {
                if (c.value == tagValue) {
                    return c;
                }
            }
            return _ERR_NONE_SELECTED;
        }
    }

    private Choices choice;

    public Integer16 variableName = null;

    public ParameterizedAccess parameterizedAccess = null;

    public BlockNumberAccess blockNumberAccess = null;

    public ReadDataBlockAccess readDataBlockAccess = null;

    public WriteDataBlockAccess writeDataBlockAccess = null;

    public VariableAccessSpecification() {
    }

    public VariableAccessSpecification(byte[] code) {
        this.code = code;
    }

    @Override
    public int encode(ReverseByteArrayOutputStream axdrOStream) throws IOException {
        if (code != null) {
            for (int i = code.length - 1; i >= 0; i--) {
                axdrOStream.write(code[i]);
            }
            return code.length;

        }
        if (choice == Choices._ERR_NONE_SELECTED) {
            throw new IOException("Error encoding AxdrChoice: No item in choice was selected.");
        }

        int codeLength = 0;

        if (choice == Choices.WRITE_DATA_BLOCK_ACCESS) {
            codeLength += writeDataBlockAccess.encode(axdrOStream);
            AxdrEnum c = new AxdrEnum(7);
            codeLength += c.encode(axdrOStream);
            return codeLength;
        }

        if (choice == Choices.READ_DATA_BLOCK_ACCESS) {
            codeLength += readDataBlockAccess.encode(axdrOStream);
            AxdrEnum c = new AxdrEnum(6);
            codeLength += c.encode(axdrOStream);
            return codeLength;
        }

        if (choice == Choices.BLOCK_NUMBER_ACCESS) {
            codeLength += blockNumberAccess.encode(axdrOStream);
            AxdrEnum c = new AxdrEnum(5);
            codeLength += c.encode(axdrOStream);
            return codeLength;
        }

        if (choice == Choices.PARAMETERIZED_ACCESS) {
            codeLength += parameterizedAccess.encode(axdrOStream);
            AxdrEnum c = new AxdrEnum(4);
            codeLength += c.encode(axdrOStream);
            return codeLength;
        }

        if (choice == Choices.VARIABLE_NAME) {
            codeLength += variableName.encode(axdrOStream);
            AxdrEnum c = new AxdrEnum(2);
            codeLength += c.encode(axdrOStream);
            return codeLength;
        }

        // This block should be unreachable
        throw new IOException("Error encoding AxdrChoice: No item in choice was encoded.");
    }

    @Override
    public int decode(InputStream iStream) throws IOException {
        int codeLength = 0;
        AxdrEnum choosen = new AxdrEnum();

        codeLength += choosen.decode(iStream);
        resetChoices();
        this.choice = Choices.valueOf(choosen.getValue());

        if (choice == Choices.VARIABLE_NAME) {
            variableName = new Integer16();
            codeLength += variableName.decode(iStream);
            return codeLength;
        }

        if (choice == Choices.PARAMETERIZED_ACCESS) {
            parameterizedAccess = new ParameterizedAccess();
            codeLength += parameterizedAccess.decode(iStream);
            return codeLength;
        }

        if (choice == Choices.BLOCK_NUMBER_ACCESS) {
            blockNumberAccess = new BlockNumberAccess();
            codeLength += blockNumberAccess.decode(iStream);
            return codeLength;
        }

        if (choice == Choices.READ_DATA_BLOCK_ACCESS) {
            readDataBlockAccess = new ReadDataBlockAccess();
            codeLength += readDataBlockAccess.decode(iStream);
            return codeLength;
        }

        if (choice == Choices.WRITE_DATA_BLOCK_ACCESS) {
            writeDataBlockAccess = new WriteDataBlockAccess();
            codeLength += writeDataBlockAccess.decode(iStream);
            return codeLength;
        }

        throw new IOException("Error decoding AxdrChoice: Identifier matched to no item.");
    }

    public void encodeAndSave(int encodingSizeGuess) throws IOException {
        ReverseByteArrayOutputStream axdrOStream = new ReverseByteArrayOutputStream(encodingSizeGuess);
        encode(axdrOStream);
        code = axdrOStream.getArray();
    }

    public Choices getChoiceIndex() {
        return this.choice;
    }

    public void setVariableName(Integer16 newVal) {
        resetChoices();
        choice = Choices.VARIABLE_NAME;
        variableName = newVal;
    }

    public void setParameterizedAccess(ParameterizedAccess newVal) {
        resetChoices();
        choice = Choices.PARAMETERIZED_ACCESS;
        parameterizedAccess = newVal;
    }

    public void setBlockNumberAccess(BlockNumberAccess newVal) {
        resetChoices();
        choice = Choices.BLOCK_NUMBER_ACCESS;
        blockNumberAccess = newVal;
    }

    public void setReadDataBlockAccess(ReadDataBlockAccess newVal) {
        resetChoices();
        choice = Choices.READ_DATA_BLOCK_ACCESS;
        readDataBlockAccess = newVal;
    }

    public void setWriteDataBlockAccess(WriteDataBlockAccess newVal) {
        resetChoices();
        choice = Choices.WRITE_DATA_BLOCK_ACCESS;
        writeDataBlockAccess = newVal;
    }

    private void resetChoices() {
        choice = Choices._ERR_NONE_SELECTED;
        variableName = null;
        parameterizedAccess = null;
        blockNumberAccess = null;
        readDataBlockAccess = null;
        writeDataBlockAccess = null;
    }

    @Override
    public String toString() {
        if (choice == Choices.VARIABLE_NAME) {
            return "choice: {variableName: " + variableName + "}";
        }

        if (choice == Choices.PARAMETERIZED_ACCESS) {
            return "choice: {parameterizedAccess: " + parameterizedAccess + "}";
        }

        if (choice == Choices.BLOCK_NUMBER_ACCESS) {
            return "choice: {blockNumberAccess: " + blockNumberAccess + "}";
        }

        if (choice == Choices.READ_DATA_BLOCK_ACCESS) {
            return "choice: {readDataBlockAccess: " + readDataBlockAccess + "}";
        }

        if (choice == Choices.WRITE_DATA_BLOCK_ACCESS) {
            return "choice: {writeDataBlockAccess: " + writeDataBlockAccess + "}";
        }

        return "unknown";
    }

}
