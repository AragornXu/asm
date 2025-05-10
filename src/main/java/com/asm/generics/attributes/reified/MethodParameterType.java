package com.asm.generics.attributes.reified;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

import com.asm.generics.attributes.reified.typehints.TypeB;

public class MethodParameterType extends Attribute{
    private final int count;
    private final List<TypeB> typeList;
    public MethodParameterType() {
        super("MethodParameterType");
        this.count = 0;
        this.typeList = new ArrayList<>();
    }

    public MethodParameterType(int count, List<TypeB> typeList) {
        super("MethodParameterType");
        this.count = count;
        this.typeList = typeList;
    }

    public int getCount() {
        return count;
    }

    public List<TypeB> getTypeList() {
        return typeList;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }

    @Override
    public Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels){
        int cur = off;
        int parameterCount = cr.readUnsignedShort(cur);
        cur += 2;
        List<TypeB> typeBs = new ArrayList<>();
        for (int i = 0; i < parameterCount; i++) {
            byte kind = (byte) cr.readByte(cur);
            cur += 1;
            int index = cr.readUnsignedShort(cur);
            cur += 2;
            typeBs.add(new TypeB(kind, index));
        }
        return new MethodParameterType(parameterCount, typeBs);
    }

    @Override
    public ByteVector write(ClassWriter cw, byte[] code, int codeLength, int maxStack, int maxLocals){
        ByteVector bv = new ByteVector();
        bv.putShort(count);
        for (TypeB typeB : typeList) {
            bv.putByte(typeB.getKind());
            bv.putShort(typeB.getIndex());
        }
        return bv;
    }

}
