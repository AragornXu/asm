package com.asm.generics.attributes;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Attribute;

import com.asm.generics.attributes.reified.InstructionTypeArguments;
import com.asm.generics.attributes.reified.InvokeReturnType;
import com.asm.generics.attributes.reified.MethodParameterType;
import com.asm.generics.attributes.reified.MethodReturnType;
import com.asm.generics.attributes.reified.MethodTypeParameterCount;
import com.asm.generics.attributes.reified.typehints.TypeA;
import com.asm.generics.attributes.reified.typehints.TypeAHint;
import com.asm.generics.attributes.reified.typehints.TypeB;
import com.asm.generics.attributes.reified.typehints.TypeBHint;

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

        // file = "genClasses/scala/Checksum$";
        // Map<String, Attribute> attrs = genMethodAttr5(); //change here to the specific attribute adder function

        // file = "genClasses/reified/ArrayCopy1$";
        // Map<String, List<Attribute>> attrs = genArrayCopyAttr(); //change here to the specific attribute adder function

        // file = "genClasses/reified/GenericMethod1";
        // Map<String, List<Attribute>> attrs = genGenericMethod1(); //change here to the specific attribute adder function

        file = "genClasses/reified/testGenericMethod1$";
        Map<String, List<Attribute>> attrs = genTestGenericMethod1(); //change here to the specific attribute adder function


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

    public static Map<String, List<Attribute>> genGenericMethod1(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genGenericMethod1, for GenericMethod1.class");
        Map<String, List<Attribute>> res = new HashMap<>();

        //for method identity
        //def identity[T](x: T): T = x
        List<Attribute> identityAttrs = new ArrayList<>();
        //there is one type parameter T
        MethodTypeParameterCount methodTypeParameterCountIdentity = 
            new MethodTypeParameterCount(1);
        //there is one parameter (value) of type T
        List<TypeB> typeBListIdentity = new ArrayList<>();
        typeBListIdentity.add(
            new TypeB(TypeB.M_KIND, 0));
        MethodParameterType methodParameterTypeIdentity = 
            new MethodParameterType(
                typeBListIdentity.size(),
                typeBListIdentity
            );
        //identity returns T
        MethodReturnType methodReturnTypeIdentity = 
            new MethodReturnType(
                new TypeB(TypeB.M_KIND, 0)
            );
        identityAttrs.add(methodTypeParameterCountIdentity);
        identityAttrs.add(methodParameterTypeIdentity);
        identityAttrs.add(methodReturnTypeIdentity);
        res.put("identity", identityAttrs);

        //for method first
        //def first[A, B](x: A, y: B): A = x
        List<Attribute> firstAttrs = new ArrayList<>();
        //there are two type parameters A and B
        MethodTypeParameterCount methodTypeParameterCountFirst = 
            new MethodTypeParameterCount(2);
        //there are two parameters (a: A, b: B) of type param A, B
        List<TypeB> typeBListFirst = new ArrayList<>();
        typeBListFirst.add(
            new TypeB(TypeB.M_KIND, 0));
        typeBListFirst.add(
            new TypeB(TypeB.M_KIND, 1));
        MethodParameterType methodParameterTypeFirst =
            new MethodParameterType(
                typeBListFirst.size(),
                typeBListFirst
            );
        //first returns type para A
        MethodReturnType methodReturnTypeFirst = 
            new MethodReturnType(
                new TypeB(TypeB.M_KIND, 0)
        );
        firstAttrs.add(methodTypeParameterCountFirst);
        firstAttrs.add(methodParameterTypeFirst);
        firstAttrs.add(methodReturnTypeFirst);
        res.put("first", firstAttrs);
        
        return res;
    }

    @SuppressWarnings("Convert2Diamond")
    public static Map<String, List<Attribute>> genTestGenericMethod1(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genTestGenericMethod1, for testGenericMethod1$.class");
        Map<String, List<Attribute>> res = new HashMap<>();

        //for method t1
        //def t1(): Unit
        List<Attribute> t1Attrs = new ArrayList<>();
        List<TypeAHint> typeAHintsT1 = new ArrayList<>();
        //14: invokevirtual #39 // Method bcGen/GenericMethod1.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        typeAHintsT1.add(new TypeAHint(
                        14, 
                        Arrays.asList(TypeA.TYPEA_INT))
        );
        InstructionTypeArguments instructionTypeArgumentsT1 = 
            new InstructionTypeArguments(typeAHintsT1);
        typeAHintsT1 = new ArrayList<TypeAHint>();
        typeAHintsT1.add(
            new TypeAHint(14, 
                Arrays.asList(TypeA.TYPEA_INT))
        );
        InvokeReturnType invokeReturnTypeT1 =
            new InvokeReturnType(
                typeAHintsT1.size(), 
                typeAHintsT1
            );  

        t1Attrs.add(instructionTypeArgumentsT1);
        t1Attrs.add(invokeReturnTypeT1);
        res.put("t1", t1Attrs);

        //for method t2
        //def t2(): Unit
        List<Attribute> t2Attrs = new ArrayList<>();
        List<TypeAHint> typeAHintsT2 = new ArrayList<>();
        //20: invokevirtual #58 // Method bcGen/GenericMethod1.first:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        typeAHintsT2.add(new TypeAHint(
                        20, 
                        Arrays.asList(TypeA.TYPEA_DOUBLE, TypeA.TYPEA_INT))
        );
        InstructionTypeArguments instructionTypeArgumentsT2 = 
            new InstructionTypeArguments(typeAHintsT2);
        typeAHintsT2 = new ArrayList<TypeAHint>();
        typeAHintsT2.add(
            new TypeAHint(20, 
                Arrays.asList(TypeA.TYPEA_DOUBLE))
        );
        InvokeReturnType invokeReturnTypeT2 =
            new InvokeReturnType(
                typeAHintsT2.size(), 
                typeAHintsT2
            );
        t2Attrs.add(instructionTypeArgumentsT2);
        t2Attrs.add(invokeReturnTypeT2);
        res.put("t2", t2Attrs);

        return res;
    }
    
    @SuppressWarnings("Convert2Diamond")
    public static Map<String, List<Attribute>> genArrayCopyAttr(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genArrayCopyAttr, for ArrayCopy1$.class");
        Map<String, List<Attribute>> res = new HashMap<>();

        List<Attribute> attrs = new ArrayList<>();
        //for method copy
        //def copy[T](src: Array[T], dest: Array[T]): Unit
        //one type parameter T 
        MethodTypeParameterCount methodTypeParameterCount = 
            new MethodTypeParameterCount(1);  
        attrs.add(methodTypeParameterCount);
        //two parameters (src: Array[T], dest: Array[T]) of type Array[T]
        List<TypeB> typeBList = new ArrayList<>();
        typeBList.add(
            new TypeB(TypeB.ARR_M_KIND, 0));
        typeBList.add(
            new TypeB(TypeB.ARR_M_KIND, 0));
        MethodParameterType methodParameterType = 
            new MethodParameterType(
                typeBList.size(),
                typeBList
            );
        
        List<TypeAHint> typeAHints = new ArrayList<>();
        //23: invokevirtual #43 // Method scala/runtime/ScalaRunTime$.array_apply:(Ljava/lang/Object;I)Ljava/lang/Object;
        typeAHints.add(
            new TypeAHint(23, 
                Arrays.asList(
                    new TypeA(TypeA.M_KIND, 0)
                )));
        //26: invokevirtual #47 // Method scala/runtime/ScalaRunTime$.array_update:(Ljava/lang/Object;ILjava/lang/Object;)V
        typeAHints.add( //TODO
            new TypeAHint(26, 
                Arrays.asList(
                    new TypeA(TypeA.M_KIND, 0)
                )));
        InstructionTypeArguments instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        typeAHints = new ArrayList<TypeAHint>();
        typeAHints.add(
            new TypeAHint(23, 
                Arrays.asList(
                    new TypeA(TypeA.M_KIND, 0)
                )));
        InvokeReturnType invokeReturnType = 
            new InvokeReturnType(
                typeAHints.size(), 
                typeAHints
            );

        attrs.add(methodParameterType);
        attrs.add(instructionTypeArguments);
        attrs.add(invokeReturnType);
        res.put("copy", attrs);

        return res;
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
