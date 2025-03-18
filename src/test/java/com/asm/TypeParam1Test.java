package com.asm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.asm.generics.TypeFieldInjector;

public class TypeParam1Test {
    @Before
    public void b4(){
        System.out.println("-------------------------");
        System.out.println("Starting Type Param test 1");
    }

    @After
    public void after(){
        System.out.println("Finished Type Param test 1");
    }

    @Test
    public void test1() throws Exception{
        String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/FooStack";
        TypeFieldInjector.run(path);
    }
}
