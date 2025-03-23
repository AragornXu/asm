package com.asm.generics.attributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class PrintAttribute {
    public static void main(String[] args) throws IOException{
        String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericMethod1$_Modified.class";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ClassReader cr = new ClassReader(bytes);
        cr.accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9) {
                        @Override
                        public void visitAttribute(Attribute attr) {
                            System.out.println("Attribute: ");
                            if (attr instanceof GenericsAttribute) {
                                GenericsAttribute ga = (GenericsAttribute) attr;
                                System.out.println("GenericsAttribute found");
                                System.out.println("bcIndex: " + ga.getBcIndex());
                                System.out.println("typeList: " + ga.getTypeList());
                            }
                        }
                    };
                }
            },
            new Attribute[]{new GenericsAttribute()}, 
            0);
    }
}
