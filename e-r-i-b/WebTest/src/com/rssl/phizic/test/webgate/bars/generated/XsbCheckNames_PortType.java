/**
 * XsbCheckNames_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.bars.generated;

public interface XsbCheckNames_PortType extends java.rmi.Remote {
    public com.rssl.phizic.test.webgate.bars.generated.XsbChecksReturn checkRemoteClientName(java.lang.String xsbDocument, com.rssl.phizic.test.webgate.bars.generated.XsbParameter[] parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.test.webgate.bars.generated.XsbRemoteClientNameReturn readRemoteClientName(java.lang.String xsbDocument, com.rssl.phizic.test.webgate.bars.generated.XsbParameter[] parameters) throws java.rmi.RemoteException;
    public com.rssl.phizic.test.webgate.bars.generated.XsbRemoteClientNameExtendedReturn readRemoteClientNameExtended(java.lang.String xsbDocument, com.rssl.phizic.test.webgate.bars.generated.XsbParameter[] parameters) throws java.rmi.RemoteException;
}
