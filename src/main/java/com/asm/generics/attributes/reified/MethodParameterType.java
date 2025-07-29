package com.asm.generics.attributes.reified;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

import com.asm.generics.attributes.reified.typehints.TypeB;

/*
MethodParameterType_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
	u2 parameter_count;
	{	u1 K_M_indicator
		u2 outer_class_indicator
        u2 index
    } typeBs[parameter_count];
}
*/
public class MethodParameterType extends Attribute{
    private final int count;
    private final List<TypeB> typeList;
    public MethodParameterType() {
        super("MethodParameterType");
        this.count = 0;
        this.typeList = new ArrayList<>();
    }

    public MethodParameterType(int count, List<TypeB> typeList) {
        super("MethodParameterType");
        this.count = count;
        this.typeList = typeList;
    }

    public int getCount() {
        return count;
    }

    public List<TypeB> getTypeList() {
        return typeList;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }

    @Override
    public Attribute read(ClassReader cr, int off, int len, char[] buf, int codeOff, Label[] labels){
        int cur = off;
        int parameterCount = cr.readUnsignedShort(cur);
        cur += 2;
        List<TypeB> typeBs = new ArrayList<>();
        for (int i = 0; i < parameterCount; i++) {
            byte kind = (byte) cr.readByte(cur);
            cur += 1;
            int outerClassIndex = cr.readUnsignedShort(cur);
            cur += 2;
            int index = cr.readUnsignedShort(cur);
            cur += 2;
            typeBs.add(new TypeB(kind, outerClassIndex, index));
        }
        return new MethodParameterType(parameterCount, typeBs);
    }

    @Override
    public ByteVector write(ClassWriter cw, byte[] code, int codeLength, int maxStack, int maxLocals){
        ByteVector bv = new ByteVector();
        bv.putShort(count);
        for (TypeB typeB : typeList) {
            bv.putByte(typeB.getKind());
            bv.putShort(typeB.getOuterClassIndex());
            bv.putShort(typeB.getIndex());
        }
        return bv;
    }

}
