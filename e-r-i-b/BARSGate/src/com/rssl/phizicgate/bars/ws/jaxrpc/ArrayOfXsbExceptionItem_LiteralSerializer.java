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

public class ArrayOfXsbExceptionItem_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final javax.xml.namespace.QName ns1_XsbExceptionItem_QNAME = new QName("", "XsbExceptionItem");
    private static final javax.xml.namespace.QName ns3_XsbExceptionItem_TYPE_QNAME = new QName("http://common.xsb.webservices.bars.sbrf", "XsbExceptionItem");
    private CombinedSerializer ns3_myXsbExceptionItem_LiteralSerializer;
    
    public ArrayOfXsbExceptionItem_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public ArrayOfXsbExceptionItem_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myXsbExceptionItem_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizicgate.bars.ws.jaxrpc.XsbExceptionItem.class, ns3_XsbExceptionItem_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem instance = new com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem();
        java.lang.Object member=null;
        javax.xml.namespace.QName elementName;
        java.util.List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_XsbExceptionItem_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_XsbExceptionItem_QNAME))) {
                    value = ns3_myXsbExceptionItem_LiteralSerializer.deserialize(ns1_XsbExceptionItem_QNAME, reader, context);
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.rssl.phizicgate.bars.ws.jaxrpc.XsbExceptionItem[values.size()];
            member = values.toArray((Object[]) member);
            instance.setXsbExceptionItem((com.rssl.phizicgate.bars.ws.jaxrpc.XsbExceptionItem[])member);
        }
        else {
            instance.setXsbExceptionItem(new com.rssl.phizicgate.bars.ws.jaxrpc.XsbExceptionItem[0]);
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem instance = (com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem instance = (com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbExceptionItem)obj;
        
        if (instance.getXsbExceptionItem() != null) {
            for (int i = 0; i < instance.getXsbExceptionItem().length; ++i) {
                ns3_myXsbExceptionItem_LiteralSerializer.serialize(instance.getXsbExceptionItem()[i], ns1_XsbExceptionItem_QNAME, null, writer, context);
            }
        }
    }
}