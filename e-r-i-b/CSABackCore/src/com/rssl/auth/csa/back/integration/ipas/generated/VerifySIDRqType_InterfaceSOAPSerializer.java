// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.auth.csa.back.integration.ipas.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class VerifySIDRqType_InterfaceSOAPSerializer extends InterfaceSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_VerifySPRqType_TYPE_QNAME = new QName("http://www.openwaygroup.com/iPAS/WS", "VerifySPRqType");
    private CombinedSerializer ns1_myVerifySPRqType_LiteralSerializer;
    private CombinedSerializer ns1_myVerifySIDRqType_LiteralSerializer;
    
    public VerifySIDRqType_InterfaceSOAPSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, encodeType, true, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns1_myVerifySPRqType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.auth.csa.back.integration.ipas.generated.VerifySPRqType.class, ns1_VerifySPRqType_TYPE_QNAME);
        ns1_myVerifySPRqType_LiteralSerializer = ns1_myVerifySPRqType_LiteralSerializer.getInnermostSerializer();
        QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "VerifySIDRqType");
        CombinedSerializer interfaceSerializer = new com.rssl.auth.csa.back.integration.ipas.generated.VerifySIDRqType_LiteralSerializer(type, "", ENCODE_TYPE);
        ns1_myVerifySIDRqType_LiteralSerializer = interfaceSerializer.getInnermostSerializer();
        if (ns1_myVerifySIDRqType_LiteralSerializer instanceof Initializable) {
            ((Initializable)ns1_myVerifySIDRqType_LiteralSerializer).initialize(registry);
        }
    }
    
    public java.lang.Object doDeserialize(javax.xml.namespace.QName name, XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        javax.xml.namespace.QName elementType = getType(reader);
        if (elementType != null && elementType.equals(ns1_myVerifySPRqType_LiteralSerializer.getXmlType())) {
            return ns1_myVerifySPRqType_LiteralSerializer.deserialize(name, reader, context);
        } else if (elementType == null || elementType.equals(ns1_myVerifySIDRqType_LiteralSerializer.getXmlType())) {
            Object obj = ns1_myVerifySIDRqType_LiteralSerializer.deserialize(name, reader, context);
            return obj;
        }
        throw new DeserializationException("soap.unexpectedElementType", new Object[] {"", elementType.toString()});
    }
    
    public void doSerializeInstance(java.lang.Object obj, javax.xml.namespace.QName name, SerializerCallback callback,
        XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.rssl.auth.csa.back.integration.ipas.generated.VerifySIDRqType instance = (com.rssl.auth.csa.back.integration.ipas.generated.VerifySIDRqType)obj;
        
        if (obj instanceof com.rssl.auth.csa.back.integration.ipas.generated.VerifySPRqType) {
            ns1_myVerifySPRqType_LiteralSerializer.serialize(obj, name, callback, writer, context);
        } else {
            ns1_myVerifySIDRqType_LiteralSerializer.serialize(obj, name, callback, writer, context);
        }
    }
}
