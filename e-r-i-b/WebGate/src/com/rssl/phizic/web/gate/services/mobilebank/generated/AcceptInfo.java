// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;


public class AcceptInfo {
    protected java.lang.Long messageId;
    protected java.util.Calendar receiptTime;
    
    public AcceptInfo() {
    }
    
    public AcceptInfo(java.lang.Long messageId, java.util.Calendar receiptTime) {
        this.messageId = messageId;
        this.receiptTime = receiptTime;
    }
    
    public java.lang.Long getMessageId() {
        return messageId;
    }
    
    public void setMessageId(java.lang.Long messageId) {
        this.messageId = messageId;
    }
    
    public java.util.Calendar getReceiptTime() {
        return receiptTime;
    }
    
    public void setReceiptTime(java.util.Calendar receiptTime) {
        this.receiptTime = receiptTime;
    }
}
