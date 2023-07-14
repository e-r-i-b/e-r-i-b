/**
 * CollectableCredential.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the collectable credential types
 */
public class CollectableCredential  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionReason collectionReason;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionType collectionType;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialType credentialType;

    public CollectableCredential() {
    }

    public CollectableCredential(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionReason collectionReason,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionType collectionType,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialType credentialType) {
           this.collectionReason = collectionReason;
           this.collectionType = collectionType;
           this.credentialType = credentialType;
    }


    /**
     * Gets the collectionReason value for this CollectableCredential.
     * 
     * @return collectionReason
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionReason getCollectionReason() {
        return collectionReason;
    }


    /**
     * Sets the collectionReason value for this CollectableCredential.
     * 
     * @param collectionReason
     */
    public void setCollectionReason(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionReason collectionReason) {
        this.collectionReason = collectionReason;
    }


    /**
     * Gets the collectionType value for this CollectableCredential.
     * 
     * @return collectionType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionType getCollectionType() {
        return collectionType;
    }


    /**
     * Sets the collectionType value for this CollectableCredential.
     * 
     * @param collectionType
     */
    public void setCollectionType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionType collectionType) {
        this.collectionType = collectionType;
    }


    /**
     * Gets the credentialType value for this CollectableCredential.
     * 
     * @return credentialType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialType getCredentialType() {
        return credentialType;
    }


    /**
     * Sets the credentialType value for this CollectableCredential.
     * 
     * @param credentialType
     */
    public void setCredentialType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialType credentialType) {
        this.credentialType = credentialType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CollectableCredential)) return false;
        CollectableCredential other = (CollectableCredential) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.collectionReason==null && other.getCollectionReason()==null) || 
             (this.collectionReason!=null &&
              this.collectionReason.equals(other.getCollectionReason()))) &&
            ((this.collectionType==null && other.getCollectionType()==null) || 
             (this.collectionType!=null &&
              this.collectionType.equals(other.getCollectionType()))) &&
            ((this.credentialType==null && other.getCredentialType()==null) || 
             (this.credentialType!=null &&
              this.credentialType.equals(other.getCredentialType())));
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
        if (getCollectionReason() != null) {
            _hashCode += getCollectionReason().hashCode();
        }
        if (getCollectionType() != null) {
            _hashCode += getCollectionType().hashCode();
        }
        if (getCredentialType() != null) {
            _hashCode += getCredentialType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CollectableCredential.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectableCredential"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "collectionReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionReason"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "collectionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionType"));
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
