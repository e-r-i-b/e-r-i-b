// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Trustee_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Trustee _instance;
    private java.util.Calendar endingDate;
    private java.lang.String name;
    private static final int myENDINGDATE_INDEX = 0;
    private static final int myNAME_INDEX = 1;
    
    public Trustee_SOAPBuilder() {
    }
    
    public void setEndingDate(java.util.Calendar endingDate) {
        this.endingDate = endingDate;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myNAME_INDEX:
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
                case myNAME_INDEX:
                    _instance.setName((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.Trustee)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}