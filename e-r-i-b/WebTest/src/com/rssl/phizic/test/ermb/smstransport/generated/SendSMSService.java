/**
 * SendSMSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.ermb.smstransport.generated;

public interface SendSMSService extends java.rmi.Remote {
    public com.rssl.phizic.common.types.ermb.generated.SendSMSRsType sendSMS(com.rssl.phizic.common.types.ermb.generated.SendSMSRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.common.types.ermb.generated.SendSMSRsType sendSMSWithIMSI(com.rssl.phizic.common.types.ermb.generated.SendSMSRqType parameters) throws java.rmi.RemoteException;
}
