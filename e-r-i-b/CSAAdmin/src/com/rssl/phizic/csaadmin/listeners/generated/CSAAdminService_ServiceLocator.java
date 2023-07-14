/**
 * CSAAdminService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class CSAAdminService_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.csaadmin.listeners.generated.CSAAdminService_Service {

    public CSAAdminService_ServiceLocator() {
    }


    public CSAAdminService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CSAAdminService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CSAAdminService
    private java.lang.String CSAAdminService_address = "http://localhost:8080/CSAAdmin/services/CSAAdminService";

    public java.lang.String getCSAAdminServiceAddress() {
        return CSAAdminService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CSAAdminServiceWSDDServiceName = "CSAAdminService";

    public java.lang.String getCSAAdminServiceWSDDServiceName() {
        return CSAAdminServiceWSDDServiceName;
    }

    public void setCSAAdminServiceWSDDServiceName(java.lang.String name) {
        CSAAdminServiceWSDDServiceName = name;
    }

    public com.rssl.phizic.csaadmin.listeners.generated.CSAAdminService_PortType getCSAAdminService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CSAAdminService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCSAAdminService(endpoint);
    }

    public com.rssl.phizic.csaadmin.listeners.generated.CSAAdminService_PortType getCSAAdminService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.csaadmin.listeners.generated.CSAAdminServiceSoapBindingStub _stub = new com.rssl.phizic.csaadmin.listeners.generated.CSAAdminServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCSAAdminServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCSAAdminServiceEndpointAddress(java.lang.String address) {
        CSAAdminService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.csaadmin.listeners.generated.CSAAdminService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.csaadmin.listeners.generated.CSAAdminServiceSoapBindingStub _stub = new com.rssl.phizic.csaadmin.listeners.generated.CSAAdminServiceSoapBindingStub(new java.net.URL(CSAAdminService_address), this);
                _stub.setPortName(getCSAAdminServiceWSDDServiceName());
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
        if ("CSAAdminService".equals(inputPortName)) {
            return getCSAAdminService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "CSAAdminService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "CSAAdminService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CSAAdminService".equals(portName)) {
            setCSAAdminServiceEndpointAddress(address);
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
