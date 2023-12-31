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

public class GroupResult_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_exceptions_QNAME = new QName("", "exceptions");
    private static final javax.xml.namespace.QName ns5_map_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_MAP;
    private CombinedSerializer ns5_myns5_map__MapInterfaceSerializer;
    private static final javax.xml.namespace.QName ns1_results_QNAME = new QName("", "results");
    private static final int myEXCEPTIONS_INDEX = 0;
    private static final int myRESULTS_INDEX = 1;
    
    public GroupResult_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns5_myns5_map__MapInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Map.class, ns5_map_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult instance = new com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult();
        com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_exceptions_QNAME)) {
                member = ns5_myns5_map__MapInterfaceSerializer.deserialize(ns1_exceptions_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myEXCEPTIONS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setExceptions((java.util.Map)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_results_QNAME)) {
                member = ns5_myns5_map__MapInterfaceSerializer.deserialize(ns1_results_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myRESULTS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setResults((java.util.Map)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult instance = (com.rssl.phizicgate.wsgate.services.bankroll.generated.GroupResult)obj;
        
        ns5_myns5_map__MapInterfaceSerializer.serialize(instance.getExceptions(), ns1_exceptions_QNAME, null, writer, context);
        ns5_myns5_map__MapInterfaceSerializer.serialize(instance.getResults(), ns1_results_QNAME, null, writer, context);
    }
}
