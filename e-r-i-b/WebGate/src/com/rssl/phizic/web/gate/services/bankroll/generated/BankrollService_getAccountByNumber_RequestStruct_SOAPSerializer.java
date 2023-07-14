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

public class BankrollService_getAccountByNumber_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_arrayOfPair_1_QNAME = new QName("", "arrayOfPair_1");
    private static final javax.xml.namespace.QName ns2_ArrayOfPair_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "ArrayOfPair");
    private CombinedSerializer ns2_myns2_ArrayOfPair__PairArray_SOAPSerializer1;
    private static final int myARRAYOFPAIR_1_INDEX = 0;
    
    public BankrollService_getAccountByNumber_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myns2_ArrayOfPair__PairArray_SOAPSerializer1 = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.bankroll.generated.Pair[].class, ns2_ArrayOfPair_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAccountByNumber_RequestStruct instance = new com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAccountByNumber_RequestStruct();
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAccountByNumber_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_arrayOfPair_1_QNAME)) {
                member = ns2_myns2_ArrayOfPair__PairArray_SOAPSerializer1.deserialize(ns1_arrayOfPair_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAccountByNumber_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myARRAYOFPAIR_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setArrayOfPair_1((com.rssl.phizic.web.gate.services.bankroll.generated.Pair[])member);
                }
                reader.nextElementContent();
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_arrayOfPair_1_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAccountByNumber_RequestStruct instance = (com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAccountByNumber_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAccountByNumber_RequestStruct instance = (com.rssl.phizic.web.gate.services.bankroll.generated.BankrollService_getAccountByNumber_RequestStruct)obj;
        
        ns2_myns2_ArrayOfPair__PairArray_SOAPSerializer1.serialize(instance.getArrayOfPair_1(), ns1_arrayOfPair_1_QNAME, null, writer, context);
    }
}
