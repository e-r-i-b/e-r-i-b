// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Pair_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.bankroll.generated.Pair _instance;
    private java.lang.Object first;
    private java.lang.Object second;
    private static final int myFIRST_INDEX = 0;
    private static final int mySECOND_INDEX = 1;
    
    public Pair_SOAPBuilder() {
    }
    
    public void setFirst(java.lang.Object first) {
        this.first = first;
    }
    
    public void setSecond(java.lang.Object second) {
        this.second = second;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myFIRST_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySECOND_INDEX:
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
                case myFIRST_INDEX:
                    _instance.setFirst((java.lang.Object)memberValue);
                    break;
                case mySECOND_INDEX:
                    _instance.setSecond((java.lang.Object)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.bankroll.generated.Pair)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}