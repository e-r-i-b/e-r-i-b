// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.deposits.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class DepositService_getDepositAbstract2_RequestStruct2_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.deposits.generated.DepositService_getDepositAbstract2_RequestStruct2 _instance;
    private com.rssl.phizic.web.gate.services.deposits.generated.Deposit deposit_1;
    private java.util.Calendar calendar_2;
    private java.util.Calendar calendar_3;
    private static final int myDEPOSIT_1_INDEX = 0;
    private static final int myCALENDAR_2_INDEX = 1;
    private static final int myCALENDAR_3_INDEX = 2;
    
    public DepositService_getDepositAbstract2_RequestStruct2_SOAPBuilder() {
    }
    
    public void setDeposit_1(com.rssl.phizic.web.gate.services.deposits.generated.Deposit deposit_1) {
        this.deposit_1 = deposit_1;
    }
    
    public void setCalendar_2(java.util.Calendar calendar_2) {
        this.calendar_2 = calendar_2;
    }
    
    public void setCalendar_3(java.util.Calendar calendar_3) {
        this.calendar_3 = calendar_3;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myDEPOSIT_1_INDEX:
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
                case myDEPOSIT_1_INDEX:
                    _instance.setDeposit_1((com.rssl.phizic.web.gate.services.deposits.generated.Deposit)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.deposits.generated.DepositService_getDepositAbstract2_RequestStruct2)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
