// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.csa.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class CSABackRefService_removeErmbPhone_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct _instance;
    private com.rssl.phizic.wsgate.csa.generated.ClientTemplate clientTemplate_1;
    private java.lang.String string_2;
    private static final int myCLIENTTEMPLATE_1_INDEX = 0;
    private static final int mySTRING_2_INDEX = 1;
    
    public CSABackRefService_removeErmbPhone_RequestStruct_SOAPBuilder() {
    }
    
    public void setClientTemplate_1(com.rssl.phizic.wsgate.csa.generated.ClientTemplate clientTemplate_1) {
        this.clientTemplate_1 = clientTemplate_1;
    }
    
    public void setString_2(java.lang.String string_2) {
        this.string_2 = string_2;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCLIENTTEMPLATE_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRING_2_INDEX:
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
                case myCLIENTTEMPLATE_1_INDEX:
                    _instance.setClientTemplate_1((com.rssl.phizic.wsgate.csa.generated.ClientTemplate)memberValue);
                    break;
                case mySTRING_2_INDEX:
                    _instance.setString_2((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
