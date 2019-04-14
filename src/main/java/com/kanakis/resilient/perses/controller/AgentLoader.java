package com.kanakis.resilient.perses.controller;

import com.kanakis.resilient.perses.agents.OperationMode;
import com.kanakis.resilient.perses.agents.ChaosTransformer;
import com.kanakis.resilient.perses.agents.ModifyMethod;
import com.kanakis.resilient.perses.agents.TransformProperties;
import com.kanakis.resilient.perses.agents.TransformerService;
import com.kanakis.resilient.perses.agents.AttackAgent;
import com.kanakis.resilient.perses.agents.TransformerServiceMBean;
import com.kanakis.resilient.perses.targetApp.Person;
import com.sun.tools.attach.VirtualMachine;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class AgentLoader {

    /**
     * The created agent jar file name
     */
    protected static final AtomicReference<String> agentJar = new AtomicReference<String>(null);

    public static void run(String[] args) {
        String applicationName = "AppLauncher";

        //iterate all jvms and get the first one that matches our application name
        Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list()
                .stream()
                .filter(jvm -> {
                    System.out.println("jvm: " + jvm.displayName());
                    return jvm.displayName().contains(applicationName);
                })
                .findFirst().get().id());

        if (!jvmProcessOpt.isPresent()) {
            System.out.println("Target Application not found");
            return;
        }
        String agentFileName = createAgent();
        String jvmPid = jvmProcessOpt.get();
        System.out.println("Attaching to target JVM with PID: " + jvmPid);

        try {
            VirtualMachine jvm = VirtualMachine.attach(jvmPid);
            if ("true".equals(jvm.getSystemProperties().getProperty("chaos.agent.installed"))) {
                System.out.println("Agent is already attached...");
                return;
            }
            jvm.loadAgent(agentFileName);
            System.out.println("Agent Loaded");
            ObjectName on = new ObjectName("transformer:service=ChaosTransformer");
            System.out.println("Instrumentation Deployed:" + ManagementFactory.getPlatformMBeanServer().isRegistered(on));

            manipulateMbean(jvm);

            Person person = new Person();
            for (int i = 0; i < 1000; i++) {
                person.sayHello(i);
                Thread.currentThread().join(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String createAgent() {
        if (agentJar.get() == null) {
            synchronized (agentJar) {
                if (agentJar.get() == null) {
                    FileOutputStream fos = null;
                    JarOutputStream jos = null;
                    try {
                        File tmpFile = File.createTempFile(AttackAgent.class.getName(), ".jar");
                        System.out.println("Temp File:" + tmpFile.getAbsolutePath());
                        tmpFile.deleteOnExit();
                        StringBuilder manifest = new StringBuilder();
                        manifest.append("Manifest-Version: 1.0\nAgent-Class: " + AttackAgent.class.getName() + "\n");
                        manifest.append("Can-Redefine-Classes: true\n");
                        manifest.append("Can-Retransform-Classes: true\n");
                        manifest.append("Premain-Class: " + AttackAgent.class.getName() + "\n");
                        ByteArrayInputStream bais = new ByteArrayInputStream(manifest.toString().getBytes());
                        Manifest mf = new Manifest(bais);
                        fos = new FileOutputStream(tmpFile, false);
                        jos = new JarOutputStream(fos, mf);
                        addClassesToJar(jos, AttackAgent.class,
                                ChaosTransformer.class,
                                ModifyMethod.class,
                                TransformerService.class,
                                TransformerServiceMBean.class,
                                OperationMode.class,
                                TransformProperties.class);
                        jos.flush();
                        jos.close();
                        fos.flush();
                        fos.close();
                        agentJar.set(tmpFile.getAbsolutePath());
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to write Agent installer Jar", e);
                    } finally {
                        if (fos != null) try {
                            fos.close();
                        } catch (Exception e) {
                        }
                    }

                }
            }
        }
        return agentJar.get();
    }

    /**
     * Writes the passed classes to the passed JarOutputStream
     *
     * @param jos     the JarOutputStream
     * @param clazzes The classes to write
     * @throws IOException on an IOException
     */
    protected static void addClassesToJar(JarOutputStream jos, Class<?>... clazzes) throws IOException {
        for (Class<?> clazz : clazzes) {
            jos.putNextEntry(new ZipEntry(clazz.getName().replace('.', '/') + ".class"));
            jos.write(getClassBytes(clazz));
            jos.flush();
            jos.closeEntry();
        }
    }

    /**
     * Returns the bytecode bytes for the passed class
     *
     * @param clazz The class to get the bytecode for
     * @return a byte array of bytecode for the passed class
     */
    public static byte[] getClassBytes(Class<?> clazz) {
        InputStream is = null;
        try {
            is = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class");
            ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available());
            byte[] buffer = new byte[8092];
            int bytesRead = -1;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read class bytes for [" + clazz.getName() + "]", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void manipulateMbean(VirtualMachine jvm) throws Exception {

        String connectorAddress = jvm.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress", null);
        if (connectorAddress == null) {
            // It's not, so install the management agent
            String javaHome = jvm.getSystemProperties().getProperty("java.home");
            File managementAgentJarFile = new File(javaHome + File.separator + "lib" + File.separator + "management-agent.jar");
            jvm.loadAgent(managementAgentJarFile.getAbsolutePath());
            connectorAddress = jvm.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress", null);
            // Now it's installed
        }
        // Now connect and transform the classnames provided in the remaining args.
        JMXConnector connector = null;
        try {
            // This is the ObjectName of the MBean registered when loaded.jar was installed.
            ObjectName on = new ObjectName("transformer:service=ChaosTransformer");
            // Here we're connecting to the target JVM through the management agent
            connector = JMXConnectorFactory.connect(new JMXServiceURL(connectorAddress));
            MBeanServerConnection server = connector.getMBeanServerConnection();

            TransformerServiceMBean transformerServiceMBean = JMX.newMBeanProxy(server, on, TransformerServiceMBean.class);
            transformerServiceMBean.addLatency("com.kanakis.resilient.perses.targetApp.Person", "sayHello", 10000);
            //transformerServiceMBean.throwException("com.kanakis.resilient.perses.targetApp.Person", "sayHello");
            // transformerServiceMBean.restoreMethod("com.kanakis.resilient.perses.targetApp.Person", "sayHello");


        } catch (
                Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connector != null) try {
                connector.close();
            } catch (Exception e) {
            }
        }
    }

}
