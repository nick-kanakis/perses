package com.kanakis.resilient.perses;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.sun.tools.attach.VirtualMachine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class GetMethodsOfClass {
    private static MBeanWrapper mBeanWrapper;

    @BeforeClass
    public static void init() throws Exception {
        final VirtualMachine jvm = AgentLoader.run("", ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        mBeanWrapper = MBeanWrapper.getMBean(jvm);
    }

    @AfterClass
    public static void tearDown() throws IOException {
        if (mBeanWrapper != null)
            mBeanWrapper.close();
    }

    @Test
    public void should_get_methods_of_class() throws Throwable {
        List<MethodProperties> res = mBeanWrapper.getMethodsOfClass("com.kanakis.resilient.perses.testApp.TargetClass");

        assertTrue(res.size() == 4);
    }

}
