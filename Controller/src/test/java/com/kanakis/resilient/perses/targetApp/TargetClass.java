package com.kanakis.resilient.perses.targetApp;

public class TargetClass {
    public boolean targetMethod() {
        return helper();
    }

    private boolean helper() {
        return true;
    }
}
