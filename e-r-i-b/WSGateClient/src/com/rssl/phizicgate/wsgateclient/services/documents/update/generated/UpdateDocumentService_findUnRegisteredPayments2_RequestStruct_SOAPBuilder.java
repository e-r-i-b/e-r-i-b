// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.documents.update.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class UpdateDocumentService_findUnRegisteredPayments2_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentService_findUnRegisteredPayments2_RequestStruct _instance;
    private com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State state_1;
    private java.lang.String string_2;
    private java.lang.Integer integer_3;
    private java.lang.Integer integer_4;
    private static final int mySTATE_1_INDEX = 0;
    private static final int mySTRING_2_INDEX = 1;
    private static final int myINTEGER_3_INDEX = 2;
    private static final int myINTEGER_4_INDEX = 3;
    
    public UpdateDocumentService_findUnRegisteredPayments2_RequestStruct_SOAPBuilder() {
    }
    
    public void setState_1(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State state_1) {
        this.state_1 = state_1;
    }
    
    public void setString_2(java.lang.String string_2) {
        this.string_2 = string_2;
    }
    
    public void setInteger_3(java.lang.Integer integer_3) {
        this.integer_3 = integer_3;
    }
    
    public void setInteger_4(java.lang.Integer integer_4) {
        this.integer_4 = integer_4;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case mySTATE_1_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRING_2_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myINTEGER_3_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myINTEGER_4_INDEX:
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
                case mySTATE_1_INDEX:
                    _instance.setState_1((com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State)memberValue);
                    break;
                case mySTRING_2_INDEX:
                    _instance.setString_2((java.lang.String)memberValue);
                    break;
                case myINTEGER_3_INDEX:
                    _instance.setInteger_3((java.lang.Integer)memberValue);
                    break;
                case myINTEGER_4_INDEX:
                    _instance.setInteger_4((java.lang.Integer)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentService_findUnRegisteredPayments2_RequestStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}