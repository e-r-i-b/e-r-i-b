// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

public interface MobileBankService extends java.rmi.Remote {
    public void addRegistration(com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client client_1, java.lang.String string_2, java.lang.String string_3, java.lang.String string_4, java.lang.String string_5) throws 
         java.rmi.RemoteException;
    public void addTemplates(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo mobileBankCardInfo_1, java.util.List list_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.BeginMigrationResult beginMigrationErmb(java.util.Set set_1, java.util.Set set_2, java.util.Set set_3, java.lang.String string_4) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.CommitMigrationResult[] commitMigrationErmb(java.lang.Long long_1) throws 
         java.rmi.RemoteException;
    public void confirmMbRegistrationsLoading(java.util.List list_1) throws 
         java.rmi.RemoteException;
    public void confirmUESIMessages(java.util.List list_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean generatePassword(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.lang.Boolean generatePasswordForErmbClient(java.lang.String string_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.Card getCardByPhone(com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client client_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public java.lang.String getCardNumberByPhone(com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client client_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public java.util.Set getCardsByPhone(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.Set getCardsByPhoneViaReportDB(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfo getClientByCardNumber(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfo getClientByLogin(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfo getClientByPhone(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.List getDisconnectedPhones(int int_1) throws 
         java.rmi.RemoteException;
    public java.util.List getMbkRegistrationsForErmb() throws 
         java.rmi.RemoteException;
    public java.lang.String getMobileContact(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.List getMobilePaymentCardRequests() throws 
         java.rmi.RemoteException;
    public java.util.List getOfferConfirm() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.GatePaymentTemplate getPaymentTemplate(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.GroupResult getPaymentTemplates(java.lang.String[] arrayOfString_1) throws 
         java.rmi.RemoteException;
    public java.util.List getPhonesForPeriod(java.util.Calendar calendar_1) throws 
         java.rmi.RemoteException;
    public java.lang.String getQuickServiceStatus(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.Set getRegPhonesByCardNumbers(java.util.List list_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.GroupResult getRegistrations(java.lang.Boolean boolean_1, java.lang.String[] arrayOfString_2) throws 
         java.rmi.RemoteException;
    public java.util.List getRegistrations3(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.List getRegistrations4(java.lang.String string_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public java.util.List getRegistrationsPack(java.lang.Boolean boolean_1, java.lang.String[] arrayOfString_2) throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.mobilebank.generated.GroupResult getTemplates(java.lang.Long long_1, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo[] arrayOfMobileBankCardInfo_2) throws 
         java.rmi.RemoteException;
    public java.util.List getUESIMessagesFromMBK() throws 
         java.rmi.RemoteException;
    public void methodForAddClassesToWSDL(com.rssl.phizicgate.wsgate.services.mobilebank.generated.CommitMigrationResult commitMigrationResult_1, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankTemplate mobileBankTemplate_2, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkCard mbkCard_3, com.rssl.phizicgate.wsgate.services.mobilebank.generated.DisconnectedPhoneResult disconnectedPhoneResult_4, com.rssl.phizicgate.wsgate.services.mobilebank.generated.P2PRequest p2PRequest_5, com.rssl.phizicgate.wsgate.services.mobilebank.generated.AcceptInfo acceptInfo_6, com.rssl.phizicgate.wsgate.services.mobilebank.generated.UESIMessage UESIMessage_7, com.rssl.phizicgate.wsgate.services.mobilebank.generated.ERMBPhone ERMBPhone_8, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration mobileBankRegistration_9, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone MBKPhone_10, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MQInfo MQInfo_11, com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo nodeInfo_12, com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfoCSA userInfoCSA_13, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKRegistration MBKRegistration_14, com.rssl.phizicgate.wsgate.services.mobilebank.generated.CodeOffice codeOffice_15, com.rssl.phizicgate.wsgate.services.mobilebank.generated.IKFLException IKFLException_16, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration3 mobileBankRegistration3_17) throws 
         java.rmi.RemoteException;
    public void removeTemplates(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo mobileBankCardInfo_1, java.util.List list_2) throws 
         java.rmi.RemoteException;
    public void reverseMigration(java.lang.Long long_1, com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientTariffInfo clientTariffInfo_2) throws 
         java.rmi.RemoteException;
    public void rollbackMigration(java.lang.Long long_1) throws 
         java.rmi.RemoteException;
    public java.util.Map sendIMSICheck(java.lang.String[] arrayOfString_1) throws 
         java.rmi.RemoteException;
    public void sendMbRegistrationProcessingResult(long long_1, java.lang.String string_2, java.lang.String string_3) throws 
         java.rmi.RemoteException;
    public void sendMobilePaymentCardResult(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobilePaymentCardResult mobilePaymentCardResult_1) throws 
         java.rmi.RemoteException;
    public void sendOfferMessageSMS(java.lang.String string_1, java.lang.String string_2, java.lang.Long long_3, java.lang.String string_4) throws 
         java.rmi.RemoteException;
    public void sendOfferQuit(java.lang.Long long_1) throws 
         java.rmi.RemoteException;
    public void sendSMS(java.lang.String string_1, java.lang.String string_2, int int_3, java.lang.String string_4) throws 
         java.rmi.RemoteException;
    public java.util.Map sendSMSWithIMSICheck(com.rssl.phizicgate.wsgate.services.mobilebank.generated.MessageInfo messageInfo_1, java.lang.String[] arrayOfString_2) throws 
         java.rmi.RemoteException;
    public java.lang.String setQuickServiceStatus(java.lang.String string_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public void updateDisconnectedPhonesState(java.util.List list_1) throws 
         java.rmi.RemoteException;
    public void updateErmbPhonesInMb(java.util.List list_1) throws 
         java.rmi.RemoteException;
}
