/**
 * OutMessageConsumerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated;

public class OutMessageConsumerServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerService {

    public OutMessageConsumerServiceLocator() {
    }


    public OutMessageConsumerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OutMessageConsumerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OutMessageConsumer
    private java.lang.String OutMessageConsumer_address = "http://localhost:8080/sb/ifm/sms/sb0/OutMessageConsumer";

    public java.lang.String getOutMessageConsumerAddress() {
        return OutMessageConsumer_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OutMessageConsumerWSDDServiceName = "OutMessageConsumer";

    public java.lang.String getOutMessageConsumerWSDDServiceName() {
        return OutMessageConsumerWSDDServiceName;
    }

    public void setOutMessageConsumerWSDDServiceName(java.lang.String name) {
        OutMessageConsumerWSDDServiceName = name;
    }

    public com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumer getOutMessageConsumer() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OutMessageConsumer_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOutMessageConsumer(endpoint);
    }

    public com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumer getOutMessageConsumer(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerSOAPBindingStub _stub = new com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerSOAPBindingStub(portAddress, this);
            _stub.setPortName(getOutMessageConsumerWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOutMessageConsumerEndpointAddress(java.lang.String address) {
        OutMessageConsumer_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumer.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerSOAPBindingStub _stub = new com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerSOAPBindingStub(new java.net.URL(OutMessageConsumer_address), this);
                _stub.setPortName(getOutMessageConsumerWSDDServiceName());
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
        if ("OutMessageConsumer".equals(inputPortName)) {
            return getOutMessageConsumer();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "OutMessageConsumerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "OutMessageConsumer"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("OutMessageConsumer".equals(portName)) {
            setOutMessageConsumerEndpointAddress(address);
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
