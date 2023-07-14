// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class ExternalExceptionInfo_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_application_QNAME = new QName("", "application");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_detail_QNAME = new QName("", "detail");
    private static final javax.xml.namespace.QName ns1_errorCode_QNAME = new QName("", "errorCode");
    private static final javax.xml.namespace.QName ns1_errorText_QNAME = new QName("", "errorText");
    private static final javax.xml.namespace.QName ns1_gate_QNAME = new QName("", "gate");
    private static final javax.xml.namespace.QName ns1_messageKey_QNAME = new QName("", "messageKey");
    private static final javax.xml.namespace.QName ns1_system_QNAME = new QName("", "system");
    private static final javax.xml.namespace.QName ns1_tb_QNAME = new QName("", "tb");
    private static final int myAPPLICATION_INDEX = 0;
    private static final int myDETAIL_INDEX = 1;
    private static final int myERRORCODE_INDEX = 2;
    private static final int myERRORTEXT_INDEX = 3;
    private static final int myGATE_INDEX = 4;
    private static final int myMESSAGEKEY_INDEX = 5;
    private static final int mySYSTEM_INDEX = 6;
    private static final int myTB_INDEX = 7;
    
    public ExternalExceptionInfo_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo instance = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo();
        com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_application_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_application_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAPPLICATION_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setApplication((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_detail_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_detail_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDETAIL_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDetail((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_errorCode_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_errorCode_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myERRORCODE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setErrorCode((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_errorText_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_errorText_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myERRORTEXT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setErrorText((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_gate_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_gate_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myGATE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setGate((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_messageKey_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_messageKey_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMESSAGEKEY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMessageKey((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_system_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_system_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySYSTEM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSystem((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_tb_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_tb_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTB_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTb((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo instance = (com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo instance = (com.rssl.phizicgate.wsgateclient.services.statistics.exception.generated.ExternalExceptionInfo)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getApplication(), ns1_application_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDetail(), ns1_detail_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getErrorCode(), ns1_errorCode_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getErrorText(), ns1_errorText_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getGate(), ns1_gate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMessageKey(), ns1_messageKey_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSystem(), ns1_system_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getTb(), ns1_tb_QNAME, null, writer, context);
    }
}