// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MobileBankService_sendOfferMessageSMS_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_sendOfferMessageSMS_RequestStruct _instance;
    private java.lang.String string_1;
    private java.lang.String string_2;
    private java.lang.Long long_3;
    private java.lang.String string_4;
    private static final int mySTRING_1_INDEX = 0;
    private static final int mySTRING_2_INDEX = 1;
    private static final int myLONG_3_INDEX = 2;
    private static final int mySTRING_4_INDEX = 3;
    
    public MobileBankService_sendOfferMessageSMS_RequestStruct_SOAPBuilder() {
    }
    
    public void setString_1(java.lang.String string_1) {
        this.string_1 = string_1;
    }
    
    public void setString_2(java.lang.String string_2) {
        this.string_2 = string_2;
    }
    
    public void setLong_3(java.lang.Long long_3) {
        this.long_3 = long_3;
    }
    
    public void setString_4(java.lang.String string_4) {
        this.string_4 = string_4;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case mySTRING_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRING_2_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myLONG_3_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRING_4_INDEX:
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
                case mySTRING_1_INDEX:
                    _instance.setString_1((java.lang.String)memberValue);
                    break;
                case mySTRING_2_INDEX:
                    _instance.setString_2((java.lang.String)memberValue);
                    break;
                case myLONG_3_INDEX:
                    _instance.setLong_3((java.lang.Long)memberValue);
                    break;
                case mySTRING_4_INDEX:
                    _instance.setString_4((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_sendOfferMessageSMS_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
