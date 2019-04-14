package com.kanakis.resilient.perses.agents;

import javassist.CannotCompileException;
import javassist.CtMethod;


public enum OperationMode {
    LATENCY {
        @Override
        public CtMethod generateCode(CtMethod method, TransformProperties properties) throws CannotCompileException {
            String assaultCode = "try { Thread.sleep(" + properties.getLatency() + "l);} catch (InterruptedException e) {}";
            method.insertAfter(assaultCode);

            return method;
        }
    },
    FAULT {
        @Override
        public CtMethod generateCode(CtMethod method, TransformProperties properties) throws CannotCompileException {
            String assaultCode = "if(true) throw new OutOfMemoryError(\"This is an injected exception by Perses\");";
            method.insertAfter(assaultCode);
            return method;
        }

    }, RESTORE {
        @Override
        public CtMethod generateCode(CtMethod method, TransformProperties properties) throws CannotCompileException {
            return method;
        }
    };


    public abstract CtMethod generateCode(CtMethod method, TransformProperties properties) throws CannotCompileException;
}
