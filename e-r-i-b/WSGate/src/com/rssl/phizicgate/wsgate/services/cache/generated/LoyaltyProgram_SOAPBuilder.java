// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class LoyaltyProgram_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.cache.generated.LoyaltyProgram _instance;
    private java.math.BigDecimal balance;
    private java.lang.String email;
    private java.lang.String externalId;
    private java.lang.String phone;
    private static final int myBALANCE_INDEX = 0;
    private static final int myEMAIL_INDEX = 1;
    private static final int myEXTERNALID_INDEX = 2;
    private static final int myPHONE_INDEX = 3;
    
    public LoyaltyProgram_SOAPBuilder() {
    }
    
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }
    
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
    public void setExternalId(java.lang.String externalId) {
        this.externalId = externalId;
    }
    
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myEMAIL_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myEXTERNALID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPHONE_INDEX:
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
                case myEMAIL_INDEX:
                    _instance.setEmail((java.lang.String)memberValue);
                    break;
                case myEXTERNALID_INDEX:
                    _instance.setExternalId((java.lang.String)memberValue);
                    break;
                case myPHONE_INDEX:
                    _instance.setPhone((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.cache.generated.LoyaltyProgram)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
