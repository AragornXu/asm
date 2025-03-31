package com.asm.generics.attributes;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Attribute;

public class ClassAttrVisitor extends ClassVisitor{
    private final Attribute attr;

    public ClassAttrVisitor(int api, ClassVisitor classVisitor, Attribute attr) {
        super(api, classVisitor);
        this.attr = attr;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new MethodAttrVisitor(api, mv, attr);
    }
    
}
