package com.kanakis.resilient.perses.testApp;

public class TargetClass {
    public boolean targetMethod() {
        return helper();
    }

    private boolean helper() {
        return true;
    }
}
