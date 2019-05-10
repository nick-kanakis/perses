package com.kanakis.resilient.perses.controller;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.kanakis.resilient.perses.service.InjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    //todo: should be GET (?)
    @GetMapping("/getInvoked")
    public List<MethodProperties> getInvokedMethods(@RequestBody AttackProperties properties) throws Throwable {
        return injectorService.getInvokedMethods(properties);
    }
}
