// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class AcceptInfo_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.mobilebank.generated.AcceptInfo _instance;
    private java.lang.Long messageId;
    private java.util.Calendar receiptTime;
    private static final int myMESSAGEID_INDEX = 0;
    private static final int myRECEIPTTIME_INDEX = 1;
    
    public AcceptInfo_SOAPBuilder() {
    }
    
    public void setMessageId(java.lang.Long messageId) {
        this.messageId = messageId;
    }
    
    public void setReceiptTime(java.util.Calendar receiptTime) {
        this.receiptTime = receiptTime;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myMESSAGEID_INDEX:
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
                case myMESSAGEID_INDEX:
                    _instance.setMessageId((java.lang.Long)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.AcceptInfo)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
