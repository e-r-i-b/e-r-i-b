// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class MBKPhone_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_added_QNAME = new QName("", "added");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns3_long_TYPE_QNAME = SchemaConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns3_myns3__long__long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_lastUpdateTime_QNAME = new QName("", "lastUpdateTime");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_phone_QNAME = new QName("", "phone");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final int myADDED_INDEX = 0;
    private static final int myID_INDEX = 1;
    private static final int myLASTUPDATETIME_INDEX = 2;
    private static final int myPHONE_INDEX = 3;
    
    public MBKPhone_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
        ns3_myns3__long__long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, long.class, ns3_long_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone instance = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone();
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_added_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_added_QNAME, reader, context);
                instance.setAdded(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_id_QNAME, reader, context);
                instance.setId(((Long)member).longValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_lastUpdateTime_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_lastUpdateTime_QNAME, reader, context);
                instance.setLastUpdateTime((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_phone_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_phone_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPHONE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPhone((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone)obj;
        
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isAdded()), ns1_added_QNAME, null, writer, context);
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getId()), ns1_id_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getLastUpdateTime(), ns1_lastUpdateTime_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPhone(), ns1_phone_QNAME, null, writer, context);
    }
}
