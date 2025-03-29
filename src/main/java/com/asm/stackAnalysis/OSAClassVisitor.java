package com.asm.stackAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.asm.generics.attributes.GenericsAttribute;
import com.asm.util.OffsetMethodVisitor;

public class OSAClassVisitor extends ClassVisitor {
    private GenericsAttribute genericsAttribute = null;
    private List<MethodVisitor> methodVisitors = new ArrayList<>();
    private Map<String, Map<Integer, Integer>> offsetMap;

    public OSAClassVisitor(int api, Map<String, Map<Integer, Integer>> offsetMap) {
        super(api);
        this.offsetMap = offsetMap;
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
        System.out.println("In OSA Class Visitor, Method: " + name);
        List<Integer> bcIndex = new ArrayList<>();
        if (genericsAttribute != null) {
            bcIndex = genericsAttribute.getBcIndexForMethod(name);
            System.out.println("bcIndex: " + bcIndex);
        }
        List<String> typeList = new ArrayList<>();
        if (genericsAttribute != null) {
            typeList = genericsAttribute.getTypeForMethod(name);
            System.out.println("typeList: " + typeList);
        }
        // OffsetVisitor offsetVisitor = new OffsetVisitor(Opcodes.ASM9, methodNode);
        // methodNode.accept(offsetVisitor);
        // System.out.println("In OSAClass Visitor, MethodNode insns: " + methodNode.instructions.size());
        // Map<AbstractInsnNode, Integer> offsetMap = offsetVisitor.getOffsetMap();
        // System.out.println("offsetMap: " + offsetMap);
        OSAMethodVisitor osa = new OSAMethodVisitor(
            api, methodNode, className, bcIndex, typeList, offsetMap.getOrDefault(name, null));
        methodVisitors.add(osa);
        return osa;
    }

    @Override
    public void visitAttribute(Attribute attr){
        System.out.println("in OSA visit Attribute: ");
        if (attr instanceof GenericsAttribute) {
            System.out.println("GenericsAttribute found");
            GenericsAttribute ga = (GenericsAttribute) attr;
            List<String> methodList = ga.getMethodList();
            List<Integer> bcIndex = ga.getBcIndex();
            List<String> typeList = ga.getTypeList();
            assert bcIndex.size() == typeList.size();
            for (int i = 0; i < bcIndex.size(); i++) {
                String method = methodList.get(i);
                int intVal = bcIndex.get(i);
                String strVal = typeList.get(i);
                System.out.println("method: " + method + ", Int: " + intVal + ", String: " + strVal);
            }
            genericsAttribute = ga;
            super.visitAttribute(attr);
        }
    }

    // @Override
    // public void visitEnd() {
    //     System.out.println("In OSAClassVisitor, visitEnd");
    //     for (MethodVisitor mv : methodVisitors) {
    //         if (mv instanceof OSAMethodVisitor) {
    //             OSAMethodVisitor osa = (OSAMethodVisitor) mv;
    //             MethodNode methodNode = osa.getMethodNode();
    //             System.out.println("In OSAClass Visitor, MethodNode insns: " + methodNode.instructions.size());
    //             // OffsetMethodVisitor offsetVisitor = new OffsetMethodVisitor(Opcodes.ASM9, methodNode);
    //             // methodNode.accept(offsetVisitor);
    //             // Map<AbstractInsnNode, Integer> offsetMap = offsetVisitor.getOffsetMap();
    //             // System.out.println("offsetMap: " + offsetMap);
    //             osa.analyzeMethod(className, methodNode, 
    //                 offsetMap.getOrDefault(methodNode.name, null));
    //         }
    //     }
    //     super.visitEnd();
    // }

    

}
