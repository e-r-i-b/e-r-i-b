// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.documents.generated;


public class CurrencyRate {
    protected com.rssl.phizic.web.gate.services.documents.generated.Currency fromCurrency;
    protected java.math.BigDecimal fromValue;
    protected java.lang.String tarifPlanCodeType;
    protected com.rssl.phizic.web.gate.services.documents.generated.Currency toCurrency;
    protected java.math.BigDecimal toValue;
    protected java.lang.String type;
    
    public CurrencyRate() {
    }
    
    public CurrencyRate(com.rssl.phizic.web.gate.services.documents.generated.Currency fromCurrency, java.math.BigDecimal fromValue, java.lang.String tarifPlanCodeType, com.rssl.phizic.web.gate.services.documents.generated.Currency toCurrency, java.math.BigDecimal toValue, java.lang.String type) {
        this.fromCurrency = fromCurrency;
        this.fromValue = fromValue;
        this.tarifPlanCodeType = tarifPlanCodeType;
        this.toCurrency = toCurrency;
        this.toValue = toValue;
        this.type = type;
    }
    
    public com.rssl.phizic.web.gate.services.documents.generated.Currency getFromCurrency() {
        return fromCurrency;
    }
    
    public void setFromCurrency(com.rssl.phizic.web.gate.services.documents.generated.Currency fromCurrency) {
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
    
    public com.rssl.phizic.web.gate.services.documents.generated.Currency getToCurrency() {
        return toCurrency;
    }
    
    public void setToCurrency(com.rssl.phizic.web.gate.services.documents.generated.Currency toCurrency) {
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
