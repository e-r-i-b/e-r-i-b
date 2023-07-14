// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class MobileBankTemplateImpl_SOAPBuilder implements SOAPInstanceBuilder {
    private com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate _instance;
    private com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo cardInfo;
    private java.lang.String[] payerCodes;
    private java.lang.String recipient;
    private static final int myCARDINFO_INDEX = 0;
    private static final int myPAYERCODES_INDEX = 1;
    private static final int myRECIPIENT_INDEX = 2;
    
    public MobileBankTemplateImpl_SOAPBuilder() {
    }
    
    public void setCardInfo(com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }
    
    public void setPayerCodes(java.lang.String[] payerCodes) {
        this.payerCodes = payerCodes;
    }
    
    public void setRecipient(java.lang.String recipient) {
        this.recipient = recipient;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myCARDINFO_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myPAYERCODES_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            case myRECIPIENT_INDEX:
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
                case myCARDINFO_INDEX:
                    _instance.setCardInfo((com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo)memberValue);
                    break;
                case myPAYERCODES_INDEX:
                    _instance.setPayerCodes((java.lang.String[])memberValue);
                    break;
                case myRECIPIENT_INDEX:
                    _instance.setRecipient((java.lang.String)memberValue);
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
        _instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
