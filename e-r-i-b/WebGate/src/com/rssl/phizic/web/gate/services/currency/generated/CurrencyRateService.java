// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.currency.generated;

public interface CurrencyRateService extends java.rmi.Remote {
    public com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate convert(com.rssl.phizic.web.gate.services.currency.generated.Currency currency_1, com.rssl.phizic.web.gate.services.currency.generated.Money money_2, com.rssl.phizic.web.gate.services.currency.generated.Office office_3, java.lang.String string_4) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate convert2(com.rssl.phizic.web.gate.services.currency.generated.Money money_1, com.rssl.phizic.web.gate.services.currency.generated.Currency currency_2, com.rssl.phizic.web.gate.services.currency.generated.Office office_3, java.lang.String string_4) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate getRate(com.rssl.phizic.web.gate.services.currency.generated.Currency currency_1, com.rssl.phizic.web.gate.services.currency.generated.Currency currency_2, java.lang.String string_3, com.rssl.phizic.web.gate.services.currency.generated.Office office_4, java.lang.String string_5) throws 
         java.rmi.RemoteException;
}