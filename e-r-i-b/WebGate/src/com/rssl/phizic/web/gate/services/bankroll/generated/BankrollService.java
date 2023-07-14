// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;

public interface BankrollService extends java.rmi.Remote {
    public com.rssl.phizic.web.gate.services.bankroll.generated.AccountInfo __forGenerateAccountInfo() throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.AccountTransaction __forGenerateAccountTransaction() throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo __forGenerateCardInfo() throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.CardOperation __forGenerateCardOperation() throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.ClientDocument __forGenerateClientDocument() throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.IKFLException __forGenerateIKFLException() throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo __forGenerateOverdraftInfo() throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.Trustee __forGenerateTrustee() throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.AccountAbstract getAccHistoryFullExtract(com.rssl.phizic.web.gate.services.bankroll.generated.Account account_1, java.util.Calendar calendar_2, java.util.Calendar calendar_3, java.lang.Boolean boolean_4) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getAccount(java.lang.String[] arrayOfString_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getAccountAbstract(java.lang.Long long_1, com.rssl.phizic.web.gate.services.bankroll.generated.Account[] arrayOfAccount_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.AccountAbstract getAccountAbstract2(com.rssl.phizic.web.gate.services.bankroll.generated.Account account_1, java.util.Calendar calendar_2, java.util.Calendar calendar_3, java.lang.Boolean boolean_4) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getAccountByNumber(com.rssl.phizic.web.gate.services.bankroll.generated.Pair[] arrayOfPair_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.AccountAbstract getAccountExtendedAbstract(com.rssl.phizic.web.gate.services.bankroll.generated.Account account_1, java.util.Calendar calendar_2, java.util.Calendar calendar_3) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getAdditionalCards(com.rssl.phizic.web.gate.services.bankroll.generated.Card[] arrayOfCard_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getCard(java.lang.String[] arrayOfString_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getCardAbstract(java.lang.Long long_1, com.rssl.phizic.web.gate.services.bankroll.generated.Card[] arrayOfCard_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.CardAbstract getCardAbstract2(com.rssl.phizic.web.gate.services.bankroll.generated.Card card_1, java.util.Calendar calendar_2, java.util.Calendar calendar_3, java.lang.Boolean boolean_4) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getCardByNumber(com.rssl.phizic.web.gate.services.bankroll.generated.Client client_1, com.rssl.phizic.web.gate.services.bankroll.generated.Pair[] arrayOfPair_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getCardPrimaryAccount(com.rssl.phizic.web.gate.services.bankroll.generated.Card[] arrayOfCard_1) throws 
         java.rmi.RemoteException;
    public java.util.List getClientAccounts(com.rssl.phizic.web.gate.services.bankroll.generated.Client client_1) throws 
         java.rmi.RemoteException;
    public java.util.List getClientCards(com.rssl.phizic.web.gate.services.bankroll.generated.Client client_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getOwnerInfo(com.rssl.phizic.web.gate.services.bankroll.generated.Account[] arrayOfAccount_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getOwnerInfo2(com.rssl.phizic.web.gate.services.bankroll.generated.Card[] arrayOfCard_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult getOwnerInfoByCardNumber(com.rssl.phizic.web.gate.services.bankroll.generated.Pair[] arrayOfPair_1) throws 
         java.rmi.RemoteException;
}
