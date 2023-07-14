/**
 * TaskWebService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.ermb.migration.list.task.service.generated;

public class TaskWebService_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebService_Service {

    public TaskWebService_ServiceLocator() {
    }


    public TaskWebService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TaskWebService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TaskWebServicePort
    private java.lang.String TaskWebServicePort_address = "http://localhost:8080/services/TaskWebServiceImpl";

    public java.lang.String getTaskWebServicePortAddress() {
        return TaskWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TaskWebServicePortWSDDServiceName = "TaskWebServicePort";

    public java.lang.String getTaskWebServicePortWSDDServiceName() {
        return TaskWebServicePortWSDDServiceName;
    }

    public void setTaskWebServicePortWSDDServiceName(java.lang.String name) {
        TaskWebServicePortWSDDServiceName = name;
    }

    public com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebService_PortType getTaskWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TaskWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTaskWebServicePort(endpoint);
    }

    public com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebService_PortType getTaskWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebServiceBindingStub _stub = new com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebServiceBindingStub(portAddress, this);
            _stub.setPortName(getTaskWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTaskWebServicePortEndpointAddress(java.lang.String address) {
        TaskWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebServiceBindingStub _stub = new com.rssl.phizic.business.ermb.migration.list.task.service.generated.TaskWebServiceBindingStub(new java.net.URL(TaskWebServicePort_address), this);
                _stub.setPortName(getTaskWebServicePortWSDDServiceName());
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
        if ("TaskWebServicePort".equals(inputPortName)) {
            return getTaskWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.task.sbrf.ru", "TaskWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.task.sbrf.ru", "TaskWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TaskWebServicePort".equals(portName)) {
            setTaskWebServicePortEndpointAddress(address);
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
