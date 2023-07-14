// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class DebtImpl_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.documents.generated.DebtImpl _instance;
    private java.lang.String accountNumber;
    private java.lang.String code;
    private java.lang.String description;
    private java.lang.Boolean fixed;
    private java.util.Calendar lastPayDate;
    private java.util.Calendar period;
    private java.util.List rows;
    private static final int myACCOUNTNUMBER_INDEX = 0;
    private static final int myCODE_INDEX = 1;
    private static final int myDESCRIPTION_INDEX = 2;
    private static final int myFIXED_INDEX = 3;
    private static final int myLASTPAYDATE_INDEX = 4;
    private static final int myPERIOD_INDEX = 5;
    private static final int myROWS_INDEX = 6;
    
    public DebtImpl_SOAPBuilder() {
    }
    
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public void setCode(java.lang.String code) {
        this.code = code;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public void setFixed(java.lang.Boolean fixed) {
        this.fixed = fixed;
    }
    
    public void setLastPayDate(java.util.Calendar lastPayDate) {
        this.lastPayDate = lastPayDate;
    }
    
    public void setPeriod(java.util.Calendar period) {
        this.period = period;
    }
    
    public void setRows(java.util.List rows) {
        this.rows = rows;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myACCOUNTNUMBER_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDESCRIPTION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFIXED_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myROWS_INDEX:
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
                case myACCOUNTNUMBER_INDEX:
                    _instance.setAccountNumber((java.lang.String)memberValue);
                    break;
                case myCODE_INDEX:
                    _instance.setCode((java.lang.String)memberValue);
                    break;
                case myDESCRIPTION_INDEX:
                    _instance.setDescription((java.lang.String)memberValue);
                    break;
                case myFIXED_INDEX:
                    _instance.setFixed((java.lang.Boolean)memberValue);
                    break;
                case myROWS_INDEX:
                    _instance.setRows((java.util.List)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.documents.generated.DebtImpl)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
