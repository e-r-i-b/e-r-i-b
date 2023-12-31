// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.notification.generated;

import com.sun.xml.rpc.server.http.MessageContextProperties;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.soap.streaming.*;
import com.sun.xml.rpc.soap.message.*;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.soap.SOAPEncodingConstants;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.lang.reflect.*;
import java.lang.Class;
import com.sun.xml.rpc.client.SenderException;
import com.sun.xml.rpc.client.*;
import com.sun.xml.rpc.client.http.*;
import javax.xml.rpc.handler.*;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.soap.SOAPFaultException;

public class NotificationSubscribeService_Stub
    extends com.sun.xml.rpc.client.StubBase
    implements com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService {
    
    
    
    /*
     *  public constructor
     */
    public NotificationSubscribeService_Stub(HandlerChain handlerChain) {
        super(handlerChain);
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/services/NotificationSubscribeServiceImpl");
    }
    
    
    /*
     *  implementation of unsubscribeAccount
     */
    public void unsubscribeAccount(com.rssl.phizicgate.wsgate.services.notification.generated.Account account_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(unsubscribeAccount_OPCODE);
            com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_unsubscribeAccount_RequestStruct _myNotificationSubscribeService_unsubscribeAccount_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_unsubscribeAccount_RequestStruct();
            
            _myNotificationSubscribeService_unsubscribeAccount_RequestStruct.setAccount_1(account_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_unsubscribeAccount_unsubscribeAccount_QNAME);
            _bodyBlock.setValue(_myNotificationSubscribeService_unsubscribeAccount_RequestStruct);
            _bodyBlock.setSerializer(ns1_myNotificationSubscribeService_unsubscribeAccount_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_unsubscribeAccount_ResponseStruct _myNotificationSubscribeService_unsubscribeAccount_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myNotificationSubscribeService_unsubscribeAccount_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_unsubscribeAccount_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myNotificationSubscribeService_unsubscribeAccount_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_unsubscribeAccount_ResponseStruct)_responseObj;
            }
            
        } catch (RemoteException e) {
            // let this one through unchanged
            throw e;
        } catch (JAXRPCException e) {
            throw new RemoteException(e.getMessage(), e);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            } else {
                throw new RemoteException(e.getMessage(), e);
            }
        }
    }
    
    /*
     *  implementation of subscribeAccount
     */
    public void subscribeAccount(com.rssl.phizicgate.wsgate.services.notification.generated.Account account_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(subscribeAccount_OPCODE);
            com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_subscribeAccount_RequestStruct _myNotificationSubscribeService_subscribeAccount_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_subscribeAccount_RequestStruct();
            
            _myNotificationSubscribeService_subscribeAccount_RequestStruct.setAccount_1(account_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_subscribeAccount_subscribeAccount_QNAME);
            _bodyBlock.setValue(_myNotificationSubscribeService_subscribeAccount_RequestStruct);
            _bodyBlock.setSerializer(ns1_myNotificationSubscribeService_subscribeAccount_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_subscribeAccount_ResponseStruct _myNotificationSubscribeService_subscribeAccount_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myNotificationSubscribeService_subscribeAccount_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_subscribeAccount_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myNotificationSubscribeService_subscribeAccount_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_subscribeAccount_ResponseStruct)_responseObj;
            }
            
        } catch (RemoteException e) {
            // let this one through unchanged
            throw e;
        } catch (JAXRPCException e) {
            throw new RemoteException(e.getMessage(), e);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            } else {
                throw new RemoteException(e.getMessage(), e);
            }
        }
    }
    
    
    /*
     *  this method deserializes the request/response structure in the body
     */
    protected void _readFirstBodyElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState  state) throws Exception {
        int opcode = state.getRequest().getOperationCode();
        switch (opcode) {
            case unsubscribeAccount_OPCODE:
                _deserialize_unsubscribeAccount(bodyReader, deserializationContext, state);
                break;
            case subscribeAccount_OPCODE:
                _deserialize_subscribeAccount(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SenderException("sender.response.unrecognizedOperation", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the unsubscribeAccount operation.
     */
    private void _deserialize_unsubscribeAccount(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myNotificationSubscribeService_unsubscribeAccount_ResponseStructObj =
            ns1_myNotificationSubscribeService_unsubscribeAccount_ResponseStruct_SOAPSerializer.deserialize(ns1_unsubscribeAccount_unsubscribeAccountResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_unsubscribeAccount_unsubscribeAccountResponse_QNAME);
        bodyBlock.setValue(myNotificationSubscribeService_unsubscribeAccount_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the subscribeAccount operation.
     */
    private void _deserialize_subscribeAccount(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myNotificationSubscribeService_subscribeAccount_ResponseStructObj =
            ns1_myNotificationSubscribeService_subscribeAccount_ResponseStruct_SOAPSerializer.deserialize(ns1_subscribeAccount_subscribeAccountResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_subscribeAccount_subscribeAccountResponse_QNAME);
        bodyBlock.setValue(myNotificationSubscribeService_subscribeAccount_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    
    
    public java.lang.String _getDefaultEnvelopeEncodingStyle() {
        return SOAPNamespaceConstants.ENCODING;
    }
    
    public java.lang.String _getImplicitEnvelopeEncodingStyle() {
        return "";
    }
    
    public java.lang.String _getEncodingStyle() {
        return SOAPNamespaceConstants.ENCODING;
    }
    
    public void _setEncodingStyle(java.lang.String encodingStyle) {
        throw new UnsupportedOperationException("cannot set encoding style");
    }
    
    
    
    
    
    /*
     * This method returns an array containing (prefix, nsURI) pairs.
     */
    protected java.lang.String[] _getNamespaceDeclarations() {
        return myNamespace_declarations;
    }
    
    /*
     * This method returns an array containing the names of the headers we understand.
     */
    public javax.xml.namespace.QName[] _getUnderstoodHeaders() {
        return understoodHeaderNames;
    }
    
    public void _initialize(InternalTypeMappingRegistry registry) throws Exception {
        super._initialize(registry);
        ns1_myNotificationSubscribeService_unsubscribeAccount_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_unsubscribeAccount_ResponseStruct.class, ns1_unsubscribeAccountResponse_TYPE_QNAME);
        ns1_myNotificationSubscribeService_subscribeAccount_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_subscribeAccount_RequestStruct.class, ns1_subscribeAccount_TYPE_QNAME);
        ns1_myNotificationSubscribeService_unsubscribeAccount_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_unsubscribeAccount_RequestStruct.class, ns1_unsubscribeAccount_TYPE_QNAME);
        ns1_myNotificationSubscribeService_subscribeAccount_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.notification.generated.NotificationSubscribeService_subscribeAccount_ResponseStruct.class, ns1_subscribeAccountResponse_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName _portName = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "NotificationSubscribeServicePort");
    private static final int unsubscribeAccount_OPCODE = 0;
    private static final int subscribeAccount_OPCODE = 1;
    private static final javax.xml.namespace.QName ns1_unsubscribeAccount_unsubscribeAccount_QNAME = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "unsubscribeAccount");
    private static final javax.xml.namespace.QName ns1_unsubscribeAccount_TYPE_QNAME = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "unsubscribeAccount");
    private CombinedSerializer ns1_myNotificationSubscribeService_unsubscribeAccount_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_unsubscribeAccount_unsubscribeAccountResponse_QNAME = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "unsubscribeAccountResponse");
    private static final javax.xml.namespace.QName ns1_unsubscribeAccountResponse_TYPE_QNAME = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "unsubscribeAccountResponse");
    private CombinedSerializer ns1_myNotificationSubscribeService_unsubscribeAccount_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_subscribeAccount_subscribeAccount_QNAME = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "subscribeAccount");
    private static final javax.xml.namespace.QName ns1_subscribeAccount_TYPE_QNAME = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "subscribeAccount");
    private CombinedSerializer ns1_myNotificationSubscribeService_subscribeAccount_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_subscribeAccount_subscribeAccountResponse_QNAME = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "subscribeAccountResponse");
    private static final javax.xml.namespace.QName ns1_subscribeAccountResponse_TYPE_QNAME = new QName("http://generated.notification.services.gate.web.phizic.rssl.com", "subscribeAccountResponse");
    private CombinedSerializer ns1_myNotificationSubscribeService_subscribeAccount_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.notification.services.gate.web.phizic.rssl.com",
                                            "ns1", "http://java.sun.com/jax-rpc-ri/internal"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}
