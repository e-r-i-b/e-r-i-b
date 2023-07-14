// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.monitoring.generated;

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

public class RunGateMonitoringService_Stub
    extends com.sun.xml.rpc.client.StubBase
    implements com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService {
    
    
    
    /*
     *  public constructor
     */
    public RunGateMonitoringService_Stub(HandlerChain handlerChain) {
        super(handlerChain);
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/services/RunGateMonitoringServiceImpl");
    }
    
    
    /*
     *  implementation of run
     */
    public void run(com.rssl.phizicgate.wsgate.services.monitoring.generated.MonitoringParameters monitoringParameters_1)
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(run_OPCODE);
            com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService_run_RequestStruct _myRunGateMonitoringService_run_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService_run_RequestStruct();
            
            _myRunGateMonitoringService_run_RequestStruct.setMonitoringParameters_1(monitoringParameters_1);
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_run_run_QNAME);
            _bodyBlock.setValue(_myRunGateMonitoringService_run_RequestStruct);
            _bodyBlock.setSerializer(ns1_myRunGateMonitoringService_run_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService_run_ResponseStruct _myRunGateMonitoringService_run_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myRunGateMonitoringService_run_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService_run_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myRunGateMonitoringService_run_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService_run_ResponseStruct)_responseObj;
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
     *  implementation of __forGenerateMonitoringStateParameters
     */
    public com.rssl.phizicgate.wsgate.services.monitoring.generated.MonitoringStateParameters __forGenerateMonitoringStateParameters()
        throws java.rmi.RemoteException {
        
        try {
            
            StreamingSenderState _state = _start(_handlerChain);
            
            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(__forGenerateMonitoringStateParameters_OPCODE);
            com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct _myRunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct =
                new com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct();
            
            
            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1___forGenerateMonitoringStateParameters___forGenerateMonitoringStateParameters_QNAME);
            _bodyBlock.setValue(_myRunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct);
            _bodyBlock.setSerializer(ns1_myRunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);
            
            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");
            
            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);
            
            com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct _myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct)((SOAPDeserializationState)_responseObj).getInstance();
            } else {
                _myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct =
                    (com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct)_responseObj;
            }
            
            return _myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct.getResult();
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
            case run_OPCODE:
                _deserialize_run(bodyReader, deserializationContext, state);
                break;
            case __forGenerateMonitoringStateParameters_OPCODE:
                _deserialize___forGenerateMonitoringStateParameters(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SenderException("sender.response.unrecognizedOperation", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the run operation.
     */
    private void _deserialize_run(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myRunGateMonitoringService_run_ResponseStructObj =
            ns1_myRunGateMonitoringService_run_ResponseStruct_SOAPSerializer.deserialize(ns1_run_runResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_run_runResponse_QNAME);
        bodyBlock.setValue(myRunGateMonitoringService_run_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the __forGenerateMonitoringStateParameters operation.
     */
    private void _deserialize___forGenerateMonitoringStateParameters(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStructObj =
            ns1_myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct_SOAPSerializer.deserialize(ns1___forGenerateMonitoringStateParameters___forGenerateMonitoringStateParametersResponse_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGenerateMonitoringStateParameters___forGenerateMonitoringStateParametersResponse_QNAME);
        bodyBlock.setValue(myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStructObj);
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
        ns1_myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct.class, ns1___forGenerateMonitoringStateParametersResponse_TYPE_QNAME);
        ns1_myRunGateMonitoringService_run_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService_run_ResponseStruct.class, ns1_runResponse_TYPE_QNAME);
        ns1_myRunGateMonitoringService_run_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService_run_RequestStruct.class, ns1_run_TYPE_QNAME);
        ns1_myRunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct.class, ns1___forGenerateMonitoringStateParameters_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName _portName = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "RunGateMonitoringServicePort");
    private static final int run_OPCODE = 0;
    private static final int __forGenerateMonitoringStateParameters_OPCODE = 1;
    private static final javax.xml.namespace.QName ns1_run_run_QNAME = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "run");
    private static final javax.xml.namespace.QName ns1_run_TYPE_QNAME = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "run");
    private CombinedSerializer ns1_myRunGateMonitoringService_run_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_run_runResponse_QNAME = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "runResponse");
    private static final javax.xml.namespace.QName ns1_runResponse_TYPE_QNAME = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "runResponse");
    private CombinedSerializer ns1_myRunGateMonitoringService_run_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateMonitoringStateParameters___forGenerateMonitoringStateParameters_QNAME = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "__forGenerateMonitoringStateParameters");
    private static final javax.xml.namespace.QName ns1___forGenerateMonitoringStateParameters_TYPE_QNAME = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "__forGenerateMonitoringStateParameters");
    private CombinedSerializer ns1_myRunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1___forGenerateMonitoringStateParameters___forGenerateMonitoringStateParametersResponse_QNAME = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "__forGenerateMonitoringStateParametersResponse");
    private static final javax.xml.namespace.QName ns1___forGenerateMonitoringStateParametersResponse_TYPE_QNAME = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "__forGenerateMonitoringStateParametersResponse");
    private CombinedSerializer ns1_myRunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.monitoring.services.gate.web.phizic.rssl.com",
                                            "ns1", "http://java.sun.com/jax-rpc-ri/internal"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}