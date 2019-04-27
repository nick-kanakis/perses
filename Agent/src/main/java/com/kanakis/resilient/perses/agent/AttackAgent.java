package com.kanakis.resilient.perses.agent;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;

class AttackAgent {

    public static void agentmain(String agentArguments, Instrumentation instrumentation) throws Exception {
        System.out.println("Installing AgentMain...");
        TransformerService ts = new TransformerService(instrumentation);
        ObjectName on = new ObjectName("transformer:service=ChaosTransformer");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(ts, on);
        System.setProperty("chaos.agent.installed", "true");
        System.out.println("AgentMain Installed");
    }

    public static void premain(String agentArguments, Instrumentation instrumentation) throws Exception {
        agentmain(agentArguments, instrumentation);
    }
}
