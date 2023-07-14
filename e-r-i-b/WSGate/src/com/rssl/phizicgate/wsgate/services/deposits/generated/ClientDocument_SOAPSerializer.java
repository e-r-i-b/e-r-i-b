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

public class ClientDocument_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_docIdentify_QNAME = new QName("", "docIdentify");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_docIssueBy_QNAME = new QName("", "docIssueBy");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_docIssueByCode_QNAME = new QName("", "docIssueByCode");
    private static final javax.xml.namespace.QName ns1_docIssueDate_QNAME = new QName("", "docIssueDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_docNumber_QNAME = new QName("", "docNumber");
    private static final javax.xml.namespace.QName ns1_docSeries_QNAME = new QName("", "docSeries");
    private static final javax.xml.namespace.QName ns1_docTimeUpDate_QNAME = new QName("", "docTimeUpDate");
    private static final javax.xml.namespace.QName ns1_docTypeName_QNAME = new QName("", "docTypeName");
    private static final javax.xml.namespace.QName ns1_documentType_QNAME = new QName("", "documentType");
    private static final javax.xml.namespace.QName ns1_paperKind_QNAME = new QName("", "paperKind");
    private static final javax.xml.namespace.QName ns4_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns4_myns4__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_personId_QNAME = new QName("", "personId");
    private static final int myDOCIDENTIFY_INDEX = 0;
    private static final int myDOCISSUEBY_INDEX = 1;
    private static final int myDOCISSUEBYCODE_INDEX = 2;
    private static final int myDOCISSUEDATE_INDEX = 3;
    private static final int myDOCNUMBER_INDEX = 4;
    private static final int myDOCSERIES_INDEX = 5;
    private static final int myDOCTIMEUPDATE_INDEX = 6;
    private static final int myDOCTYPENAME_INDEX = 7;
    private static final int myDOCUMENTTYPE_INDEX = 8;
    private static final int myPAPERKIND_INDEX = 9;
    private static final int myPERSONID_INDEX = 10;
    
    public ClientDocument_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns4_myns4__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns4_long_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument instance = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument();
        com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_docIdentify_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_docIdentify_QNAME, reader, context);
                instance.setDocIdentify(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_docIssueBy_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_docIssueBy_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDOCISSUEBY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDocIssueBy((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_docIssueByCode_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_docIssueByCode_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDOCISSUEBYCODE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDocIssueByCode((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_docIssueDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_docIssueDate_QNAME, reader, context);
                instance.setDocIssueDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_docNumber_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_docNumber_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDOCNUMBER_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDocNumber((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_docSeries_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_docSeries_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDOCSERIES_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDocSeries((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_docTimeUpDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_docTimeUpDate_QNAME, reader, context);
                instance.setDocTimeUpDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_docTypeName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_docTypeName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDOCTYPENAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDocTypeName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_documentType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_documentType_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDOCUMENTTYPE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDocumentType((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_paperKind_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_paperKind_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPAPERKIND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPaperKind((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_personId_QNAME)) {
                member = ns4_myns4__long__java_lang_Long_Long_Serializer.deserialize(ns1_personId_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPERSONID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPersonId((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.ClientDocument)obj;
        
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isDocIdentify()), ns1_docIdentify_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDocIssueBy(), ns1_docIssueBy_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDocIssueByCode(), ns1_docIssueByCode_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getDocIssueDate(), ns1_docIssueDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDocNumber(), ns1_docNumber_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDocSeries(), ns1_docSeries_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getDocTimeUpDate(), ns1_docTimeUpDate_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDocTypeName(), ns1_docTypeName_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDocumentType(), ns1_documentType_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getPaperKind(), ns1_paperKind_QNAME, null, writer, context);
        ns4_myns4__long__java_lang_Long_Long_Serializer.serialize(instance.getPersonId(), ns1_personId_QNAME, null, writer, context);
    }
}
