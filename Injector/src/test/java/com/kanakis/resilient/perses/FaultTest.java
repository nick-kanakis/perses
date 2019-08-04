package com.kanakis.resilient.perses;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.kanakis.resilient.perses.testApp.TargetClass;
import com.sun.tools.attach.VirtualMachine;

public class FaultTest {

    private static MBeanWrapper mBeanWrapper;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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
    public void should_throw_OutOfMemoryError() {
        expectedEx.expect(OutOfMemoryError.class);
        expectedEx.expectMessage("This is an injected exception by Perses");

        AttackProperties properties = new AttackProperties();
        properties.setClassPath("com.kanakis.resilient.perses.testApp.TargetClass");
        properties.setMethodName("targetMethod");

        mBeanWrapper.throwException(properties);
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_throw_a_IllegalArgumentException() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("This is an injected exception by Perses");

        AttackProperties properties = new AttackProperties();
        properties.setClassPath("com.kanakis.resilient.perses.testApp.TargetClass");
        properties.setMethodName("targetMethod");
        properties.setException("IllegalArgumentException");

        mBeanWrapper.throwException(properties);
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_throw_a_NullPointerException() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("This is an injected exception by Perses");

        AttackProperties properties = new AttackProperties();
        properties.setClassPath("com.kanakis.resilient.perses.testApp.TargetClass");
        properties.setMethodName("targetMethod");
        properties.setSignature("()Z");
        properties.setException("NullPointerException");

        mBeanWrapper.throwException(properties);
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_throw_OutOfMemoryError_when_called_with_defined_signature() {
        expectedEx.expect(OutOfMemoryError.class);
        expectedEx.expectMessage("This is an injected exception by Perses");

        AttackProperties properties = new AttackProperties();
        properties.setClassPath("com.kanakis.resilient.perses.testApp.TargetClass");
        properties.setMethodName("targetMethod");
        properties.setSignature("()Z");

        mBeanWrapper.throwException(properties);
        TargetClass c = new TargetClass();
        c.targetMethod();
    }
}
