// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class UserInfo_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo _instance;
    private boolean activeCard;
    private java.util.Calendar birthdate;
    private java.lang.String cardNumber;
    private java.util.List cards;
    private java.lang.String cbCode;
    private java.lang.String firstname;
    private java.lang.String loginType;
    private boolean mainCard;
    private java.lang.String passport;
    private java.lang.String patrname;
    private java.lang.String surname;
    private java.lang.String userId;
    private static final int myACTIVECARD_INDEX = 0;
    private static final int myBIRTHDATE_INDEX = 1;
    private static final int myCARDNUMBER_INDEX = 2;
    private static final int myCARDS_INDEX = 3;
    private static final int myCBCODE_INDEX = 4;
    private static final int myFIRSTNAME_INDEX = 5;
    private static final int myLOGINTYPE_INDEX = 6;
    private static final int myMAINCARD_INDEX = 7;
    private static final int myPASSPORT_INDEX = 8;
    private static final int myPATRNAME_INDEX = 9;
    private static final int mySURNAME_INDEX = 10;
    private static final int myUSERID_INDEX = 11;
    
    public UserInfo_SOAPBuilder() {
    }
    
    public void setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
    }
    
    public void setBirthdate(java.util.Calendar birthdate) {
        this.birthdate = birthdate;
    }
    
    public void setCardNumber(java.lang.String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public void setCards(java.util.List cards) {
        this.cards = cards;
    }
    
    public void setCbCode(java.lang.String cbCode) {
        this.cbCode = cbCode;
    }
    
    public void setFirstname(java.lang.String firstname) {
        this.firstname = firstname;
    }
    
    public void setLoginType(java.lang.String loginType) {
        this.loginType = loginType;
    }
    
    public void setMainCard(boolean mainCard) {
        this.mainCard = mainCard;
    }
    
    public void setPassport(java.lang.String passport) {
        this.passport = passport;
    }
    
    public void setPatrname(java.lang.String patrname) {
        this.patrname = patrname;
    }
    
    public void setSurname(java.lang.String surname) {
        this.surname = surname;
    }
    
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCARDNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCARDS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCBCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFIRSTNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myLOGINTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPASSPORT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPATRNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySURNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myUSERID_INDEX:
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
                case myCARDNUMBER_INDEX:
                    _instance.setCardNumber((java.lang.String)memberValue);
                    break;
                case myCARDS_INDEX:
                    _instance.setCards((java.util.List)memberValue);
                    break;
                case myCBCODE_INDEX:
                    _instance.setCbCode((java.lang.String)memberValue);
                    break;
                case myFIRSTNAME_INDEX:
                    _instance.setFirstname((java.lang.String)memberValue);
                    break;
                case myLOGINTYPE_INDEX:
                    _instance.setLoginType((java.lang.String)memberValue);
                    break;
                case myPASSPORT_INDEX:
                    _instance.setPassport((java.lang.String)memberValue);
                    break;
                case myPATRNAME_INDEX:
                    _instance.setPatrname((java.lang.String)memberValue);
                    break;
                case mySURNAME_INDEX:
                    _instance.setSurname((java.lang.String)memberValue);
                    break;
                case myUSERID_INDEX:
                    _instance.setUserId((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
