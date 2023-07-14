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

public class Field_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_businessSubType_QNAME = new QName("", "businessSubType");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_defaultValue_QNAME = new QName("", "defaultValue");
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns1_editable_QNAME = new QName("", "editable");
    private static final javax.xml.namespace.QName ns4_boolean_TYPE_QNAME = SOAPConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_error_QNAME = new QName("", "error");
    private static final javax.xml.namespace.QName ns1_extendedDescId_QNAME = new QName("", "extendedDescId");
    private static final javax.xml.namespace.QName ns1_externalId_QNAME = new QName("", "externalId");
    private static final javax.xml.namespace.QName ns1_fieldValidationRules_QNAME = new QName("", "fieldValidationRules");
    private static final javax.xml.namespace.QName ns5_list_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_LIST;
    private CombinedSerializer ns5_myns5_list__CollectionInterfaceSerializer;
    private static final javax.xml.namespace.QName ns1_graphicTemplateName_QNAME = new QName("", "graphicTemplateName");
    private static final javax.xml.namespace.QName ns1_groupName_QNAME = new QName("", "groupName");
    private static final javax.xml.namespace.QName ns1_hideInConfirmation_QNAME = new QName("", "hideInConfirmation");
    private static final javax.xml.namespace.QName ns1_hint_QNAME = new QName("", "hint");
    private static final javax.xml.namespace.QName ns1_key_QNAME = new QName("", "key");
    private static final javax.xml.namespace.QName ns1_linesNumber_QNAME = new QName("", "linesNumber");
    private static final javax.xml.namespace.QName ns3_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer ns3_myns3__int__int_Int_Serializer;
    private static final javax.xml.namespace.QName ns1_mainSum_QNAME = new QName("", "mainSum");
    private static final javax.xml.namespace.QName ns1_mask_QNAME = new QName("", "mask");
    private static final javax.xml.namespace.QName ns1_maxLength_QNAME = new QName("", "maxLength");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_minLength_QNAME = new QName("", "minLength");
    private static final javax.xml.namespace.QName ns1_name_QNAME = new QName("", "name");
    private static final javax.xml.namespace.QName ns1_numberPrecision_QNAME = new QName("", "numberPrecision");
    private static final javax.xml.namespace.QName ns4_int_TYPE_QNAME = SOAPConstants.QNAME_TYPE_INT;
    private CombinedSerializer ns4_myns4__int__java_lang_Integer_Int_Serializer;
    private static final javax.xml.namespace.QName ns1_period_QNAME = new QName("", "period");
    private static final javax.xml.namespace.QName ns1_popupHint_QNAME = new QName("", "popupHint");
    private static final javax.xml.namespace.QName ns1_required_QNAME = new QName("", "required");
    private static final javax.xml.namespace.QName ns1_requiredForBill_QNAME = new QName("", "requiredForBill");
    private static final javax.xml.namespace.QName ns1_requiredForConformation_QNAME = new QName("", "requiredForConformation");
    private static final javax.xml.namespace.QName ns1_saveInTemplate_QNAME = new QName("", "saveInTemplate");
    private static final javax.xml.namespace.QName ns1_type_QNAME = new QName("", "type");
    private static final javax.xml.namespace.QName ns1_value_QNAME = new QName("", "value");
    private static final javax.xml.namespace.QName ns3_anyType_TYPE_QNAME = SchemaConstants.QNAME_TYPE_URTYPE;
    private CombinedSerializer ns3_myns3_anyType__DynamicSerializer;
    private static final javax.xml.namespace.QName ns1_values_QNAME = new QName("", "values");
    private static final javax.xml.namespace.QName ns1_visible_QNAME = new QName("", "visible");
    private static final int myBUSINESSSUBTYPE_INDEX = 0;
    private static final int myDEFAULTVALUE_INDEX = 1;
    private static final int myDESCRIPTION_INDEX = 2;
    private static final int myEDITABLE_INDEX = 3;
    private static final int myERROR_INDEX = 4;
    private static final int myEXTENDEDDESCID_INDEX = 5;
    private static final int myEXTERNALID_INDEX = 6;
    private static final int myFIELDVALIDATIONRULES_INDEX = 7;
    private static final int myGRAPHICTEMPLATENAME_INDEX = 8;
    private static final int myGROUPNAME_INDEX = 9;
    private static final int myHIDEINCONFIRMATION_INDEX = 10;
    private static final int myHINT_INDEX = 11;
    private static final int myKEY_INDEX = 12;
    private static final int myLINESNUMBER_INDEX = 13;
    private static final int myMAINSUM_INDEX = 14;
    private static final int myMASK_INDEX = 15;
    private static final int myMAXLENGTH_INDEX = 16;
    private static final int myMINLENGTH_INDEX = 17;
    private static final int myNAME_INDEX = 18;
    private static final int myNUMBERPRECISION_INDEX = 19;
    private static final int myPERIOD_INDEX = 20;
    private static final int myPOPUPHINT_INDEX = 21;
    private static final int myREQUIRED_INDEX = 22;
    private static final int myREQUIREDFORBILL_INDEX = 23;
    private static final int myREQUIREDFORCONFORMATION_INDEX = 24;
    private static final int mySAVEINTEMPLATE_INDEX = 25;
    private static final int myTYPE_INDEX = 26;
    private static final int myVALUE_INDEX = 27;
    private static final int myVALUES_INDEX = 28;
    private static final int myVISIBLE_INDEX = 29;
    
    public Field_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Boolean.class, ns4_boolean_TYPE_QNAME);
        ns5_myns5_list__CollectionInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.List.class, ns5_list_TYPE_QNAME);
        ns3_myns3__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, int.class, ns3_int_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
        ns4_myns4__int__java_lang_Integer_Int_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Integer.class, ns4_int_TYPE_QNAME);
        ns3_myns3_anyType__DynamicSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Object.class, ns3_anyType_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.Field instance = new com.rssl.phizicgate.wsgate.services.documents.generated.Field();
        com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_businessSubType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_businessSubType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myBUSINESSSUBTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setBusinessSubType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_defaultValue_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_defaultValue_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDEFAULTVALUE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDefaultValue((java.lang.String)member);
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
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
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
            if (elementName.equals(ns1_editable_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_editable_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myEDITABLE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setEditable((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_error_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_error_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myERROR_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setError((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_extendedDescId_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_extendedDescId_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myEXTENDEDDESCID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setExtendedDescId((java.lang.String)member);
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
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
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
            if (elementName.equals(ns1_fieldValidationRules_QNAME)) {
                member = ns5_myns5_list__CollectionInterfaceSerializer.deserialize(ns1_fieldValidationRules_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFIELDVALIDATIONRULES_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFieldValidationRules((java.util.List)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_graphicTemplateName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_graphicTemplateName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myGRAPHICTEMPLATENAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setGraphicTemplateName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_groupName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_groupName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myGROUPNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setGroupName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_hideInConfirmation_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_hideInConfirmation_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myHIDEINCONFIRMATION_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setHideInConfirmation((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_hint_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_hint_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myHINT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setHint((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_key_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_key_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myKEY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setKey((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_linesNumber_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_linesNumber_QNAME, reader, context);
                instance.setLinesNumber(((java.lang.Integer)member).intValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_mainSum_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_mainSum_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMAINSUM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMainSum((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_mask_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_mask_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMASK_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMask((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_maxLength_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_maxLength_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMAXLENGTH_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMaxLength((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_minLength_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_minLength_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMINLENGTH_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMinLength((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_name_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_name_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
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
            if (elementName.equals(ns1_numberPrecision_QNAME)) {
                member = ns4_myns4__int__java_lang_Integer_Int_Serializer.deserialize(ns1_numberPrecision_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myNUMBERPRECISION_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setNumberPrecision((java.lang.Integer)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_period_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_period_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPERIOD_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPeriod((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_popupHint_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_popupHint_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPOPUPHINT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPopupHint((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_required_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_required_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREQUIRED_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRequired((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_requiredForBill_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_requiredForBill_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREQUIREDFORBILL_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRequiredForBill((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_requiredForConformation_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_requiredForConformation_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREQUIREDFORCONFORMATION_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRequiredForConformation((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_saveInTemplate_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_saveInTemplate_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySAVEINTEMPLATE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSaveInTemplate((java.lang.Boolean)member);
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
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
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
            if (elementName.equals(ns1_value_QNAME)) {
                member = ns3_myns3_anyType__DynamicSerializer.deserialize(ns1_value_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myVALUE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setValue((java.lang.Object)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_values_QNAME)) {
                member = ns5_myns5_list__CollectionInterfaceSerializer.deserialize(ns1_values_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myVALUES_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setValues((java.util.List)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_visible_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_visible_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Field_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myVISIBLE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setVisible((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.Field instance = (com.rssl.phizicgate.wsgate.services.documents.generated.Field)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.Field instance = (com.rssl.phizicgate.wsgate.services.documents.generated.Field)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getBusinessSubType(), ns1_businessSubType_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDefaultValue(), ns1_defaultValue_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getEditable(), ns1_editable_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getError(), ns1_error_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getExtendedDescId(), ns1_extendedDescId_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getExternalId(), ns1_externalId_QNAME, null, writer, context);
        ns5_myns5_list__CollectionInterfaceSerializer.serialize(instance.getFieldValidationRules(), ns1_fieldValidationRules_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getGraphicTemplateName(), ns1_graphicTemplateName_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getGroupName(), ns1_groupName_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getHideInConfirmation(), ns1_hideInConfirmation_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getHint(), ns1_hint_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getKey(), ns1_key_QNAME, null, writer, context);
        ns3_myns3__int__int_Int_Serializer.serialize(new java.lang.Integer(instance.getLinesNumber()), ns1_linesNumber_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getMainSum(), ns1_mainSum_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMask(), ns1_mask_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getMaxLength(), ns1_maxLength_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getMinLength(), ns1_minLength_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
        ns4_myns4__int__java_lang_Integer_Int_Serializer.serialize(instance.getNumberPrecision(), ns1_numberPrecision_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPeriod(), ns1_period_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getPopupHint(), ns1_popupHint_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getRequired(), ns1_required_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getRequiredForBill(), ns1_requiredForBill_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getRequiredForConformation(), ns1_requiredForConformation_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getSaveInTemplate(), ns1_saveInTemplate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getType(), ns1_type_QNAME, null, writer, context);
        ns3_myns3_anyType__DynamicSerializer.serialize(instance.getValue(), ns1_value_QNAME, null, writer, context);
        ns5_myns5_list__CollectionInterfaceSerializer.serialize(instance.getValues(), ns1_values_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getVisible(), ns1_visible_QNAME, null, writer, context);
    }
}
