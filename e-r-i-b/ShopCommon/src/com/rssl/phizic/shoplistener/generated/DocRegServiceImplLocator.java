/**
 * DocRegServiceImplLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.shoplistener.generated;

public class DocRegServiceImplLocator extends org.apache.axis.client.Service implements com.rssl.phizic.shoplistener.generated.DocRegServiceImpl {

    public DocRegServiceImplLocator() {
    }


    public DocRegServiceImplLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DocRegServiceImplLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DocRegServicePort
    private java.lang.String DocRegServicePort_address = "http://localhost:8080/services/DocRegServiceImpl";

    public java.lang.String getDocRegServicePortAddress() {
        return DocRegServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DocRegServicePortWSDDServiceName = "DocRegServicePort";

    public java.lang.String getDocRegServicePortWSDDServiceName() {
        return DocRegServicePortWSDDServiceName;
    }

    public void setDocRegServicePortWSDDServiceName(java.lang.String name) {
        DocRegServicePortWSDDServiceName = name;
    }

    public com.rssl.phizic.shoplistener.generated.DocRegService getDocRegServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DocRegServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDocRegServicePort(endpoint);
    }

    public com.rssl.phizic.shoplistener.generated.DocRegService getDocRegServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.shoplistener.generated.DocRegServiceSoapBindingStub _stub = new com.rssl.phizic.shoplistener.generated.DocRegServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getDocRegServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDocRegServicePortEndpointAddress(java.lang.String address) {
        DocRegServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.shoplistener.generated.DocRegService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.shoplistener.generated.DocRegServiceSoapBindingStub _stub = new com.rssl.phizic.shoplistener.generated.DocRegServiceSoapBindingStub(new java.net.URL(DocRegServicePort_address), this);
                _stub.setPortName(getDocRegServicePortWSDDServiceName());
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
        if ("DocRegServicePort".equals(inputPortName)) {
            return getDocRegServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DocRegServiceImpl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DocRegServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DocRegServicePort".equals(portName)) {
            setDocRegServicePortEndpointAddress(address);
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
