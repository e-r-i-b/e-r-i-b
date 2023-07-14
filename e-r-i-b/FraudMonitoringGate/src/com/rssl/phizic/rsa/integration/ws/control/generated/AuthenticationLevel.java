/**
 * AuthenticationLevel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * Allows customers using their own authentication mechanism to pass
 * data to the RSA AA System
 */
public class AuthenticationLevel  implements java.io.Serializable {
    private java.lang.Integer attemptsTryCount;

    private java.lang.Integer level;

    private java.lang.Boolean successful;

    public AuthenticationLevel() {
    }

    public AuthenticationLevel(
           java.lang.Integer attemptsTryCount,
           java.lang.Integer level,
           java.lang.Boolean successful) {
           this.attemptsTryCount = attemptsTryCount;
           this.level = level;
           this.successful = successful;
    }


    /**
     * Gets the attemptsTryCount value for this AuthenticationLevel.
     * 
     * @return attemptsTryCount
     */
    public java.lang.Integer getAttemptsTryCount() {
        return attemptsTryCount;
    }


    /**
     * Sets the attemptsTryCount value for this AuthenticationLevel.
     * 
     * @param attemptsTryCount
     */
    public void setAttemptsTryCount(java.lang.Integer attemptsTryCount) {
        this.attemptsTryCount = attemptsTryCount;
    }


    /**
     * Gets the level value for this AuthenticationLevel.
     * 
     * @return level
     */
    public java.lang.Integer getLevel() {
        return level;
    }


    /**
     * Sets the level value for this AuthenticationLevel.
     * 
     * @param level
     */
    public void setLevel(java.lang.Integer level) {
        this.level = level;
    }


    /**
     * Gets the successful value for this AuthenticationLevel.
     * 
     * @return successful
     */
    public java.lang.Boolean getSuccessful() {
        return successful;
    }


    /**
     * Sets the successful value for this AuthenticationLevel.
     * 
     * @param successful
     */
    public void setSuccessful(java.lang.Boolean successful) {
        this.successful = successful;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AuthenticationLevel)) return false;
        AuthenticationLevel other = (AuthenticationLevel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attemptsTryCount==null && other.getAttemptsTryCount()==null) || 
             (this.attemptsTryCount!=null &&
              this.attemptsTryCount.equals(other.getAttemptsTryCount()))) &&
            ((this.level==null && other.getLevel()==null) || 
             (this.level!=null &&
              this.level.equals(other.getLevel()))) &&
            ((this.successful==null && other.getSuccessful()==null) || 
             (this.successful!=null &&
              this.successful.equals(other.getSuccessful())));
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
        if (getAttemptsTryCount() != null) {
            _hashCode += getAttemptsTryCount().hashCode();
        }
        if (getLevel() != null) {
            _hashCode += getLevel().hashCode();
        }
        if (getSuccessful() != null) {
            _hashCode += getSuccessful().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuthenticationLevel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticationLevel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attemptsTryCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "attemptsTryCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("level");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "level"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("successful");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "successful"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
