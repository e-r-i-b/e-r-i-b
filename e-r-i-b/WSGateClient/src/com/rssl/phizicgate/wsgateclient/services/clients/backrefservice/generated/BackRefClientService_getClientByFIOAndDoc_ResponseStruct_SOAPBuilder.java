// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class BackRefClientService_getClientByFIOAndDoc_ResponseStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct _instance;
    private com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client result;
    private static final int myRESULT_INDEX = 0;
    
    public BackRefClientService_getClientByFIOAndDoc_ResponseStruct_SOAPBuilder() {
    }
    
    public void setResult(com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client result) {
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
                    _instance.setResult((com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
