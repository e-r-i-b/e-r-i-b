/**
 * ConsumeOutMessageArg.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated;

public class ConsumeOutMessageArg  implements java.io.Serializable {
    private com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.Auth auth;

    private com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.OutMessage[] outMessage;

    public ConsumeOutMessageArg() {
    }

    public ConsumeOutMessageArg(
           com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.Auth auth,
           com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.OutMessage[] outMessage) {
           this.auth = auth;
           this.outMessage = outMessage;
    }


    /**
     * Gets the auth value for this ConsumeOutMessageArg.
     * 
     * @return auth
     */
    public com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.Auth getAuth() {
        return auth;
    }


    /**
     * Sets the auth value for this ConsumeOutMessageArg.
     * 
     * @param auth
     */
    public void setAuth(com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.Auth auth) {
        this.auth = auth;
    }


    /**
     * Gets the outMessage value for this ConsumeOutMessageArg.
     * 
     * @return outMessage
     */
    public com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.OutMessage[] getOutMessage() {
        return outMessage;
    }


    /**
     * Sets the outMessage value for this ConsumeOutMessageArg.
     * 
     * @param outMessage
     */
    public void setOutMessage(com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.OutMessage[] outMessage) {
        this.outMessage = outMessage;
    }

    public com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.OutMessage getOutMessage(int i) {
        return this.outMessage[i];
    }

    public void setOutMessage(int i, com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.OutMessage _value) {
        this.outMessage[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsumeOutMessageArg)) return false;
        ConsumeOutMessageArg other = (ConsumeOutMessageArg) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.auth==null && other.getAuth()==null) || 
             (this.auth!=null &&
              this.auth.equals(other.getAuth()))) &&
            ((this.outMessage==null && other.getOutMessage()==null) || 
             (this.outMessage!=null &&
              java.util.Arrays.equals(this.outMessage, other.getOutMessage())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAuth() != null) {
            _hashCode += getAuth().hashCode();
        }
        if (getOutMessage() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOutMessage());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOutMessage(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsumeOutMessageArg.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "ConsumeOutMessageArg"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "auth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "Auth"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "outMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "OutMessage"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
