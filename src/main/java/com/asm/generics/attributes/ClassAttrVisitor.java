package com.asm.generics.attributes;

import java.util.List;
import java.util.Map;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassAttrVisitor extends ClassVisitor{
    private final Map<String, List<Attribute>> attributes;

    public ClassAttrVisitor(int api, ClassVisitor classVisitor, Map<String, List<Attribute>> attributes) {
        super(api, classVisitor);
        this.attributes = attributes;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        List<Attribute> attrs = attributes.getOrDefault(name, null);
        return new MethodAttrVisitor(api, mv, attrs);
    }
    
}
