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

public class Card_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_additionalCardType_QNAME = new QName("", "additionalCardType");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_availableLimit_QNAME = new QName("", "availableLimit");
    private static final javax.xml.namespace.QName ns2_Money_TYPE_QNAME = new QName("http://generated.bankroll.wsgate.phizic.rssl.com", "Money");
    private CombinedSerializer ns2_myMoney_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_cardBonusSign_QNAME = new QName("", "cardBonusSign");
    private static final javax.xml.namespace.QName ns1_cardLevel_QNAME = new QName("", "cardLevel");
    private static final javax.xml.namespace.QName ns1_cardState_QNAME = new QName("", "cardState");
    private static final javax.xml.namespace.QName ns1_cardType_QNAME = new QName("", "cardType");
    private static final javax.xml.namespace.QName ns1_contractNumber_QNAME = new QName("", "contractNumber");
    private static final javax.xml.namespace.QName ns1_currency_QNAME = new QName("", "currency");
    private static final javax.xml.namespace.QName ns2_Currency_TYPE_QNAME = new QName("http://generated.bankroll.wsgate.phizic.rssl.com", "Currency");
    private CombinedSerializer ns2_myCurrency_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns1_displayedExpireDate_QNAME = new QName("", "displayedExpireDate");
    private static final javax.xml.namespace.QName ns1_emailAddress_QNAME = new QName("", "emailAddress");
    private static final javax.xml.namespace.QName ns1_expireDate_QNAME = new QName("", "expireDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns1_issueDate_QNAME = new QName("", "issueDate");
    private static final javax.xml.namespace.QName ns1_kind_QNAME = new QName("", "kind");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_main_QNAME = new QName("", "main");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_mainCardNumber_QNAME = new QName("", "mainCardNumber");
    private static final javax.xml.namespace.QName ns1_number_QNAME = new QName("", "number");
    private static final javax.xml.namespace.QName ns1_office_QNAME = new QName("", "office");
    private static final javax.xml.namespace.QName ns2_Office_TYPE_QNAME = new QName("http://generated.bankroll.wsgate.phizic.rssl.com", "Office");
    private CombinedSerializer ns2_myOffice_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_primaryAccountExternalId_QNAME = new QName("", "primaryAccountExternalId");
    private static final javax.xml.namespace.QName ns1_primaryAccountNumber_QNAME = new QName("", "primaryAccountNumber");
    private static final javax.xml.namespace.QName ns1_reportDeliveryLanguage_QNAME = new QName("", "reportDeliveryLanguage");
    private static final javax.xml.namespace.QName ns1_reportDeliveryType_QNAME = new QName("", "reportDeliveryType");
    private static final javax.xml.namespace.QName ns1_statusDescription_QNAME = new QName("", "statusDescription");
    private static final javax.xml.namespace.QName ns1_subkind_QNAME = new QName("", "subkind");
    private static final javax.xml.namespace.QName ns1_type_QNAME = new QName("", "type");
    private static final javax.xml.namespace.QName ns1_useReportDelivery_QNAME = new QName("", "useReportDelivery");
    private static final javax.xml.namespace.QName ns1_virtual_QNAME = new QName("", "virtual");
    private static final int myADDITIONALCARDTYPE_INDEX = 0;
    private static final int myAVAILABLELIMIT_INDEX = 1;
    private static final int myCARDBONUSSIGN_INDEX = 2;
    private static final int myCARDLEVEL_INDEX = 3;
    private static final int myCARDSTATE_INDEX = 4;
    private static final int myCARDTYPE_INDEX = 5;
    private static final int myCONTRACTNUMBER_INDEX = 6;
    private static final int myCURRENCY_INDEX = 7;
    private static final int myDESCRIPTION_INDEX = 8;
    private static final int myDISPLAYEDEXPIREDATE_INDEX = 9;
    private static final int myEMAILADDRESS_INDEX = 10;
    private static final int myEXPIREDATE_INDEX = 11;
    private static final int myID_INDEX = 12;
    private static final int myISSUEDATE_INDEX = 13;
    private static final int myKIND_INDEX = 14;
    private static final int myMAIN_INDEX = 15;
    private static final int myMAINCARDNUMBER_INDEX = 16;
    private static final int myNUMBER_INDEX = 17;
    private static final int myOFFICE_INDEX = 18;
    private static final int myPRIMARYACCOUNTEXTERNALID_INDEX = 19;
    private static final int myPRIMARYACCOUNTNUMBER_INDEX = 20;
    private static final int myREPORTDELIVERYLANGUAGE_INDEX = 21;
    private static final int myREPORTDELIVERYTYPE_INDEX = 22;
    private static final int mySTATUSDESCRIPTION_INDEX = 23;
    private static final int mySUBKIND_INDEX = 24;
    private static final int myTYPE_INDEX = 25;
    private static final int myUSEREPORTDELIVERY_INDEX = 26;
    private static final int myVIRTUAL_INDEX = 27;
    
    public Card_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myMoney_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.bankroll.generated.Money.class, ns2_Money_TYPE_QNAME);
        ns2_myCurrency_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.bankroll.generated.Currency.class, ns2_Currency_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
        ns2_myOffice_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.bankroll.generated.Office.class, ns2_Office_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.bankroll.generated.Card instance = new com.rssl.phizic.wsgate.bankroll.generated.Card();
        com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_additionalCardType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_additionalCardType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myADDITIONALCARDTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAdditionalCardType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_availableLimit_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_availableLimit_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAVAILABLELIMIT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAvailableLimit((com.rssl.phizic.wsgate.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_cardBonusSign_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_cardBonusSign_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCARDBONUSSIGN_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCardBonusSign((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_cardLevel_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_cardLevel_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCARDLEVEL_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCardLevel((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_cardState_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_cardState_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCARDSTATE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCardState((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_cardType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_cardType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCARDTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCardType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_contractNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_contractNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCONTRACTNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setContractNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_currency_QNAME)) {
                member = ns2_myCurrency_SOAPSerializer.deserialize(ns1_currency_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCURRENCY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCurrency((com.rssl.phizic.wsgate.bankroll.generated.Currency)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_description_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_description_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
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
            if (elementName.equals(ns1_displayedExpireDate_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_displayedExpireDate_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDISPLAYEDEXPIREDATE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDisplayedExpireDate((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_emailAddress_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_emailAddress_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myEMAILADDRESS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setEmailAddress((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_expireDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_expireDate_QNAME, reader, context);
                instance.setExpireDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_id_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
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
            if (elementName.equals(ns1_issueDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_issueDate_QNAME, reader, context);
                instance.setIssueDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_kind_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_kind_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myKIND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setKind((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_main_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_main_QNAME, reader, context);
                instance.setMain(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_mainCardNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_mainCardNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMAINCARDNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMainCardNumber((java.lang.String)member);
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
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
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
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOFFICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOffice((com.rssl.phizic.wsgate.bankroll.generated.Office)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_primaryAccountExternalId_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_primaryAccountExternalId_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPRIMARYACCOUNTEXTERNALID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPrimaryAccountExternalId((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_primaryAccountNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_primaryAccountNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPRIMARYACCOUNTNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPrimaryAccountNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_reportDeliveryLanguage_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_reportDeliveryLanguage_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREPORTDELIVERYLANGUAGE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setReportDeliveryLanguage((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_reportDeliveryType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_reportDeliveryType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREPORTDELIVERYTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setReportDeliveryType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_statusDescription_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_statusDescription_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTATUSDESCRIPTION_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setStatusDescription((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_subkind_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_subkind_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySUBKIND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSubkind((java.lang.Long)member);
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
                        builder = new com.rssl.phizic.wsgate.bankroll.generated.Card_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_useReportDelivery_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_useReportDelivery_QNAME, reader, context);
                instance.setUseReportDelivery(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_virtual_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_virtual_QNAME, reader, context);
                instance.setVirtual(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.bankroll.generated.Card instance = (com.rssl.phizic.wsgate.bankroll.generated.Card)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.bankroll.generated.Card instance = (com.rssl.phizic.wsgate.bankroll.generated.Card)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getAdditionalCardType(), ns1_additionalCardType_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getAvailableLimit(), ns1_availableLimit_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCardBonusSign(), ns1_cardBonusSign_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCardLevel(), ns1_cardLevel_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCardState(), ns1_cardState_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCardType(), ns1_cardType_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getContractNumber(), ns1_contractNumber_QNAME, null, writer, context);
        ns2_myCurrency_SOAPSerializer.serialize(instance.getCurrency(), ns1_currency_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDisplayedExpireDate(), ns1_displayedExpireDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getEmailAddress(), ns1_emailAddress_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getExpireDate(), ns1_expireDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getId(), ns1_id_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getIssueDate(), ns1_issueDate_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getKind(), ns1_kind_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isMain()), ns1_main_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMainCardNumber(), ns1_mainCardNumber_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getNumber(), ns1_number_QNAME, null, writer, context);
        ns2_myOffice_SOAPSerializer.serialize(instance.getOffice(), ns1_office_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPrimaryAccountExternalId(), ns1_primaryAccountExternalId_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPrimaryAccountNumber(), ns1_primaryAccountNumber_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getReportDeliveryLanguage(), ns1_reportDeliveryLanguage_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getReportDeliveryType(), ns1_reportDeliveryType_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getStatusDescription(), ns1_statusDescription_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getSubkind(), ns1_subkind_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getType(), ns1_type_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isUseReportDelivery()), ns1_useReportDelivery_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isVirtual()), ns1_virtual_QNAME, null, writer, context);
    }
}
