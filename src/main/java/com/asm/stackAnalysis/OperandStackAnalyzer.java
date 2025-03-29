package com.asm.stackAnalysis;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import com.asm.generics.attributes.GenericsAttribute;
import com.asm.util.OffsetClassVisitor;

public class OperandStackAnalyzer {
    public void run(String path) throws Exception {
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/TestBC1.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/LinkedListExample.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/MatrixOperations.class";
        // String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/FooStack.class";
        ClassReader reader = new ClassReader(Files.newInputStream(Paths.get(path)));
        // ClassVisitor bcOffsetVisitor = new ClassVisitor(){
        //     @Override
        //     public void 
        // }
        Map<String, Map<Integer, Integer>> offsetMap = new HashMap<>();
        //second visitor
        OSAClassVisitor stackVisitor = new OSAClassVisitor(Opcodes.ASM9, offsetMap);
        //first visitor
        OffsetClassVisitor offsetVisitor = new OffsetClassVisitor(Opcodes.ASM9, stackVisitor, offsetMap);
        // OSAMethodVisitor methodVisitor = new OSAMethodVisitor(Opcodes.ASM9);
        reader.accept(offsetVisitor, new Attribute[] {
            new GenericsAttribute()
        }, 0);
    }

    public static void main(String[] args) throws Exception {
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericMethod1$_Modified.class");
        // osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/GenericMethod1.class");

    }
}