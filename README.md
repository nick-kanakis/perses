# Perses
[![Build Status](https://travis-ci.org/nicolasmanic/perses.svg?branch=master)](https://travis-ci.org/nicolasmanic/perses)
[![License](https://img.shields.io/pypi/l/ansicolortags.svg)](https://github.com/nicolasmanic/perses/blob/master/LICENSE)
[![Gitter](https://badges.gitter.im/perses-app/community.svg)](https://gitter.im/perses-app/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## A project to cause (controlled) destruction to a jvm application 

> **Perses** (Ancient Greek: Πέρσης) was the Titan god of destruction. His name is derived from the Ancient Greek word perthō ("to destroy")


### Perses status

Perses is under development and not ready for production. We are currently experimenting with different jvm versions and in the near future 
there will be a Perses version for each major JDK release.  

| JDK Version | Status | Release |
| :---: | :--- | :---: |
| 8  | Working  | [0.0.8](https://github.com/nicolasmanic/perses/releases/tag/0.0.8) |
| 9  | Working  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk9) |
| 10  | Working  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk10) |  
| 11 | Working  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk11) |
| 12 | Working  | [0.9.0](https://github.com/nicolasmanic/perses/releases/tag/0.9.0-jdk12) |


### What is the goal.

Perses is a tool designed to give you insides to your jvm application by dynamically injecting failure/latency at the bytecode level
with surgical precision. Perses is designed with [Principles of Chaos Engineering][PoC] in mind.

[PoC]: http://principlesofchaos.org/


For more information about how **Perses** works under the hood, how to set everything up & how to use it please visit 
the [wiki](https://github.com/nicolasmanic/perses/wiki).