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

public class CommonRsType_InterfaceSOAPSerializer extends InterfaceSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_GeneratePasswordRsType_TYPE_QNAME = new QName("http://www.openwaygroup.com/iPAS/WS", "GeneratePasswordRsType");
    private CombinedSerializer ns1_myGeneratePasswordRsType_LiteralSerializer;
    private static final javax.xml.namespace.QName ns1_PrepareOTPRsType_TYPE_QNAME = new QName("http://www.openwaygroup.com/iPAS/WS", "PrepareOTPRsType");
    private CombinedSerializer ns1_myPrepareOTPRsType_LiteralSerializer;
    private static final javax.xml.namespace.QName ns1_VerifyAttRsType_TYPE_QNAME = new QName("http://www.openwaygroup.com/iPAS/WS", "VerifyAttRsType");
    private CombinedSerializer ns1_myVerifyAttRsType_LiteralSerializer;
    private static final javax.xml.namespace.QName ns1_VerifyRsType_TYPE_QNAME = new QName("http://www.openwaygroup.com/iPAS/WS", "VerifyRsType");
    private CombinedSerializer ns1_myVerifyRsType_LiteralSerializer;
    private CombinedSerializer ns1_myCommonRsType_LiteralSerializer;
    
    public CommonRsType_InterfaceSOAPSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, encodeType, true, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns1_myGeneratePasswordRsType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.auth.csa.back.integration.ipas.generated.GeneratePasswordRsType.class, ns1_GeneratePasswordRsType_TYPE_QNAME);
        ns1_myGeneratePasswordRsType_LiteralSerializer = ns1_myGeneratePasswordRsType_LiteralSerializer.getInnermostSerializer();
        ns1_myPrepareOTPRsType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.auth.csa.back.integration.ipas.generated.PrepareOTPRsType.class, ns1_PrepareOTPRsType_TYPE_QNAME);
        ns1_myPrepareOTPRsType_LiteralSerializer = ns1_myPrepareOTPRsType_LiteralSerializer.getInnermostSerializer();
        ns1_myVerifyAttRsType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.auth.csa.back.integration.ipas.generated.VerifyAttRsType.class, ns1_VerifyAttRsType_TYPE_QNAME);
        ns1_myVerifyAttRsType_LiteralSerializer = ns1_myVerifyAttRsType_LiteralSerializer.getInnermostSerializer();
        ns1_myVerifyRsType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.auth.csa.back.integration.ipas.generated.VerifyRsType.class, ns1_VerifyRsType_TYPE_QNAME);
        ns1_myVerifyRsType_LiteralSerializer = ns1_myVerifyRsType_LiteralSerializer.getInnermostSerializer();
        QName type = new QName("http://www.openwaygroup.com/iPAS/WS", "CommonRsType");
        CombinedSerializer interfaceSerializer = new com.rssl.auth.csa.back.integration.ipas.generated.CommonRsType_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
        ns1_myCommonRsType_LiteralSerializer = interfaceSerializer.getInnermostSerializer();
        if (ns1_myCommonRsType_LiteralSerializer instanceof Initializable) {
            ((Initializable)ns1_myCommonRsType_LiteralSerializer).initialize(registry);
        }
    }
    
    public java.lang.Object doDeserialize(javax.xml.namespace.QName name, XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        javax.xml.namespace.QName elementType = getType(reader);
        if (elementType != null && elementType.equals(ns1_myGeneratePasswordRsType_LiteralSerializer.getXmlType())) {
            return ns1_myGeneratePasswordRsType_LiteralSerializer.deserialize(name, reader, context);
        } else if (elementType != null && elementType.equals(ns1_myPrepareOTPRsType_LiteralSerializer.getXmlType())) {
            return ns1_myPrepareOTPRsType_LiteralSerializer.deserialize(name, reader, context);
        } else if (elementType != null && elementType.equals(ns1_myVerifyAttRsType_LiteralSerializer.getXmlType())) {
            return ns1_myVerifyAttRsType_LiteralSerializer.deserialize(name, reader, context);
        } else if (elementType != null && elementType.equals(ns1_myVerifyRsType_LiteralSerializer.getXmlType())) {
            return ns1_myVerifyRsType_LiteralSerializer.deserialize(name, reader, context);
        } else if (elementType == null || elementType.equals(ns1_myCommonRsType_LiteralSerializer.getXmlType())) {
            Object obj = ns1_myCommonRsType_LiteralSerializer.deserialize(name, reader, context);
            return obj;
        }
        throw new DeserializationException("soap.unexpectedElementType", new Object[] {"", elementType.toString()});
    }
    
    public void doSerializeInstance(java.lang.Object obj, javax.xml.namespace.QName name, SerializerCallback callback,
        XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.rssl.auth.csa.back.integration.ipas.generated.CommonRsType instance = (com.rssl.auth.csa.back.integration.ipas.generated.CommonRsType)obj;
        
        if (obj instanceof com.rssl.auth.csa.back.integration.ipas.generated.GeneratePasswordRsType) {
            ns1_myGeneratePasswordRsType_LiteralSerializer.serialize(obj, name, callback, writer, context);
        } else if (obj instanceof com.rssl.auth.csa.back.integration.ipas.generated.PrepareOTPRsType) {
            ns1_myPrepareOTPRsType_LiteralSerializer.serialize(obj, name, callback, writer, context);
        } else if (obj instanceof com.rssl.auth.csa.back.integration.ipas.generated.VerifyAttRsType) {
            ns1_myVerifyAttRsType_LiteralSerializer.serialize(obj, name, callback, writer, context);
        } else if (obj instanceof com.rssl.auth.csa.back.integration.ipas.generated.VerifyRsType) {
            ns1_myVerifyRsType_LiteralSerializer.serialize(obj, name, callback, writer, context);
        } else {
            ns1_myCommonRsType_LiteralSerializer.serialize(obj, name, callback, writer, context);
        }
    }
}
