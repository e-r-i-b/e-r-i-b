// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.way4.ws.generated;

public interface IPASWSSoap extends java.rmi.Remote {
    public com.rssl.phizic.authgate.way4.ws.generated.VerifyRsType verifyPassword(java.lang.String STAN, java.lang.String userId, java.lang.String password) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.authgate.way4.ws.generated.PrepareOTPRsType prepareOTP(java.lang.String STAN, java.lang.String userId) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.authgate.way4.ws.generated.VerifyAttRsType verifyOTP(java.lang.String STAN, java.lang.String SID, java.lang.String password) throws 
         java.rmi.RemoteException;
    public com.rssl.phizic.authgate.way4.ws.generated.GeneratePasswordRsType generatePassword(java.lang.String STAN, java.lang.String userId, java.lang.String password) throws 
         java.rmi.RemoteException;
}
