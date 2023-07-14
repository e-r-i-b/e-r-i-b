// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.deposits.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class DepositTransaction_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.deposits.generated.DepositTransaction _instance;
    private com.rssl.phizic.web.gate.services.deposits.generated.Money balance;
    private java.lang.String bookAccount;
    private java.lang.String counteragent;
    private java.lang.String counteragentAccount;
    private java.lang.String counteragentBank;
    private java.lang.String counteragentBankName;
    private com.rssl.phizic.web.gate.services.deposits.generated.Money creditSum;
    private java.lang.String cunteragentCorAccount;
    private java.util.Calendar date;
    private com.rssl.phizic.web.gate.services.deposits.generated.Money debitSum;
    private java.lang.String description;
    private java.lang.String documentNumber;
    private java.lang.String operationCode;
    private static final int myBALANCE_INDEX = 0;
    private static final int myBOOKACCOUNT_INDEX = 1;
    private static final int myCOUNTERAGENT_INDEX = 2;
    private static final int myCOUNTERAGENTACCOUNT_INDEX = 3;
    private static final int myCOUNTERAGENTBANK_INDEX = 4;
    private static final int myCOUNTERAGENTBANKNAME_INDEX = 5;
    private static final int myCREDITSUM_INDEX = 6;
    private static final int myCUNTERAGENTCORACCOUNT_INDEX = 7;
    private static final int myDATE_INDEX = 8;
    private static final int myDEBITSUM_INDEX = 9;
    private static final int myDESCRIPTION_INDEX = 10;
    private static final int myDOCUMENTNUMBER_INDEX = 11;
    private static final int myOPERATIONCODE_INDEX = 12;
    
    public DepositTransaction_SOAPBuilder() {
    }
    
    public void setBalance(com.rssl.phizic.web.gate.services.deposits.generated.Money balance) {
        this.balance = balance;
    }
    
    public void setBookAccount(java.lang.String bookAccount) {
        this.bookAccount = bookAccount;
    }
    
    public void setCounteragent(java.lang.String counteragent) {
        this.counteragent = counteragent;
    }
    
    public void setCounteragentAccount(java.lang.String counteragentAccount) {
        this.counteragentAccount = counteragentAccount;
    }
    
    public void setCounteragentBank(java.lang.String counteragentBank) {
        this.counteragentBank = counteragentBank;
    }
    
    public void setCounteragentBankName(java.lang.String counteragentBankName) {
        this.counteragentBankName = counteragentBankName;
    }
    
    public void setCreditSum(com.rssl.phizic.web.gate.services.deposits.generated.Money creditSum) {
        this.creditSum = creditSum;
    }
    
    public void setCunteragentCorAccount(java.lang.String cunteragentCorAccount) {
        this.cunteragentCorAccount = cunteragentCorAccount;
    }
    
    public void setDate(java.util.Calendar date) {
        this.date = date;
    }
    
    public void setDebitSum(com.rssl.phizic.web.gate.services.deposits.generated.Money debitSum) {
        this.debitSum = debitSum;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public void setDocumentNumber(java.lang.String documentNumber) {
        this.documentNumber = documentNumber;
    }
    
    public void setOperationCode(java.lang.String operationCode) {
        this.operationCode = operationCode;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myBALANCE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myBOOKACCOUNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCOUNTERAGENT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCOUNTERAGENTACCOUNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCOUNTERAGENTBANK_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCOUNTERAGENTBANKNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCREDITSUM_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCUNTERAGENTCORACCOUNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDEBITSUM_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDESCRIPTION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDOCUMENTNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOPERATIONCODE_INDEX:
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
                case myBALANCE_INDEX:
                    _instance.setBalance((com.rssl.phizic.web.gate.services.deposits.generated.Money)memberValue);
                    break;
                case myBOOKACCOUNT_INDEX:
                    _instance.setBookAccount((java.lang.String)memberValue);
                    break;
                case myCOUNTERAGENT_INDEX:
                    _instance.setCounteragent((java.lang.String)memberValue);
                    break;
                case myCOUNTERAGENTACCOUNT_INDEX:
                    _instance.setCounteragentAccount((java.lang.String)memberValue);
                    break;
                case myCOUNTERAGENTBANK_INDEX:
                    _instance.setCounteragentBank((java.lang.String)memberValue);
                    break;
                case myCOUNTERAGENTBANKNAME_INDEX:
                    _instance.setCounteragentBankName((java.lang.String)memberValue);
                    break;
                case myCREDITSUM_INDEX:
                    _instance.setCreditSum((com.rssl.phizic.web.gate.services.deposits.generated.Money)memberValue);
                    break;
                case myCUNTERAGENTCORACCOUNT_INDEX:
                    _instance.setCunteragentCorAccount((java.lang.String)memberValue);
                    break;
                case myDEBITSUM_INDEX:
                    _instance.setDebitSum((com.rssl.phizic.web.gate.services.deposits.generated.Money)memberValue);
                    break;
                case myDESCRIPTION_INDEX:
                    _instance.setDescription((java.lang.String)memberValue);
                    break;
                case myDOCUMENTNUMBER_INDEX:
                    _instance.setDocumentNumber((java.lang.String)memberValue);
                    break;
                case myOPERATIONCODE_INDEX:
                    _instance.setOperationCode((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.deposits.generated.DepositTransaction)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
