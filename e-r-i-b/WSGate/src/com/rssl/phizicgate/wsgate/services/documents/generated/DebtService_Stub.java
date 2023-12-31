// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;

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

public class DebtService_Stub
    extends com.sun.xml.rpc.client.StubBase
    implements com.rssl.phizicgate.wsgate.services.documents.generated.DebtService {
    
    
    
    /*
     *  public constructor
     */
    public DebtService_Stub(HandlerChain handlerChain) {
        super(handlerChain);
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/services/DebtServiceImpl");
    }
    
    
    /*
     *  implementation of __forGenerateDebtRow
     */
    public com.rssl.phizicgate.wsgate.services.documents.generated.DebtRowImpl __forGenerateDebtRow()
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(__forGenerateDebtRow_OPCODE);
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebtRow_RequestStruct _myDebtService___forGenerateDebtRow_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebtRow_RequestStruct();
            
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1___forGenerateDebtRow___forGenerateDebtRow_QNAME);
            _bodyBlock.setValue(_myDebtService___forGenerateDebtRow_RequestStruct);
            _bodyBlock.setSerializer(ns1_myDebtService___forGenerateDebtRow_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebtRow_ResponseStruct _myDebtService___forGenerateDebtRow_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myDebtService___forGenerateDebtRow_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebtRow_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myDebtService___forGenerateDebtRow_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebtRow_ResponseStruct)_responseObj;
            }
            
            return _myDebtService___forGenerateDebtRow_ResponseStruct.getResult();
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
     *  implementation of getDebts
     */
    public java.util.List getDebts(com.rssl.phizicgate.wsgate.services.documents.generated.RecipientImpl recipientImpl_1, java.util.List list_2)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getDebts_OPCODE);
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService_getDebts_RequestStruct _myDebtService_getDebts_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.documents.generated.DebtService_getDebts_RequestStruct();
            
            _myDebtService_getDebts_RequestStruct.setRecipientImpl_1(recipientImpl_1);
            _myDebtService_getDebts_RequestStruct.setList_2(list_2);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getDebts_getDebts_QNAME);
            _bodyBlock.setValue(_myDebtService_getDebts_RequestStruct);
            _bodyBlock.setSerializer(ns1_myDebtService_getDebts_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService_getDebts_ResponseStruct _myDebtService_getDebts_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myDebtService_getDebts_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService_getDebts_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myDebtService_getDebts_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService_getDebts_ResponseStruct)_responseObj;
            }
            
            return _myDebtService_getDebts_ResponseStruct.getResult();
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
     *  implementation of __forGenerateDebt
     */
    public com.rssl.phizicgate.wsgate.services.documents.generated.DebtImpl __forGenerateDebt()
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(__forGenerateDebt_OPCODE);
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebt_RequestStruct _myDebtService___forGenerateDebt_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebt_RequestStruct();
            
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1___forGenerateDebt___forGenerateDebt_QNAME);
            _bodyBlock.setValue(_myDebtService___forGenerateDebt_RequestStruct);
            _bodyBlock.setSerializer(ns1_myDebtService___forGenerateDebt_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebt_ResponseStruct _myDebtService___forGenerateDebt_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myDebtService___forGenerateDebt_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebt_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myDebtService___forGenerateDebt_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebt_ResponseStruct)_responseObj;
            }
            
            return _myDebtService___forGenerateDebt_ResponseStruct.getResult();
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
     *  implementation of __forGenerateField
     */
    public com.rssl.phizicgate.wsgate.services.documents.generated.Field __forGenerateField()
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(__forGenerateField_OPCODE);
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateField_RequestStruct _myDebtService___forGenerateField_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateField_RequestStruct();
            
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1___forGenerateField___forGenerateField_QNAME);
            _bodyBlock.setValue(_myDebtService___forGenerateField_RequestStruct);
            _bodyBlock.setSerializer(ns1_myDebtService___forGenerateField_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateField_ResponseStruct _myDebtService___forGenerateField_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myDebtService___forGenerateField_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateField_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myDebtService___forGenerateField_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateField_ResponseStruct)_responseObj;
            }
            
            return _myDebtService___forGenerateField_ResponseStruct.getResult();
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
     *  implementation of __forGenerateListValue
     */
    public com.rssl.phizicgate.wsgate.services.documents.generated.ListValue __forGenerateListValue()
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(__forGenerateListValue_OPCODE);
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateListValue_RequestStruct _myDebtService___forGenerateListValue_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateListValue_RequestStruct();
            
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1___forGenerateListValue___forGenerateListValue_QNAME);
            _bodyBlock.setValue(_myDebtService___forGenerateListValue_RequestStruct);
            _bodyBlock.setSerializer(ns1_myDebtService___forGenerateListValue_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateListValue_ResponseStruct _myDebtService___forGenerateListValue_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myDebtService___forGenerateListValue_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateListValue_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myDebtService___forGenerateListValue_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateListValue_ResponseStruct)_responseObj;
            }
            
            return _myDebtService___forGenerateListValue_ResponseStruct.getResult();
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
     *  implementation of __forGeneratePair
     */
    public com.rssl.phizicgate.wsgate.services.documents.generated.Pair __forGeneratePair()
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(__forGeneratePair_OPCODE);
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGeneratePair_RequestStruct _myDebtService___forGeneratePair_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGeneratePair_RequestStruct();
            
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1___forGeneratePair___forGeneratePair_QNAME);
            _bodyBlock.setValue(_myDebtService___forGeneratePair_RequestStruct);
            _bodyBlock.setSerializer(ns1_myDebtService___forGeneratePair_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGeneratePair_ResponseStruct _myDebtService___forGeneratePair_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myDebtService___forGeneratePair_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGeneratePair_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myDebtService___forGeneratePair_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGeneratePair_ResponseStruct)_responseObj;
            }
            
            return _myDebtService___forGeneratePair_ResponseStruct.getResult();
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
            case __forGenerateDebtRow_OPCODE:
                _deserialize___forGenerateDebtRow(bodyReader, deserializationContext, state);
                break;
            case getDebts_OPCODE:
                _deserialize_getDebts(bodyReader, deserializationContext, state);
                break;
            case __forGenerateDebt_OPCODE:
                _deserialize___forGenerateDebt(bodyReader, deserializationContext, state);
                break;
            case __forGenerateField_OPCODE:
                _deserialize___forGenerateField(bodyReader, deserializationContext, state);
                break;
            case __forGenerateListValue_OPCODE:
                _deserialize___forGenerateListValue(bodyReader, deserializationContext, state);
                break;
            case __forGeneratePair_OPCODE:
                _deserialize___forGeneratePair(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SenderException("sender.response.unrecognizedOperation", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the __forGenerateDebtRow operation.
     */
    private void _deserialize___forGenerateDebtRow(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myDebtService___forGenerateDebtRow_ResponseStructObj =
            ns1_myDebtService___forGenerateDebtRow_ResponseStruct_SOAPSerializer.deserialize(ns1___forGenerateDebtRow___forGenerateDebtRowResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGenerateDebtRow___forGenerateDebtRowResponse_QNAME);
        bodyBlock.setValue(myDebtService___forGenerateDebtRow_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getDebts operation.
     */
    private void _deserialize_getDebts(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myDebtService_getDebts_ResponseStructObj =
            ns1_myDebtService_getDebts_ResponseStruct_SOAPSerializer.deserialize(ns1_getDebts_getDebtsResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getDebts_getDebtsResponse_QNAME);
        bodyBlock.setValue(myDebtService_getDebts_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the __forGenerateDebt operation.
     */
    private void _deserialize___forGenerateDebt(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myDebtService___forGenerateDebt_ResponseStructObj =
            ns1_myDebtService___forGenerateDebt_ResponseStruct_SOAPSerializer.deserialize(ns1___forGenerateDebt___forGenerateDebtResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGenerateDebt___forGenerateDebtResponse_QNAME);
        bodyBlock.setValue(myDebtService___forGenerateDebt_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the __forGenerateField operation.
     */
    private void _deserialize___forGenerateField(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myDebtService___forGenerateField_ResponseStructObj =
            ns1_myDebtService___forGenerateField_ResponseStruct_SOAPSerializer.deserialize(ns1___forGenerateField___forGenerateFieldResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGenerateField___forGenerateFieldResponse_QNAME);
        bodyBlock.setValue(myDebtService___forGenerateField_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the __forGenerateListValue operation.
     */
    private void _deserialize___forGenerateListValue(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myDebtService___forGenerateListValue_ResponseStructObj =
            ns1_myDebtService___forGenerateListValue_ResponseStruct_SOAPSerializer.deserialize(ns1___forGenerateListValue___forGenerateListValueResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGenerateListValue___forGenerateListValueResponse_QNAME);
        bodyBlock.setValue(myDebtService___forGenerateListValue_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the __forGeneratePair operation.
     */
    private void _deserialize___forGeneratePair(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myDebtService___forGeneratePair_ResponseStructObj =
            ns1_myDebtService___forGeneratePair_ResponseStruct_SOAPSerializer.deserialize(ns1___forGeneratePair___forGeneratePairResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGeneratePair___forGeneratePairResponse_QNAME);
        bodyBlock.setValue(myDebtService___forGeneratePair_ResponseStructObj);
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
        ns1_myDebtService___forGenerateField_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateField_RequestStruct.class, ns1___forGenerateField_TYPE_QNAME);
        ns1_myDebtService___forGeneratePair_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGeneratePair_ResponseStruct.class, ns1___forGeneratePairResponse_TYPE_QNAME);
        ns1_myDebtService___forGenerateDebtRow_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebtRow_ResponseStruct.class, ns1___forGenerateDebtRowResponse_TYPE_QNAME);
        ns1_myDebtService_getDebts_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService_getDebts_ResponseStruct.class, ns1_getDebtsResponse_TYPE_QNAME);
        ns1_myDebtService___forGenerateListValue_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateListValue_ResponseStruct.class, ns1___forGenerateListValueResponse_TYPE_QNAME);
        ns1_myDebtService___forGenerateField_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateField_ResponseStruct.class, ns1___forGenerateFieldResponse_TYPE_QNAME);
        ns1_myDebtService___forGenerateListValue_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateListValue_RequestStruct.class, ns1___forGenerateListValue_TYPE_QNAME);
        ns1_myDebtService___forGenerateDebtRow_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebtRow_RequestStruct.class, ns1___forGenerateDebtRow_TYPE_QNAME);
        ns1_myDebtService_getDebts_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService_getDebts_RequestStruct.class, ns1_getDebts_TYPE_QNAME);
        ns1_myDebtService___forGenerateDebt_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebt_ResponseStruct.class, ns1___forGenerateDebtResponse_TYPE_QNAME);
        ns1_myDebtService___forGeneratePair_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGeneratePair_RequestStruct.class, ns1___forGeneratePair_TYPE_QNAME);
        ns1_myDebtService___forGenerateDebt_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.DebtService___forGenerateDebt_RequestStruct.class, ns1___forGenerateDebt_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName _portName = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "DebtServicePort");
    private static final int __forGenerateDebtRow_OPCODE = 0;
    private static final int getDebts_OPCODE = 1;
    private static final int __forGenerateDebt_OPCODE = 2;
    private static final int __forGenerateField_OPCODE = 3;
    private static final int __forGenerateListValue_OPCODE = 4;
    private static final int __forGeneratePair_OPCODE = 5;
    private static final javax.xml.namespace.QName ns1___forGenerateDebtRow___forGenerateDebtRow_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateDebtRow");
    private static final javax.xml.namespace.QName ns1___forGenerateDebtRow_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateDebtRow");
    private CombinedSerializer ns1_myDebtService___forGenerateDebtRow_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateDebtRow___forGenerateDebtRowResponse_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateDebtRowResponse");
    private static final javax.xml.namespace.QName ns1___forGenerateDebtRowResponse_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateDebtRowResponse");
    private CombinedSerializer ns1_myDebtService___forGenerateDebtRow_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getDebts_getDebts_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getDebts");
    private static final javax.xml.namespace.QName ns1_getDebts_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getDebts");
    private CombinedSerializer ns1_myDebtService_getDebts_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getDebts_getDebtsResponse_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getDebtsResponse");
    private static final javax.xml.namespace.QName ns1_getDebtsResponse_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getDebtsResponse");
    private CombinedSerializer ns1_myDebtService_getDebts_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateDebt___forGenerateDebt_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateDebt");
    private static final javax.xml.namespace.QName ns1___forGenerateDebt_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateDebt");
    private CombinedSerializer ns1_myDebtService___forGenerateDebt_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateDebt___forGenerateDebtResponse_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateDebtResponse");
    private static final javax.xml.namespace.QName ns1___forGenerateDebtResponse_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateDebtResponse");
    private CombinedSerializer ns1_myDebtService___forGenerateDebt_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateField___forGenerateField_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateField");
    private static final javax.xml.namespace.QName ns1___forGenerateField_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateField");
    private CombinedSerializer ns1_myDebtService___forGenerateField_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateField___forGenerateFieldResponse_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateFieldResponse");
    private static final javax.xml.namespace.QName ns1___forGenerateFieldResponse_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateFieldResponse");
    private CombinedSerializer ns1_myDebtService___forGenerateField_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateListValue___forGenerateListValue_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateListValue");
    private static final javax.xml.namespace.QName ns1___forGenerateListValue_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateListValue");
    private CombinedSerializer ns1_myDebtService___forGenerateListValue_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateListValue___forGenerateListValueResponse_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateListValueResponse");
    private static final javax.xml.namespace.QName ns1___forGenerateListValueResponse_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateListValueResponse");
    private CombinedSerializer ns1_myDebtService___forGenerateListValue_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGeneratePair___forGeneratePair_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGeneratePair");
    private static final javax.xml.namespace.QName ns1___forGeneratePair_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGeneratePair");
    private CombinedSerializer ns1_myDebtService___forGeneratePair_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGeneratePair___forGeneratePairResponse_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGeneratePairResponse");
    private static final javax.xml.namespace.QName ns1___forGeneratePairResponse_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGeneratePairResponse");
    private CombinedSerializer ns1_myDebtService___forGeneratePair_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.documents.services.gate.web.phizic.rssl.com",
                                            "ns1", "http://java.sun.com/jax-rpc-ri/internal"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}
