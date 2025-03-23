package com.asm;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

import com.asm.archive.ClassPrinter;

public class PrtinerTest {

    @Before
    public void b4(){
        System.out.println("-------------------------");
        System.out.println("Starting Printer test 1");
    }

    @After
    public void after(){
        System.out.println("Finished Printer test 1");
    }

    @Test
    public void test1() throws Exception{
        ClassPrinter cp = new ClassPrinter();
        String path = "/home/j523xu/Desktop/asm/asmProj/genClasses/FooStack.class";
        ClassReader cr = new ClassReader(Files.newInputStream(Paths.get(path)));
        cr.accept(cp, 0);
    }
}
