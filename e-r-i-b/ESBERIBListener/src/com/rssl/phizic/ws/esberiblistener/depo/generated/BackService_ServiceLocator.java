/**
 * BackService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.depo.generated;

public class BackService_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.ws.esberiblistener.depo.generated.BackService_Service {

    public BackService_ServiceLocator() {
    }


    public BackService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BackService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for backService
    private java.lang.String backService_address = "http://localhost:8080/services/backService";

    public java.lang.String getbackServiceAddress() {
        return backService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String backServiceWSDDServiceName = "backService";

    public java.lang.String getbackServiceWSDDServiceName() {
        return backServiceWSDDServiceName;
    }

    public void setbackServiceWSDDServiceName(java.lang.String name) {
        backServiceWSDDServiceName = name;
    }

    public com.rssl.phizic.ws.esberiblistener.depo.generated.BackService_PortType getbackService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(backService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getbackService(endpoint);
    }

    public com.rssl.phizic.ws.esberiblistener.depo.generated.BackService_PortType getbackService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.ws.esberiblistener.depo.generated.BackServiceStub _stub = new com.rssl.phizic.ws.esberiblistener.depo.generated.BackServiceStub(portAddress, this);
            _stub.setPortName(getbackServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setbackServiceEndpointAddress(java.lang.String address) {
        backService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.ws.esberiblistener.depo.generated.BackService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.ws.esberiblistener.depo.generated.BackServiceStub _stub = new com.rssl.phizic.ws.esberiblistener.depo.generated.BackServiceStub(new java.net.URL(backService_address), this);
                _stub.setPortName(getbackServiceWSDDServiceName());
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
        if ("backService".equals(inputPortName)) {
            return getbackService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/backService", "backService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/backService", "backService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("backService".equals(portName)) {
            setbackServiceEndpointAddress(address);
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
