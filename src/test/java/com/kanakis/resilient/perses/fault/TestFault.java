package com.kanakis.resilient.perses.fault;

import com.kanakis.resilient.perses.controller.AgentLoader;
import com.kanakis.resilient.perses.controller.MBeanWrapper;
import com.kanakis.resilient.perses.targetApp.TargetClass;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.lang.management.ManagementFactory;


public class TestFault {
    private static MBeanWrapper mBeanWrapper;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void init() {
        mBeanWrapper = AgentLoader.run("", ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    }

    @AfterClass
    public static void tearDown() throws IOException {
        if(mBeanWrapper != null)
            mBeanWrapper.close();
    }


    @Test
    public void should_throw_OutOfMemoryError() {
        expectedEx.expect(OutOfMemoryError.class);
        expectedEx.expectMessage("This is an injected exception by Perses");
        mBeanWrapper.throwException("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod");
        TargetClass c = new TargetClass();
        c.targetMethod();
    }
}
