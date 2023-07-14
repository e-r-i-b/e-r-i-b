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

public class UserInfoCSA_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_birthdate_QNAME = new QName("", "birthdate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_cbCode_QNAME = new QName("", "cbCode");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_firstname_QNAME = new QName("", "firstname");
    private static final javax.xml.namespace.QName ns1_passport_QNAME = new QName("", "passport");
    private static final javax.xml.namespace.QName ns1_patrname_QNAME = new QName("", "patrname");
    private static final javax.xml.namespace.QName ns1_surname_QNAME = new QName("", "surname");
    private static final javax.xml.namespace.QName ns1_tb_QNAME = new QName("", "tb");
    private static final int myBIRTHDATE_INDEX = 0;
    private static final int myCBCODE_INDEX = 1;
    private static final int myFIRSTNAME_INDEX = 2;
    private static final int myPASSPORT_INDEX = 3;
    private static final int myPATRNAME_INDEX = 4;
    private static final int mySURNAME_INDEX = 5;
    private static final int myTB_INDEX = 6;
    
    public UserInfoCSA_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA instance = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA();
        com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_birthdate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_birthdate_QNAME, reader, context);
                instance.setBirthdate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_cbCode_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_cbCode_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCBCODE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCbCode((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_firstname_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_firstname_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFIRSTNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFirstname((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_passport_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_passport_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPASSPORT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPassport((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_patrname_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_patrname_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPATRNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPatrname((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_surname_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_surname_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySURNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSurname((java.lang.String)member);
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
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA_SOAPBuilder();
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
        com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA)obj;
        
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getBirthdate(), ns1_birthdate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCbCode(), ns1_cbCode_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getFirstname(), ns1_firstname_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPassport(), ns1_passport_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPatrname(), ns1_patrname_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSurname(), ns1_surname_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getTb(), ns1_tb_QNAME, null, writer, context);
    }
}
