// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class GateConfiguration_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_connectMode_QNAME = new QName("", "connectMode");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_connectionTimeout_QNAME = new QName("", "connectionTimeout");
    private static final javax.xml.namespace.QName ns5_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns5_myns5__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_userName_QNAME = new QName("", "userName");
    private static final int myCONNECTMODE_INDEX = 0;
    private static final int myCONNECTIONTIMEOUT_INDEX = 1;
    private static final int myUSERNAME_INDEX = 2;
    
    public GateConfiguration_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns5_myns5__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns5_long_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration instance = new com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration();
        com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_connectMode_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_connectMode_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCONNECTMODE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setConnectMode((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_connectionTimeout_QNAME)) {
                member = ns5_myns5__long__java_lang_Long_Long_Serializer.deserialize(ns1_connectionTimeout_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCONNECTIONTIMEOUT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setConnectionTimeout((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_userName_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_userName_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myUSERNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setUserName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration instance = (com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration instance = (com.rssl.phizicgate.wsgate.services.utils.generated.GateConfiguration)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getConnectMode(), ns1_connectMode_QNAME, null, writer, context);
        ns5_myns5__long__java_lang_Long_Long_Serializer.serialize(instance.getConnectionTimeout(), ns1_connectionTimeout_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getUserName(), ns1_userName_QNAME, null, writer, context);
    }
}