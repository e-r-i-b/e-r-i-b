/**
 * SmsLogLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.ermb.auxiliary.smslog.generated;

public class SmsLogLocator extends org.apache.axis.client.Service implements com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLog {

    public SmsLogLocator() {
    }


    public SmsLogLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SmsLogLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for smsLog
    private java.lang.String smsLog_address = "http://localhost:8080/services/smsLog";

    public java.lang.String getsmsLogAddress() {
        return smsLog_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String smsLogWSDDServiceName = "smsLog";

    public java.lang.String getsmsLogWSDDServiceName() {
        return smsLogWSDDServiceName;
    }

    public void setsmsLogWSDDServiceName(java.lang.String name) {
        smsLogWSDDServiceName = name;
    }

    public com.rssl.phizic.business.ermb.auxiliary.smslog.generated.ErmbSmsLog getsmsLog() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(smsLog_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getsmsLog(endpoint);
    }

    public com.rssl.phizic.business.ermb.auxiliary.smslog.generated.ErmbSmsLog getsmsLog(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogStub _stub = new com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogStub(portAddress, this);
            _stub.setPortName(getsmsLogWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setsmsLogEndpointAddress(java.lang.String address) {
        smsLog_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.business.ermb.auxiliary.smslog.generated.ErmbSmsLog.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogStub _stub = new com.rssl.phizic.business.ermb.auxiliary.smslog.generated.SmsLogStub(new java.net.URL(smsLog_address), this);
                _stub.setPortName(getsmsLogWSDDServiceName());
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
        if ("smsLog".equals(inputPortName)) {
            return getsmsLog();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sbrf.srb.ru/ermb/smsLog", "smsLog");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sbrf.srb.ru/ermb/smsLog", "smsLog"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("smsLog".equals(portName)) {
            setsmsLogEndpointAddress(address);
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
