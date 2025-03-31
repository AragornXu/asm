package com.asm.generics.attributes;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.MethodVisitor;

public class MethodAttrVisitor extends MethodVisitor{
    private final Attribute attr;

    public MethodAttrVisitor(int api, MethodVisitor methodVisitor, Attribute attr) {
        super(api, methodVisitor);
        this.attr = attr;
    }

    @Override
    public void visitEnd() {
        // Add the attribute to the method
        visitAttribute(attr);
        super.visitEnd();
    }
    
}
