// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class IService1_PortType_revokePayment_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated.IService1_PortType_revokePayment_RequestStruct _instance;
    private java.lang.String value;
    private static final int myVALUE_INDEX = 0;
    
    public IService1_PortType_revokePayment_RequestStruct_SOAPBuilder() {
    }
    
    public void setValue(java.lang.String value) {
        this.value = value;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myVALUE_INDEX:
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
                case myVALUE_INDEX:
                    _instance.setValue((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated.IService1_PortType_revokePayment_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
