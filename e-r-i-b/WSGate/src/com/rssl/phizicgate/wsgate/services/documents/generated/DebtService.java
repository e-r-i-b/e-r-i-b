// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;

public interface DebtService extends java.rmi.Remote {
    public com.rssl.phizicgate.wsgate.services.documents.generated.DebtImpl __forGenerateDebt() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.documents.generated.DebtRowImpl __forGenerateDebtRow() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.documents.generated.Field __forGenerateField() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.documents.generated.ListValue __forGenerateListValue() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.documents.generated.Pair __forGeneratePair() throws 
         java.rmi.RemoteException;
    public java.util.List getDebts(com.rssl.phizicgate.wsgate.services.documents.generated.RecipientImpl recipientImpl_1, java.util.List list_2) throws 
         java.rmi.RemoteException;
}