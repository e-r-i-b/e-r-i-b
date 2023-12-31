// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class ErrorMessage_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.documents.generated.ErrorMessage _instance;
    private java.lang.String errorType;
    private java.lang.String message;
    private java.lang.String regExp;
    private java.lang.String system;
    private static final int myERRORTYPE_INDEX = 0;
    private static final int myMESSAGE_INDEX = 1;
    private static final int myREGEXP_INDEX = 2;
    private static final int mySYSTEM_INDEX = 3;
    
    public ErrorMessage_SOAPBuilder() {
    }
    
    public void setErrorType(java.lang.String errorType) {
        this.errorType = errorType;
    }
    
    public void setMessage(java.lang.String message) {
        this.message = message;
    }
    
    public void setRegExp(java.lang.String regExp) {
        this.regExp = regExp;
    }
    
    public void setSystem(java.lang.String system) {
        this.system = system;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myERRORTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMESSAGE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myREGEXP_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySYSTEM_INDEX:
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
                case myERRORTYPE_INDEX:
                    _instance.setErrorType((java.lang.String)memberValue);
                    break;
                case myMESSAGE_INDEX:
                    _instance.setMessage((java.lang.String)memberValue);
                    break;
                case myREGEXP_INDEX:
                    _instance.setRegExp((java.lang.String)memberValue);
                    break;
                case mySYSTEM_INDEX:
                    _instance.setSystem((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.documents.generated.ErrorMessage)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
