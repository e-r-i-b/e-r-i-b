// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Money_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_currency_QNAME = new QName("", "currency");
    private static final javax.xml.namespace.QName ns2_Currency_TYPE_QNAME = new QName("http://generated.recipients.systems.payments.services.gate.web.phizic.rssl.com", "Currency");
    private CombinedSerializer ns2_myCurrency_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_decimal_QNAME = new QName("", "decimal");
    private static final javax.xml.namespace.QName ns3_decimal_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DECIMAL;
    private CombinedSerializer ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer;
    private static final int myCURRENCY_INDEX = 0;
    private static final int myDECIMAL_INDEX = 1;
    
    public Money_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myCurrency_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Currency.class, ns2_Currency_TYPE_QNAME);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.math.BigDecimal.class, ns3_decimal_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money instance = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money();
        com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_currency_QNAME)) {
                member = ns2_myCurrency_SOAPSerializer.deserialize(ns1_currency_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCURRENCY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCurrency((com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Currency)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_decimal_QNAME)) {
                member = ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.deserialize(ns1_decimal_QNAME, reader, context);
                instance.setDecimal((java.math.BigDecimal)member);
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money instance = (com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money instance = (com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money)obj;
        
        ns2_myCurrency_SOAPSerializer.serialize(instance.getCurrency(), ns1_currency_QNAME, null, writer, context);
        ns3_myns3_decimal__java_math_BigDecimal_Decimal_Serializer.serialize(instance.getDecimal(), ns1_decimal_QNAME, null, writer, context);
    }
}
