// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated;

public interface PaymentRecipientGateService extends java.rmi.Remote {
    public com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.DebtRowImpl __forGenerateDebtRowImpl() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field __forGenerateField() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.FieldValidationRule __forGenerateFieldValidationRule() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.ListValue __forGeneratedListValue() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Pair __forPair() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Client getCardOwner(java.lang.String string_1, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing billing_2) throws 
         java.rmi.RemoteException;
    public java.util.List getPersonalRecipientFieldsValues(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipient_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public java.util.List getPersonalRecipientList(java.lang.String string_1, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing billing_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient getRecipient(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.List getRecipientFields(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipient_1, java.util.List list_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.RecipientInfo getRecipientInfo(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipient_1, java.util.List list_2, java.lang.String string_3) throws 
         java.rmi.RemoteException;
    public java.util.List getRecipientKeyFields(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipient_1) throws 
         java.rmi.RemoteException;
    public java.util.List getRecipientList(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing billing_1) throws 
         java.rmi.RemoteException;
    public java.util.List getRecipientList2(java.lang.String string_1, java.lang.String string_2, java.lang.String string_3, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing billing_4) throws 
         java.rmi.RemoteException;
}
