package com.asm.stackAnalysis;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

public class OSAMethodVisitor extends MethodVisitor {
    private String className;
    private MethodNode methodNode;
    private List<Integer> bcIndex;
    private List<String> typeList;
    private boolean containAttribute = false;

    public OSAMethodVisitor(int api) {
        super(api);
    }
    public OSAMethodVisitor(int api, MethodNode methodNode, String className, List<Integer> bcIndex, List<String> typeList) {
        super(api, methodNode);
        this.methodNode = methodNode;
        this.className = className;
        this.bcIndex = bcIndex;
        this.typeList = typeList;
        if (!bcIndex.isEmpty()) {
            containAttribute = true;
        }
    }

    // @Override
    // public void visitAttribute(Attribute attr){
    //     System.out.println("in visit Attribute: ");
    //     if (attr instanceof GenericsAttribute) {
    //         System.out.println("GenericsAttribute found");
    //         GenericsAttribute ga = (GenericsAttribute) attr;
    //         List<String> methodList = ga.getMethodList();
    //         List<Integer> bcIndex = ga.getBcIndex();
    //         List<String> typeList = ga.getTypeList();
    //         assert bcIndex.size() == typeList.size();
    //         for (int i = 0; i < bcIndex.size(); i++) {
    //             String method = methodList.get(i);
    //             int intVal = bcIndex.get(i);
    //             String strVal = typeList.get(i);
    //             System.out.println("method: " + method + "Int: " + intVal + ", String: " + strVal);
    //         }
    //     super.visitAttribute(attr);
    //     }
    // }
    
    @Override
    public void visitEnd() {
        analyzeMethod(className, methodNode);
        super.visitEnd();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private void analyzeMethod(String className, MethodNode methodNode) {
        try {
            // BasicInterpreter verifier = new BasicInterpreter();
            // Analyzer<BasicValue> analyzer = new Analyzer<>(verifier);
            // Frame<BasicValue>[] frames = analyzer.analyze(className, methodNode);

            // org.objectweb.asm.tree.analysis.SimpleVerifier verifier = 
            //     new org.objectweb.asm.tree.analysis.SimpleVerifier(
                    
            //     );
            // Analyzer<BasicValue> analyzer = new Analyzer<>(verifier);
            // Frame<BasicValue>[] frames = analyzer.analyze(className, methodNode);

            com.asm.stackAnalysis.boxing.BoxingSimpleVerifier verifier 
                = new com.asm.stackAnalysis.boxing.BoxingSimpleVerifier();
            Analyzer<BasicValue> analyzer = new Analyzer<>(verifier);
            Frame<BasicValue>[] frames = analyzer.analyze(className, methodNode);

            InsnList instrs = methodNode.instructions;

            System.out.print("in Method Visitor Analyze method, Method: " + methodNode.name + methodNode.desc);
            System.out.println("  in Class: " + className);
            List<String> instrStrLst = instrStrLst(methodNode);
            System.out.println("bcIndex: " + bcIndex);
            System.out.println("typeList: " + typeList);
            // System.out.println(instrStrLst);
            for (int i = 0; i < instrs.size(); i++) {
                AbstractInsnNode insn = instrs.get(i);
                String instrStr = instrStrLst.get(i);
                if (insn instanceof LabelNode) {
                    // System.out.print(" ");
                    System.out.printf("[%3d]  %-64.65s", i, instrStr);
                } else {
                    // System.out.print("  ");
                    System.out.printf("[%3d]   %-63.65s", i, instrStr);
                }
                
                // System.out.printf("%-30.30s", instrStr);
                Frame<BasicValue> frame = frames[i];
                if (frame == null) {
                    System.out.println(" - Frame is null");
                    continue;
                }
                List<String> stack = new ArrayList<>();
                for (int j = 0; j < frame.getStackSize(); j++) {
                    BasicValue value = frame.getStack(j);
                    // stack.add(value == null ? "null" : getTypeName(value.getType()));
                    stack.add(value == null ? "null" : value.toString());
                }
                System.out.print(" - Stack: " + stack);
                if (containAttribute && bcIndex.contains(i)){
                    int idx = bcIndex.indexOf(i);
                    String typeHint = typeList.get(idx);
                    System.out.print("  --Type Hint: " + typeHint);
                }
                System.out.println();
            }
        } catch (AnalyzerException e) {
            System.out.println("Analysis failed for method: " + methodNode.name);
            e.printStackTrace();
        }
    }
    private List<String> instrStrLst(MethodNode methodNode) {
        List<String> res = new ArrayList<>();
        Textifier textifier = new Textifier();
        for (AbstractInsnNode instr : methodNode.instructions) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            TraceMethodVisitor tmv = new TraceMethodVisitor(textifier);
            instr.accept(tmv);
            textifier.print(pw);
            pw.flush();
            res.add(sw.toString().trim());
            textifier.text.clear();
        }
        return res;
    }
    
    @SuppressWarnings("unused")
    private String printInstr(AbstractInsnNode instr) {
        Textifier textifier = new Textifier();
        TraceMethodVisitor tmv = new TraceMethodVisitor(textifier);
        instr.accept(tmv);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        textifier.print(pw);
        pw.flush();
        return sw.toString().trim();
    }

    @SuppressWarnings("unused")
    private String getTypeName(Type type) {
        if (type == null) return "uninitialized";
        return type.getClassName();
    }    
}