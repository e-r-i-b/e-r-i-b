// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.streaming.*;

import javax.xml.namespace.QName;

public class ReadRemoteClientNameResponse_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final javax.xml.namespace.QName ns1_readRemoteClientNameReturn_QNAME = new QName("", "readRemoteClientNameReturn");
    private static final javax.xml.namespace.QName ns5_XsbRemoteClientNameReturn_TYPE_QNAME = new QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameReturn");
    private CombinedSerializer ns5_myXsbRemoteClientNameReturn_LiteralSerializer;
    
    public ReadRemoteClientNameResponse_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public ReadRemoteClientNameResponse_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns5_myXsbRemoteClientNameReturn_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameReturn.class, ns5_XsbRemoteClientNameReturn_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameResponse instance = new com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameResponse();
        java.lang.Object member=null;
        javax.xml.namespace.QName elementName;
        java.util.List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_readRemoteClientNameReturn_QNAME)) {
                member = ns5_myXsbRemoteClientNameReturn_LiteralSerializer.deserialize(ns1_readRemoteClientNameReturn_QNAME, reader, context);
                instance.setReadRemoteClientNameReturn((com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameReturn)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_readRemoteClientNameReturn_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameResponse instance = (com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameResponse)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameResponse instance = (com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameResponse)obj;
        
        ns5_myXsbRemoteClientNameReturn_LiteralSerializer.serialize(instance.getReadRemoteClientNameReturn(), ns1_readRemoteClientNameReturn_QNAME, null, writer, context);
    }
}
