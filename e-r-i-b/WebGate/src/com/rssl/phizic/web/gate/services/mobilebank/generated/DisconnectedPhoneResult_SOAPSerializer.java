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

public class DisconnectedPhoneResult_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_createdAt_QNAME = new QName("", "createdAt");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_disconnectedPhoneSource_QNAME = new QName("", "disconnectedPhoneSource");
    private static final javax.xml.namespace.QName ns3_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer ns3_myns3__int__int_Int_Serializer;
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns1_phoneNumber_QNAME = new QName("", "phoneNumber");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_processedTime_QNAME = new QName("", "processedTime");
    private static final javax.xml.namespace.QName ns1_reason_QNAME = new QName("", "reason");
    private static final javax.xml.namespace.QName ns1_receivedAt_QNAME = new QName("", "receivedAt");
    private static final javax.xml.namespace.QName ns1_state_QNAME = new QName("", "state");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final int myCREATEDAT_INDEX = 0;
    private static final int myDISCONNECTEDPHONESOURCE_INDEX = 1;
    private static final int myID_INDEX = 2;
    private static final int myPHONENUMBER_INDEX = 3;
    private static final int myPROCESSEDTIME_INDEX = 4;
    private static final int myREASON_INDEX = 5;
    private static final int myRECEIVEDAT_INDEX = 6;
    private static final int mySTATE_INDEX = 7;
    
    public DisconnectedPhoneResult_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns3_myns3__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, int.class, ns3_int_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult instance = new com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult();
        com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_createdAt_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_createdAt_QNAME, reader, context);
                instance.setCreatedAt((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_disconnectedPhoneSource_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_disconnectedPhoneSource_QNAME, reader, context);
                instance.setDisconnectedPhoneSource(((java.lang.Integer)member).intValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_id_QNAME, reader, context);
                instance.setId(((java.lang.Integer)member).intValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_phoneNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_phoneNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPHONENUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPhoneNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_processedTime_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_processedTime_QNAME, reader, context);
                instance.setProcessedTime((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_reason_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_reason_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREASON_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setReason((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_receivedAt_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_receivedAt_QNAME, reader, context);
                instance.setReceivedAt((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_state_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_state_QNAME, reader, context);
                instance.setState(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult)obj;
        
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getCreatedAt(), ns1_createdAt_QNAME, null, writer, context);
        ns3_myns3__int__int_Int_Serializer.serialize(new java.lang.Integer(instance.getDisconnectedPhoneSource()), ns1_disconnectedPhoneSource_QNAME, null, writer, context);
        ns3_myns3__int__int_Int_Serializer.serialize(new java.lang.Integer(instance.getId()), ns1_id_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPhoneNumber(), ns1_phoneNumber_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getProcessedTime(), ns1_processedTime_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getReason(), ns1_reason_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getReceivedAt(), ns1_receivedAt_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isState()), ns1_state_QNAME, null, writer, context);
    }
}
