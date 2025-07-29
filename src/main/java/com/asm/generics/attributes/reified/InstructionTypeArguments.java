package com.asm.generics.attributes.reified;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

import com.asm.generics.attributes.reified.typehints.TypeA;
import com.asm.generics.attributes.reified.typehints.TypeAHint;

/*
InstructionTypeArguments_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
	u2 typehint_length;
	{ 	u2 byecode_offset
	    u2 typeA_number
        {	u1 K_M_indicator
            u2 outer_class_indicator
            u2 index
        } typeAs[typeA_number]
    } typeHints[typehint_length];
}
*/
public class InstructionTypeArguments extends Attribute {
    private final List<TypeAHint> typeArguments;
    public InstructionTypeArguments() {
        super("InstructionTypeArguments");
        this.typeArguments = new ArrayList<>();
    }
    public InstructionTypeArguments(List<TypeAHint> typeArguments) {
        super("InstructionTypeArguments");
        this.typeArguments = typeArguments;
    }
    @Override
    public boolean isUnknown() {
        return false;
    }
    @Override
    public Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
        int cur = off;
        int typeHintLength = cr.readUnsignedShort(cur);
        cur += 2;
        List<TypeAHint> typeHints = new ArrayList<>();
        for (int i = 0; i < typeHintLength; i++) {
            int bytecodeOffset = cr.readUnsignedShort(cur);
            cur += 2;
            int typeAnum = cr.readUnsignedShort(cur);
            cur += 2;
            List<TypeA> typeList = new ArrayList<>();
            for (int j = 0 ; j < typeAnum; j++) {
                byte kind = (byte) cr.readByte(cur);
                cur += 1;
                int outerClassIndex = cr.readUnsignedShort(cur);
                cur += 2;
                int index = cr.readUnsignedShort(cur);
                cur += 2;
                typeList.add(new TypeA(kind, outerClassIndex, index));
            }
            typeHints.add(new TypeAHint(bytecodeOffset, typeList));
        }
        return new InstructionTypeArguments(typeHints);
    }

    @Override
    public ByteVector write(ClassWriter cw, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ByteVector bv = new ByteVector();
        bv.putShort(typeArguments.size());
        for (TypeAHint typeHint : typeArguments) {
            bv.putShort(typeHint.getBytecodeOffset());
            bv.putShort(typeHint.getTypeList().size());
            for (TypeA typeA : typeHint.getTypeList()) {
                bv.putByte(typeA.getKind());
                bv.putShort(typeA.getOuterClassIndex());
                bv.putShort(typeA.getIndex());
            }
        }
        return bv;
    }

    public List<TypeAHint> getTypeArguments() {
        return typeArguments;
    }
}
