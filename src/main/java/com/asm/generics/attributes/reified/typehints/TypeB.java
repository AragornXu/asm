package com.asm.generics.attributes.reified.typehints;

public class TypeB {
    public static final TypeB NO_HINT = 
        new TypeB((byte)0, -1);

    public static final byte K_KIND = 'K';
    public static final byte M_KIND = 'M';
    public static final byte ARR_K_KIND = 'k';
    public static final byte ARR_M_KIND = 'm';

    private final byte kind;
    private final int outerClassIndex;
    private final int index;

    public TypeB(byte kind, int index) {
        this.kind = kind;
        this.index = index;
        this.outerClassIndex = 0;
    }

    public TypeB(byte kind, int outerClassIndex, int index) {
        this.kind = kind;
        this.index = index;
        this.outerClassIndex = outerClassIndex;
    }

    public byte getKind() {
        return kind;
    }

    public int getIndex() {
        return index;
    }

    public int getOuterClassIndex() {
        return outerClassIndex;
    }

    public boolean isNoHint() {
        return this == NO_HINT || (this.kind == 0 && this.index == -1);
    }
}
