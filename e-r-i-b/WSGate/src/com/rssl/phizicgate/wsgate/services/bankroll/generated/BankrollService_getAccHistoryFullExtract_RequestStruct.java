// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;


public class BankrollService_getAccHistoryFullExtract_RequestStruct {
    protected com.rssl.phizicgate.wsgate.services.bankroll.generated.Account account_1;
    protected java.util.Calendar calendar_2;
    protected java.util.Calendar calendar_3;
    protected java.lang.Boolean boolean_4;
    
    public BankrollService_getAccHistoryFullExtract_RequestStruct() {
    }
    
    public BankrollService_getAccHistoryFullExtract_RequestStruct(com.rssl.phizicgate.wsgate.services.bankroll.generated.Account account_1, java.util.Calendar calendar_2, java.util.Calendar calendar_3, java.lang.Boolean boolean_4) {
        this.account_1 = account_1;
        this.calendar_2 = calendar_2;
        this.calendar_3 = calendar_3;
        this.boolean_4 = boolean_4;
    }
    
    public com.rssl.phizicgate.wsgate.services.bankroll.generated.Account getAccount_1() {
        return account_1;
    }
    
    public void setAccount_1(com.rssl.phizicgate.wsgate.services.bankroll.generated.Account account_1) {
        this.account_1 = account_1;
    }
    
    public java.util.Calendar getCalendar_2() {
        return calendar_2;
    }
    
    public void setCalendar_2(java.util.Calendar calendar_2) {
        this.calendar_2 = calendar_2;
    }
    
    public java.util.Calendar getCalendar_3() {
        return calendar_3;
    }
    
    public void setCalendar_3(java.util.Calendar calendar_3) {
        this.calendar_3 = calendar_3;
    }
    
    public java.lang.Boolean getBoolean_4() {
        return boolean_4;
    }
    
    public void setBoolean_4(java.lang.Boolean boolean_4) {
        this.boolean_4 = boolean_4;
    }
}
