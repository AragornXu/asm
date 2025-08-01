package com.asm.generics.attributes;

import java.util.List;
import java.util.Map;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassAttrVisitor extends ClassVisitor{
    private final List<Attribute> classAttributes;
    private final Map<String, List<Attribute>> methodAttributes;
    private final Map<String, Attribute> fieldAttributes;

    public ClassAttrVisitor(int api, ClassVisitor classVisitor, 
                            List<Attribute> classAttributes,
                            Map<String, List<Attribute>> methodAttributes,
                            Map<String, Attribute> fieldAttributes) {
        super(api, classVisitor);
        this.classAttributes = classAttributes;
        this.methodAttributes = methodAttributes;
        this.fieldAttributes = fieldAttributes;
    }
    
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        FieldVisitor fv = super.visitField(access, name, descriptor, signature, value);
        Attribute attr = fieldAttributes.getOrDefault(name, null);
        if (attr != null) {
            fv.visitAttribute(attr);
        }
        return fv;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        List<Attribute> attrs = methodAttributes.getOrDefault(name, null);
        return new MethodAttrVisitor(api, mv, attrs);
    }

    @Override
    public void visitEnd() {
        if (classAttributes != null) {
            for (Attribute attr : classAttributes) {
                this.visitAttribute(attr);
            }
        }
        super.visitEnd();
    }
    
}
