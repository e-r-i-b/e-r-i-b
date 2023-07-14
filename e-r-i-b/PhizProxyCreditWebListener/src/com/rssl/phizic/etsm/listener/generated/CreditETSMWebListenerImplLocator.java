/**
 * CreditETSMWebListenerImplLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.etsm.listener.generated;

public class CreditETSMWebListenerImplLocator extends org.apache.axis.client.Service implements com.rssl.phizic.etsm.listener.generated.CreditETSMWebListenerImpl {

    public CreditETSMWebListenerImplLocator() {
    }


    public CreditETSMWebListenerImplLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CreditETSMWebListenerImplLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CreditETSMWebListenerPort
    private java.lang.String CreditETSMWebListenerPort_address = "http://localhost:8080/services/CreditETSMWebListenerImpl";

    public java.lang.String getCreditETSMWebListenerPortAddress() {
        return CreditETSMWebListenerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CreditETSMWebListenerPortWSDDServiceName = "CreditETSMWebListenerPort";

    public java.lang.String getCreditETSMWebListenerPortWSDDServiceName() {
        return CreditETSMWebListenerPortWSDDServiceName;
    }

    public void setCreditETSMWebListenerPortWSDDServiceName(java.lang.String name) {
        CreditETSMWebListenerPortWSDDServiceName = name;
    }

    public com.rssl.phizic.etsm.listener.generated.CreditETSMWebListener getCreditETSMWebListenerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CreditETSMWebListenerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCreditETSMWebListenerPort(endpoint);
    }

    public com.rssl.phizic.etsm.listener.generated.CreditETSMWebListener getCreditETSMWebListenerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.etsm.listener.generated.CreditETSMWebListenerSoapBindingStub _stub = new com.rssl.phizic.etsm.listener.generated.CreditETSMWebListenerSoapBindingStub(portAddress, this);
            _stub.setPortName(getCreditETSMWebListenerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCreditETSMWebListenerPortEndpointAddress(java.lang.String address) {
        CreditETSMWebListenerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.etsm.listener.generated.CreditETSMWebListener.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.etsm.listener.generated.CreditETSMWebListenerSoapBindingStub _stub = new com.rssl.phizic.etsm.listener.generated.CreditETSMWebListenerSoapBindingStub(new java.net.URL(CreditETSMWebListenerPort_address), this);
                _stub.setPortName(getCreditETSMWebListenerPortWSDDServiceName());
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
        if ("CreditETSMWebListenerPort".equals(inputPortName)) {
            return getCreditETSMWebListenerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "CreditETSMWebListenerImpl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "CreditETSMWebListenerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CreditETSMWebListenerPort".equals(portName)) {
            setCreditETSMWebListenerPortEndpointAddress(address);
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