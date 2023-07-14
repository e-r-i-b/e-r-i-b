// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.cache.generated;

public interface CacheService extends java.rmi.Remote {
    public void clearAccountCache(com.rssl.phizic.web.gate.services.cache.generated.Account account_1) throws 
         java.rmi.RemoteException;
    public void clearAutoPaymentCache(com.rssl.phizic.web.gate.services.cache.generated.AutoPayment autoPayment_1, com.rssl.phizic.web.gate.services.cache.generated.Card[] arrayOfCard_2) throws 
         java.rmi.RemoteException;
    public void clearAutoSubscriptionCache(com.rssl.phizic.web.gate.services.cache.generated.AutoSubscription autoSubscription_1) throws 
         java.rmi.RemoteException;
    public void clearCardCache(com.rssl.phizic.web.gate.services.cache.generated.Card card_1) throws 
         java.rmi.RemoteException;
    public void clearClientCache(com.rssl.phizic.web.gate.services.cache.generated.Client client_1) throws 
         java.rmi.RemoteException;
    public void clearClientProductsCache(com.rssl.phizic.web.gate.services.cache.generated.Client client_1, java.lang.String[] arrayOfString_2) throws 
         java.rmi.RemoteException;
    public void clearCurrencyRateCache(com.rssl.phizic.web.gate.services.cache.generated.CurrencyRate currencyRate_1, com.rssl.phizic.web.gate.services.cache.generated.Office office_2) throws 
         java.rmi.RemoteException;
    public void clearDepoAccountCache(com.rssl.phizic.web.gate.services.cache.generated.DepoAccount depoAccount_1) throws 
         java.rmi.RemoteException;
    public void clearDepositCache(com.rssl.phizic.web.gate.services.cache.generated.Deposit deposit_1) throws 
         java.rmi.RemoteException;
    public void clearIMACache(com.rssl.phizic.web.gate.services.cache.generated.IMAccount IMAccount_1) throws 
         java.rmi.RemoteException;
    public void clearInsuranceAppCache(com.rssl.phizic.web.gate.services.cache.generated.InsuranceApp insuranceApp_1) throws 
         java.rmi.RemoteException;
    public void clearLoanCache(com.rssl.phizic.web.gate.services.cache.generated.Loan loan_1) throws 
         java.rmi.RemoteException;
    public void clearLongOfferCache(com.rssl.phizic.web.gate.services.cache.generated.LongOffer longOffer_1) throws 
         java.rmi.RemoteException;
    public void clearLoyaltyProgramCache(com.rssl.phizic.web.gate.services.cache.generated.LoyaltyProgram loyaltyProgram_1) throws 
         java.rmi.RemoteException;
    public void clearSecurityAccountCache(com.rssl.phizic.web.gate.services.cache.generated.SecurityAccount securityAccount_1) throws 
         java.rmi.RemoteException;
}
