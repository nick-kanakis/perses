package com.kanakis.resilient.perses.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.kanakis.resilient.perses.enums.InjectorType;
import com.kanakis.resilient.perses.model.ConnectDTO;

@Component
public class ConnectionService {

    private static final String JMX_URL_PATTERN = "service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi";
    private final ConfigurableListableBeanFactory beanFactory;
    private final ApplicationContext context;

    @Autowired
    public ConnectionService(ConfigurableListableBeanFactory beanFactory, ApplicationContext context) {
        this.beanFactory = beanFactory;
        this.context = context;
    }

    public void createConnection(ConnectDTO properties) throws Exception {

        if (getInjectorEntrySet().size() > 0) {
            throw new RuntimeException("You already have a opened connection, close it.");
        }

        if (!StringUtils.isEmpty(properties.getAppName()) || !StringUtils.isEmpty(properties.getPid())) {
            LocalInjector localInjector = new LocalInjector(properties.getAppName(), properties.getPid());
            beanFactory.registerSingleton(InjectorType.LOCAL.getType(), localInjector);
        } else if (!StringUtils.isEmpty(properties.getHost()) && !StringUtils.isEmpty(properties.getPort())) {
            RemoteInjector remoteInjector = new RemoteInjector(String.format(JMX_URL_PATTERN, properties.getHost(), properties.getPort()));
            beanFactory.registerSingleton(InjectorType.REMOTE.getType(), remoteInjector);
        }
    }

    private Set<Map.Entry<String, InjectorService>> getInjectorEntrySet() {
        return context.getBeansOfType(InjectorService.class).entrySet();
    }

    public void closeConnection() {
        String value = getInjectorEntrySet().iterator().next().getKey();
        ((DefaultListableBeanFactory) beanFactory).destroySingleton(value);
    }

    public InjectorService getCurrentConnection() {
        Set<Map.Entry<String, InjectorService>> entries = getInjectorEntrySet();
        if (entries.size() == 0) {
            throw new RuntimeException("You don't have a opened connection.");
        }
        return entries.iterator().next().getValue();
    }

}
