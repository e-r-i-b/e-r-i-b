// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.test.ipas.cap.ws.mock.generated;


public class VerifyCAPRqType extends com.rssl.phizic.test.ipas.cap.ws.mock.generated.CommonRqType {
    protected java.lang.String cardNumber;
    protected java.lang.String CAPToken;
    
    public VerifyCAPRqType() {
    }
    
    public VerifyCAPRqType(java.lang.String STAN, java.lang.String cardNumber, java.lang.String CAPToken) {
        this.STAN = STAN;
        this.cardNumber = cardNumber;
        this.CAPToken = CAPToken;
    }
    
    public java.lang.String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(java.lang.String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public java.lang.String getCAPToken() {
        return CAPToken;
    }
    
    public void setCAPToken(java.lang.String CAPToken) {
        this.CAPToken = CAPToken;
    }
}
