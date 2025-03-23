package com.asm.archive;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class ClassPrinter extends ClassVisitor{
    public ClassPrinter(){
        super(Opcodes.ASM9);
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
        System.out.println("in outer class: " + owner + " name: " + name + " desc: " + desc);
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
            System.out.println("in visit inner class");
            System.out.println("name: " + name + " outerName: " + outerName + " innerName: " + innerName + " access: " + access);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc,
        String signature, Object value){
            System.out.println("in visit field");
            System.out.println("access: " + access + " name: " + name + " desc: " + desc + " signature: " + signature + " value: " + value);
            return super.visitField(access, name, desc, signature, value);
    }

}
