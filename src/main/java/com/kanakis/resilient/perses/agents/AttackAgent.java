package com.kanakis.resilient.perses.agents;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;

public class AttackAgent {

    public static void agentmain(String agentArguments, Instrumentation instrumentation) throws Exception {
        System.out.println("Installing AgentMain...");
        TransformerService ts = new TransformerService(instrumentation);
        ObjectName on = new ObjectName("transformer:service=ChaosTransformer");

        // Could be a different MBeanServer. If so, pass a JMX Default Domain Name in agentArgs
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(ts, on);

        Thread.sleep(500);
        ts.transformClass("com.kanakis.resilient.perses.targetApp.Person", "sayHello", "latency", 30000);

        // todo: check the chaos.agent.installed property before attaching the agent
        // Set this property so the installer knows we're already here
        System.setProperty("chaos.agent.installed", "true");
        System.out.println("AgentMain Installed");
    }


}
