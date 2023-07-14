// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.csa.ws.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class MoveSessionRsType_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final javax.xml.namespace.QName ns1_Status_QNAME = new QName("", "Status");
    private static final javax.xml.namespace.QName ns3_StatusType_TYPE_QNAME = new QName("http://csa.sbrf.ru/AuthService", "StatusType");
    private CombinedSerializer ns3_myStatusType_LiteralSerializer;
    private static final javax.xml.namespace.QName ns1_SID_QNAME = new QName("", "SID");
    private static final javax.xml.namespace.QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    private static final javax.xml.namespace.QName ns1_AuthToken_QNAME = new QName("", "AuthToken");
    private static final javax.xml.namespace.QName ns3_AuthTokenType_TYPE_QNAME = new QName("http://csa.sbrf.ru/AuthService", "AuthTokenType");
    private CombinedSerializer ns3_myAuthTokenType_LiteralSerializer;
    private static final javax.xml.namespace.QName ns1_AuthContext_QNAME = new QName("", "AuthContext");
    private static final javax.xml.namespace.QName ns3_GetPutListType_TYPE_QNAME = new QName("http://csa.sbrf.ru/AuthService", "GetPutListType");
    private CombinedSerializer ns3_myGetPutListType_LiteralSerializer;
    
    public MoveSessionRsType_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public MoveSessionRsType_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myStatusType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizic.authgate.csa.ws.generated.StatusType.class, ns3_StatusType_TYPE_QNAME);
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        ns3_myAuthTokenType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizic.authgate.csa.ws.generated.AuthTokenType.class, ns3_AuthTokenType_TYPE_QNAME);
        ns3_myGetPutListType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizic.authgate.csa.ws.generated.GetPutListType.class, ns3_GetPutListType_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.MoveSessionRsType instance = new com.rssl.phizic.authgate.csa.ws.generated.MoveSessionRsType();
        java.lang.Object member=null;
        javax.xml.namespace.QName elementName;
        java.util.List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_Status_QNAME)) {
                member = ns3_myStatusType_LiteralSerializer.deserialize(ns1_Status_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setStatus((com.rssl.phizic.authgate.csa.ws.generated.StatusType)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_SID_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_SID_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setSID((java.lang.String)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_AuthToken_QNAME)) {
                member = ns3_myAuthTokenType_LiteralSerializer.deserialize(ns1_AuthToken_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setAuthToken((com.rssl.phizic.authgate.csa.ws.generated.AuthTokenType)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_AuthContext_QNAME)) {
                member = ns3_myGetPutListType_LiteralSerializer.deserialize(ns1_AuthContext_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setAuthContext((com.rssl.phizic.authgate.csa.ws.generated.GetPutListType)member);
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.MoveSessionRsType instance = (com.rssl.phizic.authgate.csa.ws.generated.MoveSessionRsType)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.MoveSessionRsType instance = (com.rssl.phizic.authgate.csa.ws.generated.MoveSessionRsType)obj;
        
        if (instance.getStatus() != null) {
            ns3_myStatusType_LiteralSerializer.serialize(instance.getStatus(), ns1_Status_QNAME, null, writer, context);
        }
        if (instance.getSID() != null) {
            ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getSID(), ns1_SID_QNAME, null, writer, context);
        }
        if (instance.getAuthToken() != null) {
            ns3_myAuthTokenType_LiteralSerializer.serialize(instance.getAuthToken(), ns1_AuthToken_QNAME, null, writer, context);
        }
        if (instance.getAuthContext() != null) {
            ns3_myGetPutListType_LiteralSerializer.serialize(instance.getAuthContext(), ns1_AuthContext_QNAME, null, writer, context);
        }
    }
}
