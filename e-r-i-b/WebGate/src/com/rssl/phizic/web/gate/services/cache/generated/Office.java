// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.cache.generated;


public class Office {
    protected java.lang.String BIC;
    protected java.lang.String address;
    protected com.rssl.phizic.web.gate.services.cache.generated.Code code;
    protected boolean creditCardOffice;
    protected java.lang.String name;
    protected boolean needUpdateCreditCardOffice;
    protected boolean openIMAOffice;
    protected java.lang.String parentSynchKey;
    protected java.lang.String sbidnt;
    protected java.lang.String synchKey;
    protected java.lang.String telephone;
    
    public Office() {
    }
    
    public Office(java.lang.String BIC, java.lang.String address, com.rssl.phizic.web.gate.services.cache.generated.Code code, boolean creditCardOffice, java.lang.String name, boolean needUpdateCreditCardOffice, boolean openIMAOffice, java.lang.String parentSynchKey, java.lang.String sbidnt, java.lang.String synchKey, java.lang.String telephone) {
        this.BIC = BIC;
        this.address = address;
        this.code = code;
        this.creditCardOffice = creditCardOffice;
        this.name = name;
        this.needUpdateCreditCardOffice = needUpdateCreditCardOffice;
        this.openIMAOffice = openIMAOffice;
        this.parentSynchKey = parentSynchKey;
        this.sbidnt = sbidnt;
        this.synchKey = synchKey;
        this.telephone = telephone;
    }
    
    public java.lang.String getBIC() {
        return BIC;
    }
    
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }
    
    public java.lang.String getAddress() {
        return address;
    }
    
    public void setAddress(java.lang.String address) {
        this.address = address;
    }
    
    public com.rssl.phizic.web.gate.services.cache.generated.Code getCode() {
        return code;
    }
    
    public void setCode(com.rssl.phizic.web.gate.services.cache.generated.Code code) {
        this.code = code;
    }
    
    public boolean isCreditCardOffice() {
        return creditCardOffice;
    }
    
    public void setCreditCardOffice(boolean creditCardOffice) {
        this.creditCardOffice = creditCardOffice;
    }
    
    public java.lang.String getName() {
        return name;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public boolean isNeedUpdateCreditCardOffice() {
        return needUpdateCreditCardOffice;
    }
    
    public void setNeedUpdateCreditCardOffice(boolean needUpdateCreditCardOffice) {
        this.needUpdateCreditCardOffice = needUpdateCreditCardOffice;
    }
    
    public boolean isOpenIMAOffice() {
        return openIMAOffice;
    }
    
    public void setOpenIMAOffice(boolean openIMAOffice) {
        this.openIMAOffice = openIMAOffice;
    }
    
    public java.lang.String getParentSynchKey() {
        return parentSynchKey;
    }
    
    public void setParentSynchKey(java.lang.String parentSynchKey) {
        this.parentSynchKey = parentSynchKey;
    }
    
    public java.lang.String getSbidnt() {
        return sbidnt;
    }
    
    public void setSbidnt(java.lang.String sbidnt) {
        this.sbidnt = sbidnt;
    }
    
    public java.lang.String getSynchKey() {
        return synchKey;
    }
    
    public void setSynchKey(java.lang.String synchKey) {
        this.synchKey = synchKey;
    }
    
    public java.lang.String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(java.lang.String telephone) {
        this.telephone = telephone;
    }
}
