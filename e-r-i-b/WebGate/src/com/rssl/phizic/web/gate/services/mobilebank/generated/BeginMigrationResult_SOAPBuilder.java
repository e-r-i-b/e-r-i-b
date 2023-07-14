// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class BeginMigrationResult_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.mobilebank.generated.BeginMigrationResult _instance;
    private com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo[] mbkConnectionInfo;
    private java.lang.Long migrationId;
    private static final int myMBKCONNECTIONINFO_INDEX = 0;
    private static final int myMIGRATIONID_INDEX = 1;
    
    public BeginMigrationResult_SOAPBuilder() {
    }
    
    public void setMbkConnectionInfo(com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo[] mbkConnectionInfo) {
        this.mbkConnectionInfo = mbkConnectionInfo;
    }
    
    public void setMigrationId(java.lang.Long migrationId) {
        this.migrationId = migrationId;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myMBKCONNECTIONINFO_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMIGRATIONID_INDEX:
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
                case myMBKCONNECTIONINFO_INDEX:
                    _instance.setMbkConnectionInfo((com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo[])memberValue);
                    break;
                case myMIGRATIONID_INDEX:
                    _instance.setMigrationId((java.lang.Long)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.BeginMigrationResult)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}