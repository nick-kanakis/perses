package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.MBeanWrapper;

import java.util.Objects;

public class LocalInjector extends Injector implements InjectorService {
    private String appName;
    private String pid;

    LocalInjector(String appName, String pid) throws Exception {
        super( MBeanWrapper.getMBean(Objects.requireNonNull(AgentLoader.run(appName, pid))));
        this.appName = appName;
        this.pid = pid;
    }

    String getAppName() {
        return appName;
    }

    String getPid() {
        return pid;
    }
}
