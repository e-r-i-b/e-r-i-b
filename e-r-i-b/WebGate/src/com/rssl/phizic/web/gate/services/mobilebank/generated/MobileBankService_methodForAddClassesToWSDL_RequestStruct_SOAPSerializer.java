// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.mobilebank.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_CommitMigrationResult_1_QNAME = new QName("", "CommitMigrationResult_1");
    private static final javax.xml.namespace.QName ns2_CommitMigrationResult_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "CommitMigrationResult");
    private CombinedSerializer ns2_myCommitMigrationResult_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_MobileBankTemplate_2_QNAME = new QName("", "MobileBankTemplate_2");
    private static final javax.xml.namespace.QName ns2_MobileBankTemplate_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MobileBankTemplate");
    private CombinedSerializer ns2_myMobileBankTemplate_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_MbkCard_3_QNAME = new QName("", "MbkCard_3");
    private static final javax.xml.namespace.QName ns2_MbkCard_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MbkCard");
    private CombinedSerializer ns2_myMbkCard_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_DisconnectedPhoneResult_4_QNAME = new QName("", "DisconnectedPhoneResult_4");
    private static final javax.xml.namespace.QName ns2_DisconnectedPhoneResult_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "DisconnectedPhoneResult");
    private CombinedSerializer ns2_myDisconnectedPhoneResult_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_P2PRequest_5_QNAME = new QName("", "P2PRequest_5");
    private static final javax.xml.namespace.QName ns2_P2PRequest_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "P2PRequest");
    private CombinedSerializer ns2_myP2PRequest_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_AcceptInfo_6_QNAME = new QName("", "AcceptInfo_6");
    private static final javax.xml.namespace.QName ns2_AcceptInfo_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "AcceptInfo");
    private CombinedSerializer ns2_myAcceptInfo_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_UESIMessage_7_QNAME = new QName("", "UESIMessage_7");
    private static final javax.xml.namespace.QName ns2_UESIMessage_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "UESIMessage");
    private CombinedSerializer ns2_myUESIMessage_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_ERMBPhone_8_QNAME = new QName("", "ERMBPhone_8");
    private static final javax.xml.namespace.QName ns2_ERMBPhone_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "ERMBPhone");
    private CombinedSerializer ns2_myERMBPhone_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_MobileBankRegistration_9_QNAME = new QName("", "MobileBankRegistration_9");
    private static final javax.xml.namespace.QName ns2_MobileBankRegistration_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MobileBankRegistration");
    private CombinedSerializer ns2_myMobileBankRegistration_InterfaceSOAPSerializer;
    private static final javax.xml.namespace.QName ns1_MBKPhone_10_QNAME = new QName("", "MBKPhone_10");
    private static final javax.xml.namespace.QName ns2_MBKPhone_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MBKPhone");
    private CombinedSerializer ns2_myMBKPhone_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_MQInfo_11_QNAME = new QName("", "MQInfo_11");
    private static final javax.xml.namespace.QName ns2_MQInfo_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MQInfo");
    private CombinedSerializer ns2_myMQInfo_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_NodeInfo_12_QNAME = new QName("", "NodeInfo_12");
    private static final javax.xml.namespace.QName ns2_NodeInfo_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "NodeInfo");
    private CombinedSerializer ns2_myNodeInfo_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_UserInfoCSA_13_QNAME = new QName("", "UserInfoCSA_13");
    private static final javax.xml.namespace.QName ns2_UserInfoCSA_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "UserInfoCSA");
    private CombinedSerializer ns2_myUserInfoCSA_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_MBKRegistration_14_QNAME = new QName("", "MBKRegistration_14");
    private static final javax.xml.namespace.QName ns2_MBKRegistration_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MBKRegistration");
    private CombinedSerializer ns2_myMBKRegistration_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_CodeOffice_15_QNAME = new QName("", "CodeOffice_15");
    private static final javax.xml.namespace.QName ns2_CodeOffice_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "CodeOffice");
    private CombinedSerializer ns2_myCodeOffice_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_IKFLException_16_QNAME = new QName("", "IKFLException_16");
    private static final javax.xml.namespace.QName ns2_IKFLException_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "IKFLException");
    private CombinedSerializer ns2_myIKFLException_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_MobileBankRegistration3_17_QNAME = new QName("", "MobileBankRegistration3_17");
    private static final javax.xml.namespace.QName ns2_MobileBankRegistration3_TYPE_QNAME = new QName("http://generated.mobilebank.services.gate.web.phizic.rssl.com", "MobileBankRegistration3");
    private CombinedSerializer ns2_myMobileBankRegistration3_SOAPSerializer;
    private static final int myCOMMITMIGRATIONRESULT_1_INDEX = 0;
    private static final int myMOBILEBANKTEMPLATE_2_INDEX = 1;
    private static final int myMBKCARD_3_INDEX = 2;
    private static final int myDISCONNECTEDPHONERESULT_4_INDEX = 3;
    private static final int myP2PREQUEST_5_INDEX = 4;
    private static final int myACCEPTINFO_6_INDEX = 5;
    private static final int myUESIMESSAGE_7_INDEX = 6;
    private static final int myERMBPHONE_8_INDEX = 7;
    private static final int myMOBILEBANKREGISTRATION_9_INDEX = 8;
    private static final int myMBKPHONE_10_INDEX = 9;
    private static final int myMQINFO_11_INDEX = 10;
    private static final int myNODEINFO_12_INDEX = 11;
    private static final int myUSERINFOCSA_13_INDEX = 12;
    private static final int myMBKREGISTRATION_14_INDEX = 13;
    private static final int myCODEOFFICE_15_INDEX = 14;
    private static final int myIKFLEXCEPTION_16_INDEX = 15;
    private static final int myMOBILEBANKREGISTRATION3_17_INDEX = 16;
    
    public MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myCommitMigrationResult_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult.class, ns2_CommitMigrationResult_TYPE_QNAME);
        ns2_myMobileBankTemplate_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate.class, ns2_MobileBankTemplate_TYPE_QNAME);
        ns2_myMbkCard_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.MbkCard.class, ns2_MbkCard_TYPE_QNAME);
        ns2_myDisconnectedPhoneResult_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult.class, ns2_DisconnectedPhoneResult_TYPE_QNAME);
        ns2_myP2PRequest_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.P2PRequest.class, ns2_P2PRequest_TYPE_QNAME);
        ns2_myAcceptInfo_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.AcceptInfo.class, ns2_AcceptInfo_TYPE_QNAME);
        ns2_myUESIMessage_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage.class, ns2_UESIMessage_TYPE_QNAME);
        ns2_myERMBPhone_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone.class, ns2_ERMBPhone_TYPE_QNAME);
        ns2_myMobileBankRegistration_InterfaceSOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration.class, ns2_MobileBankRegistration_TYPE_QNAME);
        ns2_myMBKPhone_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.MBKPhone.class, ns2_MBKPhone_TYPE_QNAME);
        ns2_myMQInfo_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo.class, ns2_MQInfo_TYPE_QNAME);
        ns2_myNodeInfo_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.NodeInfo.class, ns2_NodeInfo_TYPE_QNAME);
        ns2_myUserInfoCSA_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA.class, ns2_UserInfoCSA_TYPE_QNAME);
        ns2_myMBKRegistration_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.MBKRegistration.class, ns2_MBKRegistration_TYPE_QNAME);
        ns2_myCodeOffice_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.CodeOffice.class, ns2_CodeOffice_TYPE_QNAME);
        ns2_myIKFLException_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.IKFLException.class, ns2_IKFLException_TYPE_QNAME);
        ns2_myMobileBankRegistration3_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration3.class, ns2_MobileBankRegistration3_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct instance = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct();
        com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        for (int i=0; i<17; i++) {
            elementName = reader.getName();
            if (reader.getState() == XMLReader.END) {
                break;
            }
            if (elementName.equals(ns1_CommitMigrationResult_1_QNAME)) {
                member = ns2_myCommitMigrationResult_SOAPSerializer.deserialize(ns1_CommitMigrationResult_1_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCOMMITMIGRATIONRESULT_1_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCommitMigrationResult_1((com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_MobileBankTemplate_2_QNAME)) {
                member = ns2_myMobileBankTemplate_SOAPSerializer.deserialize(ns1_MobileBankTemplate_2_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMOBILEBANKTEMPLATE_2_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMobileBankTemplate_2((com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_MbkCard_3_QNAME)) {
                member = ns2_myMbkCard_SOAPSerializer.deserialize(ns1_MbkCard_3_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMBKCARD_3_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMbkCard_3((com.rssl.phizic.web.gate.services.mobilebank.generated.MbkCard)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_DisconnectedPhoneResult_4_QNAME)) {
                member = ns2_myDisconnectedPhoneResult_SOAPSerializer.deserialize(ns1_DisconnectedPhoneResult_4_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myDISCONNECTEDPHONERESULT_4_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setDisconnectedPhoneResult_4((com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_P2PRequest_5_QNAME)) {
                member = ns2_myP2PRequest_SOAPSerializer.deserialize(ns1_P2PRequest_5_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myP2PREQUEST_5_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setP2PRequest_5((com.rssl.phizic.web.gate.services.mobilebank.generated.P2PRequest)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_AcceptInfo_6_QNAME)) {
                member = ns2_myAcceptInfo_SOAPSerializer.deserialize(ns1_AcceptInfo_6_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myACCEPTINFO_6_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAcceptInfo_6((com.rssl.phizic.web.gate.services.mobilebank.generated.AcceptInfo)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_UESIMessage_7_QNAME)) {
                member = ns2_myUESIMessage_SOAPSerializer.deserialize(ns1_UESIMessage_7_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myUESIMESSAGE_7_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setUESIMessage_7((com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_ERMBPhone_8_QNAME)) {
                member = ns2_myERMBPhone_SOAPSerializer.deserialize(ns1_ERMBPhone_8_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myERMBPHONE_8_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setERMBPhone_8((com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_MobileBankRegistration_9_QNAME)) {
                member = ns2_myMobileBankRegistration_InterfaceSOAPSerializer.deserialize(ns1_MobileBankRegistration_9_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMOBILEBANKREGISTRATION_9_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMobileBankRegistration_9((com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_MBKPhone_10_QNAME)) {
                member = ns2_myMBKPhone_SOAPSerializer.deserialize(ns1_MBKPhone_10_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMBKPHONE_10_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMBKPhone_10((com.rssl.phizic.web.gate.services.mobilebank.generated.MBKPhone)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_MQInfo_11_QNAME)) {
                member = ns2_myMQInfo_SOAPSerializer.deserialize(ns1_MQInfo_11_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMQINFO_11_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMQInfo_11((com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_NodeInfo_12_QNAME)) {
                member = ns2_myNodeInfo_SOAPSerializer.deserialize(ns1_NodeInfo_12_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myNODEINFO_12_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setNodeInfo_12((com.rssl.phizic.web.gate.services.mobilebank.generated.NodeInfo)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_UserInfoCSA_13_QNAME)) {
                member = ns2_myUserInfoCSA_SOAPSerializer.deserialize(ns1_UserInfoCSA_13_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myUSERINFOCSA_13_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setUserInfoCSA_13((com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_MBKRegistration_14_QNAME)) {
                member = ns2_myMBKRegistration_SOAPSerializer.deserialize(ns1_MBKRegistration_14_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMBKREGISTRATION_14_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMBKRegistration_14((com.rssl.phizic.web.gate.services.mobilebank.generated.MBKRegistration)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_CodeOffice_15_QNAME)) {
                member = ns2_myCodeOffice_SOAPSerializer.deserialize(ns1_CodeOffice_15_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCODEOFFICE_15_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCodeOffice_15((com.rssl.phizic.web.gate.services.mobilebank.generated.CodeOffice)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_IKFLException_16_QNAME)) {
                member = ns2_myIKFLException_SOAPSerializer.deserialize(ns1_IKFLException_16_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myIKFLEXCEPTION_16_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setIKFLException_16((com.rssl.phizic.web.gate.services.mobilebank.generated.IKFLException)member);
                }
                reader.nextElementContent();
                continue;
            }
            if (elementName.equals(ns1_MobileBankRegistration3_17_QNAME)) {
                member = ns2_myMobileBankRegistration3_SOAPSerializer.deserialize(ns1_MobileBankRegistration3_17_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMOBILEBANKREGISTRATION3_17_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMobileBankRegistration3_17((com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration3)member);
                }
                reader.nextElementContent();
                continue;
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[] {ns1_MobileBankRegistration3_17_QNAME, elementName});
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct instance = (com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService_methodForAddClassesToWSDL_RequestStruct)obj;
        
        ns2_myCommitMigrationResult_SOAPSerializer.serialize(instance.getCommitMigrationResult_1(), ns1_CommitMigrationResult_1_QNAME, null, writer, context);
        ns2_myMobileBankTemplate_SOAPSerializer.serialize(instance.getMobileBankTemplate_2(), ns1_MobileBankTemplate_2_QNAME, null, writer, context);
        ns2_myMbkCard_SOAPSerializer.serialize(instance.getMbkCard_3(), ns1_MbkCard_3_QNAME, null, writer, context);
        ns2_myDisconnectedPhoneResult_SOAPSerializer.serialize(instance.getDisconnectedPhoneResult_4(), ns1_DisconnectedPhoneResult_4_QNAME, null, writer, context);
        ns2_myP2PRequest_SOAPSerializer.serialize(instance.getP2PRequest_5(), ns1_P2PRequest_5_QNAME, null, writer, context);
        ns2_myAcceptInfo_SOAPSerializer.serialize(instance.getAcceptInfo_6(), ns1_AcceptInfo_6_QNAME, null, writer, context);
        ns2_myUESIMessage_SOAPSerializer.serialize(instance.getUESIMessage_7(), ns1_UESIMessage_7_QNAME, null, writer, context);
        ns2_myERMBPhone_SOAPSerializer.serialize(instance.getERMBPhone_8(), ns1_ERMBPhone_8_QNAME, null, writer, context);
        ns2_myMobileBankRegistration_InterfaceSOAPSerializer.serialize(instance.getMobileBankRegistration_9(), ns1_MobileBankRegistration_9_QNAME, null, writer, context);
        ns2_myMBKPhone_SOAPSerializer.serialize(instance.getMBKPhone_10(), ns1_MBKPhone_10_QNAME, null, writer, context);
        ns2_myMQInfo_SOAPSerializer.serialize(instance.getMQInfo_11(), ns1_MQInfo_11_QNAME, null, writer, context);
        ns2_myNodeInfo_SOAPSerializer.serialize(instance.getNodeInfo_12(), ns1_NodeInfo_12_QNAME, null, writer, context);
        ns2_myUserInfoCSA_SOAPSerializer.serialize(instance.getUserInfoCSA_13(), ns1_UserInfoCSA_13_QNAME, null, writer, context);
        ns2_myMBKRegistration_SOAPSerializer.serialize(instance.getMBKRegistration_14(), ns1_MBKRegistration_14_QNAME, null, writer, context);
        ns2_myCodeOffice_SOAPSerializer.serialize(instance.getCodeOffice_15(), ns1_CodeOffice_15_QNAME, null, writer, context);
        ns2_myIKFLException_SOAPSerializer.serialize(instance.getIKFLException_16(), ns1_IKFLException_16_QNAME, null, writer, context);
        ns2_myMobileBankRegistration3_SOAPSerializer.serialize(instance.getMobileBankRegistration3_17(), ns1_MobileBankRegistration3_17_QNAME, null, writer, context);
    }
}
