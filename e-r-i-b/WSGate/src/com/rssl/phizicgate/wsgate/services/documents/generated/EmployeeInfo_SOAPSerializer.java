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

public class EmployeeInfo_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_employeeOffice_QNAME = new QName("", "employeeOffice");
    private static final javax.xml.namespace.QName ns2_Office_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Office");
    private CombinedSerializer ns2_myOffice_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_login_QNAME = new QName("", "login");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_personName_QNAME = new QName("", "personName");
    private static final javax.xml.namespace.QName ns2_PersonName_TYPE_QNAME = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "PersonName");
    private CombinedSerializer ns2_myPersonName_SOAPSerializer;
    private static final int myEMPLOYEEOFFICE_INDEX = 0;
    private static final int myLOGIN_INDEX = 1;
    private static final int myPERSONNAME_INDEX = 2;
    
    public EmployeeInfo_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myOffice_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.Office.class, ns2_Office_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myPersonName_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.documents.generated.PersonName.class, ns2_PersonName_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo instance = new com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo();
        com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_employeeOffice_QNAME)) {
                member = ns2_myOffice_SOAPSerializer.deserialize(ns1_employeeOffice_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myEMPLOYEEOFFICE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setEmployeeOffice((com.rssl.phizicgate.wsgate.services.documents.generated.Office)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_login_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_login_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myLOGIN_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setLogin((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_personName_QNAME)) {
                member = ns2_myPersonName_SOAPSerializer.deserialize(ns1_personName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myPERSONNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setPersonName((com.rssl.phizicgate.wsgate.services.documents.generated.PersonName)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo instance = (com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo instance = (com.rssl.phizicgate.wsgate.services.documents.generated.EmployeeInfo)obj;
        
        ns2_myOffice_SOAPSerializer.serialize(instance.getEmployeeOffice(), ns1_employeeOffice_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getLogin(), ns1_login_QNAME, null, writer, context);
        ns2_myPersonName_SOAPSerializer.serialize(instance.getPersonName(), ns1_personName_QNAME, null, writer, context);
    }
}
