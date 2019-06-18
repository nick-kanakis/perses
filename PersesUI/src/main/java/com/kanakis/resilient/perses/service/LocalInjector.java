package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.sun.tools.attach.VirtualMachine;

import java.util.List;

public class LocalInjector implements InjectorService {
    private MBeanWrapper mBeanWrapper;
    private String appName;
    private String pid;

    public LocalInjector(String appName, String pid) throws Exception {
        this.appName = appName;
        this.pid = pid;
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

    @Override
    public List<MethodProperties> getMethodsInvokedByClass(String classPath) throws Throwable {
        return mBeanWrapper.getMethodsOfClass(classPath);
    }

    public String getAppName() {
        return appName;
    }

    public String getPid() {
        return pid;
    }
}
