// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;


public class AccountInfo {
    protected long accountId;
    protected java.lang.String agreementNumber;
    protected java.math.BigDecimal clearBalance;
    protected java.util.Calendar closeDate;
    protected java.lang.Boolean creditCrossAgencyAllowed;
    protected java.lang.Boolean debitCrossAgencyAllowed;
    protected java.lang.String interestTransferAccount;
    protected java.lang.String interestTransferCard;
    protected java.util.Calendar lastTransactionDate;
    protected java.math.BigDecimal maxBalance;
    protected java.lang.Boolean passbook;
    protected com.rssl.phizic.web.gate.services.bankroll.generated.DateSpan period;
    protected java.lang.Boolean prolongationAllowed;
    
    public AccountInfo() {
    }
    
    public AccountInfo(long accountId, java.lang.String agreementNumber, java.math.BigDecimal clearBalance, java.util.Calendar closeDate, java.lang.Boolean creditCrossAgencyAllowed, java.lang.Boolean debitCrossAgencyAllowed, java.lang.String interestTransferAccount, java.lang.String interestTransferCard, java.util.Calendar lastTransactionDate, java.math.BigDecimal maxBalance, java.lang.Boolean passbook, com.rssl.phizic.web.gate.services.bankroll.generated.DateSpan period, java.lang.Boolean prolongationAllowed) {
        this.accountId = accountId;
        this.agreementNumber = agreementNumber;
        this.clearBalance = clearBalance;
        this.closeDate = closeDate;
        this.creditCrossAgencyAllowed = creditCrossAgencyAllowed;
        this.debitCrossAgencyAllowed = debitCrossAgencyAllowed;
        this.interestTransferAccount = interestTransferAccount;
        this.interestTransferCard = interestTransferCard;
        this.lastTransactionDate = lastTransactionDate;
        this.maxBalance = maxBalance;
        this.passbook = passbook;
        this.period = period;
        this.prolongationAllowed = prolongationAllowed;
    }
    
    public long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    
    public java.lang.String getAgreementNumber() {
        return agreementNumber;
    }
    
    public void setAgreementNumber(java.lang.String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
    
    public java.math.BigDecimal getClearBalance() {
        return clearBalance;
    }
    
    public void setClearBalance(java.math.BigDecimal clearBalance) {
        this.clearBalance = clearBalance;
    }
    
    public java.util.Calendar getCloseDate() {
        return closeDate;
    }
    
    public void setCloseDate(java.util.Calendar closeDate) {
        this.closeDate = closeDate;
    }
    
    public java.lang.Boolean getCreditCrossAgencyAllowed() {
        return creditCrossAgencyAllowed;
    }
    
    public void setCreditCrossAgencyAllowed(java.lang.Boolean creditCrossAgencyAllowed) {
        this.creditCrossAgencyAllowed = creditCrossAgencyAllowed;
    }
    
    public java.lang.Boolean getDebitCrossAgencyAllowed() {
        return debitCrossAgencyAllowed;
    }
    
    public void setDebitCrossAgencyAllowed(java.lang.Boolean debitCrossAgencyAllowed) {
        this.debitCrossAgencyAllowed = debitCrossAgencyAllowed;
    }
    
    public java.lang.String getInterestTransferAccount() {
        return interestTransferAccount;
    }
    
    public void setInterestTransferAccount(java.lang.String interestTransferAccount) {
        this.interestTransferAccount = interestTransferAccount;
    }
    
    public java.lang.String getInterestTransferCard() {
        return interestTransferCard;
    }
    
    public void setInterestTransferCard(java.lang.String interestTransferCard) {
        this.interestTransferCard = interestTransferCard;
    }
    
    public java.util.Calendar getLastTransactionDate() {
        return lastTransactionDate;
    }
    
    public void setLastTransactionDate(java.util.Calendar lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }
    
    public java.math.BigDecimal getMaxBalance() {
        return maxBalance;
    }
    
    public void setMaxBalance(java.math.BigDecimal maxBalance) {
        this.maxBalance = maxBalance;
    }
    
    public java.lang.Boolean getPassbook() {
        return passbook;
    }
    
    public void setPassbook(java.lang.Boolean passbook) {
        this.passbook = passbook;
    }
    
    public com.rssl.phizic.web.gate.services.bankroll.generated.DateSpan getPeriod() {
        return period;
    }
    
    public void setPeriod(com.rssl.phizic.web.gate.services.bankroll.generated.DateSpan period) {
        this.period = period;
    }
    
    public java.lang.Boolean getProlongationAllowed() {
        return prolongationAllowed;
    }
    
    public void setProlongationAllowed(java.lang.Boolean prolongationAllowed) {
        this.prolongationAllowed = prolongationAllowed;
    }
}