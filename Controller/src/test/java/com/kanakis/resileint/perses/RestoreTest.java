package com.kanakis.resileint.perses;

import com.kanakis.resileint.perses.targetApp.TargetClass;
import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import static com.kanakis.resileint.perses.LatencyTest.timed;


public class RestoreTest {
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
        mBeanWrapper.throwException("com.kanakis.resileint.perses.targetApp.TargetClass", "targetMethod");
        mBeanWrapper.restoreMethod("com.kanakis.resileint.perses.targetApp.TargetClass", "targetMethod");
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_not_delay_after_method_restored() {
        mBeanWrapper.addLatency("com.kanakis.resileint.perses.targetApp.TargetClass", "targetMethod", 3000);
        mBeanWrapper.restoreMethod("com.kanakis.resileint.perses.targetApp.TargetClass", "targetMethod");
        final long time = timed(new TargetClass()::targetMethod);

        Assert.assertTrue(time < 1000 );

    }
}
