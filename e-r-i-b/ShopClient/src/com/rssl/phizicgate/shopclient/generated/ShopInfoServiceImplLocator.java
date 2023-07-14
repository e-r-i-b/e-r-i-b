/**
 * ShopInfoServiceImplLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.shopclient.generated;

public class ShopInfoServiceImplLocator extends org.apache.axis.client.Service implements com.rssl.phizicgate.shopclient.generated.ShopInfoServiceImpl {

    public ShopInfoServiceImplLocator() {
    }


    public ShopInfoServiceImplLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ShopInfoServiceImplLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ShopInfoServicePort
    private java.lang.String ShopInfoServicePort_address = "http://localhost:8080/services/ShopInfoServiceImpl";

    public java.lang.String getShopInfoServicePortAddress() {
        return ShopInfoServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ShopInfoServicePortWSDDServiceName = "ShopInfoServicePort";

    public java.lang.String getShopInfoServicePortWSDDServiceName() {
        return ShopInfoServicePortWSDDServiceName;
    }

    public void setShopInfoServicePortWSDDServiceName(java.lang.String name) {
        ShopInfoServicePortWSDDServiceName = name;
    }

    public com.rssl.phizicgate.shopclient.generated.ShopInfoService getShopInfoServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ShopInfoServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getShopInfoServicePort(endpoint);
    }

    public com.rssl.phizicgate.shopclient.generated.ShopInfoService getShopInfoServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizicgate.shopclient.generated.ShopInfoServiceSoapBindingStub _stub = new com.rssl.phizicgate.shopclient.generated.ShopInfoServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getShopInfoServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setShopInfoServicePortEndpointAddress(java.lang.String address) {
        ShopInfoServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizicgate.shopclient.generated.ShopInfoService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizicgate.shopclient.generated.ShopInfoServiceSoapBindingStub _stub = new com.rssl.phizicgate.shopclient.generated.ShopInfoServiceSoapBindingStub(new java.net.URL(ShopInfoServicePort_address), this);
                _stub.setPortName(getShopInfoServicePortWSDDServiceName());
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
        if ("ShopInfoServicePort".equals(inputPortName)) {
            return getShopInfoServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ShopInfoServiceImpl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ShopInfoServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ShopInfoServicePort".equals(portName)) {
            setShopInfoServicePortEndpointAddress(address);
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
