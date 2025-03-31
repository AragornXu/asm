package com.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.asm.stackAnalysis.OperandStackAnalyzer;

public class OSA1Test {
    @Before
    public void b4(){
        System.out.println("-------------------------");
        System.out.println("Starting OSA test 1");
    }

    @After
    public void after(){
        System.out.println("Finished OSA test 1");
    }

    @Test
    public void test1() throws Exception{
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/archive/FooStacktest$.class");
    }

    @Test
    public void test2() throws Exception{
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/JavaBoxing.class");
    }

    @Test
    public void test3() throws Exception{
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericClass1$.class");
    }

    @Test
    public void test4() throws Exception{
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericClass1$_Modified.class");
    }

    @Test
    public void test5() throws Exception{
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/GenericMethod1.class");
    }

    @Test
    public void test6() throws Exception{
        OperandStackAnalyzer osa = new OperandStackAnalyzer();
        osa.run("/home/j523xu/Desktop/asm/asmProj/genClasses/attributes/testGenericMethod1$_Modified.class");
    }


}
