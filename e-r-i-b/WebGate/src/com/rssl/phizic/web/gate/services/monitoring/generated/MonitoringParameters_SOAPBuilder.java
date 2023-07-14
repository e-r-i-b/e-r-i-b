// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.monitoring.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MonitoringParameters_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringParameters _instance;
    private java.util.List allStateConfig;
    private java.lang.String serviceName;
    private static final int myALLSTATECONFIG_INDEX = 0;
    private static final int mySERVICENAME_INDEX = 1;
    
    public MonitoringParameters_SOAPBuilder() {
    }
    
    public void setAllStateConfig(java.util.List allStateConfig) {
        this.allStateConfig = allStateConfig;
    }
    
    public void setServiceName(java.lang.String serviceName) {
        this.serviceName = serviceName;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myALLSTATECONFIG_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySERVICENAME_INDEX:
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
                case myALLSTATECONFIG_INDEX:
                    _instance.setAllStateConfig((java.util.List)memberValue);
                    break;
                case mySERVICENAME_INDEX:
                    _instance.setServiceName((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringParameters)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
