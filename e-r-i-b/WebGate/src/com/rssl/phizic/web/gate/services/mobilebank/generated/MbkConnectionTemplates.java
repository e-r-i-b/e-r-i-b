// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;


public class MbkConnectionTemplates {
    protected com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo cardInfo;
    protected java.lang.String recipient;
    protected java.lang.String pymentCard;
    protected java.lang.String[] payerCodes;
    
    public MbkConnectionTemplates() {
    }
    
    public MbkConnectionTemplates(com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo cardInfo, java.lang.String recipient, java.lang.String pymentCard, java.lang.String[] payerCodes) {
        this.cardInfo = cardInfo;
        this.recipient = recipient;
        this.pymentCard = pymentCard;
        this.payerCodes = payerCodes;
    }
    
    public com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo getCardInfo() {
        return cardInfo;
    }
    
    public void setCardInfo(com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }
    
    public java.lang.String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(java.lang.String recipient) {
        this.recipient = recipient;
    }
    
    public java.lang.String getPymentCard() {
        return pymentCard;
    }
    
    public void setPymentCard(java.lang.String pymentCard) {
        this.pymentCard = pymentCard;
    }
    
    public java.lang.String[] getPayerCodes() {
        return payerCodes;
    }
    
    public void setPayerCodes(java.lang.String[] payerCodes) {
        this.payerCodes = payerCodes;
    }
}