// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.registration.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class RegistartionClientService_PortType_update_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update_RequestStruct _instance;
    private com.rssl.phizicgate.wsgate.services.registration.generated.Client client_1;
    private java.util.Calendar calendar_2;
    private boolean boolean_3;
    private com.rssl.phizicgate.wsgate.services.registration.generated.User user_4;
    private static final int myCLIENT_1_INDEX = 0;
    private static final int myCALENDAR_2_INDEX = 1;
    private static final int myBOOLEAN_3_INDEX = 2;
    private static final int myUSER_4_INDEX = 3;
    
    public RegistartionClientService_PortType_update_RequestStruct_SOAPBuilder() {
    }
    
    public void setClient_1(com.rssl.phizicgate.wsgate.services.registration.generated.Client client_1) {
        this.client_1 = client_1;
    }
    
    public void setCalendar_2(java.util.Calendar calendar_2) {
        this.calendar_2 = calendar_2;
    }
    
    public void setBoolean_3(boolean boolean_3) {
        this.boolean_3 = boolean_3;
    }
    
    public void setUser_4(com.rssl.phizicgate.wsgate.services.registration.generated.User user_4) {
        this.user_4 = user_4;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCLIENT_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myUSER_4_INDEX:
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
                case myCLIENT_1_INDEX:
                    _instance.setClient_1((com.rssl.phizicgate.wsgate.services.registration.generated.Client)memberValue);
                    break;
                case myUSER_4_INDEX:
                    _instance.setUser_4((com.rssl.phizicgate.wsgate.services.registration.generated.User)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
