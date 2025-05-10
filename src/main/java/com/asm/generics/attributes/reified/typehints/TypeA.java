package com.asm.generics.attributes.reified.typehints;

public class TypeA {
    public static final int BYTE = 'B';
    public static final int CHAR = 'C';
    public static final int DOUBLE = 'D';
    public static final int FLOAT = 'F';
    public static final int INT = 'I';
    public static final int LONG = 'J';
    public static final int SHORT = 'S';
    public static final int BOOLEAN = 'Z';
    public static final int REFERENCE = 'L';

    

    public static final byte K_KIND = 'K';
    public static final byte M_KIND = 'M';
    public static final byte PRIMITIVE_KIND = 'P';
    
    public static final TypeA TYPEA_BYTE = 
        new TypeA(PRIMITIVE_KIND, BYTE);
    public static final TypeA TYPEA_CHAR =
        new TypeA(PRIMITIVE_KIND, CHAR);
    public static final TypeA TYPEA_DOUBLE =
        new TypeA(PRIMITIVE_KIND, DOUBLE);
    public static final TypeA TYPEA_FLOAT =
        new TypeA(PRIMITIVE_KIND, FLOAT);
    public static final TypeA TYPEA_INT =
        new TypeA(PRIMITIVE_KIND, INT);
    public static final TypeA TYPEA_LONG =
        new TypeA(PRIMITIVE_KIND, LONG);
    public static final TypeA TYPEA_SHORT =
        new TypeA(PRIMITIVE_KIND, SHORT);
    public static final TypeA TYPEA_BOOLEAN =
        new TypeA(PRIMITIVE_KIND, BOOLEAN);
    public static final TypeA TYPEA_REFERENCE =
        new TypeA(PRIMITIVE_KIND, REFERENCE);

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
