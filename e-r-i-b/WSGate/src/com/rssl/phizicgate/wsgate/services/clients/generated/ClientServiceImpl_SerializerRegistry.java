// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.clients.generated;

import com.sun.xml.rpc.client.BasicService;
import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.soap.SOAPVersion;

import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.Deserializer;
import javax.xml.rpc.encoding.Serializer;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;

public class ClientServiceImpl_SerializerRegistry implements SerializerConstants {
    public ClientServiceImpl_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "getClientsByTemplate");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientsByTemplate_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientsByTemplate_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "getClientByIdResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientById_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientById_ResponseStruct.class, type, serializer);
        }
	    {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "ClientDocument");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.ClientDocument_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.ClientDocument.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "Address");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.Address_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.Address.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "getClientsByTemplateResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientsByTemplate_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientsByTemplate_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "getClientByCardNumberResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientByCardNumber_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientByCardNumber_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "getClientById");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientById_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientById_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "getClientByCardNumber");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientByCardNumber_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_getClientByCardNumber_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "Code");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.Code_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.Code.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "Office");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.Office_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.Office.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "Client");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.Client_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.Client.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.clients.services.gate.web.phizic.rssl.com", "ClientState");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.clients.generated.Client_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.clients.generated.ClientState.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}
