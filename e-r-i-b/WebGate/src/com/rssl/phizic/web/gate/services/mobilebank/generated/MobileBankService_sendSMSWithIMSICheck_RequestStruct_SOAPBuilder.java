// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MobileBankService_sendSMSWithIMSICheck_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_sendSMSWithIMSICheck_RequestStruct _instance;
    private com.rssl.phizic.web.gate.services.mobilebank.generated.MessageInfo messageInfo_1;
    private java.lang.String[] arrayOfString_2;
    private static final int myMESSAGEINFO_1_INDEX = 0;
    private static final int myARRAYOFSTRING_2_INDEX = 1;
    
    public MobileBankService_sendSMSWithIMSICheck_RequestStruct_SOAPBuilder() {
    }
    
    public void setMessageInfo_1(com.rssl.phizic.web.gate.services.mobilebank.generated.MessageInfo messageInfo_1) {
        this.messageInfo_1 = messageInfo_1;
    }
    
    public void setArrayOfString_2(java.lang.String[] arrayOfString_2) {
        this.arrayOfString_2 = arrayOfString_2;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myMESSAGEINFO_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myARRAYOFSTRING_2_INDEX:
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
                case myMESSAGEINFO_1_INDEX:
                    _instance.setMessageInfo_1((com.rssl.phizic.web.gate.services.mobilebank.generated.MessageInfo)memberValue);
                    break;
                case myARRAYOFSTRING_2_INDEX:
                    _instance.setArrayOfString_2((java.lang.String[])memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_sendSMSWithIMSICheck_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
