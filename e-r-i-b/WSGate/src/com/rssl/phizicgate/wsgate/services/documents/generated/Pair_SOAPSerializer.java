// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Pair_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_first_QNAME = new QName("", "first");
    private static final javax.xml.namespace.QName ns3_anyType_TYPE_QNAME = SchemaConstants.QNAME_TYPE_URTYPE;
    private CombinedSerializer ns3_myns3_anyType__DynamicSerializer;
    private static final javax.xml.namespace.QName ns1_second_QNAME = new QName("", "second");
    private static final int myFIRST_INDEX = 0;
    private static final int mySECOND_INDEX = 1;
    
    public Pair_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_anyType__DynamicSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Object.class, ns3_anyType_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.Pair instance = new com.rssl.phizicgate.wsgate.services.documents.generated.Pair();
        com.rssl.phizicgate.wsgate.services.documents.generated.Pair_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_first_QNAME)) {
                member = ns3_myns3_anyType__DynamicSerializer.deserialize(ns1_first_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Pair_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myFIRST_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setFirst((java.lang.Object)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_second_QNAME)) {
                member = ns3_myns3_anyType__DynamicSerializer.deserialize(ns1_second_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.Pair_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySECOND_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSecond((java.lang.Object)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.Pair instance = (com.rssl.phizicgate.wsgate.services.documents.generated.Pair)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.Pair instance = (com.rssl.phizicgate.wsgate.services.documents.generated.Pair)obj;
        
        ns3_myns3_anyType__DynamicSerializer.serialize(instance.getFirst(), ns1_first_QNAME, null, writer, context);
        ns3_myns3_anyType__DynamicSerializer.serialize(instance.getSecond(), ns1_second_QNAME, null, writer, context);
    }
}
