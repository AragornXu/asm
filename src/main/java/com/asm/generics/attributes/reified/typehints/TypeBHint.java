package com.asm.generics.attributes.reified.typehints;

public class TypeBHint {
    private final int bytecodeOffset;
    private final TypeB typeB;

    public TypeBHint(int bytecodeOffset, TypeB typeB) {
        this.bytecodeOffset = bytecodeOffset;
        this.typeB = typeB;
    }
    public int getBytecodeOffset() {
        return bytecodeOffset;
    }
    public TypeB getTypeB() {
        return typeB;
    }
}
