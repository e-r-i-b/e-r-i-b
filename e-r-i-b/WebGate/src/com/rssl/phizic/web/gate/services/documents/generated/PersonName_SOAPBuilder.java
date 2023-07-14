// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.documents.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class PersonName_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.documents.generated.PersonName _instance;
    private java.lang.String firstName;
    private java.lang.String lastName;
    private java.lang.String middleName;
    private static final int myFIRSTNAME_INDEX = 0;
    private static final int myLASTNAME_INDEX = 1;
    private static final int myMIDDLENAME_INDEX = 2;
    
    public PersonName_SOAPBuilder() {
    }
    
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }
    
    public void setMiddleName(java.lang.String middleName) {
        this.middleName = middleName;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myFIRSTNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myLASTNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMIDDLENAME_INDEX:
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
                case myFIRSTNAME_INDEX:
                    _instance.setFirstName((java.lang.String)memberValue);
                    break;
                case myLASTNAME_INDEX:
                    _instance.setLastName((java.lang.String)memberValue);
                    break;
                case myMIDDLENAME_INDEX:
                    _instance.setMiddleName((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.documents.generated.PersonName)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
