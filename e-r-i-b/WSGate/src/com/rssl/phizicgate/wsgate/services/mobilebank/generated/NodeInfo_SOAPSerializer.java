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

public class NodeInfo_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_adminAvailable_QNAME = new QName("", "adminAvailable");
    private static final javax.xml.namespace.QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    private static final javax.xml.namespace.QName ns1_dictionaryMQ_QNAME = new QName("", "dictionaryMQ");
    private static final javax.xml.namespace.QName ns2_MQInfo_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MQInfo");
    private CombinedSerializer ns2_myMQInfo_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_ermbQueueMQ_QNAME = new QName("", "ermbQueueMQ");
    private static final javax.xml.namespace.QName ns1_existingUsersAllowed_QNAME = new QName("", "existingUsersAllowed");
    private static final javax.xml.namespace.QName ns1_guestAvailable_QNAME = new QName("", "guestAvailable");
    private static final javax.xml.namespace.QName ns1_hostname_QNAME = new QName("", "hostname");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_id_QNAME = new QName("", "id");
    private static final javax.xml.namespace.QName ns5_long_TYPE_QNAME = SOAPConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns5_myns5__long__java_lang_Long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_listenerHost_QNAME = new QName("", "listenerHost");
    private static final javax.xml.namespace.QName ns1_mbkRegistrationMQ_QNAME = new QName("", "mbkRegistrationMQ");
    private static final javax.xml.namespace.QName ns1_multiNodeDataMQ_QNAME = new QName("", "multiNodeDataMQ");
    private static final javax.xml.namespace.QName ns1_name_QNAME = new QName("", "name");
    private static final javax.xml.namespace.QName ns1_newUsersAllowed_QNAME = new QName("", "newUsersAllowed");
    private static final javax.xml.namespace.QName ns1_smsMQ_QNAME = new QName("", "smsMQ");
    private static final javax.xml.namespace.QName ns1_temporaryUsersAllowed_QNAME = new QName("", "temporaryUsersAllowed");
    private static final javax.xml.namespace.QName ns1_usersTransferAllowed_QNAME = new QName("", "usersTransferAllowed");
    private static final int myADMINAVAILABLE_INDEX = 0;
    private static final int myDICTIONARYMQ_INDEX = 1;
    private static final int myERMBQUEUEMQ_INDEX = 2;
    private static final int myEXISTINGUSERSALLOWED_INDEX = 3;
    private static final int myGUESTAVAILABLE_INDEX = 4;
    private static final int myHOSTNAME_INDEX = 5;
    private static final int myID_INDEX = 6;
    private static final int myLISTENERHOST_INDEX = 7;
    private static final int myMBKREGISTRATIONMQ_INDEX = 8;
    private static final int myMULTINODEDATAMQ_INDEX = 9;
    private static final int myNAME_INDEX = 10;
    private static final int myNEWUSERSALLOWED_INDEX = 11;
    private static final int mySMSMQ_INDEX = 12;
    private static final int myTEMPORARYUSERSALLOWED_INDEX = 13;
    private static final int myUSERSTRANSFERALLOWED_INDEX = 14;
    
    public NodeInfo_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, boolean.class, ns3_boolean_TYPE_QNAME);
        ns2_myMQInfo_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.mobilebank.generated.MQInfo.class, ns2_MQInfo_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
        ns5_myns5__long__java_lang_Long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.Long.class, ns5_long_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo instance = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo();
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_adminAvailable_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_adminAvailable_QNAME, reader, context);
                instance.setAdminAvailable(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_dictionaryMQ_QNAME)) {
                member = ns2_myMQInfo_SOAPSerializer.deserialize(ns1_dictionaryMQ_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDICTIONARYMQ_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDictionaryMQ((com.rssl.phizicgate.wsgate.services.mobilebank.generated.MQInfo)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_ermbQueueMQ_QNAME)) {
                member = ns2_myMQInfo_SOAPSerializer.deserialize(ns1_ermbQueueMQ_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myERMBQUEUEMQ_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setErmbQueueMQ((com.rssl.phizicgate.wsgate.services.mobilebank.generated.MQInfo)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_existingUsersAllowed_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_existingUsersAllowed_QNAME, reader, context);
                instance.setExistingUsersAllowed(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_guestAvailable_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_guestAvailable_QNAME, reader, context);
                instance.setGuestAvailable(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_hostname_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_hostname_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myHOSTNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setHostname((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns5_myns5__long__java_lang_Long_Long_Serializer.deserialize(ns1_id_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myID_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setId((java.lang.Long)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_listenerHost_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_listenerHost_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myLISTENERHOST_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setListenerHost((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_mbkRegistrationMQ_QNAME)) {
                member = ns2_myMQInfo_SOAPSerializer.deserialize(ns1_mbkRegistrationMQ_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMBKREGISTRATIONMQ_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMbkRegistrationMQ((com.rssl.phizicgate.wsgate.services.mobilebank.generated.MQInfo)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_multiNodeDataMQ_QNAME)) {
                member = ns2_myMQInfo_SOAPSerializer.deserialize(ns1_multiNodeDataMQ_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMULTINODEDATAMQ_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMultiNodeDataMQ((com.rssl.phizicgate.wsgate.services.mobilebank.generated.MQInfo)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_name_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_name_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myNAME_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setName((java.lang.String)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_newUsersAllowed_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_newUsersAllowed_QNAME, reader, context);
                instance.setNewUsersAllowed(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_smsMQ_QNAME)) {
                member = ns2_myMQInfo_SOAPSerializer.deserialize(ns1_smsMQ_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, mySMSMQ_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setSmsMQ((com.rssl.phizicgate.wsgate.services.mobilebank.generated.MQInfo)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_temporaryUsersAllowed_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_temporaryUsersAllowed_QNAME, reader, context);
                instance.setTemporaryUsersAllowed(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_usersTransferAllowed_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_usersTransferAllowed_QNAME, reader, context);
                instance.setUsersTransferAllowed(((Boolean)member).booleanValue());
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo instance = (com.rssl.phizicgate.wsgate.services.mobilebank.generated.NodeInfo)obj;
        
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isAdminAvailable()), ns1_adminAvailable_QNAME, null, writer, context);
        ns2_myMQInfo_SOAPSerializer.serialize(instance.getDictionaryMQ(), ns1_dictionaryMQ_QNAME, null, writer, context);
        ns2_myMQInfo_SOAPSerializer.serialize(instance.getErmbQueueMQ(), ns1_ermbQueueMQ_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isExistingUsersAllowed()), ns1_existingUsersAllowed_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isGuestAvailable()), ns1_guestAvailable_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getHostname(), ns1_hostname_QNAME, null, writer, context);
        ns5_myns5__long__java_lang_Long_Long_Serializer.serialize(instance.getId(), ns1_id_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getListenerHost(), ns1_listenerHost_QNAME, null, writer, context);
        ns2_myMQInfo_SOAPSerializer.serialize(instance.getMbkRegistrationMQ(), ns1_mbkRegistrationMQ_QNAME, null, writer, context);
        ns2_myMQInfo_SOAPSerializer.serialize(instance.getMultiNodeDataMQ(), ns1_multiNodeDataMQ_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isNewUsersAllowed()), ns1_newUsersAllowed_QNAME, null, writer, context);
        ns2_myMQInfo_SOAPSerializer.serialize(instance.getSmsMQ(), ns1_smsMQ_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isTemporaryUsersAllowed()), ns1_temporaryUsersAllowed_QNAME, null, writer, context);
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isUsersTransferAllowed()), ns1_usersTransferAllowed_QNAME, null, writer, context);
    }
}
