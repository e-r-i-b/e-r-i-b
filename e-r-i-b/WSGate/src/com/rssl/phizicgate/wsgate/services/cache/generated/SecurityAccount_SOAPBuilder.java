// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class SecurityAccount_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount _instance;
    private java.lang.String bankId;
    private java.lang.String bankName;
    private java.lang.String bankPostAddr;
    private java.lang.String blankType;
    private java.util.Calendar composeDt;
    private java.util.Calendar docDt;
    private java.lang.String docNum;
    private java.lang.String id;
    private com.rssl.phizicgate.wsgate.services.cache.generated.Money incomeAmt;
    private java.math.BigDecimal incomeRate;
    private java.lang.String issuerBankId;
    private java.lang.String issuerBankName;
    private com.rssl.phizicgate.wsgate.services.cache.generated.Money nominalAmount;
    private boolean onStorageInBank;
    private java.lang.String serialNumber;
    private java.lang.Long termDays;
    private java.util.Calendar termFinishDt;
    private java.util.Calendar termLimitDt;
    private java.util.Calendar termStartDt;
    private java.lang.String termType;
    private static final int myBANKID_INDEX = 0;
    private static final int myBANKNAME_INDEX = 1;
    private static final int myBANKPOSTADDR_INDEX = 2;
    private static final int myBLANKTYPE_INDEX = 3;
    private static final int myCOMPOSEDT_INDEX = 4;
    private static final int myDOCDT_INDEX = 5;
    private static final int myDOCNUM_INDEX = 6;
    private static final int myID_INDEX = 7;
    private static final int myINCOMEAMT_INDEX = 8;
    private static final int myINCOMERATE_INDEX = 9;
    private static final int myISSUERBANKID_INDEX = 10;
    private static final int myISSUERBANKNAME_INDEX = 11;
    private static final int myNOMINALAMOUNT_INDEX = 12;
    private static final int myONSTORAGEINBANK_INDEX = 13;
    private static final int mySERIALNUMBER_INDEX = 14;
    private static final int myTERMDAYS_INDEX = 15;
    private static final int myTERMFINISHDT_INDEX = 16;
    private static final int myTERMLIMITDT_INDEX = 17;
    private static final int myTERMSTARTDT_INDEX = 18;
    private static final int myTERMTYPE_INDEX = 19;
    
    public SecurityAccount_SOAPBuilder() {
    }
    
    public void setBankId(java.lang.String bankId) {
        this.bankId = bankId;
    }
    
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }
    
    public void setBankPostAddr(java.lang.String bankPostAddr) {
        this.bankPostAddr = bankPostAddr;
    }
    
    public void setBlankType(java.lang.String blankType) {
        this.blankType = blankType;
    }
    
    public void setComposeDt(java.util.Calendar composeDt) {
        this.composeDt = composeDt;
    }
    
    public void setDocDt(java.util.Calendar docDt) {
        this.docDt = docDt;
    }
    
    public void setDocNum(java.lang.String docNum) {
        this.docNum = docNum;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public void setIncomeAmt(com.rssl.phizicgate.wsgate.services.cache.generated.Money incomeAmt) {
        this.incomeAmt = incomeAmt;
    }
    
    public void setIncomeRate(java.math.BigDecimal incomeRate) {
        this.incomeRate = incomeRate;
    }
    
    public void setIssuerBankId(java.lang.String issuerBankId) {
        this.issuerBankId = issuerBankId;
    }
    
    public void setIssuerBankName(java.lang.String issuerBankName) {
        this.issuerBankName = issuerBankName;
    }
    
    public void setNominalAmount(com.rssl.phizicgate.wsgate.services.cache.generated.Money nominalAmount) {
        this.nominalAmount = nominalAmount;
    }
    
    public void setOnStorageInBank(boolean onStorageInBank) {
        this.onStorageInBank = onStorageInBank;
    }
    
    public void setSerialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public void setTermDays(java.lang.Long termDays) {
        this.termDays = termDays;
    }
    
    public void setTermFinishDt(java.util.Calendar termFinishDt) {
        this.termFinishDt = termFinishDt;
    }
    
    public void setTermLimitDt(java.util.Calendar termLimitDt) {
        this.termLimitDt = termLimitDt;
    }
    
    public void setTermStartDt(java.util.Calendar termStartDt) {
        this.termStartDt = termStartDt;
    }
    
    public void setTermType(java.lang.String termType) {
        this.termType = termType;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myBANKID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myBANKNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myBANKPOSTADDR_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myBLANKTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDOCNUM_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myINCOMEAMT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myISSUERBANKID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myISSUERBANKNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNOMINALAMOUNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySERIALNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTERMDAYS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTERMTYPE_INDEX:
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
                case myBANKID_INDEX:
                    _instance.setBankId((java.lang.String)memberValue);
                    break;
                case myBANKNAME_INDEX:
                    _instance.setBankName((java.lang.String)memberValue);
                    break;
                case myBANKPOSTADDR_INDEX:
                    _instance.setBankPostAddr((java.lang.String)memberValue);
                    break;
                case myBLANKTYPE_INDEX:
                    _instance.setBlankType((java.lang.String)memberValue);
                    break;
                case myDOCNUM_INDEX:
                    _instance.setDocNum((java.lang.String)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
                    break;
                case myINCOMEAMT_INDEX:
                    _instance.setIncomeAmt((com.rssl.phizicgate.wsgate.services.cache.generated.Money)memberValue);
                    break;
                case myISSUERBANKID_INDEX:
                    _instance.setIssuerBankId((java.lang.String)memberValue);
                    break;
                case myISSUERBANKNAME_INDEX:
                    _instance.setIssuerBankName((java.lang.String)memberValue);
                    break;
                case myNOMINALAMOUNT_INDEX:
                    _instance.setNominalAmount((com.rssl.phizicgate.wsgate.services.cache.generated.Money)memberValue);
                    break;
                case mySERIALNUMBER_INDEX:
                    _instance.setSerialNumber((java.lang.String)memberValue);
                    break;
                case myTERMDAYS_INDEX:
                    _instance.setTermDays((java.lang.Long)memberValue);
                    break;
                case myTERMTYPE_INDEX:
                    _instance.setTermType((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
