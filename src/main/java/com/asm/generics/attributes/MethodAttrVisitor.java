package com.asm.generics.attributes;

import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.MethodVisitor;

public class MethodAttrVisitor extends MethodVisitor{
    private final List<Attribute> attrs;

    public MethodAttrVisitor(int api, MethodVisitor methodVisitor, List<Attribute> attrs) {
        super(api, methodVisitor);
        this.attrs = attrs;
    }

    @Override
    public void visitEnd() {
        int len = attrs == null ? 0 : attrs.size();
        for (int i = 0; i < len; i++) {
            Attribute attr = attrs.get(i);
            visitAttribute(attr);
        }
        super.visitEnd();
    }
    
}
