package com.asm;

/* 
 * The methods of the ClassVisitor class must be called in the following order,
    specified in the Javadoc of this class:
        visit visitSource? visitOuterClass? ( visitAnnotation | visitAttribute )*
        ( visitInnerClass | visitField | visitMethod )*
        visitEnd


         These methods must be
        called in the following order (with some additional constraints specified in the
        Javadoc of the MethodVisitor interface):
            visitAnnotationDefault?
            ( visitAnnotation | visitParameterAnnotation | visitAttribute )*
            ( visitCode
            ( visitTryCatchBlock | visitLabel | visitFrame | visitXxxInsn |
            visitLocalVariable | visitLineNumber )*
            visitMaxs
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class Notes {
    
}
