// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class ERMBPhone_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone _instance;
    private java.util.Calendar lastModified;
    private java.util.Calendar lastUpload;
    private com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber phoneNumber;
    private boolean phoneUsage;
    private static final int myLASTMODIFIED_INDEX = 0;
    private static final int myLASTUPLOAD_INDEX = 1;
    private static final int myPHONENUMBER_INDEX = 2;
    private static final int myPHONEUSAGE_INDEX = 3;
    
    public ERMBPhone_SOAPBuilder() {
    }
    
    public void setLastModified(java.util.Calendar lastModified) {
        this.lastModified = lastModified;
    }
    
    public void setLastUpload(java.util.Calendar lastUpload) {
        this.lastUpload = lastUpload;
    }
    
    public void setPhoneNumber(com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setPhoneUsage(boolean phoneUsage) {
        this.phoneUsage = phoneUsage;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myPHONENUMBER_INDEX:
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
                case myPHONENUMBER_INDEX:
                    _instance.setPhoneNumber((com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}