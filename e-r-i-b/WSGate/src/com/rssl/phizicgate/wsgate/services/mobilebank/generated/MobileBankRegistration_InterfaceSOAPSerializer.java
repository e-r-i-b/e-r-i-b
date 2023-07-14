// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class MobileBankRegistration_InterfaceSOAPSerializer extends InterfaceSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_MobileBankRegistration3_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MobileBankRegistration3");
    private CombinedSerializer ns1_myMobileBankRegistration3_SOAPSerializer;
    private CombinedSerializer ns1_myMobileBankRegistration_SOAPSerializer;
    
    public MobileBankRegistration_InterfaceSOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns1_myMobileBankRegistration3_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration3.class, ns1_MobileBankRegistration3_TYPE_QNAME);
        ns1_myMobileBankRegistration3_SOAPSerializer = ns1_myMobileBankRegistration3_SOAPSerializer.getInnermostSerializer();
        QName type = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MobileBankRegistration");
        CombinedSerializer interfaceSerializer = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration_SOAPSerializer(type,
            ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
        interfaceSerializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, interfaceSerializer, SOAPVersion.SOAP_11);
        ns1_myMobileBankRegistration_SOAPSerializer = interfaceSerializer.getInnermostSerializer();
        if (ns1_myMobileBankRegistration_SOAPSerializer instanceof Initializable) {
            ((Initializable)ns1_myMobileBankRegistration_SOAPSerializer).initialize(registry);
        }
    }
    
    public java.lang.Object doDeserialize(javax.xml.namespace.QName name, XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        javax.xml.namespace.QName elementType = getType(reader);
        if (elementType != null && elementType.equals(ns1_myMobileBankRegistration3_SOAPSerializer.getXmlType())) {
            return ns1_myMobileBankRegistration3_SOAPSerializer.deserialize(name, reader, context);
        } else if (elementType == null || elementType.equals(ns1_myMobileBankRegistration_SOAPSerializer.getXmlType())) {
            Object obj = ns1_myMobileBankRegistration_SOAPSerializer.deserialize(name, reader, context);
            return obj;
        }
        throw new DeserializationException("soap.unexpectedElementType", new Object[] {"", elementType.toString()});
    }
    
    public void doSerializeInstance(java.lang.Object obj, javax.xml.namespace.QName name, SerializerCallback callback,
        XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration)obj;
        
        if (obj instanceof com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration3) {
            ns1_myMobileBankRegistration3_SOAPSerializer.serialize(obj, name, callback, writer, context);
        } else {
            ns1_myMobileBankRegistration_SOAPSerializer.serialize(obj, name, callback, writer, context);
        }
    }
}
