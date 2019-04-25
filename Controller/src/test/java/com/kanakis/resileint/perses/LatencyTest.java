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

public class LatencyTest {
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
        mBeanWrapper.addLatency("com.kanakis.resileint.perses.targetApp.TargetClass", "targetMethod", 3000);
        final long time = timed(new TargetClass()::targetMethod);

        Assert.assertTrue(time > 3000 && time < 5000);
    }

    public static long timed(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - startTime;
    }

}
