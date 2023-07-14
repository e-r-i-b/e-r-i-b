// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.statistics.exception.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class ExternalExceptionInfo_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.wsgate.statistics.exception.generated.ExternalExceptionInfo _instance;
    private java.lang.String application;
    private java.lang.String detail;
    private java.lang.String errorCode;
    private java.lang.String errorText;
    private java.lang.String gate;
    private java.lang.String messageKey;
    private java.lang.String system;
    private java.lang.String tb;
    private static final int myAPPLICATION_INDEX = 0;
    private static final int myDETAIL_INDEX = 1;
    private static final int myERRORCODE_INDEX = 2;
    private static final int myERRORTEXT_INDEX = 3;
    private static final int myGATE_INDEX = 4;
    private static final int myMESSAGEKEY_INDEX = 5;
    private static final int mySYSTEM_INDEX = 6;
    private static final int myTB_INDEX = 7;
    
    public ExternalExceptionInfo_SOAPBuilder() {
    }
    
    public void setApplication(java.lang.String application) {
        this.application = application;
    }
    
    public void setDetail(java.lang.String detail) {
        this.detail = detail;
    }
    
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }
    
    public void setErrorText(java.lang.String errorText) {
        this.errorText = errorText;
    }
    
    public void setGate(java.lang.String gate) {
        this.gate = gate;
    }
    
    public void setMessageKey(java.lang.String messageKey) {
        this.messageKey = messageKey;
    }
    
    public void setSystem(java.lang.String system) {
        this.system = system;
    }
    
    public void setTb(java.lang.String tb) {
        this.tb = tb;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myAPPLICATION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDETAIL_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myERRORCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myERRORTEXT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myGATE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMESSAGEKEY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySYSTEM_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTB_INDEX:
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
                case myAPPLICATION_INDEX:
                    _instance.setApplication((java.lang.String)memberValue);
                    break;
                case myDETAIL_INDEX:
                    _instance.setDetail((java.lang.String)memberValue);
                    break;
                case myERRORCODE_INDEX:
                    _instance.setErrorCode((java.lang.String)memberValue);
                    break;
                case myERRORTEXT_INDEX:
                    _instance.setErrorText((java.lang.String)memberValue);
                    break;
                case myGATE_INDEX:
                    _instance.setGate((java.lang.String)memberValue);
                    break;
                case myMESSAGEKEY_INDEX:
                    _instance.setMessageKey((java.lang.String)memberValue);
                    break;
                case mySYSTEM_INDEX:
                    _instance.setSystem((java.lang.String)memberValue);
                    break;
                case myTB_INDEX:
                    _instance.setTb((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.wsgate.statistics.exception.generated.ExternalExceptionInfo)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
