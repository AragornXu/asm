package com.asm.util;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

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
        Map<Integer, Integer> methodOffsetMap = new HashMap<>();
        offsetMap.put(name, methodOffsetMap);
        MethodVisitor offsetMV = new OffsetMethodVisitor(api, methodOffsetMap);
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    public Map<String, Map<Integer, Integer>> getOffsetMap() {
        return offsetMap;
    }
}
