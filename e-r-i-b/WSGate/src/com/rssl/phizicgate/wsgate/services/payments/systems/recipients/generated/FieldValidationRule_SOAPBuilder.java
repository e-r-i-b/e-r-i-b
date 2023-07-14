// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class FieldValidationRule_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.FieldValidationRule _instance;
    private java.lang.String errorMessage;
    private java.lang.String fieldValidationRuleType;
    private java.util.Map parameters;
    private static final int myERRORMESSAGE_INDEX = 0;
    private static final int myFIELDVALIDATIONRULETYPE_INDEX = 1;
    private static final int myPARAMETERS_INDEX = 2;
    
    public FieldValidationRule_SOAPBuilder() {
    }
    
    public void setErrorMessage(java.lang.String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public void setFieldValidationRuleType(java.lang.String fieldValidationRuleType) {
        this.fieldValidationRuleType = fieldValidationRuleType;
    }
    
    public void setParameters(java.util.Map parameters) {
        this.parameters = parameters;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myERRORMESSAGE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFIELDVALIDATIONRULETYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPARAMETERS_INDEX:
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
                case myERRORMESSAGE_INDEX:
                    _instance.setErrorMessage((java.lang.String)memberValue);
                    break;
                case myFIELDVALIDATIONRULETYPE_INDEX:
                    _instance.setFieldValidationRuleType((java.lang.String)memberValue);
                    break;
                case myPARAMETERS_INDEX:
                    _instance.setParameters((java.util.Map)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.FieldValidationRule)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
