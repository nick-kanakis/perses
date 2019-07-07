package com.kanakis.resilient.perses.service;

import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.dto.Method;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InjectorService {
    void throwException(AttackProperties properties);

    void addLatency(AttackProperties properties);

    void restoreMethod(AttackProperties properties);

    List<Method> getInvokedMethods(AttackProperties properties) throws Throwable;

    List<Method> getMethodsInvokedByClass(String classPath) throws Throwable;
}
