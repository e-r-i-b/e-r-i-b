// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.loyalty.generated;


public class LoyaltyProgramOperation {
    protected com.rssl.phizic.web.gate.services.loyalty.generated.Money moneyOperationalBalance;
    protected java.util.Calendar operationDate;
    protected java.lang.String operationInfo;
    protected java.lang.String operationKind;
    protected java.math.BigDecimal operationalBalance;
    
    public LoyaltyProgramOperation() {
    }
    
    public LoyaltyProgramOperation(com.rssl.phizic.web.gate.services.loyalty.generated.Money moneyOperationalBalance, java.util.Calendar operationDate, java.lang.String operationInfo, java.lang.String operationKind, java.math.BigDecimal operationalBalance) {
        this.moneyOperationalBalance = moneyOperationalBalance;
        this.operationDate = operationDate;
        this.operationInfo = operationInfo;
        this.operationKind = operationKind;
        this.operationalBalance = operationalBalance;
    }
    
    public com.rssl.phizic.web.gate.services.loyalty.generated.Money getMoneyOperationalBalance() {
        return moneyOperationalBalance;
    }
    
    public void setMoneyOperationalBalance(com.rssl.phizic.web.gate.services.loyalty.generated.Money moneyOperationalBalance) {
        this.moneyOperationalBalance = moneyOperationalBalance;
    }
    
    public java.util.Calendar getOperationDate() {
        return operationDate;
    }
    
    public void setOperationDate(java.util.Calendar operationDate) {
        this.operationDate = operationDate;
    }
    
    public java.lang.String getOperationInfo() {
        return operationInfo;
    }
    
    public void setOperationInfo(java.lang.String operationInfo) {
        this.operationInfo = operationInfo;
    }
    
    public java.lang.String getOperationKind() {
        return operationKind;
    }
    
    public void setOperationKind(java.lang.String operationKind) {
        this.operationKind = operationKind;
    }
    
    public java.math.BigDecimal getOperationalBalance() {
        return operationalBalance;
    }
    
    public void setOperationalBalance(java.math.BigDecimal operationalBalance) {
        this.operationalBalance = operationalBalance;
    }
}