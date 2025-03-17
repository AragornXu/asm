package com.asm.archive;

import java.io.FileOutputStream;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TestASMGenClass 
{
    public static void main( String[] args )
    {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, 
                Opcodes.ACC_PUBLIC, 
                "GenClass", 
                null, 
                "java/lang/Object", null);

        MethodVisitor mv = cw.visitMethod(
            Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, 
            "main", 
            "([Ljava/lang/String;)V", 
            null, null);

        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello World111!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();

        cw.visitEnd();

        byte[] code = cw.toByteArray();
        try (FileOutputStream f = new FileOutputStream("genClasses/GenClass2.class")) {
            f.write(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}