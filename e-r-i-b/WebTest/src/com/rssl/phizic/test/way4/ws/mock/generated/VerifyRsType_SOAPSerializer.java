// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.test.way4.ws.mock.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class VerifyRsType_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_STAN_QNAME = new QName("", "STAN");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_status_QNAME = new QName("", "status");
    private static final javax.xml.namespace.QName ns1_token_QNAME = new QName("", "token");
    private static final javax.xml.namespace.QName ns1_userInfo_QNAME = new QName("", "userInfo");
    private static final javax.xml.namespace.QName ns3_UserInfoType_TYPE_QNAME = new QName("http://www.openwaygroup.com/iPAS/WS", "UserInfoType");
    private CombinedSerializer ns3_myUserInfoType_SOAPSerializer;
    private static final int mySTAN_INDEX = 0;
    private static final int mySTATUS_INDEX = 1;
    private static final int myTOKEN_INDEX = 2;
    private static final int myUSERINFO_INDEX = 3;
    
    public VerifyRsType_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns2_string_TYPE_QNAME);
        ns3_myUserInfoType_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.test.way4.ws.mock.generated.UserInfoType.class, ns3_UserInfoType_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType instance = new com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType();
        com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_STAN_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_STAN_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTAN_INDEX, builder);
                    isComplete = false;
                } else {
                    ((com.rssl.phizic.test.way4.ws.mock.generated.CommonRsType)instance).setSTAN((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_status_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_status_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTATUS_INDEX, builder);
                    isComplete = false;
                } else {
                    ((com.rssl.phizic.test.way4.ws.mock.generated.CommonRsType)instance).setStatus((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_token_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_token_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTOKEN_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setToken((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_userInfo_QNAME)) {
                member = ns3_myUserInfoType_SOAPSerializer.deserialize(ns1_userInfo_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myUSERINFO_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setUserInfo((com.rssl.phizic.test.way4.ws.mock.generated.UserInfoType)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType instance = (com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType instance = (com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType)obj;
        
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getSTAN(), ns1_STAN_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getStatus(), ns1_status_QNAME, null, writer, context);
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getToken(), ns1_token_QNAME, null, writer, context);
        ns3_myUserInfoType_SOAPSerializer.serialize(instance.getUserInfo(), ns1_userInfo_QNAME, null, writer, context);
    }
}
