// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.monitoring.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MonitoringServiceGateConfigImpl_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateConfigImpl _instance;
    private com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateStateConfigImpl degradationConfig;
    private com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateStateConfigImpl inaccessibleConfig;
    private java.lang.String serviceName;
    private java.lang.String state;
    private static final int myDEGRADATIONCONFIG_INDEX = 0;
    private static final int myINACCESSIBLECONFIG_INDEX = 1;
    private static final int mySERVICENAME_INDEX = 2;
    private static final int mySTATE_INDEX = 3;
    
    public MonitoringServiceGateConfigImpl_SOAPBuilder() {
    }
    
    public void setDegradationConfig(com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateStateConfigImpl degradationConfig) {
        this.degradationConfig = degradationConfig;
    }
    
    public void setInaccessibleConfig(com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateStateConfigImpl inaccessibleConfig) {
        this.inaccessibleConfig = inaccessibleConfig;
    }
    
    public void setServiceName(java.lang.String serviceName) {
        this.serviceName = serviceName;
    }
    
    public void setState(java.lang.String state) {
        this.state = state;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myDEGRADATIONCONFIG_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myINACCESSIBLECONFIG_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySERVICENAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTATE_INDEX:
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
                case myDEGRADATIONCONFIG_INDEX:
                    _instance.setDegradationConfig((com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateStateConfigImpl)memberValue);
                    break;
                case myINACCESSIBLECONFIG_INDEX:
                    _instance.setInaccessibleConfig((com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateStateConfigImpl)memberValue);
                    break;
                case mySERVICENAME_INDEX:
                    _instance.setServiceName((java.lang.String)memberValue);
                    break;
                case mySTATE_INDEX:
                    _instance.setState((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateConfigImpl)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
