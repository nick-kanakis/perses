package com.kanakis.resilient.perses.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.kanakis.resilient.perses.enums.InjectorType;
import com.kanakis.resilient.perses.model.ConnectionDTO;

@Component
public class ConnectionService {

    private final ConfigurableListableBeanFactory beanFactory;
    private final ApplicationContext context;

    @Autowired
    public ConnectionService(ConfigurableListableBeanFactory beanFactory, ApplicationContext context) {
        this.beanFactory = beanFactory;
        this.context = context;
    }

    public ConnectionDTO createConnection(ConnectionDTO properties) throws Exception {

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

    public RemoteInjector createRemoteInjector(ConnectionDTO properties) throws Exception {
        return new RemoteInjector(properties.getHost(), properties.getPort());
    }

    public LocalInjector createLocalInjector(ConnectionDTO properties) throws Exception {
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

    public ConnectionDTO getCurrentConnectionFromAPI() throws IOException {
        InjectorService currentConnection = getCurrentConnection();
        if(currentConnection instanceof LocalInjector){
            LocalInjector localConnection = (LocalInjector) currentConnection;
            return new ConnectionDTO(localConnection.getAppName(), localConnection.getPid());
        } else if(currentConnection instanceof RemoteInjector){
            RemoteInjector remoteInjector = (RemoteInjector) currentConnection;
            return new ConnectionDTO(remoteInjector.getHost(), remoteInjector.getPort());
        }

        return null;
    }

}
