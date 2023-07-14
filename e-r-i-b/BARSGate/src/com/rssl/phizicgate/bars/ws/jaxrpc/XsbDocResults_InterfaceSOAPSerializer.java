// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.streaming.*;

import javax.xml.namespace.QName;

public class XsbDocResults_InterfaceSOAPSerializer extends InterfaceSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_XsbChecksDocResults_TYPE_QNAME = new QName("http://checks.xsb.webservices.bars.sbrf", "XsbChecksDocResults");
    private CombinedSerializer ns1_myXsbChecksDocResults_LiteralSerializer;
    private static final javax.xml.namespace.QName ns2_XsbRemoteClientNameExtendedResult_TYPE_QNAME = new QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameExtendedResult");
    private CombinedSerializer ns2_myXsbRemoteClientNameExtendedResult_LiteralSerializer;
    private static final javax.xml.namespace.QName ns2_XsbRemoteClientNameResult_TYPE_QNAME = new QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameResult");
    private CombinedSerializer ns2_myXsbRemoteClientNameResult_LiteralSerializer;
    private CombinedSerializer ns3_myXsbDocResults_LiteralSerializer;
    
    public XsbDocResults_InterfaceSOAPSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, encodeType, true, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns1_myXsbChecksDocResults_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizicgate.bars.ws.jaxrpc.XsbChecksDocResults.class, ns1_XsbChecksDocResults_TYPE_QNAME);
        ns1_myXsbChecksDocResults_LiteralSerializer = ns1_myXsbChecksDocResults_LiteralSerializer.getInnermostSerializer();
        ns2_myXsbRemoteClientNameExtendedResult_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameExtendedResult.class, ns2_XsbRemoteClientNameExtendedResult_TYPE_QNAME);
        ns2_myXsbRemoteClientNameExtendedResult_LiteralSerializer = ns2_myXsbRemoteClientNameExtendedResult_LiteralSerializer.getInnermostSerializer();
        ns2_myXsbRemoteClientNameResult_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameResult.class, ns2_XsbRemoteClientNameResult_TYPE_QNAME);
        ns2_myXsbRemoteClientNameResult_LiteralSerializer = ns2_myXsbRemoteClientNameResult_LiteralSerializer.getInnermostSerializer();
        QName type = new QName("http://common.xsb.webservices.bars.sbrf", "XsbDocResults");
        CombinedSerializer interfaceSerializer = new com.rssl.phizicgate.bars.ws.jaxrpc.XsbDocResults_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
        ns3_myXsbDocResults_LiteralSerializer = interfaceSerializer.getInnermostSerializer();
        if (ns3_myXsbDocResults_LiteralSerializer instanceof Initializable) {
            ((Initializable)ns3_myXsbDocResults_LiteralSerializer).initialize(registry);
        }
    }
    
    public java.lang.Object doDeserialize(javax.xml.namespace.QName name, XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        javax.xml.namespace.QName elementType = getType(reader);
        if (elementType != null && elementType.equals(ns1_myXsbChecksDocResults_LiteralSerializer.getXmlType())) {
            return ns1_myXsbChecksDocResults_LiteralSerializer.deserialize(name, reader, context);
        } else if (elementType != null && elementType.equals(ns2_myXsbRemoteClientNameExtendedResult_LiteralSerializer.getXmlType())) {
            return ns2_myXsbRemoteClientNameExtendedResult_LiteralSerializer.deserialize(name, reader, context);
        } else if (elementType != null && elementType.equals(ns2_myXsbRemoteClientNameResult_LiteralSerializer.getXmlType())) {
            return ns2_myXsbRemoteClientNameResult_LiteralSerializer.deserialize(name, reader, context);
        } else if (elementType == null || elementType.equals(ns3_myXsbDocResults_LiteralSerializer.getXmlType())) {
            Object obj = ns3_myXsbDocResults_LiteralSerializer.deserialize(name, reader, context);
            return obj;
        }
        throw new DeserializationException("soap.unexpectedElementType", new Object[] {"", elementType.toString()});
    }
    
    public void doSerializeInstance(java.lang.Object obj, javax.xml.namespace.QName name, SerializerCallback callback,
        XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.XsbDocResults instance = (com.rssl.phizicgate.bars.ws.jaxrpc.XsbDocResults)obj;
        
        if (obj instanceof com.rssl.phizicgate.bars.ws.jaxrpc.XsbChecksDocResults) {
            ns1_myXsbChecksDocResults_LiteralSerializer.serialize(obj, name, callback, writer, context);
        } else if (obj instanceof com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameExtendedResult) {
            ns2_myXsbRemoteClientNameExtendedResult_LiteralSerializer.serialize(obj, name, callback, writer, context);
        } else if (obj instanceof com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameResult) {
            ns2_myXsbRemoteClientNameResult_LiteralSerializer.serialize(obj, name, callback, writer, context);
        } else {
            ns3_myXsbDocResults_LiteralSerializer.serialize(obj, name, callback, writer, context);
        }
    }
}
