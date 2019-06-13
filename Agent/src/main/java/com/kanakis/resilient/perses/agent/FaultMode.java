package com.kanakis.resilient.perses.agent;

import javassist.CannotCompileException;
import javassist.CtMethod;

class FaultMode implements OperationMode {
    @Override
    public CtMethod generateCode(CtMethod method, TransformProperties properties) throws CannotCompileException {
        String assaultCode = "if (Math.random() <= " + properties.getRate() + ") throw new " + properties.getException() + "(\"This is an injected exception by Perses\");";
        method.insertAfter(assaultCode);

        return method;
    }
}
