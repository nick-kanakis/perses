package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.kanakis.resilient.perses.dto.Method;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InjectorTest {

    @Mock private MBeanWrapper mBeanWrapper;

    private Injector injector;
    private AttackProperties attackProperties;
    private MethodProperties methodProperties;

    @Before
    public void setUp() throws Exception {
        injector = new Injector(mBeanWrapper);
        attackProperties = new AttackProperties();
        attackProperties.setClassPath("my.test.path");
        attackProperties.setMethodName("testMethod");
        attackProperties.setSignature("()Z");

        methodProperties = new MethodProperties("my.test.path", "testMethod", "()Z");
    }

    @Test
    public void should_store_instrumented_methods_locally_when_throw_exception_is_called() throws Throwable {
        when(mBeanWrapper.getInvokedMethods(any())).thenReturn(Lists.newArrayList(methodProperties));
        injector.throwException(attackProperties);

        final List<Method> invokedMethods = injector.getInvokedMethods(attackProperties);

        assertEquals(1, invokedMethods.size());
        assertTrue(invokedMethods.get(0).isInstrumented());
        assertEquals(attackProperties, invokedMethods.get(0).getProperties());
    }

    @Test
    public void should_store_instrumented_methods_locally_when_add_latency_is_called() throws Throwable {
        when(mBeanWrapper.getInvokedMethods(any())).thenReturn(Lists.newArrayList(methodProperties));
        injector.addLatency(attackProperties);

        final List<Method> invokedMethods = injector.getInvokedMethods(attackProperties);

        assertEquals(1, invokedMethods.size());
        assertTrue(invokedMethods.get(0).isInstrumented());
        assertEquals(attackProperties, invokedMethods.get(0).getProperties());
    }

    @Test
    public void should_remove_instrumented_method_from_local_memory_when_restore_method_is_called() throws Throwable {
        when(mBeanWrapper.getInvokedMethods(any())).thenReturn(Lists.newArrayList(methodProperties));
        injector.addLatency(attackProperties);
        injector.restoreMethod(attackProperties);

        final List<Method> invokedMethods = injector.getInvokedMethods(attackProperties);

        assertFalse(invokedMethods.get(0).isInstrumented());
    }

}
