package com.kanakis.resilient.perses.controller;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.service.InjectorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestAPI {
    private final InjectorService injectorService;

    public RestAPI(InjectorService injectorService) {
        this.injectorService = injectorService;
    }

    //todo: add signature versions
    @PostMapping("/failure")
    public void failure(@RequestBody AttackProperties properties) {
        injectorService.throwException(properties);
    }

    @PostMapping("/latency")
    public void latency(@RequestBody AttackProperties properties) {
        injectorService.addLatency(properties);
    }

    @PostMapping("/restore")
    public void restore(@RequestBody AttackProperties properties) {
        injectorService.restoreMethod(properties);
    }

    @GetMapping("/getInvoked")
    public List<MethodProperties> getInvokedMethods(@RequestParam String classPath,
                                                    @RequestParam String methodName,
                                                    @RequestParam(required = false) String signature) throws Throwable {
        AttackProperties properties = new AttackProperties();
        properties.setClassPath(classPath);
        properties.setMethodName(methodName);
        properties.setSignature(signature);
        return injectorService.getInvokedMethods(properties);
    }

    @GetMapping("/getMethodOfClass")
    public List<MethodProperties> getInvokedMethods(@RequestParam String classPath) throws Throwable {

        return injectorService.getMethodsInvokedByClass(classPath);
    }

}
