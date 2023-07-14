// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class BackRefBankrollService_PortType_getAccountOffice_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_Long_1_QNAME = new QName("", "Long_1");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_String_2_QNAME = new QName("", "String_2");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final int myLONG_1_INDEX = 0;
    private static final int mySTRING_2_INDEX = 1;
    
    public BackRefBankrollService_PortType_getAccountOffice_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct instance = new com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct();
        com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        for (int i=0; i<2; i++) {
            elementName = reader.getName();
            if (reader.getState() == XMLReader.END) {
                break;
            }
            if (elementName.equals(ns1_Long_1_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_Long_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myLONG_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setLong_1((java.lang.Long)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_String_2_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_String_2_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct_SOAPBuilder();
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
        com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct instance = (com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct instance = (com.rssl.phizic.wsgate.bankroll.generated.BackRefBankrollService_PortType_getAccountOffice_RequestStruct)obj;
        
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getLong_1(), ns1_Long_1_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getString_2(), ns1_String_2_QNAME, null, writer, context);
    }
}
