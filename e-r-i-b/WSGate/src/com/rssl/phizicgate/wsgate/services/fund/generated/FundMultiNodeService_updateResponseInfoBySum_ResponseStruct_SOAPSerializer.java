// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.fund.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class FundMultiNodeService_updateResponseInfoBySum_ResponseStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    
    public FundMultiNodeService_updateResponseInfoBySum_ResponseStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService_updateResponseInfoBySum_ResponseStruct instance = new com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService_updateResponseInfoBySum_ResponseStruct();
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService_updateResponseInfoBySum_ResponseStruct instance = (com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService_updateResponseInfoBySum_ResponseStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService_updateResponseInfoBySum_ResponseStruct instance = (com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService_updateResponseInfoBySum_ResponseStruct)obj;
        
    }
    protected void verifyName(XMLReader reader, javax.xml.namespace.QName expectedName) throws java.lang.Exception {
    }
}