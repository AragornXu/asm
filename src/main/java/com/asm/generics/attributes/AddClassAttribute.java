package com.asm.generics.attributes;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class AddClassAttribute {
    public byte[] addClassAttribute(Attribute attr, String path) throws Exception {
        ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public void visitEnd(){
                visitAttribute(attr);
                super.visitEnd();
            }
        };
        cr.accept(cv, new Attribute[] { new GenericsAttribute() }, 0);
        return cw.toByteArray();
    }
}
