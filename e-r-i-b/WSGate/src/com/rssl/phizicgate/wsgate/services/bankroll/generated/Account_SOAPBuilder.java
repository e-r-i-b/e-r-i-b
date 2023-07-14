// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Account_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Account _instance;
    private java.lang.String accountState;
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Money balance;
    private java.lang.Long clientKind;
    private java.lang.Boolean creditAllowed;
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Currency currency;
    private java.lang.Boolean debitAllowed;
    private java.lang.Boolean demand;
    private java.lang.String description;
    private java.lang.String id;
    private java.math.BigDecimal interestRate;
    private java.lang.Long kind;
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Money maxSumWrite;
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Money minimumBalance;
    private java.lang.String number;
    private com.rssl.phizicgate.wsgate.services.bankroll.generated.Office office;
    private java.util.Calendar openDate;
    private java.lang.Boolean passbook;
    private java.util.Calendar prolongationDate;
    private java.lang.Long subKind;
    private java.lang.String type;
    private static final int myACCOUNTSTATE_INDEX = 0;
    private static final int myBALANCE_INDEX = 1;
    private static final int myCLIENTKIND_INDEX = 2;
    private static final int myCREDITALLOWED_INDEX = 3;
    private static final int myCURRENCY_INDEX = 4;
    private static final int myDEBITALLOWED_INDEX = 5;
    private static final int myDEMAND_INDEX = 6;
    private static final int myDESCRIPTION_INDEX = 7;
    private static final int myID_INDEX = 8;
    private static final int myINTERESTRATE_INDEX = 9;
    private static final int myKIND_INDEX = 10;
    private static final int myMAXSUMWRITE_INDEX = 11;
    private static final int myMINIMUMBALANCE_INDEX = 12;
    private static final int myNUMBER_INDEX = 13;
    private static final int myOFFICE_INDEX = 14;
    private static final int myOPENDATE_INDEX = 15;
    private static final int myPASSBOOK_INDEX = 16;
    private static final int myPROLONGATIONDATE_INDEX = 17;
    private static final int mySUBKIND_INDEX = 18;
    private static final int myTYPE_INDEX = 19;
    
    public Account_SOAPBuilder() {
    }
    
    public void setAccountState(java.lang.String accountState) {
        this.accountState = accountState;
    }
    
    public void setBalance(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money balance) {
        this.balance = balance;
    }
    
    public void setClientKind(java.lang.Long clientKind) {
        this.clientKind = clientKind;
    }
    
    public void setCreditAllowed(java.lang.Boolean creditAllowed) {
        this.creditAllowed = creditAllowed;
    }
    
    public void setCurrency(com.rssl.phizicgate.wsgate.services.bankroll.generated.Currency currency) {
        this.currency = currency;
    }
    
    public void setDebitAllowed(java.lang.Boolean debitAllowed) {
        this.debitAllowed = debitAllowed;
    }
    
    public void setDemand(java.lang.Boolean demand) {
        this.demand = demand;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public void setInterestRate(java.math.BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    
    public void setKind(java.lang.Long kind) {
        this.kind = kind;
    }
    
    public void setMaxSumWrite(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money maxSumWrite) {
        this.maxSumWrite = maxSumWrite;
    }
    
    public void setMinimumBalance(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
    
    public void setNumber(java.lang.String number) {
        this.number = number;
    }
    
    public void setOffice(com.rssl.phizicgate.wsgate.services.bankroll.generated.Office office) {
        this.office = office;
    }
    
    public void setOpenDate(java.util.Calendar openDate) {
        this.openDate = openDate;
    }
    
    public void setPassbook(java.lang.Boolean passbook) {
        this.passbook = passbook;
    }
    
    public void setProlongationDate(java.util.Calendar prolongationDate) {
        this.prolongationDate = prolongationDate;
    }
    
    public void setSubKind(java.lang.Long subKind) {
        this.subKind = subKind;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myACCOUNTSTATE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myBALANCE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCLIENTKIND_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCREDITALLOWED_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCURRENCY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDEBITALLOWED_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDEMAND_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDESCRIPTION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myKIND_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMAXSUMWRITE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myMINIMUMBALANCE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOFFICE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPASSBOOK_INDEX:
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
                case myACCOUNTSTATE_INDEX:
                    _instance.setAccountState((java.lang.String)memberValue);
                    break;
                case myBALANCE_INDEX:
                    _instance.setBalance((com.rssl.phizicgate.wsgate.services.bankroll.generated.Money)memberValue);
                    break;
                case myCLIENTKIND_INDEX:
                    _instance.setClientKind((java.lang.Long)memberValue);
                    break;
                case myCREDITALLOWED_INDEX:
                    _instance.setCreditAllowed((java.lang.Boolean)memberValue);
                    break;
                case myCURRENCY_INDEX:
                    _instance.setCurrency((com.rssl.phizicgate.wsgate.services.bankroll.generated.Currency)memberValue);
                    break;
                case myDEBITALLOWED_INDEX:
                    _instance.setDebitAllowed((java.lang.Boolean)memberValue);
                    break;
                case myDEMAND_INDEX:
                    _instance.setDemand((java.lang.Boolean)memberValue);
                    break;
                case myDESCRIPTION_INDEX:
                    _instance.setDescription((java.lang.String)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
                    break;
                case myKIND_INDEX:
                    _instance.setKind((java.lang.Long)memberValue);
                    break;
                case myMAXSUMWRITE_INDEX:
                    _instance.setMaxSumWrite((com.rssl.phizicgate.wsgate.services.bankroll.generated.Money)memberValue);
                    break;
                case myMINIMUMBALANCE_INDEX:
                    _instance.setMinimumBalance((com.rssl.phizicgate.wsgate.services.bankroll.generated.Money)memberValue);
                    break;
                case myNUMBER_INDEX:
                    _instance.setNumber((java.lang.String)memberValue);
                    break;
                case myOFFICE_INDEX:
                    _instance.setOffice((com.rssl.phizicgate.wsgate.services.bankroll.generated.Office)memberValue);
                    break;
                case myPASSBOOK_INDEX:
                    _instance.setPassbook((java.lang.Boolean)memberValue);
                    break;
                case mySUBKIND_INDEX:
                    _instance.setSubKind((java.lang.Long)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.Account)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
