// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated;

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

public class BackRefClientService_Stub
    extends com.sun.xml.rpc.client.StubBase
    implements com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService {
    
    
    
    /*
     *  public constructor
     */
    public BackRefClientService_Stub(HandlerChain handlerChain) {
        super(handlerChain);
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/services/BackRefClientServiceImpl");
    }
    
    
    /*
     *  implementation of getClientDepartmentCode
     */
    public java.lang.String getClientDepartmentCode(java.lang.Long long_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getClientDepartmentCode_OPCODE);
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct _myBackRefClientService_getClientDepartmentCode_RequestStruct =
                new com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct();
            
            _myBackRefClientService_getClientDepartmentCode_RequestStruct.setLong_1(long_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getClientDepartmentCode_getClientDepartmentCode_QNAME);
            _bodyBlock.setValue(_myBackRefClientService_getClientDepartmentCode_RequestStruct);
            _bodyBlock.setSerializer(ns1_myBackRefClientService_getClientDepartmentCode_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct _myBackRefClientService_getClientDepartmentCode_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myBackRefClientService_getClientDepartmentCode_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myBackRefClientService_getClientDepartmentCode_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct)_responseObj;
            }
            
            return _myBackRefClientService_getClientDepartmentCode_ResponseStruct.getResult();
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
     *  implementation of getClientByFIOAndDoc
     */
    public com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client getClientByFIOAndDoc(java.lang.String string_1, java.lang.String string_2, java.lang.String string_3, java.lang.String string_4, java.lang.String string_5, java.util.Calendar calendar_6, java.lang.String string_7)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getClientByFIOAndDoc_OPCODE);
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct _myBackRefClientService_getClientByFIOAndDoc_RequestStruct =
                new com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct();
            
            _myBackRefClientService_getClientByFIOAndDoc_RequestStruct.setString_1(string_1);
            _myBackRefClientService_getClientByFIOAndDoc_RequestStruct.setString_2(string_2);
            _myBackRefClientService_getClientByFIOAndDoc_RequestStruct.setString_3(string_3);
            _myBackRefClientService_getClientByFIOAndDoc_RequestStruct.setString_4(string_4);
            _myBackRefClientService_getClientByFIOAndDoc_RequestStruct.setString_5(string_5);
            _myBackRefClientService_getClientByFIOAndDoc_RequestStruct.setCalendar_6(calendar_6);
            _myBackRefClientService_getClientByFIOAndDoc_RequestStruct.setString_7(string_7);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getClientByFIOAndDoc_getClientByFIOAndDoc_QNAME);
            _bodyBlock.setValue(_myBackRefClientService_getClientByFIOAndDoc_RequestStruct);
            _bodyBlock.setSerializer(ns1_myBackRefClientService_getClientByFIOAndDoc_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct _myBackRefClientService_getClientByFIOAndDoc_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myBackRefClientService_getClientByFIOAndDoc_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myBackRefClientService_getClientByFIOAndDoc_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct)_responseObj;
            }
            
            return _myBackRefClientService_getClientByFIOAndDoc_ResponseStruct.getResult();
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
     *  implementation of getClientCreationType
     */
    public java.lang.String getClientCreationType(java.lang.String string_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getClientCreationType_OPCODE);
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct _myBackRefClientService_getClientCreationType_RequestStruct =
                new com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct();
            
            _myBackRefClientService_getClientCreationType_RequestStruct.setString_1(string_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getClientCreationType_getClientCreationType_QNAME);
            _bodyBlock.setValue(_myBackRefClientService_getClientCreationType_RequestStruct);
            _bodyBlock.setSerializer(ns1_myBackRefClientService_getClientCreationType_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct _myBackRefClientService_getClientCreationType_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myBackRefClientService_getClientCreationType_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myBackRefClientService_getClientCreationType_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct)_responseObj;
            }
            
            return _myBackRefClientService_getClientCreationType_ResponseStruct.getResult();
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
     *  implementation of __forGenerateClientDocument
     */
    public void __forGenerateClientDocument(com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.ClientDocument clientDocument_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(__forGenerateClientDocument_OPCODE);
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct _myBackRefClientService___forGenerateClientDocument_RequestStruct =
                new com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct();
            
            _myBackRefClientService___forGenerateClientDocument_RequestStruct.setClientDocument_1(clientDocument_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1___forGenerateClientDocument___forGenerateClientDocument_QNAME);
            _bodyBlock.setValue(_myBackRefClientService___forGenerateClientDocument_RequestStruct);
            _bodyBlock.setSerializer(ns1_myBackRefClientService___forGenerateClientDocument_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct _myBackRefClientService___forGenerateClientDocument_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myBackRefClientService___forGenerateClientDocument_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myBackRefClientService___forGenerateClientDocument_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct)_responseObj;
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
     *  implementation of getClientById
     */
    public com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client getClientById(java.lang.Long long_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getClientById_OPCODE);
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct _myBackRefClientService_getClientById_RequestStruct =
                new com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct();
            
            _myBackRefClientService_getClientById_RequestStruct.setLong_1(long_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getClientById_getClientById_QNAME);
            _bodyBlock.setValue(_myBackRefClientService_getClientById_RequestStruct);
            _bodyBlock.setSerializer(ns1_myBackRefClientService_getClientById_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct _myBackRefClientService_getClientById_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myBackRefClientService_getClientById_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myBackRefClientService_getClientById_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct)_responseObj;
            }
            
            return _myBackRefClientService_getClientById_ResponseStruct.getResult();
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
            case getClientDepartmentCode_OPCODE:
                _deserialize_getClientDepartmentCode(bodyReader, deserializationContext, state);
                break;
            case getClientByFIOAndDoc_OPCODE:
                _deserialize_getClientByFIOAndDoc(bodyReader, deserializationContext, state);
                break;
            case getClientCreationType_OPCODE:
                _deserialize_getClientCreationType(bodyReader, deserializationContext, state);
                break;
            case __forGenerateClientDocument_OPCODE:
                _deserialize___forGenerateClientDocument(bodyReader, deserializationContext, state);
                break;
            case getClientById_OPCODE:
                _deserialize_getClientById(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SenderException("sender.response.unrecognizedOperation", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the getClientDepartmentCode operation.
     */
    private void _deserialize_getClientDepartmentCode(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myBackRefClientService_getClientDepartmentCode_ResponseStructObj =
            ns1_myBackRefClientService_getClientDepartmentCode_ResponseStruct_SOAPSerializer.deserialize(ns1_getClientDepartmentCode_getClientDepartmentCodeResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientDepartmentCode_getClientDepartmentCodeResponse_QNAME);
        bodyBlock.setValue(myBackRefClientService_getClientDepartmentCode_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getClientByFIOAndDoc operation.
     */
    private void _deserialize_getClientByFIOAndDoc(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myBackRefClientService_getClientByFIOAndDoc_ResponseStructObj =
            ns1_myBackRefClientService_getClientByFIOAndDoc_ResponseStruct_SOAPSerializer.deserialize(ns1_getClientByFIOAndDoc_getClientByFIOAndDocResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientByFIOAndDoc_getClientByFIOAndDocResponse_QNAME);
        bodyBlock.setValue(myBackRefClientService_getClientByFIOAndDoc_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getClientCreationType operation.
     */
    private void _deserialize_getClientCreationType(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myBackRefClientService_getClientCreationType_ResponseStructObj =
            ns1_myBackRefClientService_getClientCreationType_ResponseStruct_SOAPSerializer.deserialize(ns1_getClientCreationType_getClientCreationTypeResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientCreationType_getClientCreationTypeResponse_QNAME);
        bodyBlock.setValue(myBackRefClientService_getClientCreationType_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the __forGenerateClientDocument operation.
     */
    private void _deserialize___forGenerateClientDocument(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myBackRefClientService___forGenerateClientDocument_ResponseStructObj =
            ns1_myBackRefClientService___forGenerateClientDocument_ResponseStruct_SOAPSerializer.deserialize(ns1___forGenerateClientDocument___forGenerateClientDocumentResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGenerateClientDocument___forGenerateClientDocumentResponse_QNAME);
        bodyBlock.setValue(myBackRefClientService___forGenerateClientDocument_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getClientById operation.
     */
    private void _deserialize_getClientById(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myBackRefClientService_getClientById_ResponseStructObj =
            ns1_myBackRefClientService_getClientById_ResponseStruct_SOAPSerializer.deserialize(ns1_getClientById_getClientByIdResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientById_getClientByIdResponse_QNAME);
        bodyBlock.setValue(myBackRefClientService_getClientById_ResponseStructObj);
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
        ns1_myBackRefClientService_getClientDepartmentCode_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct.class, ns1_getClientDepartmentCodeResponse_TYPE_QNAME);
        ns1_myBackRefClientService_getClientById_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct.class, ns1_getClientById_TYPE_QNAME);
        ns1_myBackRefClientService_getClientByFIOAndDoc_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct.class, ns1_getClientByFIOAndDoc_TYPE_QNAME);
        ns1_myBackRefClientService___forGenerateClientDocument_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct.class, ns1___forGenerateClientDocument_TYPE_QNAME);
        ns1_myBackRefClientService_getClientByFIOAndDoc_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct.class, ns1_getClientByFIOAndDocResponse_TYPE_QNAME);
        ns1_myBackRefClientService_getClientCreationType_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct.class, ns1_getClientCreationType_TYPE_QNAME);
        ns1_myBackRefClientService_getClientById_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct.class, ns1_getClientByIdResponse_TYPE_QNAME);
        ns1_myBackRefClientService___forGenerateClientDocument_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct.class, ns1___forGenerateClientDocumentResponse_TYPE_QNAME);
        ns1_myBackRefClientService_getClientDepartmentCode_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct.class, ns1_getClientDepartmentCode_TYPE_QNAME);
        ns1_myBackRefClientService_getClientCreationType_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct.class, ns1_getClientCreationTypeResponse_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName _portName = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "BackRefClientServicePort");
    private static final int getClientDepartmentCode_OPCODE = 0;
    private static final int getClientByFIOAndDoc_OPCODE = 1;
    private static final int getClientCreationType_OPCODE = 2;
    private static final int __forGenerateClientDocument_OPCODE = 3;
    private static final int getClientById_OPCODE = 4;
    private static final javax.xml.namespace.QName ns1_getClientDepartmentCode_getClientDepartmentCode_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientDepartmentCode");
    private static final javax.xml.namespace.QName ns1_getClientDepartmentCode_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientDepartmentCode");
    private CombinedSerializer ns1_myBackRefClientService_getClientDepartmentCode_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getClientDepartmentCode_getClientDepartmentCodeResponse_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientDepartmentCodeResponse");
    private static final javax.xml.namespace.QName ns1_getClientDepartmentCodeResponse_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientDepartmentCodeResponse");
    private CombinedSerializer ns1_myBackRefClientService_getClientDepartmentCode_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getClientByFIOAndDoc_getClientByFIOAndDoc_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByFIOAndDoc");
    private static final javax.xml.namespace.QName ns1_getClientByFIOAndDoc_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByFIOAndDoc");
    private CombinedSerializer ns1_myBackRefClientService_getClientByFIOAndDoc_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getClientByFIOAndDoc_getClientByFIOAndDocResponse_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByFIOAndDocResponse");
    private static final javax.xml.namespace.QName ns1_getClientByFIOAndDocResponse_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByFIOAndDocResponse");
    private CombinedSerializer ns1_myBackRefClientService_getClientByFIOAndDoc_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getClientCreationType_getClientCreationType_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientCreationType");
    private static final javax.xml.namespace.QName ns1_getClientCreationType_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientCreationType");
    private CombinedSerializer ns1_myBackRefClientService_getClientCreationType_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getClientCreationType_getClientCreationTypeResponse_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientCreationTypeResponse");
    private static final javax.xml.namespace.QName ns1_getClientCreationTypeResponse_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientCreationTypeResponse");
    private CombinedSerializer ns1_myBackRefClientService_getClientCreationType_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateClientDocument___forGenerateClientDocument_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "__forGenerateClientDocument");
    private static final javax.xml.namespace.QName ns1___forGenerateClientDocument_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "__forGenerateClientDocument");
    private CombinedSerializer ns1_myBackRefClientService___forGenerateClientDocument_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateClientDocument___forGenerateClientDocumentResponse_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "__forGenerateClientDocumentResponse");
    private static final javax.xml.namespace.QName ns1___forGenerateClientDocumentResponse_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "__forGenerateClientDocumentResponse");
    private CombinedSerializer ns1_myBackRefClientService___forGenerateClientDocument_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getClientById_getClientById_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientById");
    private static final javax.xml.namespace.QName ns1_getClientById_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientById");
    private CombinedSerializer ns1_myBackRefClientService_getClientById_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getClientById_getClientByIdResponse_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByIdResponse");
    private static final javax.xml.namespace.QName ns1_getClientByIdResponse_TYPE_QNAME = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByIdResponse");
    private CombinedSerializer ns1_myBackRefClientService_getClientById_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.backrefservice.clients.wsgate.phizic.rssl.com",
                                            "ns1", "http://java.sun.com/jax-rpc-ri/internal"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}