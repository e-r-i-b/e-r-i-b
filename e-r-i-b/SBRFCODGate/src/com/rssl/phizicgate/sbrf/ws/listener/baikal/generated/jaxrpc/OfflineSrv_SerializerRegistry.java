// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.jaxrpc;

import com.sun.xml.rpc.client.BasicService;
import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.soap.*;

import javax.xml.rpc.encoding.*;
import javax.xml.namespace.QName;

public class OfflineSrv_SerializerRegistry implements SerializerConstants {
    public OfflineSrv_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://tempuri.org/", "getXMLmessageResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.jaxrpc.GetXMLmessageResponse_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2, com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.jaxrpc.GetXMLmessageResponse.class, type, serializer);
        }
        {
            QName type = new QName("http://tempuri.org/", "getXMLmessage");
            CombinedSerializer serializer = new com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.jaxrpc.GetXMLmessage_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2, com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.jaxrpc.GetXMLmessage.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}
