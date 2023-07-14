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

public class CurrencyRate_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_dynamicExchangeRate_QNAME = new QName("", "dynamicExchangeRate");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_fromCurrency_QNAME = new QName("", "fromCurrency");
    private static final javax.xml.namespace.QName ns2_Currency_TYPE_QNAME = new QName("http://generated.listener.wsgate.phizic.rssl.com", "Currency");
    private CombinedSerializer ns2_myCurrency_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_fromValue_QNAME = new QName("", "fromValue");
    private static final javax.xml.namespace.QName ns3_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final javax.xml.namespace.QName ns1_tarifPlanCodeType_QNAME = new QName("", "tarifPlanCodeType");
    private static final javax.xml.namespace.QName ns1_toCurrency_QNAME = new QName("", "toCurrency");
    private static final javax.xml.namespace.QName ns1_toValue_QNAME = new QName("", "toValue");
    private static final javax.xml.namespace.QName ns1_type_QNAME = new QName("", "type");
    private static final int myDYNAMICEXCHANGERATE_INDEX = 0;
    private static final int myFROMCURRENCY_INDEX = 1;
    private static final int myFROMVALUE_INDEX = 2;
    private static final int myTARIFPLANCODETYPE_INDEX = 3;
    private static final int myTOCURRENCY_INDEX = 4;
    private static final int myTOVALUE_INDEX = 5;
    private static final int myTYPE_INDEX = 6;
    
    public CurrencyRate_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myCurrency_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.listener.generated.Currency.class, ns2_Currency_TYPE_QNAME);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns3_decimal_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.CurrencyRate instance = new com.rssl.phizic.wsgate.listener.generated.CurrencyRate();
        com.rssl.phizic.wsgate.listener.generated.CurrencyRate_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_dynamicExchangeRate_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_dynamicExchangeRate_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.CurrencyRate_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDYNAMICEXCHANGERATE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDynamicExchangeRate((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_fromCurrency_QNAME)) {
                member = ns2_myCurrency_SOAPSerializer.deserialize(ns1_fromCurrency_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.CurrencyRate_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFROMCURRENCY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFromCurrency((com.rssl.phizic.wsgate.listener.generated.Currency)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_fromValue_QNAME)) {
                member = ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_fromValue_QNAME, reader, context);
                instance.setFromValue((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_tarifPlanCodeType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_tarifPlanCodeType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.CurrencyRate_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTARIFPLANCODETYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTarifPlanCodeType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_toCurrency_QNAME)) {
                member = ns2_myCurrency_SOAPSerializer.deserialize(ns1_toCurrency_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.CurrencyRate_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTOCURRENCY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setToCurrency((com.rssl.phizic.wsgate.listener.generated.Currency)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_toValue_QNAME)) {
                member = ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_toValue_QNAME, reader, context);
                instance.setToValue((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_type_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_type_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.CurrencyRate_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.CurrencyRate instance = (com.rssl.phizic.wsgate.listener.generated.CurrencyRate)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.CurrencyRate instance = (com.rssl.phizic.wsgate.listener.generated.CurrencyRate)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDynamicExchangeRate(), ns1_dynamicExchangeRate_QNAME, null, writer, context);
        ns2_myCurrency_SOAPSerializer.serialize(instance.getFromCurrency(), ns1_fromCurrency_QNAME, null, writer, context);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getFromValue(), ns1_fromValue_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getTarifPlanCodeType(), ns1_tarifPlanCodeType_QNAME, null, writer, context);
        ns2_myCurrency_SOAPSerializer.serialize(instance.getToCurrency(), ns1_toCurrency_QNAME, null, writer, context);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getToValue(), ns1_toValue_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getType(), ns1_type_QNAME, null, writer, context);
    }
}
