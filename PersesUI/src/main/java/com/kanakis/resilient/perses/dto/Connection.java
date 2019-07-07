package com.kanakis.resilient.perses.dto;

public class Connection {

    public Connection() {
    }

    public Connection(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public Connection(String appName, String pid) {
        this.appName = appName;
        this.pid = pid;
    }

    private String host;
    private Integer port;
    private String appName;
    private String pid;

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getAppName() {
        return appName;
    }

    public String getPid() {
        return pid;
    }
}
