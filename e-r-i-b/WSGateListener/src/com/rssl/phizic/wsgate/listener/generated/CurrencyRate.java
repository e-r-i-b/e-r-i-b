// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.listener.generated;


public class CurrencyRate {
    protected java.lang.String dynamicExchangeRate;
    protected com.rssl.phizic.wsgate.listener.generated.Currency fromCurrency;
    protected java.math.BigDecimal fromValue;
    protected java.lang.String tarifPlanCodeType;
    protected com.rssl.phizic.wsgate.listener.generated.Currency toCurrency;
    protected java.math.BigDecimal toValue;
    protected java.lang.String type;
    
    public CurrencyRate() {
    }
    
    public CurrencyRate(java.lang.String dynamicExchangeRate, com.rssl.phizic.wsgate.listener.generated.Currency fromCurrency, java.math.BigDecimal fromValue, java.lang.String tarifPlanCodeType, com.rssl.phizic.wsgate.listener.generated.Currency toCurrency, java.math.BigDecimal toValue, java.lang.String type) {
        this.dynamicExchangeRate = dynamicExchangeRate;
        this.fromCurrency = fromCurrency;
        this.fromValue = fromValue;
        this.tarifPlanCodeType = tarifPlanCodeType;
        this.toCurrency = toCurrency;
        this.toValue = toValue;
        this.type = type;
    }
    
    public java.lang.String getDynamicExchangeRate() {
        return dynamicExchangeRate;
    }
    
    public void setDynamicExchangeRate(java.lang.String dynamicExchangeRate) {
        this.dynamicExchangeRate = dynamicExchangeRate;
    }
    
    public com.rssl.phizic.wsgate.listener.generated.Currency getFromCurrency() {
        return fromCurrency;
    }
    
    public void setFromCurrency(com.rssl.phizic.wsgate.listener.generated.Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    
    public java.math.BigDecimal getFromValue() {
        return fromValue;
    }
    
    public void setFromValue(java.math.BigDecimal fromValue) {
        this.fromValue = fromValue;
    }
    
    public java.lang.String getTarifPlanCodeType() {
        return tarifPlanCodeType;
    }
    
    public void setTarifPlanCodeType(java.lang.String tarifPlanCodeType) {
        this.tarifPlanCodeType = tarifPlanCodeType;
    }
    
    public com.rssl.phizic.wsgate.listener.generated.Currency getToCurrency() {
        return toCurrency;
    }
    
    public void setToCurrency(com.rssl.phizic.wsgate.listener.generated.Currency toCurrency) {
        this.toCurrency = toCurrency;
    }
    
    public java.math.BigDecimal getToValue() {
        return toValue;
    }
    
    public void setToValue(java.math.BigDecimal toValue) {
        this.toValue = toValue;
    }
    
    public java.lang.String getType() {
        return type;
    }
    
    public void setType(java.lang.String type) {
        this.type = type;
    }
}
