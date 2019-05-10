package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.sun.tools.attach.VirtualMachine;
import org.springframework.stereotype.Service;

import java.util.List;

public class LocalInjector implements InjectorService {
    private MBeanWrapper mBeanWrapper;

    public LocalInjector(String appName, String pid) throws Exception {
        VirtualMachine jvm = AgentLoader.run(appName, pid);
        this.mBeanWrapper = MBeanWrapper.getMBean(jvm);
    }

    @Override
    public void throwException(AttackProperties properties) {
        mBeanWrapper.throwException(properties);
    }

    @Override
    public void addLatency(AttackProperties properties) {
        mBeanWrapper.addLatency(properties);
    }

    @Override
    public void restoreMethod(AttackProperties properties) {
        mBeanWrapper.restoreMethod(properties);
    }

    @Override
    public List<MethodProperties> getInvokedMethods(AttackProperties properties) throws Throwable {
        return mBeanWrapper.getInvokedMethods(properties);
    }
}
