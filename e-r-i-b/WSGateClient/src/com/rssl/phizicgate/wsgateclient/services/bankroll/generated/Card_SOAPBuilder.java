// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Card_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Card _instance;
    private java.lang.String additionalCardType;
    private com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Money availableLimit;
    private java.lang.String cardBonusSign;
    private java.lang.String cardLevel;
    private java.lang.String cardState;
    private java.lang.String cardType;
    private java.lang.String contractNumber;
    private com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency currency;
    private java.lang.String description;
    private java.lang.String displayedExpireDate;
    private java.lang.String emailAddress;
    private java.util.Calendar expireDate;
    private java.lang.String id;
    private java.util.Calendar issueDate;
    private java.lang.Long kind;
    private boolean main;
    private java.lang.String mainCardNumber;
    private java.lang.String number;
    private com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office office;
    private java.lang.String primaryAccountExternalId;
    private java.lang.String primaryAccountNumber;
    private java.lang.String reportDeliveryLanguage;
    private java.lang.String reportDeliveryType;
    private java.lang.String statusDescription;
    private java.lang.Long subkind;
    private java.lang.String type;
    private boolean useReportDelivery;
    private boolean virtual;
    private static final int myADDITIONALCARDTYPE_INDEX = 0;
    private static final int myAVAILABLELIMIT_INDEX = 1;
    private static final int myCARDBONUSSIGN_INDEX = 2;
    private static final int myCARDLEVEL_INDEX = 3;
    private static final int myCARDSTATE_INDEX = 4;
    private static final int myCARDTYPE_INDEX = 5;
    private static final int myCONTRACTNUMBER_INDEX = 6;
    private static final int myCURRENCY_INDEX = 7;
    private static final int myDESCRIPTION_INDEX = 8;
    private static final int myDISPLAYEDEXPIREDATE_INDEX = 9;
    private static final int myEMAILADDRESS_INDEX = 10;
    private static final int myEXPIREDATE_INDEX = 11;
    private static final int myID_INDEX = 12;
    private static final int myISSUEDATE_INDEX = 13;
    private static final int myKIND_INDEX = 14;
    private static final int myMAIN_INDEX = 15;
    private static final int myMAINCARDNUMBER_INDEX = 16;
    private static final int myNUMBER_INDEX = 17;
    private static final int myOFFICE_INDEX = 18;
    private static final int myPRIMARYACCOUNTEXTERNALID_INDEX = 19;
    private static final int myPRIMARYACCOUNTNUMBER_INDEX = 20;
    private static final int myREPORTDELIVERYLANGUAGE_INDEX = 21;
    private static final int myREPORTDELIVERYTYPE_INDEX = 22;
    private static final int mySTATUSDESCRIPTION_INDEX = 23;
    private static final int mySUBKIND_INDEX = 24;
    private static final int myTYPE_INDEX = 25;
    private static final int myUSEREPORTDELIVERY_INDEX = 26;
    private static final int myVIRTUAL_INDEX = 27;
    
    public Card_SOAPBuilder() {
    }
    
    public void setAdditionalCardType(java.lang.String additionalCardType) {
        this.additionalCardType = additionalCardType;
    }
    
    public void setAvailableLimit(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Money availableLimit) {
        this.availableLimit = availableLimit;
    }
    
    public void setCardBonusSign(java.lang.String cardBonusSign) {
        this.cardBonusSign = cardBonusSign;
    }
    
    public void setCardLevel(java.lang.String cardLevel) {
        this.cardLevel = cardLevel;
    }
    
    public void setCardState(java.lang.String cardState) {
        this.cardState = cardState;
    }
    
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }
    
    public void setContractNumber(java.lang.String contractNumber) {
        this.contractNumber = contractNumber;
    }
    
    public void setCurrency(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency currency) {
        this.currency = currency;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public void setDisplayedExpireDate(java.lang.String displayedExpireDate) {
        this.displayedExpireDate = displayedExpireDate;
    }
    
    public void setEmailAddress(java.lang.String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public void setExpireDate(java.util.Calendar expireDate) {
        this.expireDate = expireDate;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public void setIssueDate(java.util.Calendar issueDate) {
        this.issueDate = issueDate;
    }
    
    public void setKind(java.lang.Long kind) {
        this.kind = kind;
    }
    
    public void setMain(boolean main) {
        this.main = main;
    }
    
    public void setMainCardNumber(java.lang.String mainCardNumber) {
        this.mainCardNumber = mainCardNumber;
    }
    
    public void setNumber(java.lang.String number) {
        this.number = number;
    }
    
    public void setOffice(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office office) {
        this.office = office;
    }
    
    public void setPrimaryAccountExternalId(java.lang.String primaryAccountExternalId) {
        this.primaryAccountExternalId = primaryAccountExternalId;
    }
    
    public void setPrimaryAccountNumber(java.lang.String primaryAccountNumber) {
        this.primaryAccountNumber = primaryAccountNumber;
    }
    
    public void setReportDeliveryLanguage(java.lang.String reportDeliveryLanguage) {
        this.reportDeliveryLanguage = reportDeliveryLanguage;
    }
    
    public void setReportDeliveryType(java.lang.String reportDeliveryType) {
        this.reportDeliveryType = reportDeliveryType;
    }
    
    public void setStatusDescription(java.lang.String statusDescription) {
        this.statusDescription = statusDescription;
    }
    
    public void setSubkind(java.lang.Long subkind) {
        this.subkind = subkind;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
    
    public void setUseReportDelivery(boolean useReportDelivery) {
        this.useReportDelivery = useReportDelivery;
    }
    
    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myADDITIONALCARDTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myAVAILABLELIMIT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCARDBONUSSIGN_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCARDLEVEL_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCARDSTATE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCARDTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCONTRACTNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCURRENCY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDESCRIPTION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDISPLAYEDEXPIREDATE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myEMAILADDRESS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myKIND_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMAINCARDNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOFFICE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPRIMARYACCOUNTEXTERNALID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPRIMARYACCOUNTNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myREPORTDELIVERYLANGUAGE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myREPORTDELIVERYTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTATUSDESCRIPTION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySUBKIND_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTYPE_INDEX:
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
                case myADDITIONALCARDTYPE_INDEX:
                    _instance.setAdditionalCardType((java.lang.String)memberValue);
                    break;
                case myAVAILABLELIMIT_INDEX:
                    _instance.setAvailableLimit((com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Money)memberValue);
                    break;
                case myCARDBONUSSIGN_INDEX:
                    _instance.setCardBonusSign((java.lang.String)memberValue);
                    break;
                case myCARDLEVEL_INDEX:
                    _instance.setCardLevel((java.lang.String)memberValue);
                    break;
                case myCARDSTATE_INDEX:
                    _instance.setCardState((java.lang.String)memberValue);
                    break;
                case myCARDTYPE_INDEX:
                    _instance.setCardType((java.lang.String)memberValue);
                    break;
                case myCONTRACTNUMBER_INDEX:
                    _instance.setContractNumber((java.lang.String)memberValue);
                    break;
                case myCURRENCY_INDEX:
                    _instance.setCurrency((com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency)memberValue);
                    break;
                case myDESCRIPTION_INDEX:
                    _instance.setDescription((java.lang.String)memberValue);
                    break;
                case myDISPLAYEDEXPIREDATE_INDEX:
                    _instance.setDisplayedExpireDate((java.lang.String)memberValue);
                    break;
                case myEMAILADDRESS_INDEX:
                    _instance.setEmailAddress((java.lang.String)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
                    break;
                case myKIND_INDEX:
                    _instance.setKind((java.lang.Long)memberValue);
                    break;
                case myMAINCARDNUMBER_INDEX:
                    _instance.setMainCardNumber((java.lang.String)memberValue);
                    break;
                case myNUMBER_INDEX:
                    _instance.setNumber((java.lang.String)memberValue);
                    break;
                case myOFFICE_INDEX:
                    _instance.setOffice((com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office)memberValue);
                    break;
                case myPRIMARYACCOUNTEXTERNALID_INDEX:
                    _instance.setPrimaryAccountExternalId((java.lang.String)memberValue);
                    break;
                case myPRIMARYACCOUNTNUMBER_INDEX:
                    _instance.setPrimaryAccountNumber((java.lang.String)memberValue);
                    break;
                case myREPORTDELIVERYLANGUAGE_INDEX:
                    _instance.setReportDeliveryLanguage((java.lang.String)memberValue);
                    break;
                case myREPORTDELIVERYTYPE_INDEX:
                    _instance.setReportDeliveryType((java.lang.String)memberValue);
                    break;
                case mySTATUSDESCRIPTION_INDEX:
                    _instance.setStatusDescription((java.lang.String)memberValue);
                    break;
                case mySUBKIND_INDEX:
                    _instance.setSubkind((java.lang.Long)memberValue);
                    break;
                case myTYPE_INDEX:
                    _instance.setType((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Card)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}