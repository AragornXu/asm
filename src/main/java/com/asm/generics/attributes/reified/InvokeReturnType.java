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

public class InvokeReturnType extends Attribute{
    private final int count;
    private final List<TypeAHint> typeList;
    public InvokeReturnType() {
        super("InvokeReturnType");
        this.count = 0;
        this.typeList = new ArrayList<>();
    }
    public InvokeReturnType(int count, List<TypeAHint> typeList) {
        super("InvokeReturnType");
        this.count = count;
        this.typeList = typeList;
    }

    public int getCount() {
        return count;
    }

    public List<TypeAHint> getTypeList() {
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
        List<TypeAHint> typeHints = new ArrayList<>();
        for (int i = 0; i < typeHintLength; i++){
            int bytecodeOffset = cr.readUnsignedShort(cur);
            cur += 2;
            int typeAnum = cr.readUnsignedShort(cur);
            cur += 2;
            List<TypeA> typeAs = new ArrayList<>();
            for (int j = 0; j < typeAnum; j++){
                byte kind = (byte) cr.readByte(cur);
                cur += 1;
                int index = cr.readUnsignedShort(cur);
                cur += 2;
                typeAs.add(new TypeA(kind, index));
            }
            typeHints.add(new TypeAHint(bytecodeOffset, typeAs));
        }
        return new InvokeReturnType(typeHintLength, typeHints);
    }

    @Override
    public ByteVector write(ClassWriter cw, byte[] code, int codeLength, int maxStack, int maxLocals) {
        ByteVector bv = new ByteVector();
        bv.putShort(count);
        for (TypeAHint typeHint : typeList) {
            bv.putShort(typeHint.getBytecodeOffset());
            bv.putShort(typeHint.getTypeList().size());
            for (TypeA typeA : typeHint.getTypeList()) {
                bv.putByte(typeA.getKind());
                bv.putShort(typeA.getIndex());
            }
        }
        return bv;
    }
    
}
