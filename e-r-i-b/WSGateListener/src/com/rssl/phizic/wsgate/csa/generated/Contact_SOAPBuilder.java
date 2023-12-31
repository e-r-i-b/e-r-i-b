// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.csa.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Contact_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.wsgate.csa.generated.Contact _instance;
    private java.lang.String contactNum;
    private java.lang.String contactType;
    private static final int myCONTACTNUM_INDEX = 0;
    private static final int myCONTACTTYPE_INDEX = 1;
    
    public Contact_SOAPBuilder() {
    }
    
    public void setContactNum(java.lang.String contactNum) {
        this.contactNum = contactNum;
    }
    
    public void setContactType(java.lang.String contactType) {
        this.contactType = contactType;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCONTACTNUM_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCONTACTTYPE_INDEX:
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
                case myCONTACTNUM_INDEX:
                    _instance.setContactNum((java.lang.String)memberValue);
                    break;
                case myCONTACTTYPE_INDEX:
                    _instance.setContactType((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.wsgate.csa.generated.Contact)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
