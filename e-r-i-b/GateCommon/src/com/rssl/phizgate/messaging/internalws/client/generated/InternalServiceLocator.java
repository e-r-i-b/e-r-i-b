/**
 * InternalServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizgate.messaging.internalws.client.generated;

public class InternalServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizgate.messaging.internalws.client.generated.InternalService {

    public InternalServiceLocator() {
    }


    public InternalServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InternalServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for InternalServicePort
    private java.lang.String InternalServicePort_address = "http://tempuri.org/InternalService/InternalServicePort";

    public java.lang.String getInternalServicePortAddress() {
        return InternalServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String InternalServicePortWSDDServiceName = "InternalServicePort";

    public java.lang.String getInternalServicePortWSDDServiceName() {
        return InternalServicePortWSDDServiceName;
    }

    public void setInternalServicePortWSDDServiceName(java.lang.String name) {
        InternalServicePortWSDDServiceName = name;
    }

    public com.rssl.phizgate.messaging.internalws.client.generated.InternalServicePortType getInternalServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(InternalServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getInternalServicePort(endpoint);
    }

    public com.rssl.phizgate.messaging.internalws.client.generated.InternalServicePortType getInternalServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizgate.messaging.internalws.client.generated.InternalServiceSoapBindingStub _stub = new com.rssl.phizgate.messaging.internalws.client.generated.InternalServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getInternalServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setInternalServicePortEndpointAddress(java.lang.String address) {
        InternalServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizgate.messaging.internalws.client.generated.InternalServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizgate.messaging.internalws.client.generated.InternalServiceSoapBindingStub _stub = new com.rssl.phizgate.messaging.internalws.client.generated.InternalServiceSoapBindingStub(new java.net.URL(InternalServicePort_address), this);
                _stub.setPortName(getInternalServicePortWSDDServiceName());
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
        if ("InternalServicePort".equals(inputPortName)) {
            return getInternalServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "InternalService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "InternalServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("InternalServicePort".equals(portName)) {
            setInternalServicePortEndpointAddress(address);
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
