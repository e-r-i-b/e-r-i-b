// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.loyalty.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Currency_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.loyalty.generated.Currency _instance;
    private java.lang.String code;
    private java.lang.String externalId;
    private java.lang.String name;
    private java.lang.String number;
    private static final int myCODE_INDEX = 0;
    private static final int myEXTERNALID_INDEX = 1;
    private static final int myNAME_INDEX = 2;
    private static final int myNUMBER_INDEX = 3;
    
    public Currency_SOAPBuilder() {
    }
    
    public void setCode(java.lang.String code) {
        this.code = code;
    }
    
    public void setExternalId(java.lang.String externalId) {
        this.externalId = externalId;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public void setNumber(java.lang.String number) {
        this.number = number;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myEXTERNALID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNUMBER_INDEX:
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
                case myCODE_INDEX:
                    _instance.setCode((java.lang.String)memberValue);
                    break;
                case myEXTERNALID_INDEX:
                    _instance.setExternalId((java.lang.String)memberValue);
                    break;
                case myNAME_INDEX:
                    _instance.setName((java.lang.String)memberValue);
                    break;
                case myNUMBER_INDEX:
                    _instance.setNumber((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.loyalty.generated.Currency)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
