package com.asm.generics.attributes.reified;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;

import com.asm.generics.attributes.reified.typehints.TypeB;

public class FieldType extends Attribute{
    private final TypeB typeB;

    public FieldType() {
        super("FieldType");
        this.typeB = TypeB.NO_HINT;
    }

    public FieldType(TypeB typeB) {
        super("FieldType");
        this.typeB = typeB;
    }

    public TypeB getTypeB() {
        return typeB;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }

    @Override
    public Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels) {
        int cur = off;
        byte kind = (byte) cr.readByte(cur);
        cur += 1;
        int index = cr.readUnsignedShort(cur);
        //cur += 2;
        return new FieldType(new TypeB(kind, index));
    }
    
}
