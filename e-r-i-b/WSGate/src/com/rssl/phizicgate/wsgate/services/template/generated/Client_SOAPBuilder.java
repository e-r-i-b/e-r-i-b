// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.template.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Client_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.template.generated.Client _instance;
    private java.util.Calendar birthDay;
    private java.util.List documents;
    private java.lang.String firstName;
    private com.rssl.phizicgate.wsgate.services.template.generated.Office office;
    private java.lang.String patrName;
    private java.lang.String surName;
    private static final int myBIRTHDAY_INDEX = 0;
    private static final int myDOCUMENTS_INDEX = 1;
    private static final int myFIRSTNAME_INDEX = 2;
    private static final int myOFFICE_INDEX = 3;
    private static final int myPATRNAME_INDEX = 4;
    private static final int mySURNAME_INDEX = 5;
    
    public Client_SOAPBuilder() {
    }
    
    public void setBirthDay(java.util.Calendar birthDay) {
        this.birthDay = birthDay;
    }
    
    public void setDocuments(java.util.List documents) {
        this.documents = documents;
    }
    
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }
    
    public void setOffice(com.rssl.phizicgate.wsgate.services.template.generated.Office office) {
        this.office = office;
    }
    
    public void setPatrName(java.lang.String patrName) {
        this.patrName = patrName;
    }
    
    public void setSurName(java.lang.String surName) {
        this.surName = surName;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myDOCUMENTS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFIRSTNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOFFICE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPATRNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySURNAME_INDEX:
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
                case myDOCUMENTS_INDEX:
                    _instance.setDocuments((java.util.List)memberValue);
                    break;
                case myFIRSTNAME_INDEX:
                    _instance.setFirstName((java.lang.String)memberValue);
                    break;
                case myOFFICE_INDEX:
                    _instance.setOffice((com.rssl.phizicgate.wsgate.services.template.generated.Office)memberValue);
                    break;
                case myPATRNAME_INDEX:
                    _instance.setPatrName((java.lang.String)memberValue);
                    break;
                case mySURNAME_INDEX:
                    _instance.setSurName((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.template.generated.Client)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}