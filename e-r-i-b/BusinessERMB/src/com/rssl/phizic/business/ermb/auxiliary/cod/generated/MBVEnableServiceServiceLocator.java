/**
 * MBVEnableServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.ermb.auxiliary.cod.generated;

public class MBVEnableServiceServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableServiceService {

    public MBVEnableServiceServiceLocator() {
    }


    public MBVEnableServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MBVEnableServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MBVEnableService
    private java.lang.String MBVEnableService_address = "http://localhost:9081/mbv-web/services/MBVEnableService";

    public java.lang.String getMBVEnableServiceAddress() {
        return MBVEnableService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MBVEnableServiceWSDDServiceName = "MBVEnableService";

    public java.lang.String getMBVEnableServiceWSDDServiceName() {
        return MBVEnableServiceWSDDServiceName;
    }

    public void setMBVEnableServiceWSDDServiceName(java.lang.String name) {
        MBVEnableServiceWSDDServiceName = name;
    }

    public com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableService getMBVEnableService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MBVEnableService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMBVEnableService(endpoint);
    }

    public com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableService getMBVEnableService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableServiceSoapBindingStub _stub = new com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getMBVEnableServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMBVEnableServiceEndpointAddress(java.lang.String address) {
        MBVEnableService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableServiceSoapBindingStub _stub = new com.rssl.phizic.business.ermb.auxiliary.cod.generated.MBVEnableServiceSoapBindingStub(new java.net.URL(MBVEnableService_address), this);
                _stub.setPortName(getMBVEnableServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MBVEnableService".equals(inputPortName)) {
            return getMBVEnableService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.mbv.sbrf.ru", "MBVEnableServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.mbv.sbrf.ru", "MBVEnableService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MBVEnableService".equals(portName)) {
            setMBVEnableServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
