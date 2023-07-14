// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.monitoring.services.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class MonitoringServiceGateStateConfigImpl_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_available_QNAME = new QName("", "available");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_availableChangeInactiveType_QNAME = new QName("", "availableChangeInactiveType");
    private static final javax.xml.namespace.QName ns1_deteriorationTime_QNAME = new QName("", "deteriorationTime");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_inactiveType_QNAME = new QName("", "inactiveType");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_messageText_QNAME = new QName("", "messageText");
    private static final javax.xml.namespace.QName ns1_monitoringCount_QNAME = new QName("", "monitoringCount");
    private static final javax.xml.namespace.QName ns3_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer ns3_myns3__int__int_Int_Serializer;
    private static final javax.xml.namespace.QName ns1_monitoringErrorPercent_QNAME = new QName("", "monitoringErrorPercent");
    private static final javax.xml.namespace.QName ns1_monitoringTime_QNAME = new QName("", "monitoringTime");
    private static final javax.xml.namespace.QName ns3_long_TYPE_QNAME = SchemaConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns3_myns3__long__long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_recoveryTime_QNAME = new QName("", "recoveryTime");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_resources_QNAME = new QName("", "resources");
    private static final javax.xml.namespace.QName ns5_map_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_MAP;
    private CombinedSerializer ns5_myns5_map__MapInterfaceSerializer;
    private static final javax.xml.namespace.QName ns1_timeout_QNAME = new QName("", "timeout");
    private static final javax.xml.namespace.QName ns1_useMonitoring_QNAME = new QName("", "useMonitoring");
    private static final int myAVAILABLE_INDEX = 0;
    private static final int myAVAILABLECHANGEINACTIVETYPE_INDEX = 1;
    private static final int myDETERIORATIONTIME_INDEX = 2;
    private static final int myINACTIVETYPE_INDEX = 3;
    private static final int myMESSAGETEXT_INDEX = 4;
    private static final int myMONITORINGCOUNT_INDEX = 5;
    private static final int myMONITORINGERRORPERCENT_INDEX = 6;
    private static final int myMONITORINGTIME_INDEX = 7;
    private static final int myRECOVERYTIME_INDEX = 8;
    private static final int myRESOURCES_INDEX = 9;
    private static final int myTIMEOUT_INDEX = 10;
    private static final int myUSEMONITORING_INDEX = 11;
    
    public MonitoringServiceGateStateConfigImpl_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, int.class, ns3_int_TYPE_QNAME);
        ns3_myns3__long__long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, long.class, ns3_long_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
        ns5_myns5_map__MapInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Map.class, ns5_map_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl instance = new com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl();
        com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_available_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_available_QNAME, reader, context);
                instance.setAvailable(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_availableChangeInactiveType_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_availableChangeInactiveType_QNAME, reader, context);
                instance.setAvailableChangeInactiveType(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_deteriorationTime_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_deteriorationTime_QNAME, reader, context);
                instance.setDeteriorationTime((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_inactiveType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_inactiveType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myINACTIVETYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setInactiveType((java.lang.String)member);
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
                        builder = new com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl_SOAPBuilder();
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
            if (elementName.equals(ns1_monitoringCount_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_monitoringCount_QNAME, reader, context);
                instance.setMonitoringCount(((java.lang.Integer)member).intValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_monitoringErrorPercent_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_monitoringErrorPercent_QNAME, reader, context);
                instance.setMonitoringErrorPercent(((java.lang.Integer)member).intValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_monitoringTime_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_monitoringTime_QNAME, reader, context);
                instance.setMonitoringTime(((Long)member).longValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_recoveryTime_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_recoveryTime_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myRECOVERYTIME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRecoveryTime((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_resources_QNAME)) {
                member = ns5_myns5_map__MapInterfaceSerializer.deserialize(ns1_resources_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myRESOURCES_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setResources((java.util.Map)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_timeout_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_timeout_QNAME, reader, context);
                instance.setTimeout(((Long)member).longValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_useMonitoring_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_useMonitoring_QNAME, reader, context);
                instance.setUseMonitoring(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl instance = (com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl instance = (com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateStateConfigImpl)obj;
        
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isAvailable()), ns1_available_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isAvailableChangeInactiveType()), ns1_availableChangeInactiveType_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getDeteriorationTime(), ns1_deteriorationTime_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getInactiveType(), ns1_inactiveType_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMessageText(), ns1_messageText_QNAME, null, writer, context);
        ns3_myns3__int__int_Int_Serializer.serialize(new java.lang.Integer(instance.getMonitoringCount()), ns1_monitoringCount_QNAME, null, writer, context);
        ns3_myns3__int__int_Int_Serializer.serialize(new java.lang.Integer(instance.getMonitoringErrorPercent()), ns1_monitoringErrorPercent_QNAME, null, writer, context);
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getMonitoringTime()), ns1_monitoringTime_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getRecoveryTime(), ns1_recoveryTime_QNAME, null, writer, context);
        ns5_myns5_map__MapInterfaceSerializer.serialize(instance.getResources(), ns1_resources_QNAME, null, writer, context);
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getTimeout()), ns1_timeout_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isUseMonitoring()), ns1_useMonitoring_QNAME, null, writer, context);
    }
}
