// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.payments.systems.recipients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Recipient_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_main_QNAME = new QName("", "main");
    private static final javax.xml.namespace.QName ns4_boolean_TYPE_QNAME = SOAPConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_name_QNAME = new QName("", "name");
    private static final javax.xml.namespace.QName ns1_service_QNAME = new QName("", "service");
    private static final javax.xml.namespace.QName ns2_Service_TYPE_QNAME = new QName("http://generated.recipients.systems.payments.services.gate.web.phizic.rssl.com", "Service");
    private CombinedSerializer ns2_myService_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_synchKey_QNAME = new QName("", "synchKey");
    private static final int myDESCRIPTION_INDEX = 0;
    private static final int myMAIN_INDEX = 1;
    private static final int myNAME_INDEX = 2;
    private static final int mySERVICE_INDEX = 3;
    private static final int mySYNCHKEY_INDEX = 4;
    
    public Recipient_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Boolean.class, ns4_boolean_TYPE_QNAME);
        ns2_myService_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Service.class, ns2_Service_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient instance = new com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient();
        com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_description_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_description_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDESCRIPTION_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDescription((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_main_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_main_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMAIN_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMain((java.lang.Boolean)member);
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
                        builder = new com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient_SOAPBuilder();
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
            if (elementName.equals(ns1_service_QNAME)) {
                member = ns2_myService_SOAPSerializer.deserialize(ns1_service_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySERVICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setService((com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Service)member);
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
                        builder = new com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient_SOAPBuilder();
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
        com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient instance = (com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient instance = (com.rssl.phizic.web.gate.services.payments.systems.recipients.generated.Recipient)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getMain(), ns1_main_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
        ns2_myService_SOAPSerializer.serialize(instance.getService(), ns1_service_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSynchKey(), ns1_synchKey_QNAME, null, writer, context);
    }
}
