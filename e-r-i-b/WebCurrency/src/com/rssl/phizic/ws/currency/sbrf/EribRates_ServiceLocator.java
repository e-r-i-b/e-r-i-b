/**
 * EribRates_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.currency.sbrf;

public class EribRates_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.ws.currency.sbrf.EribRates_Service {

    public EribRates_ServiceLocator() {
    }


    public EribRates_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EribRates_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for eribRates
    private java.lang.String eribRates_address = "http://localhost:8080/services/EribRates";

    public java.lang.String geteribRatesAddress() {
        return eribRates_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String eribRatesWSDDServiceName = "eribRates";

    public java.lang.String geteribRatesWSDDServiceName() {
        return eribRatesWSDDServiceName;
    }

    public void seteribRatesWSDDServiceName(java.lang.String name) {
        eribRatesWSDDServiceName = name;
    }

    public com.rssl.phizic.ws.currency.sbrf.EribRates_PortType geteribRates() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(eribRates_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return geteribRates(endpoint);
    }

    public com.rssl.phizic.ws.currency.sbrf.EribRates_PortType geteribRates(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.ws.currency.sbrf.EribRatesStub _stub = new com.rssl.phizic.ws.currency.sbrf.EribRatesStub(portAddress, this);
            _stub.setPortName(geteribRatesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void seteribRatesEndpointAddress(java.lang.String address) {
        eribRates_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.ws.currency.sbrf.EribRates_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.ws.currency.sbrf.EribRatesStub _stub = new com.rssl.phizic.ws.currency.sbrf.EribRatesStub(new java.net.URL(eribRates_address), this);
                _stub.setPortName(geteribRatesWSDDServiceName());
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
        if ("eribRates".equals(inputPortName)) {
            return geteribRates();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/rates", "eribRates");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/rates", "eribRates"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("eribRates".equals(portName)) {
            seteribRatesEndpointAddress(address);
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
