package com.asm.generics.attributes;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.objectweb.asm.Attribute;

public class AttributeAdder {
    public static void main(String[] args) throws Exception {
        String file = "/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericClass1$";
        byte[] originalFile = Files.readAllBytes(Paths.get(file + ".class"));

        AddMethodAttribute attrHelper = new AddMethodAttribute();
        //AddClassAttribute attrHelper = new AddClassAttribute();
        
        Attribute attr = genAttribute2(); //change here to the specific attribute adder function
        
        //byte[] modifiedFile = attrHelper.addClassAttribute(attr, file + ".class");
        byte[] modifiedFile = attrHelper.addMethodAttribute(attr, file + ".class");
        
        boolean areEqual = Arrays.equals(modifiedFile, originalFile); //should not be equal
        System.out.println("Equal? " + areEqual);

        try (FileOutputStream f = new FileOutputStream(
            file + "_Modified.class")) {
            f.write(modifiedFile);
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    //for testGenericMethod1$.class
    public static GenericsAttribute genAttribute1() {
        GenericsAttribute attr = new GenericsAttribute();
        attr.addToAttribute("t1", 12, "int,string");
        attr.addToAttribute("t1", 21, "double,char");
        attr.addToAttribute("t1", 38 , "int");
        return attr;
    }

    //for testGenericClass1$.class
    public static GenericsAttribute genAttribute2(){
        GenericsAttribute attr = new GenericsAttribute();
        attr.addToAttribute("t1", 4, "int");
        attr.addToAttribute("t1", 10, "double");
        attr.addToAttribute("t1", 19, "double,int");
        attr.addToAttribute("t1", 27, "string,char");
        attr.addToAttribute("t1", 32, "int");
        attr.addToAttribute("t1", 37, "double");
        attr.addToAttribute("t1", 44, "int");
        return attr;
    }

    // public static byte[] addAttribute1(String path) throws Exception {
    //     ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
    //     ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
    //     ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
    //         @Override
    //         public void visitEnd(){
    //             GenericsAttribute attr = new GenericsAttribute();
    //             attr.addToAttribute("t1", 12, "int,string");
    //             attr.addToAttribute("t1", 21, "double,char");
    //             attr.addToAttribute("t1", 38 , "int");
    //             System.out.println("Attribute created");
    //             visitAttribute(attr);
    //             super.visitEnd();
    //         }
    //         // failed attempt:
    //         // @Override
    //         // public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
    //         //     MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
    //         //     System.out.println("Method: " + name);
    //         //     if (name.equals("t1")) {
    //         //         // GenericsAttribute attr = new GenericsAttribute();
    //         //         // attr.addToAttribute(12, "int,string");
    //         //         // attr.addToAttribute(21, "double,char");
    //         //         // System.out.println("Attribute created");
    //         //         // mv.visitAttribute(attr);
                    
    //         //     }
    //         //     return mv;
    //         // }
    //     };
    //     cr.accept(cv, new Attribute[] { new GenericsAttribute() }, 0);
    //     return cw.toByteArray();
    // }
}
