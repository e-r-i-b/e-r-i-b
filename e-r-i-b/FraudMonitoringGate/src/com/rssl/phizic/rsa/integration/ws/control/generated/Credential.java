/**
 * Credential.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines what makes up a credential
 */
public class Credential  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialStatus credentialStatus;

    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType credentialType;

    private java.lang.String genericCredentialType;

    public Credential() {
    }

    public Credential(
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialStatus credentialStatus,
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType credentialType,
           java.lang.String genericCredentialType) {
           this.credentialStatus = credentialStatus;
           this.credentialType = credentialType;
           this.genericCredentialType = genericCredentialType;
    }


    /**
     * Gets the credentialStatus value for this Credential.
     * 
     * @return credentialStatus
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialStatus getCredentialStatus() {
        return credentialStatus;
    }


    /**
     * Sets the credentialStatus value for this Credential.
     * 
     * @param credentialStatus
     */
    public void setCredentialStatus(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialStatus credentialStatus) {
        this.credentialStatus = credentialStatus;
    }


    /**
     * Gets the credentialType value for this Credential.
     * 
     * @return credentialType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType getCredentialType() {
        return credentialType;
    }


    /**
     * Sets the credentialType value for this Credential.
     * 
     * @param credentialType
     */
    public void setCredentialType(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType credentialType) {
        this.credentialType = credentialType;
    }


    /**
     * Gets the genericCredentialType value for this Credential.
     * 
     * @return genericCredentialType
     */
    public java.lang.String getGenericCredentialType() {
        return genericCredentialType;
    }


    /**
     * Sets the genericCredentialType value for this Credential.
     * 
     * @param genericCredentialType
     */
    public void setGenericCredentialType(java.lang.String genericCredentialType) {
        this.genericCredentialType = genericCredentialType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Credential)) return false;
        Credential other = (Credential) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.credentialStatus==null && other.getCredentialStatus()==null) || 
             (this.credentialStatus!=null &&
              this.credentialStatus.equals(other.getCredentialStatus()))) &&
            ((this.credentialType==null && other.getCredentialType()==null) || 
             (this.credentialType!=null &&
              this.credentialType.equals(other.getCredentialType()))) &&
            ((this.genericCredentialType==null && other.getGenericCredentialType()==null) || 
             (this.genericCredentialType!=null &&
              this.genericCredentialType.equals(other.getGenericCredentialType())));
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
        if (getCredentialStatus() != null) {
            _hashCode += getCredentialStatus().hashCode();
        }
        if (getCredentialType() != null) {
            _hashCode += getCredentialType().hashCode();
        }
        if (getGenericCredentialType() != null) {
            _hashCode += getGenericCredentialType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Credential.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Credential"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("genericCredentialType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "genericCredentialType"));
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
