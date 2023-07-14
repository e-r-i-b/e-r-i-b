// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.clients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class ClientState_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.clients.generated.ClientState _instance;
    private java.lang.String category;
    private java.lang.String code;
    private java.lang.String description;
    private static final int myCATEGORY_INDEX = 0;
    private static final int myCODE_INDEX = 1;
    private static final int myDESCRIPTION_INDEX = 2;
    
    public ClientState_SOAPBuilder() {
    }
    
    public void setCategory(java.lang.String category) {
        this.category = category;
    }
    
    public void setCode(java.lang.String code) {
        this.code = code;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCATEGORY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDESCRIPTION_INDEX:
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
                case myCODE_INDEX:
                    _instance.setCode((java.lang.String)memberValue);
                    break;
                case myDESCRIPTION_INDEX:
                    _instance.setDescription((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.clients.generated.ClientState)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
