// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class IService1_PortType_requestAttr_ResponseStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated.IService1_PortType_requestAttr_ResponseStruct _instance;
    private java.lang.String _return;
    private static final int my_RETURN_INDEX = 0;
    
    public IService1_PortType_requestAttr_ResponseStruct_SOAPBuilder() {
    }
    
    public void set_return(java.lang.String _return) {
        this._return = _return;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case my_RETURN_INDEX:
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
                case my_RETURN_INDEX:
                    _instance.set_return((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated.IService1_PortType_requestAttr_ResponseStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
