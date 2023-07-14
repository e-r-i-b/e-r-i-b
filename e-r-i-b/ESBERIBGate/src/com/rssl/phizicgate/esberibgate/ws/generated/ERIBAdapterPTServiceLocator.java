/**
 * ERIBAdapterPTServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class ERIBAdapterPTServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPTService {

    public ERIBAdapterPTServiceLocator() {
    }


    public ERIBAdapterPTServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ERIBAdapterPTServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ERIBAdapterPTServicePort
    private java.lang.String ERIBAdapterPTServicePort_address = "http://10.73.7.34:4021/BaseProduct/services/ERIBAdapterPT";

    public java.lang.String getERIBAdapterPTServicePortAddress() {
        return ERIBAdapterPTServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ERIBAdapterPTServicePortWSDDServiceName = "ERIBAdapterPTServicePort";

    public java.lang.String getERIBAdapterPTServicePortWSDDServiceName() {
        return ERIBAdapterPTServicePortWSDDServiceName;
    }

    public void setERIBAdapterPTServicePortWSDDServiceName(java.lang.String name) {
        ERIBAdapterPTServicePortWSDDServiceName = name;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPT getERIBAdapterPTServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ERIBAdapterPTServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getERIBAdapterPTServicePort(endpoint);
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPT getERIBAdapterPTServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPTSoapBindingStub _stub = new com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPTSoapBindingStub(portAddress, this);
            _stub.setPortName(getERIBAdapterPTServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setERIBAdapterPTServicePortEndpointAddress(java.lang.String address) {
        ERIBAdapterPTServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPT.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPTSoapBindingStub _stub = new com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPTSoapBindingStub(new java.net.URL(ERIBAdapterPTServicePort_address), this);
                _stub.setPortName(getERIBAdapterPTServicePortWSDDServiceName());
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
        if ("ERIBAdapterPTServicePort".equals(inputPortName)) {
            return getERIBAdapterPTServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ERIBAdapterPTService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ERIBAdapterPTServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ERIBAdapterPTServicePort".equals(portName)) {
            setERIBAdapterPTServicePortEndpointAddress(address);
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
