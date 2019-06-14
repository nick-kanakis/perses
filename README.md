# Perses
[![Build Status](https://travis-ci.org/nicolasmanic/perses.svg?branch=master)](https://travis-ci.org/nicolasmanic/perses)

## A project to cause (controlled) destruction to a jvm application 

> **Perses** (Ancient Greek: Πέρσης) was the Titan god of destruction. His name is derived from the Ancient Greek word perthō ("to destroy")

### What is the goal.

Perses is a tool designed to give you insides to your jvm application by dynamically injecting failure/latency at the bytecode level
with surgical precision. Perses is designed with [Principles of Chaos Engineering][PoC] in mind.

[PoC]: http://principlesofchaos.org/

### How it works.

![Overview](https://i.ibb.co/f9hVcxn/image.png)

The tool consist of 3 parts:

#### Agent

Agent is the jar what will be loaded inside the JVM and give you access to the internals of your application, intercept and 
manipulate the bytecode. It exposes a set of MBeans throw JMX so external components can connect and manipulate the bytecode 
on demand.

#### Injector

Injector is responsible for finding the targeted JVM and loading the Agent jar inside the JVM.

#### UI

UI is the only part that does not need to be in the same environment as the target JVM. It connects throw JMX to the Agent
and provides an intuitive ui to instrument the attacks. Finally there is a REST API to connect any external component.

### How to use it.

#### Remote
To use Perses to connect in a remotely application, you will need to expose the port of JMX and create a connection using the **host** and the **jmx port exposed**.
##### How to expose a JMX port?
To expose the JMX port you need some JVM parameters:
```properties
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=<JMX_PORT> -Dcom.sun.management.jmxremote.rmi.port=<JMX_PORT> -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
```

##### Container World
Once you have your app running inside a container, you need to install our agent inside it, but this process is very simple. We have a [script](https://github.com/nicolasmanic/perses/blob/master/install_perses.sh) to do this automatically for you!

##### Kubernetes World
Once you are in the K8S world, you need to change your **service.yaml** to expose the JMX port chosen.
```yaml
spec:
  ports:
    ...
    - port: <JMX-PORT>
      targetPort: <JMX-PORT>
      protocol: TCP
      name: <YOUR-SERVICE-NAME>-jmx
``` 

#### Local
To use Perses to connect in a local application, the only thing that you need is the **application name** **OR** **application PID**.

#### Create a Connection
Perses only allow 1 connection per time, so, you need to choose between [local](#local) or [remotely](#remote).
![Create a Connection](https://i.ibb.co/zV3b2mZ/image-2.png)

#### Search the Methods
You have 2 options to search the method that you want to inject the failure or latency.

- Only fill the **Classpath**.
  - Perses will get all methods of the class that you filled.
- Fill **Method Name**
  - Perses will get only the method that you filled.
  
![Search Methods](https://i.ibb.co/vcrqj84/image-3.png)

#### Inject Failure
To inject a failure, you need to fill which **exception** you want to inject and the **rate***.

#### Inject Latency
To inject a failure, you need to fill the **latency** (milliseconds) you want to inject and the **rate***.

**Note: The rate value is between 0 and 1, so if you choose 0.5 you are saying that Perses need to inject the failure only in 50% of the calls*

#### Restore
To restore the default behavior of the method, you can click in the button called **Restore**.

![Inject](https://i.ibb.co/wwTJn7P/image-5.png)

