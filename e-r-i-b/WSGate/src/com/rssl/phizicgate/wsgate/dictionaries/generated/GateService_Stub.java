// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.dictionaries.generated;

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

public class GateService_Stub
    extends com.sun.xml.rpc.client.StubBase
    implements com.rssl.phizicgate.wsgate.dictionaries.generated.GateService {
    
    
    
    /*
     *  public constructor
     */
    public GateService_Stub(HandlerChain handlerChain) {
        super(handlerChain);
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/services/GateServiceImpl");
    }
    
    
    /*
     *  implementation of getAll
     */
    public java.util.List getAll(int int_1, int int_2)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(getAll_OPCODE);
            com.rssl.phizicgate.wsgate.dictionaries.generated.GateService_getAll_RequestStruct _myGateService_getAll_RequestStruct =
                new com.rssl.phizicgate.wsgate.dictionaries.generated.GateService_getAll_RequestStruct();
            
            _myGateService_getAll_RequestStruct.setInt_1(int_1);
            _myGateService_getAll_RequestStruct.setInt_2(int_2);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_getAll_getAll_QNAME);
            _bodyBlock.setValue(_myGateService_getAll_RequestStruct);
            _bodyBlock.setSerializer(ns1_myGateService_getAll_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.dictionaries.generated.GateService_getAll_ResponseStruct _myGateService_getAll_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myGateService_getAll_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.dictionaries.generated.GateService_getAll_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myGateService_getAll_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.dictionaries.generated.GateService_getAll_ResponseStruct)_responseObj;
            }
            
            return _myGateService_getAll_ResponseStruct.getResult();
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
            case getAll_OPCODE:
                _deserialize_getAll(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SenderException("sender.response.unrecognizedOperation", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the getAll operation.
     */
    private void _deserialize_getAll(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myGateService_getAll_ResponseStructObj =
            ns1_myGateService_getAll_ResponseStruct_SOAPSerializer.deserialize(ns1_getAll_getAllResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getAll_getAllResponse_QNAME);
        bodyBlock.setValue(myGateService_getAll_ResponseStructObj);
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
        ns1_myGateService_getAll_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.dictionaries.generated.GateService_getAll_ResponseStruct.class, ns1_getAllResponse_TYPE_QNAME);
        ns1_myGateService_getAll_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.dictionaries.generated.GateService_getAll_RequestStruct.class, ns1_getAll_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName _portName = new QName("http://generated.dictionaries.gate.web.phizic.rssl.com", "GateServicePort");
    private static final int getAll_OPCODE = 0;
    private static final javax.xml.namespace.QName ns1_getAll_getAll_QNAME = new QName("http://generated.dictionaries.gate.web.phizic.rssl.com", "getAll");
    private static final javax.xml.namespace.QName ns1_getAll_TYPE_QNAME = new QName("http://generated.dictionaries.gate.web.phizic.rssl.com", "getAll");
    private CombinedSerializer ns1_myGateService_getAll_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getAll_getAllResponse_QNAME = new QName("http://generated.dictionaries.gate.web.phizic.rssl.com", "getAllResponse");
    private static final javax.xml.namespace.QName ns1_getAllResponse_TYPE_QNAME = new QName("http://generated.dictionaries.gate.web.phizic.rssl.com", "getAllResponse");
    private CombinedSerializer ns1_myGateService_getAll_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.dictionaries.gate.web.phizic.rssl.com",
                                            "ns1", "http://java.sun.com/jax-rpc-ri/internal"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}
