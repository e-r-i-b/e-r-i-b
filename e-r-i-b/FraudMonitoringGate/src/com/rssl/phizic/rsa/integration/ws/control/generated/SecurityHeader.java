/**
 * SecurityHeader.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This identifies the credential used to authenticate the caller
 * to the RSA System
 */
public class SecurityHeader  implements java.io.Serializable {
    private java.lang.String callerCredential;

    private java.lang.String callerId;

    private com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod method;

    public SecurityHeader() {
    }

    public SecurityHeader(
           java.lang.String callerCredential,
           java.lang.String callerId,
           com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod method) {
           this.callerCredential = callerCredential;
           this.callerId = callerId;
           this.method = method;
    }


    /**
     * Gets the callerCredential value for this SecurityHeader.
     * 
     * @return callerCredential
     */
    public java.lang.String getCallerCredential() {
        return callerCredential;
    }


    /**
     * Sets the callerCredential value for this SecurityHeader.
     * 
     * @param callerCredential
     */
    public void setCallerCredential(java.lang.String callerCredential) {
        this.callerCredential = callerCredential;
    }


    /**
     * Gets the callerId value for this SecurityHeader.
     * 
     * @return callerId
     */
    public java.lang.String getCallerId() {
        return callerId;
    }


    /**
     * Sets the callerId value for this SecurityHeader.
     * 
     * @param callerId
     */
    public void setCallerId(java.lang.String callerId) {
        this.callerId = callerId;
    }


    /**
     * Gets the method value for this SecurityHeader.
     * 
     * @return method
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod getMethod() {
        return method;
    }


    /**
     * Sets the method value for this SecurityHeader.
     * 
     * @param method
     */
    public void setMethod(com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod method) {
        this.method = method;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecurityHeader)) return false;
        SecurityHeader other = (SecurityHeader) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.callerCredential==null && other.getCallerCredential()==null) || 
             (this.callerCredential!=null &&
              this.callerCredential.equals(other.getCallerCredential()))) &&
            ((this.callerId==null && other.getCallerId()==null) || 
             (this.callerId!=null &&
              this.callerId.equals(other.getCallerId()))) &&
            ((this.method==null && other.getMethod()==null) || 
             (this.method!=null &&
              this.method.equals(other.getMethod())));
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
        if (getCallerCredential() != null) {
            _hashCode += getCallerCredential().hashCode();
        }
        if (getCallerId() != null) {
            _hashCode += getCallerId().hashCode();
        }
        if (getMethod() != null) {
            _hashCode += getMethod().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecurityHeader.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SecurityHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callerCredential");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "callerCredential"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "callerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("method");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "method"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthorizationMethod"));
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
