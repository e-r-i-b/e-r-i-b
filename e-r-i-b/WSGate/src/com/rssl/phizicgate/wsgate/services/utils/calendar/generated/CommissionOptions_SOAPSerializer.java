// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.utils.calendar.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class CommissionOptions_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_account_QNAME = new QName("", "account");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_target_QNAME = new QName("", "target");
    private static final javax.xml.namespace.QName ns2_CommissionOptions_TYPE_QNAME = new QName("http://generated.calendar.util.services.gate.web.phizic.rssl.com", "CommissionOptions");
    private CombinedSerializer ns2_myCommissionOptions_SOAPSerializer;
    private static final int myACCOUNT_INDEX = 0;
    private static final int myTARGET_INDEX = 1;
    
    public CommissionOptions_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myCommissionOptions_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions.class, ns2_CommissionOptions_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions instance = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions();
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_account_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_account_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myACCOUNT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAccount((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_target_QNAME)) {
                member = ns2_myCommissionOptions_SOAPSerializer.deserialize(ns1_target_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTARGET_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTarget((com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions instance = (com.rssl.phizicgate.wsgate.services.utils.calendar.generated.CommissionOptions)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getAccount(), ns1_account_QNAME, null, writer, context);
        ns2_myCommissionOptions_SOAPSerializer.serialize(instance.getTarget(), ns1_target_QNAME, null, writer, context);
    }
}
