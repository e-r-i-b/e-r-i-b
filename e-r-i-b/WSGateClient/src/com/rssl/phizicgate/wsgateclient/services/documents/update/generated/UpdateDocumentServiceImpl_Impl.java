// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.documents.update.generated;

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

public class UpdateDocumentServiceImpl_Impl extends com.sun.xml.rpc.client.BasicService implements UpdateDocumentServiceImpl {
    private static final QName serviceName = new QName("http://generated.listener.wsgate.phizic.rssl.com", "UpdateDocumentServiceImpl");
    private static final QName ns1_UpdateDocumentServicePort_QNAME = new QName("http://generated.listener.wsgate.phizic.rssl.com", "UpdateDocumentServicePort");
    private static final Class updateDocumentService_PortClass = com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentService.class;
    
    public UpdateDocumentServiceImpl_Impl() {
        super(serviceName, new QName[] {
                        ns1_UpdateDocumentServicePort_QNAME
                    },
            new com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentServiceImpl_SerializerRegistry().getRegistry());
        
    }
    
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (portName.equals(ns1_UpdateDocumentServicePort_QNAME) &&
                serviceDefInterface.equals(updateDocumentService_PortClass)) {
                return getUpdateDocumentServicePort();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(portName, serviceDefInterface);
    }
    
    public java.rmi.Remote getPort(java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (serviceDefInterface.equals(updateDocumentService_PortClass)) {
                return getUpdateDocumentServicePort();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(serviceDefInterface);
    }
    
    public com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentService getUpdateDocumentServicePort() {
        java.lang.String[] roles = new java.lang.String[] {};
        HandlerChainImpl handlerChain = new HandlerChainImpl(getHandlerRegistry().getHandlerChain(ns1_UpdateDocumentServicePort_QNAME));
        handlerChain.setRoles(roles);
        com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentService_Stub stub = new com.rssl.phizicgate.wsgateclient.services.documents.update.generated.UpdateDocumentService_Stub(handlerChain);
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