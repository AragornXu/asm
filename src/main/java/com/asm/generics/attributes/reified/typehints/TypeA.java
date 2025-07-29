package com.asm.generics.attributes.reified.typehints;

public class TypeA {
    public static final byte BYTE = 'B';
    public static final byte CHAR = 'C';
    public static final byte DOUBLE = 'D';
    public static final byte FLOAT = 'F';
    public static final byte INT = 'I';
    public static final byte LONG = 'J';
    public static final byte SHORT = 'S';
    public static final byte BOOLEAN = 'Z';
    public static final byte REFERENCE = 'L';

    

    public static final byte K_KIND = 'K';
    public static final byte M_KIND = 'M';
    // public static final byte PRIMITIVE_KIND = 'P';
    
    public static final TypeA TYPEA_BYTE = 
        new TypeA(BYTE, 0);
    public static final TypeA TYPEA_CHAR =
        new TypeA(CHAR, 0);
    public static final TypeA TYPEA_DOUBLE =
        new TypeA(DOUBLE, 0);
    public static final TypeA TYPEA_FLOAT =
        new TypeA(FLOAT, 0);
    public static final TypeA TYPEA_INT =
        new TypeA(INT, 0);
    public static final TypeA TYPEA_LONG =
        new TypeA(LONG, 0);
    public static final TypeA TYPEA_SHORT =
        new TypeA(SHORT, 0);
    public static final TypeA TYPEA_BOOLEAN =
        new TypeA(BOOLEAN, 0);
    public static final TypeA TYPEA_REFERENCE =
        new TypeA(REFERENCE, 0);

    private final byte kind;
    private final int index;

    public TypeA(byte kind, int index) {
        this.kind = kind;
        this.index = index;
    }

    public byte getKind() {
        return kind;
    }

    public int getIndex() {
        return index;
    }
}
