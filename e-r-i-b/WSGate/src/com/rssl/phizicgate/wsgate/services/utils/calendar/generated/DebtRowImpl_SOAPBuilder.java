// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.calendar.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class DebtRowImpl_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.DebtRowImpl _instance;
    private java.lang.String code;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money commission;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money debt;
    private java.lang.String description;
    private com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money fine;
    private static final int myCODE_INDEX = 0;
    private static final int myCOMMISSION_INDEX = 1;
    private static final int myDEBT_INDEX = 2;
    private static final int myDESCRIPTION_INDEX = 3;
    private static final int myFINE_INDEX = 4;
    
    public DebtRowImpl_SOAPBuilder() {
    }
    
    public void setCode(java.lang.String code) {
        this.code = code;
    }
    
    public void setCommission(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money commission) {
        this.commission = commission;
    }
    
    public void setDebt(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money debt) {
        this.debt = debt;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public void setFine(com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money fine) {
        this.fine = fine;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCODE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myCOMMISSION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDEBT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myDESCRIPTION_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFINE_INDEX:
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
                case myCODE_INDEX:
                    _instance.setCode((java.lang.String)memberValue);
                    break;
                case myCOMMISSION_INDEX:
                    _instance.setCommission((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money)memberValue);
                    break;
                case myDEBT_INDEX:
                    _instance.setDebt((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money)memberValue);
                    break;
                case myDESCRIPTION_INDEX:
                    _instance.setDescription((java.lang.String)memberValue);
                    break;
                case myFINE_INDEX:
                    _instance.setFine((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.Money)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.DebtRowImpl)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
