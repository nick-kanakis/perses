package com.kanakis.resilient.perses;

import com.kanakis.resilient.perses.targetApp.TargetClass;
import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;


public class RestoreTest {
    private static MBeanWrapper mBeanWrapper;

    @BeforeClass
    public static void init() throws IOException {
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
        mBeanWrapper.addLatency("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod", 1000);
        mBeanWrapper.restoreMethod("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod");
        final long time = LatencyTest.timed(new TargetClass()::targetMethod);

        Assert.assertTrue(time < 1000 );
    }

    @Test
    public void should_not_throw_exception_after_method_restored_when_called_defined_signature() {
        mBeanWrapper.throwException("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod", "()Z");
        mBeanWrapper.restoreMethod("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod", "()Z");
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_not_delay_after_method_restored_when_called_defined_signature() {
        mBeanWrapper.addLatency("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod", "()Z", 1000);
        mBeanWrapper.restoreMethod("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod", "()Z");
        final long time = LatencyTest.timed(new TargetClass()::targetMethod);

        Assert.assertTrue(time < 1000 );
    }
}
