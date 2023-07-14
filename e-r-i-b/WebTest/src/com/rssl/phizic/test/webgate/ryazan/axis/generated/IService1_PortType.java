/**
 * IService1_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.ryazan.axis.generated;

public interface IService1_PortType extends java.rmi.Remote {
    public java.lang.String echoString(java.lang.String value) throws java.rmi.RemoteException;
    public java.lang.String requestAttr(java.lang.String value) throws java.rmi.RemoteException;
    public java.lang.String preparePayment(java.lang.String value) throws java.rmi.RemoteException;
    public java.lang.String executePayment(java.lang.String value) throws java.rmi.RemoteException;
    public java.lang.String revokePayment(java.lang.String value) throws java.rmi.RemoteException;
}
