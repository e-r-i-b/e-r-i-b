// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.fund.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class FundInfo_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.fund.generated.FundInfo _instance;
    private java.lang.String externalResponseId;
    private com.rssl.phizicgate.wsgate.services.fund.generated.GUID initiatorGuid;
    private java.lang.String initiatorPhones;
    private com.rssl.phizicgate.wsgate.services.fund.generated.Request request;
    private com.rssl.phizicgate.wsgate.services.fund.generated.GUID senderGuid;
    private static final int myEXTERNALRESPONSEID_INDEX = 0;
    private static final int myINITIATORGUID_INDEX = 1;
    private static final int myINITIATORPHONES_INDEX = 2;
    private static final int myREQUEST_INDEX = 3;
    private static final int mySENDERGUID_INDEX = 4;
    
    public FundInfo_SOAPBuilder() {
    }
    
    public void setExternalResponseId(java.lang.String externalResponseId) {
        this.externalResponseId = externalResponseId;
    }
    
    public void setInitiatorGuid(com.rssl.phizicgate.wsgate.services.fund.generated.GUID initiatorGuid) {
        this.initiatorGuid = initiatorGuid;
    }
    
    public void setInitiatorPhones(java.lang.String initiatorPhones) {
        this.initiatorPhones = initiatorPhones;
    }
    
    public void setRequest(com.rssl.phizicgate.wsgate.services.fund.generated.Request request) {
        this.request = request;
    }
    
    public void setSenderGuid(com.rssl.phizicgate.wsgate.services.fund.generated.GUID senderGuid) {
        this.senderGuid = senderGuid;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myEXTERNALRESPONSEID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myINITIATORGUID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myINITIATORPHONES_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myREQUEST_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySENDERGUID_INDEX:
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
                case myEXTERNALRESPONSEID_INDEX:
                    _instance.setExternalResponseId((java.lang.String)memberValue);
                    break;
                case myINITIATORGUID_INDEX:
                    _instance.setInitiatorGuid((com.rssl.phizicgate.wsgate.services.fund.generated.GUID)memberValue);
                    break;
                case myINITIATORPHONES_INDEX:
                    _instance.setInitiatorPhones((java.lang.String)memberValue);
                    break;
                case myREQUEST_INDEX:
                    _instance.setRequest((com.rssl.phizicgate.wsgate.services.fund.generated.Request)memberValue);
                    break;
                case mySENDERGUID_INDEX:
                    _instance.setSenderGuid((com.rssl.phizicgate.wsgate.services.fund.generated.GUID)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.fund.generated.FundInfo)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}