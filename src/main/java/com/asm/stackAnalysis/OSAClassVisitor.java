package com.asm.stackAnalysis;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

public class OSAClassVisitor extends ClassVisitor {
    public OSAClassVisitor(int api) {
        super(api);
    }

    private String className;
    @Override
    public void visit(int version, int access, String name, String signature, String superName,
            String[] interfaces) {
        this.className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                        String signature, String[] exceptions) {
        MethodNode methodNode = new MethodNode(Opcodes.ASM9, access, name, descriptor, signature, exceptions);
        return new OSAMethodVisitor(Opcodes.ASM9, methodNode, className);
    }

}
