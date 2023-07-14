// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod;

import com.sun.xml.rpc.client.HandlerChainImpl;
import com.sun.xml.rpc.client.ServiceExceptionImpl;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;

public class WebBankService_Impl extends com.sun.xml.rpc.client.BasicService implements WebBankService {
    private static final QName serviceName = new QName("urn:dpc", "WebBankService");
    private static final QName ns1_WebBankServiceIFPort_QNAME = new QName("urn:dpc", "WebBankServiceIFPort");
    private static final Class webBankServiceIF_PortClass = com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod.WebBankServiceIF.class;
    
    public WebBankService_Impl() {
        super(serviceName, new QName[] {
                        ns1_WebBankServiceIFPort_QNAME
                    },
            new com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod.WebBankService_SerializerRegistry().getRegistry());
        
    }
    
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (portName.equals(ns1_WebBankServiceIFPort_QNAME) &&
                serviceDefInterface.equals(webBankServiceIF_PortClass)) {
                return getWebBankServiceIFPort();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(portName, serviceDefInterface);
    }
    
    public java.rmi.Remote getPort(java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (serviceDefInterface.equals(webBankServiceIF_PortClass)) {
                return getWebBankServiceIFPort();
            }
        } catch (Exception e) {
            throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));
        }
        return super.getPort(serviceDefInterface);
    }
    
    public com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod.WebBankServiceIF getWebBankServiceIFPort() {
        java.lang.String[] roles = new java.lang.String[] {};
        HandlerChainImpl handlerChain = new HandlerChainImpl(getHandlerRegistry().getHandlerChain(ns1_WebBankServiceIFPort_QNAME));
        handlerChain.setRoles(roles);
        com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod.WebBankServiceIF_Stub stub = new com.rssl.phizicgate.sbrf.ws.generated.jaxrpc.cod.WebBankServiceIF_Stub(handlerChain);
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