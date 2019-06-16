package com.kanakis.resilient.perses.handler;

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
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kanakis.resilient.perses.model.ConnectDTO;
import com.kanakis.resilient.perses.service.ConnectionService;
import com.kanakis.resilient.perses.service.InjectorService;
import com.kanakis.resilient.perses.service.LocalInjector;
import com.kanakis.resilient.perses.service.RemoteInjector;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InjectorHandlerTest {

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

    private InjectorHandler injectorHandler;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        initMocks(this);
        connectionService = spy( new ConnectionService(beanFactory, context));
        injectorHandler = new InjectorHandler(context);
    }

    @Test
    public void create_local_connection_and_return_it() throws Exception {
        ConnectDTO properties = new ConnectDTO("", "9999");
        doReturn(localInjector).when(connectionService).createLocalInjector(any());
        connectionService.createConnection(properties);
        connectionService.getCurrentConnection();
        InjectorService injectorService = injectorHandler.getInjectorService();
        Assert.assertNotNull(injectorService);
        Assert.assertTrue(injectorService instanceof LocalInjector);
    }

    @Test
    public void create_remote_connection_and_return_it() throws Exception {
        ConnectDTO properties = new ConnectDTO("localhost", 12345);
        doReturn(remoteInjector).when(connectionService).createRemoteInjector(any());
        connectionService.createConnection(properties);
        InjectorService currentConnection = injectorHandler.getInjectorService();
        Assert.assertNotNull(currentConnection);
        Assert.assertTrue(currentConnection instanceof RemoteInjector);
    }

    @Test
    public void should_return_an_error_because_there_is_no_connection_opened(){
        expectedException.expect(NoSuchBeanDefinitionException.class);
        expectedException.expectMessage("No InjectorType found");
        injectorHandler.getInjectorService();
    }


}