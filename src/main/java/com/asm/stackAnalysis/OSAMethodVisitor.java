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
import org.objectweb.asm.tree.analysis.SimpleVerifier;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

public class OSAMethodVisitor extends MethodVisitor {
    private String className;
    private MethodNode methodNode;

    public OSAMethodVisitor(int api) {
        super(api);
    }
    public OSAMethodVisitor(int api, MethodNode methodNode, String className) {
        super(api, methodNode);
        this.methodNode = methodNode;
        this.className = className;
    }
    
    @Override
    public void visitEnd() {
        analyzeMethod(className, methodNode);
        super.visitEnd();
    }

    private static void analyzeMethod(String className, MethodNode methodNode) {
        try {
            Analyzer<BasicValue> analyzer = new Analyzer<>(new SimpleVerifier());
            Frame<BasicValue>[] frames = analyzer.analyze(className, methodNode);
            InsnList instrs = methodNode.instructions;

            System.out.print("Method: " + methodNode.name + methodNode.desc);
            System.out.println("  in Class: " + className);
            List<String> instrStrLst = instrStrLst(methodNode);
            // System.out.println(instrStrLst);
            for (int i = 0; i < instrs.size(); i++) {
                AbstractInsnNode insn = instrs.get(i);
                String instrStr = instrStrLst.get(i);
                if (insn instanceof LabelNode) {
                    System.out.print(" ");
                    System.out.printf("%-64.65s", instrStr);
                } else {
                    System.out.print("  ");
                    System.out.printf("%-63.65s", instrStr);
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
                    stack.add(value == null ? "null" : getTypeName(value.getType()));
                }
                System.out.println(" - Stack: " + stack);
            }
        } catch (AnalyzerException e) {
            System.out.println("Analysis failed for method: " + methodNode.name);
            e.printStackTrace();
        }
    }
    private static List<String> instrStrLst(MethodNode methodNode) {
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
    private static String printInstr(AbstractInsnNode instr, Textifier textifier2) {
        // StringWriter sw = new StringWriter();
        // Printer printer = new Textifier();
        // TraceMethodVisitor tmv = new TraceMethodVisitor(printer);
        // instr.accept(tmv);
        // return sw.toString();
        Textifier textifier = new Textifier();
        TraceMethodVisitor tmv = new TraceMethodVisitor(textifier2);
        instr.accept(tmv);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        textifier2.print(pw);
        pw.flush();
        return sw.toString().trim();
    }

    private static String getTypeName(Type type) {
        if (type == null) return "uninitialized";
        return type.getClassName();
    }
    
}
