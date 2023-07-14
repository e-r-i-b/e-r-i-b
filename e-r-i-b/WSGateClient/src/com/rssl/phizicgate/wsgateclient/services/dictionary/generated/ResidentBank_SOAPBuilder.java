// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.dictionary.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class ResidentBank_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgateclient.services.dictionary.generated.ResidentBank _instance;
    private java.lang.String BIC;
    private java.lang.String account;
    private java.lang.Long id;
    private java.lang.String name;
    private java.lang.Boolean our;
    private java.lang.String place;
    private java.lang.String shortName;
    private java.lang.String synchKey;
    private static final int myBIC_INDEX = 0;
    private static final int myACCOUNT_INDEX = 1;
    private static final int myID_INDEX = 2;
    private static final int myNAME_INDEX = 3;
    private static final int myOUR_INDEX = 4;
    private static final int myPLACE_INDEX = 5;
    private static final int mySHORTNAME_INDEX = 6;
    private static final int mySYNCHKEY_INDEX = 7;
    
    public ResidentBank_SOAPBuilder() {
    }
    
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }
    
    public void setAccount(java.lang.String account) {
        this.account = account;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public void setOur(java.lang.Boolean our) {
        this.our = our;
    }
    
    public void setPlace(java.lang.String place) {
        this.place = place;
    }
    
    public void setShortName(java.lang.String shortName) {
        this.shortName = shortName;
    }
    
    public void setSynchKey(java.lang.String synchKey) {
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
            case myOUR_INDEX:
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
    
    public void setMember(int index, java.lang.Object memberValue) {
        try {
            switch(index) {
                case myBIC_INDEX:
                    _instance.setBIC((java.lang.String)memberValue);
                    break;
                case myACCOUNT_INDEX:
                    _instance.setAccount((java.lang.String)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.Long)memberValue);
                    break;
                case myNAME_INDEX:
                    _instance.setName((java.lang.String)memberValue);
                    break;
                case myOUR_INDEX:
                    _instance.setOur((java.lang.Boolean)memberValue);
                    break;
                case myPLACE_INDEX:
                    _instance.setPlace((java.lang.String)memberValue);
                    break;
                case mySHORTNAME_INDEX:
                    _instance.setShortName((java.lang.String)memberValue);
                    break;
                case mySYNCHKEY_INDEX:
                    _instance.setSynchKey((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgateclient.services.dictionary.generated.ResidentBank)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
