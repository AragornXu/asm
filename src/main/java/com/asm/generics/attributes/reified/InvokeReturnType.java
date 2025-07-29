package com.asm.generics.attributes.reified;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

import com.asm.generics.attributes.reified.typehints.TypeB;
import com.asm.generics.attributes.reified.typehints.TypeBHint;

/*
InvokeReturnType_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
	u2 typehint_length;
	{ 	u2 byecode_offset
		u1 K_M_indicator
        u2 outer_class_indicator
        u2 index
    } typeHints[typehint_length];
}
*/
public class InvokeReturnType extends Attribute{
    private final int count;
    private final List<TypeBHint> typeList;
    public InvokeReturnType() {
        super("InvokeReturnType");
        this.count = 0;
        this.typeList = new ArrayList<>();
    }
    public InvokeReturnType(int count, List<TypeBHint> typeList) {
        super("InvokeReturnType");
        assert count == typeList.size();
        this.count = count;
        this.typeList = typeList;
    }

    public int getCount() {
        return count;
    }

    public List<TypeBHint> getTypeList() {
        return typeList;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }

    @Override
    public Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels){
        int cur = off;
        int typeHintLength = cr.readUnsignedShort(cur);
        cur += 2;
        List<TypeBHint> typeHints = new ArrayList<>();
        for (int i = 0; i < typeHintLength; i++){
            int bytecodeOffset = cr.readUnsignedShort(cur);
            cur += 2;
            byte kind = (byte) cr.readByte(cur);
            cur += 1;
            int outerClassIndex = cr.readUnsignedShort(cur);
            cur += 2;
            int index = cr.readUnsignedShort(cur);
            cur += 2;
            typeHints.add(
                new TypeBHint(
                    bytecodeOffset, 
                    new TypeB(kind, outerClassIndex, index))
            );
        }
        return new InvokeReturnType(typeHintLength, typeHints);
    }

    @Override
    public ByteVector write(ClassWriter cw, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ByteVector bv = new ByteVector();
        bv.putShort(count);
        for (TypeBHint typeHint : typeList) {
            bv.putShort(typeHint.getBytecodeOffset());
            TypeB typeB = typeHint.getTypeB();
            bv.putByte(typeB.getKind());
            bv.putShort(typeB.getOuterClassIndex());
            bv.putShort(typeB.getIndex());
        }
        return bv;
    }
    
}
