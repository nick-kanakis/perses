package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.dto.Connection;
import com.kanakis.resilient.perses.enums.InjectorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Component
public class ConnectionService {

    private final ConfigurableListableBeanFactory beanFactory;
    private final ApplicationContext context;

    @Autowired
    public ConnectionService(ConfigurableListableBeanFactory beanFactory, ApplicationContext context) {
        this.beanFactory = beanFactory;
        this.context = context;
    }

    public Connection createConnection(Connection properties) throws Exception {
        if (getInjectorEntrySet().size() > 0) {
            throw new RuntimeException("You already have a opened connection, close it.");
        }

        if (!StringUtils.isEmpty(properties.getAppName()) || !StringUtils.isEmpty(properties.getPid())) {
            LocalInjector localInjector = createLocalInjector(properties);
            beanFactory.registerSingleton(InjectorType.LOCAL.getType(), localInjector);
        } else if (!StringUtils.isEmpty(properties.getHost()) && !StringUtils.isEmpty(properties.getPort())) {
            RemoteInjector remoteInjector = createRemoteInjector(properties);
            beanFactory.registerSingleton(InjectorType.REMOTE.getType(), remoteInjector);
        }

        return getCurrentConnectionFromAPI();
    }

    public RemoteInjector createRemoteInjector(Connection properties) throws Exception {
        return new RemoteInjector(properties.getHost(), properties.getPort());
    }

    public LocalInjector createLocalInjector(Connection properties) throws Exception {
        return new LocalInjector(properties.getAppName(), properties.getPid());
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

    public Connection getCurrentConnectionFromAPI() throws IOException {
        InjectorService currentConnection = getCurrentConnection();
        if(currentConnection instanceof LocalInjector){
            LocalInjector localConnection = (LocalInjector) currentConnection;
            return new Connection(localConnection.getAppName(), localConnection.getPid());
        } else if(currentConnection instanceof RemoteInjector){
            RemoteInjector remoteInjector = (RemoteInjector) currentConnection;
            return new Connection(remoteInjector.getHost(), remoteInjector.getPort());
        }
        return null;
    }

}
