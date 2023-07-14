// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.monitoring.generated;

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

public class RunGateMonitoringServiceImpl_SerializerRegistry implements SerializerConstants {
    public RunGateMonitoringServiceImpl_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "run");
            CombinedSerializer serializer = new com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService_run_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService_run_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "MonitoringStateParameters");
            CombinedSerializer serializer = new com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringStateParameters_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringStateParameters.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "__forGenerateMonitoringStateParametersResponse");
            CombinedSerializer serializer = new com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "MonitoringParameters");
            CombinedSerializer serializer = new com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringParameters_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringParameters.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "__forGenerateMonitoringStateParameters");
            CombinedSerializer serializer = new com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService___forGenerateMonitoringStateParameters_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.monitoring.services.gate.web.phizic.rssl.com", "runResponse");
            CombinedSerializer serializer = new com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService_run_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService_run_ResponseStruct.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}