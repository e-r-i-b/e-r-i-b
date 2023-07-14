/**
 * GetOfficeLoansByFIODulBDRs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.etsm.offer.service.generated;

public class GetOfficeLoansByFIODulBDRs  implements java.io.Serializable {
    /* Идентификатор запроса REGEXP: [0-9a-fA-F]{32} */
    private java.lang.String rqUID;

    private java.util.Calendar rqTm;

    private com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType[] officeLoanClaims;

    public GetOfficeLoansByFIODulBDRs() {
    }

    public GetOfficeLoansByFIODulBDRs(
           java.lang.String rqUID,
           java.util.Calendar rqTm,
           com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType[] officeLoanClaims) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.officeLoanClaims = officeLoanClaims;
    }


    /**
     * Gets the rqUID value for this GetOfficeLoansByFIODulBDRs.
     * 
     * @return rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this GetOfficeLoansByFIODulBDRs.
     * 
     * @param rqUID   * Идентификатор запроса REGEXP: [0-9a-fA-F]{32}
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this GetOfficeLoansByFIODulBDRs.
     * 
     * @return rqTm
     */
    public java.util.Calendar getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this GetOfficeLoansByFIODulBDRs.
     * 
     * @param rqTm
     */
    public void setRqTm(java.util.Calendar rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the officeLoanClaims value for this GetOfficeLoansByFIODulBDRs.
     * 
     * @return officeLoanClaims
     */
    public com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType[] getOfficeLoanClaims() {
        return officeLoanClaims;
    }


    /**
     * Sets the officeLoanClaims value for this GetOfficeLoansByFIODulBDRs.
     * 
     * @param officeLoanClaims
     */
    public void setOfficeLoanClaims(com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType[] officeLoanClaims) {
        this.officeLoanClaims = officeLoanClaims;
    }

    public com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType getOfficeLoanClaims(int i) {
        return this.officeLoanClaims[i];
    }

    public void setOfficeLoanClaims(int i, com.rssl.phizic.business.etsm.offer.service.generated.OfficeLoanClaimType _value) {
        this.officeLoanClaims[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOfficeLoansByFIODulBDRs)) return false;
        GetOfficeLoansByFIODulBDRs other = (GetOfficeLoansByFIODulBDRs) obj;
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
            ((this.officeLoanClaims==null && other.getOfficeLoanClaims()==null) || 
             (this.officeLoanClaims!=null &&
              java.util.Arrays.equals(this.officeLoanClaims, other.getOfficeLoanClaims())));
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
        if (getOfficeLoanClaims() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOfficeLoanClaims());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOfficeLoanClaims(), i);
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
        new org.apache.axis.description.TypeDesc(GetOfficeLoansByFIODulBDRs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "GetOfficeLoansByFIODulBDRs"));
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
        elemField.setFieldName("officeLoanClaims");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfficeLoanClaims"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.etsm.phizic.rssl.com", "OfficeLoanClaimType"));
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
