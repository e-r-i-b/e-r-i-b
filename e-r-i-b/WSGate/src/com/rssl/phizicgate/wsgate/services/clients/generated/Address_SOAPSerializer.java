// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.clients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Address_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_building_QNAME = new QName("", "building");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_city_QNAME = new QName("", "city");
    private static final javax.xml.namespace.QName ns1_district_QNAME = new QName("", "district");
    private static final javax.xml.namespace.QName ns1_flat_QNAME = new QName("", "flat");
    private static final javax.xml.namespace.QName ns1_homePhone_QNAME = new QName("", "homePhone");
    private static final javax.xml.namespace.QName ns1_house_QNAME = new QName("", "house");
    private static final javax.xml.namespace.QName ns1_mobilePhone_QNAME = new QName("", "mobilePhone");
    private static final javax.xml.namespace.QName ns1_mobileOperator_QNAME = new QName("", "mobileOperator");
    private static final javax.xml.namespace.QName ns1_postalCode_QNAME = new QName("", "postalCode");
    private static final javax.xml.namespace.QName ns1_province_QNAME = new QName("", "province");
    private static final javax.xml.namespace.QName ns1_street_QNAME = new QName("", "street");
    private static final javax.xml.namespace.QName ns1_workPhone_QNAME = new QName("", "workPhone");
    private static final int myBUILDING_INDEX = 0;
    private static final int myCITY_INDEX = 1;
    private static final int myDISTRICT_INDEX = 2;
    private static final int myFLAT_INDEX = 3;
    private static final int myHOMEPHONE_INDEX = 4;
    private static final int myHOUSE_INDEX = 5;
    private static final int myMOBILEPHONE_INDEX = 6;
    private static final int myMOBILEOPERATOR_INDEX = 7;
    private static final int myPOSTALCODE_INDEX = 8;
    private static final int myPROVINCE_INDEX = 9;
    private static final int mySTREET_INDEX = 10;
    private static final int myWORKPHONE_INDEX = 11;
    
    public Address_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns2_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.clients.generated.Address instance = new com.rssl.phizicgate.wsgate.services.clients.generated.Address();
        com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_building_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_building_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBUILDING_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBuilding((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_city_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_city_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCITY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCity((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_district_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_district_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDISTRICT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDistrict((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_flat_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_flat_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFLAT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFlat((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_homePhone_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_homePhone_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
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
            if (elementName.equals(ns1_house_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_house_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myHOUSE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setHouse((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_mobilePhone_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_mobilePhone_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
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
            if (elementName.equals(ns1_mobileOperator_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_mobileOperator_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMOBILEOPERATOR_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMobileOperator((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_postalCode_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_postalCode_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPOSTALCODE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPostalCode((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_province_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_province_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPROVINCE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setProvince((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_street_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_street_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTREET_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setStreet((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_workPhone_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_workPhone_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myWORKPHONE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setWorkPhone((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.clients.generated.Address instance = (com.rssl.phizicgate.wsgate.services.clients.generated.Address)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.clients.generated.Address instance = (com.rssl.phizicgate.wsgate.services.clients.generated.Address)obj;
        
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getBuilding(), ns1_building_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getCity(), ns1_city_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getDistrict(), ns1_district_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getFlat(), ns1_flat_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getHomePhone(), ns1_homePhone_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getHouse(), ns1_house_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getMobilePhone(), ns1_mobilePhone_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getMobileOperator(), ns1_mobileOperator_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getPostalCode(), ns1_postalCode_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getProvince(), ns1_province_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getStreet(), ns1_street_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getWorkPhone(), ns1_workPhone_QNAME, null, writer, context);
    }
}
