// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.currency.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class CurrencyRateService_convert2_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.currency.generated.CurrencyRateService_convert2_RequestStruct _instance;
    private com.rssl.phizic.web.gate.services.currency.generated.Money money_1;
    private com.rssl.phizic.web.gate.services.currency.generated.Currency currency_2;
    private com.rssl.phizic.web.gate.services.currency.generated.Office office_3;
    private java.lang.String string_4;
    private static final int myMONEY_1_INDEX = 0;
    private static final int myCURRENCY_2_INDEX = 1;
    private static final int myOFFICE_3_INDEX = 2;
    private static final int mySTRING_4_INDEX = 3;
    
    public CurrencyRateService_convert2_RequestStruct_SOAPBuilder() {
    }
    
    public void setMoney_1(com.rssl.phizic.web.gate.services.currency.generated.Money money_1) {
        this.money_1 = money_1;
    }
    
    public void setCurrency_2(com.rssl.phizic.web.gate.services.currency.generated.Currency currency_2) {
        this.currency_2 = currency_2;
    }
    
    public void setOffice_3(com.rssl.phizic.web.gate.services.currency.generated.Office office_3) {
        this.office_3 = office_3;
    }
    
    public void setString_4(java.lang.String string_4) {
        this.string_4 = string_4;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myMONEY_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCURRENCY_2_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOFFICE_3_INDEX:
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
                case myMONEY_1_INDEX:
                    _instance.setMoney_1((com.rssl.phizic.web.gate.services.currency.generated.Money)memberValue);
                    break;
                case myCURRENCY_2_INDEX:
                    _instance.setCurrency_2((com.rssl.phizic.web.gate.services.currency.generated.Currency)memberValue);
                    break;
                case myOFFICE_3_INDEX:
                    _instance.setOffice_3((com.rssl.phizic.web.gate.services.currency.generated.Office)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.currency.generated.CurrencyRateService_convert2_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
