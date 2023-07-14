// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated;

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

public class ExceptionStatisticService_Stub
    extends com.sun.xml.rpc.client.StubBase
    implements com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService {
    
    
    
    /*
     *  public constructor
     */
    public ExceptionStatisticService_Stub(HandlerChain handlerChain) {
        super(handlerChain);
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/services/ExceptionStatisticServiceImpl");
    }
    
    
    /*
     *  implementation of addException
     */
    public java.lang.String addException(com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo externalExceptionInfo_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(addException_OPCODE);
            com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService_addException_RequestStruct _myExceptionStatisticService_addException_RequestStruct =
                new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService_addException_RequestStruct();
            
            _myExceptionStatisticService_addException_RequestStruct.setExternalExceptionInfo_1(externalExceptionInfo_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_addException_addException_QNAME);
            _bodyBlock.setValue(_myExceptionStatisticService_addException_RequestStruct);
            _bodyBlock.setSerializer(ns1_myExceptionStatisticService_addException_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService_addException_ResponseStruct _myExceptionStatisticService_addException_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myExceptionStatisticService_addException_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService_addException_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myExceptionStatisticService_addException_ResponseStruct =
                    (com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService_addException_ResponseStruct)_responseObj;
            }
            
            return _myExceptionStatisticService_addException_ResponseStruct.getResult();
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
            case addException_OPCODE:
                _deserialize_addException(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SenderException("sender.response.unrecognizedOperation", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the addException operation.
     */
    private void _deserialize_addException(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myExceptionStatisticService_addException_ResponseStructObj =
            ns1_myExceptionStatisticService_addException_ResponseStruct_SOAPSerializer.deserialize(ns1_addException_addExceptionResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_addException_addExceptionResponse_QNAME);
        bodyBlock.setValue(myExceptionStatisticService_addException_ResponseStructObj);
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
        ns1_myExceptionStatisticService_addException_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService_addException_RequestStruct.class, ns1_addException_TYPE_QNAME);
        ns1_myExceptionStatisticService_addException_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExceptionStatisticService_addException_ResponseStruct.class, ns1_addExceptionResponse_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName _portName = new QName("http://generated.exception.statistics.wsgate.phizic.rssl.com", "ExceptionStatisticServicePort");
    private static final int addException_OPCODE = 0;
    private static final javax.xml.namespace.QName ns1_addException_addException_QNAME = new QName("http://generated.exception.statistics.wsgate.phizic.rssl.com", "addException");
    private static final javax.xml.namespace.QName ns1_addException_TYPE_QNAME = new QName("http://generated.exception.statistics.wsgate.phizic.rssl.com", "addException");
    private CombinedSerializer ns1_myExceptionStatisticService_addException_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_addException_addExceptionResponse_QNAME = new QName("http://generated.exception.statistics.wsgate.phizic.rssl.com", "addExceptionResponse");
    private static final javax.xml.namespace.QName ns1_addExceptionResponse_TYPE_QNAME = new QName("http://generated.exception.statistics.wsgate.phizic.rssl.com", "addExceptionResponse");
    private CombinedSerializer ns1_myExceptionStatisticService_addException_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.exception.statistics.wsgate.phizic.rssl.com"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}