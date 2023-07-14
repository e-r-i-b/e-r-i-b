// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizicgate.wsgate.services.cache.generated;

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

public class CacheServiceImpl_SerializerRegistry implements SerializerConstants {
    public CacheServiceImpl_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearClientProductsCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearClientProductsCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearClientProductsCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearSecurityAccountCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearSecurityAccountCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearSecurityAccountCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Address");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Address_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Address.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearDepoAccountCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearDepoAccountCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearDepoAccountCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "ClientState");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.ClientState_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.ClientState.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "DepoAccount");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.DepoAccount_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.DepoAccount.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearCardCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearCardCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearCardCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearCurrencyRateCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearCurrencyRateCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearCurrencyRateCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearClientCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearClientCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearClientCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Loan");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Loan_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Loan.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "ArrayOfstring");
            QName elemName = new QName("", "item");
            CombinedSerializer serializer = new ObjectArraySerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING , 
                elemName, SchemaConstants.QNAME_TYPE_STRING, java.lang.String.class, 1, null, SOAPVersion.SOAP_11);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,java.lang.String[].class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearAutoPaymentCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoPaymentCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoPaymentCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "LoyaltyProgram");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.LoyaltyProgram_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.LoyaltyProgram.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "LongOffer");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.LongOffer_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.LongOffer.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Currency");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Currency_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Currency.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Money");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Money_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Money.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "IMAccount");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.IMAccount_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.IMAccount.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearInsuranceAppCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearInsuranceAppCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearInsuranceAppCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearCardCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearCardCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearCardCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearLoanCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLoanCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLoanCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearDepoAccountCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearDepoAccountCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearDepoAccountCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Account");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Account_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Account.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearInsuranceAppCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearInsuranceAppCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearInsuranceAppCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearAutoSubscriptionCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoSubscriptionCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoSubscriptionCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearDepositCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearDepositCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearDepositCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearAutoSubscriptionCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoSubscriptionCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoSubscriptionCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "AutoSubscription");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.AutoSubscription_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.AutoSubscription.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearSecurityAccountCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearSecurityAccountCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearSecurityAccountCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "ArrayOfCard");
            QName elemName = new QName("", "item");
            QName elemType = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Card");
            CombinedSerializer serializer = new ObjectArraySerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING , 
                elemName, elemType, com.rssl.phizicgate.wsgate.services.cache.generated.Card.class, 1, null, SOAPVersion.SOAP_11);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Card[].class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearClientProductsCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearClientProductsCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearClientProductsCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearLoyaltyProgramCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLoyaltyProgramCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLoyaltyProgramCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearAutoPaymentCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoPaymentCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAutoPaymentCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Office");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Office_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Office.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearLongOfferCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLongOfferCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLongOfferCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Client");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Client_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Client.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "DateSpan");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.DateSpan_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.DateSpan.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "AutoPayment");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.AutoPayment.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "InsuranceApp");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.InsuranceApp_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.InsuranceApp.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearAccountCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAccountCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAccountCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearLongOfferCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLongOfferCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLongOfferCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "SecurityAccount");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearCurrencyRateCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearCurrencyRateCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearCurrencyRateCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "PolicyDetails");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.PolicyDetails_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.PolicyDetails.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Code");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Code_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Code.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearLoyaltyProgramCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLoyaltyProgramCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLoyaltyProgramCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "CurrencyRate");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearAccountCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAccountCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearAccountCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearIMACache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearIMACache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearIMACache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearDepositCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearDepositCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearDepositCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearLoanCacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLoanCache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearLoanCache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Deposit");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Deposit_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Deposit.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearIMACacheResponse");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearIMACache_ResponseStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearIMACache_ResponseStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "clearClientCache");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearClientCache_RequestStruct_SOAPSerializer(type,
                DONT_ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(DONT_SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.CacheService_clearClientCache_RequestStruct.class, type, serializer);
        }
        {
            QName type = new QName("http://generated.cache.services.gate.web.phizic.rssl.com", "Card");
            CombinedSerializer serializer = new com.rssl.phizicgate.wsgate.services.cache.generated.Card_SOAPSerializer(type,
                ENCODE_TYPE, NULLABLE, SOAPConstants.NS_SOAP_ENCODING);
            serializer = new ReferenceableSerializerImpl(SERIALIZE_AS_REF, serializer, SOAPVersion.SOAP_11);
            registerSerializer(mapping,com.rssl.phizicgate.wsgate.services.cache.generated.Card.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}
