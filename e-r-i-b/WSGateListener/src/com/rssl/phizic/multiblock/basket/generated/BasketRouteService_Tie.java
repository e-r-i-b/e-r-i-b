// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.multiblock.basket.generated;

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

public class BasketRouteService_Tie
    extends com.sun.xml.rpc.server.TieBase implements SerializerConstants {
    
    
    
    public BasketRouteService_Tie() throws Exception {
        super(new com.rssl.phizic.multiblock.basket.generated.BasketRouteServiceImpl_SerializerRegistry().getRegistry());
        initialize(internalTypeMappingRegistry);
    }
    
    /*
     * This method does the actual method invocation for operation: addBillBasketInfo
     */
    private void invoke_addBillBasketInfo(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.multiblock.basket.generated.BasketRouteService_addBillBasketInfo_RequestStruct myBasketRouteService_addBillBasketInfo_RequestStruct = null;
        Object myBasketRouteService_addBillBasketInfo_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBasketRouteService_addBillBasketInfo_RequestStructObj instanceof SOAPDeserializationState) {
            myBasketRouteService_addBillBasketInfo_RequestStruct = (com.rssl.phizic.multiblock.basket.generated.BasketRouteService_addBillBasketInfo_RequestStruct)((SOAPDeserializationState)myBasketRouteService_addBillBasketInfo_RequestStructObj).getInstance();
        } else {
            myBasketRouteService_addBillBasketInfo_RequestStruct = (com.rssl.phizic.multiblock.basket.generated.BasketRouteService_addBillBasketInfo_RequestStruct)myBasketRouteService_addBillBasketInfo_RequestStructObj;
        }
        
        try {
            ((com.rssl.phizic.multiblock.basket.generated.BasketRouteService) getTarget()).addBillBasketInfo(myBasketRouteService_addBillBasketInfo_RequestStruct.getString_1(), myBasketRouteService_addBillBasketInfo_RequestStruct.getString_2());
            com.rssl.phizic.multiblock.basket.generated.BasketRouteService_addBillBasketInfo_ResponseStruct myBasketRouteService_addBillBasketInfo_ResponseStruct =
                new com.rssl.phizic.multiblock.basket.generated.BasketRouteService_addBillBasketInfo_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_addBillBasketInfo_addBillBasketInfoResponse_QNAME);
            bodyBlock.setValue(myBasketRouteService_addBillBasketInfo_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBasketRouteService_addBillBasketInfo_ResponseStruct_SOAPSerializer);
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
     * This method does the actual method invocation for operation: acceptBillBasketExecute
     */
    private void invoke_acceptBillBasketExecute(StreamingHandlerState state) throws Exception {
        
        com.rssl.phizic.multiblock.basket.generated.BasketRouteService_acceptBillBasketExecute_RequestStruct myBasketRouteService_acceptBillBasketExecute_RequestStruct = null;
        Object myBasketRouteService_acceptBillBasketExecute_RequestStructObj =
            state.getRequest().getBody().getValue();
        
        if (myBasketRouteService_acceptBillBasketExecute_RequestStructObj instanceof SOAPDeserializationState) {
            myBasketRouteService_acceptBillBasketExecute_RequestStruct = (com.rssl.phizic.multiblock.basket.generated.BasketRouteService_acceptBillBasketExecute_RequestStruct)((SOAPDeserializationState)myBasketRouteService_acceptBillBasketExecute_RequestStructObj).getInstance();
        } else {
            myBasketRouteService_acceptBillBasketExecute_RequestStruct = (com.rssl.phizic.multiblock.basket.generated.BasketRouteService_acceptBillBasketExecute_RequestStruct)myBasketRouteService_acceptBillBasketExecute_RequestStructObj;
        }
        
        try {
            ((com.rssl.phizic.multiblock.basket.generated.BasketRouteService) getTarget()).acceptBillBasketExecute(myBasketRouteService_acceptBillBasketExecute_RequestStruct.getString_1());
            com.rssl.phizic.multiblock.basket.generated.BasketRouteService_acceptBillBasketExecute_ResponseStruct myBasketRouteService_acceptBillBasketExecute_ResponseStruct =
                new com.rssl.phizic.multiblock.basket.generated.BasketRouteService_acceptBillBasketExecute_ResponseStruct();
            SOAPHeaderBlockInfo headerInfo;
            
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_acceptBillBasketExecute_acceptBillBasketExecuteResponse_QNAME);
            bodyBlock.setValue(myBasketRouteService_acceptBillBasketExecute_ResponseStruct);
            bodyBlock.setSerializer(ns1_myBasketRouteService_acceptBillBasketExecute_ResponseStruct_SOAPSerializer);
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
        if (bodyReader.getName().equals(ns1_addBillBasketInfo_addBillBasketInfo_QNAME)) {
            state.getRequest().setOperationCode(addBillBasketInfo_OPCODE);
        }
        else if (bodyReader.getName().equals(ns1_acceptBillBasketExecute_acceptBillBasketExecute_QNAME)) {
            state.getRequest().setOperationCode(acceptBillBasketExecute_OPCODE);
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
            case addBillBasketInfo_OPCODE:
                deserialize_addBillBasketInfo(bodyReader, deserializationContext, state);
                break;
            case acceptBillBasketExecute_OPCODE:
                deserialize_acceptBillBasketExecute(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SOAPProtocolViolationException("soap.operation.unrecognized", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the addBillBasketInfo operation.
     */
    private void deserialize_addBillBasketInfo(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBasketRouteService_addBillBasketInfo_RequestStructObj =
            ns1_myBasketRouteService_addBillBasketInfo_RequestStruct_SOAPSerializer.deserialize(ns1_addBillBasketInfo_addBillBasketInfo_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_addBillBasketInfo_addBillBasketInfo_QNAME);
        bodyBlock.setValue(myBasketRouteService_addBillBasketInfo_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    /*
     * This method deserializes the body of the acceptBillBasketExecute operation.
     */
    private void deserialize_acceptBillBasketExecute(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myBasketRouteService_acceptBillBasketExecute_RequestStructObj =
            ns1_myBasketRouteService_acceptBillBasketExecute_RequestStruct_SOAPSerializer.deserialize(ns1_acceptBillBasketExecute_acceptBillBasketExecute_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_acceptBillBasketExecute_acceptBillBasketExecute_QNAME);
        bodyBlock.setValue(myBasketRouteService_acceptBillBasketExecute_RequestStructObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    
    /*
     * This method must invoke the correct method on the servant based on the opcode.
     */
    protected void processingHook(StreamingHandlerState state) throws Exception {
        switch (state.getRequest().getOperationCode()) {
            case addBillBasketInfo_OPCODE:
                invoke_addBillBasketInfo(state);
                break;
            case acceptBillBasketExecute_OPCODE:
                invoke_acceptBillBasketExecute(state);
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
        if (name.equals(ns1_addBillBasketInfo_addBillBasketInfo_QNAME)) {
            return addBillBasketInfo_OPCODE;
        }
        if (name.equals(ns1_acceptBillBasketExecute_acceptBillBasketExecute_QNAME)) {
            return acceptBillBasketExecute_OPCODE;
        }
        return super.getOpcodeForFirstBodyElementName(name);
    }
    
    
    private Method internalGetMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {
        
        Method theMethod = null;
        
        switch(opcode) {
            case addBillBasketInfo_OPCODE:
                {
                    Class[] carray = { java.lang.String.class,java.lang.String.class };
                    theMethod = (com.rssl.phizic.multiblock.basket.generated.BasketRouteService.class).getMethod("addBillBasketInfo", carray);
                }
                break;
            
            case acceptBillBasketExecute_OPCODE:
                {
                    Class[] carray = { java.lang.String.class };
                    theMethod = (com.rssl.phizic.multiblock.basket.generated.BasketRouteService.class).getMethod("acceptBillBasketExecute", carray);
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
        ns1_myBasketRouteService_addBillBasketInfo_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.multiblock.basket.generated.BasketRouteService_addBillBasketInfo_RequestStruct.class, ns1_addBillBasketInfo_TYPE_QNAME);
        ns1_myBasketRouteService_acceptBillBasketExecute_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.multiblock.basket.generated.BasketRouteService_acceptBillBasketExecute_ResponseStruct.class, ns1_acceptBillBasketExecuteResponse_TYPE_QNAME);
        ns1_myBasketRouteService_addBillBasketInfo_ResponseStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.multiblock.basket.generated.BasketRouteService_addBillBasketInfo_ResponseStruct.class, ns1_addBillBasketInfoResponse_TYPE_QNAME);
        ns1_myBasketRouteService_acceptBillBasketExecute_RequestStruct_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.multiblock.basket.generated.BasketRouteService_acceptBillBasketExecute_RequestStruct.class, ns1_acceptBillBasketExecute_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName portName = new QName("http://generated.basket.multiblock.phizic.rssl.com", "BasketRouteServicePort");
    private static final int addBillBasketInfo_OPCODE = 0;
    private static final int acceptBillBasketExecute_OPCODE = 1;
    private static final javax.xml.namespace.QName ns1_addBillBasketInfo_addBillBasketInfo_QNAME = new QName("http://generated.basket.multiblock.phizic.rssl.com", "addBillBasketInfo");
    private static final javax.xml.namespace.QName ns1_addBillBasketInfo_TYPE_QNAME = new QName("http://generated.basket.multiblock.phizic.rssl.com", "addBillBasketInfo");
    private CombinedSerializer ns1_myBasketRouteService_addBillBasketInfo_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_addBillBasketInfo_addBillBasketInfoResponse_QNAME = new QName("http://generated.basket.multiblock.phizic.rssl.com", "addBillBasketInfoResponse");
    private static final javax.xml.namespace.QName ns1_addBillBasketInfoResponse_TYPE_QNAME = new QName("http://generated.basket.multiblock.phizic.rssl.com", "addBillBasketInfoResponse");
    private CombinedSerializer ns1_myBasketRouteService_addBillBasketInfo_ResponseStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_acceptBillBasketExecute_acceptBillBasketExecute_QNAME = new QName("http://generated.basket.multiblock.phizic.rssl.com", "acceptBillBasketExecute");
    private static final javax.xml.namespace.QName ns1_acceptBillBasketExecute_TYPE_QNAME = new QName("http://generated.basket.multiblock.phizic.rssl.com", "acceptBillBasketExecute");
    private CombinedSerializer ns1_myBasketRouteService_acceptBillBasketExecute_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_acceptBillBasketExecute_acceptBillBasketExecuteResponse_QNAME = new QName("http://generated.basket.multiblock.phizic.rssl.com", "acceptBillBasketExecuteResponse");
    private static final javax.xml.namespace.QName ns1_acceptBillBasketExecuteResponse_TYPE_QNAME = new QName("http://generated.basket.multiblock.phizic.rssl.com", "acceptBillBasketExecuteResponse");
    private CombinedSerializer ns1_myBasketRouteService_acceptBillBasketExecute_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            "ns0", "http://generated.basket.multiblock.phizic.rssl.com"
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}
