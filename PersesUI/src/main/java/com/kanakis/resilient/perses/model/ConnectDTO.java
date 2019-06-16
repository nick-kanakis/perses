package com.kanakis.resilient.perses.model;

public class ConnectDTO {

    public ConnectDTO() {

    }

    public ConnectDTO(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public ConnectDTO(String appName, String pid) {
        this.appName = appName;
        this.pid = pid;
    }

    public ConnectDTO(String host, Integer port, String appName, String pid) {
        this.host = host;
        this.port = port;
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
