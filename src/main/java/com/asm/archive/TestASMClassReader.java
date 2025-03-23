package com.asm.archive;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class TestASMClassReader {
    public static void main(String[] args) throws Exception {
        // Load the class file from the classpath
        //InputStream in = ASMExample.class.getResourceAsStream("/com/asm/ClassPrinter.class");
        byte[] in = Files.readAllBytes(Paths.get("/home/j523xu/Desktop/asm/testing/genClasses/TestBC1.class"));
        if (in == null) {
            throw new RuntimeException("Class file not found");
        }
        
        // Create the ClassReader
        ClassReader classReader = new ClassReader(in);
        
        // Parse the class file with your visitor
        classReader.accept(new MyClassVisitor(), 0);
    }
}

class MyClassVisitor extends ClassVisitor {
    public MyClassVisitor() {
        super(Opcodes.ASM9);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("Class name: " + name);
        System.out.println("Super class: " + superName);
        if (interfaces != null && interfaces.length > 0) {
            System.out.println("Interfaces:");
            for (String iface : interfaces) {
                System.out.println("  " + iface);
            }
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }
}