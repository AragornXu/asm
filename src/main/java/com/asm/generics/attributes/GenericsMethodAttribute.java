package com.asm.generics.attributes;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
public class GenericsMethodAttribute extends Attribute{
    private List<Integer> bcIndex = new ArrayList<>(); //change to offset
    private List<String> typeList = new ArrayList<>();
    private int len;

    public GenericsMethodAttribute() {
        super("GenericsTypeInfoOnMethodAttribute");
        this.len = 0;
    }

    public GenericsMethodAttribute(GenericsMethodAttribute gma) {
        super("GenericsTypeInfoOnMethodAttribute");
        this.bcIndex = new ArrayList<>(gma.bcIndex);
        this.typeList = new ArrayList<>(gma.typeList);
        this.len = gma.len;
    }

    public List<Integer> getBcIndex() {
        return bcIndex;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void addToAttribute(int index, String tp){
        bcIndex.add(index);
        typeList.add(tp);
        len++;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }
    
    @Override
    protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
        System.out.println(String.format("In write generics method attribute %d", this.len));
        ByteVector bv = new ByteVector();
        bv.putShort(this.len);
        for (int i = 0; i < this.len; i++) {
            bv.putInt(bcIndex.get(i));
            bv.putShort(cw.newUTF8(typeList.get(i)));
        }
        return bv;
    }

    @Override
    protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
        System.out.println("In read generics method attribute");
        GenericsMethodAttribute attr = new GenericsMethodAttribute();
        int count = cr.readUnsignedShort(off);
        int currentOff = off + 2;
        for (int i = 0; i < count; i++) {
            int index = cr.readInt(currentOff);
            currentOff += 4;
            String tp = cr.readUTF8(currentOff, buf);
            currentOff += 2;
            attr.addToAttribute(index, tp);
        }
        return attr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GenericsTypeInfoOnMethodAttribute:\n");
        for (int i = 0; i < bcIndex.size(); i++) {
            sb.append(String.format("  - index=%d, type=%s\n",
                bcIndex.get(i), typeList.get(i)));
        }
        return sb.toString();
    }
}
