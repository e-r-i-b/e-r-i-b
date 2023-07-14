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

public class ERMBPhone_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_lastModified_QNAME = new QName("", "lastModified");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_lastUpload_QNAME = new QName("", "lastUpload");
    private static final javax.xml.namespace.QName ns1_phoneNumber_QNAME = new QName("", "phoneNumber");
    private static final javax.xml.namespace.QName ns2_PhoneNumber_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "PhoneNumber");
    private CombinedSerializer ns2_myPhoneNumber_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_phoneUsage_QNAME = new QName("", "phoneUsage");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final int myLASTMODIFIED_INDEX = 0;
    private static final int myLASTUPLOAD_INDEX = 1;
    private static final int myPHONENUMBER_INDEX = 2;
    private static final int myPHONEUSAGE_INDEX = 3;
    
    public ERMBPhone_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns2_myPhoneNumber_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber.class, ns2_PhoneNumber_TYPE_QNAME);
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone instance = new com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone();
        com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_lastModified_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_lastModified_QNAME, reader, context);
                instance.setLastModified((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_lastUpload_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_lastUpload_QNAME, reader, context);
                instance.setLastUpload((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_phoneNumber_QNAME)) {
                member = ns2_myPhoneNumber_SOAPSerializer.deserialize(ns1_phoneNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPHONENUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPhoneNumber((com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_phoneUsage_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_phoneUsage_QNAME, reader, context);
                instance.setPhoneUsage(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone)obj;
        
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getLastModified(), ns1_lastModified_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getLastUpload(), ns1_lastUpload_QNAME, null, writer, context);
        ns2_myPhoneNumber_SOAPSerializer.serialize(instance.getPhoneNumber(), ns1_phoneNumber_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isPhoneUsage()), ns1_phoneUsage_QNAME, null, writer, context);
    }
}
