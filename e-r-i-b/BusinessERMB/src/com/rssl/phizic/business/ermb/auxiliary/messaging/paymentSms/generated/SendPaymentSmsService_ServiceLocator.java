/**
 * SendPaymentSmsService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated;

public class SendPaymentSmsService_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.SendPaymentSmsService_Service {

    public SendPaymentSmsService_ServiceLocator() {
    }


    public SendPaymentSmsService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SendPaymentSmsService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SendPaymentSmsServicePort
    private java.lang.String SendPaymentSmsServicePort_address = "http://localhost:8080/services/SendPaymentSmsServiceImpl";

    public java.lang.String getSendPaymentSmsServicePortAddress() {
        return SendPaymentSmsServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SendPaymentSmsServicePortWSDDServiceName = "SendPaymentSmsServicePort";

    public java.lang.String getSendPaymentSmsServicePortWSDDServiceName() {
        return SendPaymentSmsServicePortWSDDServiceName;
    }

    public void setSendPaymentSmsServicePortWSDDServiceName(java.lang.String name) {
        SendPaymentSmsServicePortWSDDServiceName = name;
    }

    public com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.SendPaymentSmsService_PortType getSendPaymentSmsServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SendPaymentSmsServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSendPaymentSmsServicePort(endpoint);
    }

    public com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.SendPaymentSmsService_PortType getSendPaymentSmsServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.SendPaymentSmsServiceBindingStub _stub = new com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.SendPaymentSmsServiceBindingStub(portAddress, this);
            _stub.setPortName(getSendPaymentSmsServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSendPaymentSmsServicePortEndpointAddress(java.lang.String address) {
        SendPaymentSmsServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.SendPaymentSmsService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.SendPaymentSmsServiceBindingStub _stub = new com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.generated.SendPaymentSmsServiceBindingStub(new java.net.URL(SendPaymentSmsServicePort_address), this);
                _stub.setPortName(getSendPaymentSmsServicePortWSDDServiceName());
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
        if ("SendPaymentSmsServicePort".equals(inputPortName)) {
            return getSendPaymentSmsServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "SendPaymentSmsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "SendPaymentSmsServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SendPaymentSmsServicePort".equals(portName)) {
            setSendPaymentSmsServicePortEndpointAddress(address);
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
