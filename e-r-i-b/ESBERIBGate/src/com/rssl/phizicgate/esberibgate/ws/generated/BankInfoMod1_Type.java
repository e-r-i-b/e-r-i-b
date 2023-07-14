/**
 * BankInfoMod1_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Реквизиты банков. Тип используется для идентификации банка
 */
public class BankInfoMod1_Type  implements java.io.Serializable {
    /* Код тербанка = 
     * '99' */
    private java.lang.String regionId;

    /* Код отделения */
    private java.lang.String agencyId;

    /* Код филиала банка */
    private java.lang.String branchId;

    public BankInfoMod1_Type() {
    }

    public BankInfoMod1_Type(
           java.lang.String regionId,
           java.lang.String agencyId,
           java.lang.String branchId) {
           this.regionId = regionId;
           this.agencyId = agencyId;
           this.branchId = branchId;
    }


    /**
     * Gets the regionId value for this BankInfoMod1_Type.
     * 
     * @return regionId   * Код тербанка = 
     * '99'
     */
    public java.lang.String getRegionId() {
        return regionId;
    }


    /**
     * Sets the regionId value for this BankInfoMod1_Type.
     * 
     * @param regionId   * Код тербанка = 
     * '99'
     */
    public void setRegionId(java.lang.String regionId) {
        this.regionId = regionId;
    }


    /**
     * Gets the agencyId value for this BankInfoMod1_Type.
     * 
     * @return agencyId   * Код отделения
     */
    public java.lang.String getAgencyId() {
        return agencyId;
    }


    /**
     * Sets the agencyId value for this BankInfoMod1_Type.
     * 
     * @param agencyId   * Код отделения
     */
    public void setAgencyId(java.lang.String agencyId) {
        this.agencyId = agencyId;
    }


    /**
     * Gets the branchId value for this BankInfoMod1_Type.
     * 
     * @return branchId   * Код филиала банка
     */
    public java.lang.String getBranchId() {
        return branchId;
    }


    /**
     * Sets the branchId value for this BankInfoMod1_Type.
     * 
     * @param branchId   * Код филиала банка
     */
    public void setBranchId(java.lang.String branchId) {
        this.branchId = branchId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankInfoMod1_Type)) return false;
        BankInfoMod1_Type other = (BankInfoMod1_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.regionId==null && other.getRegionId()==null) || 
             (this.regionId!=null &&
              this.regionId.equals(other.getRegionId()))) &&
            ((this.agencyId==null && other.getAgencyId()==null) || 
             (this.agencyId!=null &&
              this.agencyId.equals(other.getAgencyId()))) &&
            ((this.branchId==null && other.getBranchId()==null) || 
             (this.branchId!=null &&
              this.branchId.equals(other.getBranchId())));
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
        if (getRegionId() != null) {
            _hashCode += getRegionId().hashCode();
        }
        if (getAgencyId() != null) {
            _hashCode += getAgencyId().hashCode();
        }
        if (getBranchId() != null) {
            _hashCode += getBranchId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankInfoMod1_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoMod1_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgencyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BranchId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BranchId_Type"));
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
