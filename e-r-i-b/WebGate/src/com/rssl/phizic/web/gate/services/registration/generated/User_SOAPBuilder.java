// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.registration.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class User_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.registration.generated.User _instance;
    private java.lang.String category;
    private static final int myCATEGORY_INDEX = 0;
    
    public User_SOAPBuilder() {
    }
    
    public void setCategory(java.lang.String category) {
        this.category = category;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCATEGORY_INDEX:
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
                case myCATEGORY_INDEX:
                    _instance.setCategory((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.registration.generated.User)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
