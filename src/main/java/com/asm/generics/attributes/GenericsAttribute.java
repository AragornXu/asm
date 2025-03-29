package com.asm.generics.attributes;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

@SuppressWarnings("FieldMayBeFinal")
public class GenericsAttribute extends Attribute {
    private List<String> methodList = new ArrayList<>(); //change to method
    private List<Integer> bcIndex = new ArrayList<>(); //change to offset
    private List<String> typeList = new ArrayList<>();
    private int len;

    public GenericsAttribute() {
        super("GenericsTypeInfoAttribute");
        this.len = 0;
    }

    public GenericsAttribute(GenericsAttribute ga) {
        super("GenericsTypeInfoAttribute");
        this.methodList = new ArrayList<>(ga.methodList);
        this.bcIndex = new ArrayList<>(ga.bcIndex);
        this.typeList = new ArrayList<>(ga.typeList);
        this.len = ga.len;
    }

    public List<String> getMethodList() {
        return methodList;
    }

    public List<Integer> getBcIndex() {
        return bcIndex;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void addToAttribute(String method, int index, String tp){
        methodList.add(method);
        bcIndex.add(index);
        typeList.add(tp);
        len++;
    }

    public List<Integer> getBcIndexForMethod(String method){
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < methodList.size(); i++) {
            if (methodList.get(i).equals(method)) {
                res.add(bcIndex.get(i));
            }
        }
        return res;
    }

    public List<String> getTypeForMethod(String method){
        List<String> res = new ArrayList<>();
        for (int i = 0; i < methodList.size(); i++) {
            if (methodList.get(i).equals(method)) {
                res.add(typeList.get(i));
            }
        }
        return res;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }
    
    @Override
    protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
        System.out.println(String.format("In write generics attribute %d", this.len));
        ByteVector bv = new ByteVector();
        bv.putShort(this.len);
        for (int i = 0; i < this.len; i++) {
            bv.putShort(cw.newUTF8(methodList.get(i)));
            bv.putInt(bcIndex.get(i));
            bv.putShort(cw.newUTF8(typeList.get(i)));
        }
        return bv;
    }

    @Override
    protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
        System.out.println("In read generics attribute");
        GenericsAttribute attr = new GenericsAttribute();
        int count = cr.readUnsignedShort(off);
        int currentOff = off + 2;
        for (int i = 0; i < count; i++) {
            String method = cr.readUTF8(currentOff, buf);
            currentOff += 2;
            int index = cr.readInt(currentOff);
            currentOff += 4;
            String tp = cr.readUTF8(currentOff, buf);
            currentOff += 2;
            attr.addToAttribute(method, index, tp);
        }
        return attr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GenericsTypeInfoAttribute:\n");
        for (int i = 0; i < methodList.size(); i++) {
            sb.append(String.format("  - method=%s, index=%d, type=%s\n",
                methodList.get(i), bcIndex.get(i), typeList.get(i)));
        }
        return sb.toString();
    }
}