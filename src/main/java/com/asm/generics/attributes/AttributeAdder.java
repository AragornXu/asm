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
        // file = "genClasses/attributes/testGenericClass1$";
        // Map<String, Attribute> attrs = genMethodAttr1(); //change here to the specific attribute adder function
        
        // file = "genClasses/attributes/testGenericMethod1$";
        // Map<String, Attribute> attrs = genMethodAttr2(); //change here to the specific attribute adder function

        // file = "genClasses/attributes/GenericMethod1";
        // Map<String, Attribute> attrs = genMethodAttr3(); //change here to the specific attribute adder function
        
        // file = "genClasses/scala/ArrayCopy$";
        // Map<String, Attribute> attrs = genMethodAttr4(); //change here to the specific attribute adder function

        file = "genClasses/scala/Checksum$";
        Map<String, Attribute> attrs = genMethodAttr5(); //change here to the specific attribute adder function

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
        attr1.addToAttribute(5, "I");
        attr1.addToAttribute(12, "D");
        attr1.addToAttribute(16 , "I");
        attr1.addToAttribute(21 , "D");
        attr1.addToAttribute(30 , "D|I");
        attr1.addToAttribute(37 , "L|C");
        attr1.addToAttribute(41 , "I");
        attr1.addToAttribute(45 , "D");
        attr1.addToAttribute(51 , "I");
        attrs.put("t1", attr1);

        return attrs;
    }

    //for testGenericMethod1.class
    public static Map<String, Attribute> genMethodAttr2(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genMethodAttr2, for testGenericMethod1.class");
        Map<String, Attribute> attrs = new HashMap<>();

        GenericsMethodAttribute attr1 = new GenericsMethodAttribute();
        attr1.addToAttribute(16, "I|L");
        attr1.addToAttribute(32, "D|C");
        attrs.put("t1", attr1);

        GenericsMethodAttribute attr2 = new GenericsMethodAttribute();
        attr2.addToAttribute(14, "I");
        attr2.addToAttribute(38, "J");
        attrs.put("t2", attr2);

        return attrs;
    }

    //for GenericMethod1.class
    public static Map<String, Attribute> genMethodAttr3(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genMethodAttr3, for GenericMethod1.class");
        Map<String, Attribute> attrs = new HashMap<>();

        GenericsMethodAttribute attr1 = new GenericsMethodAttribute();
        attr1.addToAttribute(0, "M0");
        attrs.put("identity", attr1);

        GenericsMethodAttribute attr2 = new GenericsMethodAttribute();
        attr2.addToAttribute(17, "M0");
        attr2.addToAttribute(26, "M0");
        attrs.put("printTypeInfo", attr2);

        GenericsMethodAttribute attr3 = new GenericsMethodAttribute();
        attr3.addToAttribute(15, "M0");
        attr3.addToAttribute(19, "M1");
        attrs.put("makeListOfTwo", attr3);

        return attrs;
    }

    //for ArrayCopy$.class
    public static Map<String, Attribute> genMethodAttr4(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genMethodAttr4, for ArrayCopy$.class");
        Map<String, Attribute> attrs = new HashMap<>();

        GenericsMethodAttribute attr1 = new GenericsMethodAttribute();
        attr1.addToAttribute(7, "M0");
        attr1.addToAttribute(23, "M0");
        attr1.addToAttribute(26, "M0");
        attrs.put("copy", attr1);

        return attrs;
    }

    //for Checksum$.class
    public static Map<String, Attribute> genMethodAttr5(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genMethodAttr4, for Checksum$.class");
        Map<String, Attribute> attrs = new HashMap<>();

        GenericsMethodAttribute attr1 = new GenericsMethodAttribute();
        attr1.addToAttribute(10, "M0");
        attr1.addToAttribute(22, "M0");
        attr1.addToAttribute(25, "M0");
        attrs.put("checksum", attr1);

        return attrs;
    }
    //for testGenericMethod1$.class
    public static GenericsAttribute genAttribute1() {
        GenericsAttribute attr = new GenericsAttribute();
        attr.addToAttribute("t1", 12, "I|L");
        attr.addToAttribute("t1", 21, "D|C");
        attr.addToAttribute("t1", 38 , "I");
        return attr;
    }

    //for testGenericClass1$.class
    public static GenericsAttribute genAttribute2(){
        GenericsAttribute attr = new GenericsAttribute();
        attr.addToAttribute("t1", 4, "I");
        attr.addToAttribute("t1", 10, "D");
        attr.addToAttribute("t1", 19, "D|I");
        attr.addToAttribute("t1", 27, "L|C");
        attr.addToAttribute("t1", 32, "I");
        attr.addToAttribute("t1", 37, "D");
        attr.addToAttribute("t1", 44, "I");
        return attr;
    }
}
