/**
 * RequiredCredential.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This is a list of the authorization results for each credential
 */
public class RequiredCredential  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType credentialType;

    private java.lang.String genericCredentialType;

    private java.lang.String groupName;

    private java.lang.Integer preference;

    private java.lang.Boolean required;

    public RequiredCredential() {
    }

    public RequiredCredential(
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType credentialType,
           java.lang.String genericCredentialType,
           java.lang.String groupName,
           java.lang.Integer preference,
           java.lang.Boolean required) {
           this.credentialType = credentialType;
           this.genericCredentialType = genericCredentialType;
           this.groupName = groupName;
           this.preference = preference;
           this.required = required;
    }


    /**
     * Gets the credentialType value for this RequiredCredential.
     * 
     * @return credentialType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType getCredentialType() {
        return credentialType;
    }


    /**
     * Sets the credentialType value for this RequiredCredential.
     * 
     * @param credentialType
     */
    public void setCredentialType(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType credentialType) {
        this.credentialType = credentialType;
    }


    /**
     * Gets the genericCredentialType value for this RequiredCredential.
     * 
     * @return genericCredentialType
     */
    public java.lang.String getGenericCredentialType() {
        return genericCredentialType;
    }


    /**
     * Sets the genericCredentialType value for this RequiredCredential.
     * 
     * @param genericCredentialType
     */
    public void setGenericCredentialType(java.lang.String genericCredentialType) {
        this.genericCredentialType = genericCredentialType;
    }


    /**
     * Gets the groupName value for this RequiredCredential.
     * 
     * @return groupName
     */
    public java.lang.String getGroupName() {
        return groupName;
    }


    /**
     * Sets the groupName value for this RequiredCredential.
     * 
     * @param groupName
     */
    public void setGroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }


    /**
     * Gets the preference value for this RequiredCredential.
     * 
     * @return preference
     */
    public java.lang.Integer getPreference() {
        return preference;
    }


    /**
     * Sets the preference value for this RequiredCredential.
     * 
     * @param preference
     */
    public void setPreference(java.lang.Integer preference) {
        this.preference = preference;
    }


    /**
     * Gets the required value for this RequiredCredential.
     * 
     * @return required
     */
    public java.lang.Boolean getRequired() {
        return required;
    }


    /**
     * Sets the required value for this RequiredCredential.
     * 
     * @param required
     */
    public void setRequired(java.lang.Boolean required) {
        this.required = required;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequiredCredential)) return false;
        RequiredCredential other = (RequiredCredential) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.credentialType==null && other.getCredentialType()==null) || 
             (this.credentialType!=null &&
              this.credentialType.equals(other.getCredentialType()))) &&
            ((this.genericCredentialType==null && other.getGenericCredentialType()==null) || 
             (this.genericCredentialType!=null &&
              this.genericCredentialType.equals(other.getGenericCredentialType()))) &&
            ((this.groupName==null && other.getGroupName()==null) || 
             (this.groupName!=null &&
              this.groupName.equals(other.getGroupName()))) &&
            ((this.preference==null && other.getPreference()==null) || 
             (this.preference!=null &&
              this.preference.equals(other.getPreference()))) &&
            ((this.required==null && other.getRequired()==null) || 
             (this.required!=null &&
              this.required.equals(other.getRequired())));
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
        if (getCredentialType() != null) {
            _hashCode += getCredentialType().hashCode();
        }
        if (getGenericCredentialType() != null) {
            _hashCode += getGenericCredentialType().hashCode();
        }
        if (getGroupName() != null) {
            _hashCode += getGroupName().hashCode();
        }
        if (getPreference() != null) {
            _hashCode += getPreference().hashCode();
        }
        if (getRequired() != null) {
            _hashCode += getRequired().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequiredCredential.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RequiredCredential"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "groupName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "preference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("required");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "required"));
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
