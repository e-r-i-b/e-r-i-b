// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.recipients.generated;

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

public class BackRefReceiverInfoServiceImpl_Impl extends com.sun.xml.rpc.client.BasicService implements BackRefReceiverInfoServiceImpl {
    private static final QName serviceName = new QName("http://generated.recipients.wsgate.phizic.rssl.com", "BackRefReceiverInfoServiceImpl");
    private static final QName ns1_BackRefReceiverInfoServicePort_QNAME = new QName("http://generated.recipients.wsgate.phizic.rssl.com", "BackRefReceiverInfoServicePort");
    private static final Class backRefReceiverInfoService_PortClass = com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService.class;
    
    public BackRefReceiverInfoServiceImpl_Impl() {
        super(serviceName, new QName[] {
                        ns1_BackRefReceiverInfoServicePort_QNAME
                    },
            new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoServiceImpl_SerializerRegistry().getRegistry());
        
    }
    
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (portName.equals(ns1_BackRefReceiverInfoServicePort_QNAME) &&
                serviceDefInterface.equals(backRefReceiverInfoService_PortClass)) {
                return getBackRefReceiverInfoServicePort();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(portName, serviceDefInterface);
    }
    
    public java.rmi.Remote getPort(java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (serviceDefInterface.equals(backRefReceiverInfoService_PortClass)) {
                return getBackRefReceiverInfoServicePort();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(serviceDefInterface);
    }
    
    public com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService getBackRefReceiverInfoServicePort() {
        java.lang.String[] roles = new java.lang.String[] {};
        HandlerChainImpl handlerChain = new HandlerChainImpl(getHandlerRegistry().getHandlerChain(ns1_BackRefReceiverInfoServicePort_QNAME));
        handlerChain.setRoles(roles);
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_Stub stub = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_Stub(handlerChain);
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
