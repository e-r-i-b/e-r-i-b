// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.calendar.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class CalendarGateService_isHoliday_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_Calendar_1_QNAME = new QName("", "Calendar_1");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_GateDocument_2_QNAME = new QName("", "GateDocument_2");
    private static final javax.xml.namespace.QName ns2_GateDocument_TYPE_QNAME = new QName("http://generated.calendar.util.services.gate.web.phizic.rssl.com", "GateDocument");
    private CombinedSerializer ns2_myGateDocument_SOAPSerializer;
    private static final int myCALENDAR_1_INDEX = 0;
    private static final int myGATEDOCUMENT_2_INDEX = 1;
    
    public CalendarGateService_isHoliday_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns2_myGateDocument_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.utils.calendar.generated.GateDocument.class, ns2_GateDocument_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_isHoliday_RequestStruct instance = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_isHoliday_RequestStruct();
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_isHoliday_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        for (int i=0; i<2; i++) {
            elementName = reader.getName();
            if (reader.getState() == XMLReader.END) {
                break;
            }
            if (elementName.equals(ns1_Calendar_1_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_Calendar_1_QNAME, reader, context);
                instance.setCalendar_1((java.util.Calendar)member);
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_GateDocument_2_QNAME)) {
                member = ns2_myGateDocument_SOAPSerializer.deserialize(ns1_GateDocument_2_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_isHoliday_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myGATEDOCUMENT_2_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setGateDocument_2((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.GateDocument)member);
                }
                reader.nextElementContent();
                continue;
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_GateDocument_2_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_isHoliday_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_isHoliday_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_isHoliday_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CalendarGateService_isHoliday_RequestStruct)obj;
        
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getCalendar_1(), ns1_Calendar_1_QNAME, null, writer, context);
        ns2_myGateDocument_SOAPSerializer.serialize(instance.getGateDocument_2(), ns1_GateDocument_2_QNAME, null, writer, context);
    }
}
