/**
 * Sberbank_SetNewClientsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.ermb.newclient.generated;

public class Sberbank_SetNewClientsLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClients {

    public Sberbank_SetNewClientsLocator() {
    }


    public Sberbank_SetNewClientsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Sberbank_SetNewClientsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Sberbank_SetNewClientsSoap12
    private java.lang.String Sberbank_SetNewClientsSoap12_address = "http://localhost/TestWS/Sberbank_SetNewClients.asmx";

    public java.lang.String getSberbank_SetNewClientsSoap12Address() {
        return Sberbank_SetNewClientsSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Sberbank_SetNewClientsSoap12WSDDServiceName = "Sberbank_SetNewClientsSoap12";

    public java.lang.String getSberbank_SetNewClientsSoap12WSDDServiceName() {
        return Sberbank_SetNewClientsSoap12WSDDServiceName;
    }

    public void setSberbank_SetNewClientsSoap12WSDDServiceName(java.lang.String name) {
        Sberbank_SetNewClientsSoap12WSDDServiceName = name;
    }

    public com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap getSberbank_SetNewClientsSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Sberbank_SetNewClientsSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSberbank_SetNewClientsSoap12(endpoint);
    }

    public com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap getSberbank_SetNewClientsSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap12Stub _stub = new com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap12Stub(portAddress, this);
            _stub.setPortName(getSberbank_SetNewClientsSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSberbank_SetNewClientsSoap12EndpointAddress(java.lang.String address) {
        Sberbank_SetNewClientsSoap12_address = address;
    }


    // Use to get a proxy class for Sberbank_SetNewClientsSoap
    private java.lang.String Sberbank_SetNewClientsSoap_address = "http://localhost/TestWS/Sberbank_SetNewClients.asmx";

    public java.lang.String getSberbank_SetNewClientsSoapAddress() {
        return Sberbank_SetNewClientsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Sberbank_SetNewClientsSoapWSDDServiceName = "Sberbank_SetNewClientsSoap";

    public java.lang.String getSberbank_SetNewClientsSoapWSDDServiceName() {
        return Sberbank_SetNewClientsSoapWSDDServiceName;
    }

    public void setSberbank_SetNewClientsSoapWSDDServiceName(java.lang.String name) {
        Sberbank_SetNewClientsSoapWSDDServiceName = name;
    }

    public com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap getSberbank_SetNewClientsSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Sberbank_SetNewClientsSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSberbank_SetNewClientsSoap(endpoint);
    }

    public com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap getSberbank_SetNewClientsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoapStub _stub = new com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoapStub(portAddress, this);
            _stub.setPortName(getSberbank_SetNewClientsSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSberbank_SetNewClientsSoapEndpointAddress(java.lang.String address) {
        Sberbank_SetNewClientsSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap12Stub _stub = new com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap12Stub(new java.net.URL(Sberbank_SetNewClientsSoap12_address), this);
                _stub.setPortName(getSberbank_SetNewClientsSoap12WSDDServiceName());
                return _stub;
            }
            if (com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoapStub _stub = new com.rssl.phizic.test.ermb.newclient.generated.Sberbank_SetNewClientsSoapStub(new java.net.URL(Sberbank_SetNewClientsSoap_address), this);
                _stub.setPortName(getSberbank_SetNewClientsSoapWSDDServiceName());
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
        if ("Sberbank_SetNewClientsSoap12".equals(inputPortName)) {
            return getSberbank_SetNewClientsSoap12();
        }
        else if ("Sberbank_SetNewClientsSoap".equals(inputPortName)) {
            return getSberbank_SetNewClientsSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sberbank.ru/MobileBank/OperatorIntegrations/", "Sberbank_SetNewClients");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sberbank.ru/MobileBank/OperatorIntegrations/", "Sberbank_SetNewClientsSoap12"));
            ports.add(new javax.xml.namespace.QName("http://sberbank.ru/MobileBank/OperatorIntegrations/", "Sberbank_SetNewClientsSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Sberbank_SetNewClientsSoap12".equals(portName)) {
            setSberbank_SetNewClientsSoap12EndpointAddress(address);
        }
        else 
if ("Sberbank_SetNewClientsSoap".equals(portName)) {
            setSberbank_SetNewClientsSoapEndpointAddress(address);
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
