/**
 * QueryOfferRsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.etsm.offer.service.generated;

public class QueryOfferRsType  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    private com.rssl.phizic.business.etsm.offer.service.generated.StatusType status;

    private com.rssl.phizic.business.etsm.offer.service.generated.IdentityType identity;

    private com.rssl.phizic.business.etsm.offer.service.generated.OfferType offer;

    public QueryOfferRsType() {
    }

    public QueryOfferRsType(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           com.rssl.phizic.business.etsm.offer.service.generated.StatusType status,
           com.rssl.phizic.business.etsm.offer.service.generated.IdentityType identity,
           com.rssl.phizic.business.etsm.offer.service.generated.OfferType offer) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.status = status;
           this.identity = identity;
           this.offer = offer;
    }


    /**
     * Gets the rqUID value for this QueryOfferRsType.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this QueryOfferRsType.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this QueryOfferRsType.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this QueryOfferRsType.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the status value for this QueryOfferRsType.
     * 
     * @return status
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.StatusType getStatus() {
        return status;
    }


    /**
     * Sets the status value for this QueryOfferRsType.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizic.business.etsm.offer.service.generated.StatusType status) {
        this.status = status;
    }


    /**
     * Gets the identity value for this QueryOfferRsType.
     * 
     * @return identity
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.IdentityType getIdentity() {
        return identity;
    }


    /**
     * Sets the identity value for this QueryOfferRsType.
     * 
     * @param identity
     */
    public void setIdentity(com.rssl.phizic.business.etsm.offer.service.generated.IdentityType identity) {
        this.identity = identity;
    }


    /**
     * Gets the offer value for this QueryOfferRsType.
     * 
     * @return offer
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.OfferType getOffer() {
        return offer;
    }


    /**
     * Sets the offer value for this QueryOfferRsType.
     * 
     * @param offer
     */
    public void setOffer(com.rssl.phizic.business.etsm.offer.service.generated.OfferType offer) {
        this.offer = offer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryOfferRsType)) return false;
        QueryOfferRsType other = (QueryOfferRsType) obj;
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
            ((this.offer==null && other.getOffer()==null) || 
             (this.offer!=null &&
              this.offer.equals(other.getOffer())));
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
        if (getOffer() != null) {
            _hashCode += getOffer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryOfferRsType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "QueryOfferRsType"));
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
        elemField.setFieldName("offer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "Offer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfferType"));
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
