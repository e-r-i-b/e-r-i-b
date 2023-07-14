// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.documents.update.generated;


public class DebtImpl {
    protected java.lang.String accountNumber;
    protected java.lang.String code;
    protected java.lang.String description;
    protected java.lang.Boolean fixed;
    protected java.util.Calendar lastPayDate;
    protected java.util.Calendar period;
    protected java.util.List rows;
    
    public DebtImpl() {
    }
    
    public DebtImpl(java.lang.String accountNumber, java.lang.String code, java.lang.String description, java.lang.Boolean fixed, java.util.Calendar lastPayDate, java.util.Calendar period, java.util.List rows) {
        this.accountNumber = accountNumber;
        this.code = code;
        this.description = description;
        this.fixed = fixed;
        this.lastPayDate = lastPayDate;
        this.period = period;
        this.rows = rows;
    }
    
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public java.lang.String getCode() {
        return code;
    }
    
    public void setCode(java.lang.String code) {
        this.code = code;
    }
    
    public java.lang.String getDescription() {
        return description;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public java.lang.Boolean getFixed() {
        return fixed;
    }
    
    public void setFixed(java.lang.Boolean fixed) {
        this.fixed = fixed;
    }
    
    public java.util.Calendar getLastPayDate() {
        return lastPayDate;
    }
    
    public void setLastPayDate(java.util.Calendar lastPayDate) {
        this.lastPayDate = lastPayDate;
    }
    
    public java.util.Calendar getPeriod() {
        return period;
    }
    
    public void setPeriod(java.util.Calendar period) {
        this.period = period;
    }
    
    public java.util.List getRows() {
        return rows;
    }
    
    public void setRows(java.util.List rows) {
        this.rows = rows;
    }
}
