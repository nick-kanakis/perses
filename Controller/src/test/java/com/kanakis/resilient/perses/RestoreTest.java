package com.kanakis.resilient.perses;

import com.kanakis.resilient.perses.core.AttackProperties;
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
        AttackProperties properties = new AttackProperties();
        properties.setClassName("com.kanakis.resilient.perses.targetApp.TargetClass");
        properties.setMethodName("targetMethod");

        mBeanWrapper.throwException(properties);
        mBeanWrapper.restoreMethod(properties);
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_not_delay_after_method_restored() {
        AttackProperties properties = new AttackProperties();
        properties.setClassName("com.kanakis.resilient.perses.targetApp.TargetClass");
        properties.setMethodName("targetMethod");
        properties.setLatency(1000);

        mBeanWrapper.addLatency(properties);
        mBeanWrapper.restoreMethod(properties);
        final long time = LatencyTest.timed(new TargetClass()::targetMethod);

        Assert.assertTrue(time < 1000 );
    }

    @Test
    public void should_not_throw_exception_after_method_restored_when_called_with_defined_signature() {
        AttackProperties properties = new AttackProperties();
        properties.setClassName("com.kanakis.resilient.perses.targetApp.TargetClass");
        properties.setMethodName("targetMethod");
        properties.setSignature("()Z");

        mBeanWrapper.throwException(properties);
        mBeanWrapper.restoreMethod(properties);
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_not_delay_after_method_restored_when_when_called_with_defined_signature() {
        AttackProperties properties = new AttackProperties();
        properties.setClassName("com.kanakis.resilient.perses.targetApp.TargetClass");
        properties.setMethodName("targetMethod");
        properties.setSignature("()Z");
        properties.setLatency(1000);

        mBeanWrapper.addLatency(properties);
        mBeanWrapper.restoreMethod(properties);
        final long time = LatencyTest.timed(new TargetClass()::targetMethod);

        Assert.assertTrue(time < 1000 );
    }
}