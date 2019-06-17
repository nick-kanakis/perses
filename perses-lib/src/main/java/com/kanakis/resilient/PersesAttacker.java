package com.kanakis.resilient;

import java.lang.management.ManagementFactory;
import java.util.Objects;

import com.kanakis.resilient.perses.core.AgentLoader;
import com.kanakis.resilient.perses.core.AttackProperties;
import com.kanakis.resilient.perses.core.MBeanWrapper;
import com.sun.tools.attach.VirtualMachine;

public class PersesAttacker {

    private MBeanWrapper mBeanWrapper;

    private PersesAttacker(MBeanWrapper mBeanWrapper) {
        this.mBeanWrapper = mBeanWrapper;
    }

    public static PersesAttacker loadAgent() throws Exception {
        final VirtualMachine jvm = AgentLoader.run("", ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        return new PersesAttacker(MBeanWrapper.getMBean(jvm));
    }

    public PersesAttackerClassPath classPath(String classPath) {
        return new PersesAttackerClassPath(mBeanWrapper, classPath);
    }

    public class PersesAttackerClassPath {
        private MBeanWrapper mBeanWrapper;
        private String classPath;
        private String signature;

        public PersesAttackerClassPath(MBeanWrapper mBeanWrapper, String classPath) {
            this.mBeanWrapper = mBeanWrapper;
            this.classPath = classPath;
        }

        public PersesAttackerStrategy method(String method) {
            return new PersesAttackerStrategy(mBeanWrapper, classPath, method, signature);
        }

        public PersesAttackerClassPath signature(String signature) {
            this.signature = signature;
            return this;
        }
    }

    public class PersesAttackerStrategy {
        private MBeanWrapper mBeanWrapper;
        private String classPath;
        private String method;
        private String signature;
        private double rate = 1.0;

        public PersesAttackerStrategy(MBeanWrapper mBeanWrapper, String classPath, String method, String signature) {
            this.mBeanWrapper = mBeanWrapper;
            this.classPath = classPath;
            this.method = method;
            this.signature = signature;
        }

        public PersesAttackerStrategy rate(double rate) {
            this.rate = rate;
            return this;
        }

        public PersesAttackerFinal injectException(Class exception) {
            return new PersesAttackerFinal(mBeanWrapper, classPath, method, signature, rate, exception.getSimpleName());
        }

        public PersesAttackerFinal injectLatency(long latency) {
            return new PersesAttackerFinal(mBeanWrapper, classPath, method, signature, rate, latency);
        }
    }

    public class PersesAttackerFinal {
        private MBeanWrapper mBeanWrapper;
        private String classPath;
        private String method;
        private String signature;
        private double rate = 1.0;
        private String exception;
        private long latency = 0;

        public PersesAttackerFinal(MBeanWrapper mBeanWrapper, String classPath, String method, String signature, double rate, String exception) {
            commonShadowing(mBeanWrapper, classPath, method, signature, rate);
            this.exception = exception;
        }

        public PersesAttackerFinal(MBeanWrapper mBeanWrapper, String classPath, String method, String signature, double rate, Long latency) {
            commonShadowing(mBeanWrapper, classPath, method, signature, rate);
            this.latency = latency;
        }

        private void commonShadowing(MBeanWrapper mBeanWrapper, String classPath, String method, String signature, double rate) {
            this.mBeanWrapper = mBeanWrapper;
            this.classPath = classPath;
            this.method = method;
            this.signature = signature;
            this.rate = rate;
        }

        public void attack() {
            AttackProperties properties = new AttackProperties(this.classPath, this.method, this.signature, this.latency, this.rate, this.exception);
            if (Objects.nonNull(this.exception)) {
                this.mBeanWrapper.throwException(properties);
            } else {
                this.mBeanWrapper.addLatency(properties);
            }
        }
    }


}
