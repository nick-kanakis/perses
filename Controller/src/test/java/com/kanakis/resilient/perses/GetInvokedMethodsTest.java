package com.kanakis.resilient.perses;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.kanakis.resilient.perses.targetApp.TargetClass;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class GetInvokedMethodsTest {
    private static MBeanWrapper mBeanWrapper;

    @BeforeClass
    public static void init() throws IOException {
        mBeanWrapper = AgentLoader.run("", ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    }

    @AfterClass
    public static void tearDown() throws IOException {
        if (mBeanWrapper != null)
            mBeanWrapper.close();
    }

    @Test
    public void should_get_invoked_methods() throws Throwable {
        List<MethodProperties> res = mBeanWrapper.getInvokedMethods("com.kanakis.resilient.perses.targetApp.TargetClass", "targetMethod");

        MethodProperties expectedMethodProperties = new MethodProperties("com.kanakis.resilient.perses.targetApp.TargetClass", "helper", "()Z");
        assertTrue(res.contains(expectedMethodProperties));
    }

}
