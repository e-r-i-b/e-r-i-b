// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.bankroll.generated;


public class Money {
    protected com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency currency;
    protected java.math.BigDecimal decimal;
    
    public Money() {
    }
    
    public Money(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency currency, java.math.BigDecimal decimal) {
        this.currency = currency;
        this.decimal = decimal;
    }
    
    public com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency currency) {
        this.currency = currency;
    }
    
    public java.math.BigDecimal getDecimal() {
        return decimal;
    }
    
    public void setDecimal(java.math.BigDecimal decimal) {
        this.decimal = decimal;
    }
}
