/**
 * IService1_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated;

public class IService1_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1_Service {

    public IService1_ServiceLocator() {
    }


    public IService1_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IService1_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IService1Port
    private java.lang.String IService1Port_address = "http://62.33.125.234:1888/soap/IService1";

    public java.lang.String getIService1PortAddress() {
        return IService1Port_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IService1PortWSDDServiceName = "IService1Port";

    public java.lang.String getIService1PortWSDDServiceName() {
        return IService1PortWSDDServiceName;
    }

    public void setIService1PortWSDDServiceName(java.lang.String name) {
        IService1PortWSDDServiceName = name;
    }

    public com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1_PortType getIService1Port() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IService1Port_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIService1Port(endpoint);
    }

    public com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1_PortType getIService1Port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1BindingStub _stub = new com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1BindingStub(portAddress, this);
            _stub.setPortName(getIService1PortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIService1PortEndpointAddress(java.lang.String address) {
        IService1Port_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1BindingStub _stub = new com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1BindingStub(new java.net.URL(IService1Port_address), this);
                _stub.setPortName(getIService1PortWSDDServiceName());
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
        if ("IService1Port".equals(inputPortName)) {
            return getIService1Port();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "IService1");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "IService1Port"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IService1Port".equals(portName)) {
            setIService1PortEndpointAddress(address);
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
