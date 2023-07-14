/**
 * CommunalServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.cpfl.generated.axis;

public class CommunalServiceServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServiceService {

    public CommunalServiceServiceLocator() {
    }


    public CommunalServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CommunalServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CommunalServicePort
    private java.lang.String CommunalServicePort_address = "http://10.73.28.80:9080/NplatService_Web/services/CommunalServicePort";

    public java.lang.String getCommunalServicePortAddress() {
        return CommunalServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CommunalServicePortWSDDServiceName = "CommunalServicePort";

    public java.lang.String getCommunalServicePortWSDDServiceName() {
        return CommunalServicePortWSDDServiceName;
    }

    public void setCommunalServicePortWSDDServiceName(java.lang.String name) {
        CommunalServicePortWSDDServiceName = name;
    }

    public com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServicePT getCommunalServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CommunalServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCommunalServicePort(endpoint);
    }

    public com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServicePT getCommunalServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServiceBindingStub _stub = new com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServiceBindingStub(portAddress, this);
            _stub.setPortName(getCommunalServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCommunalServicePortEndpointAddress(java.lang.String address) {
        CommunalServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServicePT.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServiceBindingStub _stub = new com.rssl.phizic.test.webgate.cpfl.generated.axis.CommunalServiceBindingStub(new java.net.URL(CommunalServicePort_address), this);
                _stub.setPortName(getCommunalServicePortWSDDServiceName());
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
        if ("CommunalServicePort".equals(inputPortName)) {
            return getCommunalServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.nplat.filial.ubs.ca.sbrf.ru/CommunalService/", "CommunalServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.nplat.filial.ubs.ca.sbrf.ru/CommunalService/", "CommunalServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CommunalServicePort".equals(portName)) {
            setCommunalServicePortEndpointAddress(address);
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
