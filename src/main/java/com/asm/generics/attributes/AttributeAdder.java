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

import com.asm.generics.attributes.reified.ClassTypeParameterCount;
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

@SuppressWarnings({"unused", "FieldMayBeFinal", "unchecked"})
public class AttributeAdder {
    private static String srcDirectory = "genClasses/scala/";
    private static String packageName = "bcGen";
    private static String dstDirectory = "genClasses/modified/";
    private static String file;
    private static String path;
    private static String dstPath;
    public static void main(String[] args) throws Exception {
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();
        Map<String, Attribute> fieldAttributes = new HashMap<>();
        List<Attribute> classAttributes = new ArrayList<>();
        Object[] res = {null, null, null};

        // file = "genClasses/reified/ArrayCopy1$";
        // Map<String, List<Attribute>> attrs = genArrayCopyAttr(); //change here to the specific attribute adder function

        // file = "GenericMethod";
        // path = srcDirectory + packageName + "/" + file;
        // res = genGenericMethod(); //change here to the specific attribute adder function

        // file = "testGenericMethod";
        // path = srcDirectory + packageName + "/" + file;
        // res = genTestGenericMethod(); //change here to the specific attribute adder function

        // file = "testGenericMethod$";
        // path = srcDirectory + packageName + "/" + file;
        // res = genTestGenericMethod$(); //change here to the specific attribute adder function

        file = "ArrayCopy$";
        path = srcDirectory + packageName + "/" + file;
        res = genArrayCopy$(); //change here to the specific attribute adder function

        // file = "testArray$";
        // path = srcDirectory + packageName + "/" + file;
        // res = genTestArray$(); //change here to the specific attribute adder function

        // file = "Classes/Pair";
        // path = srcDirectory + packageName + "/" + file;
        // res = genPair(); //change here to the specific attribute adder function

        // file = "Classes/testPair$";
        // path = srcDirectory + packageName + "/" + file;
        // res = genTestPair$(); //change here to the specific attribute adder function

        fieldAttributes = (Map<String, Attribute>) res[0];
        methodAttributes = (Map<String, List<Attribute>>) res[1];
        classAttributes = (List<Attribute>) res[2];

        byte[] originalFile = Files.readAllBytes(Paths.get(path + ".class"));
        AddAttributes attrHelper = new AddAttributes();
        byte[] modifiedFile = attrHelper.addAttributes(classAttributes, methodAttributes, fieldAttributes, path + ".class"); 
        boolean areEqual = Arrays.equals(modifiedFile, originalFile); //should not be equal
        System.out.println("Equal? " + areEqual);

        dstPath = dstDirectory + packageName + "/" + file;

        try (FileOutputStream f = new FileOutputStream(
            dstPath + ".class")) {
            f.write(modifiedFile);
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    public static Object[] genGenericMethod(){
        System.out.println("Changing file:" + path);
        System.out.println("Using genGenericMethod, for GenericMethod.class");
        Object[] res = new Object[3];
        Map<String, Attribute> fieldAttributes = new HashMap<>();
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

    public static Object[] genTestGenericMethod(){
        System.out.println("Changing file:" + path);
        System.out.println("Using genTestGenericMethod, for testGenericMethod.class");
        Object[] res = new Object[3];
        Map<String, Attribute> fieldAttributes = new HashMap<>();
        res[0] = fieldAttributes;
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();

        //for method test2
        //def test2[U, X, Y](value: U, fst: X, snd: Y): Unit
        List<Attribute> test2Attrs = new ArrayList<>();
        MethodTypeParameterCount methodTypeParameterCountTest2 = 
            new MethodTypeParameterCount(3);
        List<TypeB> typeBListTest2 = new ArrayList<>();
        typeBListTest2.add(
            new TypeB(TypeB.M_KIND, 0));
        typeBListTest2.add(
            new TypeB(TypeB.M_KIND, 1));
        typeBListTest2.add(
            new TypeB(TypeB.M_KIND, 2));
        MethodParameterType methodParameterTypeTest2 =
            new MethodParameterType(
                typeBListTest2.size(),
                typeBListTest2
            );
        List<TypeAHint> typeAHints1 = new ArrayList<>();
        //6: invokevirtual #20  // Method bcGen/testGenericMethod$.test2:(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints1.add(new TypeAHint(
                        6, 
                        Arrays.asList(new TypeA(TypeA.M_KIND, 0), 
                            new TypeA(TypeA.M_KIND, 1), 
                            new TypeA(TypeA.M_KIND, 2)))
        );
        InstructionTypeArguments instructionTypeArgumentsTest2 = 
            new InstructionTypeArguments(typeAHints1);
        test2Attrs.add(methodTypeParameterCountTest2);
        test2Attrs.add(methodParameterTypeTest2);
        test2Attrs.add(instructionTypeArgumentsTest2);
        methodAttributes.put("test2", test2Attrs);
        res[1] = methodAttributes;
        return res;
    }

    @SuppressWarnings("Convert2Diamond")
    public static Object[] genTestGenericMethod$(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genTestGenericMethod$, for testGenericMethod$.class");
        Object[] res = new Object[3];
        Map<String, Attribute> fieldAttributes = new HashMap<>();
        res[0] = fieldAttributes;
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();

        //for method test1
        //def test1(): Unit
        List<Attribute> test1Attrs = new ArrayList<>();
        List<TypeAHint> typeAHints1 = new ArrayList<>();
        //22: invokevirtual #78 // Method bcGen/GenericMethod.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        //method identity is a generic method,
        // taking in an Int
        typeAHints1.add(new TypeAHint(
                        22, 
                        Arrays.asList(TypeA.TYPEA_INT))
        );
        //85: invokevirtual #97 // Method bcGen/GenericMethod.first:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //method first is a generic method,
        // taking in a Char and a Double
        typeAHints1.add(new TypeAHint(
                        85, 
                        Arrays.asList(TypeA.TYPEA_CHAR, TypeA.TYPEA_DOUBLE))
        );
        //121: invokevirtual #78 // Method bcGen/GenericMethod.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        //method identity is a generic method,
        // taking in a ClassRef (Scala class)
        typeAHints1.add(new TypeAHint(
                        121, 
                        Arrays.asList(TypeA.TYPEA_REFERENCE))
        );
        //181: invokevirtual #78 // Method bcGen/GenericMethod.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        //method identity is a generic method,
        // taking in a java.lang.Integer
        typeAHints1.add(new TypeAHint(
                        181, 
                        Arrays.asList(TypeA.TYPEA_REFERENCE))
        );
        //no hints on println, identity2, passRef, passInt since they are not generic methods
        InstructionTypeArguments instructionTypeArgumentsT1 = 
            new InstructionTypeArguments(typeAHints1);

        test1Attrs.add(instructionTypeArgumentsT1);
        methodAttributes.put("test1", test1Attrs);

        //for method test2
        //def test2[U, X, Y](value: U, fst: X, snd: Y): Unit
        List<Attribute> test2Attrs = new ArrayList<>();
        MethodTypeParameterCount methodTypeParameterCountTest2 = 
            new MethodTypeParameterCount(3);
        MethodParameterType methodParameterTypeTest2 =
            new MethodParameterType(
                3, 
                Arrays.asList(
                    new TypeB(TypeB.M_KIND, 0), 
                    new TypeB(TypeB.M_KIND, 1), 
                    new TypeB(TypeB.M_KIND, 2)
                )
            );
        List<TypeAHint> typeAHints3 = new ArrayList<>();
        //20: invokevirtual #78 // Method bcGen/GenericMethod.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        //method identity is a generic method,
        // taking in a type parameter: Y
        // which is third type parameter of test2
        typeAHints3.add(new TypeAHint(
                        20, 
                        Arrays.asList(new TypeA(TypeA.M_KIND, 2)))
        );
        //68: invokevirtual #78 // Method bcGen/GenericMethod.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        //method identity now takes Any: L
        typeAHints3.add(new TypeAHint(
                        68, 
                        Arrays.asList(TypeA.TYPEA_REFERENCE))
        );
        //93: invokevirtual #97 // Method bcGen/GenericMethod.first:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //method first is a generic method,
        // taking in type parameters: X and Y
        // M1, M2
        typeAHints3.add(new TypeAHint(
            93,
            Arrays.asList(
                new TypeA(TypeA.M_KIND, 1), 
                new TypeA(TypeA.M_KIND, 2)
            ))
        );
        //118: invokevirtual #97 // Method bcGen/GenericMethod.first:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //method first now takes Any: L
        // and Y: M2
        typeAHints3.add(new TypeAHint(
            118,
            Arrays.asList(
                TypeA.TYPEA_REFERENCE, 
                new TypeA(TypeA.M_KIND, 2)
            ))
        );
        //134: invokevirtual #78 // Method bcGen/GenericMethod.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        //method identity takes in a type parameter: U
        // which is first type parameter of test2
        typeAHints3.add(new TypeAHint(
                        134, 
                        Arrays.asList(new TypeA(TypeA.M_KIND, 0)))
        );
        // no type hints on identity2
        InstructionTypeArguments instructionTypeArgumentsT2 = 
            new InstructionTypeArguments(typeAHints3);
        List<TypeBHint> typeBHints4 = new ArrayList<TypeBHint>();
        //20: invokevirtual #78 // Method bcGen/GenericMethod.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        // the return type of method identity Y: M2
        typeBHints4.add(
            new TypeBHint(20, new TypeB(TypeB.M_KIND, 2))
        );
        //93: invokevirtual #97 // Method bcGen/GenericMethod.first:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        // the return type of method first X: M1
        typeBHints4.add(
            new TypeBHint(93, new TypeB(TypeA.M_KIND, 1))
        );
        //134: invokevirtual #78 // Method bcGen/GenericMethod.identity:(Ljava/lang/Object;)Ljava/lang/Object;
        // the return type of method identity U: M0
        typeBHints4.add(
            new TypeBHint(134, new TypeB(TypeA.M_KIND, 0))
        );
        InvokeReturnType invokeReturnTypeTest2 =
            new InvokeReturnType(
                typeBHints4.size(), 
                typeBHints4
            );
        test2Attrs.add(methodTypeParameterCountTest2);
        test2Attrs.add(methodParameterTypeTest2);
        test2Attrs.add(instructionTypeArgumentsT2);
        test2Attrs.add(invokeReturnTypeTest2);
        methodAttributes.put("test2", test2Attrs);

        //for method testGenericMethodMain
        //def testGenericMethodMain(): Unit
        List<Attribute> testGenericMethodMainAttrs = new ArrayList<>();
        List<TypeAHint> typeAHints5 = new ArrayList<>();
        //37: invokevirtual #62 // Method test2:(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //method test2 is a generic method,
        // taking in type parameters: U, X and Y
        // which is Int, Char, Double
        typeAHints5.add(new TypeAHint(
                        37, 
                        Arrays.asList(
                            TypeA.TYPEA_INT, 
                            TypeA.TYPEA_CHAR, 
                            TypeA.TYPEA_DOUBLE)
        ));
        //75: invokevirtual #62  // Method test2:(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //method test2 is a generic method,
        // taking in type parameters: U, X and Y
        // which is Foo (L), Foo(L) and Foo(L)
        typeAHints5.add(new TypeAHint(
                        75, 
                        Arrays.asList(
                            TypeA.TYPEA_REFERENCE, 
                            TypeA.TYPEA_REFERENCE, 
                            TypeA.TYPEA_REFERENCE)
        ));
        InstructionTypeArguments instructionTypeArgumentsTestGenericMethodMain = 
            new InstructionTypeArguments(typeAHints5);
        testGenericMethodMainAttrs.add(instructionTypeArgumentsTestGenericMethodMain);
        methodAttributes.put("testGenericMethodMain", testGenericMethodMainAttrs);
        res[1] = methodAttributes;
        return res;
    }
    
    @SuppressWarnings("Convert2Diamond")
    public static Object[] genArrayCopy$(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genArrayCopyAttr, for ArrayCopy$.class");
        Object[] res = new Object[3];
        Map<String, Attribute> fieldAttributes = new HashMap<>();
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

        attrs.add(methodParameterType);
        methodAttributes.put("copy", attrs);
        res[1] = methodAttributes;
        return res;
    }

    @SuppressWarnings("Convert2Diamond")
    public static Object[] genTestArray$(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genArrayCopyAttr, for testArray$.class");
        Object[] res = new Object[3];
        Map<String, Attribute> fieldAttributes = new HashMap<>();
        res[0] = fieldAttributes;       
        Map<String, List<Attribute>> methodAttributes = new HashMap<>();

        List<Attribute> attrs = new ArrayList<>();
        //for method mainArr1
        //ArrayCopy.copy[Int](intArr, intArrCp)
        //sending in two arrays of type [I
        List<TypeAHint> typeAHints = new ArrayList<>();
        //11: invokevirtual #189 // Method bcGen/ArrayCopy$.copy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(11, 
                Arrays.asList(
                    TypeA.TYPEA_INT
                )));
        InstructionTypeArguments instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        attrs.add(instructionTypeArguments);
        methodAttributes.put("mainArr1", attrs);

        //for method mainArr2
        //ArrayCopy.copy[Any](objArr, anyArrCp)
        //sending in two arrays of type [Ljava/lang/Object;
        attrs = new ArrayList<>();
        typeAHints = new ArrayList<>();
        //11: invokevirtual #189 // Method bcGen/ArrayCopy$.copy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(11, 
                Arrays.asList(
                    TypeA.TYPEA_REFERENCE
                )));
        instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        attrs.add(instructionTypeArguments);
        methodAttributes.put("mainArr2", attrs);

        //for method mainArr3
        //ArrayCopy.copy[Double](doubleArr, doubleArrCp)
        //sending in two arrays of type [D
        attrs = new ArrayList<>();
        typeAHints = new ArrayList<>();
        //11: invokevirtual #189 // Method bcGen/ArrayCopy$.copy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(11, 
                Arrays.asList(
                    TypeA.TYPEA_DOUBLE
                )));
        instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        attrs.add(instructionTypeArguments);
        methodAttributes.put("mainArr3", attrs);

        //for method mainArr4
        //ArrayCopy.copy[Any](intDoubleArr, intDoubleArrCp)
        //sending in two arrays of type [Ljava/lang/Object;
        attrs = new ArrayList<>();
        typeAHints = new ArrayList<>();
        //11: invokevirtual #189 // Method bcGen/ArrayCopy$.copy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(11, 
                Arrays.asList(
                    TypeA.TYPEA_REFERENCE
                )));
        instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        attrs.add(instructionTypeArguments);
        methodAttributes.put("mainArr4", attrs);

        //for method genericCopy
        //def genericCopy[T](src: Array[T], dst: Array[T]): Unit
        attrs = new ArrayList<>();
        MethodTypeParameterCount methodTypeParameterCount = 
            new MethodTypeParameterCount(1);
        //two parameters (src: Array[T], dest: Array[T]) of type Array[T
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
        typeAHints = new ArrayList<>();
        //5: invokevirtual #189 // Method bcGen/ArrayCopy$.copy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(5, 
                Arrays.asList(
                    new TypeA(TypeA.M_KIND, 0)
                )));
        instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        attrs.add(methodTypeParameterCount);
        attrs.add(methodParameterType);
        attrs.add(instructionTypeArguments);
        methodAttributes.put("genericCopy", attrs);

        //for method mainArr5
        //genericCopy[Int](intArr, intArrCp)
        attrs = new ArrayList<>();
        typeAHints = new ArrayList<>();
        //9: invokevirtual #271 // Method genericCopy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(9, 
                Arrays.asList(
                    TypeA.TYPEA_INT
                )));
        instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        attrs.add(instructionTypeArguments);
        methodAttributes.put("mainArr5", attrs);

        //for method mainArr6
        //genericCopy[Any](objArr, anyArrCp)
        attrs = new ArrayList<>();
        typeAHints = new ArrayList<>();
        //9: invokevirtual #271 // Method genericCopy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(9, 
                Arrays.asList(
                    TypeA.TYPEA_REFERENCE
                )));
        instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        attrs.add(instructionTypeArguments);
        methodAttributes.put("mainArr6", attrs);

        //for method mainArr7
        //genericCopy[Double](doubleArr, doubleArrCp)
        attrs = new ArrayList<>();
        typeAHints = new ArrayList<>();
        //9: invokevirtual #271 // Method genericCopy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(9, 
                Arrays.asList(
                    TypeA.TYPEA_DOUBLE
                )));
        instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);

        attrs.add(instructionTypeArguments);
        methodAttributes.put("mainArr7", attrs);

        //for method mainArr8
        //genericCopy[Any](intDoubleArr, intDoubleArrCp)
        attrs = new ArrayList<>();
        typeAHints = new ArrayList<>();
        //9: invokevirtual #271 // Method genericCopy:(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(
            new TypeAHint(9,
                Arrays.asList(
                    TypeA.TYPEA_REFERENCE
                )));
        instructionTypeArguments =
            new InstructionTypeArguments(typeAHints);

        attrs.add(instructionTypeArguments);
        methodAttributes.put("mainArr8", attrs);

        res[1] = methodAttributes;
        return res;
    }
    
    public static Object[] genPair(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genPair, for Classes/Pair.class");
        Object[] res = new Object[3];
        Map<String, Attribute> fieldAttributes = new HashMap<>();
        FieldType kFieldType = new FieldType(
            new TypeB(TypeB.K_KIND, 0, 0));
        FieldType vFieldType = new FieldType(
            new TypeB(TypeB.K_KIND, 0, 1));
        fieldAttributes.put("k", kFieldType);
        fieldAttributes.put("v", vFieldType);
        res[0] = fieldAttributes;

        Map<String, List<Attribute>> methodAttributes = new HashMap<>();
        List<Attribute> attrs = new ArrayList<>();
        //for method k(): scala getter for field k
        MethodReturnType methodReturnTypeK = 
            new MethodReturnType(
                new TypeB(TypeB.K_KIND, 0, 0));
        attrs.add(methodReturnTypeK);
        methodAttributes.put("k", attrs);

        attrs = new ArrayList<>();
        //for method v(): scala getter for field v
        MethodReturnType methodReturnTypeV = 
            new MethodReturnType(
                new TypeB(TypeB.K_KIND, 0, 1));
        attrs.add(methodReturnTypeV);
        methodAttributes.put("v", attrs);

        attrs = new ArrayList<>();
        //for method k_$eq(K): scala setter for field k
        MethodParameterType methodParameterTypeK_$eq = 
            new MethodParameterType(
                1, Arrays.asList(
                    new TypeB(TypeB.K_KIND, 0, 0)
                )
            );
        attrs.add(methodParameterTypeK_$eq);
        methodAttributes.put("k_$eq", attrs);

        attrs = new ArrayList<>();
        //for method v_$eq(V): scala setter for field v
        MethodParameterType methodParameterTypeV_$eq = 
            new MethodParameterType(
                1, Arrays.asList(
                    new TypeB(TypeB.K_KIND, 0, 1)
                )
            );
        attrs.add(methodParameterTypeV_$eq);
        methodAttributes.put("v_$eq", attrs);

        attrs = new ArrayList<>();
        //for method getKey()
        MethodReturnType methodReturnTypeGetKey = 
            new MethodReturnType(
                new TypeB(TypeB.K_KIND, 0, 0));
        List<TypeBHint> typeBHints = new ArrayList<>();
        // 1: invokevirtual #33 // Method k:()Ljava/lang/Object;
        typeBHints.add(
            new TypeBHint(1, 
                new TypeB(TypeB.K_KIND, 0, 0)
        ));
        InvokeReturnType invokeReturnTypeGetKey = 
            new InvokeReturnType(
                typeBHints.size(), 
                typeBHints
            );
        attrs.add(methodReturnTypeGetKey);
        attrs.add(invokeReturnTypeGetKey);
        methodAttributes.put("getKey", attrs);

        attrs = new ArrayList<>();
        //for method getValue()
        MethodReturnType methodReturnTypeGetValue = 
            new MethodReturnType(
                new TypeB(TypeB.K_KIND, 0, 1));
        typeBHints = new ArrayList<>();
        // 1: invokevirtual #36 // Method v:()Ljava/lang/Object;
        typeBHints.add(
            new TypeBHint(1, 
                new TypeB(TypeB.K_KIND, 0, 1)
        ));
        InvokeReturnType invokeReturnTypeGetValue = 
            new InvokeReturnType(
                typeBHints.size(), 
                typeBHints
            );
        attrs.add(methodReturnTypeGetValue);
        attrs.add(invokeReturnTypeGetValue);
        methodAttributes.put("getValue", attrs);

        attrs = new ArrayList<>();
        //for method setKey(K)
        MethodParameterType methodParameterTypeSetKey = 
            new MethodParameterType(
                1, Arrays.asList(
                    new TypeB(TypeB.K_KIND, 0, 0)
                )
            );
        attrs.add(methodParameterTypeSetKey);
        methodAttributes.put("setKey", attrs);

        attrs = new ArrayList<>();
        //for method setValue(V)
        MethodParameterType methodParameterTypeSetValue = 
            new MethodParameterType(
                1, Arrays.asList(
                    new TypeB(TypeB.K_KIND, 0, 1)
                )
            );
        attrs.add(methodParameterTypeSetValue);
        methodAttributes.put("setValue", attrs);

        attrs = new ArrayList<>();
        //for Constructor <init>
        // equals to class type parameter count
        MethodTypeParameterCount methodTypeParameterCountInit = 
            new MethodTypeParameterCount(2);
        MethodParameterType methodParameterTypeInit = 
            new MethodParameterType(
                2, Arrays.asList(
                    new TypeB(TypeB.M_KIND, 0), 
                    new TypeB(TypeB.M_KIND, 1)
                )
            );
            
        attrs.add(methodTypeParameterCountInit);
        attrs.add(methodParameterTypeInit);
        methodAttributes.put("<init>", attrs);

        res[1] = methodAttributes;

        List<Attribute> classAttributes = new ArrayList<>();
        //for class type parameter count
        ClassTypeParameterCount classTypeParameterCount = 
            new ClassTypeParameterCount(2);
        classAttributes.add(classTypeParameterCount);

        res[2] = classAttributes;

        return res;
    }

    public static Object[] genTestPair$(){
        System.out.println("Changing file:" + file);
        System.out.println("Using genTestPair$, for Classes/testPair$.class");
        Object[] res = new Object[3];
        Map<String, Attribute> fieldAttributes = new HashMap<>();
        res[0] = fieldAttributes;

        Map<String, List<Attribute>> methodAttributes = new HashMap<>();
        List<Attribute> attrs = new ArrayList<>();
        //for method <clinit>
        //creates Pair[Int, String], Pair[Double, Long], Pair[Any, Any]
        List<TypeAHint> typeAHints = new ArrayList<>();
        // 10: new #24 // class bcGen/Classes/Pair
        typeAHints.add(new TypeAHint(
                        10, 
                        Arrays.asList(
                            TypeA.TYPEA_INT, 
                            TypeA.TYPEA_REFERENCE
                        ))
        );
        // 20: invokespecial #35 // Method bcGen/Classes/Pair."<init>":(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(new TypeAHint(
                        20, 
                        Arrays.asList(
                            TypeA.TYPEA_INT, 
                            TypeA.TYPEA_REFERENCE
                        ))
        );
        // 26: new #24 // class bcGen/Classes/Pair
        typeAHints.add(new TypeAHint(
                        26, 
                        Arrays.asList(
                            TypeA.TYPEA_DOUBLE, 
                            TypeA.TYPEA_LONG
                        ))
        );
        // 42: invokespecial #35 // Method bcGen/Classes/Pair."<init>":(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(new TypeAHint(
                        42, 
                        Arrays.asList(
                            TypeA.TYPEA_DOUBLE, 
                            TypeA.TYPEA_LONG
                        ))
        );
        // 48: new #24 // class bcGen/Classes/Pair
        typeAHints.add(new TypeAHint(
                        48, 
                        Arrays.asList(
                            TypeA.TYPEA_REFERENCE, 
                            TypeA.TYPEA_REFERENCE
                        ))
        );
        // 60: invokespecial #35 // Method bcGen/Classes/Pair."<init>":(Ljava/lang/Object;Ljava/lang/Object;)V
        typeAHints.add(new TypeAHint(
                        60, 
                        Arrays.asList(
                            TypeA.TYPEA_REFERENCE, 
                            TypeA.TYPEA_REFERENCE
                        ))
        );
        InstructionTypeArguments instructionTypeArguments = 
            new InstructionTypeArguments(typeAHints);
        attrs.add(instructionTypeArguments);
        methodAttributes.put("<clinit>", attrs);

        attrs = new ArrayList<>();
        //for method testPair1
        // no hints needed

        attrs = new ArrayList<>();
        //for method testPair2
        // no hint needed

        attrs = new ArrayList<>();
        //for method testPair3
        // no hint needed

        attrs = new ArrayList<>();
        //for method testPair4
        // no hint needed

        attrs = new ArrayList<>();
        //for method passGenericPair
        //  def passGenericPair[K, V](pair: Pair[K, V]): String = {
        MethodTypeParameterCount methodTypeParameterCount = 
            new MethodTypeParameterCount(2);
        attrs.add(methodTypeParameterCount);
        methodAttributes.put("passGenericPair", attrs);

        attrs = new ArrayList<>();
        //for method testPair5
        // 5: invokevirtual #136 // Method passGenericPair:(LbcGen/Classes/Pair;)Ljava/lang/String;
        typeAHints = new ArrayList<>();
        typeAHints.add(new TypeAHint(
                        5, 
                        Arrays.asList(
                            TypeA.TYPEA_INT,
                            TypeA.TYPEA_REFERENCE
                        ))
        );
        InstructionTypeArguments instructionTypeArgumentsTestPair5 = 
            new InstructionTypeArguments(typeAHints);
        attrs.add(instructionTypeArgumentsTestPair5);
        methodAttributes.put("testPair5", attrs);

        attrs = new ArrayList<>();
        //for method testPair6
        // 5: invokevirtual #136 // Method passGenericPair:(LbcGen/Classes/Pair;)Ljava/lang/String;
        typeAHints = new ArrayList<>();
        typeAHints.add(new TypeAHint(
                        5, 
                        Arrays.asList(
                            TypeA.TYPEA_DOUBLE,
                            TypeA.TYPEA_LONG
                        ))
        );
        InstructionTypeArguments instructionTypeArgumentsTestPair6 = 
            new InstructionTypeArguments(typeAHints);
        attrs.add(instructionTypeArgumentsTestPair6);
        methodAttributes.put("testPair6", attrs);

        attrs = new ArrayList<>();
        //for method testPair7
        // no hints needed

        attrs = new ArrayList<>();
        //for method testPair8
        // no hints needed

        attrs = new ArrayList<>();
        //for method testPair9
        // 5: invokevirtual #136 // Method passGenericPair:(LbcGen/Classes/Pair;)Ljava/lang/String;
        typeAHints = new ArrayList<>();
        typeAHints.add(new TypeAHint(
                        5, 
                        Arrays.asList(
                            TypeA.TYPEA_REFERENCE,
                            TypeA.TYPEA_REFERENCE
                        ))
        );
        InstructionTypeArguments instructionTypeArgumentsTestPair9 = 
            new InstructionTypeArguments(typeAHints);
        attrs.add(instructionTypeArgumentsTestPair9);
        methodAttributes.put("testPair9", attrs);

        res[1] = methodAttributes;

        List<Attribute> classAttributes = new ArrayList<>();
        res[2] = classAttributes;

        return res;
    }
}