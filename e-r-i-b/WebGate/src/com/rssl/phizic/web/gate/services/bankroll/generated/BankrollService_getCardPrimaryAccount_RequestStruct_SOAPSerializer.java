// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class BankrollService_getCardPrimaryAccount_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_arrayOfCard_1_QNAME = new QName("", "arrayOfCard_1");
    private static final javax.xml.namespace.QName ns2_ArrayOfCard_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "ArrayOfCard");
    private CombinedSerializer ns2_myns2_ArrayOfCard__CardArray_SOAPSerializer1;
    private static final int myARRAYOFCARD_1_INDEX = 0;
    
    public BankrollService_getCardPrimaryAccount_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myns2_ArrayOfCard__CardArray_SOAPSerializer1 = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.bankroll.generated.Card[].class, ns2_ArrayOfCard_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardPrimaryAccount_RequestStruct instance = new com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardPrimaryAccount_RequestStruct();
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardPrimaryAccount_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_arrayOfCard_1_QNAME)) {
                member = ns2_myns2_ArrayOfCard__CardArray_SOAPSerializer1.deserialize(ns1_arrayOfCard_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardPrimaryAccount_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myARRAYOFCARD_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setArrayOfCard_1((com.rssl.phizic.web.gate.services.bankroll.generated.Card[])member);
                }
                reader.nextElementContent();
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_arrayOfCard_1_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardPrimaryAccount_RequestStruct instance = (com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardPrimaryAccount_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardPrimaryAccount_RequestStruct instance = (com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getCardPrimaryAccount_RequestStruct)obj;
        
        ns2_myns2_ArrayOfCard__CardArray_SOAPSerializer1.serialize(instance.getArrayOfCard_1(), ns1_arrayOfCard_1_QNAME, null, writer, context);
    }
}
