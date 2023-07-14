// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MbkCard_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkCard _instance;
    private java.lang.String cardType;
    private boolean ermbConnected;
    private java.lang.String number;
    private static final int myCARDTYPE_INDEX = 0;
    private static final int myERMBCONNECTED_INDEX = 1;
    private static final int myNUMBER_INDEX = 2;
    
    public MbkCard_SOAPBuilder() {
    }
    
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }
    
    public void setErmbConnected(boolean ermbConnected) {
        this.ermbConnected = ermbConnected;
    }
    
    public void setNumber(java.lang.String number) {
        this.number = number;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCARDTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNUMBER_INDEX:
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
                case myCARDTYPE_INDEX:
                    _instance.setCardType((java.lang.String)memberValue);
                    break;
                case myNUMBER_INDEX:
                    _instance.setNumber((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkCard)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
