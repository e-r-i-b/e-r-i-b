// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.fund.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class FundMultiNodeService_updateResponseInfoBySum_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService_updateResponseInfoBySum_RequestStruct _instance;
    private com.rssl.phizicgate.wsgate.services.fund.generated.Response response_1;
    private java.math.BigDecimal bigDecimal_2;
    private static final int myRESPONSE_1_INDEX = 0;
    private static final int myBIGDECIMAL_2_INDEX = 1;
    
    public FundMultiNodeService_updateResponseInfoBySum_RequestStruct_SOAPBuilder() {
    }
    
    public void setResponse_1(com.rssl.phizicgate.wsgate.services.fund.generated.Response response_1) {
        this.response_1 = response_1;
    }
    
    public void setBigDecimal_2(java.math.BigDecimal bigDecimal_2) {
        this.bigDecimal_2 = bigDecimal_2;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myRESPONSE_1_INDEX:
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
                case myRESPONSE_1_INDEX:
                    _instance.setResponse_1((com.rssl.phizicgate.wsgate.services.fund.generated.Response)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService_updateResponseInfoBySum_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
