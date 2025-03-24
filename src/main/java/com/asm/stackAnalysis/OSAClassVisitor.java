package com.asm.stackAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

import com.asm.generics.attributes.GenericsAttribute;

public class OSAClassVisitor extends ClassVisitor {
    private GenericsAttribute genericsAttribute = null;
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
        System.out.println("In Class Visitor, Method: " + name);
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
        return new OSAMethodVisitor(Opcodes.ASM9, methodNode, className, bcIndex, typeList);
    }

    @Override
    public void visitAttribute(Attribute attr){
        System.out.println("in visit Attribute: ");
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

}
