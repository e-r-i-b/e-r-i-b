// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.offices.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class OfficeGateService_PortType_getAll_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.offices.generated.OfficeGateService_PortType_getAll_RequestStruct _instance;
    private com.rssl.phizic.web.gate.services.offices.generated.Office office_1;
    private int int_2;
    private int int_3;
    private static final int myOFFICE_1_INDEX = 0;
    private static final int myINT_2_INDEX = 1;
    private static final int myINT_3_INDEX = 2;
    
    public OfficeGateService_PortType_getAll_RequestStruct_SOAPBuilder() {
    }
    
    public void setOffice_1(com.rssl.phizic.web.gate.services.offices.generated.Office office_1) {
        this.office_1 = office_1;
    }
    
    public void setInt_2(int int_2) {
        this.int_2 = int_2;
    }
    
    public void setInt_3(int int_3) {
        this.int_3 = int_3;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myOFFICE_1_INDEX:
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
                case myOFFICE_1_INDEX:
                    _instance.setOffice_1((com.rssl.phizic.web.gate.services.offices.generated.Office)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.offices.generated.OfficeGateService_PortType_getAll_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
