/**
 * EribMDM_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mdm.generated;

public class EribMDM_ServiceLocator extends org.apache.axis.client.Service implements com.rssl.phizic.test.wsgateclient.mdm.generated.EribMDM_Service {

    public EribMDM_ServiceLocator() {
    }


    public EribMDM_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EribMDM_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for eribMDMService
    private java.lang.String eribMDMService_address = "http://localhost:8080/services/EribMDM";

    public java.lang.String geteribMDMServiceAddress() {
        return eribMDMService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String eribMDMServiceWSDDServiceName = "eribMDMService";

    public java.lang.String geteribMDMServiceWSDDServiceName() {
        return eribMDMServiceWSDDServiceName;
    }

    public void seteribMDMServiceWSDDServiceName(java.lang.String name) {
        eribMDMServiceWSDDServiceName = name;
    }

    public com.rssl.phizic.test.wsgateclient.mdm.generated.EribMDM_PortType geteribMDMService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(eribMDMService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return geteribMDMService(endpoint);
    }

    public com.rssl.phizic.test.wsgateclient.mdm.generated.EribMDM_PortType geteribMDMService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rssl.phizic.test.wsgateclient.mdm.generated.EribMDMBindingStub _stub = new com.rssl.phizic.test.wsgateclient.mdm.generated.EribMDMBindingStub(portAddress, this);
            _stub.setPortName(geteribMDMServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void seteribMDMServiceEndpointAddress(java.lang.String address) {
        eribMDMService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rssl.phizic.test.wsgateclient.mdm.generated.EribMDM_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rssl.phizic.test.wsgateclient.mdm.generated.EribMDMBindingStub _stub = new com.rssl.phizic.test.wsgateclient.mdm.generated.EribMDMBindingStub(new java.net.URL(eribMDMService_address), this);
                _stub.setPortName(geteribMDMServiceWSDDServiceName());
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
        if ("eribMDMService".equals(inputPortName)) {
            return geteribMDMService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "eribMDM");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "eribMDMService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("eribMDMService".equals(portName)) {
            seteribMDMServiceEndpointAddress(address);
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
