/**
 * AuthenticationResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the result of an authentication
 */
public class AuthenticationResult  implements java.io.Serializable {
    private java.lang.String authStatusCode;

    private java.lang.Integer risk;

    public AuthenticationResult() {
    }

    public AuthenticationResult(
           java.lang.String authStatusCode,
           java.lang.Integer risk) {
           this.authStatusCode = authStatusCode;
           this.risk = risk;
    }


    /**
     * Gets the authStatusCode value for this AuthenticationResult.
     * 
     * @return authStatusCode
     */
    public java.lang.String getAuthStatusCode() {
        return authStatusCode;
    }


    /**
     * Sets the authStatusCode value for this AuthenticationResult.
     * 
     * @param authStatusCode
     */
    public void setAuthStatusCode(java.lang.String authStatusCode) {
        this.authStatusCode = authStatusCode;
    }


    /**
     * Gets the risk value for this AuthenticationResult.
     * 
     * @return risk
     */
    public java.lang.Integer getRisk() {
        return risk;
    }


    /**
     * Sets the risk value for this AuthenticationResult.
     * 
     * @param risk
     */
    public void setRisk(java.lang.Integer risk) {
        this.risk = risk;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AuthenticationResult)) return false;
        AuthenticationResult other = (AuthenticationResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authStatusCode==null && other.getAuthStatusCode()==null) || 
             (this.authStatusCode!=null &&
              this.authStatusCode.equals(other.getAuthStatusCode()))) &&
            ((this.risk==null && other.getRisk()==null) || 
             (this.risk!=null &&
              this.risk.equals(other.getRisk())));
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
        if (getAuthStatusCode() != null) {
            _hashCode += getAuthStatusCode().hashCode();
        }
        if (getRisk() != null) {
            _hashCode += getRisk().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuthenticationResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticationResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authStatusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "authStatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("risk");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "risk"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
