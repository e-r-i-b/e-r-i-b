/**
 * CollectionRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This describes why a collection is being initiated and the reasons
 * behind it
 */
public class CollectionRequest  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionInitiator collectionInitiator;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionReason collectionReason;

    private java.lang.Boolean forceCollection;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Credential[] orgCredentialList;

    public CollectionRequest() {
    }

    public CollectionRequest(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionInitiator collectionInitiator,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionReason collectionReason,
           java.lang.Boolean forceCollection,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Credential[] orgCredentialList) {
           this.collectionInitiator = collectionInitiator;
           this.collectionReason = collectionReason;
           this.forceCollection = forceCollection;
           this.orgCredentialList = orgCredentialList;
    }


    /**
     * Gets the collectionInitiator value for this CollectionRequest.
     * 
     * @return collectionInitiator
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionInitiator getCollectionInitiator() {
        return collectionInitiator;
    }


    /**
     * Sets the collectionInitiator value for this CollectionRequest.
     * 
     * @param collectionInitiator
     */
    public void setCollectionInitiator(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionInitiator collectionInitiator) {
        this.collectionInitiator = collectionInitiator;
    }


    /**
     * Gets the collectionReason value for this CollectionRequest.
     * 
     * @return collectionReason
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionReason getCollectionReason() {
        return collectionReason;
    }


    /**
     * Sets the collectionReason value for this CollectionRequest.
     * 
     * @param collectionReason
     */
    public void setCollectionReason(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionReason collectionReason) {
        this.collectionReason = collectionReason;
    }


    /**
     * Gets the forceCollection value for this CollectionRequest.
     * 
     * @return forceCollection
     */
    public java.lang.Boolean getForceCollection() {
        return forceCollection;
    }


    /**
     * Sets the forceCollection value for this CollectionRequest.
     * 
     * @param forceCollection
     */
    public void setForceCollection(java.lang.Boolean forceCollection) {
        this.forceCollection = forceCollection;
    }


    /**
     * Gets the orgCredentialList value for this CollectionRequest.
     * 
     * @return orgCredentialList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Credential[] getOrgCredentialList() {
        return orgCredentialList;
    }


    /**
     * Sets the orgCredentialList value for this CollectionRequest.
     * 
     * @param orgCredentialList
     */
    public void setOrgCredentialList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Credential[] orgCredentialList) {
        this.orgCredentialList = orgCredentialList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CollectionRequest)) return false;
        CollectionRequest other = (CollectionRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.collectionInitiator==null && other.getCollectionInitiator()==null) || 
             (this.collectionInitiator!=null &&
              this.collectionInitiator.equals(other.getCollectionInitiator()))) &&
            ((this.collectionReason==null && other.getCollectionReason()==null) || 
             (this.collectionReason!=null &&
              this.collectionReason.equals(other.getCollectionReason()))) &&
            ((this.forceCollection==null && other.getForceCollection()==null) || 
             (this.forceCollection!=null &&
              this.forceCollection.equals(other.getForceCollection()))) &&
            ((this.orgCredentialList==null && other.getOrgCredentialList()==null) || 
             (this.orgCredentialList!=null &&
              java.util.Arrays.equals(this.orgCredentialList, other.getOrgCredentialList())));
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
        if (getCollectionInitiator() != null) {
            _hashCode += getCollectionInitiator().hashCode();
        }
        if (getCollectionReason() != null) {
            _hashCode += getCollectionReason().hashCode();
        }
        if (getForceCollection() != null) {
            _hashCode += getForceCollection().hashCode();
        }
        if (getOrgCredentialList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOrgCredentialList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOrgCredentialList(), i);
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
        new org.apache.axis.description.TypeDesc(CollectionRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionInitiator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "collectionInitiator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionInitiator"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "collectionReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionReason"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("forceCollection");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "forceCollection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgCredentialList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "orgCredentialList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Credential"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credential"));
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
