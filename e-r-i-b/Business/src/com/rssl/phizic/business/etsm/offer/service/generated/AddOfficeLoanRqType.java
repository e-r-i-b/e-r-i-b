/**
 * AddOfficeLoanRqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.etsm.offer.service.generated;

public class AddOfficeLoanRqType  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    private com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType officeLoanClaim;

    public AddOfficeLoanRqType() {
    }

    public AddOfficeLoanRqType(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType officeLoanClaim) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.officeLoanClaim = officeLoanClaim;
    }


    /**
     * Gets the rqUID value for this AddOfficeLoanRqType.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this AddOfficeLoanRqType.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this AddOfficeLoanRqType.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this AddOfficeLoanRqType.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the officeLoanClaim value for this AddOfficeLoanRqType.
     * 
     * @return officeLoanClaim
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType getOfficeLoanClaim() {
        return officeLoanClaim;
    }


    /**
     * Sets the officeLoanClaim value for this AddOfficeLoanRqType.
     * 
     * @param officeLoanClaim
     */
    public void setOfficeLoanClaim(com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType officeLoanClaim) {
        this.officeLoanClaim = officeLoanClaim;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddOfficeLoanRqType)) return false;
        AddOfficeLoanRqType other = (AddOfficeLoanRqType) obj;
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
            ((this.officeLoanClaim==null && other.getOfficeLoanClaim()==null) || 
             (this.officeLoanClaim!=null &&
              this.officeLoanClaim.equals(other.getOfficeLoanClaim())));
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
        if (getOfficeLoanClaim() != null) {
            _hashCode += getOfficeLoanClaim().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddOfficeLoanRqType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "AddOfficeLoanRqType"));
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
        elemField.setFieldName("officeLoanClaim");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfficeLoanClaim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfficeLoanClaimType"));
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
