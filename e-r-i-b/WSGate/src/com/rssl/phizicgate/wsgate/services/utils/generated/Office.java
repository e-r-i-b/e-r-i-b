// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.generated;


public class Office {
    protected java.lang.String BIC;
    protected java.lang.String address;
    protected com.rssl.phizicgate.wsgate.services.utils.generated.Code code;
    protected java.lang.String name;
    protected java.lang.String parentSynchKey;
    protected java.lang.String sbidnt;
    protected java.lang.String synchKey;
    protected java.lang.String telephone;
    protected boolean creditCardOffice;
    protected boolean openIMAOffice;
    protected boolean needUpdateCreditCardOffice;
    
    public Office() {
    }
    
    public Office(java.lang.String BIC, java.lang.String address, com.rssl.phizicgate.wsgate.services.utils.generated.Code code, java.lang.String name, java.lang.String parentSynchKey, java.lang.String sbidnt, java.lang.String synchKey, java.lang.String telephone, boolean creditCardOffice, boolean openIMAOffice, boolean needUpdateCreditCardOffice) {
        this.BIC = BIC;
        this.address = address;
        this.code = code;
        this.name = name;
        this.parentSynchKey = parentSynchKey;
        this.sbidnt = sbidnt;
        this.synchKey = synchKey;
        this.telephone = telephone;
        this.creditCardOffice = creditCardOffice;
        this.openIMAOffice = openIMAOffice;
        this.needUpdateCreditCardOffice = needUpdateCreditCardOffice;
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
    
    public com.rssl.phizicgate.wsgate.services.utils.generated.Code getCode() {
        return code;
    }
    
    public void setCode(com.rssl.phizicgate.wsgate.services.utils.generated.Code code) {
        this.code = code;
    }
    
    public java.lang.String getName() {
        return name;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
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
    
    public boolean isCreditCardOffice() {
        return creditCardOffice;
    }
    
    public void setCreditCardOffice(boolean creditCardOffice) {
        this.creditCardOffice = creditCardOffice;
    }
    
    public boolean isOpenIMAOffice() {
        return openIMAOffice;
    }
    
    public void setOpenIMAOffice(boolean openIMAOffice) {
        this.openIMAOffice = openIMAOffice;
    }
    
    public boolean isNeedUpdateCreditCardOffice() {
        return needUpdateCreditCardOffice;
    }
    
    public void setNeedUpdateCreditCardOffice(boolean needUpdateCreditCardOffice) {
        this.needUpdateCreditCardOffice = needUpdateCreditCardOffice;
    }
}
