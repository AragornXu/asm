package com.asm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.CheckClassAdapter;

public class BCVerifier {
    public static void main(String[] args) {
        String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericMethod1$_Modified.class";
        verifyViaClassLoader(path);
        verifyViaClassVerifier(path);

    }

    public static void verifyViaClassLoader(String path) {
        try {
            Class<?> loadClass = new MyClassLoader().loadFromPath(path);
            System.out.println("Loaded class: " + loadClass.getName());
        } catch (Exception e) {
            System.out.println("nope");
        }
    }

    public static void verifyViaClassVerifier(String path) {
        File classFile = new File(path);
        if (!classFile.exists() || !classFile.isFile()) {
            System.out.println("File not found: " + path);
            return;
        }
        try (FileInputStream fis = new FileInputStream(classFile)) {
            ClassReader classReader = new ClassReader(fis);

            // Use ASM's CheckClassAdapter to validate the bytecode
            CheckClassAdapter.verify(classReader, false, new PrintWriter(System.out));
            System.out.println("Class is valid");
        } catch (Exception e) {
            System.out.println("Class is invalid: " + e.getMessage());
        }
    }

    static class MyClassLoader extends ClassLoader {
        public Class<?> loadFromPath(String path) throws Exception {
            File classFile = new File(path);
            if (!classFile.exists() || !classFile.isFile()) {
                throw new IOException("File not found: " + path);
            }

            byte[] classBytes;
            try (FileInputStream fis = new FileInputStream(classFile)) {
                classBytes = fis.readAllBytes();
            }

            return defineClass(null, classBytes, 0, classBytes.length);
        }
    }
}
