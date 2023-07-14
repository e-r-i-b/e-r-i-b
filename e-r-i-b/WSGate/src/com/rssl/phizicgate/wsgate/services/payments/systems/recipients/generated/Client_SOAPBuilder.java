// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Client_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Client _instance;
    private java.lang.String INN;
    private boolean UDBO;
    private java.util.Calendar birthDay;
    private java.lang.String birthPlace;
    private java.lang.String citizenship;
    private java.lang.String displayId;
    private java.util.List documents;
    private java.lang.String email;
    private java.lang.String firstName;
    private java.lang.String fullName;
    private java.lang.String homePhone;
    private java.lang.String id;
    private java.lang.String jobPhone;
    private com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Address legalAddress;
    private java.lang.String managerId;
    private java.lang.String managerOSB;
    private java.lang.String managerTB;
    private java.lang.String managerVSP;
    private java.lang.String mobileOperator;
    private java.lang.String mobilePhone;
    private java.lang.String patrName;
    private com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Address realAddress;
    private boolean resident;
    private java.lang.String segmentCodeType;
    private java.lang.String sex;
    private java.lang.String shortName;
    private java.lang.String surName;
    private java.lang.String tarifPlanCodeType;
    private java.util.Calendar tarifPlanConnectionDate;
    private static final int myINN_INDEX = 0;
    private static final int myUDBO_INDEX = 1;
    private static final int myBIRTHDAY_INDEX = 2;
    private static final int myBIRTHPLACE_INDEX = 3;
    private static final int myCITIZENSHIP_INDEX = 4;
    private static final int myDISPLAYID_INDEX = 5;
    private static final int myDOCUMENTS_INDEX = 6;
    private static final int myEMAIL_INDEX = 7;
    private static final int myFIRSTNAME_INDEX = 8;
    private static final int myFULLNAME_INDEX = 9;
    private static final int myHOMEPHONE_INDEX = 10;
    private static final int myID_INDEX = 11;
    private static final int myJOBPHONE_INDEX = 12;
    private static final int myLEGALADDRESS_INDEX = 13;
    private static final int myMANAGERID_INDEX = 14;
    private static final int myMANAGEROSB_INDEX = 15;
    private static final int myMANAGERTB_INDEX = 16;
    private static final int myMANAGERVSP_INDEX = 17;
    private static final int myMOBILEOPERATOR_INDEX = 18;
    private static final int myMOBILEPHONE_INDEX = 19;
    private static final int myPATRNAME_INDEX = 20;
    private static final int myREALADDRESS_INDEX = 21;
    private static final int myRESIDENT_INDEX = 22;
    private static final int mySEGMENTCODETYPE_INDEX = 23;
    private static final int mySEX_INDEX = 24;
    private static final int mySHORTNAME_INDEX = 25;
    private static final int mySURNAME_INDEX = 26;
    private static final int myTARIFPLANCODETYPE_INDEX = 27;
    private static final int myTARIFPLANCONNECTIONDATE_INDEX = 28;
    
    public Client_SOAPBuilder() {
    }
    
    public void setINN(java.lang.String INN) {
        this.INN = INN;
    }
    
    public void setUDBO(boolean UDBO) {
        this.UDBO = UDBO;
    }
    
    public void setBirthDay(java.util.Calendar birthDay) {
        this.birthDay = birthDay;
    }
    
    public void setBirthPlace(java.lang.String birthPlace) {
        this.birthPlace = birthPlace;
    }
    
    public void setCitizenship(java.lang.String citizenship) {
        this.citizenship = citizenship;
    }
    
    public void setDisplayId(java.lang.String displayId) {
        this.displayId = displayId;
    }
    
    public void setDocuments(java.util.List documents) {
        this.documents = documents;
    }
    
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }
    
    public void setFullName(java.lang.String fullName) {
        this.fullName = fullName;
    }
    
    public void setHomePhone(java.lang.String homePhone) {
        this.homePhone = homePhone;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public void setJobPhone(java.lang.String jobPhone) {
        this.jobPhone = jobPhone;
    }
    
    public void setLegalAddress(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Address legalAddress) {
        this.legalAddress = legalAddress;
    }
    
    public void setManagerId(java.lang.String managerId) {
        this.managerId = managerId;
    }
    
    public void setManagerOSB(java.lang.String managerOSB) {
        this.managerOSB = managerOSB;
    }
    
    public void setManagerTB(java.lang.String managerTB) {
        this.managerTB = managerTB;
    }
    
    public void setManagerVSP(java.lang.String managerVSP) {
        this.managerVSP = managerVSP;
    }
    
    public void setMobileOperator(java.lang.String mobileOperator) {
        this.mobileOperator = mobileOperator;
    }
    
    public void setMobilePhone(java.lang.String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    public void setPatrName(java.lang.String patrName) {
        this.patrName = patrName;
    }
    
    public void setRealAddress(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Address realAddress) {
        this.realAddress = realAddress;
    }
    
    public void setResident(boolean resident) {
        this.resident = resident;
    }
    
    public void setSegmentCodeType(java.lang.String segmentCodeType) {
        this.segmentCodeType = segmentCodeType;
    }
    
    public void setSex(java.lang.String sex) {
        this.sex = sex;
    }
    
    public void setShortName(java.lang.String shortName) {
        this.shortName = shortName;
    }
    
    public void setSurName(java.lang.String surName) {
        this.surName = surName;
    }
    
    public void setTarifPlanCodeType(java.lang.String tarifPlanCodeType) {
        this.tarifPlanCodeType = tarifPlanCodeType;
    }
    
    public void setTarifPlanConnectionDate(java.util.Calendar tarifPlanConnectionDate) {
        this.tarifPlanConnectionDate = tarifPlanConnectionDate;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myINN_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myBIRTHPLACE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCITIZENSHIP_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDISPLAYID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDOCUMENTS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myEMAIL_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFIRSTNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFULLNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myHOMEPHONE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myJOBPHONE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myLEGALADDRESS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMANAGERID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMANAGEROSB_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMANAGERTB_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMANAGERVSP_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMOBILEOPERATOR_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMOBILEPHONE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPATRNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myREALADDRESS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySEGMENTCODETYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySEX_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySHORTNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySURNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTARIFPLANCODETYPE_INDEX:
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
                case myINN_INDEX:
                    _instance.setINN((java.lang.String)memberValue);
                    break;
                case myBIRTHPLACE_INDEX:
                    _instance.setBirthPlace((java.lang.String)memberValue);
                    break;
                case myCITIZENSHIP_INDEX:
                    _instance.setCitizenship((java.lang.String)memberValue);
                    break;
                case myDISPLAYID_INDEX:
                    _instance.setDisplayId((java.lang.String)memberValue);
                    break;
                case myDOCUMENTS_INDEX:
                    _instance.setDocuments((java.util.List)memberValue);
                    break;
                case myEMAIL_INDEX:
                    _instance.setEmail((java.lang.String)memberValue);
                    break;
                case myFIRSTNAME_INDEX:
                    _instance.setFirstName((java.lang.String)memberValue);
                    break;
                case myFULLNAME_INDEX:
                    _instance.setFullName((java.lang.String)memberValue);
                    break;
                case myHOMEPHONE_INDEX:
                    _instance.setHomePhone((java.lang.String)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
                    break;
                case myJOBPHONE_INDEX:
                    _instance.setJobPhone((java.lang.String)memberValue);
                    break;
                case myLEGALADDRESS_INDEX:
                    _instance.setLegalAddress((com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Address)memberValue);
                    break;
                case myMANAGERID_INDEX:
                    _instance.setManagerId((java.lang.String)memberValue);
                    break;
                case myMANAGEROSB_INDEX:
                    _instance.setManagerOSB((java.lang.String)memberValue);
                    break;
                case myMANAGERTB_INDEX:
                    _instance.setManagerTB((java.lang.String)memberValue);
                    break;
                case myMANAGERVSP_INDEX:
                    _instance.setManagerVSP((java.lang.String)memberValue);
                    break;
                case myMOBILEOPERATOR_INDEX:
                    _instance.setMobileOperator((java.lang.String)memberValue);
                    break;
                case myMOBILEPHONE_INDEX:
                    _instance.setMobilePhone((java.lang.String)memberValue);
                    break;
                case myPATRNAME_INDEX:
                    _instance.setPatrName((java.lang.String)memberValue);
                    break;
                case myREALADDRESS_INDEX:
                    _instance.setRealAddress((com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Address)memberValue);
                    break;
                case mySEGMENTCODETYPE_INDEX:
                    _instance.setSegmentCodeType((java.lang.String)memberValue);
                    break;
                case mySEX_INDEX:
                    _instance.setSex((java.lang.String)memberValue);
                    break;
                case mySHORTNAME_INDEX:
                    _instance.setShortName((java.lang.String)memberValue);
                    break;
                case mySURNAME_INDEX:
                    _instance.setSurName((java.lang.String)memberValue);
                    break;
                case myTARIFPLANCODETYPE_INDEX:
                    _instance.setTarifPlanCodeType((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Client)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
