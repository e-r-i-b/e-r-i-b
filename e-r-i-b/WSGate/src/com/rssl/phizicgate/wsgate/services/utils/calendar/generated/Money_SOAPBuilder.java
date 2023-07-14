// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.calendar.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Money_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money _instance;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency currency;
    private java.math.BigDecimal decimal;
    private static final int myCURRENCY_INDEX = 0;
    private static final int myDECIMAL_INDEX = 1;
    
    public Money_SOAPBuilder() {
    }
    
    public void setCurrency(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency currency) {
        this.currency = currency;
    }
    
    public void setDecimal(java.math.BigDecimal decimal) {
        this.decimal = decimal;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCURRENCY_INDEX:
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
                case myCURRENCY_INDEX:
                    _instance.setCurrency((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
