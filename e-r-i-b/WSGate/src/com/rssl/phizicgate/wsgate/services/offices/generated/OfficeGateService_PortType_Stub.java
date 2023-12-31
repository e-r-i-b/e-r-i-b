// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.offices.generated;

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

public class OfficeGateService_PortType_Stub
    extends com.sun.xml.rpc.client.StubBase
    implements com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType {
    
    
    
    /*
     *  public constructor
     */
    public OfficeGateService_PortType_Stub(HandlerChain handlerChain) {
        super(handlerChain);
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/services/OfficeGateService");
    }
    
    
    /*
     *  implementation of getOfficeById
     */
    public com.rssl.phizicgate.wsgate.services.offices.generated.Office getOfficeById(java.lang.String string_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getOfficeById_OPCODE);
            com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getOfficeById_RequestStruct _myOfficeGateService_PortType_getOfficeById_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getOfficeById_RequestStruct();
            
            _myOfficeGateService_PortType_getOfficeById_RequestStruct.setString_1(string_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getOfficeById_getOfficeById_QNAME);
            _bodyBlock.setValue(_myOfficeGateService_PortType_getOfficeById_RequestStruct);
            _bodyBlock.setSerializer(ns1_myOfficeGateService_PortType_getOfficeById_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getOfficeById_ResponseStruct _myOfficeGateService_PortType_getOfficeById_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myOfficeGateService_PortType_getOfficeById_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getOfficeById_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myOfficeGateService_PortType_getOfficeById_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getOfficeById_ResponseStruct)_responseObj;
            }
            
            return _myOfficeGateService_PortType_getOfficeById_ResponseStruct.getResult();
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
     *  implementation of getAll
     */
    public java.util.List getAll(com.rssl.phizicgate.wsgate.services.offices.generated.Office office_1, int int_2, int int_3)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getAll_OPCODE);
            com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll_RequestStruct _myOfficeGateService_PortType_getAll_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll_RequestStruct();
            
            _myOfficeGateService_PortType_getAll_RequestStruct.setOffice_1(office_1);
            _myOfficeGateService_PortType_getAll_RequestStruct.setInt_2(int_2);
            _myOfficeGateService_PortType_getAll_RequestStruct.setInt_3(int_3);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getAll_getAll_QNAME);
            _bodyBlock.setValue(_myOfficeGateService_PortType_getAll_RequestStruct);
            _bodyBlock.setSerializer(ns1_myOfficeGateService_PortType_getAll_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll_ResponseStruct _myOfficeGateService_PortType_getAll_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myOfficeGateService_PortType_getAll_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myOfficeGateService_PortType_getAll_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll_ResponseStruct)_responseObj;
            }
            
            return _myOfficeGateService_PortType_getAll_ResponseStruct.getResult();
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
     *  implementation of getAll2
     */
    public java.util.List getAll2(int int_1, int int_2)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getAll2_OPCODE);
            com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll2_RequestStruct _myOfficeGateService_PortType_getAll2_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll2_RequestStruct();
            
            _myOfficeGateService_PortType_getAll2_RequestStruct.setInt_1(int_1);
            _myOfficeGateService_PortType_getAll2_RequestStruct.setInt_2(int_2);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getAll2_getAll2_QNAME);
            _bodyBlock.setValue(_myOfficeGateService_PortType_getAll2_RequestStruct);
            _bodyBlock.setSerializer(ns1_myOfficeGateService_PortType_getAll2_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll2_ResponseStruct _myOfficeGateService_PortType_getAll2_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myOfficeGateService_PortType_getAll2_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll2_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myOfficeGateService_PortType_getAll2_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll2_ResponseStruct)_responseObj;
            }
            
            return _myOfficeGateService_PortType_getAll2_ResponseStruct.getResult();
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
     *  implementation of getAllChildren
     */
    public java.util.List getAllChildren(com.rssl.phizicgate.wsgate.services.offices.generated.Office office_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getAllChildren_OPCODE);
            com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAllChildren_RequestStruct _myOfficeGateService_PortType_getAllChildren_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAllChildren_RequestStruct();
            
            _myOfficeGateService_PortType_getAllChildren_RequestStruct.setOffice_1(office_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getAllChildren_getAllChildren_QNAME);
            _bodyBlock.setValue(_myOfficeGateService_PortType_getAllChildren_RequestStruct);
            _bodyBlock.setSerializer(ns1_myOfficeGateService_PortType_getAllChildren_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAllChildren_ResponseStruct _myOfficeGateService_PortType_getAllChildren_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myOfficeGateService_PortType_getAllChildren_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAllChildren_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myOfficeGateService_PortType_getAllChildren_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAllChildren_ResponseStruct)_responseObj;
            }
            
            return _myOfficeGateService_PortType_getAllChildren_ResponseStruct.getResult();
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
            case getOfficeById_OPCODE:
                _deserialize_getOfficeById(bodyReader, deserializationContext, state);
                break;
            case getAll_OPCODE:
                _deserialize_getAll(bodyReader, deserializationContext, state);
                break;
            case getAll2_OPCODE:
                _deserialize_getAll2(bodyReader, deserializationContext, state);
                break;
            case getAllChildren_OPCODE:
                _deserialize_getAllChildren(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SenderException("sender.response.unrecognizedOperation", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the getOfficeById operation.
     */
    private void _deserialize_getOfficeById(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myOfficeGateService_PortType_getOfficeById_ResponseStructObj =
            ns1_myOfficeGateService_PortType_getOfficeById_ResponseStruct_SOAPSerializer.deserialize(ns1_getOfficeById_getOfficeByIdResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getOfficeById_getOfficeByIdResponse_QNAME);
        bodyBlock.setValue(myOfficeGateService_PortType_getOfficeById_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getAll operation.
     */
    private void _deserialize_getAll(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myOfficeGateService_PortType_getAll_ResponseStructObj =
            ns1_myOfficeGateService_PortType_getAll_ResponseStruct_SOAPSerializer.deserialize(ns1_getAll_getAllResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getAll_getAllResponse_QNAME);
        bodyBlock.setValue(myOfficeGateService_PortType_getAll_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getAll2 operation.
     */
    private void _deserialize_getAll2(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myOfficeGateService_PortType_getAll2_ResponseStructObj =
            ns1_myOfficeGateService_PortType_getAll2_ResponseStruct_SOAPSerializer.deserialize(ns1_getAll2_getAll2Response_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getAll2_getAll2Response_QNAME);
        bodyBlock.setValue(myOfficeGateService_PortType_getAll2_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getAllChildren operation.
     */
    private void _deserialize_getAllChildren(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myOfficeGateService_PortType_getAllChildren_ResponseStructObj =
            ns1_myOfficeGateService_PortType_getAllChildren_ResponseStruct_SOAPSerializer.deserialize(ns1_getAllChildren_getAllChildrenResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getAllChildren_getAllChildrenResponse_QNAME);
        bodyBlock.setValue(myOfficeGateService_PortType_getAllChildren_ResponseStructObj);
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
        ns1_myOfficeGateService_PortType_getAllChildren_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAllChildren_ResponseStruct.class, ns1_getAllChildrenResponse_TYPE_QNAME);
        ns1_myOfficeGateService_PortType_getOfficeById_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getOfficeById_RequestStruct.class, ns1_getOfficeById_TYPE_QNAME);
        ns1_myOfficeGateService_PortType_getAll_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll_RequestStruct.class, ns1_getAll_TYPE_QNAME);
        ns1_myOfficeGateService_PortType_getOfficeById_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getOfficeById_ResponseStruct.class, ns1_getOfficeByIdResponse_TYPE_QNAME);
        ns1_myOfficeGateService_PortType_getAll2_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll2_ResponseStruct.class, ns1_getAll2Response_TYPE_QNAME);
        ns1_myOfficeGateService_PortType_getAllChildren_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAllChildren_RequestStruct.class, ns1_getAllChildren_TYPE_QNAME);
        ns1_myOfficeGateService_PortType_getAll2_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll2_RequestStruct.class, ns1_getAll2_TYPE_QNAME);
        ns1_myOfficeGateService_PortType_getAll_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.offices.generated.OfficeGateService_PortType_getAll_ResponseStruct.class, ns1_getAllResponse_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName _portName = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "OfficeGateService_PortTypePort");
    private static final int getOfficeById_OPCODE = 0;
    private static final int getAll_OPCODE = 1;
    private static final int getAll2_OPCODE = 2;
    private static final int getAllChildren_OPCODE = 3;
    private static final javax.xml.namespace.QName ns1_getOfficeById_getOfficeById_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getOfficeById");
    private static final javax.xml.namespace.QName ns1_getOfficeById_TYPE_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getOfficeById");
    private CombinedSerializer ns1_myOfficeGateService_PortType_getOfficeById_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getOfficeById_getOfficeByIdResponse_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getOfficeByIdResponse");
    private static final javax.xml.namespace.QName ns1_getOfficeByIdResponse_TYPE_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getOfficeByIdResponse");
    private CombinedSerializer ns1_myOfficeGateService_PortType_getOfficeById_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getAll_getAll_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAll");
    private static final javax.xml.namespace.QName ns1_getAll_TYPE_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAll");
    private CombinedSerializer ns1_myOfficeGateService_PortType_getAll_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getAll_getAllResponse_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAllResponse");
    private static final javax.xml.namespace.QName ns1_getAllResponse_TYPE_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAllResponse");
    private CombinedSerializer ns1_myOfficeGateService_PortType_getAll_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getAll2_getAll2_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAll2");
    private static final javax.xml.namespace.QName ns1_getAll2_TYPE_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAll2");
    private CombinedSerializer ns1_myOfficeGateService_PortType_getAll2_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getAll2_getAll2Response_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAll2Response");
    private static final javax.xml.namespace.QName ns1_getAll2Response_TYPE_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAll2Response");
    private CombinedSerializer ns1_myOfficeGateService_PortType_getAll2_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getAllChildren_getAllChildren_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAllChildren");
    private static final javax.xml.namespace.QName ns1_getAllChildren_TYPE_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAllChildren");
    private CombinedSerializer ns1_myOfficeGateService_PortType_getAllChildren_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getAllChildren_getAllChildrenResponse_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAllChildrenResponse");
    private static final javax.xml.namespace.QName ns1_getAllChildrenResponse_TYPE_QNAME = new QName("http://generated.offices.services.gate.web.phizic.rssl.com", "getAllChildrenResponse");
    private CombinedSerializer ns1_myOfficeGateService_PortType_getAllChildren_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.offices.services.gate.web.phizic.rssl.com",
                                            "ns1", "http://java.sun.com/jax-rpc-ri/internal"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}
