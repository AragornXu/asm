package com.asm.stackAnalysis;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import com.asm.generics.attributes.GenericsAttribute;
import com.asm.generics.attributes.GenericsMethodAttribute;
import com.asm.util.OffsetClassVisitor;

public class OperandStackAnalyzer {
    public void run(String path) throws Exception {
        System.out.println("Running OperandStackAnalyzer on " + path);
        ClassReader reader = new ClassReader(Files.newInputStream(Paths.get(path)));
        Map<String, Map<Integer, Integer>> offsetMap = new HashMap<>();
        //second visitor
        OSAClassVisitor stackVisitor = new OSAClassVisitor(Opcodes.ASM9, offsetMap);
        //first visitor
        OffsetClassVisitor offsetVisitor = new OffsetClassVisitor(Opcodes.ASM9, stackVisitor, offsetMap);
        // OSAMethodVisitor methodVisitor = new OSAMethodVisitor(Opcodes.ASM9);
        reader.accept(offsetVisitor, new Attribute[] {
            new GenericsAttribute(), new GenericsMethodAttribute()
        }, 0);
    }

    public static void main(String[] args) throws Exception {
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("genClasses/attributes/testGenericMethod1$_Modified.class");
    }
}