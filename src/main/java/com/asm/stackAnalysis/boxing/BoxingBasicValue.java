package com.asm.stackAnalysis.boxing;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.analysis.BasicValue;

public class BoxingBasicValue extends BasicValue{
    public final AbstractInsnNode producer;

    public BoxingBasicValue(Type type, AbstractInsnNode producer) {
        super(type);
        this.producer = producer;
    }

    public boolean isBoxedByScala(){
        // System.out.println("called isBoxedByScala");
        if (producer == null || producer.getOpcode() != Opcodes.INVOKESTATIC) {
            return false;
        }
        if (producer instanceof MethodInsnNode){
            MethodInsnNode methodInsnNode = (MethodInsnNode) producer;
            String owner = methodInsnNode.owner;
            String name = methodInsnNode.name;
            return owner.equals("scala/runtime/BoxesRunTime") && name.startsWith("boxTo");
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String init = super.toString();
        if (isBoxedByScala()){
            return init + " (boxed by Scala)";
        } else {
            return init;
        }
    }

}
