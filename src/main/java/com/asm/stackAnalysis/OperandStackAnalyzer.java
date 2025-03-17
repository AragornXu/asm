package com.asm.stackAnalysis;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class OperandStackAnalyzer {
    public static void main(String[] args) throws Exception {
        String path = "/home/j523xu/Desktop/asm/testing/genClasses/TestBC1.class";
        ClassReader reader = new ClassReader(Files.newInputStream(Paths.get(path)));
        OSAClassVisitor visitor = new OSAClassVisitor(Opcodes.ASM9);
        // OSAMethodVisitor methodVisitor = new OSAMethodVisitor(Opcodes.ASM9);
        reader.accept(visitor, 0);
    }



}
