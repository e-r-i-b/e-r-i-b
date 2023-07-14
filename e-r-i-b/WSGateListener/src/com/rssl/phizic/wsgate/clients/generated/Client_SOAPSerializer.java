// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.clients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Client_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_INN_QNAME = new QName("", "INN");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_birthDay_QNAME = new QName("", "birthDay");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_birthPlace_QNAME = new QName("", "birthPlace");
    private static final javax.xml.namespace.QName ns1_citizenship_QNAME = new QName("", "citizenship");
    private static final javax.xml.namespace.QName ns1_displayId_QNAME = new QName("", "displayId");
    private static final javax.xml.namespace.QName ns1_documents_QNAME = new QName("", "documents");
    private static final javax.xml.namespace.QName ns5_list_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_LIST;
    private CombinedSerializer ns5_myns5_list__CollectionInterfaceSerializer;
    private static final javax.xml.namespace.QName ns1_email_QNAME = new QName("", "email");
    private static final javax.xml.namespace.QName ns1_firstName_QNAME = new QName("", "firstName");
    private static final javax.xml.namespace.QName ns1_fullName_QNAME = new QName("", "fullName");
    private static final javax.xml.namespace.QName ns1_homePhone_QNAME = new QName("", "homePhone");
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns1_isResident_QNAME = new QName("", "isResident");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_jobPhone_QNAME = new QName("", "jobPhone");
    private static final javax.xml.namespace.QName ns1_legalAddress_QNAME = new QName("", "legalAddress");
    private static final javax.xml.namespace.QName ns2_Address_TYPE_QNAME = new QName("http://clients.wsgate.phizic.rssl.com", "Address");
    private CombinedSerializer ns2_myAddress_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_mobilePhone_QNAME = new QName("", "mobilePhone");
    private static final javax.xml.namespace.QName ns1_patrName_QNAME = new QName("", "patrName");
    private static final javax.xml.namespace.QName ns1_realAddress_QNAME = new QName("", "realAddress");
    private static final javax.xml.namespace.QName ns1_sex_QNAME = new QName("", "sex");
    private static final javax.xml.namespace.QName ns1_shortName_QNAME = new QName("", "shortName");
    private static final javax.xml.namespace.QName ns1_surName_QNAME = new QName("", "surName");
    private static final int myINN_INDEX = 0;
    private static final int myBIRTHDAY_INDEX = 1;
    private static final int myBIRTHPLACE_INDEX = 2;
    private static final int myCITIZENSHIP_INDEX = 3;
    private static final int myDISPLAYID_INDEX = 4;
    private static final int myDOCUMENTS_INDEX = 5;
    private static final int myEMAIL_INDEX = 6;
    private static final int myFIRSTNAME_INDEX = 7;
    private static final int myFULLNAME_INDEX = 8;
    private static final int myHOMEPHONE_INDEX = 9;
    private static final int myID_INDEX = 10;
    private static final int myISRESIDENT_INDEX = 11;
    private static final int myJOBPHONE_INDEX = 12;
    private static final int myLEGALADDRESS_INDEX = 13;
    private static final int myMOBILEPHONE_INDEX = 14;
    private static final int myPATRNAME_INDEX = 15;
    private static final int myREALADDRESS_INDEX = 16;
    private static final int mySEX_INDEX = 17;
    private static final int mySHORTNAME_INDEX = 18;
    private static final int mySURNAME_INDEX = 19;
    
    public Client_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns5_myns5_list__CollectionInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.List.class, ns5_list_TYPE_QNAME);
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
        ns2_myAddress_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.clients.generated.Address.class, ns2_Address_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.clients.generated.Client instance = new com.rssl.phizic.wsgate.clients.generated.Client();
        com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_INN_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_INN_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myINN_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setINN((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_birthDay_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_birthDay_QNAME, reader, context);
                instance.setBirthDay((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_birthPlace_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_birthPlace_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBIRTHPLACE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBirthPlace((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_citizenship_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_citizenship_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCITIZENSHIP_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCitizenship((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_displayId_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_displayId_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDISPLAYID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDisplayId((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_documents_QNAME)) {
                member = ns5_myns5_list__CollectionInterfaceSerializer.deserialize(ns1_documents_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDOCUMENTS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDocuments((java.util.List)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_email_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_email_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myEMAIL_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setEmail((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_firstName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_firstName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFIRSTNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFirstName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_fullName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_fullName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFULLNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFullName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_homePhone_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_homePhone_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myHOMEPHONE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setHomePhone((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_id_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setId((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_isResident_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_isResident_QNAME, reader, context);
                instance.setIsResident(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_jobPhone_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_jobPhone_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myJOBPHONE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setJobPhone((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_legalAddress_QNAME)) {
                member = ns2_myAddress_SOAPSerializer.deserialize(ns1_legalAddress_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myLEGALADDRESS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setLegalAddress((com.rssl.phizic.wsgate.clients.generated.Address)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_mobilePhone_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_mobilePhone_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMOBILEPHONE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMobilePhone((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_patrName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_patrName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPATRNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPatrName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_realAddress_QNAME)) {
                member = ns2_myAddress_SOAPSerializer.deserialize(ns1_realAddress_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREALADDRESS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRealAddress((com.rssl.phizic.wsgate.clients.generated.Address)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_sex_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_sex_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySEX_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSex((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_shortName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_shortName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySHORTNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setShortName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_surName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_surName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.clients.generated.Client_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySURNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSurName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.clients.generated.Client instance = (com.rssl.phizic.wsgate.clients.generated.Client)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.clients.generated.Client instance = (com.rssl.phizic.wsgate.clients.generated.Client)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getINN(), ns1_INN_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getBirthDay(), ns1_birthDay_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getBirthPlace(), ns1_birthPlace_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCitizenship(), ns1_citizenship_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDisplayId(), ns1_displayId_QNAME, null, writer, context);
        ns5_myns5_list__CollectionInterfaceSerializer.serialize(instance.getDocuments(), ns1_documents_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getEmail(), ns1_email_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getFirstName(), ns1_firstName_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getFullName(), ns1_fullName_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getHomePhone(), ns1_homePhone_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getId(), ns1_id_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isIsResident()), ns1_isResident_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getJobPhone(), ns1_jobPhone_QNAME, null, writer, context);
        ns2_myAddress_SOAPSerializer.serialize(instance.getLegalAddress(), ns1_legalAddress_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMobilePhone(), ns1_mobilePhone_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPatrName(), ns1_patrName_QNAME, null, writer, context);
        ns2_myAddress_SOAPSerializer.serialize(instance.getRealAddress(), ns1_realAddress_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSex(), ns1_sex_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getShortName(), ns1_shortName_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSurName(), ns1_surName_QNAME, null, writer, context);
    }
}
