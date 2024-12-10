package com.nuance.ent.cc.e2e.cloudtest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;


public class DemoTest {

    @Test
    public void demoTest(){
        Assert.assertEquals("Hello World","Hello World");
        System.out.println("Hello World!!");
    }
}
