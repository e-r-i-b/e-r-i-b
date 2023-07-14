/**
 * SmsLogServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.ermb.auxiliary.smslog.generated;

public class SmsLogServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogService {

    public SmsLogServiceLocator() {
    }


    public SmsLogServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SmsLogServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for smsLogService
    private java.lang.String smsLogService_address = "http://localhost:8080/services/smsLogService";

    public java.lang.String getsmsLogServiceAddress() {
        return smsLogService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String smsLogServiceWSDDServiceName = "smsLogService";

    public java.lang.String getsmsLogServiceWSDDServiceName() {
        return smsLogServiceWSDDServiceName;
    }

    public void setsmsLogServiceWSDDServiceName(java.lang.String name) {
        smsLogServiceWSDDServiceName = name;
    }

    public com.rssl.phizic.business.ermb.auxiliary.smslog.generated.ErmbSmsLogService getsmsLogService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(smsLogService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getsmsLogService(endpoint);
    }

    public com.rssl.phizic.business.ermb.auxiliary.smslog.generated.ErmbSmsLogService getsmsLogService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogServiceStub _stub = new com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogServiceStub(portAddress, this);
            _stub.setPortName(getsmsLogServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setsmsLogServiceEndpointAddress(java.lang.String address) {
        smsLogService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.business.ermb.auxiliary.smslog.generated.ErmbSmsLogService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogServiceStub _stub = new com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogServiceStub(new java.net.URL(smsLogService_address), this);
                _stub.setPortName(getsmsLogServiceWSDDServiceName());
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
        if ("smsLogService".equals(inputPortName)) {
            return getsmsLogService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "smsLogService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "smsLogService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("smsLogService".equals(portName)) {
            setsmsLogServiceEndpointAddress(address);
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
