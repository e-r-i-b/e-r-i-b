// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.way4.ws.generated;


public class PrepareOTPRsType extends com.rssl.phizic.authgate.way4.ws.generated.CommonRsType {
    protected java.lang.String SID;
    protected java.lang.String passwordNo;
    protected java.lang.String receiptNo;
    protected java.lang.Integer passwordsLeft;
    
    public PrepareOTPRsType() {
    }
    
    public PrepareOTPRsType(java.lang.String STAN, java.lang.String status, java.lang.String SID, java.lang.String passwordNo, java.lang.String receiptNo, java.lang.Integer passwordsLeft) {
        this.STAN = STAN;
        this.status = status;
        this.SID = SID;
        this.passwordNo = passwordNo;
        this.receiptNo = receiptNo;
        this.passwordsLeft = passwordsLeft;
    }
    
    public java.lang.String getSID() {
        return SID;
    }
    
    public void setSID(java.lang.String SID) {
        this.SID = SID;
    }
    
    public java.lang.String getPasswordNo() {
        return passwordNo;
    }
    
    public void setPasswordNo(java.lang.String passwordNo) {
        this.passwordNo = passwordNo;
    }
    
    public java.lang.String getReceiptNo() {
        return receiptNo;
    }
    
    public void setReceiptNo(java.lang.String receiptNo) {
        this.receiptNo = receiptNo;
    }
    
    public java.lang.Integer getPasswordsLeft() {
        return passwordsLeft;
    }
    
    public void setPasswordsLeft(java.lang.Integer passwordsLeft) {
        this.passwordsLeft = passwordsLeft;
    }
}
