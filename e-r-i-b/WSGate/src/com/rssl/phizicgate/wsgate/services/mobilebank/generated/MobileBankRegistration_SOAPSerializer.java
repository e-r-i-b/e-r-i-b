// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class MobileBankRegistration_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_linkedCards_QNAME = new QName("", "linkedCards");
    private static final javax.xml.namespace.QName ns4_list_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_LIST;
    private CombinedSerializer ns4_myns4_list__CollectionInterfaceSerializer;
    private static final javax.xml.namespace.QName ns1_mainCardInfo_QNAME = new QName("", "mainCardInfo");
    private static final javax.xml.namespace.QName ns2_MobileBankCardInfo_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MobileBankCardInfo");
    private CombinedSerializer ns2_myMobileBankCardInfo_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_status_QNAME = new QName("", "status");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_tariff_QNAME = new QName("", "tariff");
    private static final int myLINKEDCARDS_INDEX = 0;
    private static final int myMAINCARDINFO_INDEX = 1;
    private static final int mySTATUS_INDEX = 2;
    private static final int myTARIFF_INDEX = 3;
    
    public MobileBankRegistration_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns4_myns4_list__CollectionInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.List.class, ns4_list_TYPE_QNAME);
        ns2_myMobileBankCardInfo_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo.class, ns2_MobileBankCardInfo_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration instance = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration();
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_linkedCards_QNAME)) {
                member = ns4_myns4_list__CollectionInterfaceSerializer.deserialize(ns1_linkedCards_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myLINKEDCARDS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setLinkedCards((java.util.List)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_mainCardInfo_QNAME)) {
                member = ns2_myMobileBankCardInfo_SOAPSerializer.deserialize(ns1_mainCardInfo_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMAINCARDINFO_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMainCardInfo((com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_status_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_status_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySTATUS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setStatus((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_tariff_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_tariff_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTARIFF_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTariff((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration)obj;
        
        ns4_myns4_list__CollectionInterfaceSerializer.serialize(instance.getLinkedCards(), ns1_linkedCards_QNAME, null, writer, context);
        ns2_myMobileBankCardInfo_SOAPSerializer.serialize(instance.getMainCardInfo(), ns1_mainCardInfo_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getStatus(), ns1_status_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getTariff(), ns1_tariff_QNAME, null, writer, context);
    }
}