package com.kanakis.resilient.perses.agent;

import javassist.CannotCompileException;
import javassist.CtMethod;

class RestoreMode implements OperationMode {
    @Override
    public CtMethod generateCode(CtMethod method, TransformProperties properties) throws CannotCompileException {
        return method;
    }
}
