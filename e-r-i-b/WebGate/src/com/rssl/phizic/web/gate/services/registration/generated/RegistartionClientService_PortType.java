// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.registration.generated;

public interface RegistartionClientService_PortType extends java.rmi.Remote {
    public com.rssl.phizic.web.gate.services.registration.generated.CancelationCallBackImpl cancellation(com.rssl.phizic.web.gate.services.registration.generated.Client client_1, java.lang.String string_2, java.util.Calendar calendar_3, java.lang.String string_4, java.lang.String string_5) throws 
         java.rmi.RemoteException;
    public void register(com.rssl.phizic.web.gate.services.registration.generated.Office office_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
    public void update(com.rssl.phizic.web.gate.services.registration.generated.Client client_1, java.util.Calendar calendar_2, boolean boolean_3, com.rssl.phizic.web.gate.services.registration.generated.User user_4) throws 
         java.rmi.RemoteException;
    public void update2(com.rssl.phizic.web.gate.services.registration.generated.Office office_1, java.lang.String string_2) throws 
         java.rmi.RemoteException;
}
