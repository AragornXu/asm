package com.asm.stackAnalysis;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import com.asm.generics.attributes.GenericsAttribute;

public class OperandStackAnalyzer {
    public void run(String path) throws Exception {
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/TestBC1.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/LinkedListExample.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/MatrixOperations.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/FooStack.class";
        ClassReader reader = new ClassReader(Files.newInputStream(Paths.get(path)));
        OSAClassVisitor visitor = new OSAClassVisitor(Opcodes.ASM9);
        // OSAMethodVisitor methodVisitor = new OSAMethodVisitor(Opcodes.ASM9);
        reader.accept(visitor, new Attribute[] {
            new GenericsAttribute()
        }, 0);
    }

    public static void main(String[] args) throws Exception {
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericMethod1$_Modified.class");
        // osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/GenericMethod1.class");

    }
}