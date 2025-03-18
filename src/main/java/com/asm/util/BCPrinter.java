package com.asm.util;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

public class BCPrinter {
    public static void main(String[] args) throws Exception{
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/FooStack.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/Foo.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/FooNested$Inner.class";
        String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/Foo_Modified.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/GenClass2.class";
        PrintWriter writer = new PrintWriter(System.out);
        ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
        TraceClassVisitor tcv = new TraceClassVisitor(writer);
        cr.accept(tcv, 0);
    }

    public void run(String path) throws Exception{
        PrintWriter writer = new PrintWriter(System.out);
        ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
        TraceClassVisitor tcv = new TraceClassVisitor(writer);
        cr.accept(tcv, 0);
    }
}