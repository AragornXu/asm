package com.asm.util;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;

import com.asm.stackAnalysis.OSAMethodVisitor;

public class OffsetClassVisitor extends ClassVisitor{
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Map<Integer, Integer>> offsetMap;
    public OffsetClassVisitor(int api, ClassVisitor cv, Map<String, Map<Integer, Integer>> offsetMap) {
        super(api, cv);
        this.offsetMap = offsetMap;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
            String signature, String[] exceptions) {
        System.out.println("In OffsetClassVisitor, visitMethod: " + name);
        Map<Integer, Integer> methodOffsetMap = new HashMap<>();
        offsetMap.put(name, methodOffsetMap);
        //"second" methodvisitor
        MethodVisitor secondMV = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (secondMV instanceof OSAMethodVisitor){
            OSAMethodVisitor osa = (OSAMethodVisitor) secondMV;
            System.out.println("In OffsetClassVisitor, secondMV instanceof osa: ");
            MethodNode mn = osa.getMethodNode();
            System.out.println("In OffsetClassVisitor, osa mn: " + mn.instructions.size());
            return new OffsetMethodVisitor(api, osa, offsetMap.get(name));
        } else {
            System.out.println("In OffsetClassVisitor, secondMV not instanceof osa: ");
            return new OffsetMethodVisitor(api, secondMV, offsetMap.get(name));
        }
    }

    public Map<String, Map<Integer, Integer>> getOffsetMap() {
        return offsetMap;
    }
}
