package com.asm.generics.attributes.reified;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

import com.asm.generics.attributes.reified.typehints.TypeB;

/*
MethodReturnType_attribute{
    u2 attribute_name_index;
	u4 attribute_length;
	u1 K_M_indicator
	u2 outer_class_indicator
	u2 index 
}
*/
public class MethodReturnType extends Attribute{
    private final TypeB typeB;

    public MethodReturnType() {
        super("MethodReturnType");
        this.typeB = TypeB.NO_HINT;
    }

    public MethodReturnType(TypeB typeB) {
        super("MethodReturnType");
        this.typeB = typeB;
    }
    public TypeB getTypeB() {
        return typeB;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
        int cur = off;
        byte kind = (byte) cr.readByte(cur);
        cur += 1;
        int outerClassIndex = cr.readUnsignedShort(cur);
        cur += 2;
        int index = cr.readUnsignedShort(cur);
        cur += 2;
        return new MethodReturnType(new TypeB(kind, outerClassIndex, index));
    }

    @Override
    public ByteVector write(ClassWriter cw, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ByteVector bv = new ByteVector();
        bv.putByte(typeB.getKind());
        bv.putShort(typeB.getOuterClassIndex());
        bv.putShort(typeB.getIndex());
        return bv;
    }
}
