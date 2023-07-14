/**
 * AsyncAdaptiveAuthenticationLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class AsyncAdaptiveAuthenticationLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AsyncAdaptiveAuthentication {

    public AsyncAdaptiveAuthenticationLocator() {
    }


    public AsyncAdaptiveAuthenticationLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AsyncAdaptiveAuthenticationLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AsyncAdaptiveAuthentication
    private java.lang.String AsyncAdaptiveAuthentication_address = "http://d3.hq.passmarksecurity.com:8080/AdaptiveAuthentication/services/AsyncAdaptiveAuthentication";

    public java.lang.String getAsyncAdaptiveAuthenticationAddress() {
        return AsyncAdaptiveAuthentication_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AsyncAdaptiveAuthenticationWSDDServiceName = "AsyncAdaptiveAuthentication";

    public java.lang.String getAsyncAdaptiveAuthenticationWSDDServiceName() {
        return AsyncAdaptiveAuthenticationWSDDServiceName;
    }

    public void setAsyncAdaptiveAuthenticationWSDDServiceName(java.lang.String name) {
        AsyncAdaptiveAuthenticationWSDDServiceName = name;
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AsyncAdaptiveAuthenticationInterface getAsyncAdaptiveAuthentication() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AsyncAdaptiveAuthentication_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAsyncAdaptiveAuthentication(endpoint);
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AsyncAdaptiveAuthenticationInterface getAsyncAdaptiveAuthentication(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AsyncAdaptiveAuthenticationSoapBindingStub _stub = new com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AsyncAdaptiveAuthenticationSoapBindingStub(portAddress, this);
            _stub.setPortName(getAsyncAdaptiveAuthenticationWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAsyncAdaptiveAuthenticationEndpointAddress(java.lang.String address) {
        AsyncAdaptiveAuthentication_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AsyncAdaptiveAuthenticationInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AsyncAdaptiveAuthenticationSoapBindingStub _stub = new com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AsyncAdaptiveAuthenticationSoapBindingStub(new java.net.URL(AsyncAdaptiveAuthentication_address), this);
                _stub.setPortName(getAsyncAdaptiveAuthenticationWSDDServiceName());
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
        if ("AsyncAdaptiveAuthentication".equals(inputPortName)) {
            return getAsyncAdaptiveAuthentication();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AsyncAdaptiveAuthentication");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AsyncAdaptiveAuthentication"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AsyncAdaptiveAuthentication".equals(portName)) {
            setAsyncAdaptiveAuthenticationEndpointAddress(address);
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
