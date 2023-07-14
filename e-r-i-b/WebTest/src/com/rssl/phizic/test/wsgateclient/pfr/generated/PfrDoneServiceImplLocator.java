/**
 * PfrDoneServiceImplLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.pfr.generated;

public class PfrDoneServiceImplLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.wsgateclient.pfr.generated.PfrDoneServiceImpl {

    public PfrDoneServiceImplLocator() {
    }


    public PfrDoneServiceImplLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PfrDoneServiceImplLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PfrDoneServicePort
    private java.lang.String PfrDoneServicePort_address = "http://localhost:8080/services/PfrDoneServiceImpl";

    public java.lang.String getPfrDoneServicePortAddress() {
        return PfrDoneServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PfrDoneServicePortWSDDServiceName = "PfrDoneServicePort";

    public java.lang.String getPfrDoneServicePortWSDDServiceName() {
        return PfrDoneServicePortWSDDServiceName;
    }

    public void setPfrDoneServicePortWSDDServiceName(java.lang.String name) {
        PfrDoneServicePortWSDDServiceName = name;
    }

    public com.rssl.phizic.test.wsgateclient.pfr.generated.PfrDoneService getPfrDoneServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PfrDoneServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPfrDoneServicePort(endpoint);
    }

    public com.rssl.phizic.test.wsgateclient.pfr.generated.PfrDoneService getPfrDoneServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.wsgateclient.pfr.generated.PfrDoneServiceSoapBindingStub _stub = new com.rssl.phizic.test.wsgateclient.pfr.generated.PfrDoneServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getPfrDoneServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPfrDoneServicePortEndpointAddress(java.lang.String address) {
        PfrDoneServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.wsgateclient.pfr.generated.PfrDoneService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.wsgateclient.pfr.generated.PfrDoneServiceSoapBindingStub _stub = new com.rssl.phizic.test.wsgateclient.pfr.generated.PfrDoneServiceSoapBindingStub(new java.net.URL(PfrDoneServicePort_address), this);
                _stub.setPortName(getPfrDoneServicePortWSDDServiceName());
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
        if ("PfrDoneServicePort".equals(inputPortName)) {
            return getPfrDoneServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://generated.pfr.esberiblistener.ws.phizic.rssl.com", "PfrDoneServiceImpl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://generated.pfr.esberiblistener.ws.phizic.rssl.com", "PfrDoneServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PfrDoneServicePort".equals(portName)) {
            setPfrDoneServicePortEndpointAddress(address);
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
