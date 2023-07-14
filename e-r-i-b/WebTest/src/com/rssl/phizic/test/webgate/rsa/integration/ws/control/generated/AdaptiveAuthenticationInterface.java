/**
 * AdaptiveAuthenticationInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public interface AdaptiveAuthenticationInterface extends java.rmi.Remote {
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.NotifyResponse notify(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.NotifyRequest request) throws java.rmi.RemoteException, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SoapFaultType;
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.QueryResponse query(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.QueryRequest request) throws java.rmi.RemoteException, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SoapFaultType;
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AnalyzeResponse analyze(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AnalyzeRequest request) throws java.rmi.RemoteException, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SoapFaultType;
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AuthenticateResponse authenticate(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AuthenticateRequest request) throws java.rmi.RemoteException, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SoapFaultType;
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeResponse challenge(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeRequest request) throws java.rmi.RemoteException, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SoapFaultType;
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CreateUserResponse createUser(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CreateUserRequest request) throws java.rmi.RemoteException, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SoapFaultType;
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.QueryAuthStatusResponse queryAuthStatus(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.QueryAuthStatusRequest request) throws java.rmi.RemoteException, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SoapFaultType;
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UpdateUserResponse updateUser(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UpdateUserRequest request) throws java.rmi.RemoteException, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SoapFaultType;
}
