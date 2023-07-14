/**
 * ERIBClientServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.webAPI.generated;

public class ERIBClientServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientService {

    public ERIBClientServiceLocator() {
    }


    public ERIBClientServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ERIBClientServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ERIBClientServicePort
    private java.lang.String ERIBClientServicePort_address = "http://erib.web.services.org/ERIBClientService/ERIBClientServicePort";

    public java.lang.String getERIBClientServicePortAddress() {
        return ERIBClientServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ERIBClientServicePortWSDDServiceName = "ERIBClientServicePort";

    public java.lang.String getERIBClientServicePortWSDDServiceName() {
        return ERIBClientServicePortWSDDServiceName;
    }

    public void setERIBClientServicePortWSDDServiceName(java.lang.String name) {
        ERIBClientServicePortWSDDServiceName = name;
    }

    public com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServicePortType getERIBClientServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ERIBClientServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getERIBClientServicePort(endpoint);
    }

    public com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServicePortType getERIBClientServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServiceSoapBindingStub _stub = new com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getERIBClientServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setERIBClientServicePortEndpointAddress(java.lang.String address) {
        ERIBClientServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServiceSoapBindingStub _stub = new com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServiceSoapBindingStub(new java.net.URL(ERIBClientServicePort_address), this);
                _stub.setPortName(getERIBClientServicePortWSDDServiceName());
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
        if ("ERIBClientServicePort".equals(inputPortName)) {
            return getERIBClientServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://erib.web.services.org/", "ERIBClientService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://erib.web.services.org/", "ERIBClientServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ERIBClientServicePort".equals(portName)) {
            setERIBClientServicePortEndpointAddress(address);
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
