/**
 * XsbCheckNames_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public interface XsbCheckNames_PortType extends java.rmi.Remote {
    public com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn checkRemoteClientName(java.lang.String xsbDocument, com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters) throws java.rmi.RemoteException;
    public com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn readRemoteClientName(java.lang.String xsbDocument, com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters) throws java.rmi.RemoteException;
    public com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedReturn readRemoteClientNameExtended(java.lang.String xsbDocument, com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters) throws java.rmi.RemoteException;
}
