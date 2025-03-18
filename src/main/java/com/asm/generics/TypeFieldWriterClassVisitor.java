package com.asm.generics;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class TypeFieldWriterClassVisitor extends ClassVisitor {
    private final List<String> typeParams;
    public TypeFieldWriterClassVisitor(int api, ClassVisitor cv, List<String> typeParams) {
        super(api, cv);
        this.typeParams = typeParams;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        for (String typeParam : typeParams) {
            String fieldName = typeParam + "TypeParamField";
            String fieldDesc = "I";
            // String fieldSignature = "I";
            FieldVisitor fv = super.visitField(
                Opcodes.ACC_PRIVATE, fieldName, fieldDesc, null, null);
            if (fv != null) {
                fv.visitEnd();
            }
        }
    }
}
