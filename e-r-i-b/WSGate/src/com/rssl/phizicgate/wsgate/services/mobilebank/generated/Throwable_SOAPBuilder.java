// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Throwable_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.mobilebank.generated.Throwable _instance;
    private java.lang.Object backtrace;
    private com.rssl.phizicgate.wsgate.services.mobilebank.generated.Throwable cause;
    private java.lang.String detailMessage;
    private com.rssl.phizicgate.wsgate.services.mobilebank.generated.StackTraceElement[] stackTrace;
    private static final int myBACKTRACE_INDEX = 0;
    private static final int myCAUSE_INDEX = 1;
    private static final int myDETAILMESSAGE_INDEX = 2;
    private static final int mySTACKTRACE_INDEX = 3;
    
    public Throwable_SOAPBuilder() {
    }
    
    public void setBacktrace(java.lang.Object backtrace) {
        this.backtrace = backtrace;
    }
    
    public void setCause(com.rssl.phizicgate.wsgate.services.mobilebank.generated.Throwable cause) {
        this.cause = cause;
    }
    
    public void setDetailMessage(java.lang.String detailMessage) {
        this.detailMessage = detailMessage;
    }
    
    public void setStackTrace(com.rssl.phizicgate.wsgate.services.mobilebank.generated.StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myBACKTRACE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCAUSE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDETAILMESSAGE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTACKTRACE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    public void construct() {
    }
    
    public void setMember(int index, java.lang.Object memberValue) {
        try {
            switch(index) {
                case myBACKTRACE_INDEX:
                    _instance.setBacktrace((java.lang.Object)memberValue);
                    break;
                case myCAUSE_INDEX:
                    _instance.setCause((com.rssl.phizicgate.wsgate.services.mobilebank.generated.Throwable)memberValue);
                    break;
                case myDETAILMESSAGE_INDEX:
                    _instance.setDetailMessage((java.lang.String)memberValue);
                    break;
                case mySTACKTRACE_INDEX:
                    _instance.setStackTrace((com.rssl.phizicgate.wsgate.services.mobilebank.generated.StackTraceElement[])memberValue);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        }
        catch (java.lang.RuntimeException e) {
            throw e;
        }
        catch (java.lang.Exception e) {
            throw new DeserializationException(new LocalizableExceptionAdapter(e));
        }
    }
    
    public void initialize() {
    }
    
    public void setInstance(java.lang.Object instance) {
        _instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.Throwable)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
