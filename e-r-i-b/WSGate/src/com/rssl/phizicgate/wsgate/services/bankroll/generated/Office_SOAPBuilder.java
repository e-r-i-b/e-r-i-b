// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Office_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Office _instance;
    private java.lang.String BIC;
    private java.lang.String address;
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Code code;
    private boolean creditCardOffice;
    private java.lang.String name;
    private boolean needUpdateCreditCardOffice;
    private boolean openIMAOffice;
    private java.lang.String parentSynchKey;
    private java.lang.String sbidnt;
    private java.lang.String synchKey;
    private java.lang.String telephone;
    private static final int myBIC_INDEX = 0;
    private static final int myADDRESS_INDEX = 1;
    private static final int myCODE_INDEX = 2;
    private static final int myCREDITCARDOFFICE_INDEX = 3;
    private static final int myNAME_INDEX = 4;
    private static final int myNEEDUPDATECREDITCARDOFFICE_INDEX = 5;
    private static final int myOPENIMAOFFICE_INDEX = 6;
    private static final int myPARENTSYNCHKEY_INDEX = 7;
    private static final int mySBIDNT_INDEX = 8;
    private static final int mySYNCHKEY_INDEX = 9;
    private static final int myTELEPHONE_INDEX = 10;
    
    public Office_SOAPBuilder() {
    }
    
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }
    
    public void setAddress(java.lang.String address) {
        this.address = address;
    }
    
    public void setCode(com.rssl.phizicgate.wsgate.services.bankroll.generated.Code code) {
        this.code = code;
    }
    
    public void setCreditCardOffice(boolean creditCardOffice) {
        this.creditCardOffice = creditCardOffice;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public void setNeedUpdateCreditCardOffice(boolean needUpdateCreditCardOffice) {
        this.needUpdateCreditCardOffice = needUpdateCreditCardOffice;
    }
    
    public void setOpenIMAOffice(boolean openIMAOffice) {
        this.openIMAOffice = openIMAOffice;
    }
    
    public void setParentSynchKey(java.lang.String parentSynchKey) {
        this.parentSynchKey = parentSynchKey;
    }
    
    public void setSbidnt(java.lang.String sbidnt) {
        this.sbidnt = sbidnt;
    }
    
    public void setSynchKey(java.lang.String synchKey) {
        this.synchKey = synchKey;
    }
    
    public void setTelephone(java.lang.String telephone) {
        this.telephone = telephone;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myBIC_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myADDRESS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPARENTSYNCHKEY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySBIDNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySYNCHKEY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTELEPHONE_INDEX:
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
                case myBIC_INDEX:
                    _instance.setBIC((java.lang.String)memberValue);
                    break;
                case myADDRESS_INDEX:
                    _instance.setAddress((java.lang.String)memberValue);
                    break;
                case myCODE_INDEX:
                    _instance.setCode((com.rssl.phizicgate.wsgate.services.bankroll.generated.Code)memberValue);
                    break;
                case myNAME_INDEX:
                    _instance.setName((java.lang.String)memberValue);
                    break;
                case myPARENTSYNCHKEY_INDEX:
                    _instance.setParentSynchKey((java.lang.String)memberValue);
                    break;
                case mySBIDNT_INDEX:
                    _instance.setSbidnt((java.lang.String)memberValue);
                    break;
                case mySYNCHKEY_INDEX:
                    _instance.setSynchKey((java.lang.String)memberValue);
                    break;
                case myTELEPHONE_INDEX:
                    _instance.setTelephone((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.Office)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
