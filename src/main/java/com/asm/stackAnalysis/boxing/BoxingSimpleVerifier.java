package com.asm.stackAnalysis.boxing;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.SimpleVerifier;

public class BoxingSimpleVerifier extends SimpleVerifier{
    public BoxingSimpleVerifier(){
        super(Opcodes.ASM9, null, null, null, false);
    }

    @Override
    public BasicValue newValue(Type type){
        BasicValue value = super.newValue(type);
        if (value == null){
            return null;
        }
        return new BoxingBasicValue(value.getType(), null);
    }

    @Override
    public BasicValue newOperation(AbstractInsnNode insn) throws AnalyzerException {
        BasicValue value = super.newOperation(insn);
        if (value == null){
            return null;
        }
        return new BoxingBasicValue(value.getType(), insn);
    }

    @Override
    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) throws AnalyzerException {
        BasicValue copy = super.copyOperation(insn, value);
        if (copy == null){
            return null;
        }
        return new BoxingBasicValue(copy.getType(), insn);
    }

    @Override
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) throws AnalyzerException {
        BasicValue unary = super.unaryOperation(insn, value);
        if (unary == null){
            return null;
        }
        return new BoxingBasicValue(unary.getType(), insn);
    }

    @Override
    public BasicValue binaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2) throws AnalyzerException {
        BasicValue binary = super.binaryOperation(insn, value1, value2);
        if (binary == null){
            return null;
        }
        return new BoxingBasicValue(binary.getType(), insn);
    }

    @Override
    public BasicValue ternaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2, BasicValue value3) throws AnalyzerException {
        BasicValue ternary = super.ternaryOperation(insn, value1, value2, value3);
        if (ternary == null){
            return null;
        }
        return new BoxingBasicValue(ternary.getType(), insn);
    }

    @Override
    public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) throws AnalyzerException {
        BasicValue nary = super.naryOperation(insn, values);
        if (nary == null){
            return null;
        }
        return new BoxingBasicValue(nary.getType(), insn);
    }

}
