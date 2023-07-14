// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.offices.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Code_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.offices.generated.Code _instance;
    private java.lang.String branch;
    private java.util.Map fields;
    private java.lang.String id;
    private java.lang.String office;
    private java.lang.String region;
    private static final int myBRANCH_INDEX = 0;
    private static final int myFIELDS_INDEX = 1;
    private static final int myID_INDEX = 2;
    private static final int myOFFICE_INDEX = 3;
    private static final int myREGION_INDEX = 4;
    
    public Code_SOAPBuilder() {
    }
    
    public void setBranch(java.lang.String branch) {
        this.branch = branch;
    }
    
    public void setFields(java.util.Map fields) {
        this.fields = fields;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public void setOffice(java.lang.String office) {
        this.office = office;
    }
    
    public void setRegion(java.lang.String region) {
        this.region = region;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myBRANCH_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myFIELDS_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myID_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myOFFICE_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myREGION_INDEX:
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
                case myBRANCH_INDEX:
                    _instance.setBranch((java.lang.String)memberValue);
                    break;
                case myFIELDS_INDEX:
                    _instance.setFields((java.util.Map)memberValue);
                    break;
                case myID_INDEX:
                    _instance.setId((java.lang.String)memberValue);
                    break;
                case myOFFICE_INDEX:
                    _instance.setOffice((java.lang.String)memberValue);
                    break;
                case myREGION_INDEX:
                    _instance.setRegion((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.offices.generated.Code)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
