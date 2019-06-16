package com.kanakis.resilient.perses.core;


import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.agent.TransformerServiceMBean;
import com.sun.tools.attach.VirtualMachine;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.List;

public class MBeanWrapper implements AutoCloseable {

    private TransformerServiceMBean mBean;
    private JMXConnector connector;

    private MBeanWrapper(TransformerServiceMBean mBean, JMXConnector connector) {
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
            mBean.throwException(properties.getClassPath(),
                    properties.getMethodName(),
                    properties.getRate(),
                    properties.getException());
        else
            mBean.throwException(properties.getClassPath(),
                    properties.getMethodName(),
                    properties.getSignature(),
                    properties.getRate(),
                    properties.getException());
    }

    public void addLatency(AttackProperties properties) {
        if (properties.getSignature() == null || properties.getSignature().isEmpty())
            mBean.addLatency(properties.getClassPath(),
                    properties.getMethodName(),
                    properties.getLatency(),
                    properties.getRate());
        else
            mBean.addLatency(properties.getClassPath(),
                    properties.getMethodName(),
                    properties.getSignature(),
                    properties.getLatency(),
                    properties.getRate());
    }

    public void restoreMethod(AttackProperties properties) {
        if (properties.getSignature() == null || properties.getSignature().isEmpty())
            mBean.restoreMethod(properties.getClassPath(),
                    properties.getMethodName());
        else
            mBean.restoreMethod(properties.getClassPath(),
                    properties.getMethodName(),
                    properties.getSignature());
    }

    public List<MethodProperties> getInvokedMethods(AttackProperties properties) throws Throwable {
        if (properties.getSignature() == null || properties.getSignature().isEmpty())
            return mBean.getInvokedMethods(properties.getClassPath(),
                    properties.getMethodName());
        else
            return mBean.getInvokedMethods(properties.getClassPath(),
                    properties.getMethodName(),
                    properties.getSignature());
    }

    public List<MethodProperties> getMethodsOfClass(String classPath) throws Throwable {
        return mBean.getMethodsOfClass(classPath);
    }

    /**
     * Return the MBean that is used to manipulate the agent
     *
     * @param jvm the VirtualMachine
     * @throws Exception
     */
    public static MBeanWrapper getMBean(VirtualMachine jvm) throws Exception {
        String connectorAddress = jvm.startLocalManagementAgent();
        return getRemoteMBean(connectorAddress);
    }

    /**
     * Return the remote Bean that is used to manipulate the agent
     *
     * @param connectorAddress the address of the connector server to connect to
     * @throws Exception
     */
    public static MBeanWrapper getRemoteMBean(String connectorAddress) throws Exception {
        JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(connectorAddress));
        ObjectName on = new ObjectName("transformer:service=ChaosTransformer");
        MBeanServerConnection server = connector.getMBeanServerConnection();
        return new MBeanWrapper(JMX.newMBeanProxy(server, on, TransformerServiceMBean.class), connector);
    }
}
