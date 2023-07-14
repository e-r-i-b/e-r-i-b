/**
 * ASFilialInfoServiceImplLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.asfilial.generated;

public class ASFilialInfoServiceImplLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.wsgateclient.asfilial.generated.ASFilialInfoServiceImpl {

    public ASFilialInfoServiceImplLocator() {
    }


    public ASFilialInfoServiceImplLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ASFilialInfoServiceImplLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ASFilialInfoServicePort
    private java.lang.String ASFilialInfoServicePort_address = "http://localhost:8080/services/ASFilialInfoServiceImpl";

    public java.lang.String getASFilialInfoServicePortAddress() {
        return ASFilialInfoServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ASFilialInfoServicePortWSDDServiceName = "ASFilialInfoServicePort";

    public java.lang.String getASFilialInfoServicePortWSDDServiceName() {
        return ASFilialInfoServicePortWSDDServiceName;
    }

    public void setASFilialInfoServicePortWSDDServiceName(java.lang.String name) {
        ASFilialInfoServicePortWSDDServiceName = name;
    }

    public com.rssl.phizic.test.wsgateclient.asfilial.generated.ASFilialInfoService getASFilialInfoServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ASFilialInfoServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getASFilialInfoServicePort(endpoint);
    }

    public com.rssl.phizic.test.wsgateclient.asfilial.generated.ASFilialInfoService getASFilialInfoServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.wsgateclient.asfilial.generated.ASFilialInfoServiceSoapBindingStub _stub = new com.rssl.phizic.test.wsgateclient.asfilial.generated.ASFilialInfoServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getASFilialInfoServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setASFilialInfoServicePortEndpointAddress(java.lang.String address) {
        ASFilialInfoServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.wsgateclient.asfilial.generated.ASFilialInfoService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.wsgateclient.asfilial.generated.ASFilialInfoServiceSoapBindingStub _stub = new com.rssl.phizic.test.wsgateclient.asfilial.generated.ASFilialInfoServiceSoapBindingStub(new java.net.URL(ASFilialInfoServicePort_address), this);
                _stub.setPortName(getASFilialInfoServicePortWSDDServiceName());
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
        if ("ASFilialInfoServicePort".equals(inputPortName)) {
            return getASFilialInfoServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ASFilialInfoServiceImpl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ASFilialInfoServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ASFilialInfoServicePort".equals(portName)) {
            setASFilialInfoServicePortEndpointAddress(address);
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
