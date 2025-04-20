package com.asm.stackAnalysis;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.MethodVisitor;
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

import com.asm.generics.attributes.GenericsMethodAttribute;


public class OSAMethodVisitor extends MethodVisitor {
    private String className;
    private MethodNode methodNode;
    private List<Integer> bcIndex;
    private List<String> typeList;
    // private Map<AbstractInsnNode, Integer> offsetMap = new HashMap<>();
    private boolean containClassLevelAttribute = false;
    private Map<Integer, Integer> offsetMap;
    private final boolean printLocal = false;
    private final boolean printStack = false;
    private final boolean printTypeHint = true;

    public OSAMethodVisitor(int api) {
        super(api);
    }
    public OSAMethodVisitor(int api, MethodNode methodNode, String className, 
            List<Integer> bcIndex, List<String> typeList, Map<Integer, Integer> offsetMap) {
        super(api, methodNode);
        this.methodNode = methodNode;
        this.className = className;
        this.bcIndex = bcIndex;
        this.typeList = typeList;
        assert offsetMap != null;
        this.offsetMap = offsetMap;
        if (!bcIndex.isEmpty()) {
            containClassLevelAttribute = true;
        }
    }

    public MethodNode getMethodNode() {
        return methodNode;
    }

    @Override
    public void visitAttribute(Attribute attr){
        System.out.println("in OSAMethodVisitor, visitAttribute");
        if (attr instanceof GenericsMethodAttribute){
            GenericsMethodAttribute gma = (GenericsMethodAttribute) attr;
            System.out.println("GenericsMethodAttribute found:");
            this.bcIndex = gma.getBcIndex();
            this.typeList = gma.getTypeList();
            System.out.println("bcIndex: " + bcIndex);
            System.out.println("typeList: " + typeList);
        }
        super.visitAttribute(attr);
    }
    
    @Override
    public void visitEnd() {
        System.out.println("In OSA Method Visitor, visitEnd");
        analyzeMethod(className, methodNode, offsetMap);
        super.visitEnd();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void analyzeMethod(String className, MethodNode methodNode, 
            Map<Integer, Integer> offsetMap) {
        System.out.println("In OSA Method Visitor, analyzeMethod");
        try {
            // BasicInterpreter verifier = new BasicInterpreter();
            // Analyzer<BasicValue> analyzer = new Analyzer<>(verifier);
            // Frame<BasicValue>[] frames = analyzer.analyze(className, methodNode);

            // org.objectweb.asm.tree.analysis.SimpleVerifier verifier = 
            //     new org.objectweb.asm.tree.analysis.SimpleVerifier();
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
            int offsetMapIndex = 0;
            /* i is not used in the loop since instrs contains instructions like LINENUMBER
               so offsetMapIndex is used to track the index of the instruction
               offsetMapIndex is also used to get the type hints
            */
            for (int i = 0; i < instrs.size(); i++) {
                AbstractInsnNode insn = instrs.get(i);
                if (insn instanceof org.objectweb.asm.tree.LineNumberNode) continue;
                if (insn instanceof org.objectweb.asm.tree.FrameNode) continue;
                String instrStr = instrStrLst.get(i);
                int offset = offsetMap.getOrDefault(offsetMapIndex, -1);
                if (insn instanceof LabelNode) {
                    // System.out.print(" ");
                    System.out.printf("[%3d | offset: %-3d]  %-61.61s", offsetMapIndex, offset, instrStr);
                } else {
                    // System.out.print("  ");
                    System.out.printf("[%3d | offset: %-3d]   %-60.60s", offsetMapIndex, offset, instrStr);
                }
                
                // System.out.printf("%-30.30s", instrStr);
                Frame<BasicValue> frame = frames[i];
                if (frame == null) {
                    if (printStack) System.out.print(" - Frame is null");
                    System.out.println();
                    continue;
                }
                List<String> stack = new ArrayList<>();
                for (int j = 0; j < frame.getStackSize(); j++) {
                    BasicValue value = frame.getStack(j);
                    // stack.add(value == null ? "null" : getTypeName(value.getType()));
                    stack.add(value == null ? "null" : value.toString());
                }
                List<String> locals = new ArrayList<>();
                for (int j = 0; j < frame.getLocals(); j++) {
                    BasicValue value = frame.getLocal(j);
                    locals.add(value == null ? "null" : value.toString());
                }
                if (printStack) System.out.print(" - Stack: " + stack);
                if (printTypeHint && bcIndex.contains(offset) && !(insn instanceof org.objectweb.asm.tree.LabelNode)){
                    int idx = bcIndex.indexOf(offset);
                    String typeHint = typeList.get(idx);
                    //String typeHint = typeList.get(offsetMapIndex);
                    System.out.print("  --Type Hint: " + typeHint);
                }
                System.out.println();
                if (printLocal) System.out.println(" - Locals: " + locals);
                //if (printLocal) System.out.printf("%-50s - Locals: %s%n", "", locals);
                offsetMapIndex++;
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
}