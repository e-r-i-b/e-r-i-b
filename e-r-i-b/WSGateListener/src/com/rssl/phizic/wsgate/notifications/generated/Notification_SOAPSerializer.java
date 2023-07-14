// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.notifications.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Notification_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_accountNumber_QNAME = new QName("", "accountNumber");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_applicationKey_QNAME = new QName("", "applicationKey");
    private static final javax.xml.namespace.QName ns1_applicationKind_QNAME = new QName("", "applicationKind");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_currentRest_QNAME = new QName("", "currentRest");
    private static final javax.xml.namespace.QName ns4_double_TYPE_QNAME = SOAPConstants.QNAME_TYPE_DOUBLE;
    private CombinedSerializer ns4_myns4__double__java_lang_Double_Double_Serializer;
    private static final javax.xml.namespace.QName ns1_dateCreated_QNAME = new QName("", "dateCreated");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_documentId_QNAME = new QName("", "documentId");
    private static final javax.xml.namespace.QName ns1_error_QNAME = new QName("", "error");
    private static final javax.xml.namespace.QName ns1_gstatus_QNAME = new QName("", "gstatus");
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns3_long_TYPE_QNAME = SchemaConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns3_myns3__long__long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_minRest_QNAME = new QName("", "minRest");
    private static final javax.xml.namespace.QName ns1_newRest_QNAME = new QName("", "newRest");
    private static final javax.xml.namespace.QName ns1_notificationObjectType_QNAME = new QName("", "notificationObjectType");
    private static final javax.xml.namespace.QName ns3_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer ns3_myns3__int__int_Int_Serializer;
    private static final javax.xml.namespace.QName ns1_objectNumber_QNAME = new QName("", "objectNumber");
    private static final javax.xml.namespace.QName ns1_objectType_QNAME = new QName("", "objectType");
    private static final javax.xml.namespace.QName ns1_oldRest_QNAME = new QName("", "oldRest");
    private static final javax.xml.namespace.QName ns1_rest_QNAME = new QName("", "rest");
    private static final javax.xml.namespace.QName ns1_transactionSum_QNAME = new QName("", "transactionSum");
    private static final javax.xml.namespace.QName ns1_type_QNAME = new QName("", "type");
    private static final int myACCOUNTNUMBER_INDEX = 0;
    private static final int myAPPLICATIONKEY_INDEX = 1;
    private static final int myAPPLICATIONKIND_INDEX = 2;
    private static final int myCURRENTREST_INDEX = 3;
    private static final int myDATECREATED_INDEX = 4;
    private static final int myDOCUMENTID_INDEX = 5;
    private static final int myERROR_INDEX = 6;
    private static final int myGSTATUS_INDEX = 7;
    private static final int myID_INDEX = 8;
    private static final int myMINREST_INDEX = 9;
    private static final int myNEWREST_INDEX = 10;
    private static final int myNOTIFICATIONOBJECTTYPE_INDEX = 11;
    private static final int myOBJECTNUMBER_INDEX = 12;
    private static final int myOBJECTTYPE_INDEX = 13;
    private static final int myOLDREST_INDEX = 14;
    private static final int myREST_INDEX = 15;
    private static final int myTRANSACTIONSUM_INDEX = 16;
    private static final int myTYPE_INDEX = 17;
    
    public Notification_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
        ns4_myns4__double__java_lang_Double_Double_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Double.class, ns4_double_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns3_myns3__long__long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, long.class, ns3_long_TYPE_QNAME);
        ns3_myns3__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, int.class, ns3_int_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.notifications.generated.Notification instance = new com.rssl.phizic.wsgate.notifications.generated.Notification();
        com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_accountNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_accountNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myACCOUNTNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAccountNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_applicationKey_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_applicationKey_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAPPLICATIONKEY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setApplicationKey((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_applicationKind_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_applicationKind_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAPPLICATIONKIND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setApplicationKind((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_currentRest_QNAME)) {
                member = ns4_myns4__double__java_lang_Double_Double_Serializer.deserialize(ns1_currentRest_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCURRENTREST_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCurrentRest((java.lang.Double)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_dateCreated_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_dateCreated_QNAME, reader, context);
                instance.setDateCreated((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_documentId_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_documentId_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDOCUMENTID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDocumentId((java.lang.String)member);
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
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
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
            if (elementName.equals(ns1_gstatus_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_gstatus_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myGSTATUS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setGstatus((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_id_QNAME, reader, context);
                instance.setId(((Long)member).longValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_minRest_QNAME)) {
                member = ns4_myns4__double__java_lang_Double_Double_Serializer.deserialize(ns1_minRest_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMINREST_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMinRest((java.lang.Double)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_newRest_QNAME)) {
                member = ns4_myns4__double__java_lang_Double_Double_Serializer.deserialize(ns1_newRest_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myNEWREST_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setNewRest((java.lang.Double)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_notificationObjectType_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_notificationObjectType_QNAME, reader, context);
                instance.setNotificationObjectType(((java.lang.Integer)member).intValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_objectNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_objectNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOBJECTNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setObjectNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_objectType_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_objectType_QNAME, reader, context);
                instance.setObjectType(((java.lang.Integer)member).intValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_oldRest_QNAME)) {
                member = ns4_myns4__double__java_lang_Double_Double_Serializer.deserialize(ns1_oldRest_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOLDREST_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOldRest((java.lang.Double)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_rest_QNAME)) {
                member = ns4_myns4__double__java_lang_Double_Double_Serializer.deserialize(ns1_rest_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myREST_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRest((java.lang.Double)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_transactionSum_QNAME)) {
                member = ns4_myns4__double__java_lang_Double_Double_Serializer.deserialize(ns1_transactionSum_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTRANSACTIONSUM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTransactionSum((java.lang.Double)member);
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
                        builder = new com.rssl.phizic.wsgate.notifications.generated.Notification_SOAPBuilder();
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
        com.rssl.phizic.wsgate.notifications.generated.Notification instance = (com.rssl.phizic.wsgate.notifications.generated.Notification)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.notifications.generated.Notification instance = (com.rssl.phizic.wsgate.notifications.generated.Notification)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getAccountNumber(), ns1_accountNumber_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getApplicationKey(), ns1_applicationKey_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getApplicationKind(), ns1_applicationKind_QNAME, null, writer, context);
        ns4_myns4__double__java_lang_Double_Double_Serializer.serialize(instance.getCurrentRest(), ns1_currentRest_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getDateCreated(), ns1_dateCreated_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDocumentId(), ns1_documentId_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getError(), ns1_error_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getGstatus(), ns1_gstatus_QNAME, null, writer, context);
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getId()), ns1_id_QNAME, null, writer, context);
        ns4_myns4__double__java_lang_Double_Double_Serializer.serialize(instance.getMinRest(), ns1_minRest_QNAME, null, writer, context);
        ns4_myns4__double__java_lang_Double_Double_Serializer.serialize(instance.getNewRest(), ns1_newRest_QNAME, null, writer, context);
        ns3_myns3__int__int_Int_Serializer.serialize(new java.lang.Integer(instance.getNotificationObjectType()), ns1_notificationObjectType_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getObjectNumber(), ns1_objectNumber_QNAME, null, writer, context);
        ns3_myns3__int__int_Int_Serializer.serialize(new java.lang.Integer(instance.getObjectType()), ns1_objectType_QNAME, null, writer, context);
        ns4_myns4__double__java_lang_Double_Double_Serializer.serialize(instance.getOldRest(), ns1_oldRest_QNAME, null, writer, context);
        ns4_myns4__double__java_lang_Double_Double_Serializer.serialize(instance.getRest(), ns1_rest_QNAME, null, writer, context);
        ns4_myns4__double__java_lang_Double_Double_Serializer.serialize(instance.getTransactionSum(), ns1_transactionSum_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getType(), ns1_type_QNAME, null, writer, context);
    }
}
