package com.asm.generics.attributes.reified;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.ClassWriter;

public class MethodTypeParameterCount extends Attribute{
    private final int count;
    public MethodTypeParameterCount() {
        super("MethodTypeParameterCount");
        this.count = 0;
    }
    public MethodTypeParameterCount(int count) {
        super("MethodTypeParameterCount");
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
