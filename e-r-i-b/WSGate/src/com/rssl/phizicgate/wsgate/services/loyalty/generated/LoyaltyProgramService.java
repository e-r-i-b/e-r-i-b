// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.loyalty.generated;

public interface LoyaltyProgramService extends java.rmi.Remote {
    public com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyOffer __forGenerateLoyaltyOffer() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation __forGenerateLoyaltyProgramOperation() throws 
         java.rmi.RemoteException;
    public com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram getClientLoyaltyProgram(java.lang.String string_1) throws 
         java.rmi.RemoteException;
    public java.util.List getLoyaltyOffers(com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram loyaltyProgram_1) throws 
         java.rmi.RemoteException;
    public java.util.List getLoyaltyOperationInfo(com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram loyaltyProgram_1) throws 
         java.rmi.RemoteException;
}
