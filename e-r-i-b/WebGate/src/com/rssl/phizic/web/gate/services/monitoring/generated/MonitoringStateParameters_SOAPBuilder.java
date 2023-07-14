// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.monitoring.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MonitoringStateParameters_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringStateParameters _instance;
    private java.lang.String monitoringState;
    private java.lang.Integer timeout;
    private java.lang.Boolean useMonitoring;
    private static final int myMONITORINGSTATE_INDEX = 0;
    private static final int myTIMEOUT_INDEX = 1;
    private static final int myUSEMONITORING_INDEX = 2;
    
    public MonitoringStateParameters_SOAPBuilder() {
    }
    
    public void setMonitoringState(java.lang.String monitoringState) {
        this.monitoringState = monitoringState;
    }
    
    public void setTimeout(java.lang.Integer timeout) {
        this.timeout = timeout;
    }
    
    public void setUseMonitoring(java.lang.Boolean useMonitoring) {
        this.useMonitoring = useMonitoring;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myMONITORINGSTATE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTIMEOUT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myUSEMONITORING_INDEX:
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
                case myMONITORINGSTATE_INDEX:
                    _instance.setMonitoringState((java.lang.String)memberValue);
                    break;
                case myTIMEOUT_INDEX:
                    _instance.setTimeout((java.lang.Integer)memberValue);
                    break;
                case myUSEMONITORING_INDEX:
                    _instance.setUseMonitoring((java.lang.Boolean)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringStateParameters)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
