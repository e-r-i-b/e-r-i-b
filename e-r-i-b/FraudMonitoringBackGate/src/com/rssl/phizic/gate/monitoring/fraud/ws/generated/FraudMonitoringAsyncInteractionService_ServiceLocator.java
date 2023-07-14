/**
 * FraudMonitoringAsyncInteractionService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.gate.monitoring.fraud.ws.generated;

public class FraudMonitoringAsyncInteractionService_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.gate.monitoring.fraud.ws.generated.FraudMonitoringAsyncInteractionService_Service {

    public FraudMonitoringAsyncInteractionService_ServiceLocator() {
    }


    public FraudMonitoringAsyncInteractionService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FraudMonitoringAsyncInteractionService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FraudMonitoringAsyncInteractionService
    private java.lang.String FraudMonitoringAsyncInteractionService_address = "http://localhost:8080/FraudMonitoringBackGate/services/FraudMonitoringAsyncInteractionService";

    public java.lang.String getFraudMonitoringAsyncInteractionServiceAddress() {
        return FraudMonitoringAsyncInteractionService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FraudMonitoringAsyncInteractionServiceWSDDServiceName = "FraudMonitoringAsyncInteractionService";

    public java.lang.String getFraudMonitoringAsyncInteractionServiceWSDDServiceName() {
        return FraudMonitoringAsyncInteractionServiceWSDDServiceName;
    }

    public void setFraudMonitoringAsyncInteractionServiceWSDDServiceName(java.lang.String name) {
        FraudMonitoringAsyncInteractionServiceWSDDServiceName = name;
    }

    public com.rssl.phizic.gate.monitoring.fraud.ws.generated.FraudMonitoringAsyncInteractionService_PortType getFraudMonitoringAsyncInteractionService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FraudMonitoringAsyncInteractionService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFraudMonitoringAsyncInteractionService(endpoint);
    }

    public com.rssl.phizic.gate.monitoring.fraud.ws.generated.FraudMonitoringAsyncInteractionService_PortType getFraudMonitoringAsyncInteractionService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.gate.monitoring.fraud.ws.generated.FraudMonitoringAsyncInteractionServiceSoapBindingStub _stub = new com.rssl.phizic.gate.monitoring.fraud.ws.generated.FraudMonitoringAsyncInteractionServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getFraudMonitoringAsyncInteractionServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFraudMonitoringAsyncInteractionServiceEndpointAddress(java.lang.String address) {
        FraudMonitoringAsyncInteractionService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.gate.monitoring.fraud.ws.generated.FraudMonitoringAsyncInteractionService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.gate.monitoring.fraud.ws.generated.FraudMonitoringAsyncInteractionServiceSoapBindingStub _stub = new com.rssl.phizic.gate.monitoring.fraud.ws.generated.FraudMonitoringAsyncInteractionServiceSoapBindingStub(new java.net.URL(FraudMonitoringAsyncInteractionService_address), this);
                _stub.setPortName(getFraudMonitoringAsyncInteractionServiceWSDDServiceName());
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
        if ("FraudMonitoringAsyncInteractionService".equals(inputPortName)) {
            return getFraudMonitoringAsyncInteractionService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "FraudMonitoringAsyncInteractionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "FraudMonitoringAsyncInteractionService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FraudMonitoringAsyncInteractionService".equals(portName)) {
            setFraudMonitoringAsyncInteractionServiceEndpointAddress(address);
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
