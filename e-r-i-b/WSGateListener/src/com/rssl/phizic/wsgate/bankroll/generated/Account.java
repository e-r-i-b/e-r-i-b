// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.bankroll.generated;


public class Account {
    protected java.lang.String accountState;
    protected com.rssl.phizic.wsgate.bankroll.generated.Money balance;
    protected java.lang.Long clientKind;
    protected java.lang.Boolean creditAllowed;
    protected com.rssl.phizic.wsgate.bankroll.generated.Currency currency;
    protected java.lang.Boolean debitAllowed;
    protected java.lang.Boolean demand;
    protected java.lang.String description;
    protected java.lang.String id;
    protected java.math.BigDecimal interestRate;
    protected java.lang.Long kind;
    protected com.rssl.phizic.wsgate.bankroll.generated.Money maxSumWrite;
    protected com.rssl.phizic.wsgate.bankroll.generated.Money minimumBalance;
    protected java.lang.String number;
    protected com.rssl.phizic.wsgate.bankroll.generated.Office office;
    protected java.util.Calendar openDate;
    protected java.lang.Boolean passbook;
    protected java.util.Calendar prolongationDate;
    protected java.lang.Long subKind;
    protected java.lang.String type;
    
    public Account() {
    }
    
    public Account(java.lang.String accountState, com.rssl.phizic.wsgate.bankroll.generated.Money balance, java.lang.Long clientKind, java.lang.Boolean creditAllowed, com.rssl.phizic.wsgate.bankroll.generated.Currency currency, java.lang.Boolean debitAllowed, java.lang.Boolean demand, java.lang.String description, java.lang.String id, java.math.BigDecimal interestRate, java.lang.Long kind, com.rssl.phizic.wsgate.bankroll.generated.Money maxSumWrite, com.rssl.phizic.wsgate.bankroll.generated.Money minimumBalance, java.lang.String number, com.rssl.phizic.wsgate.bankroll.generated.Office office, java.util.Calendar openDate, java.lang.Boolean passbook, java.util.Calendar prolongationDate, java.lang.Long subKind, java.lang.String type) {
        this.accountState = accountState;
        this.balance = balance;
        this.clientKind = clientKind;
        this.creditAllowed = creditAllowed;
        this.currency = currency;
        this.debitAllowed = debitAllowed;
        this.demand = demand;
        this.description = description;
        this.id = id;
        this.interestRate = interestRate;
        this.kind = kind;
        this.maxSumWrite = maxSumWrite;
        this.minimumBalance = minimumBalance;
        this.number = number;
        this.office = office;
        this.openDate = openDate;
        this.passbook = passbook;
        this.prolongationDate = prolongationDate;
        this.subKind = subKind;
        this.type = type;
    }
    
    public java.lang.String getAccountState() {
        return accountState;
    }
    
    public void setAccountState(java.lang.String accountState) {
        this.accountState = accountState;
    }
    
    public com.rssl.phizic.wsgate.bankroll.generated.Money getBalance() {
        return balance;
    }
    
    public void setBalance(com.rssl.phizic.wsgate.bankroll.generated.Money balance) {
        this.balance = balance;
    }
    
    public java.lang.Long getClientKind() {
        return clientKind;
    }
    
    public void setClientKind(java.lang.Long clientKind) {
        this.clientKind = clientKind;
    }
    
    public java.lang.Boolean getCreditAllowed() {
        return creditAllowed;
    }
    
    public void setCreditAllowed(java.lang.Boolean creditAllowed) {
        this.creditAllowed = creditAllowed;
    }
    
    public com.rssl.phizic.wsgate.bankroll.generated.Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency(com.rssl.phizic.wsgate.bankroll.generated.Currency currency) {
        this.currency = currency;
    }
    
    public java.lang.Boolean getDebitAllowed() {
        return debitAllowed;
    }
    
    public void setDebitAllowed(java.lang.Boolean debitAllowed) {
        this.debitAllowed = debitAllowed;
    }
    
    public java.lang.Boolean getDemand() {
        return demand;
    }
    
    public void setDemand(java.lang.Boolean demand) {
        this.demand = demand;
    }
    
    public java.lang.String getDescription() {
        return description;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public java.lang.String getId() {
        return id;
    }
    
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    public java.math.BigDecimal getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(java.math.BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    
    public java.lang.Long getKind() {
        return kind;
    }
    
    public void setKind(java.lang.Long kind) {
        this.kind = kind;
    }
    
    public com.rssl.phizic.wsgate.bankroll.generated.Money getMaxSumWrite() {
        return maxSumWrite;
    }
    
    public void setMaxSumWrite(com.rssl.phizic.wsgate.bankroll.generated.Money maxSumWrite) {
        this.maxSumWrite = maxSumWrite;
    }
    
    public com.rssl.phizic.wsgate.bankroll.generated.Money getMinimumBalance() {
        return minimumBalance;
    }
    
    public void setMinimumBalance(com.rssl.phizic.wsgate.bankroll.generated.Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
    
    public java.lang.String getNumber() {
        return number;
    }
    
    public void setNumber(java.lang.String number) {
        this.number = number;
    }
    
    public com.rssl.phizic.wsgate.bankroll.generated.Office getOffice() {
        return office;
    }
    
    public void setOffice(com.rssl.phizic.wsgate.bankroll.generated.Office office) {
        this.office = office;
    }
    
    public java.util.Calendar getOpenDate() {
        return openDate;
    }
    
    public void setOpenDate(java.util.Calendar openDate) {
        this.openDate = openDate;
    }
    
    public java.lang.Boolean getPassbook() {
        return passbook;
    }
    
    public void setPassbook(java.lang.Boolean passbook) {
        this.passbook = passbook;
    }
    
    public java.util.Calendar getProlongationDate() {
        return prolongationDate;
    }
    
    public void setProlongationDate(java.util.Calendar prolongationDate) {
        this.prolongationDate = prolongationDate;
    }
    
    public java.lang.Long getSubKind() {
        return subKind;
    }
    
    public void setSubKind(java.lang.Long subKind) {
        this.subKind = subKind;
    }
    
    public java.lang.String getType() {
        return type;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
}