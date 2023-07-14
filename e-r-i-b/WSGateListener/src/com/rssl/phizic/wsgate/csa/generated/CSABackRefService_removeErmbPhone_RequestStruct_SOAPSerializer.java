// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.csa.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class CSABackRefService_removeErmbPhone_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_ClientTemplate_1_QNAME = new QName("", "ClientTemplate_1");
    private static final javax.xml.namespace.QName ns2_ClientTemplate_TYPE_QNAME = new QName("http://generated.csa.wsgate.phizic.rssl.com", "ClientTemplate");
    private CombinedSerializer ns2_myClientTemplate_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_String_2_QNAME = new QName("", "String_2");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final int myCLIENTTEMPLATE_1_INDEX = 0;
    private static final int mySTRING_2_INDEX = 1;
    
    public CSABackRefService_removeErmbPhone_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myClientTemplate_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.wsgate.csa.generated.ClientTemplate.class, ns2_ClientTemplate_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct instance = new com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct();
        com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        for (int i=0; i<2; i++) {
            elementName = reader.getName();
            if (reader.getState() == XMLReader.END) {
                break;
            }
            if (elementName.equals(ns1_ClientTemplate_1_QNAME)) {
                member = ns2_myClientTemplate_SOAPSerializer.deserialize(ns1_ClientTemplate_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCLIENTTEMPLATE_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setClientTemplate_1((com.rssl.phizic.wsgate.csa.generated.ClientTemplate)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_String_2_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_String_2_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTRING_2_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setString_2((java.lang.String)member);
                }
                reader.nextElementContent();
                continue;
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_String_2_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct instance = (com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct instance = (com.rssl.phizic.wsgate.csa.generated.CSABackRefService_removeErmbPhone_RequestStruct)obj;
        
        ns2_myClientTemplate_SOAPSerializer.serialize(instance.getClientTemplate_1(), ns1_ClientTemplate_1_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getString_2(), ns1_String_2_QNAME, null, writer, context);
    }
}