// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.fund.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class FundMultiNodeService_createFundSenderResponse_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.wsgate.fund.generated.FundMultiNodeService_createFundSenderResponse_RequestStruct _instance;
    private com.rssl.phizic.wsgate.fund.generated.Request request_1;
    private com.rssl.phizic.wsgate.fund.generated.GUID GUID_2;
    private com.rssl.phizic.wsgate.fund.generated.GUID GUID_3;
    private java.lang.String string_4;
    private java.lang.String string_5;
    private java.lang.String string_6;
    private static final int myREQUEST_1_INDEX = 0;
    private static final int myGUID_2_INDEX = 1;
    private static final int myGUID_3_INDEX = 2;
    private static final int mySTRING_4_INDEX = 3;
    private static final int mySTRING_5_INDEX = 4;
    private static final int mySTRING_6_INDEX = 5;
    
    public FundMultiNodeService_createFundSenderResponse_RequestStruct_SOAPBuilder() {
    }
    
    public void setRequest_1(com.rssl.phizic.wsgate.fund.generated.Request request_1) {
        this.request_1 = request_1;
    }
    
    public void setGUID_2(com.rssl.phizic.wsgate.fund.generated.GUID GUID_2) {
        this.GUID_2 = GUID_2;
    }
    
    public void setGUID_3(com.rssl.phizic.wsgate.fund.generated.GUID GUID_3) {
        this.GUID_3 = GUID_3;
    }
    
    public void setString_4(java.lang.String string_4) {
        this.string_4 = string_4;
    }
    
    public void setString_5(java.lang.String string_5) {
        this.string_5 = string_5;
    }
    
    public void setString_6(java.lang.String string_6) {
        this.string_6 = string_6;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myREQUEST_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myGUID_2_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myGUID_3_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRING_4_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRING_5_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRING_6_INDEX:
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
                case myREQUEST_1_INDEX:
                    _instance.setRequest_1((com.rssl.phizic.wsgate.fund.generated.Request)memberValue);
                    break;
                case myGUID_2_INDEX:
                    _instance.setGUID_2((com.rssl.phizic.wsgate.fund.generated.GUID)memberValue);
                    break;
                case myGUID_3_INDEX:
                    _instance.setGUID_3((com.rssl.phizic.wsgate.fund.generated.GUID)memberValue);
                    break;
                case mySTRING_4_INDEX:
                    _instance.setString_4((java.lang.String)memberValue);
                    break;
                case mySTRING_5_INDEX:
                    _instance.setString_5((java.lang.String)memberValue);
                    break;
                case mySTRING_6_INDEX:
                    _instance.setString_6((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.wsgate.fund.generated.FundMultiNodeService_createFundSenderResponse_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}