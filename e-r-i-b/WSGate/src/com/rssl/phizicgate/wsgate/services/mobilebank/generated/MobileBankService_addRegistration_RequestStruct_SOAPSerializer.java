// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class MobileBankService_addRegistration_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_Client_1_QNAME = new QName("", "Client_1");
    private static final javax.xml.namespace.QName ns2_Client_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "Client");
    private CombinedSerializer ns2_myClient_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_String_2_QNAME = new QName("", "String_2");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_String_3_QNAME = new QName("", "String_3");
    private static final javax.xml.namespace.QName ns1_String_4_QNAME = new QName("", "String_4");
    private static final javax.xml.namespace.QName ns1_String_5_QNAME = new QName("", "String_5");
    private static final int myCLIENT_1_INDEX = 0;
    private static final int mySTRING_2_INDEX = 1;
    private static final int mySTRING_3_INDEX = 2;
    private static final int mySTRING_4_INDEX = 3;
    private static final int mySTRING_5_INDEX = 4;
    
    public MobileBankService_addRegistration_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myClient_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client.class, ns2_Client_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct instance = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct();
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        for (int i=0; i<5; i++) {
            elementName = reader.getName();
            if (reader.getState() == XMLReader.END) {
                break;
            }
            if (elementName.equals(ns1_Client_1_QNAME)) {
                member = ns2_myClient_SOAPSerializer.deserialize(ns1_Client_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCLIENT_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setClient_1((com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_String_2_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_String_2_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTRING_2_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setString_2((java.lang.String)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_String_3_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_String_3_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTRING_3_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setString_3((java.lang.String)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_String_4_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_String_4_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTRING_4_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setString_4((java.lang.String)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_String_5_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_String_5_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTRING_5_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setString_5((java.lang.String)member);
                }
                reader.nextElementContent();
                continue;
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_String_5_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_addRegistration_RequestStruct)obj;
        
        ns2_myClient_SOAPSerializer.serialize(instance.getClient_1(), ns1_Client_1_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getString_2(), ns1_String_2_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getString_3(), ns1_String_3_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getString_4(), ns1_String_4_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getString_5(), ns1_String_5_QNAME, null, writer, context);
    }
}
