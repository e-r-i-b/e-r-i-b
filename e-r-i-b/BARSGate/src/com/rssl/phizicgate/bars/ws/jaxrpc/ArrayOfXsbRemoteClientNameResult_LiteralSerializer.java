// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.streaming.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;

public class ArrayOfXsbRemoteClientNameResult_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final javax.xml.namespace.QName ns1_XsbRemoteClientNameResult_QNAME = new QName("", "XsbRemoteClientNameResult");
    private static final javax.xml.namespace.QName ns5_XsbRemoteClientNameResult_TYPE_QNAME = new QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameResult");
    private CombinedSerializer ns5_myXsbRemoteClientNameResult_LiteralSerializer;
    
    public ArrayOfXsbRemoteClientNameResult_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public ArrayOfXsbRemoteClientNameResult_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns5_myXsbRemoteClientNameResult_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameResult.class, ns5_XsbRemoteClientNameResult_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult instance = new com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult();
        java.lang.Object member=null;
        javax.xml.namespace.QName elementName;
        java.util.List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_XsbRemoteClientNameResult_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_XsbRemoteClientNameResult_QNAME))) {
                    value = ns5_myXsbRemoteClientNameResult_LiteralSerializer.deserialize(ns1_XsbRemoteClientNameResult_QNAME, reader, context);
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameResult[values.size()];
            member = values.toArray((Object[]) member);
            instance.setXsbRemoteClientNameResult((com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameResult[])member);
        }
        else {
            instance.setXsbRemoteClientNameResult(new com.rssl.phizicgate.bars.ws.jaxrpc.XsbRemoteClientNameResult[0]);
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult instance = (com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult instance = (com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbRemoteClientNameResult)obj;
        
        if (instance.getXsbRemoteClientNameResult() != null) {
            for (int i = 0; i < instance.getXsbRemoteClientNameResult().length; ++i) {
                ns5_myXsbRemoteClientNameResult_LiteralSerializer.serialize(instance.getXsbRemoteClientNameResult()[i], ns1_XsbRemoteClientNameResult_QNAME, null, writer, context);
            }
        }
    }
}
