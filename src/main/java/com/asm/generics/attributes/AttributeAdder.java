package com.asm.generics.attributes;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class AttributeAdder {
    public static void main(String[] args) throws Exception {
        String file = "/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericMethod1$";
        byte[] bytes = addAttribute1(file + ".class");
        boolean areEqual = Arrays.equals(bytes, Files.readAllBytes(Paths.get(file + ".class")));
        System.out.println("Equal? " + areEqual);
        try (FileOutputStream f = new FileOutputStream(
            file + "_Modified.class")) {
            f.write(bytes);
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    public static byte[] addAttribute1(String path) throws Exception {
        ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public void visitEnd(){
                GenericsAttribute attr = new GenericsAttribute();
                attr.addToAttribute("t1", 12, "int,string");
                attr.addToAttribute("t1", 21, "double,char");
                attr.addToAttribute("t1", 38 , "int");
                System.out.println("Attribute created");
                visitAttribute(attr);
                super.visitEnd();
            }
            // @Override
            // public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            //     MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
            //     System.out.println("Method: " + name);
            //     if (name.equals("t1")) {
            //         // GenericsAttribute attr = new GenericsAttribute();
            //         // attr.addToAttribute(12, "int,string");
            //         // attr.addToAttribute(21, "double,char");
            //         // System.out.println("Attribute created");
            //         // mv.visitAttribute(attr);
                    
            //     }
            //     return mv;
            // }
        };
        cr.accept(cv, new Attribute[] { new GenericsAttribute() }, 0);
        return cw.toByteArray();
    }
}
