// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class AutoPayment_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_amount_QNAME = new QName("", "amount");
    private static final javax.xml.namespace.QName ns2_Money_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Money");
    private CombinedSerializer ns2_myMoney_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_cardNumber_QNAME = new QName("", "cardNumber");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_codeService_QNAME = new QName("", "codeService");
    private static final javax.xml.namespace.QName ns1_dateAccepted_QNAME = new QName("", "dateAccepted");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_endDate_QNAME = new QName("", "endDate");
    private static final javax.xml.namespace.QName ns1_executionEventType_QNAME = new QName("", "executionEventType");
    private static final javax.xml.namespace.QName ns1_externalId_QNAME = new QName("", "externalId");
    private static final javax.xml.namespace.QName ns1_floorLimit_QNAME = new QName("", "floorLimit");
    private static final javax.xml.namespace.QName ns1_friendlyName_QNAME = new QName("", "friendlyName");
    private static final javax.xml.namespace.QName ns1_number_QNAME = new QName("", "number");
    private static final javax.xml.namespace.QName ns1_office_QNAME = new QName("", "office");
    private static final javax.xml.namespace.QName ns2_Office_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Office");
    private CombinedSerializer ns2_myOffice_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_payDay_QNAME = new QName("", "payDay");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_percent_QNAME = new QName("", "percent");
    private static final javax.xml.namespace.QName ns3_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final javax.xml.namespace.QName ns1_priority_QNAME = new QName("", "priority");
    private static final javax.xml.namespace.QName ns1_receiverName_QNAME = new QName("", "receiverName");
    private static final javax.xml.namespace.QName ns1_reportStatus_QNAME = new QName("", "reportStatus");
    private static final javax.xml.namespace.QName ns1_requisite_QNAME = new QName("", "requisite");
    private static final javax.xml.namespace.QName ns1_startDate_QNAME = new QName("", "startDate");
    private static final javax.xml.namespace.QName ns1_sumType_QNAME = new QName("", "sumType");
    private static final javax.xml.namespace.QName ns1_totalAmountLimit_QNAME = new QName("", "totalAmountLimit");
    private static final javax.xml.namespace.QName ns1_totalAmountPeriod_QNAME = new QName("", "totalAmountPeriod");
    private static final javax.xml.namespace.QName ns1_type_QNAME = new QName("", "type");
    private static final int myAMOUNT_INDEX = 0;
    private static final int myCARDNUMBER_INDEX = 1;
    private static final int myCODESERVICE_INDEX = 2;
    private static final int myDATEACCEPTED_INDEX = 3;
    private static final int myENDDATE_INDEX = 4;
    private static final int myEXECUTIONEVENTTYPE_INDEX = 5;
    private static final int myEXTERNALID_INDEX = 6;
    private static final int myFLOORLIMIT_INDEX = 7;
    private static final int myFRIENDLYNAME_INDEX = 8;
    private static final int myNUMBER_INDEX = 9;
    private static final int myOFFICE_INDEX = 10;
    private static final int myPAYDAY_INDEX = 11;
    private static final int myPERCENT_INDEX = 12;
    private static final int myPRIORITY_INDEX = 13;
    private static final int myRECEIVERNAME_INDEX = 14;
    private static final int myREPORTSTATUS_INDEX = 15;
    private static final int myREQUISITE_INDEX = 16;
    private static final int mySTARTDATE_INDEX = 17;
    private static final int mySUMTYPE_INDEX = 18;
    private static final int myTOTALAMOUNTLIMIT_INDEX = 19;
    private static final int myTOTALAMOUNTPERIOD_INDEX = 20;
    private static final int myTYPE_INDEX = 21;
    
    public AutoPayment_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myMoney_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.Money.class, ns2_Money_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns2_myOffice_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.Office.class, ns2_Office_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns3_decimal_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment instance = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment();
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder builder = null;
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
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAMOUNT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAmount((com.rssl.phizicgate.wsgate.services.documents.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_cardNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_cardNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCARDNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCardNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_codeService_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_codeService_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCODESERVICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCodeService((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_dateAccepted_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_dateAccepted_QNAME, reader, context);
                instance.setDateAccepted((java.util.Calendar)member);
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
            if (elementName.equals(ns1_executionEventType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_executionEventType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myEXECUTIONEVENTTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setExecutionEventType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_externalId_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_externalId_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myEXTERNALID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setExternalId((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_floorLimit_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_floorLimit_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFLOORLIMIT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFloorLimit((com.rssl.phizicgate.wsgate.services.documents.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_friendlyName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_friendlyName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFRIENDLYNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFriendlyName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_number_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_number_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_office_QNAME)) {
                member = ns2_myOffice_SOAPSerializer.deserialize(ns1_office_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOFFICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOffice((com.rssl.phizicgate.wsgate.services.documents.generated.Office)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_payDay_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_payDay_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPAYDAY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPayDay((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_percent_QNAME)) {
                member = ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_percent_QNAME, reader, context);
                instance.setPercent((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_priority_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_priority_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPRIORITY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPriority((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_receiverName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_receiverName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myRECEIVERNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setReceiverName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_reportStatus_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_reportStatus_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREPORTSTATUS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setReportStatus((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_requisite_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_requisite_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREQUISITE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRequisite((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_startDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_startDate_QNAME, reader, context);
                instance.setStartDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_sumType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_sumType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySUMTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSumType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_totalAmountLimit_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_totalAmountLimit_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTOTALAMOUNTLIMIT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTotalAmountLimit((com.rssl.phizicgate.wsgate.services.documents.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_totalAmountPeriod_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_totalAmountPeriod_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTOTALAMOUNTPERIOD_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTotalAmountPeriod((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_type_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_type_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPBuilder();
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
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment instance = (com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment instance = (com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment)obj;
        
        ns2_myMoney_SOAPSerializer.serialize(instance.getAmount(), ns1_amount_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCardNumber(), ns1_cardNumber_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCodeService(), ns1_codeService_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getDateAccepted(), ns1_dateAccepted_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getEndDate(), ns1_endDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getExecutionEventType(), ns1_executionEventType_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getExternalId(), ns1_externalId_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getFloorLimit(), ns1_floorLimit_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getFriendlyName(), ns1_friendlyName_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getNumber(), ns1_number_QNAME, null, writer, context);
        ns2_myOffice_SOAPSerializer.serialize(instance.getOffice(), ns1_office_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getPayDay(), ns1_payDay_QNAME, null, writer, context);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getPercent(), ns1_percent_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getPriority(), ns1_priority_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getReceiverName(), ns1_receiverName_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getReportStatus(), ns1_reportStatus_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getRequisite(), ns1_requisite_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getStartDate(), ns1_startDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSumType(), ns1_sumType_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getTotalAmountLimit(), ns1_totalAmountLimit_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getTotalAmountPeriod(), ns1_totalAmountPeriod_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getType(), ns1_type_QNAME, null, writer, context);
    }
}