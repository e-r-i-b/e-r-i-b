// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.monitoring.client.services.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MonitoringServiceGateStateConfigImpl_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.monitoring.client.services.generated.MonitoringServiceGateStateConfigImpl _instance;
    private boolean available;
    private boolean availableChangeInactiveType;
    private java.util.Calendar deteriorationTime;
    private java.lang.String inactiveType;
    private java.lang.String messageText;
    private int monitoringCount;
    private int monitoringErrorPercent;
    private long monitoringTime;
    private java.lang.Long recoveryTime;
    private java.util.Map resources;
    private long timeout;
    private boolean useMonitoring;
    private static final int myAVAILABLE_INDEX = 0;
    private static final int myAVAILABLECHANGEINACTIVETYPE_INDEX = 1;
    private static final int myDETERIORATIONTIME_INDEX = 2;
    private static final int myINACTIVETYPE_INDEX = 3;
    private static final int myMESSAGETEXT_INDEX = 4;
    private static final int myMONITORINGCOUNT_INDEX = 5;
    private static final int myMONITORINGERRORPERCENT_INDEX = 6;
    private static final int myMONITORINGTIME_INDEX = 7;
    private static final int myRECOVERYTIME_INDEX = 8;
    private static final int myRESOURCES_INDEX = 9;
    private static final int myTIMEOUT_INDEX = 10;
    private static final int myUSEMONITORING_INDEX = 11;
    
    public MonitoringServiceGateStateConfigImpl_SOAPBuilder() {
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public void setAvailableChangeInactiveType(boolean availableChangeInactiveType) {
        this.availableChangeInactiveType = availableChangeInactiveType;
    }
    
    public void setDeteriorationTime(java.util.Calendar deteriorationTime) {
        this.deteriorationTime = deteriorationTime;
    }
    
    public void setInactiveType(java.lang.String inactiveType) {
        this.inactiveType = inactiveType;
    }
    
    public void setMessageText(java.lang.String messageText) {
        this.messageText = messageText;
    }
    
    public void setMonitoringCount(int monitoringCount) {
        this.monitoringCount = monitoringCount;
    }
    
    public void setMonitoringErrorPercent(int monitoringErrorPercent) {
        this.monitoringErrorPercent = monitoringErrorPercent;
    }
    
    public void setMonitoringTime(long monitoringTime) {
        this.monitoringTime = monitoringTime;
    }
    
    public void setRecoveryTime(java.lang.Long recoveryTime) {
        this.recoveryTime = recoveryTime;
    }
    
    public void setResources(java.util.Map resources) {
        this.resources = resources;
    }
    
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
    
    public void setUseMonitoring(boolean useMonitoring) {
        this.useMonitoring = useMonitoring;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myINACTIVETYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMESSAGETEXT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myRECOVERYTIME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myRESOURCES_INDEX:
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
                case myINACTIVETYPE_INDEX:
                    _instance.setInactiveType((java.lang.String)memberValue);
                    break;
                case myMESSAGETEXT_INDEX:
                    _instance.setMessageText((java.lang.String)memberValue);
                    break;
                case myRECOVERYTIME_INDEX:
                    _instance.setRecoveryTime((java.lang.Long)memberValue);
                    break;
                case myRESOURCES_INDEX:
                    _instance.setResources((java.util.Map)memberValue);
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
        _instance = (com.rssl.phizic.monitoring.client.services.generated.MonitoringServiceGateStateConfigImpl)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}