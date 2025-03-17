package com.asm.archive;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassPrinter extends ClassVisitor{
    public ClassPrinter(){
        super(Opcodes.ASM6);
    }

    @Override
    public void visit(int version, int access, String name,
        String signature, String superName, String[] interfaces){
        System.out.println(name + " extends " + superName + " { ");
    }

    @Override
    public void visitSource(String source, String debug) {
        System.out.println("in visit source");
        System.out.print("source: ");
        System.out.println(source);
        System.out.print("debug: ");
        System.out.println(debug);
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        System.out.println("in outer class");
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible){
        System.out.println("in visit annotations");
        return null;
    }

    @Override
    public void visitAttribute(Attribute attr){
        System.out.println("in visit attribute");
        System.out.println(attr.toString());

    }

    @Override
    public void visitInnerClass(String name, String outerName,
        String innerName, int access) {
    }

}
