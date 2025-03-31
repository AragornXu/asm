package com.asm.util;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class OffsetMethodVisitor extends MethodVisitor{
    
    @SuppressWarnings("FieldMayBeFinal")
    private Map<Integer, Integer> offsetMap;
    private int currentOffset = 0;
    private int currentIndex = 0;
    private MethodNode methodNode;

    public OffsetMethodVisitor(int api, MethodVisitor mv, Map<Integer, Integer> offsetMap) {
        super(api, mv);
        this.offsetMap = offsetMap;
        // if (mv instanceof MethodNode) {
        //     this.methodNode = (MethodNode) mv;
        // } else {
        //     throw new IllegalArgumentException("In OffsetMethodVistor constructor: MethodVisitor is not an instance of MethodNode");
        // }
    }

    // public OffsetMethodVisitor(int api, MethodNode methodNode) {
    //     super(api, methodNode);
    //     this.methodNode = methodNode;
    //     System.out.println("In OffsetVisitor constructor for methodNode: " + methodNode.name);
    //     System.out.println("In OffsetVisitor constructor for methodNode instr size: " + methodNode.instructions.size());
        
    // }

    @Override
    public void visitEnd() {
        System.out.println("In OffsetVisitor visitEnd, offsetMap: " + offsetMap);
        // methodNode.accept(this);
        super.visitEnd();
    }

    public Map<Integer, Integer> getOffsetMap() {
        return offsetMap;
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        // System.out.println("In offsetvistor visitInsn, opcode: " + opcode);
        addOffset(1);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        // System.out.println("In offsetvistor visitIntInsn, opcode: " + opcode + ", operand: " + operand);
        super.visitIntInsn(opcode, operand);
        addOffset((opcode == Opcodes.SIPUSH) ? 3 : 2);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        // System.out.println("In offsetvistor visitVarInsn, opcode: " + opcode + ", var: " + var);
        super.visitVarInsn(opcode, var);
        addOffset((var > 255) ? 3 : 2); //TODO: check if this is correct
        
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        // System.out.println("In offsetvistor visitTypeInsn, opcode: " + opcode + ", type: " + type);
        super.visitTypeInsn(opcode, type);
        addOffset(3);
        
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        // System.out.println("In offsetvistor visitFieldInsn, opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);
        super.visitFieldInsn(opcode, owner, name, descriptor);
        addOffset(3);
        
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        // System.out.println("In offsetvistor visitMethodInsn, opcode: " + opcode + ", owner: " + owner + ", name: " + name + ", descriptor: " + descriptor);
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        addOffset((opcode == Opcodes.INVOKEINTERFACE) ? 5 : 3);
        
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, org.objectweb.asm.Handle bsm, Object... bsmArgs) {
        // System.out.println("In offsetvistor visitInvokeDynamicInsn, name: " + name + ", descriptor: " + descriptor + ", bsm: " + bsm);
        super.visitInvokeDynamicInsn(name, descriptor, bsm, bsmArgs);
        addOffset(5);
        
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        super.visitJumpInsn(opcode, label);
        addOffset(3);
        
    }

    @Override
    public void visitLabel(Label label) {
        // System.out.println("In offsetvistor visitLabel, label: " + label);
        super.visitLabel(label);
        addOffset(0);
        
    }

    @Override
    public void visitLdcInsn(Object value) {
        if (value instanceof Long || value instanceof Double) {
            addOffset(3);
        } else {
            addOffset(2);
        }
        super.visitLdcInsn(value);
    }

    @Override //TODO check correctness
    public void visitIincInsn(int var, int increment) {
        super.visitIincInsn(var, increment);
        addOffset(3);
    }

    @Override //TODO calculate offset for this
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        // int offset = 4 + (labels.length + 1) * 4;
        // addOffset(offset);
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override //TODO
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override //TODO
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
    }

    private void addOffset(int offset){
        // System.out.println("calling addOffset, currentIndex: " + currentIndex + ", currentOffset: " + currentOffset);
        // MethodNode mn;
        // try {
        //     mn = (MethodNode) mv;
        // } catch (ClassCastException e) {
        //     throw new IllegalStateException("MethodVisitor mv is not an instance of MethodNode", e);
        // }
        // AbstractInsnNode insn = methodNode.instructions.get(currentIndex);
        // System.out.println("In offsetvistor addOffset, insn: " + insn);
        // System.out.println("In offsetvistor addOffset, currentIndex: " + currentIndex + ", currentOffset: " + currentOffset);
        offsetMap.put(currentIndex, currentOffset);
        currentIndex++;
        currentOffset += offset;
    }
}
