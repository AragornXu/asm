package com.asm.util;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

public class BCPrinter {
    public static void main(String[] args) throws Exception{
        String path = "/home/j523xu/Downloads/MixedNesting$Inner$1.class";
        PrintWriter writer = new PrintWriter(System.out);
        ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
        ClassVisitor tcv = new ClassVisitor(Opcodes.ASM6){
            @Override
            public void visitAttribute(final Attribute attribute) {
                System.out.println("Attribute:" + attribute);
                super.visitAttribute(attribute);
            }
        };
        cr.accept(tcv, 0);
    }

    public void run(String path) throws Exception{
        PrintWriter writer = new PrintWriter(System.out);
        ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
        TraceClassVisitor tcv = new TraceClassVisitor(writer);
        cr.accept(tcv, 0);
    }
}