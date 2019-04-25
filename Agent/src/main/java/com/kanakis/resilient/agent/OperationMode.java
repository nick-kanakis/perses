package com.kanakis.resilient.agent;

import javassist.CannotCompileException;
import javassist.CtMethod;

public interface OperationMode {
    CtMethod generateCode(CtMethod method, TransformProperties properties) throws CannotCompileException;
}
