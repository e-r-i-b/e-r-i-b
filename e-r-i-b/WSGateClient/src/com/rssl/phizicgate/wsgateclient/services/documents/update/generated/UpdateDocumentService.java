// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.documents.update.generated;

public interface UpdateDocumentService extends java.rmi.Remote {
    public com.rssl.phizicgate.wsgateclient.services.documents.update.generated.Field __forField() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgateclient.services.documents.update.generated.FieldValidationRule __forGenerateFieldValidationRule() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgateclient.services.documents.update.generated.QuestionnaireAnswer __forGenerateQuestionnaireAnswer() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgateclient.services.documents.update.generated.WriteDownOperation __forGenerateWriteDownOperation() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgateclient.services.documents.update.generated.ListValue __forGeneratedListValue() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument find(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.List findUnRegisteredPayments(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State state_1) throws 
         java.rmi.RemoteException;
    public java.util.List findUnRegisteredPayments2(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.State state_1, java.lang.String string_2, java.lang.Integer integer_3, java.lang.Integer integer_4) throws 
         java.rmi.RemoteException;
    public void update(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument gateDocument_1) throws 
         java.rmi.RemoteException;
    public void update2(com.rssl.phizicgate.wsgateclient.services.documents.update.generated.GateDocument gateDocument_1, com.rssl.phizicgate.wsgateclient.services.documents.update.generated.DocumentCommand documentCommand_2) throws 
         java.rmi.RemoteException;
}
