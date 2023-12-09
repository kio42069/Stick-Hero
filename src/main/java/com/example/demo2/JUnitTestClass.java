package com.example.demo2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JUnitTestClass {

    @Test
    public void testSingleton(){
        StickGenerator stickGenerator1 = StickGenerator.getInstance();
        StickGenerator stickGenerator2 = StickGenerator.getInstance();
        assertEquals("Both StickGenerators are the same object", stickGenerator1, stickGenerator2);
    }
}
