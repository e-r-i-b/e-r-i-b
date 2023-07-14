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

public class DebtImpl_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_accountNumber_QNAME = new QName("", "accountNumber");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_code_QNAME = new QName("", "code");
    private static final javax.xml.namespace.QName ns1_description_QNAME = new QName("", "description");
    private static final javax.xml.namespace.QName ns1_fixed_QNAME = new QName("", "fixed");
    private static final javax.xml.namespace.QName ns4_boolean_TYPE_QNAME = SOAPConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_lastPayDate_QNAME = new QName("", "lastPayDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_period_QNAME = new QName("", "period");
    private static final javax.xml.namespace.QName ns1_rows_QNAME = new QName("", "rows");
    private static final javax.xml.namespace.QName ns5_list_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_LIST;
    private CombinedSerializer ns5_myns5_list__CollectionInterfaceSerializer;
    private static final int myACCOUNTNUMBER_INDEX = 0;
    private static final int myCODE_INDEX = 1;
    private static final int myDESCRIPTION_INDEX = 2;
    private static final int myFIXED_INDEX = 3;
    private static final int myLASTPAYDATE_INDEX = 4;
    private static final int myPERIOD_INDEX = 5;
    private static final int myROWS_INDEX = 6;
    
    public DebtImpl_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Boolean.class, ns4_boolean_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns5_myns5_list__CollectionInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.List.class, ns5_list_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.DebtImpl instance = new com.rssl.phizic.wsgate.listener.generated.DebtImpl();
        com.rssl.phizic.wsgate.listener.generated.DebtImpl_SOAPBuilder builder = null;
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
                        builder = new com.rssl.phizic.wsgate.listener.generated.DebtImpl_SOAPBuilder();
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
            if (elementName.equals(ns1_code_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_code_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.DebtImpl_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCODE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCode((java.lang.String)member);
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
                        builder = new com.rssl.phizic.wsgate.listener.generated.DebtImpl_SOAPBuilder();
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
            if (elementName.equals(ns1_fixed_QNAME)) {
                member = ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.deserialize(ns1_fixed_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.DebtImpl_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFIXED_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFixed((java.lang.Boolean)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_lastPayDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_lastPayDate_QNAME, reader, context);
                instance.setLastPayDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_period_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_period_QNAME, reader, context);
                instance.setPeriod((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_rows_QNAME)) {
                member = ns5_myns5_list__CollectionInterfaceSerializer.deserialize(ns1_rows_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.listener.generated.DebtImpl_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myROWS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRows((java.util.List)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.DebtImpl instance = (com.rssl.phizic.wsgate.listener.generated.DebtImpl)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.listener.generated.DebtImpl instance = (com.rssl.phizic.wsgate.listener.generated.DebtImpl)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getAccountNumber(), ns1_accountNumber_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getCode(), ns1_code_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        ns4_myns4__boolean__java_lang_Boolean_Boolean_Serializer.serialize(instance.getFixed(), ns1_fixed_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getLastPayDate(), ns1_lastPayDate_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getPeriod(), ns1_period_QNAME, null, writer, context);
        ns5_myns5_list__CollectionInterfaceSerializer.serialize(instance.getRows(), ns1_rows_QNAME, null, writer, context);
    }
}
