// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.util.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class GateInfoService_PortType_getCardInputMode_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.util.generated.GateInfoService_PortType_getCardInputMode_RequestStruct _instance;
    private com.rssl.phizic.web.gate.services.util.generated.Office Office_1;
    private static final int myOFFICE_1_INDEX = 0;
    
    public GateInfoService_PortType_getCardInputMode_RequestStruct_SOAPBuilder() {
    }
    
    public void setOffice_1(com.rssl.phizic.web.gate.services.util.generated.Office Office_1) {
        this.Office_1 = Office_1;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myOFFICE_1_INDEX:
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
                case myOFFICE_1_INDEX:
                    _instance.setOffice_1((com.rssl.phizic.web.gate.services.util.generated.Office)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.util.generated.GateInfoService_PortType_getCardInputMode_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
