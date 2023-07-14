// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.deposits.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Deposit_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.deposits.generated.Deposit _instance;
    private com.rssl.phizicgate.wsgate.services.deposits.generated.Money amount;
    private java.util.Calendar closeDate;
    private java.lang.String description;
    private java.lang.Long duration;
    private java.util.Calendar endDate;
    private java.lang.String id;
    private java.math.BigDecimal interestRate;
    private java.util.Calendar openDate;
    private java.lang.String state;
    private static final int myAMOUNT_INDEX = 0;
    private static final int myCLOSEDATE_INDEX = 1;
    private static final int myDESCRIPTION_INDEX = 2;
    private static final int myDURATION_INDEX = 3;
    private static final int myENDDATE_INDEX = 4;
    private static final int myID_INDEX = 5;
    private static final int myINTERESTRATE_INDEX = 6;
    private static final int myOPENDATE_INDEX = 7;
    private static final int mySTATE_INDEX = 8;
    
    public Deposit_SOAPBuilder() {
    }
    
    public void setAmount(com.rssl.phizicgate.wsgate.services.deposits.generated.Money amount) {
        this.amount = amount;
    }
    
    public void setCloseDate(java.util.Calendar closeDate) {
        this.closeDate = closeDate;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public void setDuration(java.lang.Long duration) {
        this.duration = duration;
    }
    
    public void setEndDate(java.util.Calendar endDate) {
        this.endDate = endDate;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public void setInterestRate(java.math.BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    
    public void setOpenDate(java.util.Calendar openDate) {
        this.openDate = openDate;
    }
    
    public void setState(java.lang.String state) {
        this.state = state;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myAMOUNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDESCRIPTION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDURATION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTATE_INDEX:
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
                case myAMOUNT_INDEX:
                    _instance.setAmount((com.rssl.phizicgate.wsgate.services.deposits.generated.Money)memberValue);
                    break;
                case myDESCRIPTION_INDEX:
                    _instance.setDescription((java.lang.String)memberValue);
                    break;
                case myDURATION_INDEX:
                    _instance.setDuration((java.lang.Long)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
                    break;
                case mySTATE_INDEX:
                    _instance.setState((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.Deposit)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
