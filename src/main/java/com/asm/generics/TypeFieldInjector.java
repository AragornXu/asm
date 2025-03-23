package com.asm.generics;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class TypeFieldInjector {
    public static void main(String[] args) throws Exception {
        String file = "/home/j523xu/Desktop/asm/asmProj/genClasses/FooNested$Inner";
        byte[] bytes = writeClass(file + ".class");
        try (FileOutputStream f = new FileOutputStream(
            file + "_Modified.class")) {
            f.write(bytes);
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    public static void run(String file) throws Exception {
        byte[] bytes = writeClass(file + ".class");
        try (FileOutputStream f = new FileOutputStream(
            file + "_Modified.class")) {
            f.write(bytes);
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    public static byte[] writeClass(String path) throws Exception {
        ClassReader reader = new ClassReader(Files.newInputStream(Paths.get(path)));
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        TypeParamExtractorClassVisitor exVisitor = new TypeParamExtractorClassVisitor(Opcodes.ASM9);
        reader.accept(exVisitor, 0);
        List<String> typeParams = exVisitor.getTypeParams();
        TypeFieldWriterClassVisitor writerVisitor = new TypeFieldWriterClassVisitor(Opcodes.ASM9, writer, typeParams);
        reader.accept(writerVisitor, 0);
        return writer.toByteArray();
    }
}
