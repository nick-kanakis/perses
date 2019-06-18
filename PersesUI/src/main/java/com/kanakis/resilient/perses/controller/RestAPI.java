package com.kanakis.resilient.perses.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.handler.InjectorHandler;
import com.kanakis.resilient.perses.model.ConnectionDTO;
import com.kanakis.resilient.perses.service.ConnectionService;

@RestController
public class RestAPI {
    private final ConnectionService connectionService;
    private final InjectorHandler injectorHandler;

    public RestAPI(ConnectionService connectionService, InjectorHandler injectorHandler) {
        this.connectionService = connectionService;
        this.injectorHandler = injectorHandler;
    }

    @PostMapping("/failure")
    public void failure(@RequestBody AttackProperties properties) {
        injectorHandler.getInjectorService().throwException(properties);
    }

    @PostMapping("/latency")
    public void latency(@RequestBody AttackProperties properties) {
        injectorHandler.getInjectorService().addLatency(properties);
    }

    @PostMapping("/restore")
    public void restore(@RequestBody AttackProperties properties) {
        injectorHandler.getInjectorService().restoreMethod(properties);
    }

    @GetMapping("/getInvoked")
    public List<MethodProperties> getInvokedMethods(@RequestParam String classPath,
                                                    @RequestParam String methodName,
                                                    @RequestParam(required = false) String signature) throws Throwable {
        AttackProperties properties = new AttackProperties();
        properties.setClassPath(classPath);
        properties.setMethodName(methodName);
        properties.setSignature(signature);
        return injectorHandler.getInjectorService().getInvokedMethods(properties);
    }

    @PostMapping("/connect")
    public ResponseEntity<ConnectionDTO> connect(@RequestBody ConnectionDTO properties) throws Exception {
        ConnectionDTO connection = connectionService.createConnection(properties);
        return ResponseEntity.status(HttpStatus.CREATED).body(connection);
    }

    @DeleteMapping("/connect")
    public void closeConnection() {
        connectionService.closeConnection();
    }

    @GetMapping("/checkConnection")
    public ResponseEntity<ConnectionDTO> checkConnection() throws IOException {
        return ResponseEntity.ok().body(connectionService.getCurrentConnectionFromAPI());
    }

    @GetMapping("/getMethodOfClass")
    public List<MethodProperties> getInvokedMethods(@RequestParam String classPath) throws Throwable {
        return injectorHandler.getInjectorService().getMethodsInvokedByClass(classPath);
    }

}
