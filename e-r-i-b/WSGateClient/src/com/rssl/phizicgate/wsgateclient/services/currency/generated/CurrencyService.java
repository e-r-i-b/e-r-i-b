// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.currency.generated;

public interface CurrencyService extends java.rmi.Remote {
    public com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency findByAlphabeticCode(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency findById(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency findByNumericCode(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.List getAll() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency getNationalCurrency() throws 
         java.rmi.RemoteException;
}
