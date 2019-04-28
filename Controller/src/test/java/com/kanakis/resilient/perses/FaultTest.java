package com.kanakis.resilient.perses;

import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.targetApp.TargetClass;
import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class FaultTest {

    private static MBeanWrapper mBeanWrapper;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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
    public void should_throw_RuntimeException() {
        expectedEx.expect(OutOfMemoryError.class);
        expectedEx.expectMessage("This is an injected exception by Perses");

        AttackProperties properties = new AttackProperties();
        properties.setClassName("com.kanakis.resilient.perses.targetApp.TargetClass");
        properties.setMethodName("targetMethod");

        mBeanWrapper.throwException(properties);
        TargetClass c = new TargetClass();
        c.targetMethod();
    }

    @Test
    public void should_throw_RuntimeException_when_called_with_defined_signature() {
        expectedEx.expect(OutOfMemoryError.class);
        expectedEx.expectMessage("This is an injected exception by Perses");

        AttackProperties properties = new AttackProperties();
        properties.setClassName("com.kanakis.resilient.perses.targetApp.TargetClass");
        properties.setMethodName("targetMethod");
        properties.setSignature("()Z");

        mBeanWrapper.throwException(properties);
        TargetClass c = new TargetClass();
        c.targetMethod();
    }
}
