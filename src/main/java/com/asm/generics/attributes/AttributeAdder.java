package com.asm.generics.attributes;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Attribute;

public class AttributeAdder {
    private static String file;
    public static void main(String[] args) throws Exception {
        file = "/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericClass1$";
        Map<String, Attribute> attrs = genMethodAttr1(); //change here to the specific attribute adder function
        
        // file = "/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericMethod1$";
        // Map<String, Attribute> attrs = genMethodAttr2(); //change here to the specific attribute adder function
        
        byte[] originalFile = Files.readAllBytes(Paths.get(file + ".class"));
        AddMethodAttribute attrHelper = new AddMethodAttribute();
        //AddClassAttribute attrHelper = new AddClassAttribute();
        //byte[] modifiedFile = attrHelper.addClassAttribute(attr, file + ".class");
        byte[] modifiedFile = attrHelper.addMethodAttribute(attrs, file + ".class"); 
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

    //for testGenericClass1.class
    public static Map<String, Attribute> genMethodAttr1(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genMethodAttr1, for testGenericClass1.class");
        Map<String, Attribute> attrs = new HashMap<>();

        GenericsMethodAttribute attr1 = new GenericsMethodAttribute();
        attr1.addToAttribute(5, "int");
        attr1.addToAttribute(12, "double");
        attr1.addToAttribute(16 , "int");
        attr1.addToAttribute(21 , "double");
        attr1.addToAttribute(30 , "double,int");
        attr1.addToAttribute(37 , "string,char");
        attr1.addToAttribute(41 , "int");
        attr1.addToAttribute(45 , "double");
        attr1.addToAttribute(51 , "int");
        attrs.put("t1", attr1);

        return attrs;
    }

    //for testGenericMethod1.class
    public static Map<String, Attribute> genMethodAttr2(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genMethodAttr2, for testGenericMethod1.class");
        Map<String, Attribute> attrs = new HashMap<>();

        GenericsMethodAttribute attr1 = new GenericsMethodAttribute();
        attr1.addToAttribute(10, "int,string");
        attr1.addToAttribute(18, "double,char");
        attrs.put("t1", attr1);

        GenericsMethodAttribute attr2 = new GenericsMethodAttribute();
        attr2.addToAttribute(9, "int");
        attr2.addToAttribute(21, "long");
        attrs.put("t2", attr2);

        return attrs;
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
