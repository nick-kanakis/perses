package com.kanakis.resilient.perses.restore;

import com.kanakis.resilient.perses.controller.AgentLoader;
import com.kanakis.resilient.perses.controller.MBeanWrapper;
import com.kanakis.resilient.perses.targetApp.TargetClass;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import static com.kanakis.resilient.perses.latency.TestLatency.timed;

public class TestRestore {
    private static MBeanWrapper mBeanWrapper;

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
    public void should_not_throw_exception_after_method_restored() {
        mBeanWrapper.throwException("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod");
        mBeanWrapper.restoreMethod("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod");
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_not_delay_after_method_restored() {
        mBeanWrapper.addLatency("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod", 3000);
        mBeanWrapper.restoreMethod("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod");
        final long time = timed(new TargetClass()::targetMethod);

        Assert.assertTrue(time < 1000 );

    }
}
