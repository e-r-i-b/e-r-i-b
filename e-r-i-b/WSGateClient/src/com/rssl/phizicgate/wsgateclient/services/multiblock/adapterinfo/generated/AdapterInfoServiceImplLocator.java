/**
 * AdapterInfoServiceImplLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated;

public class AdapterInfoServiceImplLocator extends org.apache.axis.client.Service implements com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.AdapterInfoServiceImpl {

    public AdapterInfoServiceImplLocator() {
    }


    public AdapterInfoServiceImplLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AdapterInfoServiceImplLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AdapterInfoServicePort
    private java.lang.String AdapterInfoServicePort_address = "http://localhost:8080/services/AdapterInfoServiceImpl";

    public java.lang.String getAdapterInfoServicePortAddress() {
        return AdapterInfoServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AdapterInfoServicePortWSDDServiceName = "AdapterInfoServicePort";

    public java.lang.String getAdapterInfoServicePortWSDDServiceName() {
        return AdapterInfoServicePortWSDDServiceName;
    }

    public void setAdapterInfoServicePortWSDDServiceName(java.lang.String name) {
        AdapterInfoServicePortWSDDServiceName = name;
    }

    public com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.AdapterInfoService getAdapterInfoServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AdapterInfoServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAdapterInfoServicePort(endpoint);
    }

    public com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.AdapterInfoService getAdapterInfoServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.AdapterInfoServiceSoapBindingStub _stub = new com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.AdapterInfoServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getAdapterInfoServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAdapterInfoServicePortEndpointAddress(java.lang.String address) {
        AdapterInfoServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.AdapterInfoService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.AdapterInfoServiceSoapBindingStub _stub = new com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.AdapterInfoServiceSoapBindingStub(new java.net.URL(AdapterInfoServicePort_address), this);
                _stub.setPortName(getAdapterInfoServicePortWSDDServiceName());
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
        if ("AdapterInfoServicePort".equals(inputPortName)) {
            return getAdapterInfoServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "AdapterInfoServiceImpl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "AdapterInfoServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AdapterInfoServicePort".equals(portName)) {
            setAdapterInfoServicePortEndpointAddress(address);
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
