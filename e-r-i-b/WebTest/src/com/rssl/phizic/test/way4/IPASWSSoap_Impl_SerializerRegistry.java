// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.test.way4;

import com.sun.xml.rpc.client.BasicService;
import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.soap.*;
import com.sun.xml.rpc.soap.SOAPVersion;

import javax.xml.rpc.encoding.*;
import javax.xml.namespace.QName;

public class IPASWSSoap_Impl_SerializerRegistry implements SerializerConstants {
    public IPASWSSoap_Impl_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "GeneratePasswordRsType");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.GeneratePasswordRsType_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.GeneratePasswordRsType.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "verifyOTP");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_verifyOTP_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_verifyOTP_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "verifyOTPResponse");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_verifyOTP_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_verifyOTP_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "verifyPasswordResponse");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_verifyPassword_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_verifyPassword_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "prepareOTP");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_prepareOTP_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_prepareOTP_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "VerifyAttRsType");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.VerifyAttRsType_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.VerifyAttRsType.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "UserInfoType");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.UserInfoType_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.UserInfoType.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "generatePasswordResponse");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_generatePassword_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_generatePassword_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "PrepareOTPRsType");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.PrepareOTPRsType_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.PrepareOTPRsType.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "generatePassword");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_generatePassword_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_generatePassword_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "prepareOTPResponse");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_prepareOTP_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_prepareOTP_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "verifyPassword");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_verifyPassword_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.IPASWSSoap_verifyPassword_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "VerifyRsType");
            CombinedSerializer serializer = new com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping, com.rssl.phizic.test.way4.ws.mock.generated.VerifyRsType.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}
