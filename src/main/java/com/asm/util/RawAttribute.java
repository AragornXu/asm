package com.asm.util;

import java.util.Arrays;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

public class RawAttribute extends Attribute {
    public byte[] rawContent;

    public RawAttribute(String type) {
        super(type);
    }

    @Override
    protected Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
        RawAttribute attr = new RawAttribute(type);
        attr.rawContent = Arrays.copyOfRange(cr.b, off, off + len);
        return attr;
    }

    @Override
    protected ByteVector write(ClassWriter cw, byte[] code, int len, int maxStack, int maxLocals) {
        ByteVector bv = new ByteVector();
        bv.putByteArray(rawContent, 0, rawContent.length);
        return bv;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(": ");
        for (byte b : rawContent) {
            sb.append(String.format("%02x ", b));
        }
        return sb.toString();
    }
}