package com.kanakis.resilient;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PersesAttackerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_return_exception() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("This is an injected exception by Perses");

        PersesAttacker.loadAgent()
                .classPath("com.kanakis.resilient.TargetClass")
                .method("method")
                .injectException(Exception.class)
                .attack();

        new TargetClass().method();
    }

    @Test
    public void should_return_NoSuchMethodException() throws Exception {
        expectedException.expect(NoSuchMethodException.class);
        expectedException.expectMessage("This is an injected exception by Perses");

        PersesAttacker.loadAgent()
                .classPath("com.kanakis.resilient.TargetClass")
                .method("method")
                .injectException(NoSuchMethodException.class)
                .attack();

        new TargetClass().method();
    }

    @Test
    public void should_add_latency() throws Exception {
        int latency = 3000;
        PersesAttacker.loadAgent()
                .classPath("com.kanakis.resilient.TargetClass")
                .method("method")
                .injectLatency(latency)
                .attack();

        long startTime = System.currentTimeMillis();
        new TargetClass().method();
        long estimatedTime = System.currentTimeMillis() - startTime;

        assertTrue(estimatedTime >= latency);
    }

}
