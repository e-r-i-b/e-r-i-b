// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.web.gate.services.bankroll.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class OverdraftInfo_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_availableLimit_QNAME = new QName("", "availableLimit");
    private static final javax.xml.namespace.QName ns2_Money_TYPE_QNAME = new QName("http://generated.bankroll.services.gate.web.phizic.rssl.com", "Money");
    private CombinedSerializer ns2_myMoney_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_cardId_QNAME = new QName("", "cardId");
    private static final javax.xml.namespace.QName ns3_long_TYPE_QNAME = SchemaConstants.QNAME_TYPE_LONG;
    private CombinedSerializer ns3_myns3__long__long_Long_Serializer;
    private static final javax.xml.namespace.QName ns1_currentOverdraftSum_QNAME = new QName("", "currentOverdraftSum");
    private static final javax.xml.namespace.QName ns1_limit_QNAME = new QName("", "limit");
    private static final javax.xml.namespace.QName ns1_minimalPayment_QNAME = new QName("", "minimalPayment");
    private static final javax.xml.namespace.QName ns1_minimalPaymentDate_QNAME = new QName("", "minimalPaymentDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_openDate_QNAME = new QName("", "openDate");
    private static final javax.xml.namespace.QName ns1_ownSum_QNAME = new QName("", "ownSum");
    private static final javax.xml.namespace.QName ns1_rate_QNAME = new QName("", "rate");
    private static final javax.xml.namespace.QName ns1_technicalOverdraftSum_QNAME = new QName("", "technicalOverdraftSum");
    private static final javax.xml.namespace.QName ns1_technicalPenalty_QNAME = new QName("", "technicalPenalty");
    private static final javax.xml.namespace.QName ns1_totalDebtSum_QNAME = new QName("", "totalDebtSum");
    private static final javax.xml.namespace.QName ns1_unsetltedDebtCreateDate_QNAME = new QName("", "unsetltedDebtCreateDate");
    private static final javax.xml.namespace.QName ns1_unsettledDebtSum_QNAME = new QName("", "unsettledDebtSum");
    private static final javax.xml.namespace.QName ns1_unsettledPenalty_QNAME = new QName("", "unsettledPenalty");
    private static final int myAVAILABLELIMIT_INDEX = 0;
    private static final int myCARDID_INDEX = 1;
    private static final int myCURRENTOVERDRAFTSUM_INDEX = 2;
    private static final int myLIMIT_INDEX = 3;
    private static final int myMINIMALPAYMENT_INDEX = 4;
    private static final int myMINIMALPAYMENTDATE_INDEX = 5;
    private static final int myOPENDATE_INDEX = 6;
    private static final int myOWNSUM_INDEX = 7;
    private static final int myRATE_INDEX = 8;
    private static final int myTECHNICALOVERDRAFTSUM_INDEX = 9;
    private static final int myTECHNICALPENALTY_INDEX = 10;
    private static final int myTOTALDEBTSUM_INDEX = 11;
    private static final int myUNSETLTEDDEBTCREATEDATE_INDEX = 12;
    private static final int myUNSETTLEDDEBTSUM_INDEX = 13;
    private static final int myUNSETTLEDPENALTY_INDEX = 14;
    
    public OverdraftInfo_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myMoney_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizic.web.gate.services.bankroll.generated.Money.class, ns2_Money_TYPE_QNAME);
        ns3_myns3__long__long_Long_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, long.class, ns3_long_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo instance = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo();
        com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_availableLimit_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_availableLimit_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myAVAILABLELIMIT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setAvailableLimit((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_cardId_QNAME)) {
                member = ns3_myns3__long__long_Long_Serializer.deserialize(ns1_cardId_QNAME, reader, context);
                instance.setCardId(((Long)member).longValue());
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_currentOverdraftSum_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_currentOverdraftSum_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCURRENTOVERDRAFTSUM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setCurrentOverdraftSum((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_limit_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_limit_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myLIMIT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setLimit((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_minimalPayment_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_minimalPayment_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myMINIMALPAYMENT_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setMinimalPayment((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_minimalPaymentDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_minimalPaymentDate_QNAME, reader, context);
                instance.setMinimalPaymentDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_openDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_openDate_QNAME, reader, context);
                instance.setOpenDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_ownSum_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_ownSum_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOWNSUM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOwnSum((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_rate_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_rate_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myRATE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setRate((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_technicalOverdraftSum_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_technicalOverdraftSum_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTECHNICALOVERDRAFTSUM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTechnicalOverdraftSum((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_technicalPenalty_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_technicalPenalty_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTECHNICALPENALTY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTechnicalPenalty((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_totalDebtSum_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_totalDebtSum_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTOTALDEBTSUM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTotalDebtSum((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_unsetltedDebtCreateDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_unsetltedDebtCreateDate_QNAME, reader, context);
                instance.setUnsetltedDebtCreateDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_unsettledDebtSum_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_unsettledDebtSum_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myUNSETTLEDDEBTSUM_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setUnsettledDebtSum((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_unsettledPenalty_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_unsettledPenalty_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myUNSETTLEDPENALTY_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setUnsettledPenalty((com.rssl.phizic.web.gate.services.bankroll.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo instance = (com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo instance = (com.rssl.phizic.web.gate.services.bankroll.generated.OverdraftInfo)obj;
        
        ns2_myMoney_SOAPSerializer.serialize(instance.getAvailableLimit(), ns1_availableLimit_QNAME, null, writer, context);
        ns3_myns3__long__long_Long_Serializer.serialize(new Long(instance.getCardId()), ns1_cardId_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getCurrentOverdraftSum(), ns1_currentOverdraftSum_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getLimit(), ns1_limit_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getMinimalPayment(), ns1_minimalPayment_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getMinimalPaymentDate(), ns1_minimalPaymentDate_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getOpenDate(), ns1_openDate_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getOwnSum(), ns1_ownSum_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getRate(), ns1_rate_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getTechnicalOverdraftSum(), ns1_technicalOverdraftSum_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getTechnicalPenalty(), ns1_technicalPenalty_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getTotalDebtSum(), ns1_totalDebtSum_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getUnsetltedDebtCreateDate(), ns1_unsetltedDebtCreateDate_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getUnsettledDebtSum(), ns1_unsettledDebtSum_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getUnsettledPenalty(), ns1_unsettledPenalty_QNAME, null, writer, context);
    }
}