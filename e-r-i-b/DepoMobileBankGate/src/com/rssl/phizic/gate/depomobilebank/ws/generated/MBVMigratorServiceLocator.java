/**
 * MBVMigratorServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.gate.depomobilebank.ws.generated;

public class MBVMigratorServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigratorService {

    public MBVMigratorServiceLocator() {
    }


    public MBVMigratorServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MBVMigratorServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MBVMigrator
    private java.lang.String MBVMigrator_address = "http://localhost:9080/mbv-web/services/MBVMigrator";

    public java.lang.String getMBVMigratorAddress() {
        return MBVMigrator_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MBVMigratorWSDDServiceName = "MBVMigrator";

    public java.lang.String getMBVMigratorWSDDServiceName() {
        return MBVMigratorWSDDServiceName;
    }

    public void setMBVMigratorWSDDServiceName(java.lang.String name) {
        MBVMigratorWSDDServiceName = name;
    }

    public com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigrator getMBVMigrator() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MBVMigrator_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMBVMigrator(endpoint);
    }

    public com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigrator getMBVMigrator(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigratorSoapBindingStub _stub = new com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigratorSoapBindingStub(portAddress, this);
            _stub.setPortName(getMBVMigratorWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMBVMigratorEndpointAddress(java.lang.String address) {
        MBVMigrator_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigrator.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigratorSoapBindingStub _stub = new com.rssl.phizic.gate.depomobilebank.ws.generated.MBVMigratorSoapBindingStub(new java.net.URL(MBVMigrator_address), this);
                _stub.setPortName(getMBVMigratorWSDDServiceName());
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
        if ("MBVMigrator".equals(inputPortName)) {
            return getMBVMigrator();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.mbv.sbrf.ru", "MBVMigratorService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.mbv.sbrf.ru", "MBVMigrator"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MBVMigrator".equals(portName)) {
            setMBVMigratorEndpointAddress(address);
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
