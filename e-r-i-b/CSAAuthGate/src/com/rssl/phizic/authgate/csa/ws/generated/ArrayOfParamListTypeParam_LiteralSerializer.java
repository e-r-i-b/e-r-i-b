// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package com.rssl.phizic.authgate.csa.ws.generated;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.streaming.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;

public class ArrayOfParamListTypeParam_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final javax.xml.namespace.QName ns1_Param_QNAME = new QName("", "Param");
    private static final javax.xml.namespace.QName ns3_CheckSessionRs$2d$AuthData$2d$Param_TYPE_QNAME = new QName("http://csa.sbrf.ru/AuthService", "CheckSessionRs-AuthData-Param");
    private CombinedSerializer ns3_myCheckSessionRsAuthDataParam_LiteralSerializer;
    
    public ArrayOfParamListTypeParam_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public ArrayOfParamListTypeParam_LiteralSerializer(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myCheckSessionRsAuthDataParam_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRsAuthDataParam.class, ns3_CheckSessionRs$2d$AuthData$2d$Param_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam instance = new com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam();
        java.lang.Object member=null;
        javax.xml.namespace.QName elementName;
        java.util.List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_Param_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_Param_QNAME))) {
                    value = ns3_myCheckSessionRsAuthDataParam_LiteralSerializer.deserialize(ns1_Param_QNAME, reader, context);
                    if (value == null) {
                        throw new DeserializationException("literal.unexpectedNull");
                    }
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRsAuthDataParam[values.size()];
            member = values.toArray((Object[]) member);
            instance.setParam((com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRsAuthDataParam[])member);
        }
        else {
            instance.setParam(new com.rssl.phizic.authgate.csa.ws.generated.CheckSessionRsAuthDataParam[0]);
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam instance = (com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam instance = (com.rssl.phizic.authgate.csa.ws.generated.ArrayOfParamListTypeParam)obj;
        
        if (instance.getParam() != null) {
            for (int i = 0; i < instance.getParam().length; ++i) {
                ns3_myCheckSessionRsAuthDataParam_LiteralSerializer.serialize(instance.getParam()[i], ns1_Param_QNAME, null, writer, context);
            }
        }
    }
}
