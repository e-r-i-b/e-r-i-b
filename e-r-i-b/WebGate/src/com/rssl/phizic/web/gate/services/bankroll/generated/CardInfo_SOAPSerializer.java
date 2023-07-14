// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class CardInfo_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_agreementDate_QNAME = new QName("", "agreementDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_agreementNumber_QNAME = new QName("", "agreementNumber");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_availableCashLimit_QNAME = new QName("", "availableCashLimit");
    private static final javax.xml.namespace.QName ns2_Money_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "Money");
    private CombinedSerializer ns2_myMoney_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_codeWord_QNAME = new QName("", "codeWord");
    private static final javax.xml.namespace.QName ns1_decimalHoldSum_QNAME = new QName("", "decimalHoldSum");
    private static final javax.xml.namespace.QName ns3_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final javax.xml.namespace.QName ns1_holdSum_QNAME = new QName("", "holdSum");
    private static final javax.xml.namespace.QName ns1_holderName_QNAME = new QName("", "holderName");
    private static final javax.xml.namespace.QName ns1_lastOperationDate_QNAME = new QName("", "lastOperationDate");
    private static final javax.xml.namespace.QName ns1_longId_QNAME = new QName("", "longId");
    private static final javax.xml.namespace.QName ns3_long_TYPE_QNAME = SchemaConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns3_myns3__long__long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_purchaseLimit_QNAME = new QName("", "purchaseLimit");
    private static final int myAGREEMENTDATE_INDEX = 0;
    private static final int myAGREEMENTNUMBER_INDEX = 1;
    private static final int myAVAILABLECASHLIMIT_INDEX = 2;
    private static final int myCODEWORD_INDEX = 3;
    private static final int myDECIMALHOLDSUM_INDEX = 4;
    private static final int myHOLDSUM_INDEX = 5;
    private static final int myHOLDERNAME_INDEX = 6;
    private static final int myLASTOPERATIONDATE_INDEX = 7;
    private static final int myLONGID_INDEX = 8;
    private static final int myPURCHASELIMIT_INDEX = 9;
    
    public CardInfo_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myMoney_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.bankroll.generated.Money.class, ns2_Money_TYPE_QNAME);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns3_decimal_TYPE_QNAME);
        ns3_myns3__long__long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, long.class, ns3_long_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo instance = new com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo();
        com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_agreementDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_agreementDate_QNAME, reader, context);
                instance.setAgreementDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_agreementNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_agreementNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAGREEMENTNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAgreementNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_availableCashLimit_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_availableCashLimit_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAVAILABLECASHLIMIT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAvailableCashLimit((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_codeWord_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_codeWord_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCODEWORD_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCodeWord((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_decimalHoldSum_QNAME)) {
                member = ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_decimalHoldSum_QNAME, reader, context);
                instance.setDecimalHoldSum((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_holdSum_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_holdSum_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myHOLDSUM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setHoldSum((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_holderName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_holderName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myHOLDERNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setHolderName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_lastOperationDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_lastOperationDate_QNAME, reader, context);
                instance.setLastOperationDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_longId_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_longId_QNAME, reader, context);
                instance.setLongId(((Long)member).longValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_purchaseLimit_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_purchaseLimit_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPURCHASELIMIT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPurchaseLimit((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo instance = (com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo instance = (com.rssl.phizic.web.gate.services.bankroll.generated.CardInfo)obj;
        
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getAgreementDate(), ns1_agreementDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getAgreementNumber(), ns1_agreementNumber_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getAvailableCashLimit(), ns1_availableCashLimit_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCodeWord(), ns1_codeWord_QNAME, null, writer, context);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getDecimalHoldSum(), ns1_decimalHoldSum_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getHoldSum(), ns1_holdSum_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getHolderName(), ns1_holderName_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getLastOperationDate(), ns1_lastOperationDate_QNAME, null, writer, context);
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getLongId()), ns1_longId_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getPurchaseLimit(), ns1_purchaseLimit_QNAME, null, writer, context);
    }
}
