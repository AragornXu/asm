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

import com.asm.generics.attributes.reified.FieldType;
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
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();
        Map<String, List<Attribute>> fieldAttributes = new HashMap<>();
        Object[] res = new Object[2];

        // file = "genClasses/reified/ArrayCopy1$";
        // Map<String, List<Attribute>> attrs = genArrayCopyAttr(); //change here to the specific attribute adder function

        // file = "genClasses/reified/GenericMethod1";
        // Map<String, List<Attribute>> attrs = genGenericMethod1(); //change here to the specific attribute adder function

        // file = "genClasses/reified/testGenericMethod1$";
        // methodAttributes = genTestGenericMethod1(); //change here to the specific attribute adder function

        // file = "genClasses/reified/GenericClass1";
        // res = genGenericClass1(); //change here to the specific attribute adder function

        // file = "genClasses/reified/GenericClass1";
        // res = genGenericClass1(); //change here to the specific attribute adder function

        file = "genClasses/reified/testGenericClass1$";
        res = genTestGenericClass1(); //change here to the specific attribute adder function


        fieldAttributes = (Map<String, List<Attribute>>) res[0];
        methodAttributes = (Map<String, List<Attribute>>) res[1];

        byte[] originalFile = Files.readAllBytes(Paths.get(file + ".class"));
        AddFieldMethodAttribute attrHelper = new AddFieldMethodAttribute();
        byte[] modifiedFile = attrHelper.addMethodAttribute(methodAttributes, fieldAttributes, file + ".class"); 
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

    public static Object[] genGenericMethod1(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genGenericMethod1, for GenericMethod1.class");
        Object[] res = new Object[2];
        Map<String, List<Attribute>> fieldAttributes = new HashMap<>();
        res[0] = fieldAttributes;
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();

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
        methodAttributes.put("identity", identityAttrs);

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
        methodAttributes.put("first", firstAttrs);
        res[1] = methodAttributes;
        return res;
    }

    @SuppressWarnings("Convert2Diamond")
    public static Object[] genTestGenericMethod1(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genTestGenericMethod1, for testGenericMethod1$.class");
        Object[] res = new Object[2];
        Map<String, List<Attribute>> fieldAttributes = new HashMap<>();
        res[0] = fieldAttributes;
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();

        //for method t1
        //def t1(): Unit
        List<Attribute> t1Attrs = new ArrayList<>();
        List<TypeAHint> typeAHints1 = new ArrayList<>();
        //14: invokevirtual #39 // Method bcGen/GenericMethod1.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        typeAHints1.add(new TypeAHint(
                        14, 
                        Arrays.asList(TypeA.TYPEA_INT))
        );
        InstructionTypeArguments instructionTypeArgumentsT1 = 
            new InstructionTypeArguments(typeAHints1);
        List<TypeAHint> typeAHints2 = new ArrayList<TypeAHint>();
        typeAHints2.add(
            new TypeAHint(14, 
                Arrays.asList(TypeA.TYPEA_INT))
        );
        InvokeReturnType invokeReturnTypeT1 =
            new InvokeReturnType(
                typeAHints2.size(), 
                typeAHints2
            );  

        t1Attrs.add(instructionTypeArgumentsT1);
        t1Attrs.add(invokeReturnTypeT1);
        methodAttributes.put("t1", t1Attrs);

        //for method t2
        //def t2(): Unit
        List<Attribute> t2Attrs = new ArrayList<>();
        List<TypeAHint> typeAHints3 = new ArrayList<>();
        //20: invokevirtual #58 // Method bcGen/GenericMethod1.first:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        typeAHints3.add(new TypeAHint(
                        20, 
                        Arrays.asList(TypeA.TYPEA_DOUBLE, TypeA.TYPEA_INT))
        );
        InstructionTypeArguments instructionTypeArgumentsT2 = 
            new InstructionTypeArguments(typeAHints3);
        List<TypeAHint> typeAHints4 = new ArrayList<TypeAHint>();
        typeAHints4.add(
            new TypeAHint(20, 
                Arrays.asList(TypeA.TYPEA_DOUBLE))
        );
        InvokeReturnType invokeReturnTypeT2 =
            new InvokeReturnType(
                typeAHints4.size(), 
                typeAHints4
            );
        t2Attrs.add(instructionTypeArgumentsT2);
        t2Attrs.add(invokeReturnTypeT2);
        methodAttributes.put("t2", t2Attrs);

        res[1] = methodAttributes;

        return res;
    }
    
    @SuppressWarnings("Convert2Diamond")
    public static Object[] genArrayCopyAttr(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genArrayCopyAttr, for ArrayCopy1$.class");
        Object[] res = new Object[2];
        Map<String, List<Attribute>> fieldAttributes = new HashMap<>();
        res[0] = fieldAttributes;       
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();

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
        typeAHints.add(
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
        methodAttributes.put("copy", attrs);
        res[1] = methodAttributes;
        return res;
    }

    public static Object[] genGenericClass1(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genGenericClass1, for GenericClass1.class");
        Object[] res = new Object[2];
        Map<String, List<Attribute>> fieldAttributes = new HashMap<>();

        //for field value:
        FieldType valueFieldType = new FieldType(
            new TypeB(TypeB.K_KIND, 0));
        List<Attribute> valueFieldAttributes = new ArrayList<>();
        valueFieldAttributes.add(valueFieldType);
        fieldAttributes.put("value", valueFieldAttributes);
        res[0] = fieldAttributes;

        //for methods:
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();

        //for constructor
        MethodParameterType methodParameterTypeConstructor = 
            new MethodParameterType(
                1, 
                Arrays.asList(new TypeB(TypeB.K_KIND, 0))
            );
        methodAttributes.put("<init>",
            Arrays.asList(methodParameterTypeConstructor)
        );
        
        
        //for method value()
        //getter for field value
        MethodReturnType methodReturnTypeValue = 
            new MethodReturnType(
                new TypeB(TypeB.K_KIND, 0)
            );
        methodAttributes.put("value",
            Arrays.asList(methodReturnTypeValue)
        );

        //for method value_$eq
        //setter for field value
        MethodParameterType methodParameterTypeValueEq = 
            new MethodParameterType(
                1, 
                Arrays.asList(new TypeB(TypeB.K_KIND, 0))
            );
        methodAttributes.put("value_$eq",
            Arrays.asList(methodParameterTypeValueEq)
        );

        //for method get
        //def get(): A
        MethodReturnType methodReturnTypeGet = 
            new MethodReturnType(
                new TypeB(TypeB.K_KIND, 0)
            );
        InstructionTypeArguments instructionTypeArgumentsGet = 
            new InstructionTypeArguments(
                Arrays.asList(new TypeAHint(1, 
                    Arrays.asList(
                        new TypeA(TypeA.K_KIND, 0)
                    )))
            );
        InvokeReturnType invokeReturnTypeGet =
            new InvokeReturnType(1,
                Arrays.asList(new TypeAHint(1, 
                    Arrays.asList(
                        new TypeA(TypeA.K_KIND, 0)
                    )))
            );
        methodAttributes.put("get",
            Arrays.asList(methodReturnTypeGet, 
                instructionTypeArgumentsGet, 
                invokeReturnTypeGet)
        );

        //for method set
        //def set(x: A): Unit
        MethodParameterType methodParameterTypeSet = 
            new MethodParameterType(
                1, 
                Arrays.asList(new TypeB(TypeB.K_KIND, 0))
            );
        InstructionTypeArguments instructionTypeArgumentsSet =
            new InstructionTypeArguments(
                Arrays.asList(new TypeAHint(2, 
                    Arrays.asList(
                        new TypeA(TypeA.K_KIND, 0)
                    )))
            );
        methodAttributes.put("set",
            Arrays.asList(methodParameterTypeSet, 
                instructionTypeArgumentsSet)
        );

        res[1] = methodAttributes;

        return res;
    }
    
    public static Object[] genTestGenericClass1(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genTestGenericClass1, for testGenericClass1$.class");
        Object[] res = new Object[2];
        Map<String, List<Attribute>> fieldAttributes = new HashMap<>();
        res[0] = fieldAttributes;
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();

        //for methods:
        //for method t1
        //def t1(): Unit
        List<Attribute> t1Attrs = new ArrayList<>();
        List<TypeAHint> typeAHints1 = new ArrayList<>();
        //9: invokespecial #37 // Method bcGen/GenericClass1."<init>":(Ljava/lang/Object;)V
        typeAHints1.add(new TypeAHint(
                        9, 
                        Arrays.asList(TypeA.TYPEA_INT))
        );
        //27: invokevirtual #47 // Method bcGen/GenericClass1.set:(Ljava/lang/Object;)V
        typeAHints1.add(new TypeAHint(
                        27, 
                        Arrays.asList(TypeA.TYPEA_INT))
        );
        InstructionTypeArguments instructionTypeArgumentsT1 = 
            new InstructionTypeArguments(typeAHints1);

        List<TypeAHint> typeAHints2 = new ArrayList<TypeAHint>();
        //14: invokevirtual #40 // Method bcGen/GenericClass1.get:()Ljava/lang/Object;
        typeAHints2.add(new TypeAHint(
                        14, 
                        Arrays.asList(TypeA.TYPEA_INT))
        );
        //31: invokevirtual #40 // Method bcGen/GenericClass1.get:()Ljava/lang/Object;
        typeAHints2.add(new TypeAHint(
                        31, 
                        Arrays.asList(TypeA.TYPEA_INT))
        );
        InvokeReturnType invokeReturnTypeT1 =
            new InvokeReturnType(
                typeAHints2.size(), 
                typeAHints2
            );  
            
        t1Attrs.add(instructionTypeArgumentsT1);
        t1Attrs.add(invokeReturnTypeT1);
        methodAttributes.put("t1", t1Attrs);
        res[1] = methodAttributes;
        return res;
    }
}