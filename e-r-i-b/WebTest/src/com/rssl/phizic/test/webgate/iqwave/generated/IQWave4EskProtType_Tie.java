// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.test.webgate.iqwave.generated;

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

public class IQWave4EskProtType_Tie
    extends com.sun.xml.rpc.server.TieBase implements SerializerConstants {
    
    
    
    public IQWave4EskProtType_Tie() throws Exception {
        super(new com.rssl.phizic.test.webgate.iqwave.generated.IQWave4EskService_SerializerRegistry().getRegistry());
        initialize(internalTypeMappingRegistry);
    }
    
    /*
     * This method does the actual method invocation for operation: getXMLmessage
     */
    private void invoke_getXMLmessage(StreamingHandlerState state) throws Exception {
        
        java.lang.String myString = null;
        Object myStringObj =
            state.getRequest().getBody().getValue();
        
        if (myStringObj instanceof SOAPDeserializationState) {
            myString = (java.lang.String)((SOAPDeserializationState)myStringObj).getInstance();
        } else {
            myString = (java.lang.String)myStringObj;
        }
        
        try {
            java.lang.String response = ((com.rssl.phizic.test.webgate.iqwave.generated.IQWave4EskProtType) getTarget()).getXMLmessage(myString);
            
            SOAPHeaderBlockInfo headerInfo;
            
            
            java.lang.String _response = response;
            SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getXMLmessage_XmlOutputString_QNAME);
            bodyBlock.setValue(_response);
            bodyBlock.setSerializer(ns2_myns2_string__java_lang_String_String_Serializer);
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
        if (bodyReader.getName().equals(ns1_getXMLmessage_XmlInputString_QNAME)) {
            state.getRequest().setOperationCode(getXMLmessage_OPCODE);
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
            case getXMLmessage_OPCODE:
                deserialize_getXMLmessage(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SOAPProtocolViolationException("soap.operation.unrecognized", java.lang.Integer.toString(opcode));
        }
    }
    
    
    
    /*
     * This method deserializes the body of the getXMLmessage operation.
     */
    private void deserialize_getXMLmessage(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
        java.lang.Object myStringObj =
            ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_getXMLmessage_XmlInputString_QNAME,
                bodyReader, deserializationContext);
        
        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns1_getXMLmessage_XmlInputString_QNAME);
        bodyBlock.setValue(myStringObj);
        state.getRequest().setBody(bodyBlock);
    }
    
    
    /*
     * This method must invoke the correct method on the servant based on the opcode.
     */
    protected void processingHook(StreamingHandlerState state) throws Exception {
        switch (state.getRequest().getOperationCode()) {
            case getXMLmessage_OPCODE:
                invoke_getXMLmessage(state);
                break;
            default:
                throw new SOAPProtocolViolationException("soap.operation.unrecognized", java.lang.Integer.toString(state.getRequest().getOperationCode()));
        }
    }
    
    protected java.lang.String getDefaultEnvelopeEncodingStyle() {
        return null;
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
        if (name.equals(ns1_getXMLmessage_XmlInputString_QNAME)) {
            return getXMLmessage_OPCODE;
        }
        return super.getOpcodeForFirstBodyElementName(name);
    }
    
    
    private Method internalGetMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {
        
        Method theMethod = null;
        
        switch(opcode) {
            case getXMLmessage_OPCODE:
                {
                    Class[] carray = { java.lang.String.class };
                    theMethod = (com.rssl.phizic.test.webgate.iqwave.generated.IQWave4EskProtType.class).getMethod("getXMLmessage", carray);
                }
                break;
            
            default:
        }
        return theMethod;
    }
    
    private Method[] methodMap = new Method[1];
    
    /*
     * This method returns the Method Obj for a specified opcode.
     */
    public Method getMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {
         
        if (opcode <= InternalSOAPMessage.NO_OPERATION ) {
            return null;
        }
         
        if (opcode >= 1 ) {
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
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
    }
    
    private static final javax.xml.namespace.QName portName = new QName("http://www.iqex.ru/sbol", "IQWave4Esk");
    private static final int getXMLmessage_OPCODE = 0;
    private static final javax.xml.namespace.QName ns1_getXMLmessage_XmlInputString_QNAME = new QName("http://www.iqex.ru/sbol", "XmlInputString");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_getXMLmessage_XmlOutputString_QNAME = new QName("http://www.iqex.ru/sbol", "XmlOutputString");
    private static final java.lang.String[] myNamespace_declarations =
                                        new java.lang.String[] {
                                            
                                        };
    
    private static final QName[] understoodHeaderNames = new QName[] {  };
}
