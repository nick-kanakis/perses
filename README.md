# Perses
[![Build Status](https://travis-ci.org/nicolasmanic/perses.svg?branch=master)](https://travis-ci.org/nicolasmanic/perses)

## A project to cause (controlled) destruction to a jvm application 

> **Perses** (Ancient Greek: Πέρσης) was the Titan god of destruction. His name is derived from the Ancient Greek word perthō ("to destroy")


### Perses status

Perses is under development and not ready for production. We are currently experimenting with different jvm versions and in the near future 
there will be a Perses version for each major JDK release.  

| JDK Version | Status | Release |
| :---: | :--- | :---: |
| 8  | Under development  | - |
| 9  | Working  | 0.0.0 |
| 10  | Working  | [0.0.10](https://github.com/nicolasmanic/perses/releases/tag/0.0.10) |  
| 11 | Working  | [0.0.11](https://github.com/nicolasmanic/perses/releases/tag/0.0.11) |
| 12 | Working  | [0.0.12](https://github.com/nicolasmanic/perses/releases/tag/0.0.12) |


### What is the goal.

Perses is a tool designed to give you insides to your jvm application by dynamically injecting failure/latency at the bytecode level
with surgical precision. Perses is designed with [Principles of Chaos Engineering][PoC] in mind.

[PoC]: http://principlesofchaos.org/

### How it works.

![Overview](https://i.imgur.com/H5uTjD2.png)

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
To connect to a remote application, you will need to expose a JMX port and create a connection using the **host** and the exposed **jmx port**.
##### How to expose a JMX port?
To expose a JMX port you need to add the following JVM parameters:
```properties
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=<JMX_PORT> -Dcom.sun.management.jmxremote.rmi.port=<JMX_PORT> -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
```

##### Container World
If the target app is running inside a container, you will need to load the agent inside the target jvm. This [script](https://github.com/nicolasmanic/perses/blob/master/install_perses.sh) can  automate this step.

##### Kubernetes World
If your application is running in a kubernetes cluster, you need to change your **service.yaml** to expose the chosen JMX port.
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
To connect to a local running application, you need to provide the **application name** **OR** **application PID**.

#### Create a Connection
Perses only allow 1 connection per time, so, you need to choose between [local](#local) or [remotely](#remote).
![Create a Connection](https://i.ibb.co/zV3b2mZ/image-2.png)

#### Search the Methods
You have 2 options when searching for the target method that you want to inject the failure/latency.

- Only fill the **Classpath**.
  - Perses will get all methods of the class that you filled.
- Fill **Method Name**
  - Perses will get only the method that you filled.
  
![Search Methods](https://i.ibb.co/vcrqj84/image-3.png)

#### Inject Failure
To inject a failure, you need to fill which **exception** you want to inject and the **rate***.

#### Inject Latency
To inject a failure, you need to fill the **latency** (milliseconds) you want to inject and the **rate***.

****Note**: The rate value is between 0 and 1 (default), so if you choose 0.5 there is 50% change each time the method is executed
that the selected attack will be triggered*

#### Restore
To restore the default behavior of the method, you can click in the button called **Restore**.

![Inject](https://i.ibb.co/wwTJn7P/image-5.png)

