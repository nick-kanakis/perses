package com.kanakis.resilient.perses;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import org.junit.AfterClass;
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
        AttackProperties properties = new AttackProperties();
        properties.setClassName("com.kanakis.resilient.perses.testApp.TargetClass");
        properties.setMethodName("targetMethod");

        List<MethodProperties> res = mBeanWrapper.getInvokedMethods(properties);

        MethodProperties expectedMethodProperties = new MethodProperties("com.kanakis.resilient.perses.testApp.TargetClass", "helper", "()Z");
        assertTrue(res.contains(expectedMethodProperties));
    }

    @Test
    public void should_get_invoked_methods_when_called_with_defined_signature() throws Throwable {
        AttackProperties properties = new AttackProperties();
        properties.setClassName("com.kanakis.resilient.perses.testApp.TargetClass");
        properties.setMethodName("targetMethod");
        properties.setSignature("()Z");

        List<MethodProperties> res = mBeanWrapper.getInvokedMethods(properties);

        MethodProperties expectedMethodProperties = new MethodProperties("com.kanakis.resilient.perses.testApp.TargetClass", "helper", "()Z");
        assertTrue(res.contains(expectedMethodProperties));
    }

}
