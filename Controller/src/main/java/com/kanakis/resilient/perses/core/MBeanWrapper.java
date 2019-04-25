package com.kanakis.resilient.perses.core;


import com.kanakis.resilient.agent.TransformerServiceMBean;

import javax.management.remote.JMXConnector;
import java.io.IOException;

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


    public void addLatency(String className, String methodName, long latency) {
        mBean.addLatency(className, methodName, latency);
    }


    public void restoreMethod(String className, String methodName) {
        mBean.restoreMethod(className, methodName);
    }
}
