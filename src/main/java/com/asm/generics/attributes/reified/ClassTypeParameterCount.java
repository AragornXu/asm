package com.asm.generics.attributes.reified;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

/*
ClassTypeParameterCount_attribute {
    u2 attribute_name_index;
	u4 attribute_length;
	u2 count;
}
*/
public class ClassTypeParameterCount extends Attribute{
    private final int count;
    public ClassTypeParameterCount() {
        super("ClassTypeParameterCount");
        this.count = 0;
    }
    public ClassTypeParameterCount(int count) {
        super("ClassTypeParameterCount");
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    @Override
    public boolean isUnknown() {
        return false;
    }
    @Override
    protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
        int localCount = cr.readUnsignedShort(off);
        return new MethodTypeParameterCount(localCount);
    }

    @Override
    protected ByteVector write(ClassWriter cw, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ByteVector bv = new ByteVector();
        bv.putShort(count);
        return bv;
    }
}
