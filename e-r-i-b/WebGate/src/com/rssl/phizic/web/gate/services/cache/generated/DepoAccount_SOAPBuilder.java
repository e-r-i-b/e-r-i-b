// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.cache.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class DepoAccount_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.cache.generated.DepoAccount _instance;
    private java.lang.String accountNumber;
    private java.util.Calendar agreementDate;
    private java.lang.String agreementNumber;
    private com.rssl.phizic.web.gate.services.cache.generated.Money debt;
    private java.lang.String id;
    private com.rssl.phizic.web.gate.services.cache.generated.Office office;
    private boolean operationAllowed;
    private java.lang.String state;
    private static final int myACCOUNTNUMBER_INDEX = 0;
    private static final int myAGREEMENTDATE_INDEX = 1;
    private static final int myAGREEMENTNUMBER_INDEX = 2;
    private static final int myDEBT_INDEX = 3;
    private static final int myID_INDEX = 4;
    private static final int myOFFICE_INDEX = 5;
    private static final int myOPERATIONALLOWED_INDEX = 6;
    private static final int mySTATE_INDEX = 7;
    
    public DepoAccount_SOAPBuilder() {
    }
    
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public void setAgreementDate(java.util.Calendar agreementDate) {
        this.agreementDate = agreementDate;
    }
    
    public void setAgreementNumber(java.lang.String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
    
    public void setDebt(com.rssl.phizic.web.gate.services.cache.generated.Money debt) {
        this.debt = debt;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public void setOffice(com.rssl.phizic.web.gate.services.cache.generated.Office office) {
        this.office = office;
    }
    
    public void setOperationAllowed(boolean operationAllowed) {
        this.operationAllowed = operationAllowed;
    }
    
    public void setState(java.lang.String state) {
        this.state = state;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myACCOUNTNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myAGREEMENTNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDEBT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOFFICE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTATE_INDEX:
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
                case myACCOUNTNUMBER_INDEX:
                    _instance.setAccountNumber((java.lang.String)memberValue);
                    break;
                case myAGREEMENTNUMBER_INDEX:
                    _instance.setAgreementNumber((java.lang.String)memberValue);
                    break;
                case myDEBT_INDEX:
                    _instance.setDebt((com.rssl.phizic.web.gate.services.cache.generated.Money)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
                    break;
                case myOFFICE_INDEX:
                    _instance.setOffice((com.rssl.phizic.web.gate.services.cache.generated.Office)memberValue);
                    break;
                case mySTATE_INDEX:
                    _instance.setState((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.cache.generated.DepoAccount)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}