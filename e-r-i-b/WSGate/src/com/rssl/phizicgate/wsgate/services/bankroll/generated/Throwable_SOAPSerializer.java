// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Throwable_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_backtrace_QNAME = new QName("", "backtrace");
    private static final javax.xml.namespace.QName ns3_anyType_TYPE_QNAME = SchemaConstants.QNAME_TYPE_URTYPE;
    private CombinedSerializer ns3_myns3_anyType__DynamicSerializer;
    private static final javax.xml.namespace.QName ns1_cause_QNAME = new QName("", "cause");
    private static final javax.xml.namespace.QName ns2_Throwable_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "Throwable");
    private CombinedSerializer ns2_myThrowable_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_detailMessage_QNAME = new QName("", "detailMessage");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_stackTrace_QNAME = new QName("", "stackTrace");
    private static final javax.xml.namespace.QName ns2_ArrayOfStackTraceElement_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "ArrayOfStackTraceElement");
    private CombinedSerializer ns2_myns2_ArrayOfStackTraceElement__StackTraceElementArray_SOAPSerializer1;
    private static final int myBACKTRACE_INDEX = 0;
    private static final int myCAUSE_INDEX = 1;
    private static final int myDETAILMESSAGE_INDEX = 2;
    private static final int mySTACKTRACE_INDEX = 3;
    
    public Throwable_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_anyType__DynamicSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Object.class, ns3_anyType_TYPE_QNAME);
        ns2_myThrowable_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable.class, ns2_Throwable_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myns2_ArrayOfStackTraceElement__StackTraceElementArray_SOAPSerializer1 = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.bankroll.generated.StackTraceElement[].class, ns2_ArrayOfStackTraceElement_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable instance = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable();
        com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_backtrace_QNAME)) {
                member = ns3_myns3_anyType__DynamicSerializer.deserialize(ns1_backtrace_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBACKTRACE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBacktrace((java.lang.Object)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_cause_QNAME)) {
                member = ns2_myThrowable_SOAPSerializer.deserialize(ns1_cause_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCAUSE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCause((com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_detailMessage_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_detailMessage_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDETAILMESSAGE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDetailMessage((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_stackTrace_QNAME)) {
                member = ns2_myns2_ArrayOfStackTraceElement__StackTraceElementArray_SOAPSerializer1.deserialize(ns1_stackTrace_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTACKTRACE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setStackTrace((com.rssl.phizicgate.wsgate.services.bankroll.generated.StackTraceElement[])member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.Throwable)obj;
        
        ns3_myns3_anyType__DynamicSerializer.serialize(instance.getBacktrace(), ns1_backtrace_QNAME, null, writer, context);
        ns2_myThrowable_SOAPSerializer.serialize(instance.getCause(), ns1_cause_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDetailMessage(), ns1_detailMessage_QNAME, null, writer, context);
        ns2_myns2_ArrayOfStackTraceElement__StackTraceElementArray_SOAPSerializer1.serialize(instance.getStackTrace(), ns1_stackTrace_QNAME, null, writer, context);
    }
}
