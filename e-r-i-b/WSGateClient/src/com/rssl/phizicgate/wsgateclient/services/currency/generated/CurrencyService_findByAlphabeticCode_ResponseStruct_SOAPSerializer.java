// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgateclient.services.currency.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.streaming.*;

import javax.xml.namespace.QName;

public class CurrencyService_findByAlphabeticCode_ResponseStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_result_QNAME = new QName("", "result");
    private static final javax.xml.namespace.QName ns3_Currency_TYPE_QNAME = new QName("http://currency.wsgate.phizic.rssl.com", "Currency");
    private CombinedSerializer ns3_myCurrency_SOAPSerializer;
    private static final int myRESULT_INDEX = 0;
    
    public CurrencyService_findByAlphabeticCode_ResponseStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myCurrency_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency.class, ns3_Currency_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService_findByAlphabeticCode_ResponseStruct instance = new com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService_findByAlphabeticCode_ResponseStruct();
        com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService_findByAlphabeticCode_ResponseStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_result_QNAME)) {
                member = ns3_myCurrency_SOAPSerializer.deserialize(ns1_result_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService_findByAlphabeticCode_ResponseStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myRESULT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setResult((com.rssl.phizicgate.wsgateclient.services.currency.generated.Currency)member);
                }
                reader.nextElementContent();
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_result_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService_findByAlphabeticCode_ResponseStruct instance = (com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService_findByAlphabeticCode_ResponseStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService_findByAlphabeticCode_ResponseStruct instance = (com.rssl.phizicgate.wsgateclient.services.currency.generated.CurrencyService_findByAlphabeticCode_ResponseStruct)obj;
        
        ns3_myCurrency_SOAPSerializer.serialize(instance.getResult(), ns1_result_QNAME, null, writer, context);
    }
    protected void verifyName(XMLReader reader, javax.xml.namespace.QName expectedName) throws java.lang.Exception {
    }
}
