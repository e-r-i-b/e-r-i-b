// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.bankroll.generated;

public interface BackRefBankrollService_PortType extends java.rmi.Remote {
    public java.lang.String findAccountBusinessOwner(com.rssl.phizic.wsgate.bankroll.generated.Account account_1) throws 
         java.rmi.RemoteException;
    public java.lang.String findAccountExternalId(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.lang.String findCardExternalId(java.lang.Long long_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public java.lang.String findCardExternalId2(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.wsgate.bankroll.generated.Office getAccountOffice(java.lang.Long long_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.wsgate.bankroll.generated.Account getCardAccount(java.lang.Long long_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.wsgate.bankroll.generated.Account getCardAccount2(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.wsgate.bankroll.generated.Office getCardOffice(java.lang.Long long_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.wsgate.bankroll.generated.Card getStoredCard(java.lang.Long long_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
}