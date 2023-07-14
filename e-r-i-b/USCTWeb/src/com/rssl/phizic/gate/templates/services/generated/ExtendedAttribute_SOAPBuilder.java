// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.gate.templates.services.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class ExtendedAttribute_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.gate.templates.services.generated.ExtendedAttribute _instance;
    private boolean changed;
    private java.lang.Long id;
    private java.lang.String name;
    private java.lang.String stringValue;
    private java.lang.String type;
    private java.lang.Object value;
    private static final int myCHANGED_INDEX = 0;
    private static final int myID_INDEX = 1;
    private static final int myNAME_INDEX = 2;
    private static final int mySTRINGVALUE_INDEX = 3;
    private static final int myTYPE_INDEX = 4;
    private static final int myVALUE_INDEX = 5;
    
    public ExtendedAttribute_SOAPBuilder() {
    }
    
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public void setStringValue(java.lang.String stringValue) {
        this.stringValue = stringValue;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
    
    public void setValue(java.lang.Object value) {
        this.value = value;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myNAME_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySTRINGVALUE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myTYPE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myVALUE_INDEX:
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
                case myID_INDEX:
                    _instance.setId((java.lang.Long)memberValue);
                    break;
                case myNAME_INDEX:
                    _instance.setName((java.lang.String)memberValue);
                    break;
                case mySTRINGVALUE_INDEX:
                    _instance.setStringValue((java.lang.String)memberValue);
                    break;
                case myTYPE_INDEX:
                    _instance.setType((java.lang.String)memberValue);
                    break;
                case myVALUE_INDEX:
                    _instance.setValue((java.lang.Object)memberValue);
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
        _instance = (com.rssl.phizic.gate.templates.services.generated.ExtendedAttribute)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
