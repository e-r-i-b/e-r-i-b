/**
 * OfflineSrv.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.listener.sbrf;

public interface OfflineSrv extends javax.xml.rpc.Service {
    public java.lang.String getOfflineSrvSoapAddress();

    public com.rssl.phizic.ws.listener.sbrf.OfflineSrvSoap_PortType getOfflineSrvSoap() throws javax.xml.rpc.ServiceException;

    public com.rssl.phizic.ws.listener.sbrf.OfflineSrvSoap_PortType getOfflineSrvSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
