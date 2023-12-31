// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.ipas.cap.ws.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.client.ServiceExceptionImpl;
import com.sun.xml.rpc.util.exception.*;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.client.HandlerChainImpl;
import javax.xml.rpc.*;
import javax.xml.rpc.encoding.*;
import javax.xml.rpc.handler.HandlerChain;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.namespace.QName;

public class CAPAuthenticationService_v1_0_Impl extends com.sun.xml.rpc.client.BasicService implements CAPAuthenticationService_v1_0 {
    private static final QName serviceName = new QName("http://www.openwaygroup.com/iPAS/WSDL/CAPAuthenticationService-v1", "CAPAuthenticationService_v1_0");
    private static final QName ns1_iPASWSSoap_QNAME = new QName("http://www.openwaygroup.com/iPAS/WSDL/CAPAuthenticationService-v1", "iPASWSSoap");
    private static final Class CAPAuthenticationPortType_v1_0_PortClass = com.rssl.phizic.authgate.ipas.cap.ws.generated.CAPAuthenticationPortType_v1_0.class;
    
    public CAPAuthenticationService_v1_0_Impl() {
        super(serviceName, new QName[] {
                        ns1_iPASWSSoap_QNAME
                    },
            new com.rssl.phizic.authgate.ipas.cap.ws.generated.CAPAuthenticationService_v1_0_SerializerRegistry().getRegistry());
        
    }
    
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (portName.equals(ns1_iPASWSSoap_QNAME) &&
                serviceDefInterface.equals(CAPAuthenticationPortType_v1_0_PortClass)) {
                return getIPASWSSoap();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(portName, serviceDefInterface);
    }
    
    public java.rmi.Remote getPort(java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (serviceDefInterface.equals(CAPAuthenticationPortType_v1_0_PortClass)) {
                return getIPASWSSoap();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(serviceDefInterface);
    }
    
    public com.rssl.phizic.authgate.ipas.cap.ws.generated.CAPAuthenticationPortType_v1_0 getIPASWSSoap() {
        java.lang.String[] roles = new java.lang.String[] {};
        HandlerChainImpl handlerChain = new HandlerChainImpl(getHandlerRegistry().getHandlerChain(ns1_iPASWSSoap_QNAME));
        handlerChain.setRoles(roles);
        com.rssl.phizic.authgate.ipas.cap.ws.generated.CAPAuthenticationPortType_v1_0_Stub stub = new com.rssl.phizic.authgate.ipas.cap.ws.generated.CAPAuthenticationPortType_v1_0_Stub(handlerChain);
        try {
            stub._initialize(super.internalTypeRegistry);
        } catch (JAXRPCException e) {
            throw e;
        } catch (Exception e) {
            throw new JAXRPCException(e.getMessage(), e);
        }
        return stub;
    }
}
