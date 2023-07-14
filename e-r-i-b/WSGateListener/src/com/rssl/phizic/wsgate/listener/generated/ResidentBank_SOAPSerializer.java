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

public class ResidentBank_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_BIC_QNAME = new QName("", "BIC");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_account_QNAME = new QName("", "account");
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_name_QNAME = new QName("", "name");
    private static final javax.xml.namespace.QName ns1_place_QNAME = new QName("", "place");
    private static final javax.xml.namespace.QName ns1_shortName_QNAME = new QName("", "shortName");
    private static final javax.xml.namespace.QName ns1_synchKey_QNAME = new QName("", "synchKey");
    private static final int myBIC_INDEX = 0;
    private static final int myACCOUNT_INDEX = 1;
    private static final int myID_INDEX = 2;
    private static final int myNAME_INDEX = 3;
    private static final int myPLACE_INDEX = 4;
    private static final int mySHORTNAME_INDEX = 5;
    private static final int mySYNCHKEY_INDEX = 6;
    
    public ResidentBank_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.ResidentBank instance = new com.rssl.phizic.wsgate.listener.generated.ResidentBank();
        com.rssl.phizic.wsgate.listener.generated.ResidentBank_SOAPBuilder builder = null;
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
                        builder = new com.rssl.phizic.wsgate.listener.generated.ResidentBank_SOAPBuilder();
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
            if (elementName.equals(ns1_account_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_account_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.ResidentBank_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myACCOUNT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAccount((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_id_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.ResidentBank_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setId((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_name_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_name_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.ResidentBank_SOAPBuilder();
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
            if (elementName.equals(ns1_place_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_place_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.ResidentBank_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPLACE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPlace((java.lang.String)member);
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
                        builder = new com.rssl.phizic.wsgate.listener.generated.ResidentBank_SOAPBuilder();
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
            if (elementName.equals(ns1_synchKey_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_synchKey_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.ResidentBank_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySYNCHKEY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSynchKey((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.ResidentBank instance = (com.rssl.phizic.wsgate.listener.generated.ResidentBank)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.ResidentBank instance = (com.rssl.phizic.wsgate.listener.generated.ResidentBank)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getBIC(), ns1_BIC_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getAccount(), ns1_account_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getId(), ns1_id_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPlace(), ns1_place_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getShortName(), ns1_shortName_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSynchKey(), ns1_synchKey_QNAME, null, writer, context);
    }
}
