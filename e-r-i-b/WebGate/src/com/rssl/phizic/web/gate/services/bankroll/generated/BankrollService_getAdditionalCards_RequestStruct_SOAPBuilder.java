// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class BankrollService_getAdditionalCards_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAdditionalCards_RequestStruct _instance;
    private com.rssl.phizic.web.gate.services.bankroll.generated.Card[] arrayOfCard_1;
    private static final int myARRAYOFCARD_1_INDEX = 0;
    
    public BankrollService_getAdditionalCards_RequestStruct_SOAPBuilder() {
    }
    
    public void setArrayOfCard_1(com.rssl.phizic.web.gate.services.bankroll.generated.Card[] arrayOfCard_1) {
        this.arrayOfCard_1 = arrayOfCard_1;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myARRAYOFCARD_1_INDEX:
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
                case myARRAYOFCARD_1_INDEX:
                    _instance.setArrayOfCard_1((com.rssl.phizic.web.gate.services.bankroll.generated.Card[])memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAdditionalCards_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
