// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.clients.generated;

public interface UpdatePersonService_PortType extends java.rmi.Remote {
    public void lockOnUnlock(java.lang.String string_1, java.util.Calendar date_2, java.lang.Boolean boolean_3, com.rssl.phizic.wsgate.clients.generated.Money money_4) throws 
         java.rmi.RemoteException;
    public void updateState(com.rssl.phizic.wsgate.clients.generated.CancelationCallBackImpl cancelationCallBackImpl_1, com.rssl.phizic.wsgate.clients.generated.ClientState clientState_2) throws 
         java.rmi.RemoteException;
    public void updateState2(java.lang.String string_1, com.rssl.phizic.wsgate.clients.generated.ClientState clientState_2) throws 
         java.rmi.RemoteException;
}
