// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.fund.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class RequestInfo_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_accumulatedSum_QNAME = new QName("", "accumulatedSum");
    private static final javax.xml.namespace.QName ns3_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final javax.xml.namespace.QName ns1_internalId_QNAME = new QName("", "internalId");
    private static final javax.xml.namespace.QName ns5_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns5_myns5__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_state_QNAME = new QName("", "state");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final int myACCUMULATEDSUM_INDEX = 0;
    private static final int myINTERNALID_INDEX = 1;
    private static final int mySTATE_INDEX = 2;
    
    public RequestInfo_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns3_decimal_TYPE_QNAME);
        ns5_myns5__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns5_long_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo instance = new com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo();
        com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_accumulatedSum_QNAME)) {
                member = ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_accumulatedSum_QNAME, reader, context);
                instance.setAccumulatedSum((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_internalId_QNAME)) {
                member = ns5_myns5__long__java_lang_Long_Long_Serializer.deserialize(ns1_internalId_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myINTERNALID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setInternalId((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_state_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_state_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTATE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setState((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo instance = (com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo instance = (com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo)obj;
        
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getAccumulatedSum(), ns1_accumulatedSum_QNAME, null, writer, context);
        ns5_myns5__long__java_lang_Long_Long_Serializer.serialize(instance.getInternalId(), ns1_internalId_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getState(), ns1_state_QNAME, null, writer, context);
    }
}
