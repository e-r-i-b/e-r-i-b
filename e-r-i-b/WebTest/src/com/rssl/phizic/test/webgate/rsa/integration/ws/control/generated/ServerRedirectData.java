/**
 * ServerRedirectData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the Server Redirect Data - returned in an anaylize
 * response for redirection after authentication
 */
public class ServerRedirectData  implements java.io.Serializable {
    private java.lang.String redirectUrl;

    private java.lang.String secretKey;

    public ServerRedirectData() {
    }

    public ServerRedirectData(
           java.lang.String redirectUrl,
           java.lang.String secretKey) {
           this.redirectUrl = redirectUrl;
           this.secretKey = secretKey;
    }


    /**
     * Gets the redirectUrl value for this ServerRedirectData.
     * 
     * @return redirectUrl
     */
    public java.lang.String getRedirectUrl() {
        return redirectUrl;
    }


    /**
     * Sets the redirectUrl value for this ServerRedirectData.
     * 
     * @param redirectUrl
     */
    public void setRedirectUrl(java.lang.String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    /**
     * Gets the secretKey value for this ServerRedirectData.
     * 
     * @return secretKey
     */
    public java.lang.String getSecretKey() {
        return secretKey;
    }


    /**
     * Sets the secretKey value for this ServerRedirectData.
     * 
     * @param secretKey
     */
    public void setSecretKey(java.lang.String secretKey) {
        this.secretKey = secretKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServerRedirectData)) return false;
        ServerRedirectData other = (ServerRedirectData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.redirectUrl==null && other.getRedirectUrl()==null) || 
             (this.redirectUrl!=null &&
              this.redirectUrl.equals(other.getRedirectUrl()))) &&
            ((this.secretKey==null && other.getSecretKey()==null) || 
             (this.secretKey!=null &&
              this.secretKey.equals(other.getSecretKey())));
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
        if (getRedirectUrl() != null) {
            _hashCode += getRedirectUrl().hashCode();
        }
        if (getSecretKey() != null) {
            _hashCode += getSecretKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServerRedirectData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ServerRedirectData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("redirectUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "redirectUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secretKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "secretKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
