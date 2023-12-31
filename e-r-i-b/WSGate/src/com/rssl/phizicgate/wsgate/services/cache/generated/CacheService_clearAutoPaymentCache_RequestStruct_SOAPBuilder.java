// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class CacheService_clearAutoPaymentCache_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoPaymentCache_RequestStruct _instance;
    private com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment autoPayment_1;
    private com.rssl.phizicgate.wsgate.services.cache.generated.Card[] arrayOfCard_2;
    private static final int myAUTOPAYMENT_1_INDEX = 0;
    private static final int myARRAYOFCARD_2_INDEX = 1;
    
    public CacheService_clearAutoPaymentCache_RequestStruct_SOAPBuilder() {
    }
    
    public void setAutoPayment_1(com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment autoPayment_1) {
        this.autoPayment_1 = autoPayment_1;
    }
    
    public void setArrayOfCard_2(com.rssl.phizicgate.wsgate.services.cache.generated.Card[] arrayOfCard_2) {
        this.arrayOfCard_2 = arrayOfCard_2;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myAUTOPAYMENT_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myARRAYOFCARD_2_INDEX:
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
                case myAUTOPAYMENT_1_INDEX:
                    _instance.setAutoPayment_1((com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment)memberValue);
                    break;
                case myARRAYOFCARD_2_INDEX:
                    _instance.setArrayOfCard_2((com.rssl.phizicgate.wsgate.services.cache.generated.Card[])memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoPaymentCache_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
