package com.rssl.phizic.web.gate.services.documents.types;

/**
 * @author egorova
 * @ created 25.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class Money
{
    protected Currency currency;
    protected java.math.BigDecimal decimal;

    public Money() {
    }

    public Money(Currency currency, java.math.BigDecimal decimal) {
        this.currency = currency;
        this.decimal = decimal;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public java.math.BigDecimal getDecimal() {
        return decimal;
    }

    public void setDecimal(java.math.BigDecimal decimal) {
        this.decimal = decimal;
    }
}
