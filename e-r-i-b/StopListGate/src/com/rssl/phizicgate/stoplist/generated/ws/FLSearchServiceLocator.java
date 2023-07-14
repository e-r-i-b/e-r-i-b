/**
 * FLSearchServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.stoplist.generated.ws;

public class FLSearchServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizicgate.stoplist.generated.ws.FLSearchService {

    public FLSearchServiceLocator() {
    }


    public FLSearchServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FLSearchServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FLSearchServiceSoap
    private java.lang.String FLSearchServiceSoap_address = "http://roshka/StopListWebServiceStub/FLSearchService.asmx";

    public java.lang.String getFLSearchServiceSoapAddress() {
        return FLSearchServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FLSearchServiceSoapWSDDServiceName = "FLSearchServiceSoap";

    public java.lang.String getFLSearchServiceSoapWSDDServiceName() {
        return FLSearchServiceSoapWSDDServiceName;
    }

    public void setFLSearchServiceSoapWSDDServiceName(java.lang.String name) {
        FLSearchServiceSoapWSDDServiceName = name;
    }

    public com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceSoap_PortType getFLSearchServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FLSearchServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFLSearchServiceSoap(endpoint);
    }

    public com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceSoap_PortType getFLSearchServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceSoap_BindingStub _stub = new com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getFLSearchServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFLSearchServiceSoapEndpointAddress(java.lang.String address) {
        FLSearchServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceSoap_BindingStub _stub = new com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceSoap_BindingStub(new java.net.URL(FLSearchServiceSoap_address), this);
                _stub.setPortName(getFLSearchServiceSoapWSDDServiceName());
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
        if ("FLSearchServiceSoap".equals(inputPortName)) {
            return getFLSearchServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "FLSearchService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "FLSearchServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FLSearchServiceSoap".equals(portName)) {
            setFLSearchServiceSoapEndpointAddress(address);
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
