/**
 * ActivityEngine_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.notification.generated;

public class ActivityEngine_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngine_Service {

    public ActivityEngine_ServiceLocator() {
    }


    public ActivityEngine_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ActivityEngine_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ActivityEngineSOAP
    private java.lang.String ActivityEngineSOAP_address = "http://ws.rsa.msk.ru/";

    public java.lang.String getActivityEngineSOAPAddress() {
        return ActivityEngineSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ActivityEngineSOAPWSDDServiceName = "ActivityEngineSOAP";

    public java.lang.String getActivityEngineSOAPWSDDServiceName() {
        return ActivityEngineSOAPWSDDServiceName;
    }

    public void setActivityEngineSOAPWSDDServiceName(java.lang.String name) {
        ActivityEngineSOAPWSDDServiceName = name;
    }

    public com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngine_PortType getActivityEngineSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ActivityEngineSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getActivityEngineSOAP(endpoint);
    }

    public com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngine_PortType getActivityEngineSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngineSOAPStub _stub = new com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngineSOAPStub(portAddress, this);
            _stub.setPortName(getActivityEngineSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setActivityEngineSOAPEndpointAddress(java.lang.String address) {
        ActivityEngineSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngine_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngineSOAPStub _stub = new com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngineSOAPStub(new java.net.URL(ActivityEngineSOAP_address), this);
                _stub.setPortName(getActivityEngineSOAPWSDDServiceName());
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
        if ("ActivityEngineSOAP".equals(inputPortName)) {
            return getActivityEngineSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "ActivityEngine");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.rsa.msk.ru/ActivityEngine/", "ActivityEngineSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ActivityEngineSOAP".equals(portName)) {
            setActivityEngineSOAPEndpointAddress(address);
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
