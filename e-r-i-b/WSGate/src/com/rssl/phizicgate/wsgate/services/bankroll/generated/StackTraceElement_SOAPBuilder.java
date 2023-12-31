// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class StackTraceElement_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.StackTraceElement _instance;
    private java.lang.String declaringClass;
    private java.lang.String fileName;
    private int lineNumber;
    private java.lang.String methodName;
    private static final int myDECLARINGCLASS_INDEX = 0;
    private static final int myFILENAME_INDEX = 1;
    private static final int myLINENUMBER_INDEX = 2;
    private static final int myMETHODNAME_INDEX = 3;
    
    public StackTraceElement_SOAPBuilder() {
    }
    
    public void setDeclaringClass(java.lang.String declaringClass) {
        this.declaringClass = declaringClass;
    }
    
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }
    
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public void setMethodName(java.lang.String methodName) {
        this.methodName = methodName;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myDECLARINGCLASS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFILENAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMETHODNAME_INDEX:
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
                case myDECLARINGCLASS_INDEX:
                    _instance.setDeclaringClass((java.lang.String)memberValue);
                    break;
                case myFILENAME_INDEX:
                    _instance.setFileName((java.lang.String)memberValue);
                    break;
                case myMETHODNAME_INDEX:
                    _instance.setMethodName((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.StackTraceElement)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
