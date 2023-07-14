// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.calendar.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class WriteDownOperation_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.WriteDownOperation _instance;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money curAmount;
    private java.lang.String operationName;
    private java.lang.String turnOver;
    private static final int myCURAMOUNT_INDEX = 0;
    private static final int myOPERATIONNAME_INDEX = 1;
    private static final int myTURNOVER_INDEX = 2;
    
    public WriteDownOperation_SOAPBuilder() {
    }
    
    public void setCurAmount(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money curAmount) {
        this.curAmount = curAmount;
    }
    
    public void setOperationName(java.lang.String operationName) {
        this.operationName = operationName;
    }
    
    public void setTurnOver(java.lang.String turnOver) {
        this.turnOver = turnOver;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCURAMOUNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOPERATIONNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTURNOVER_INDEX:
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
                case myCURAMOUNT_INDEX:
                    _instance.setCurAmount((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money)memberValue);
                    break;
                case myOPERATIONNAME_INDEX:
                    _instance.setOperationName((java.lang.String)memberValue);
                    break;
                case myTURNOVER_INDEX:
                    _instance.setTurnOver((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.WriteDownOperation)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
