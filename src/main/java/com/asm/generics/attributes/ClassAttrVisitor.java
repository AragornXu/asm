package com.asm.generics.attributes;

import java.util.List;
import java.util.Map;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassAttrVisitor extends ClassVisitor{
    private final Map<String, List<Attribute>> methodAttributes;
    private final Map<String, List<Attribute>> fieldAttributes;

    public ClassAttrVisitor(int api, ClassVisitor classVisitor, Map<String, List<Attribute>> methodAttributes, Map<String, List<Attribute>> fieldAttributes) {
        super(api, classVisitor);
        this.methodAttributes = methodAttributes;
        this.fieldAttributes = fieldAttributes;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        FieldVisitor fv = super.visitField(access, name, descriptor, signature, value);
        List<Attribute> attrs = fieldAttributes.getOrDefault(name, null);
        if (attrs != null) {
            for (Attribute attr : attrs) {
                fv.visitAttribute(attr);
            }
        }
        return fv;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        List<Attribute> attrs = methodAttributes.getOrDefault(name, null);
        return new MethodAttrVisitor(api, mv, attrs);
    }
    
}
