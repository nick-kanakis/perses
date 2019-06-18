package com.kanakis.resilient.perses.service;

import java.util.List;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;

public class RemoteInjector  implements InjectorService{

    private static final String JMX_URL_PATTERN = "service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi";
    private MBeanWrapper mBeanWrapper;
    private String host;
    private Integer port;

    public RemoteInjector(String host, Integer port) throws Exception {
        this.host = host;
        this.port = port;
        this.mBeanWrapper = MBeanWrapper.getRemoteMBean(String.format(JMX_URL_PATTERN, host, port));
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

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
