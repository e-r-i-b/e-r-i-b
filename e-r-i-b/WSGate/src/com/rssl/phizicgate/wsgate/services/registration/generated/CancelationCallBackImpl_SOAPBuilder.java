// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.registration.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class CancelationCallBackImpl_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.registration.generated.CancelationCallBackImpl _instance;
    private java.lang.String id;
    private static final int myID_INDEX = 0;
    
    public CancelationCallBackImpl_SOAPBuilder() {
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myID_INDEX:
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
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.registration.generated.CancelationCallBackImpl)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
