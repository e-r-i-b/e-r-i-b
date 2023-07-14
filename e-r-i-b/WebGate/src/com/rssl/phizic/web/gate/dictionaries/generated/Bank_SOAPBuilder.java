// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.dictionaries.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Bank_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.dictionaries.generated.Bank _instance;
    private String BIC;
    private String account;
    private Long id;
    private String name;
    private String place;
    private String shortName;
    private String synchKey;
    private static final int myBIC_INDEX = 0;
    private static final int myACCOUNT_INDEX = 1;
    private static final int myID_INDEX = 2;
    private static final int myNAME_INDEX = 3;
    private static final int myPLACE_INDEX = 4;
    private static final int mySHORTNAME_INDEX = 5;
    private static final int mySYNCHKEY_INDEX = 6;

    public Bank_SOAPBuilder() {
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setSynchKey(String synchKey) {
        this.synchKey = synchKey;
    }

    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myBIC_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myACCOUNT_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPLACE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySHORTNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySYNCHKEY_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void construct() {
    }

    public void setMember(int index, Object memberValue) {
        try {
            switch(index) {
                case myBIC_INDEX:
                    _instance.setBIC((String)memberValue);
                    break;
                case myACCOUNT_INDEX:
                    _instance.setAccount((String)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((Long)memberValue);
                    break;
                case myNAME_INDEX:
                    _instance.setName((String)memberValue);
                    break;
                case myPLACE_INDEX:
                    _instance.setPlace((String)memberValue);
                    break;
                case mySHORTNAME_INDEX:
                    _instance.setShortName((String)memberValue);
                    break;
                case mySYNCHKEY_INDEX:
                    _instance.setSynchKey((String)memberValue);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new DeserializationException(new LocalizableExceptionAdapter(e));
        }
    }

    public void initialize() {
    }

    public void setInstance(Object instance) {
        _instance = (com.rssl.phizic.web.gate.dictionaries.generated.Bank)instance;
    }

    public Object getInstance() {
        return _instance;
    }
}