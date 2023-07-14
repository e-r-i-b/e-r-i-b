// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.wsgate.clients.backrefservice;

import com.sun.xml.rpc.client.BasicService;
import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.rpc.*;
import javax.xml.rpc.encoding.*;
import javax.xml.namespace.QName;

public class BackRefClientServiceImpl_SerializerRegistry implements SerializerConstants {
    public BackRefClientServiceImpl_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "Client");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.Client_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.Client.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientDepartmentCodeResponse");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "ClientDocument");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.ClientDocument_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.ClientDocument.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "Address");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.Address_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.Address.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByIdResponse");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "ClientState");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.ClientState_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.ClientState.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientCreationTypeResponse");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByFIOAndDocResponse");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientDepartmentCode");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientDepartmentCode_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientByFIOAndDoc");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientByFIOAndDoc_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientCreationType");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientCreationType_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "getClientById");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService_getClientById_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "__forGenerateClientDocumentResponse");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.backrefservice.clients.wsgate.phizic.rssl.com", "__forGenerateClientDocument");
            CombinedSerializer serializer = new com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.wsgate.clients.backrefservice.generated.BackRefClientService___forGenerateClientDocument_RequestStruct.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}
