// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.deposits.generated;


public class DepositInfo {
    protected com.rssl.phizicgate.wsgate.services.deposits.generated.Account account;
    protected boolean additionalFee;
    protected java.lang.String agreementNumber;
    protected boolean anticipatoryRemoval;
    protected java.util.Map finalAccounts;
    protected com.rssl.phizicgate.wsgate.services.deposits.generated.Money minBalance;
    protected com.rssl.phizicgate.wsgate.services.deposits.generated.Money minReplenishmentAmount;
    protected com.rssl.phizicgate.wsgate.services.deposits.generated.Account percentAccount;
    protected boolean renewalAllowed;
    
    public DepositInfo() {
    }
    
    public DepositInfo(com.rssl.phizicgate.wsgate.services.deposits.generated.Account account, boolean additionalFee, java.lang.String agreementNumber, boolean anticipatoryRemoval, java.util.Map finalAccounts, com.rssl.phizicgate.wsgate.services.deposits.generated.Money minBalance, com.rssl.phizicgate.wsgate.services.deposits.generated.Money minReplenishmentAmount, com.rssl.phizicgate.wsgate.services.deposits.generated.Account percentAccount, boolean renewalAllowed) {
        this.account = account;
        this.additionalFee = additionalFee;
        this.agreementNumber = agreementNumber;
        this.anticipatoryRemoval = anticipatoryRemoval;
        this.finalAccounts = finalAccounts;
        this.minBalance = minBalance;
        this.minReplenishmentAmount = minReplenishmentAmount;
        this.percentAccount = percentAccount;
        this.renewalAllowed = renewalAllowed;
    }
    
    public com.rssl.phizicgate.wsgate.services.deposits.generated.Account getAccount() {
        return account;
    }
    
    public void setAccount(com.rssl.phizicgate.wsgate.services.deposits.generated.Account account) {
        this.account = account;
    }
    
    public boolean isAdditionalFee() {
        return additionalFee;
    }
    
    public void setAdditionalFee(boolean additionalFee) {
        this.additionalFee = additionalFee;
    }
    
    public java.lang.String getAgreementNumber() {
        return agreementNumber;
    }
    
    public void setAgreementNumber(java.lang.String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
    
    public boolean isAnticipatoryRemoval() {
        return anticipatoryRemoval;
    }
    
    public void setAnticipatoryRemoval(boolean anticipatoryRemoval) {
        this.anticipatoryRemoval = anticipatoryRemoval;
    }
    
    public java.util.Map getFinalAccounts() {
        return finalAccounts;
    }
    
    public void setFinalAccounts(java.util.Map finalAccounts) {
        this.finalAccounts = finalAccounts;
    }
    
    public com.rssl.phizicgate.wsgate.services.deposits.generated.Money getMinBalance() {
        return minBalance;
    }
    
    public void setMinBalance(com.rssl.phizicgate.wsgate.services.deposits.generated.Money minBalance) {
        this.minBalance = minBalance;
    }
    
    public com.rssl.phizicgate.wsgate.services.deposits.generated.Money getMinReplenishmentAmount() {
        return minReplenishmentAmount;
    }
    
    public void setMinReplenishmentAmount(com.rssl.phizicgate.wsgate.services.deposits.generated.Money minReplenishmentAmount) {
        this.minReplenishmentAmount = minReplenishmentAmount;
    }
    
    public com.rssl.phizicgate.wsgate.services.deposits.generated.Account getPercentAccount() {
        return percentAccount;
    }
    
    public void setPercentAccount(com.rssl.phizicgate.wsgate.services.deposits.generated.Account percentAccount) {
        this.percentAccount = percentAccount;
    }
    
    public boolean isRenewalAllowed() {
        return renewalAllowed;
    }
    
    public void setRenewalAllowed(boolean renewalAllowed) {
        this.renewalAllowed = renewalAllowed;
    }
}