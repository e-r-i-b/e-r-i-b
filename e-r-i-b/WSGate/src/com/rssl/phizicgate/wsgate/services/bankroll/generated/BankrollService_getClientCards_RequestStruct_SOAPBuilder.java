// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class BankrollService_getClientCards_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getClientCards_RequestStruct _instance;
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Client client_1;
    private static final int myCLIENT_1_INDEX = 0;
    
    public BankrollService_getClientCards_RequestStruct_SOAPBuilder() {
    }
    
    public void setClient_1(com.rssl.phizicgate.wsgate.services.bankroll.generated.Client client_1) {
        this.client_1 = client_1;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCLIENT_1_INDEX:
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
                case myCLIENT_1_INDEX:
                    _instance.setClient_1((com.rssl.phizicgate.wsgate.services.bankroll.generated.Client)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getClientCards_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
