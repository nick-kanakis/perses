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


    public void throwException(AttackProperties properties) {
        if (properties.getSignature() == null || properties.getSignature().isEmpty())
            mBean.throwException(properties.getClassName(),
                    properties.getMethodName());
        else
            mBean.throwException(properties.getClassName(),
                    properties.getMethodName(),
                    properties.getSignature());
    }

    public void addLatency(AttackProperties properties) {
        if (properties.getSignature() == null || properties.getSignature().isEmpty())
            mBean.addLatency(properties.getClassName(),
                    properties.getMethodName(),
                    properties.getLatency());
        else
            mBean.addLatency(properties.getClassName(),
                    properties.getMethodName(),
                    properties.getSignature(),
                    properties.getLatency());
    }

    public void restoreMethod(AttackProperties properties) {
        if (properties.getSignature() == null || properties.getSignature().isEmpty())
            mBean.restoreMethod(properties.getClassName(),
                    properties.getMethodName());
        else
            mBean.restoreMethod(properties.getClassName(),
                    properties.getMethodName(),
                    properties.getSignature());
    }


    public List<MethodProperties> getInvokedMethods(AttackProperties properties) throws Throwable {
        if (properties.getSignature() == null || properties.getSignature().isEmpty())
            return mBean.getInvokedMethods(properties.getClassName(),
                    properties.getMethodName());
        else
            return mBean.getInvokedMethods(properties.getClassName(),
                    properties.getMethodName(),
                    properties.getSignature());
    }
}
