// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.offices.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class BackRefOfficeGateService_getOfficeById_ResponseStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.wsgate.offices.generated.BackRefOfficeGateService_getOfficeById_ResponseStruct _instance;
    private com.rssl.phizic.wsgate.offices.generated.Office result;
    private static final int myRESULT_INDEX = 0;
    
    public BackRefOfficeGateService_getOfficeById_ResponseStruct_SOAPBuilder() {
    }
    
    public void setResult(com.rssl.phizic.wsgate.offices.generated.Office result) {
        this.result = result;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myRESULT_INDEX:
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
                case myRESULT_INDEX:
                    _instance.setResult((com.rssl.phizic.wsgate.offices.generated.Office)memberValue);
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
        _instance = (com.rssl.phizic.wsgate.offices.generated.BackRefOfficeGateService_getOfficeById_ResponseStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
