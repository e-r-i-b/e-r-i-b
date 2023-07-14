// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class UESIMessage_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_messageId_QNAME = new QName("", "messageId");
    private static final javax.xml.namespace.QName ns5_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns5_myns5__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_messageText_QNAME = new QName("", "messageText");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_messageTime_QNAME = new QName("", "messageTime");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_messageType_QNAME = new QName("", "messageType");
    private static final int myMESSAGEID_INDEX = 0;
    private static final int myMESSAGETEXT_INDEX = 1;
    private static final int myMESSAGETIME_INDEX = 2;
    private static final int myMESSAGETYPE_INDEX = 3;
    
    public UESIMessage_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns5_myns5__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns5_long_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage instance = new com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage();
        com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_messageId_QNAME)) {
                member = ns5_myns5__long__java_lang_Long_Long_Serializer.deserialize(ns1_messageId_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMESSAGEID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMessageId((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_messageText_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_messageText_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMESSAGETEXT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMessageText((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_messageTime_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_messageTime_QNAME, reader, context);
                instance.setMessageTime((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_messageType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_messageType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMESSAGETYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMessageType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage)obj;
        
        ns5_myns5__long__java_lang_Long_Long_Serializer.serialize(instance.getMessageId(), ns1_messageId_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMessageText(), ns1_messageText_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getMessageTime(), ns1_messageTime_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMessageType(), ns1_messageType_QNAME, null, writer, context);
    }
}
