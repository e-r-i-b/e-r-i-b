// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.csa.ws.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;

public class CheckSessionRqAuthContextGet_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final javax.xml.namespace.QName ns1_Name_QNAME = new QName("", "Name");
    private static final javax.xml.namespace.QName ns1_Type_QNAME = new QName("", "Type");
    
    public CheckSessionRqAuthContextGet_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public CheckSessionRqAuthContextGet_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRqAuthContextGet instance = new com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRqAuthContextGet();
        java.lang.Object member=null;
        javax.xml.namespace.QName elementName;
        java.util.List values;
        java.lang.Object value;
        
        Attributes attributes = reader.getAttributes();
        java.lang.String attribute = null;
        attribute = attributes.getValue(ns1_Name_QNAME);
        if (attribute != null) {
            member = XSDStringEncoder.getInstance().stringToObject(attribute, reader);
            instance.setName((java.lang.String)member);
        }
        attribute = attributes.getValue(ns1_Type_QNAME);
        if (attribute != null) {
            member = XSDStringEncoder.getInstance().stringToObject(attribute, reader);
            instance.setType((java.lang.String)member);
        }
        
        reader.nextContent();
        if (reader.getState() == XMLReader.CHARS) {
            member = XSDStringEncoder.getInstance().stringToObject(reader.getValue(), reader);
            reader.nextContent();
        }
        else if (reader.getState() == XMLReader.END) {
            member = XSDStringEncoder.getInstance().stringToObject("", reader);
        }
        else if (reader.getState() == XMLReader.START) {
            throw new DeserializationException("literal.simpleContentExpected", new Object[] {reader.getName()});
        }
        instance.set_value((java.lang.String)member);
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRqAuthContextGet instance = (com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRqAuthContextGet)obj;
        
        if (instance.getName() != null) {
            writer.writeAttribute(ns1_Name_QNAME, XSDStringEncoder.getInstance().objectToString(instance.getName(), writer));
        }
        if (instance.getType() != null) {
            writer.writeAttribute(ns1_Type_QNAME, XSDStringEncoder.getInstance().objectToString(instance.getType(), writer));
        }
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRqAuthContextGet instance = (com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRqAuthContextGet)obj;
        
        if (instance.get_value() == null) {
            writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
        }
        else {
            writer.writeChars(XSDStringEncoder.getInstance().objectToString(instance.get_value(), writer));
        }
    }
}