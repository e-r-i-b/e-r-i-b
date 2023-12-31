// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.calendar.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class FieldValidationRule_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_errorMessage_QNAME = new QName("", "errorMessage");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_fieldValidationRuleType_QNAME = new QName("", "fieldValidationRuleType");
    private static final javax.xml.namespace.QName ns1_parameters_QNAME = new QName("", "parameters");
    private static final javax.xml.namespace.QName ns5_map_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_MAP;
    private CombinedSerializer ns5_myns5_map__MapInterfaceSerializer;
    private static final int myERRORMESSAGE_INDEX = 0;
    private static final int myFIELDVALIDATIONRULETYPE_INDEX = 1;
    private static final int myPARAMETERS_INDEX = 2;
    
    public FieldValidationRule_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns5_myns5_map__MapInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Map.class, ns5_map_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule instance = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule();
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_errorMessage_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_errorMessage_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myERRORMESSAGE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setErrorMessage((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_fieldValidationRuleType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_fieldValidationRuleType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFIELDVALIDATIONRULETYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFieldValidationRuleType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_parameters_QNAME)) {
                member = ns5_myns5_map__MapInterfaceSerializer.deserialize(ns1_parameters_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPARAMETERS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setParameters((java.util.Map)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.FieldValidationRule)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getErrorMessage(), ns1_errorMessage_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getFieldValidationRuleType(), ns1_fieldValidationRuleType_QNAME, null, writer, context);
        ns5_myns5_map__MapInterfaceSerializer.serialize(instance.getParameters(), ns1_parameters_QNAME, null, writer, context);
    }
}
