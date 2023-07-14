// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;


public class DepoAccount {
    protected java.lang.String accountNumber;
    protected java.util.Calendar agreementDate;
    protected java.lang.String agreementNumber;
    protected com.rssl.phizicgate.wsgate.services.cache.generated.Money debt;
    protected java.lang.String id;
    protected com.rssl.phizicgate.wsgate.services.cache.generated.Office office;
    protected boolean operationAllowed;
    protected java.lang.String state;
    
    public DepoAccount() {
    }
    
    public DepoAccount(java.lang.String accountNumber, java.util.Calendar agreementDate, java.lang.String agreementNumber, com.rssl.phizicgate.wsgate.services.cache.generated.Money debt, java.lang.String id, com.rssl.phizicgate.wsgate.services.cache.generated.Office office, boolean operationAllowed, java.lang.String state) {
        this.accountNumber = accountNumber;
        this.agreementDate = agreementDate;
        this.agreementNumber = agreementNumber;
        this.debt = debt;
        this.id = id;
        this.office = office;
        this.operationAllowed = operationAllowed;
        this.state = state;
    }
    
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public java.util.Calendar getAgreementDate() {
        return agreementDate;
    }
    
    public void setAgreementDate(java.util.Calendar agreementDate) {
        this.agreementDate = agreementDate;
    }
    
    public java.lang.String getAgreementNumber() {
        return agreementNumber;
    }
    
    public void setAgreementNumber(java.lang.String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
    
    public com.rssl.phizicgate.wsgate.services.cache.generated.Money getDebt() {
        return debt;
    }
    
    public void setDebt(com.rssl.phizicgate.wsgate.services.cache.generated.Money debt) {
        this.debt = debt;
    }
    
    public java.lang.String getId() {
        return id;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public com.rssl.phizicgate.wsgate.services.cache.generated.Office getOffice() {
        return office;
    }
    
    public void setOffice(com.rssl.phizicgate.wsgate.services.cache.generated.Office office) {
        this.office = office;
    }
    
    public boolean isOperationAllowed() {
        return operationAllowed;
    }
    
    public void setOperationAllowed(boolean operationAllowed) {
        this.operationAllowed = operationAllowed;
    }
    
    public java.lang.String getState() {
        return state;
    }
    
    public void setState(java.lang.String state) {
        this.state = state;
    }
}