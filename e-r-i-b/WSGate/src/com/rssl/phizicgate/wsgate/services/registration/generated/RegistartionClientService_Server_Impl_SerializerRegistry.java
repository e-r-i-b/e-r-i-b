// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.registration.generated;

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

public class RegistartionClientService_Server_Impl_SerializerRegistry implements SerializerConstants {
    public RegistartionClientService_Server_Impl_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "ClientState");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.ClientState_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.ClientState.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "Code");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.Code_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.Code.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "update2Response");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update2_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update2_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "Address");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.Address_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.Address.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "cancellation");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_cancellation_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_cancellation_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "cancellationResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_cancellation_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_cancellation_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "register");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_register_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_register_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "Client");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.Client_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.Client.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "User");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.User_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.User.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "updateResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "Office");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.Office_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.Office.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "update");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "registerResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_register_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_register_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "update2");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update2_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.RegistartionClientService_PortType_update2_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "CancelationCallBackImpl");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.CancelationCallBackImpl_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.CancelationCallBackImpl.class, type, serializer);
        }
	    {
            QName type = new QName("http://generated.registration.services.gate.web.phizic.rssl.com", "ClientDocument");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.registration.generated.ClientDocument_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.registration.generated.ClientDocument.class, type, serializer);
        }	    
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}
