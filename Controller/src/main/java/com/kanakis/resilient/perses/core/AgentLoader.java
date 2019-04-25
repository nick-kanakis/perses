package com.kanakis.resilient.perses.core;

import com.kanakis.resilient.agent.TransformerServiceMBean;
import com.sun.tools.attach.VirtualMachine;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.util.Optional;

public class AgentLoader {

    /**
     * Creates a java agent jar and attach it to the target application
     *
     * @param applicationName application to attach the agent
     * @return Return the MBean that is used to manipulate the agent with metadata
     */
    public static MBeanWrapper run(String applicationName, String jvmPid) {

        if (jvmPid.isEmpty()) {
            Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list()
                    .stream()
                    .filter(jvm -> {
                        System.out.println("jvm: " + jvm.displayName());
                        return jvm.displayName().contains(applicationName);
                    })
                    .findFirst().get().id());

            if (!jvmProcessOpt.isPresent()) {
                System.out.println("Target Application not found");
                return null;
            }
            jvmPid = jvmProcessOpt.get();
        }
        AgentLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String agentFileName = "uber-perses-agent.jar";
        System.out.println("Attaching to target JVM with PID: " + jvmPid);

        try {
            VirtualMachine jvm = VirtualMachine.attach(jvmPid);
            if ("true".equals(jvm.getSystemProperties().getProperty("chaos.agent.installed"))) {
                System.out.println("Agent is already attached...");
                return getMBean(jvm);
            }
            jvm.loadAgent(agentFileName);
            System.out.println("Agent Loaded");
            ObjectName on = new ObjectName("transformer:service=ChaosTransformer");
            System.out.println("Instrumentation Deployed:" + ManagementFactory.getPlatformMBeanServer().isRegistered(on));

            return getMBean(jvm);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return the MBean that is used to manipulate the agent
     *
     * @param jvm the VirtualMachine
     * @throws Exception
     */
    private static MBeanWrapper getMBean(VirtualMachine jvm) throws Exception {
        String connectorAddress = jvm.startLocalManagementAgent();
        JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(connectorAddress));
        ObjectName on = new ObjectName("transformer:service=ChaosTransformer");
        MBeanServerConnection server = connector.getMBeanServerConnection();
        return new MBeanWrapper(JMX.newMBeanProxy(server, on, TransformerServiceMBean.class), connector);
    }

}
