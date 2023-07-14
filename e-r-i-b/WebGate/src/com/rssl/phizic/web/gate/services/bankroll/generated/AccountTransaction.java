// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;


public class AccountTransaction {
    protected com.rssl.phizic.web.gate.services.bankroll.generated.Money balance;
    protected java.lang.String bookAccount;
    protected com.rssl.phizic.web.gate.services.bankroll.generated.CardUseData cardUseData;
    protected java.lang.String counteragent;
    protected java.lang.String counteragentAccount;
    protected java.lang.String counteragentBank;
    protected java.lang.String counteragentBankName;
    protected com.rssl.phizic.web.gate.services.bankroll.generated.Money creditSum;
    protected java.lang.String cunteragentCorAccount;
    protected java.util.Calendar date;
    protected com.rssl.phizic.web.gate.services.bankroll.generated.Money debitSum;
    protected java.lang.String description;
    protected java.lang.String documentNumber;
    protected java.lang.String operationCode;
    protected java.lang.String recipient;
    
    public AccountTransaction() {
    }
    
    public AccountTransaction(com.rssl.phizic.web.gate.services.bankroll.generated.Money balance, java.lang.String bookAccount, com.rssl.phizic.web.gate.services.bankroll.generated.CardUseData cardUseData, java.lang.String counteragent, java.lang.String counteragentAccount, java.lang.String counteragentBank, java.lang.String counteragentBankName, com.rssl.phizic.web.gate.services.bankroll.generated.Money creditSum, java.lang.String cunteragentCorAccount, java.util.Calendar date, com.rssl.phizic.web.gate.services.bankroll.generated.Money debitSum, java.lang.String description, java.lang.String documentNumber, java.lang.String operationCode, java.lang.String recipient) {
        this.balance = balance;
        this.bookAccount = bookAccount;
        this.cardUseData = cardUseData;
        this.counteragent = counteragent;
        this.counteragentAccount = counteragentAccount;
        this.counteragentBank = counteragentBank;
        this.counteragentBankName = counteragentBankName;
        this.creditSum = creditSum;
        this.cunteragentCorAccount = cunteragentCorAccount;
        this.date = date;
        this.debitSum = debitSum;
        this.description = description;
        this.documentNumber = documentNumber;
        this.operationCode = operationCode;
        this.recipient = recipient;
    }
    
    public com.rssl.phizic.web.gate.services.bankroll.generated.Money getBalance() {
        return balance;
    }
    
    public void setBalance(com.rssl.phizic.web.gate.services.bankroll.generated.Money balance) {
        this.balance = balance;
    }
    
    public java.lang.String getBookAccount() {
        return bookAccount;
    }
    
    public void setBookAccount(java.lang.String bookAccount) {
        this.bookAccount = bookAccount;
    }
    
    public com.rssl.phizic.web.gate.services.bankroll.generated.CardUseData getCardUseData() {
        return cardUseData;
    }
    
    public void setCardUseData(com.rssl.phizic.web.gate.services.bankroll.generated.CardUseData cardUseData) {
        this.cardUseData = cardUseData;
    }
    
    public java.lang.String getCounteragent() {
        return counteragent;
    }
    
    public void setCounteragent(java.lang.String counteragent) {
        this.counteragent = counteragent;
    }
    
    public java.lang.String getCounteragentAccount() {
        return counteragentAccount;
    }
    
    public void setCounteragentAccount(java.lang.String counteragentAccount) {
        this.counteragentAccount = counteragentAccount;
    }
    
    public java.lang.String getCounteragentBank() {
        return counteragentBank;
    }
    
    public void setCounteragentBank(java.lang.String counteragentBank) {
        this.counteragentBank = counteragentBank;
    }
    
    public java.lang.String getCounteragentBankName() {
        return counteragentBankName;
    }
    
    public void setCounteragentBankName(java.lang.String counteragentBankName) {
        this.counteragentBankName = counteragentBankName;
    }
    
    public com.rssl.phizic.web.gate.services.bankroll.generated.Money getCreditSum() {
        return creditSum;
    }
    
    public void setCreditSum(com.rssl.phizic.web.gate.services.bankroll.generated.Money creditSum) {
        this.creditSum = creditSum;
    }
    
    public java.lang.String getCunteragentCorAccount() {
        return cunteragentCorAccount;
    }
    
    public void setCunteragentCorAccount(java.lang.String cunteragentCorAccount) {
        this.cunteragentCorAccount = cunteragentCorAccount;
    }
    
    public java.util.Calendar getDate() {
        return date;
    }
    
    public void setDate(java.util.Calendar date) {
        this.date = date;
    }
    
    public com.rssl.phizic.web.gate.services.bankroll.generated.Money getDebitSum() {
        return debitSum;
    }
    
    public void setDebitSum(com.rssl.phizic.web.gate.services.bankroll.generated.Money debitSum) {
        this.debitSum = debitSum;
    }
    
    public java.lang.String getDescription() {
        return description;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public java.lang.String getDocumentNumber() {
        return documentNumber;
    }
    
    public void setDocumentNumber(java.lang.String documentNumber) {
        this.documentNumber = documentNumber;
    }
    
    public java.lang.String getOperationCode() {
        return operationCode;
    }
    
    public void setOperationCode(java.lang.String operationCode) {
        this.operationCode = operationCode;
    }
    
    public java.lang.String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(java.lang.String recipient) {
        this.recipient = recipient;
    }
}