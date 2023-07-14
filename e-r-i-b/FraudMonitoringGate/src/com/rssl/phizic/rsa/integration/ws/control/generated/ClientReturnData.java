/**
 * ClientReturnData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines the Client Return Data - sent during an anaylze request
 * for re-direction after authentication
 */
public class ClientReturnData  implements java.io.Serializable {
    private java.lang.String returnUrl;

    private com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod validationMethod;

    public ClientReturnData() {
    }

    public ClientReturnData(
           java.lang.String returnUrl,
           com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod validationMethod) {
           this.returnUrl = returnUrl;
           this.validationMethod = validationMethod;
    }


    /**
     * Gets the returnUrl value for this ClientReturnData.
     * 
     * @return returnUrl
     */
    public java.lang.String getReturnUrl() {
        return returnUrl;
    }


    /**
     * Sets the returnUrl value for this ClientReturnData.
     * 
     * @param returnUrl
     */
    public void setReturnUrl(java.lang.String returnUrl) {
        this.returnUrl = returnUrl;
    }


    /**
     * Gets the validationMethod value for this ClientReturnData.
     * 
     * @return validationMethod
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod getValidationMethod() {
        return validationMethod;
    }


    /**
     * Sets the validationMethod value for this ClientReturnData.
     * 
     * @param validationMethod
     */
    public void setValidationMethod(com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod validationMethod) {
        this.validationMethod = validationMethod;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientReturnData)) return false;
        ClientReturnData other = (ClientReturnData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.returnUrl==null && other.getReturnUrl()==null) || 
             (this.returnUrl!=null &&
              this.returnUrl.equals(other.getReturnUrl()))) &&
            ((this.validationMethod==null && other.getValidationMethod()==null) || 
             (this.validationMethod!=null &&
              this.validationMethod.equals(other.getValidationMethod())));
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
        if (getReturnUrl() != null) {
            _hashCode += getReturnUrl().hashCode();
        }
        if (getValidationMethod() != null) {
            _hashCode += getValidationMethod().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientReturnData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientReturnData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "returnUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validationMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "validationMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthorizationMethod"));
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
