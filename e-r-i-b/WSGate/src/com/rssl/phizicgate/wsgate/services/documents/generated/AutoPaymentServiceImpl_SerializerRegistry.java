// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.documents.generated;

import com.sun.xml.rpc.client.BasicService;
import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.rpc.*;
import javax.xml.rpc.encoding.*;
import javax.xml.namespace.QName;

public class AutoPaymentServiceImpl_SerializerRegistry implements SerializerConstants {
    public AutoPaymentServiceImpl_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getAutoPaymentResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getAutoPayment_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getAutoPayment_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Throwable");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.Throwable_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.Throwable.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getSheduleReport");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getSheduleReport_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getSheduleReport_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateCard");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateCard_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateCard_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Card");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.Card_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.Card.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getSheduleReport2Response");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getSheduleReport2_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getSheduleReport2_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getAutoPayment");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getAutoPayment_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getAutoPayment_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getAllowedAutoPaymentTypesResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getAllowedAutoPaymentTypes_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getAllowedAutoPaymentTypes_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateScheduleItemResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "AutoPayment");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPayment.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateCardResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateCard_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateCard_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "checkPaymentPossibilityExecution");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_checkPaymentPossibilityExecution_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_checkPaymentPossibilityExecution_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getClientsAutoPaymentsResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "GroupResult");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.GroupResult_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.GroupResult.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getSheduleReportResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getSheduleReport_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getSheduleReport_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateIKFLExceptionResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateIKFLException_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateIKFLException_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "StackTraceElement");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.StackTraceElement_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.StackTraceElement.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getAllowedAutoPaymentTypes");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getAllowedAutoPaymentTypes_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getAllowedAutoPaymentTypes_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Office");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.Office_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.Office.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "ScheduleItem");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.ScheduleItem_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.ScheduleItem.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateScheduleItem");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateScheduleItem_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getSheduleReport2");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getSheduleReport2_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getSheduleReport2_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "checkPaymentPossibilityExecutionResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_checkPaymentPossibilityExecution_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_checkPaymentPossibilityExecution_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "IKFLException");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.IKFLException_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.IKFLException.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "ArrayOfstring");
            QName elemName = new QName("", "item");
            CombinedSerializer serializer = new ObjectArraySerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING , 
                elemName, SchemaConstants.QNAME_TYPE_STRING, java.lang.String.class, 1, null, SOAPVersion.SOAP_11);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,java.lang.String[].class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateAutoPaymentResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateAutoPayment_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateAutoPayment_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateAutoPayment");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateAutoPayment_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateAutoPayment_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "ArrayOfStackTraceElement");
            QName elemName = new QName("", "item");
            QName elemType = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "StackTraceElement");
            CombinedSerializer serializer = new ObjectArraySerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING , 
                elemName, elemType, com.rssl.phizicgate.wsgate.services.documents.generated.StackTraceElement.class, 1, null, SOAPVersion.SOAP_11);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.StackTraceElement[].class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "ErrorMessage");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.ErrorMessage_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.ErrorMessage.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Code");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.Code_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.Code.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Currency");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.Currency_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.Currency.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "getClientsAutoPayments");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService_getClientsAutoPayments_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "Money");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.Money_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.Money.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.documents.services.gate.web.phizic.rssl.com", "__forGenerateIKFLException");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateIKFLException_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.documents.generated.AutoPaymentService___forGenerateIKFLException_RequestStruct.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}