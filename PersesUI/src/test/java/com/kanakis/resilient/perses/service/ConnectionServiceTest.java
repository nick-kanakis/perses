package com.kanakis.resilient.perses.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kanakis.resilient.perses.model.ConnectDTO;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConnectionServiceTest {

    @InjectMocks
    private ConnectionService connectionService;

    @Autowired
    private GenericApplicationContext context;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Mock
    private LocalInjector localInjector;

    @Mock
    private RemoteInjector remoteInjector;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        initMocks(this);
        connectionService = spy( new ConnectionService(beanFactory, context));
    }

    @Test
    public void create_local_connection() throws Exception {
        ConnectDTO properties = new ConnectDTO("", "9999");
        doReturn(localInjector).when(connectionService).createLocalInjector(any());
        connectionService.createConnection(properties);
        InjectorService currentConnection = connectionService.getCurrentConnection();
        Assert.assertNotNull(currentConnection);
        Assert.assertTrue(currentConnection instanceof LocalInjector);
    }

    @Test
    public void create_remote_connection() throws Exception {
        ConnectDTO properties = new ConnectDTO("localhost", 12345);
        doReturn(remoteInjector).when(connectionService).createRemoteInjector(any());
        connectionService.createConnection(properties);
        InjectorService currentConnection = connectionService.getCurrentConnection();
        Assert.assertNotNull(currentConnection);
        Assert.assertTrue(currentConnection instanceof RemoteInjector);
    }

    @Test
    public void should_return_error_when_try_to_add_another_connection_with_one_alive() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("You already have a opened connection, close it.");
        ConnectDTO properties = new ConnectDTO("localhost", 12345);
        doReturn(remoteInjector).when(connectionService).createRemoteInjector(any());
        connectionService.createConnection(properties);
        connectionService.createConnection(properties);
    }

    @Test
    public void should_close_a_connection() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("You don't have a opened connection.");
        ConnectDTO properties = new ConnectDTO("localhost", 12345);
        doReturn(remoteInjector).when(connectionService).createRemoteInjector(any());
        connectionService.createConnection(properties);
        connectionService.closeConnection();
        connectionService.getCurrentConnection();
    }

}