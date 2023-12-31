// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;


public class ClientTariffInfo {
    protected java.util.Calendar firstRegistrationDate;
    protected int linkPaymentBlockID;
    protected int linkTariff;
    protected java.util.Calendar nextPaidPeriod;
    protected int payDate;
    
    public ClientTariffInfo() {
    }
    
    public ClientTariffInfo(java.util.Calendar firstRegistrationDate, int linkPaymentBlockID, int linkTariff, java.util.Calendar nextPaidPeriod, int payDate) {
        this.firstRegistrationDate = firstRegistrationDate;
        this.linkPaymentBlockID = linkPaymentBlockID;
        this.linkTariff = linkTariff;
        this.nextPaidPeriod = nextPaidPeriod;
        this.payDate = payDate;
    }
    
    public java.util.Calendar getFirstRegistrationDate() {
        return firstRegistrationDate;
    }
    
    public void setFirstRegistrationDate(java.util.Calendar firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }
    
    public int getLinkPaymentBlockID() {
        return linkPaymentBlockID;
    }
    
    public void setLinkPaymentBlockID(int linkPaymentBlockID) {
        this.linkPaymentBlockID = linkPaymentBlockID;
    }
    
    public int getLinkTariff() {
        return linkTariff;
    }
    
    public void setLinkTariff(int linkTariff) {
        this.linkTariff = linkTariff;
    }
    
    public java.util.Calendar getNextPaidPeriod() {
        return nextPaidPeriod;
    }
    
    public void setNextPaidPeriod(java.util.Calendar nextPaidPeriod) {
        this.nextPaidPeriod = nextPaidPeriod;
    }
    
    public int getPayDate() {
        return payDate;
    }
    
    public void setPayDate(int payDate) {
        this.payDate = payDate;
    }
}
