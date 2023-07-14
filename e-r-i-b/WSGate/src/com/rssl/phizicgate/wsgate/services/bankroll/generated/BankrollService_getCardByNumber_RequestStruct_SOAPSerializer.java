// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class BankrollService_getCardByNumber_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_Client_1_QNAME = new QName("", "Client_1");
    private static final javax.xml.namespace.QName ns2_Client_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "Client");
    private CombinedSerializer ns2_myClient_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_arrayOfPair_2_QNAME = new QName("", "arrayOfPair_2");
    private static final javax.xml.namespace.QName ns2_ArrayOfPair_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "ArrayOfPair");
    private CombinedSerializer ns2_myns2_ArrayOfPair__PairArray_SOAPSerializer1;
    private static final int myCLIENT_1_INDEX = 0;
    private static final int myARRAYOFPAIR_2_INDEX = 1;
    
    public BankrollService_getCardByNumber_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myClient_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.bankroll.generated.Client.class, ns2_Client_TYPE_QNAME);
        ns2_myns2_ArrayOfPair__PairArray_SOAPSerializer1 = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair[].class, ns2_ArrayOfPair_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct instance = new com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct();
        com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        for (int i=0; i<2; i++) {
            elementName = reader.getName();
            if (reader.getState() == XMLReader.END) {
                break;
            }
            if (elementName.equals(ns1_Client_1_QNAME)) {
                member = ns2_myClient_SOAPSerializer.deserialize(ns1_Client_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCLIENT_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setClient_1((com.rssl.phizicgate.wsgate.services.bankroll.generated.Client)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_arrayOfPair_2_QNAME)) {
                member = ns2_myns2_ArrayOfPair__PairArray_SOAPSerializer1.deserialize(ns1_arrayOfPair_2_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myARRAYOFPAIR_2_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setArrayOfPair_2((com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair[])member);
                }
                reader.nextElementContent();
                continue;
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_arrayOfPair_2_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.BankrollService_getCardByNumber_RequestStruct)obj;
        
        ns2_myClient_SOAPSerializer.serialize(instance.getClient_1(), ns1_Client_1_QNAME, null, writer, context);
        ns2_myns2_ArrayOfPair__PairArray_SOAPSerializer1.serialize(instance.getArrayOfPair_2(), ns1_arrayOfPair_2_QNAME, null, writer, context);
    }
}
