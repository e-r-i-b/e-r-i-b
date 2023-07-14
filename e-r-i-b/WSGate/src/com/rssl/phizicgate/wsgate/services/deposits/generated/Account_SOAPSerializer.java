// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.deposits.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Account_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_accountState_QNAME = new QName("", "accountState");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_balance_QNAME = new QName("", "balance");
    private static final javax.xml.namespace.QName ns2_Money_TYPE_QNAME = new QName("http://generated.deposits.services.gate.web.phizic.rssl.com", "Money");
    private CombinedSerializer ns2_myMoney_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_clientKind_QNAME = new QName("", "clientKind");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_creditAllowed_QNAME = new QName("", "creditAllowed");
    private static final javax.xml.namespace.QName ns4_boolean_TYPE_QNAME = SOAPConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_currency_QNAME = new QName("", "currency");
    private static final javax.xml.namespace.QName ns2_Currency_TYPE_QNAME = new QName("http://generated.deposits.services.gate.web.phizic.rssl.com", "Currency");
    private CombinedSerializer ns2_myCurrency_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_debitAllowed_QNAME = new QName("", "debitAllowed");
    private static final javax.xml.namespace.QName ns1_demand_QNAME = new QName("", "demand");
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns1_interestRate_QNAME = new QName("", "interestRate");
    private static final javax.xml.namespace.QName ns3_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final javax.xml.namespace.QName ns1_kind_QNAME = new QName("", "kind");
    private static final javax.xml.namespace.QName ns1_maxSumWrite_QNAME = new QName("", "maxSumWrite");
    private static final javax.xml.namespace.QName ns1_minimumBalance_QNAME = new QName("", "minimumBalance");
    private static final javax.xml.namespace.QName ns1_number_QNAME = new QName("", "number");
    private static final javax.xml.namespace.QName ns1_office_QNAME = new QName("", "office");
    private static final javax.xml.namespace.QName ns2_Office_TYPE_QNAME = new QName("http://generated.deposits.services.gate.web.phizic.rssl.com", "Office");
    private CombinedSerializer ns2_myOffice_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_openDate_QNAME = new QName("", "openDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_passbook_QNAME = new QName("", "passbook");
    private static final javax.xml.namespace.QName ns1_prolongationDate_QNAME = new QName("", "prolongationDate");
    private static final javax.xml.namespace.QName ns1_subKind_QNAME = new QName("", "subKind");
    private static final javax.xml.namespace.QName ns1_type_QNAME = new QName("", "type");
    private static final int myACCOUNTSTATE_INDEX = 0;
    private static final int myBALANCE_INDEX = 1;
    private static final int myCLIENTKIND_INDEX = 2;
    private static final int myCREDITALLOWED_INDEX = 3;
    private static final int myCURRENCY_INDEX = 4;
    private static final int myDEBITALLOWED_INDEX = 5;
    private static final int myDEMAND_INDEX = 6;
    private static final int myDESCRIPTION_INDEX = 7;
    private static final int myID_INDEX = 8;
    private static final int myINTERESTRATE_INDEX = 9;
    private static final int myKIND_INDEX = 10;
    private static final int myMAXSUMWRITE_INDEX = 11;
    private static final int myMINIMUMBALANCE_INDEX = 12;
    private static final int myNUMBER_INDEX = 13;
    private static final int myOFFICE_INDEX = 14;
    private static final int myOPENDATE_INDEX = 15;
    private static final int myPASSBOOK_INDEX = 16;
    private static final int myPROLONGATIONDATE_INDEX = 17;
    private static final int mySUBKIND_INDEX = 18;
    private static final int myTYPE_INDEX = 19;
    
    public Account_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myMoney_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.deposits.generated.Money.class, ns2_Money_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Boolean.class, ns4_boolean_TYPE_QNAME);
        ns2_myCurrency_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.deposits.generated.Currency.class, ns2_Currency_TYPE_QNAME);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns3_decimal_TYPE_QNAME);
        ns2_myOffice_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.deposits.generated.Office.class, ns2_Office_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.Account instance = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account();
        com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_accountState_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_accountState_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myACCOUNTSTATE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAccountState((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_balance_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_balance_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBALANCE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBalance((com.rssl.phizicgate.wsgate.services.deposits.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_clientKind_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_clientKind_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCLIENTKIND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setClientKind((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_creditAllowed_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_creditAllowed_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCREDITALLOWED_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCreditAllowed((java.lang.Boolean)member);
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
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCURRENCY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCurrency((com.rssl.phizicgate.wsgate.services.deposits.generated.Currency)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_debitAllowed_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_debitAllowed_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDEBITALLOWED_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDebitAllowed((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_demand_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_demand_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDEMAND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDemand((java.lang.Boolean)member);
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
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
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
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_id_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
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
            if (elementName.equals(ns1_kind_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_kind_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
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
            if (elementName.equals(ns1_maxSumWrite_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_maxSumWrite_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMAXSUMWRITE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMaxSumWrite((com.rssl.phizicgate.wsgate.services.deposits.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_minimumBalance_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_minimumBalance_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMINIMUMBALANCE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMinimumBalance((com.rssl.phizicgate.wsgate.services.deposits.generated.Money)member);
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
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
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
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOFFICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOffice((com.rssl.phizicgate.wsgate.services.deposits.generated.Office)member);
                }
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
            if (elementName.equals(ns1_passbook_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_passbook_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPASSBOOK_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPassbook((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_prolongationDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_prolongationDate_QNAME, reader, context);
                instance.setProlongationDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_subKind_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_subKind_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySUBKIND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSubKind((java.lang.Long)member);
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
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.Account_SOAPBuilder();
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
        com.rssl.phizicgate.wsgate.services.deposits.generated.Account instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.Account)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.Account instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.Account)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getAccountState(), ns1_accountState_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getBalance(), ns1_balance_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getClientKind(), ns1_clientKind_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getCreditAllowed(), ns1_creditAllowed_QNAME, null, writer, context);
        ns2_myCurrency_SOAPSerializer.serialize(instance.getCurrency(), ns1_currency_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getDebitAllowed(), ns1_debitAllowed_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getDemand(), ns1_demand_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getId(), ns1_id_QNAME, null, writer, context);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getInterestRate(), ns1_interestRate_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getKind(), ns1_kind_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getMaxSumWrite(), ns1_maxSumWrite_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getMinimumBalance(), ns1_minimumBalance_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getNumber(), ns1_number_QNAME, null, writer, context);
        ns2_myOffice_SOAPSerializer.serialize(instance.getOffice(), ns1_office_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getOpenDate(), ns1_openDate_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getPassbook(), ns1_passbook_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getProlongationDate(), ns1_prolongationDate_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getSubKind(), ns1_subKind_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getType(), ns1_type_QNAME, null, writer, context);
    }
}
