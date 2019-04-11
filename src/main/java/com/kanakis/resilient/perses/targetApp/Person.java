package com.kanakis.resilient.perses.targetApp;

public class Person {
    public void sayHello(String name) {
        System.out.println("Hello [" + name + "]");
    }

    public void sayHello(int x) {
        System.out.println("Hello [" + x + "]");
    }
}
