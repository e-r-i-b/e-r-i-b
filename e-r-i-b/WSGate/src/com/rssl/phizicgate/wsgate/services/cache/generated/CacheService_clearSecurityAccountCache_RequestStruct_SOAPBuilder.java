// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class CacheService_clearSecurityAccountCache_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearSecurityAccountCache_RequestStruct _instance;
    private com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount securityAccount_1;
    private static final int mySECURITYACCOUNT_1_INDEX = 0;
    
    public CacheService_clearSecurityAccountCache_RequestStruct_SOAPBuilder() {
    }
    
    public void setSecurityAccount_1(com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount securityAccount_1) {
        this.securityAccount_1 = securityAccount_1;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case mySECURITYACCOUNT_1_INDEX:
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
                case mySECURITYACCOUNT_1_INDEX:
                    _instance.setSecurityAccount_1((com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearSecurityAccountCache_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
