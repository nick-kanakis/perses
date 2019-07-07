# Perses
[![Build Status](https://travis-ci.org/nicolasmanic/perses.svg?branch=master)](https://travis-ci.org/nicolasmanic/perses)
[![License](https://img.shields.io/pypi/l/ansicolortags.svg)](https://github.com/nicolasmanic/perses/blob/master/LICENSE)
[![Gitter](https://badges.gitter.im/perses-app/community.svg)](https://gitter.im/perses-app/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## A project to cause (controlled) destruction to a jvm application 

> **Perses** (Ancient Greek: Πέρσης) was the Titan god of destruction. His name is derived from the Ancient Greek word perthō ("to destroy")


### What is the goal.

Perses allows you to dynamically inject failure/latency at the bytecode level with surgical precision, without the need to add any dependency or even restart/deploy the target app, just load 2 jars at the same enviroment the target JVM is running and run a simple command `java -jar perses-injector.jar <Target Application name>`. 

Perses is designed with [Principles of Chaos Engineering][PoC] in mind and in order to enable developpers and QAs to easily reproduce & debug tricky production issues. 

[PoC]: http://principlesofchaos.org/

For more information about how **Perses** works under the hood, how to set everything up & how to use it please visit 
the [wiki](https://github.com/nicolasmanic/perses/wiki).

### Perses status

Perses is under development. Please feel free to raise any issues you might encounter.  

| JDK Version | Status | Release |
| :---: | :--- | :---: |
| 8  | Working  | [0.0.8](https://github.com/nicolasmanic/perses/releases/tag/0.0.8) |
| 9  | Working  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk9) |
| 10  | Working  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk10) |  
| 11 | Working  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk11) |
| 12 | Working  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk12) |
