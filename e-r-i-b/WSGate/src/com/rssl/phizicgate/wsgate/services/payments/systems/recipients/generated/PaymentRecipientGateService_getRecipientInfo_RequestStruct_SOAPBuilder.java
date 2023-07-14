// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class PaymentRecipientGateService_getRecipientInfo_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.PaymentRecipientGateService_getRecipientInfo_RequestStruct _instance;
    private com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipient_1;
    private java.util.List list_2;
    private java.lang.String string_3;
    private static final int myRECIPIENT_1_INDEX = 0;
    private static final int myLIST_2_INDEX = 1;
    private static final int mySTRING_3_INDEX = 2;
    
    public PaymentRecipientGateService_getRecipientInfo_RequestStruct_SOAPBuilder() {
    }
    
    public void setRecipient_1(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipient_1) {
        this.recipient_1 = recipient_1;
    }
    
    public void setList_2(java.util.List list_2) {
        this.list_2 = list_2;
    }
    
    public void setString_3(java.lang.String string_3) {
        this.string_3 = string_3;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myRECIPIENT_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myLIST_2_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRING_3_INDEX:
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
                case myRECIPIENT_1_INDEX:
                    _instance.setRecipient_1((com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient)memberValue);
                    break;
                case myLIST_2_INDEX:
                    _instance.setList_2((java.util.List)memberValue);
                    break;
                case mySTRING_3_INDEX:
                    _instance.setString_3((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.PaymentRecipientGateService_getRecipientInfo_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
