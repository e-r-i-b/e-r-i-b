// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.recipients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class BackRefReceiverInfoService_getRecipientInfo_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_String_1_QNAME = new QName("", "String_1");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_String_2_QNAME = new QName("", "String_2");
    private static final int mySTRING_1_INDEX = 0;
    private static final int mySTRING_2_INDEX = 1;
    
    public BackRefReceiverInfoService_getRecipientInfo_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns2_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct instance = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct();
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        for (int i=0; i<2; i++) {
            elementName = reader.getName();
            if (reader.getState() == XMLReader.END) {
                break;
            }
            if (elementName.equals(ns1_String_1_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_String_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTRING_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setString_1((java.lang.String)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_String_2_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_String_2_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTRING_2_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setString_2((java.lang.String)member);
                }
                reader.nextElementContent();
                continue;
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_String_2_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct instance = (com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct instance = (com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService_getRecipientInfo_RequestStruct)obj;
        
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getString_1(), ns1_String_1_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getString_2(), ns1_String_2_QNAME, null, writer, context);
    }
}
