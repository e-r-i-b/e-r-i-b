// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.deposits.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class DepositAbstract_SOAPSerializer extends ObjectSerializerBase implements Initializable {
    private static final javax.xml.namespace.QName ns1_closingBalance_QNAME = new QName("", "closingBalance");
    private static final javax.xml.namespace.QName ns2_Money_TYPE_QNAME = new QName("http://generated.deposits.services.gate.web.phizic.rssl.com", "Money");
    private CombinedSerializer ns2_myMoney_SOAPSerializer;
    private static final javax.xml.namespace.QName ns1_fromDate_QNAME = new QName("", "fromDate");
    private static final javax.xml.namespace.QName ns3_dateTime_TYPE_QNAME = SchemaConstants.QNAME_TYPE_DATE_TIME;
    private CombinedSerializer ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer;
    private static final javax.xml.namespace.QName ns1_openingBalance_QNAME = new QName("", "openingBalance");
    private static final javax.xml.namespace.QName ns1_previousOperationDate_QNAME = new QName("", "previousOperationDate");
    private static final javax.xml.namespace.QName ns1_toDate_QNAME = new QName("", "toDate");
    private static final javax.xml.namespace.QName ns1_transactions_QNAME = new QName("", "transactions");
    private static final javax.xml.namespace.QName ns5_list_TYPE_QNAME = InternalEncodingConstants.QNAME_TYPE_LIST;
    private CombinedSerializer ns5_myns5_list__CollectionInterfaceSerializer;
    private static final int myCLOSINGBALANCE_INDEX = 0;
    private static final int myFROMDATE_INDEX = 1;
    private static final int myOPENINGBALANCE_INDEX = 2;
    private static final int myPREVIOUSOPERATIONDATE_INDEX = 3;
    private static final int myTODATE_INDEX = 4;
    private static final int myTRANSACTIONS_INDEX = 5;
    
    public DepositAbstract_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns2_myMoney_SOAPSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, com.rssl.phizicgate.wsgate.services.deposits.generated.Money.class, ns2_Money_TYPE_QNAME);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.Calendar.class, ns3_dateTime_TYPE_QNAME);
        ns5_myns5_list__CollectionInterfaceSerializer = (CombinedSerializer)registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.util.List.class, ns5_list_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract instance = new com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract();
        com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_closingBalance_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_closingBalance_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myCLOSINGBALANCE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setClosingBalance((com.rssl.phizicgate.wsgate.services.deposits.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_fromDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_fromDate_QNAME, reader, context);
                instance.setFromDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_openingBalance_QNAME)) {
                member = ns2_myMoney_SOAPSerializer.deserialize(ns1_openingBalance_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myOPENINGBALANCE_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setOpeningBalance((com.rssl.phizicgate.wsgate.services.deposits.generated.Money)member);
                }
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_previousOperationDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_previousOperationDate_QNAME, reader, context);
                instance.setPreviousOperationDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_toDate_QNAME)) {
                member = ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.deserialize(ns1_toDate_QNAME, reader, context);
                instance.setToDate((java.util.Calendar)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_transactions_QNAME)) {
                member = ns5_myns5_list__CollectionInterfaceSerializer.deserialize(ns1_transactions_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myTRANSACTIONS_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setTransactions((java.util.List)member);
                }
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract)obj;
        
    }
    
    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract instance = (com.rssl.phizicgate.wsgate.services.deposits.generated.DepositAbstract)obj;
        
        ns2_myMoney_SOAPSerializer.serialize(instance.getClosingBalance(), ns1_closingBalance_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getFromDate(), ns1_fromDate_QNAME, null, writer, context);
        ns2_myMoney_SOAPSerializer.serialize(instance.getOpeningBalance(), ns1_openingBalance_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getPreviousOperationDate(), ns1_previousOperationDate_QNAME, null, writer, context);
        ns3_myns3_dateTime__java_util_Calendar_DateTimeCalendar_Serializer.serialize(instance.getToDate(), ns1_toDate_QNAME, null, writer, context);
        ns5_myns5_list__CollectionInterfaceSerializer.serialize(instance.getTransactions(), ns1_transactions_QNAME, null, writer, context);
    }
}
