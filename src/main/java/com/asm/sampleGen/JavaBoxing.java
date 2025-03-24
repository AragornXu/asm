package com.asm.sampleGen;

@SuppressWarnings("UnnecessaryBoxing")
public class JavaBoxing {
    public static void main(String[] args) {
        // Manual boxing using valueOf
        
        Integer intBoxed = Integer.valueOf(42);
        Double doubleBoxed = Double.valueOf(3.14);
        Character charBoxed = Character.valueOf('A');
        Boolean boolBoxed = Boolean.valueOf(true);
        Long longBoxed = Long.valueOf(123456789L);
        Float floatBoxed = Float.valueOf(2.71f);
        Byte byteBoxed = Byte.valueOf((byte) 127);
        Short shortBoxed = Short.valueOf((short) 1000);

        // Autoboxing (Java automatically boxes the primitives)
        Integer intAuto = 42;
        Double doubleAuto = 3.14;
        Character charAuto = 'A';
        Boolean boolAuto = true;
        Long longAuto = 123456789L;
        Float floatAuto = 2.71f;
        Byte byteAuto = 127;
        Short shortAuto = 1000;

        // Print them
        System.out.println("Manual boxing:");
        System.out.println("Integer: " + intBoxed);
        System.out.println("Double: " + doubleBoxed);
        System.out.println("Character: " + charBoxed);
        System.out.println("Boolean: " + boolBoxed);
        System.out.println("Long: " + longBoxed);
        System.out.println("Float: " + floatBoxed);
        System.out.println("Byte: " + byteBoxed);
        System.out.println("Short: " + shortBoxed);

        System.out.println("\nAutoboxing:");
        System.out.println("Integer: " + intAuto);
        System.out.println("Double: " + doubleAuto);
        System.out.println("Character: " + charAuto);
        System.out.println("Boolean: " + boolAuto);
        System.out.println("Long: " + longAuto);
        System.out.println("Float: " + floatAuto);
        System.out.println("Byte: " + byteAuto);
        System.out.println("Short: " + shortAuto);
    }
}
