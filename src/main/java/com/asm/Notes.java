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


        must be called in the following order, which reflects the previous grammar
        rules (note that two of them return a SignatureVisitor: this is due to the
        recursive definition of type signatures):
                visitBaseType | visitArrayType | visitTypeVariable |
                ( visitClassType visitTypeArgument*
                ( visitInnerClassType visitTypeArgument* )* visitEnd ) )
        The methods used to visit method signatures are the following:
                ( visitFormalTypeParameter visitClassBound? visitInterfaceBound* )*
                visitParameterType* visitReturnType visitExceptionType*
        Finally the methods used to visit class signatures are:
                ( visitFormalTypeParameter visitClassBound? visitInterfaceBound* )*
                visitSuperClass visitInterface*
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
