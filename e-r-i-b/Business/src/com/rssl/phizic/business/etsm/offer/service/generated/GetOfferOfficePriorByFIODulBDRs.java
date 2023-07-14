/**
 * GetOfferOfficePriorByFIODulBDRs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.etsm.offer.service.generated;

public class GetOfferOfficePriorByFIODulBDRs  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    private com.rssl.phizic.business.etsm.offer.service.generated.StatusType status;

    private com.rssl.phizic.business.etsm.offer.service.generated.IdentityType identity;

    private com.rssl.phizic.business.etsm.offer.service.generated.OfferType[] offerOfficePriors;

    public GetOfferOfficePriorByFIODulBDRs() {
    }

    public GetOfferOfficePriorByFIODulBDRs(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           com.rssl.phizic.business.etsm.offer.service.generated.StatusType status,
           com.rssl.phizic.business.etsm.offer.service.generated.IdentityType identity,
           com.rssl.phizic.business.etsm.offer.service.generated.OfferType[] offerOfficePriors) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.status = status;
           this.identity = identity;
           this.offerOfficePriors = offerOfficePriors;
    }


    /**
     * Gets the rqUID value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the status value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @return status
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.StatusType getStatus() {
        return status;
    }


    /**
     * Sets the status value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.business.etsm.offer.service.generated.StatusType status) {
        this.status = status;
    }


    /**
     * Gets the identity value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @return identity
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.IdentityType getIdentity() {
        return identity;
    }


    /**
     * Sets the identity value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @param identity
     */
    public void setIdentity(com.rssl.phizic.business.etsm.offer.service.generated.IdentityType identity) {
        this.identity = identity;
    }


    /**
     * Gets the offerOfficePriors value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @return offerOfficePriors
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.OfferType[] getOfferOfficePriors() {
        return offerOfficePriors;
    }


    /**
     * Sets the offerOfficePriors value for this GetOfferOfficePriorByFIODulBDRs.
     * 
     * @param offerOfficePriors
     */
    public void setOfferOfficePriors(com.rssl.phizic.business.etsm.offer.service.generated.OfferType[] offerOfficePriors) {
        this.offerOfficePriors = offerOfficePriors;
    }

    public com.rssl.phizic.business.etsm.offer.service.generated.OfferType getOfferOfficePriors(int i) {
        return this.offerOfficePriors[i];
    }

    public void setOfferOfficePriors(int i, com.rssl.phizic.business.etsm.offer.service.generated.OfferType _value) {
        this.offerOfficePriors[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOfferOfficePriorByFIODulBDRs)) return false;
        GetOfferOfficePriorByFIODulBDRs other = (GetOfferOfficePriorByFIODulBDRs) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTm==null && other.getRqTm()==null) || 
             (this.rqTm!=null &&
              this.rqTm.equals(other.getRqTm()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.identity==null && other.getIdentity()==null) || 
             (this.identity!=null &&
              this.identity.equals(other.getIdentity()))) &&
            ((this.offerOfficePriors==null && other.getOfferOfficePriors()==null) || 
             (this.offerOfficePriors!=null &&
              java.util.Arrays.equals(this.offerOfficePriors, other.getOfferOfficePriors())));
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTm() != null) {
            _hashCode += getRqTm().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getIdentity() != null) {
            _hashCode += getIdentity().hashCode();
        }
        if (getOfferOfficePriors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOfferOfficePriors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOfferOfficePriors(), i);
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
        new org.apache.axis.description.TypeDesc(GetOfferOfficePriorByFIODulBDRs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "GetOfferOfficePriorByFIODulBDRs"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "StatusType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "Identity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "IdentityType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offerOfficePriors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfferOfficePriors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfferType"));
        elemField.setMinOccurs(0);
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
