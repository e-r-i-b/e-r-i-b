// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class PolicyDetails_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizicgate.wsgate.services.cache.generated.PolicyDetails _instance;
    private java.util.Calendar issureDt;
    private java.lang.String num;
    private java.lang.String series;
    private static final int myISSUREDT_INDEX = 0;
    private static final int myNUM_INDEX = 1;
    private static final int mySERIES_INDEX = 2;
    
    public PolicyDetails_SOAPBuilder() {
    }
    
    public void setIssureDt(java.util.Calendar issureDt) {
        this.issureDt = issureDt;
    }
    
    public void setNum(java.lang.String num) {
        this.num = num;
    }
    
    public void setSeries(java.lang.String series) {
        this.series = series;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myNUM_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case mySERIES_INDEX:
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
                case myNUM_INDEX:
                    _instance.setNum((java.lang.String)memberValue);
                    break;
                case mySERIES_INDEX:
                    _instance.setSeries((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizicgate.wsgate.services.cache.generated.PolicyDetails)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
