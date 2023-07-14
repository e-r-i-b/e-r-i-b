// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.deposits.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class DepositService_getDepositInfo_RequestStruct2_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_Deposit_1_QNAME = new QName("", "Deposit_1");
    private static final javax.xml.namespace.QName ns2_Deposit_TYPE_QNAME = new QName("http://generated.deposits.services.gate.web.phizic.rssl.com", "Deposit");
    private CombinedSerializer ns2_myDeposit_SOAPSerializer;
    private static final int myDEPOSIT_1_INDEX = 0;
    
    public DepositService_getDepositInfo_RequestStruct2_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myDeposit_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.deposits.generated.Deposit.class, ns2_Deposit_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_getDepositInfo_RequestStruct2 instance = new com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_getDepositInfo_RequestStruct2();
        com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_getDepositInfo_RequestStruct2_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_Deposit_1_QNAME)) {
                member = ns2_myDeposit_SOAPSerializer.deserialize(ns1_Deposit_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_getDepositInfo_RequestStruct2_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDEPOSIT_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDeposit_1((com.rssl.phizicgate.wsgate.services.deposits.generated.Deposit)member);
                }
                reader.nextElementContent();
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_Deposit_1_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_getDepositInfo_RequestStruct2 instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_getDepositInfo_RequestStruct2)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_getDepositInfo_RequestStruct2 instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.DepositService_getDepositInfo_RequestStruct2)obj;
        
        ns2_myDeposit_SOAPSerializer.serialize(instance.getDeposit_1(), ns1_Deposit_1_QNAME, null, writer, context);
    }
}