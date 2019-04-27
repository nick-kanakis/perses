package com.kanakis.resilient.perses.core;


import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.agent.TransformerServiceMBean;

import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.util.List;

//todo rename
public class MBeanWrapper implements AutoCloseable {

    private TransformerServiceMBean mBean;
    private JMXConnector connector;

    public MBeanWrapper(TransformerServiceMBean mBean, JMXConnector connector) {
        this.mBean = mBean;
        this.connector = connector;
    }

    public TransformerServiceMBean getmBean() {
        return mBean;
    }

    public JMXConnector getConnector() {
        return connector;
    }

    @Override
    public void close() throws IOException {
        if (connector != null) {
            connector.close();
        }
    }


    public void throwException(String className, String methodName) {
        mBean.throwException(className, methodName);
    }

    public void throwException(String className, String methodName, String signature) {
        mBean.throwException(className, methodName, signature);
    }


    public void addLatency(String className, String methodName, long latency) {
        mBean.addLatency(className, methodName, latency);
    }

    public void addLatency(String className, String methodName, String signature, long latency) {
        mBean.addLatency(className, methodName, latency, signature);
    }

    public void restoreMethod(String className, String methodName) {
        mBean.restoreMethod(className, methodName);
    }
    public void restoreMethod(String className, String methodName, String signature) {
        mBean.restoreMethod(className, methodName, signature);
    }

    public List<MethodProperties> getInvokedMethods(String className, String methodName) throws Throwable {
        return mBean.getInvokedMethods(className, methodName);
    }
}
