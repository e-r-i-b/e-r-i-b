// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.loyalty.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class LoyaltyProgramOperation_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_moneyOperationalBalance_QNAME = new QName("", "moneyOperationalBalance");
    private static final javax.xml.namespace.QName ns2_Money_TYPE_QNAME = new QName("http://generated.loyalty.services.gate.web.phizic.rssl.com", "Money");
    private CombinedSerializer ns2_myMoney_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_operationDate_QNAME = new QName("", "operationDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_operationInfo_QNAME = new QName("", "operationInfo");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_operationKind_QNAME = new QName("", "operationKind");
    private static final javax.xml.namespace.QName ns1_operationalBalance_QNAME = new QName("", "operationalBalance");
    private static final javax.xml.namespace.QName ns3_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final int myMONEYOPERATIONALBALANCE_INDEX = 0;
    private static final int myOPERATIONDATE_INDEX = 1;
    private static final int myOPERATIONINFO_INDEX = 2;
    private static final int myOPERATIONKIND_INDEX = 3;
    private static final int myOPERATIONALBALANCE_INDEX = 4;
    
    public LoyaltyProgramOperation_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myMoney_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.loyalty.generated.Money.class, ns2_Money_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns3_decimal_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation instance = new com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation();
        com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_moneyOperationalBalance_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_moneyOperationalBalance_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMONEYOPERATIONALBALANCE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMoneyOperationalBalance((com.rssl.phizicgate.wsgate.services.loyalty.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_operationDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_operationDate_QNAME, reader, context);
                instance.setOperationDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_operationInfo_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_operationInfo_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOPERATIONINFO_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOperationInfo((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_operationKind_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_operationKind_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOPERATIONKIND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOperationKind((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_operationalBalance_QNAME)) {
                member = ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_operationalBalance_QNAME, reader, context);
                instance.setOperationalBalance((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation instance = (com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation instance = (com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation)obj;
        
        ns2_myMoney_SOAPSerializer.serialize(instance.getMoneyOperationalBalance(), ns1_moneyOperationalBalance_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getOperationDate(), ns1_operationDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getOperationInfo(), ns1_operationInfo_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getOperationKind(), ns1_operationKind_QNAME, null, writer, context);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getOperationalBalance(), ns1_operationalBalance_QNAME, null, writer, context);
    }
}
