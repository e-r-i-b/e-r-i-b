/**
 * ASFilialInfoService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;

public interface ASFilialInfoService extends java.rmi.Remote {
    public com.rssl.phizic.asfilial.listener.generated.QueryProfileRsType queryProfile(com.rssl.phizic.asfilial.listener.generated.QueryProfileRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.asfilial.listener.generated.UpdateProfileRsType updateProfile(com.rssl.phizic.asfilial.listener.generated.UpdateProfileRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRsType confirmPhoneHolder(com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRqType parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRsType requestPhoneHolder(com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRqType parameters) throws java.rmi.RemoteException;
}
