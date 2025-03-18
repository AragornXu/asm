package com.asm.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassVisitor;

public class TypeParamExtractorClassVisitor extends ClassVisitor {
    private final boolean debug = false;
    @SuppressWarnings("FieldMayBeFinal")
    private List<String> typeParams = new ArrayList<>();
    public TypeParamExtractorClassVisitor(int api) {
        super(api);
    }

    public List<String> getTypeParams() {
        return typeParams;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
            String[] interfaces) {
        if (signature != null) {
            
            typeParams.addAll(getTypeParams(signature));
            System.out.println("Type Parameters: " + typeParams);
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    private List<String> getTypeParams(String signature) {
        List<String> locTypeParams = new ArrayList<>();
        Pattern pattern = Pattern.compile("<(.*)>");
        if (debug) System.out.println("Signature: " + signature);
        Matcher matcher = pattern.matcher(signature);
        if (matcher.find()) {
            if (debug)  System.out.println("match:" + matcher.group());
            if (debug)  System.out.println("match:" + matcher.group(1));
            String[] params = matcher.group(1).split(";");
            // match:<A:Ljava/lang/Object;B:Ljava/lang/Object;>
            // Type Parameters: [A:Ljava/lang/Object, B:Ljava/lang/Object]
            for (String param : params) {
                String typeParam = param.split(":")[0]; //should be all erased to Object
                locTypeParams.add(typeParam);
            }
        }
        // else {
        //     System.out.println("No match");
        // }
        return locTypeParams;
    }
}
