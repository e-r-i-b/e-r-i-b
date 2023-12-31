// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class BankrollService_getCardAbstract2_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct _instance;
    private com.rssl.phizic.web.gate.services.bankroll.generated.Card card_1;
    private java.util.Calendar calendar_2;
    private java.util.Calendar calendar_3;
    private java.lang.Boolean boolean_4;
    private static final int myCARD_1_INDEX = 0;
    private static final int myCALENDAR_2_INDEX = 1;
    private static final int myCALENDAR_3_INDEX = 2;
    private static final int myBOOLEAN_4_INDEX = 3;
    
    public BankrollService_getCardAbstract2_RequestStruct_SOAPBuilder() {
    }
    
    public void setCard_1(com.rssl.phizic.web.gate.services.bankroll.generated.Card card_1) {
        this.card_1 = card_1;
    }
    
    public void setCalendar_2(java.util.Calendar calendar_2) {
        this.calendar_2 = calendar_2;
    }
    
    public void setCalendar_3(java.util.Calendar calendar_3) {
        this.calendar_3 = calendar_3;
    }
    
    public void setBoolean_4(java.lang.Boolean boolean_4) {
        this.boolean_4 = boolean_4;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCARD_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myBOOLEAN_4_INDEX:
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
                case myCARD_1_INDEX:
                    _instance.setCard_1((com.rssl.phizic.web.gate.services.bankroll.generated.Card)memberValue);
                    break;
                case myBOOLEAN_4_INDEX:
                    _instance.setBoolean_4((java.lang.Boolean)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
