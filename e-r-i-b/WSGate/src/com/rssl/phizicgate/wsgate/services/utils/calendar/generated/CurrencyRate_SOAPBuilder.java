// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.calendar.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class CurrencyRate_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CurrencyRate _instance;
    private java.lang.String dynamicExchangeRate;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency fromCurrency;
    private java.math.BigDecimal fromValue;
    private java.lang.String tarifPlanCodeType;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency toCurrency;
    private java.math.BigDecimal toValue;
    private java.lang.String type;
    private static final int myDYNAMICEXCHANGERATE_INDEX = 0;
    private static final int myFROMCURRENCY_INDEX = 1;
    private static final int myFROMVALUE_INDEX = 2;
    private static final int myTARIFPLANCODETYPE_INDEX = 3;
    private static final int myTOCURRENCY_INDEX = 4;
    private static final int myTOVALUE_INDEX = 5;
    private static final int myTYPE_INDEX = 6;
    
    public CurrencyRate_SOAPBuilder() {
    }
    
    public void setDynamicExchangeRate(java.lang.String dynamicExchangeRate) {
        this.dynamicExchangeRate = dynamicExchangeRate;
    }
    
    public void setFromCurrency(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    
    public void setFromValue(java.math.BigDecimal fromValue) {
        this.fromValue = fromValue;
    }
    
    public void setTarifPlanCodeType(java.lang.String tarifPlanCodeType) {
        this.tarifPlanCodeType = tarifPlanCodeType;
    }
    
    public void setToCurrency(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency toCurrency) {
        this.toCurrency = toCurrency;
    }
    
    public void setToValue(java.math.BigDecimal toValue) {
        this.toValue = toValue;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myDYNAMICEXCHANGERATE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFROMCURRENCY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTARIFPLANCODETYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTOCURRENCY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTYPE_INDEX:
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
                case myDYNAMICEXCHANGERATE_INDEX:
                    _instance.setDynamicExchangeRate((java.lang.String)memberValue);
                    break;
                case myFROMCURRENCY_INDEX:
                    _instance.setFromCurrency((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency)memberValue);
                    break;
                case myTARIFPLANCODETYPE_INDEX:
                    _instance.setTarifPlanCodeType((java.lang.String)memberValue);
                    break;
                case myTOCURRENCY_INDEX:
                    _instance.setToCurrency((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Currency)memberValue);
                    break;
                case myTYPE_INDEX:
                    _instance.setType((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CurrencyRate)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
