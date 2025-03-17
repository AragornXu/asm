package com.asm.util;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

public class BytecodePrinter {
    public static void run(String path) throws Exception{
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/FooStack.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/GenClass2.class";
        PrintWriter writer = new PrintWriter(System.out);
        ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
        TraceClassVisitor tcv = new TraceClassVisitor(writer);
        cr.accept(tcv, 0);
    }
}