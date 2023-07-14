// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.clients.backrefservice.generated;

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

public class BackRefClientService_Tie
    extends com.sun.xml.rpc.server.TieBase implements SerializerConstants {
    
    
    
    public BackRefClientService_Tie() throws Exception {
        super(new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientServiceImpl_SerializerRegistry().getRegistry());
        initialize(internalTypeMappingRegistry);
    }
    
    /*
     * This method does the actual method invocation for operation: __forGenerateClientDocument
     */
    private void invoke___forGenerateClientDocument(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct myBackRefClientService___forGenerateClientDocument_RequestStruct = null;
        Object myBackRefClientService___forGenerateClientDocument_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBackRefClientService___forGenerateClientDocument_RequestStructObj instanceof SOAPDeserializationState) {
            myBackRefClientService___forGenerateClientDocument_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct)((SOAPDeserializationState)myBackRefClientService___forGenerateClientDocument_RequestStructObj).getInstance();
        } else {
            myBackRefClientService___forGenerateClientDocument_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct)myBackRefClientService___forGenerateClientDocument_RequestStructObj;
        }
        
        try {
            ((com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService) getTarget()).__forGenerateClientDocument(myBackRefClientService___forGenerateClientDocument_RequestStruct.getClientDocument_1());
            com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct myBackRefClientService___forGenerateClientDocument_ResponseStruct =
                new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGenerateClientDocument___forGenerateClientDocumentResponse_QNAME);
            bodyBlock.setValue(myBackRefClientService___forGenerateClientDocument_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBackRefClientService___forGenerateClientDocument_ResponseStruct_SOAPSerializer);
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
     * This method does the actual method invocation for operation: getClientById
     */
    private void invoke_getClientById(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct myBackRefClientService_getClientById_RequestStruct = null;
        Object myBackRefClientService_getClientById_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBackRefClientService_getClientById_RequestStructObj instanceof SOAPDeserializationState) {
            myBackRefClientService_getClientById_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct)((SOAPDeserializationState)myBackRefClientService_getClientById_RequestStructObj).getInstance();
        } else {
            myBackRefClientService_getClientById_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct)myBackRefClientService_getClientById_RequestStructObj;
        }
        
        try {
            com.rssl.phizic.wsgate.clients.backrefservice.generated.Client result = 
                ((com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService) getTarget()).getClientById(myBackRefClientService_getClientById_RequestStruct.getLong_1());
            com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct myBackRefClientService_getClientById_ResponseStruct =
                new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            myBackRefClientService_getClientById_ResponseStruct.setResult(result);
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientById_getClientByIdResponse_QNAME);
            bodyBlock.setValue(myBackRefClientService_getClientById_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBackRefClientService_getClientById_ResponseStruct_SOAPSerializer);
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
     * This method does the actual method invocation for operation: getClientDepartmentCode
     */
    private void invoke_getClientDepartmentCode(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct myBackRefClientService_getClientDepartmentCode_RequestStruct = null;
        Object myBackRefClientService_getClientDepartmentCode_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBackRefClientService_getClientDepartmentCode_RequestStructObj instanceof SOAPDeserializationState) {
            myBackRefClientService_getClientDepartmentCode_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct)((SOAPDeserializationState)myBackRefClientService_getClientDepartmentCode_RequestStructObj).getInstance();
        } else {
            myBackRefClientService_getClientDepartmentCode_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct)myBackRefClientService_getClientDepartmentCode_RequestStructObj;
        }
        
        try {
            java.lang.String result = 
                ((com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService) getTarget()).getClientDepartmentCode(myBackRefClientService_getClientDepartmentCode_RequestStruct.getLong_1());
            com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct myBackRefClientService_getClientDepartmentCode_ResponseStruct =
                new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            myBackRefClientService_getClientDepartmentCode_ResponseStruct.setResult(result);
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientDepartmentCode_getClientDepartmentCodeResponse_QNAME);
            bodyBlock.setValue(myBackRefClientService_getClientDepartmentCode_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBackRefClientService_getClientDepartmentCode_ResponseStruct_SOAPSerializer);
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
     * This method does the actual method invocation for operation: getClientByFIOAndDoc
     */
    private void invoke_getClientByFIOAndDoc(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct myBackRefClientService_getClientByFIOAndDoc_RequestStruct = null;
        Object myBackRefClientService_getClientByFIOAndDoc_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBackRefClientService_getClientByFIOAndDoc_RequestStructObj instanceof SOAPDeserializationState) {
            myBackRefClientService_getClientByFIOAndDoc_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct)((SOAPDeserializationState)myBackRefClientService_getClientByFIOAndDoc_RequestStructObj).getInstance();
        } else {
            myBackRefClientService_getClientByFIOAndDoc_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct)myBackRefClientService_getClientByFIOAndDoc_RequestStructObj;
        }
        
        try {
            com.rssl.phizic.wsgate.clients.backrefservice.generated.Client result = 
                ((com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService) getTarget()).getClientByFIOAndDoc(myBackRefClientService_getClientByFIOAndDoc_RequestStruct.getString_1(), myBackRefClientService_getClientByFIOAndDoc_RequestStruct.getString_2(), myBackRefClientService_getClientByFIOAndDoc_RequestStruct.getString_3(), myBackRefClientService_getClientByFIOAndDoc_RequestStruct.getString_4(), myBackRefClientService_getClientByFIOAndDoc_RequestStruct.getString_5(), myBackRefClientService_getClientByFIOAndDoc_RequestStruct.getCalendar_6(), myBackRefClientService_getClientByFIOAndDoc_RequestStruct.getString_7());
            com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct myBackRefClientService_getClientByFIOAndDoc_ResponseStruct =
                new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            myBackRefClientService_getClientByFIOAndDoc_ResponseStruct.setResult(result);
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientByFIOAndDoc_getClientByFIOAndDocResponse_QNAME);
            bodyBlock.setValue(myBackRefClientService_getClientByFIOAndDoc_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBackRefClientService_getClientByFIOAndDoc_ResponseStruct_SOAPSerializer);
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
     * This method does the actual method invocation for operation: getClientCreationType
     */
    private void invoke_getClientCreationType(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct myBackRefClientService_getClientCreationType_RequestStruct = null;
        Object myBackRefClientService_getClientCreationType_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBackRefClientService_getClientCreationType_RequestStructObj instanceof SOAPDeserializationState) {
            myBackRefClientService_getClientCreationType_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct)((SOAPDeserializationState)myBackRefClientService_getClientCreationType_RequestStructObj).getInstance();
        } else {
            myBackRefClientService_getClientCreationType_RequestStruct = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct)myBackRefClientService_getClientCreationType_RequestStructObj;
        }
        
        try {
            java.lang.String result = 
                ((com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService) getTarget()).getClientCreationType(myBackRefClientService_getClientCreationType_RequestStruct.getString_1());
            com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct myBackRefClientService_getClientCreationType_ResponseStruct =
                new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            myBackRefClientService_getClientCreationType_ResponseStruct.setResult(result);
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientCreationType_getClientCreationTypeResponse_QNAME);
            bodyBlock.setValue(myBackRefClientService_getClientCreationType_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBackRefClientService_getClientCreationType_ResponseStruct_SOAPSerializer);
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
        if (bodyReader.getName().equals(ns1___forGenerateClientDocument___forGenerateClientDocument_QNAME)) {
            state.getRequest().setOperationCode(__forGenerateClientDocument_OPCODE);
        }
        else if (bodyReader.getName().equals(ns1_getClientById_getClientById_QNAME)) {
            state.getRequest().setOperationCode(getClientById_OPCODE);
        }
        else if (bodyReader.getName().equals(ns1_getClientDepartmentCode_getClientDepartmentCode_QNAME)) {
            state.getRequest().setOperationCode(getClientDepartmentCode_OPCODE);
        }
        else if (bodyReader.getName().equals(ns1_getClientByFIOAndDoc_getClientByFIOAndDoc_QNAME)) {
            state.getRequest().setOperationCode(getClientByFIOAndDoc_OPCODE);
        }
        else if (bodyReader.getName().equals(ns1_getClientCreationType_getClientCreationType_QNAME)) {
            state.getRequest().setOperationCode(getClientCreationType_OPCODE);
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
            case __forGenerateClientDocument_OPCODE:
                deserialize___forGenerateClientDocument(bodyReader, deserializationContext, state);
                break;
            case getClientById_OPCODE:
                deserialize_getClientById(bodyReader, deserializationContext, state);
                break;
            case getClientDepartmentCode_OPCODE:
                deserialize_getClientDepartmentCode(bodyReader, deserializationContext, state);
                break;
            case getClientByFIOAndDoc_OPCODE:
                deserialize_getClientByFIOAndDoc(bodyReader, deserializationContext, state);
                break;
            case getClientCreationType_OPCODE:
                deserialize_getClientCreationType(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SOAPProtocolViolationException("soap.operation.unrecognized", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the __forGenerateClientDocument operation.
     */
    private void deserialize___forGenerateClientDocument(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBackRefClientService___forGenerateClientDocument_RequestStructObj =
            ns1_myBackRefClientService___forGenerateClientDocument_RequestStruct_SOAPSerializer.deserialize(ns1___forGenerateClientDocument___forGenerateClientDocument_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1___forGenerateClientDocument___forGenerateClientDocument_QNAME);
        bodyBlock.setValue(myBackRefClientService___forGenerateClientDocument_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getClientById operation.
     */
    private void deserialize_getClientById(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBackRefClientService_getClientById_RequestStructObj =
            ns1_myBackRefClientService_getClientById_RequestStruct_SOAPSerializer.deserialize(ns1_getClientById_getClientById_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientById_getClientById_QNAME);
        bodyBlock.setValue(myBackRefClientService_getClientById_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getClientDepartmentCode operation.
     */
    private void deserialize_getClientDepartmentCode(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBackRefClientService_getClientDepartmentCode_RequestStructObj =
            ns1_myBackRefClientService_getClientDepartmentCode_RequestStruct_SOAPSerializer.deserialize(ns1_getClientDepartmentCode_getClientDepartmentCode_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientDepartmentCode_getClientDepartmentCode_QNAME);
        bodyBlock.setValue(myBackRefClientService_getClientDepartmentCode_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getClientByFIOAndDoc operation.
     */
    private void deserialize_getClientByFIOAndDoc(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBackRefClientService_getClientByFIOAndDoc_RequestStructObj =
            ns1_myBackRefClientService_getClientByFIOAndDoc_RequestStruct_SOAPSerializer.deserialize(ns1_getClientByFIOAndDoc_getClientByFIOAndDoc_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientByFIOAndDoc_getClientByFIOAndDoc_QNAME);
        bodyBlock.setValue(myBackRefClientService_getClientByFIOAndDoc_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the getClientCreationType operation.
     */
    private void deserialize_getClientCreationType(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBackRefClientService_getClientCreationType_RequestStructObj =
            ns1_myBackRefClientService_getClientCreationType_RequestStruct_SOAPSerializer.deserialize(ns1_getClientCreationType_getClientCreationType_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getClientCreationType_getClientCreationType_QNAME);
        bodyBlock.setValue(myBackRefClientService_getClientCreationType_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    
    /*
     * This method must invoke the correct method on the servant based on the opcode.
     */
    protected void processingHook(StreamingHandlerState state) throws Exception {
        switch (state.getRequest().getOperationCode()) {
            case __forGenerateClientDocument_OPCODE:
                invoke___forGenerateClientDocument(state);
                break;
            case getClientById_OPCODE:
                invoke_getClientById(state);
                break;
            case getClientDepartmentCode_OPCODE:
                invoke_getClientDepartmentCode(state);
                break;
            case getClientByFIOAndDoc_OPCODE:
                invoke_getClientByFIOAndDoc(state);
                break;
            case getClientCreationType_OPCODE:
                invoke_getClientCreationType(state);
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
        if (name.equals(ns1___forGenerateClientDocument___forGenerateClientDocument_QNAME)) {
            return __forGenerateClientDocument_OPCODE;
        }
        if (name.equals(ns1_getClientById_getClientById_QNAME)) {
            return getClientById_OPCODE;
        }
        if (name.equals(ns1_getClientDepartmentCode_getClientDepartmentCode_QNAME)) {
            return getClientDepartmentCode_OPCODE;
        }
        if (name.equals(ns1_getClientByFIOAndDoc_getClientByFIOAndDoc_QNAME)) {
            return getClientByFIOAndDoc_OPCODE;
        }
        if (name.equals(ns1_getClientCreationType_getClientCreationType_QNAME)) {
            return getClientCreationType_OPCODE;
        }
        return super.getOpcodeForFirstBodyElementName(name);
    }
    
    
    private Method internalGetMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {
        
        Method theMethod = null;
        
        switch(opcode) {
            case __forGenerateClientDocument_OPCODE:
                {
                    Class[] carray = { com.rssl.phizic.wsgate.clients.backrefservice.generated.ClientDocument.class };
                    theMethod = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService.class).getMethod("__forGenerateClientDocument", carray);
                }
                break;
            
            case getClientById_OPCODE:
                {
                    Class[] carray = { java.lang.Long.class };
                    theMethod = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService.class).getMethod("getClientById", carray);
                }
                break;
            
            case getClientDepartmentCode_OPCODE:
                {
                    Class[] carray = { java.lang.Long.class };
                    theMethod = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService.class).getMethod("getClientDepartmentCode", carray);
                }
                break;
            
            case getClientByFIOAndDoc_OPCODE:
                {
                    Class[] carray = { java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.util.Calendar.class,java.lang.String.class };
                    theMethod = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService.class).getMethod("getClientByFIOAndDoc", carray);
                }
                break;
            
            case getClientCreationType_OPCODE:
                {
                    Class[] carray = { java.lang.String.class };
                    theMethod = (com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService.class).getMethod("getClientCreationType", carray);
                }
                break;
            
            default:
        }
        return theMethod;
    }
    
    private Method[] methodMap = new Method[5];
    
    /*
     * This method returns the Method Obj for a specified opcode.
     */
    public Method getMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {
         
        if (opcode <= InternalSOAPMessage.NO_OPERATION ) {
            return null;
        }
         
        if (opcode >= 5 ) {
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
        ns1_myBackRefClientService___forGenerateClientDocument_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct.class, ns1___forGenerateClientDocument_TYPE_QNAME);
        ns1_myBackRefClientService___forGenerateClientDocument_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct.class, ns1___forGenerateClientDocumentResponse_TYPE_QNAME);
        ns1_myBackRefClientService_getClientDepartmentCode_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct.class, ns1_getClientDepartmentCode_TYPE_QNAME);
        ns1_myBackRefClientService_getClientDepartmentCode_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct.class, ns1_getClientDepartmentCodeResponse_TYPE_QNAME);
        ns1_myBackRefClientService_getClientByFIOAndDoc_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct.class, ns1_getClientByFIOAndDoc_TYPE_QNAME);
        ns1_myBackRefClientService_getClientCreationType_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct.class, ns1_getClientCreationTypeResponse_TYPE_QNAME);
        ns1_myBackRefClientService_getClientCreationType_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct.class, ns1_getClientCreationType_TYPE_QNAME);
        ns1_myBackRefClientService_getClientByFIOAndDoc_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct.class, ns1_getClientByFIOAndDocResponse_TYPE_QNAME);
        ns1_myBackRefClientService_getClientById_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct.class, ns1_getClientByIdResponse_TYPE_QNAME);
        ns1_myBackRefClientService_getClientById_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct.class, ns1_getClientById_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName portName = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "BackRefClientServicePort");
    private static final int __forGenerateClientDocument_OPCODE = 0;
    private static final int getClientById_OPCODE = 1;
    private static final int getClientDepartmentCode_OPCODE = 2;
    private static final int getClientByFIOAndDoc_OPCODE = 3;
    private static final int getClientCreationType_OPCODE = 4;
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
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.backrefservice.clients.wsgate.phizic.rssl.com",
                                            "ns1", "http://java.sun.com/jax-rpc-ri/internal"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}
