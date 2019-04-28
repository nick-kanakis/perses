package com.kanakis.resilient.perses.controller;

import com.kanakis.resilient.perses.agent.MethodProperties;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestAPI {
    private final MBeanWrapper mBeanWrapper;

    public RestAPI(MBeanWrapper mBeanWrapper) {
        this.mBeanWrapper = mBeanWrapper;
    }

    //todo: add signature versions
    @PostMapping("/failure")
    public void failure(@RequestBody AttackProperties properties) {
        mBeanWrapper.throwException(properties);
    }

    @PostMapping("/latency")
    public void latency(@RequestBody AttackProperties properties) {
        mBeanWrapper.addLatency(properties);
    }

    @PostMapping("/restore")
    public void restore(@RequestBody AttackProperties properties) {
        mBeanWrapper.restoreMethod(properties);
    }

    //todo: should be GET (?)
    @GetMapping("/getInvoked")
    public List<MethodProperties> getInvokedMethods(@RequestBody AttackProperties properties) throws Throwable {
        return mBeanWrapper.getInvokedMethods(properties);
    }
}
