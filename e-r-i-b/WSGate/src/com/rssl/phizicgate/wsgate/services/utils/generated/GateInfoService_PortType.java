// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.generated;

public interface GateInfoService_PortType extends java.rmi.Remote {
    public java.lang.String getAccountInputMode(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.String getCardInputMode(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration getConfiguration(com.rssl.phizicgate.wsgate.services.utils.generated.Billing billing_1) throws 
         java.rmi.RemoteException;
    public java.lang.String getUID(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isCalendarAvailable(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isClientImportEnable(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isCurrencyRateAvailable(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isDelayedPaymentNeedSend(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isNeedAgrementCancellation(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isNeedChargeOff(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isOfficesHierarchySupported(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isPaymentCommissionAvailable(com.rssl.phizicgate.wsgate.services.utils.generated.Billing billing_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isPaymentCommissionAvailable2(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isPaymentsRecallSupported(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isPersonalRecipientAvailable(com.rssl.phizicgate.wsgate.services.utils.generated.Billing billing_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isRecipientExtedendAttributesAvailable(com.rssl.phizicgate.wsgate.services.utils.generated.Billing billing_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean isRegistrationEnable(com.rssl.phizicgate.wsgate.services.utils.generated.Office office_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean needTwoPhaseTransaction(com.rssl.phizicgate.wsgate.services.utils.generated.Billing billing_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean needTwoPhaseTransaction2(java.lang.String string_1) throws 
         java.rmi.RemoteException;
}
