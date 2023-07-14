// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.person.update.generated;

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

public class UpdatePersonService_Server_Impl_SerializerRegistry implements SerializerConstants {
    public UpdatePersonService_Server_Impl_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "CancelationCallBackImpl");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.CancelationCallBackImpl_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.CancelationCallBackImpl.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "updateState2Response");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_updateState2_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_updateState2_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "updateStateResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_updateState_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_updateState_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "Currency");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.Currency_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.Currency.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "updateState2");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_updateState2_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_updateState2_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "updateState");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_updateState_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_updateState_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "lockOnUnlock");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_lockOnUnlock_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_lockOnUnlock_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "Money");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.Money_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.Money.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "lockOnUnlockResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_lockOnUnlock_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.UpdatePersonService_PortType_lockOnUnlock_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://clients.wsgate.phizic.rssl.com", "ClientState");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgateclient.services.person.update.generated.ClientState_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgateclient.services.person.update.generated.ClientState.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}