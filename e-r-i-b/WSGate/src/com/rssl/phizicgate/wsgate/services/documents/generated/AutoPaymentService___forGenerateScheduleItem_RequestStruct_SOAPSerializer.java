// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class AutoPaymentService___forGenerateScheduleItem_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    
    public AutoPaymentService___forGenerateScheduleItem_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_RequestStruct instance = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_RequestStruct();
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_RequestStruct)obj;
        
    }
}
