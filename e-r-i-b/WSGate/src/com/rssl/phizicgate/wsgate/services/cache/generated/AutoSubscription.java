// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;


public class AutoSubscription {
    protected com.rssl.phizicgate.wsgate.services.cache.generated.Money amount;
    protected java.lang.String autoPayStatusType;
    protected java.util.Calendar endDate;
    protected java.lang.String executionEventType;
    protected java.lang.String externalId;
    protected java.util.Calendar nextPayDate;
    protected long number;
    protected com.rssl.phizicgate.wsgate.services.cache.generated.Office office;
    protected java.lang.Long payDay;
    protected com.rssl.phizicgate.wsgate.services.cache.generated.Money percent;
    protected java.util.Calendar startDate;
    protected java.lang.String sumType;
    protected java.lang.String type;
    
    public AutoSubscription() {
    }
    
    public AutoSubscription(com.rssl.phizicgate.wsgate.services.cache.generated.Money amount, java.lang.String autoPayStatusType, java.util.Calendar endDate, java.lang.String executionEventType, java.lang.String externalId, java.util.Calendar nextPayDate, long number, com.rssl.phizicgate.wsgate.services.cache.generated.Office office, java.lang.Long payDay, com.rssl.phizicgate.wsgate.services.cache.generated.Money percent, java.util.Calendar startDate, java.lang.String sumType, java.lang.String type) {
        this.amount = amount;
        this.autoPayStatusType = autoPayStatusType;
        this.endDate = endDate;
        this.executionEventType = executionEventType;
        this.externalId = externalId;
        this.nextPayDate = nextPayDate;
        this.number = number;
        this.office = office;
        this.payDay = payDay;
        this.percent = percent;
        this.startDate = startDate;
        this.sumType = sumType;
        this.type = type;
    }
    
    public com.rssl.phizicgate.wsgate.services.cache.generated.Money getAmount() {
        return amount;
    }
    
    public void setAmount(com.rssl.phizicgate.wsgate.services.cache.generated.Money amount) {
        this.amount = amount;
    }
    
    public java.lang.String getAutoPayStatusType() {
        return autoPayStatusType;
    }
    
    public void setAutoPayStatusType(java.lang.String autoPayStatusType) {
        this.autoPayStatusType = autoPayStatusType;
    }
    
    public java.util.Calendar getEndDate() {
        return endDate;
    }
    
    public void setEndDate(java.util.Calendar endDate) {
        this.endDate = endDate;
    }
    
    public java.lang.String getExecutionEventType() {
        return executionEventType;
    }
    
    public void setExecutionEventType(java.lang.String executionEventType) {
        this.executionEventType = executionEventType;
    }
    
    public java.lang.String getExternalId() {
        return externalId;
    }
    
    public void setExternalId(java.lang.String externalId) {
        this.externalId = externalId;
    }
    
    public java.util.Calendar getNextPayDate() {
        return nextPayDate;
    }
    
    public void setNextPayDate(java.util.Calendar nextPayDate) {
        this.nextPayDate = nextPayDate;
    }
    
    public long getNumber() {
        return number;
    }
    
    public void setNumber(long number) {
        this.number = number;
    }
    
    public com.rssl.phizicgate.wsgate.services.cache.generated.Office getOffice() {
        return office;
    }
    
    public void setOffice(com.rssl.phizicgate.wsgate.services.cache.generated.Office office) {
        this.office = office;
    }
    
    public java.lang.Long getPayDay() {
        return payDay;
    }
    
    public void setPayDay(java.lang.Long payDay) {
        this.payDay = payDay;
    }
    
    public com.rssl.phizicgate.wsgate.services.cache.generated.Money getPercent() {
        return percent;
    }
    
    public void setPercent(com.rssl.phizicgate.wsgate.services.cache.generated.Money percent) {
        this.percent = percent;
    }
    
    public java.util.Calendar getStartDate() {
        return startDate;
    }
    
    public void setStartDate(java.util.Calendar startDate) {
        this.startDate = startDate;
    }
    
    public java.lang.String getSumType() {
        return sumType;
    }
    
    public void setSumType(java.lang.String sumType) {
        this.sumType = sumType;
    }
    
    public java.lang.String getType() {
        return type;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
}
