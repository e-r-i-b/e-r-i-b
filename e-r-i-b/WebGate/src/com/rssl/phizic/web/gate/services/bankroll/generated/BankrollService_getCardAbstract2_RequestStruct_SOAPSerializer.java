// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class BankrollService_getCardAbstract2_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_Card_1_QNAME = new QName("", "Card_1");
    private static final javax.xml.namespace.QName ns2_Card_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "Card");
    private CombinedSerializer ns2_myCard_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_Calendar_2_QNAME = new QName("", "Calendar_2");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_Calendar_3_QNAME = new QName("", "Calendar_3");
    private static final javax.xml.namespace.QName ns1_Boolean_4_QNAME = new QName("", "Boolean_4");
    private static final javax.xml.namespace.QName ns4_boolean_TYPE_QNAME = SOAPConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer;
    private static final int myCARD_1_INDEX = 0;
    private static final int myCALENDAR_2_INDEX = 1;
    private static final int myCALENDAR_3_INDEX = 2;
    private static final int myBOOLEAN_4_INDEX = 3;
    
    public BankrollService_getCardAbstract2_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myCard_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.bankroll.generated.Card.class, ns2_Card_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Boolean.class, ns4_boolean_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct instance = new com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct();
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        for (int i=0; i<4; i++) {
            elementName = reader.getName();
            if (reader.getState() == XMLReader.END) {
                break;
            }
            if (elementName.equals(ns1_Card_1_QNAME)) {
                member = ns2_myCard_SOAPSerializer.deserialize(ns1_Card_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCARD_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCard_1((com.rssl.phizic.web.gate.services.bankroll.generated.Card)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_Calendar_2_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_Calendar_2_QNAME, reader, context);
                instance.setCalendar_2((java.util.Calendar)member);
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_Calendar_3_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_Calendar_3_QNAME, reader, context);
                instance.setCalendar_3((java.util.Calendar)member);
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_Boolean_4_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_Boolean_4_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBOOLEAN_4_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBoolean_4((java.lang.Boolean)member);
                }
                reader.nextElementContent();
                continue;
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_Boolean_4_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct instance = (com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct instance = (com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardAbstract2_RequestStruct)obj;
        
        ns2_myCard_SOAPSerializer.serialize(instance.getCard_1(), ns1_Card_1_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getCalendar_2(), ns1_Calendar_2_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getCalendar_3(), ns1_Calendar_3_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getBoolean_4(), ns1_Boolean_4_QNAME, null, writer, context);
    }
}
