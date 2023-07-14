// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.test.way4.ws.mock.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class CommonRqType_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final javax.xml.namespace.QName ns1_STAN_QNAME = new QName("http://www.openwaygroup.com/iPAS/WS", "STAN");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    
    public CommonRqType_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public CommonRqType_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.test.way4.ws.mock.generated.CommonRqType instance = new com.rssl.phizic.test.way4.ws.mock.generated.CommonRqType();
        java.lang.Object member=null;
        javax.xml.namespace.QName elementName;
        java.util.List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_STAN_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_STAN_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setSTAN((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_STAN_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.test.way4.ws.mock.generated.CommonRqType instance = (com.rssl.phizic.test.way4.ws.mock.generated.CommonRqType)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.test.way4.ws.mock.generated.CommonRqType instance = (com.rssl.phizic.test.way4.ws.mock.generated.CommonRqType)obj;
        
        if (instance.getSTAN() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getSTAN(), ns1_STAN_QNAME, null, writer, context);
    }
}
