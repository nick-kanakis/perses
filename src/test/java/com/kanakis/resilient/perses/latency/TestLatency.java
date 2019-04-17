package com.kanakis.resilient.perses.latency;

import com.kanakis.resilient.perses.controller.AgentLoader;
import com.kanakis.resilient.perses.controller.MBeanWrapper;
import com.kanakis.resilient.perses.targetApp.TargetClass;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class TestLatency {
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
    public void should_add_latency() {
        mBeanWrapper.addLatency("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod", 3000);
        final long time = timed(new TargetClass()::targetMethod);

        Assert.assertTrue(time > 3000 && time < 5000);
    }

    public static long timed(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - startTime;
    }
}
