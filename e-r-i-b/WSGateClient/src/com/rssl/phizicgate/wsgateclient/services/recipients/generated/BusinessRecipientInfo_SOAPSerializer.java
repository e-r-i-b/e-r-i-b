// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.recipients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class BusinessRecipientInfo_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_INN_QNAME = new QName("", "INN");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_KPP_QNAME = new QName("", "KPP");
    private static final javax.xml.namespace.QName ns1_account_QNAME = new QName("", "account");
    private static final javax.xml.namespace.QName ns1_bank_QNAME = new QName("", "bank");
    private static final javax.xml.namespace.QName ns3_ResidentBank_TYPE_QNAME = new QName("http://generated.recipients.wsgate.phizic.rssl.com", "ResidentBank");
    private CombinedSerializer ns3_myResidentBank_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_commissionRate_QNAME = new QName("", "commissionRate");
    private static final javax.xml.namespace.QName ns2_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns2_myns2_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final javax.xml.namespace.QName ns1_deptAvailable_QNAME = new QName("", "deptAvailable");
    private static final javax.xml.namespace.QName ns4_boolean_TYPE_QNAME = SOAPConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns1_main_QNAME = new QName("", "main");
    private static final javax.xml.namespace.QName ns1_maxCommissionAmount_QNAME = new QName("", "maxCommissionAmount");
    private static final javax.xml.namespace.QName ns1_minCommissionAmount_QNAME = new QName("", "minCommissionAmount");
    private static final javax.xml.namespace.QName ns1_name_QNAME = new QName("", "name");
    private static final javax.xml.namespace.QName ns1_office_QNAME = new QName("", "office");
    private static final javax.xml.namespace.QName ns3_Office_TYPE_QNAME = new QName("http://generated.recipients.wsgate.phizic.rssl.com", "Office");
    private CombinedSerializer ns3_myOffice_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_payerAccount_QNAME = new QName("", "payerAccount");
    private static final javax.xml.namespace.QName ns1_paymentType_QNAME = new QName("", "paymentType");
    private static final javax.xml.namespace.QName ns1_propsOnline_QNAME = new QName("", "propsOnline");
    private static final javax.xml.namespace.QName ns1_service_QNAME = new QName("", "service");
    private static final javax.xml.namespace.QName ns3_Service_TYPE_QNAME = new QName("http://generated.recipients.wsgate.phizic.rssl.com", "Service");
    private CombinedSerializer ns3_myService_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_synchKey_QNAME = new QName("", "synchKey");
    private static final javax.xml.namespace.QName ns1_transitAccount_QNAME = new QName("", "transitAccount");
    private static final int myINN_INDEX = 0;
    private static final int myKPP_INDEX = 1;
    private static final int myACCOUNT_INDEX = 2;
    private static final int myBANK_INDEX = 3;
    private static final int myCOMMISSIONRATE_INDEX = 4;
    private static final int myDEPTAVAILABLE_INDEX = 5;
    private static final int myDESCRIPTION_INDEX = 6;
    private static final int myMAIN_INDEX = 7;
    private static final int myMAXCOMMISSIONAMOUNT_INDEX = 8;
    private static final int myMINCOMMISSIONAMOUNT_INDEX = 9;
    private static final int myNAME_INDEX = 10;
    private static final int myOFFICE_INDEX = 11;
    private static final int myPAYERACCOUNT_INDEX = 12;
    private static final int myPAYMENTTYPE_INDEX = 13;
    private static final int myPROPSONLINE_INDEX = 14;
    private static final int mySERVICE_INDEX = 15;
    private static final int mySYNCHKEY_INDEX = 16;
    private static final int myTRANSITACCOUNT_INDEX = 17;
    
    public BusinessRecipientInfo_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns2_string_TYPE_QNAME);
        ns3_myResidentBank_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.recipients.generated.ResidentBank.class, ns3_ResidentBank_TYPE_QNAME);
        ns2_myns2_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns2_decimal_TYPE_QNAME);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Boolean.class, ns4_boolean_TYPE_QNAME);
        ns3_myOffice_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.recipients.generated.Office.class, ns3_Office_TYPE_QNAME);
        ns3_myService_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.recipients.generated.Service.class, ns3_Service_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo instance = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo();
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_INN_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_INN_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myINN_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setINN((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_KPP_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_KPP_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myKPP_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setKPP((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_account_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_account_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myACCOUNT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAccount((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_bank_QNAME)) {
                member = ns3_myResidentBank_SOAPSerializer.deserialize(ns1_bank_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBANK_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBank((com.rssl.phizicgate.wsgateclient.services.recipients.generated.ResidentBank)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_commissionRate_QNAME)) {
                member = ns2_myns2_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_commissionRate_QNAME, reader, context);
                instance.setCommissionRate((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_deptAvailable_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_deptAvailable_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDEPTAVAILABLE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDeptAvailable((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_description_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_description_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
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
            if (elementName.equals(ns1_main_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_main_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMAIN_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMain((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_maxCommissionAmount_QNAME)) {
                member = ns2_myns2_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_maxCommissionAmount_QNAME, reader, context);
                instance.setMaxCommissionAmount((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_minCommissionAmount_QNAME)) {
                member = ns2_myns2_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_minCommissionAmount_QNAME, reader, context);
                instance.setMinCommissionAmount((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_name_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_name_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_office_QNAME)) {
                member = ns3_myOffice_SOAPSerializer.deserialize(ns1_office_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOFFICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOffice((com.rssl.phizicgate.wsgateclient.services.recipients.generated.Office)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_payerAccount_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_payerAccount_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPAYERACCOUNT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPayerAccount((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_paymentType_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_paymentType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPAYMENTTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPaymentType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_propsOnline_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_propsOnline_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPROPSONLINE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPropsOnline((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_service_QNAME)) {
                member = ns3_myService_SOAPSerializer.deserialize(ns1_service_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySERVICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setService((com.rssl.phizicgate.wsgateclient.services.recipients.generated.Service)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_synchKey_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_synchKey_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySYNCHKEY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSynchKey((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_transitAccount_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_transitAccount_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTRANSITACCOUNT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTransitAccount((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo instance = (com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo instance = (com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo)obj;
        
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getINN(), ns1_INN_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getKPP(), ns1_KPP_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getAccount(), ns1_account_QNAME, null, writer, context);
        ns3_myResidentBank_SOAPSerializer.serialize(instance.getBank(), ns1_bank_QNAME, null, writer, context);
        ns2_myns2_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getCommissionRate(), ns1_commissionRate_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getDeptAvailable(), ns1_deptAvailable_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getMain(), ns1_main_QNAME, null, writer, context);
        ns2_myns2_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getMaxCommissionAmount(), ns1_maxCommissionAmount_QNAME, null, writer, context);
        ns2_myns2_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getMinCommissionAmount(), ns1_minCommissionAmount_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
        ns3_myOffice_SOAPSerializer.serialize(instance.getOffice(), ns1_office_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getPayerAccount(), ns1_payerAccount_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getPaymentType(), ns1_paymentType_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getPropsOnline(), ns1_propsOnline_QNAME, null, writer, context);
        ns3_myService_SOAPSerializer.serialize(instance.getService(), ns1_service_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getSynchKey(), ns1_synchKey_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getTransitAccount(), ns1_transitAccount_QNAME, null, writer, context);
    }
}
