// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MobileBankService_reverseMigration_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_reverseMigration_RequestStruct _instance;
    private java.lang.Long long_1;
    private com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientTariffInfo clientTariffInfo_2;
    private static final int myLONG_1_INDEX = 0;
    private static final int myCLIENTTARIFFINFO_2_INDEX = 1;
    
    public MobileBankService_reverseMigration_RequestStruct_SOAPBuilder() {
    }
    
    public void setLong_1(java.lang.Long long_1) {
        this.long_1 = long_1;
    }
    
    public void setClientTariffInfo_2(com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientTariffInfo clientTariffInfo_2) {
        this.clientTariffInfo_2 = clientTariffInfo_2;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myLONG_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCLIENTTARIFFINFO_2_INDEX:
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
                case myLONG_1_INDEX:
                    _instance.setLong_1((java.lang.Long)memberValue);
                    break;
                case myCLIENTTARIFFINFO_2_INDEX:
                    _instance.setClientTariffInfo_2((com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientTariffInfo)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_reverseMigration_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}