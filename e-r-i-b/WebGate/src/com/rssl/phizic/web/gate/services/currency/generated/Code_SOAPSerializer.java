// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.currency.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Code_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_branch_QNAME = new QName("", "branch");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_fields_QNAME = new QName("", "fields");
    private static final javax.xml.namespace.QName ns4_map_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_MAP;
    private CombinedSerializer ns4_myns4_map__MapInterfaceSerializer;
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns1_office_QNAME = new QName("", "office");
    private static final javax.xml.namespace.QName ns1_region_QNAME = new QName("", "region");
    private static final int myBRANCH_INDEX = 0;
    private static final int myFIELDS_INDEX = 1;
    private static final int myID_INDEX = 2;
    private static final int myOFFICE_INDEX = 3;
    private static final int myREGION_INDEX = 4;
    
    public Code_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns4_myns4_map__MapInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Map.class, ns4_map_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.currency.generated.Code instance = new com.rssl.phizic.web.gate.services.currency.generated.Code();
        com.rssl.phizic.web.gate.services.currency.generated.Code_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_branch_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_branch_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.currency.generated.Code_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBRANCH_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBranch((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_fields_QNAME)) {
                member = ns4_myns4_map__MapInterfaceSerializer.deserialize(ns1_fields_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.currency.generated.Code_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFIELDS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFields((java.util.Map)member);
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
                        builder = new com.rssl.phizic.web.gate.services.currency.generated.Code_SOAPBuilder();
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
            if (elementName.equals(ns1_office_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_office_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.currency.generated.Code_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOFFICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOffice((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_region_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_region_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.currency.generated.Code_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREGION_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRegion((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.currency.generated.Code instance = (com.rssl.phizic.web.gate.services.currency.generated.Code)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.currency.generated.Code instance = (com.rssl.phizic.web.gate.services.currency.generated.Code)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getBranch(), ns1_branch_QNAME, null, writer, context);
        ns4_myns4_map__MapInterfaceSerializer.serialize(instance.getFields(), ns1_fields_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getId(), ns1_id_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getOffice(), ns1_office_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getRegion(), ns1_region_QNAME, null, writer, context);
    }
}
