package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;

import java.util.List;

public class RemoteInjector  implements InjectorService{

    private MBeanWrapper mBeanWrapper;

    public RemoteInjector(String connectorAddress) throws Exception {
        this.mBeanWrapper = MBeanWrapper.getRemoteMBean(connectorAddress);
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
