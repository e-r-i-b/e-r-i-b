// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.listener.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Office_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_BIC_QNAME = new QName("", "BIC");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_address_QNAME = new QName("", "address");
    private static final javax.xml.namespace.QName ns1_code_QNAME = new QName("", "code");
    private static final javax.xml.namespace.QName ns2_Code_TYPE_QNAME = new QName("http://generated.listener.wsgate.phizic.rssl.com", "Code");
    private CombinedSerializer ns2_myCode_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_creditCardOffice_QNAME = new QName("", "creditCardOffice");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_name_QNAME = new QName("", "name");
    private static final javax.xml.namespace.QName ns1_needUpdateCreditCardOffice_QNAME = new QName("", "needUpdateCreditCardOffice");
    private static final javax.xml.namespace.QName ns1_openIMAOffice_QNAME = new QName("", "openIMAOffice");
    private static final javax.xml.namespace.QName ns1_parentSynchKey_QNAME = new QName("", "parentSynchKey");
    private static final javax.xml.namespace.QName ns1_sbidnt_QNAME = new QName("", "sbidnt");
    private static final javax.xml.namespace.QName ns1_synchKey_QNAME = new QName("", "synchKey");
    private static final javax.xml.namespace.QName ns1_telephone_QNAME = new QName("", "telephone");
    private static final int myBIC_INDEX = 0;
    private static final int myADDRESS_INDEX = 1;
    private static final int myCODE_INDEX = 2;
    private static final int myCREDITCARDOFFICE_INDEX = 3;
    private static final int myNAME_INDEX = 4;
    private static final int myNEEDUPDATECREDITCARDOFFICE_INDEX = 5;
    private static final int myOPENIMAOFFICE_INDEX = 6;
    private static final int myPARENTSYNCHKEY_INDEX = 7;
    private static final int mySBIDNT_INDEX = 8;
    private static final int mySYNCHKEY_INDEX = 9;
    private static final int myTELEPHONE_INDEX = 10;
    
    public Office_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myCode_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.listener.generated.Code.class, ns2_Code_TYPE_QNAME);
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.Office instance = new com.rssl.phizic.wsgate.listener.generated.Office();
        com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_BIC_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_BIC_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBIC_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBIC((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_address_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_address_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myADDRESS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAddress((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_code_QNAME)) {
                member = ns2_myCode_SOAPSerializer.deserialize(ns1_code_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCODE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCode((com.rssl.phizic.wsgate.listener.generated.Code)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_creditCardOffice_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_creditCardOffice_QNAME, reader, context);
                instance.setCreditCardOffice(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_name_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_name_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_needUpdateCreditCardOffice_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_needUpdateCreditCardOffice_QNAME, reader, context);
                instance.setNeedUpdateCreditCardOffice(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_openIMAOffice_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_openIMAOffice_QNAME, reader, context);
                instance.setOpenIMAOffice(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_parentSynchKey_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_parentSynchKey_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPARENTSYNCHKEY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setParentSynchKey((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_sbidnt_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_sbidnt_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySBIDNT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSbidnt((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_synchKey_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_synchKey_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySYNCHKEY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSynchKey((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_telephone_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_telephone_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.Office_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTELEPHONE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTelephone((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.Office instance = (com.rssl.phizic.wsgate.listener.generated.Office)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.Office instance = (com.rssl.phizic.wsgate.listener.generated.Office)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getBIC(), ns1_BIC_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getAddress(), ns1_address_QNAME, null, writer, context);
        ns2_myCode_SOAPSerializer.serialize(instance.getCode(), ns1_code_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isCreditCardOffice()), ns1_creditCardOffice_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isNeedUpdateCreditCardOffice()), ns1_needUpdateCreditCardOffice_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isOpenIMAOffice()), ns1_openIMAOffice_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getParentSynchKey(), ns1_parentSynchKey_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSbidnt(), ns1_sbidnt_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSynchKey(), ns1_synchKey_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getTelephone(), ns1_telephone_QNAME, null, writer, context);
    }
}
