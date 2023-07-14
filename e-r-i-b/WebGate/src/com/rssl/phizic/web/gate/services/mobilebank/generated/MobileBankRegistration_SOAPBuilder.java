// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MobileBankRegistration_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration _instance;
    private java.util.List linkedCards;
    private com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo mainCardInfo;
    private java.lang.String status;
    private java.lang.String tariff;
    private static final int myLINKEDCARDS_INDEX = 0;
    private static final int myMAINCARDINFO_INDEX = 1;
    private static final int mySTATUS_INDEX = 2;
    private static final int myTARIFF_INDEX = 3;
    
    public MobileBankRegistration_SOAPBuilder() {
    }
    
    public void setLinkedCards(java.util.List linkedCards) {
        this.linkedCards = linkedCards;
    }
    
    public void setMainCardInfo(com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo mainCardInfo) {
        this.mainCardInfo = mainCardInfo;
    }
    
    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    
    public void setTariff(java.lang.String tariff) {
        this.tariff = tariff;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myLINKEDCARDS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMAINCARDINFO_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTATUS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTARIFF_INDEX:
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
                case myLINKEDCARDS_INDEX:
                    _instance.setLinkedCards((java.util.List)memberValue);
                    break;
                case myMAINCARDINFO_INDEX:
                    _instance.setMainCardInfo((com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo)memberValue);
                    break;
                case mySTATUS_INDEX:
                    _instance.setStatus((java.lang.String)memberValue);
                    break;
                case myTARIFF_INDEX:
                    _instance.setTariff((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
