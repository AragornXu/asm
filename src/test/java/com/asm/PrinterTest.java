package com.asm;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

import com.asm.archive.ClassPrinter;

public class PrinterTest {

    @Before
    public void b4(){
        System.out.println("-------------------------");
        System.out.println("Starting ClassPrint test 1");
    }

    @After
    public void after(){
        System.out.println("Finished ClassPrint test 1");
    }

    @Test
    public void test1() throws Exception{
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr;
        byte[] in = Files.readAllBytes(Paths.get("/home/j523xu/Desktop/asm/testing/genClasses/Foo.class"));
        //System.out.println(txt);
        //System.out.println(classStream != null ? "Found" : "Not Found");
        cr = new ClassReader(in);
        cr.accept(cp, 0);

    }

    
}
