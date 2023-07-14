// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.loyalty.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class LoyaltyProgramService_getLoyaltyOffers_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_LoyaltyProgram_1_QNAME = new QName("", "LoyaltyProgram_1");
    private static final javax.xml.namespace.QName ns2_LoyaltyProgram_TYPE_QNAME = new QName("http://generated.loyalty.services.gate.web.phizic.rssl.com", "LoyaltyProgram");
    private CombinedSerializer ns2_myLoyaltyProgram_SOAPSerializer;
    private static final int myLOYALTYPROGRAM_1_INDEX = 0;
    
    public LoyaltyProgramService_getLoyaltyOffers_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myLoyaltyProgram_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgram.class, ns2_LoyaltyProgram_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramService_getLoyaltyOffers_RequestStruct instance = new com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramService_getLoyaltyOffers_RequestStruct();
        com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramService_getLoyaltyOffers_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_LoyaltyProgram_1_QNAME)) {
                member = ns2_myLoyaltyProgram_SOAPSerializer.deserialize(ns1_LoyaltyProgram_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramService_getLoyaltyOffers_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myLOYALTYPROGRAM_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setLoyaltyProgram_1((com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgram)member);
                }
                reader.nextElementContent();
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_LoyaltyProgram_1_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramService_getLoyaltyOffers_RequestStruct instance = (com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramService_getLoyaltyOffers_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramService_getLoyaltyOffers_RequestStruct instance = (com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramService_getLoyaltyOffers_RequestStruct)obj;
        
        ns2_myLoyaltyProgram_SOAPSerializer.serialize(instance.getLoyaltyProgram_1(), ns1_LoyaltyProgram_1_QNAME, null, writer, context);
    }
}
