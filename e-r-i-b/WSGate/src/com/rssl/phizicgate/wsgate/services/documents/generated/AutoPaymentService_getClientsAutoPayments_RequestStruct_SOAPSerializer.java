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

public class AutoPaymentService_getClientsAutoPayments_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_List_1_QNAME = new QName("", "List_1");
    private static final javax.xml.namespace.QName ns5_list_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_LIST;
    private CombinedSerializer ns5_myns5_list__CollectionInterfaceSerializer;
    private static final int myLIST_1_INDEX = 0;
    
    public AutoPaymentService_getClientsAutoPayments_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns5_myns5_list__CollectionInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.List.class, ns5_list_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct instance = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct();
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_List_1_QNAME)) {
                member = ns5_myns5_list__CollectionInterfaceSerializer.deserialize(ns1_List_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myLIST_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setList_1((java.util.List)member);
                }
                reader.nextElementContent();
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_List_1_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct instance = (com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct)obj;
        
        ns5_myns5_list__CollectionInterfaceSerializer.serialize(instance.getList_1(), ns1_List_1_QNAME, null, writer, context);
    }
}
