package com.kanakis.resilient.perses.agent;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;

public interface OperationMode {

    /**
     * Generates a new {@link javassist.CtMethod} modified based on passed properties.
     *
     * @param method     Unmodified target method
     * @param properties Modification properties
     * @return Modified version of the passed method.
     * @throws CannotCompileException
     */
    CtMethod generateCode(CtMethod method, TransformProperties properties) throws CannotCompileException, NotFoundException;
}
