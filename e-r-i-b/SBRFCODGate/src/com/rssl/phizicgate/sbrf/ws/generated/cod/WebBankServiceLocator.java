/**
 * WebBankServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.sbrf.ws.generated.cod;

public class WebBankServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankService {

    public WebBankServiceLocator() {
    }


    public WebBankServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebBankServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebBankServiceIFPort
    private java.lang.String WebBankServiceIFPort_address = "http://150.151.8.67:9080/WebBank/services/WebBankService";

    public java.lang.String getWebBankServiceIFPortAddress() {
        return WebBankServiceIFPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebBankServiceIFPortWSDDServiceName = "WebBankServiceIFPort";

    public java.lang.String getWebBankServiceIFPortWSDDServiceName() {
        return WebBankServiceIFPortWSDDServiceName;
    }

    public void setWebBankServiceIFPortWSDDServiceName(java.lang.String name) {
        WebBankServiceIFPortWSDDServiceName = name;
    }

    public com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIF getWebBankServiceIFPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebBankServiceIFPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebBankServiceIFPort(endpoint);
    }

    public com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIF getWebBankServiceIFPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIFBindingStub _stub = new com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIFBindingStub(portAddress, this);
            _stub.setPortName(getWebBankServiceIFPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebBankServiceIFPortEndpointAddress(java.lang.String address) {
        WebBankServiceIFPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIF.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIFBindingStub _stub = new com.rssl.phizicgate.sbrf.ws.generated.cod.WebBankServiceIFBindingStub(new java.net.URL(WebBankServiceIFPort_address), this);
                _stub.setPortName(getWebBankServiceIFPortWSDDServiceName());
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
        if ("WebBankServiceIFPort".equals(inputPortName)) {
            return getWebBankServiceIFPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:dpc", "WebBankService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:dpc", "WebBankServiceIFPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WebBankServiceIFPort".equals(portName)) {
            setWebBankServiceIFPortEndpointAddress(address);
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
