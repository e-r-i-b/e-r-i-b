// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.deposits.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Deposit_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_amount_QNAME = new QName("", "amount");
    private static final javax.xml.namespace.QName ns2_Money_TYPE_QNAME = new QName("http://generated.deposits.services.gate.web.phizic.rssl.com", "Money");
    private CombinedSerializer ns2_myMoney_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_closeDate_QNAME = new QName("", "closeDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_duration_QNAME = new QName("", "duration");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_endDate_QNAME = new QName("", "endDate");
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns1_interestRate_QNAME = new QName("", "interestRate");
    private static final javax.xml.namespace.QName ns3_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final javax.xml.namespace.QName ns1_openDate_QNAME = new QName("", "openDate");
    private static final javax.xml.namespace.QName ns1_state_QNAME = new QName("", "state");
    private static final int myAMOUNT_INDEX = 0;
    private static final int myCLOSEDATE_INDEX = 1;
    private static final int myDESCRIPTION_INDEX = 2;
    private static final int myDURATION_INDEX = 3;
    private static final int myENDDATE_INDEX = 4;
    private static final int myID_INDEX = 5;
    private static final int myINTERESTRATE_INDEX = 6;
    private static final int myOPENDATE_INDEX = 7;
    private static final int mySTATE_INDEX = 8;
    
    public Deposit_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myMoney_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.deposits.generated.Money.class, ns2_Money_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns3_decimal_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.deposits.generated.Deposit instance = new com.rssl.phizic.web.gate.services.deposits.generated.Deposit();
        com.rssl.phizic.web.gate.services.deposits.generated.Deposit_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_amount_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_amount_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.deposits.generated.Deposit_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAMOUNT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAmount((com.rssl.phizic.web.gate.services.deposits.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_closeDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_closeDate_QNAME, reader, context);
                instance.setCloseDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_description_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_description_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.deposits.generated.Deposit_SOAPBuilder();
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
            if (elementName.equals(ns1_duration_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_duration_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.deposits.generated.Deposit_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDURATION_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDuration((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_endDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_endDate_QNAME, reader, context);
                instance.setEndDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_id_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.deposits.generated.Deposit_SOAPBuilder();
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
            if (elementName.equals(ns1_interestRate_QNAME)) {
                member = ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_interestRate_QNAME, reader, context);
                instance.setInterestRate((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_openDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_openDate_QNAME, reader, context);
                instance.setOpenDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_state_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_state_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.deposits.generated.Deposit_SOAPBuilder();
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
        com.rssl.phizic.web.gate.services.deposits.generated.Deposit instance = (com.rssl.phizic.web.gate.services.deposits.generated.Deposit)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.deposits.generated.Deposit instance = (com.rssl.phizic.web.gate.services.deposits.generated.Deposit)obj;
        
        ns2_myMoney_SOAPSerializer.serialize(instance.getAmount(), ns1_amount_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getCloseDate(), ns1_closeDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getDuration(), ns1_duration_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getEndDate(), ns1_endDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getId(), ns1_id_QNAME, null, writer, context);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getInterestRate(), ns1_interestRate_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getOpenDate(), ns1_openDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getState(), ns1_state_QNAME, null, writer, context);
    }
}