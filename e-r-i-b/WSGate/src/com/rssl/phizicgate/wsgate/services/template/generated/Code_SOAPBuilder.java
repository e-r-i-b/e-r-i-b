// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.template.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Code_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.template.generated.Code _instance;
    private java.util.Map fields;
    private java.lang.String id;
    private static final int myFIELDS_INDEX = 0;
    private static final int myID_INDEX = 1;
    
    public Code_SOAPBuilder() {
    }
    
    public void setFields(java.util.Map fields) {
        this.fields = fields;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myFIELDS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
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
                case myFIELDS_INDEX:
                    _instance.setFields((java.util.Map)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.template.generated.Code)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
