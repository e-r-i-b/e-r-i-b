// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.bars.ws.jaxrpc;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class ReadRemoteClientNameExtended_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final javax.xml.namespace.QName ns1_xsbDocument_QNAME = new QName("", "xsbDocument");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_parameters_QNAME = new QName("", "parameters");
    private static final javax.xml.namespace.QName ns3_ArrayOfXsbParameter_TYPE_QNAME = new QName("http://common.xsb.webservices.bars.sbrf", "ArrayOfXsbParameter");
    private CombinedSerializer ns3_myArrayOfXsbParameter_LiteralSerializer;
    
    public ReadRemoteClientNameExtended_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public ReadRemoteClientNameExtended_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        ns3_myArrayOfXsbParameter_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbParameter.class, ns3_ArrayOfXsbParameter_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameExtended instance = new com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameExtended();
        java.lang.Object member=null;
        javax.xml.namespace.QName elementName;
        java.util.List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_xsbDocument_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_xsbDocument_QNAME, reader, context);
                instance.setXsbDocument((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_xsbDocument_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_parameters_QNAME)) {
                member = ns3_myArrayOfXsbParameter_LiteralSerializer.deserialize(ns1_parameters_QNAME, reader, context);
                instance.setParameters((com.rssl.phizicgate.bars.ws.jaxrpc.ArrayOfXsbParameter)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_parameters_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameExtended instance = (com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameExtended)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameExtended instance = (com.rssl.phizicgate.bars.ws.jaxrpc.ReadRemoteClientNameExtended)obj;
        
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getXsbDocument(), ns1_xsbDocument_QNAME, null, writer, context);
        ns3_myArrayOfXsbParameter_LiteralSerializer.serialize(instance.getParameters(), ns1_parameters_QNAME, null, writer, context);
    }
}
