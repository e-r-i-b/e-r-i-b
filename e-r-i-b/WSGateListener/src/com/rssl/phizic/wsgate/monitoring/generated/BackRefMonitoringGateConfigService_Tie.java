// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.monitoring.generated;

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
import com.sun.xml.rpc.server.*;
import javax.xml.rpc.handler.HandlerInfo;
import com.sun.xml.rpc.client.HandlerChainImpl;

public class BackRefMonitoringGateConfigService_Tie
    extends com.sun.xml.rpc.server.TieBase implements SerializerConstants {
    
    
    
    public BackRefMonitoringGateConfigService_Tie() throws Exception {
        super(new com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigServiceImpl_SerializerRegistry().getRegistry());
        initialize(internalTypeMappingRegistry);
    }
    
    /*
     * This method does the actual method invocation for operation: getMonitoringGateConfig
     */
    private void invoke_getMonitoringGateConfig(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct = null;
        Object myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStructObj instanceof SOAPDeserializationState) {
            myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct = (com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct)((SOAPDeserializationState)myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStructObj).getInstance();
        } else {
            myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct = (com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct)myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStructObj;
        }
        
        try {
            com.rssl.phizic.wsgate.monitoring.generated.MonitoringServiceGateConfigImpl result = 
                ((com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService) getTarget()).getMonitoringGateConfig(myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct.getString_1());
            com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct myBackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct =
                new com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            myBackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct.setResult(result);
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getMonitoringGateConfig_getMonitoringGateConfigResponse_QNAME);
            bodyBlock.setValue(myBackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct_SOAPSerializer);
            state.getResponse().setBody(bodyBlock);
        } catch (javax.xml.rpc.soap.SOAPFaultException e) {
            SOAPFaultInfo fault = new SOAPFaultInfo(e.getFaultCode(),
                e.getFaultString(), e.getFaultActor(), e.getDetail());
            SOAPBlockInfo faultBlock = new SOAPBlockInfo(com.sun.xml.rpc.encoding.soap.SOAPConstants.QNAME_SOAP_FAULT);
            faultBlock.setValue(fault);
            faultBlock.setSerializer(new SOAPFaultInfoSerializer(false, e.getDetail()==null));
            state.getResponse().setBody(faultBlock);
            state.getResponse().setFailure(true);
        }
    }
    
    /*
     * This method does the actual method invocation for operation: setState
     */
    private void invoke_setState(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_setState_RequestStruct myBackRefMonitoringGateConfigService_setState_RequestStruct = null;
        Object myBackRefMonitoringGateConfigService_setState_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBackRefMonitoringGateConfigService_setState_RequestStructObj instanceof SOAPDeserializationState) {
            myBackRefMonitoringGateConfigService_setState_RequestStruct = (com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_setState_RequestStruct)((SOAPDeserializationState)myBackRefMonitoringGateConfigService_setState_RequestStructObj).getInstance();
        } else {
            myBackRefMonitoringGateConfigService_setState_RequestStruct = (com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_setState_RequestStruct)myBackRefMonitoringGateConfigService_setState_RequestStructObj;
        }
        
        try {
            ((com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService) getTarget()).setState(myBackRefMonitoringGateConfigService_setState_RequestStruct.getString_1(), myBackRefMonitoringGateConfigService_setState_RequestStruct.getString_2());
            com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_setState_ResponseStruct myBackRefMonitoringGateConfigService_setState_ResponseStruct =
                new com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_setState_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_setState_setStateResponse_QNAME);
            bodyBlock.setValue(myBackRefMonitoringGateConfigService_setState_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBackRefMonitoringGateConfigService_setState_ResponseStruct_SOAPSerializer);
            state.getResponse().setBody(bodyBlock);
        } catch (javax.xml.rpc.soap.SOAPFaultException e) {
            SOAPFaultInfo fault = new SOAPFaultInfo(e.getFaultCode(),
                e.getFaultString(), e.getFaultActor(), e.getDetail());
            SOAPBlockInfo faultBlock = new SOAPBlockInfo(com.sun.xml.rpc.encoding.soap.SOAPConstants.QNAME_SOAP_FAULT);
            faultBlock.setValue(fault);
            faultBlock.setSerializer(new SOAPFaultInfoSerializer(false, e.getDetail()==null));
            state.getResponse().setBody(faultBlock);
            state.getResponse().setFailure(true);
        }
    }
    
    /*
     * This method must determine the opcode of the operation that has been invoked.
     */
    protected void peekFirstBodyElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        if (bodyReader.getName().equals(ns1_getMonitoringGateConfig_getMonitoringGateConfig_QNAME)) {
            state.getRequest().setOperationCode(getMonitoringGateConfig_OPCODE);
        }
        else if (bodyReader.getName().equals(ns1_setState_setState_QNAME)) {
            state.getRequest().setOperationCode(setState_OPCODE);
        }
        else {
            throw new SOAPProtocolViolationException("soap.operation.unrecognized", bodyReader.getName().toString());
        }
    }
    
    /*
     *  this method deserializes the request/response structure in the body
     */
    protected void readFirstBodyElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState  state) throws Exception {
        int opcode = state.getRequest().getOperationCode();
        switch (opcode) {
            case getMonitoringGateConfig_OPCODE:
                deserialize_getMonitoringGateConfig(bodyReader, deserializationContext, state);
                break;
            case setState_OPCODE:
                deserialize_setState(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SOAPProtocolViolationException("soap.operation.unrecognized", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the getMonitoringGateConfig operation.
     */
    private void deserialize_getMonitoringGateConfig(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStructObj =
            ns1_myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct_SOAPSerializer.deserialize(ns1_getMonitoringGateConfig_getMonitoringGateConfig_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getMonitoringGateConfig_getMonitoringGateConfig_QNAME);
        bodyBlock.setValue(myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the setState operation.
     */
    private void deserialize_setState(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBackRefMonitoringGateConfigService_setState_RequestStructObj =
            ns1_myBackRefMonitoringGateConfigService_setState_RequestStruct_SOAPSerializer.deserialize(ns1_setState_setState_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_setState_setState_QNAME);
        bodyBlock.setValue(myBackRefMonitoringGateConfigService_setState_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    
    /*
     * This method must invoke the correct method on the servant based on the opcode.
     */
    protected void processingHook(StreamingHandlerState state) throws Exception {
        switch (state.getRequest().getOperationCode()) {
            case getMonitoringGateConfig_OPCODE:
                invoke_getMonitoringGateConfig(state);
                break;
            case setState_OPCODE:
                invoke_setState(state);
                break;
            default:
                throw new SOAPProtocolViolationException("soap.operation.unrecognized", java.lang.Integer.toString(state.getRequest().getOperationCode()));
        }
    }
    
    public java.lang.String getDefaultEnvelopeEncodingStyle() {
        return SOAPNamespaceConstants.ENCODING;
    }
    
    public java.lang.String getImplicitEnvelopeEncodingStyle() {
        return "";
    }
    
    
    /*
     * This method must determine the opcode of the operation given the QName of the first body element.
     */
    public int getOpcodeForFirstBodyElementName(QName name) {
        if (name == null) {
            return InternalSOAPMessage.NO_OPERATION;
        }
        if (name.equals(ns1_getMonitoringGateConfig_getMonitoringGateConfig_QNAME)) {
            return getMonitoringGateConfig_OPCODE;
        }
        if (name.equals(ns1_setState_setState_QNAME)) {
            return setState_OPCODE;
        }
        return super.getOpcodeForFirstBodyElementName(name);
    }
    
    
    private Method internalGetMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {
        
        Method theMethod = null;
        
        switch(opcode) {
            case getMonitoringGateConfig_OPCODE:
                {
                    Class[] carray = { java.lang.String.class };
                    theMethod = (com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService.class).getMethod("getMonitoringGateConfig", carray);
                }
                break;
            
            case setState_OPCODE:
                {
                    Class[] carray = { java.lang.String.class,java.lang.String.class };
                    theMethod = (com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService.class).getMethod("setState", carray);
                }
                break;
            
            default:
        }
        return theMethod;
    }
    
    private Method[] methodMap = new Method[2];
    
    /*
     * This method returns the Method Obj for a specified opcode.
     */
    public Method getMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {
         
        if (opcode <= InternalSOAPMessage.NO_OPERATION ) {
            return null;
        }
         
        if (opcode >= 2 ) {
            return null;
        }
         
        if (methodMap[opcode] == null)  {
            methodMap[opcode] = internalGetMethodForOpcode(opcode);
        }
         
        return methodMap[opcode];
    }
    
    /*
     * This method returns an array containing (prefix, nsURI) pairs.
     */
    protected java.lang.String[] getNamespaceDeclarations() {
        return myNamespace_declarations;
    }
    
    /*
     * This method returns an array containing the names of the headers we understand.
     */
    public javax.xml.namespace.QName[] getUnderstoodHeaders() {
        return understoodHeaderNames;
    }
    
    private void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns1_myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct.class, ns1_getMonitoringGateConfig_TYPE_QNAME);
        ns1_myBackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct.class, ns1_getMonitoringGateConfigResponse_TYPE_QNAME);
        ns1_myBackRefMonitoringGateConfigService_setState_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_setState_ResponseStruct.class, ns1_setStateResponse_TYPE_QNAME);
        ns1_myBackRefMonitoringGateConfigService_setState_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.monitoring.generated.BackRefMonitoringGateConfigService_setState_RequestStruct.class, ns1_setState_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName portName = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "BackRefMonitoringGateConfigServicePort");
    private static final int getMonitoringGateConfig_OPCODE = 0;
    private static final int setState_OPCODE = 1;
    private static final javax.xml.namespace.QName ns1_getMonitoringGateConfig_getMonitoringGateConfig_QNAME = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "getMonitoringGateConfig");
    private static final javax.xml.namespace.QName ns1_getMonitoringGateConfig_TYPE_QNAME = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "getMonitoringGateConfig");
    private CombinedSerializer ns1_myBackRefMonitoringGateConfigService_getMonitoringGateConfig_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_getMonitoringGateConfig_getMonitoringGateConfigResponse_QNAME = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "getMonitoringGateConfigResponse");
    private static final javax.xml.namespace.QName ns1_getMonitoringGateConfigResponse_TYPE_QNAME = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "getMonitoringGateConfigResponse");
    private CombinedSerializer ns1_myBackRefMonitoringGateConfigService_getMonitoringGateConfig_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_setState_setState_QNAME = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "setState");
    private static final javax.xml.namespace.QName ns1_setState_TYPE_QNAME = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "setState");
    private CombinedSerializer ns1_myBackRefMonitoringGateConfigService_setState_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_setState_setStateResponse_QNAME = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "setStateResponse");
    private static final javax.xml.namespace.QName ns1_setStateResponse_TYPE_QNAME = new QName("http://generated.monitoring.wsgate.phizic.rssl.com", "setStateResponse");
    private CombinedSerializer ns1_myBackRefMonitoringGateConfigService_setState_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.monitoring.wsgate.phizic.rssl.com",
                                            "ns1", "http://java.sun.com/jax-rpc-ri/internal"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}
