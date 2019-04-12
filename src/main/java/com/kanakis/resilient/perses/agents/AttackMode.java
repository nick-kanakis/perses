package com.kanakis.resilient.perses.agents;

import javassist.CannotCompileException;
import javassist.CtMethod;

public enum AttackMode {
    LATENCY {
        @Override
        public CtMethod generateCode(CtMethod method, long timeToSleep) throws CannotCompileException {
            String assaultCode = "try { Thread.sleep(" + timeToSleep + "l);} catch (InterruptedException e) {}";
            method.insertBefore(assaultCode);

            return method;
        }

        @Override
        public CtMethod generateCode(CtMethod method) throws CannotCompileException {
            //default delay is 20 sec
            String assaultCode = "try { Thread.sleep(20000l);} catch (InterruptedException e) {}";
            method.insertBefore(assaultCode);

            return method;
        }

    },
    FAULT {
        @Override
        public CtMethod generateCode(CtMethod method) throws CannotCompileException {
            String assaultCode = "throw new OutOfMemoryError(\"This is an injected exception by Perses\");";
            method.setBody(assaultCode);

            return method;
        }

        @Override
        public CtMethod generateCode(CtMethod method, long timeToSleep) throws CannotCompileException {
            return generateCode(method);
        }
    };


    public static AttackMode getAttackMode(String mode) {
        return AttackMode.valueOf(mode.toUpperCase());
    }

    public abstract CtMethod generateCode(CtMethod method) throws CannotCompileException;

    public abstract CtMethod generateCode(CtMethod method, long timeToSleep) throws CannotCompileException;

}
