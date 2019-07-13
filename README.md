# Perses
[![Build Status](https://travis-ci.org/nicolasmanic/perses.svg?branch=master)](https://travis-ci.org/nicolasmanic/perses)
[![License](https://img.shields.io/pypi/l/ansicolortags.svg)](https://github.com/nicolasmanic/perses/blob/master/LICENSE)
[![Gitter](https://badges.gitter.im/perses-app/community.svg)](https://gitter.im/perses-app/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## Cause (controlled) destruction to your jvm application 

> **Perses** (Ancient Greek: Πέρσης) was the Titan god of destruction. His name is derived from the Ancient Greek word perthō ("to destroy")


### What is the goal.

Perses allows you to dynamically inject failure/latency at the bytecode level, without the need to add any dependency or restart/deploy the target app. Just load 2 jars at the same enviroment the target JVM is running and execute `java -jar perses-injector.jar <Target Application name>`. 

Perses is designed to enable developpers and QAs to easily reproduce & debug tricky production issues. 

For more information about how **Perses** works under the hood, how to set everything up & how to use it please visit 
the [wiki](https://github.com/nicolasmanic/perses/wiki).

### Perses status

Perses is under development. Please feel free to raise any issues you might encounter.  

| JDK Version | Release |
| :---: | :---: |
| 8  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk8) |
| 9  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk9) |
| 10 | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk10) |  
| 11 | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk11) |
| 12 | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk12) |

### How to install

All jars are available [here](https://github.com/nicolasmanic/perses/releases). Select the version that matches the target
application.

1. Add `perses-agent.jar` & `perses-injector-jar-with-dependencies.jar` in the same environment as the target application.
2. Execute `java -jar perses-injector.jar <TARGET APPLICATION NAME>`.
3. Execute `java -jar perses.jar`
4. Visit `localhost:8777`


For more information about how **Perses** works under the hood, how to set everything up & how to use it please visit 
the [wiki](https://github.com/nicolasmanic/perses/wiki).
