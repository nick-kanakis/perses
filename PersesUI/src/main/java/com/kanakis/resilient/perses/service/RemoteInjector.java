package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.core.MBeanWrapper;

public class RemoteInjector extends Injector implements InjectorService {

    private static final String JMX_URL_PATTERN = "service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi";
    private String host;
    private Integer port;

    RemoteInjector(String host, Integer port) throws Exception {
        super(MBeanWrapper.getRemoteMBean(String.format(JMX_URL_PATTERN, host, port)));
        this.host = host;
        this.port = port;
    }

    String getHost() {
        return host;
    }

    Integer getPort() {
        return port;
    }
}
