package com.kanakis.resilient.perses.core;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AgentLoader {

    public static void main(String[] args) throws IOException {

        String applicationName = "procurement";

        if (args != null && args.length > 0) {
            applicationName = args[0];
        }

        System.out.println("Using applicationName: " + applicationName);

        run(applicationName,"");
    }

    /**
     * Creates a java agent jar and attach it to the target application
     *
     * @param applicationName application to attach the agent
     * @return A reference to the attached VirtualMachine
     */
    public static VirtualMachine run(String applicationName, String jvmPid) throws IOException {

        if(jvmPid.isEmpty() && applicationName.isEmpty()) {
            throw new IllegalArgumentException("Target pid and application name are null");
        }

        System.out.println("Provided Application NAme: "+ applicationName);
        System.out.println("Provided pid: "+ jvmPid);
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
        String agentAbsolutePath = getAbsolutePathOfAgent();
        System.out.println("Attaching to target JVM with PID: " + jvmPid);
        System.out.println("Agent jar path: "+ agentAbsolutePath);

        try {
            VirtualMachine jvm = VirtualMachine.attach(jvmPid);
            if ("true".equals(jvm.getSystemProperties().getProperty("chaos.agent.installed"))) {
                System.out.println("Agent is already attached...");
                return jvm;
            }
            System.out.println("About to load agent");
            jvm.loadAgent(agentAbsolutePath);
            System.out.println("Agent Loaded");

            return jvm;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //todo: find a better way to do it
    private static String getAbsolutePathOfAgent() throws IOException {
        String canonicalPath = new File(".").getCanonicalPath();

        //The "Injector" is added to the canonical path when we run the unit test
        if(canonicalPath.endsWith("Injector"))
            canonicalPath = canonicalPath.substring(0, canonicalPath.length() - "Injector".length());
        return canonicalPath + "/perses-agent.jar";
    }

}
