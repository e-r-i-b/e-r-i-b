/**
 * DepoCOD_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.depocod.generated;

public class DepoCOD_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.webgate.depocod.generated.DepoCOD_Service {

/**
 * Public WS interface
 */

    public DepoCOD_ServiceLocator() {
    }


    public DepoCOD_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DepoCOD_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DepoCOD
    private java.lang.String DepoCOD_address = "http://10.73.6.3/DepoCOD.cgi";

    public java.lang.String getDepoCODAddress() {
        return DepoCOD_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DepoCODWSDDServiceName = "DepoCOD";

    public java.lang.String getDepoCODWSDDServiceName() {
        return DepoCODWSDDServiceName;
    }

    public void setDepoCODWSDDServiceName(java.lang.String name) {
        DepoCODWSDDServiceName = name;
    }

    public com.rssl.phizic.test.webgate.depocod.generated.DepoCODPortType getDepoCOD() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DepoCOD_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDepoCOD(endpoint);
    }

    public com.rssl.phizic.test.webgate.depocod.generated.DepoCODPortType getDepoCOD(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.webgate.depocod.generated.DepoCODStub _stub = new com.rssl.phizic.test.webgate.depocod.generated.DepoCODStub(portAddress, this);
            _stub.setPortName(getDepoCODWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDepoCODEndpointAddress(java.lang.String address) {
        DepoCOD_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.webgate.depocod.generated.DepoCODPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.webgate.depocod.generated.DepoCODStub _stub = new com.rssl.phizic.test.webgate.depocod.generated.DepoCODStub(new java.net.URL(DepoCOD_address), this);
                _stub.setPortName(getDepoCODWSDDServiceName());
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
        if ("DepoCOD".equals(inputPortName)) {
            return getDepoCOD();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.73.6.3/DepoCOD.wsdl", "DepoCOD");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.73.6.3/DepoCOD.wsdl", "DepoCOD"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DepoCOD".equals(portName)) {
            setDepoCODEndpointAddress(address);
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
