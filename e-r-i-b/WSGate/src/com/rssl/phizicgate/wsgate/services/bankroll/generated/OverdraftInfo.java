// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;


public class OverdraftInfo {
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money availableLimit;
    protected long cardId;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money currentOverdraftSum;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money limit;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money minimalPayment;
    protected java.util.Calendar minimalPaymentDate;
    protected java.util.Calendar openDate;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money ownSum;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money rate;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money technicalOverdraftSum;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money technicalPenalty;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money totalDebtSum;
    protected java.util.Calendar unsetltedDebtCreateDate;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money unsettledDebtSum;
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Money unsettledPenalty;
    
    public OverdraftInfo() {
    }
    
    public OverdraftInfo(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money availableLimit, long cardId, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money currentOverdraftSum, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money limit, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money minimalPayment, java.util.Calendar minimalPaymentDate, java.util.Calendar openDate, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money ownSum, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money rate, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money technicalOverdraftSum, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money technicalPenalty, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money totalDebtSum, java.util.Calendar unsetltedDebtCreateDate, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money unsettledDebtSum, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money unsettledPenalty) {
        this.availableLimit = availableLimit;
        this.cardId = cardId;
        this.currentOverdraftSum = currentOverdraftSum;
        this.limit = limit;
        this.minimalPayment = minimalPayment;
        this.minimalPaymentDate = minimalPaymentDate;
        this.openDate = openDate;
        this.ownSum = ownSum;
        this.rate = rate;
        this.technicalOverdraftSum = technicalOverdraftSum;
        this.technicalPenalty = technicalPenalty;
        this.totalDebtSum = totalDebtSum;
        this.unsetltedDebtCreateDate = unsetltedDebtCreateDate;
        this.unsettledDebtSum = unsettledDebtSum;
        this.unsettledPenalty = unsettledPenalty;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getAvailableLimit() {
        return availableLimit;
    }
    
    public void setAvailableLimit(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money availableLimit) {
        this.availableLimit = availableLimit;
    }
    
    public long getCardId() {
        return cardId;
    }
    
    public void setCardId(long cardId) {
        this.cardId = cardId;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getCurrentOverdraftSum() {
        return currentOverdraftSum;
    }
    
    public void setCurrentOverdraftSum(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money currentOverdraftSum) {
        this.currentOverdraftSum = currentOverdraftSum;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getLimit() {
        return limit;
    }
    
    public void setLimit(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money limit) {
        this.limit = limit;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getMinimalPayment() {
        return minimalPayment;
    }
    
    public void setMinimalPayment(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money minimalPayment) {
        this.minimalPayment = minimalPayment;
    }
    
    public java.util.Calendar getMinimalPaymentDate() {
        return minimalPaymentDate;
    }
    
    public void setMinimalPaymentDate(java.util.Calendar minimalPaymentDate) {
        this.minimalPaymentDate = minimalPaymentDate;
    }
    
    public java.util.Calendar getOpenDate() {
        return openDate;
    }
    
    public void setOpenDate(java.util.Calendar openDate) {
        this.openDate = openDate;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getOwnSum() {
        return ownSum;
    }
    
    public void setOwnSum(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money ownSum) {
        this.ownSum = ownSum;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getRate() {
        return rate;
    }
    
    public void setRate(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money rate) {
        this.rate = rate;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getTechnicalOverdraftSum() {
        return technicalOverdraftSum;
    }
    
    public void setTechnicalOverdraftSum(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money technicalOverdraftSum) {
        this.technicalOverdraftSum = technicalOverdraftSum;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getTechnicalPenalty() {
        return technicalPenalty;
    }
    
    public void setTechnicalPenalty(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money technicalPenalty) {
        this.technicalPenalty = technicalPenalty;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getTotalDebtSum() {
        return totalDebtSum;
    }
    
    public void setTotalDebtSum(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money totalDebtSum) {
        this.totalDebtSum = totalDebtSum;
    }
    
    public java.util.Calendar getUnsetltedDebtCreateDate() {
        return unsetltedDebtCreateDate;
    }
    
    public void setUnsetltedDebtCreateDate(java.util.Calendar unsetltedDebtCreateDate) {
        this.unsetltedDebtCreateDate = unsetltedDebtCreateDate;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getUnsettledDebtSum() {
        return unsettledDebtSum;
    }
    
    public void setUnsettledDebtSum(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money unsettledDebtSum) {
        this.unsettledDebtSum = unsettledDebtSum;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Money getUnsettledPenalty() {
        return unsettledPenalty;
    }
    
    public void setUnsettledPenalty(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money unsettledPenalty) {
        this.unsettledPenalty = unsettledPenalty;
    }
}
