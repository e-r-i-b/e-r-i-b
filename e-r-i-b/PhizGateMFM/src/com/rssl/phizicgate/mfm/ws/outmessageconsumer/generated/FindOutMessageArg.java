/**
 * FindOutMessageArg.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated;

public class FindOutMessageArg  implements java.io.Serializable {
    private com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.Auth auth;

    private java.lang.String[] outMessageId;

    public FindOutMessageArg() {
    }

    public FindOutMessageArg(
           com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.Auth auth,
           java.lang.String[] outMessageId) {
           this.auth = auth;
           this.outMessageId = outMessageId;
    }


    /**
     * Gets the auth value for this FindOutMessageArg.
     * 
     * @return auth
     */
    public com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.Auth getAuth() {
        return auth;
    }


    /**
     * Sets the auth value for this FindOutMessageArg.
     * 
     * @param auth
     */
    public void setAuth(com.rssl.phizicgate.mfm.ws.outmessageconsumer.generated.Auth auth) {
        this.auth = auth;
    }


    /**
     * Gets the outMessageId value for this FindOutMessageArg.
     * 
     * @return outMessageId
     */
    public java.lang.String[] getOutMessageId() {
        return outMessageId;
    }


    /**
     * Sets the outMessageId value for this FindOutMessageArg.
     * 
     * @param outMessageId
     */
    public void setOutMessageId(java.lang.String[] outMessageId) {
        this.outMessageId = outMessageId;
    }

    public java.lang.String getOutMessageId(int i) {
        return this.outMessageId[i];
    }

    public void setOutMessageId(int i, java.lang.String _value) {
        this.outMessageId[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindOutMessageArg)) return false;
        FindOutMessageArg other = (FindOutMessageArg) obj;
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
            ((this.outMessageId==null && other.getOutMessageId()==null) || 
             (this.outMessageId!=null &&
              java.util.Arrays.equals(this.outMessageId, other.getOutMessageId())));
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
        if (getOutMessageId() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOutMessageId());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOutMessageId(), i);
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
        new org.apache.axis.description.TypeDesc(FindOutMessageArg.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "FindOutMessageArg"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "auth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "Auth"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outMessageId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "outMessageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
