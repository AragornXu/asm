package com.asm.generics.attributes.reified.typehints;

import java.util.List;

public class TypeAHint {
    private final int bytecodeOffset;
    private final List<TypeA> typeList;

    public TypeAHint(int bytecodeOffset, List<TypeA> typeList) {
        this.bytecodeOffset = bytecodeOffset;
        this.typeList = typeList;
    }

    public int getBytecodeOffset() {
        return bytecodeOffset;
    }

    public List<TypeA> getTypeList() {
        return typeList;
    }
    
}
