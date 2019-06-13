package com.kanakis.resilient.perses.enums;

public enum InjectorType {
    LOCAL("localInjector"), REMOTE("remoteInjector");

    InjectorType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }
}
