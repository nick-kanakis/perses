# Perses
[![Build Status](https://travis-ci.org/nicolasmanic/perses.svg?branch=master)](https://travis-ci.org/nicolasmanic/perses)

## A project to cause (controlled) destruction to a jvm application 

> **Perses** (Ancient Greek: Πέρσης) was the Titan god of destruction. His name is derived from the Ancient Greek word perthō ("to destroy")

### What is the goal.

### How it works.

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

