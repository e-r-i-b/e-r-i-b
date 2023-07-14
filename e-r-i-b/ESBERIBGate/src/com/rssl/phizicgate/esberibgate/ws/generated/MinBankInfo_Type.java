/**
 * MinBankInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация о банке <BankInfo>. Этот агрегат используется для передачи
 * только ОСБ и ВСП, ТБ (опционально)
 */
public class MinBankInfo_Type  implements java.io.Serializable {
    /* Номер филиала */
    private java.lang.String branchId;

    /* Номер отделения */
    private java.lang.String agencyId;

    /* Номер террбанка */
    private java.lang.String regionId;

    public MinBankInfo_Type() {
    }

    public MinBankInfo_Type(
           java.lang.String branchId,
           java.lang.String agencyId,
           java.lang.String regionId) {
           this.branchId = branchId;
           this.agencyId = agencyId;
           this.regionId = regionId;
    }


    /**
     * Gets the branchId value for this MinBankInfo_Type.
     * 
     * @return branchId   * Номер филиала
     */
    public java.lang.String getBranchId() {
        return branchId;
    }


    /**
     * Sets the branchId value for this MinBankInfo_Type.
     * 
     * @param branchId   * Номер филиала
     */
    public void setBranchId(java.lang.String branchId) {
        this.branchId = branchId;
    }


    /**
     * Gets the agencyId value for this MinBankInfo_Type.
     * 
     * @return agencyId   * Номер отделения
     */
    public java.lang.String getAgencyId() {
        return agencyId;
    }


    /**
     * Sets the agencyId value for this MinBankInfo_Type.
     * 
     * @param agencyId   * Номер отделения
     */
    public void setAgencyId(java.lang.String agencyId) {
        this.agencyId = agencyId;
    }


    /**
     * Gets the regionId value for this MinBankInfo_Type.
     * 
     * @return regionId   * Номер террбанка
     */
    public java.lang.String getRegionId() {
        return regionId;
    }


    /**
     * Sets the regionId value for this MinBankInfo_Type.
     * 
     * @param regionId   * Номер террбанка
     */
    public void setRegionId(java.lang.String regionId) {
        this.regionId = regionId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MinBankInfo_Type)) return false;
        MinBankInfo_Type other = (MinBankInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.branchId==null && other.getBranchId()==null) || 
             (this.branchId!=null &&
              this.branchId.equals(other.getBranchId()))) &&
            ((this.agencyId==null && other.getAgencyId()==null) || 
             (this.agencyId!=null &&
              this.agencyId.equals(other.getAgencyId()))) &&
            ((this.regionId==null && other.getRegionId()==null) || 
             (this.regionId!=null &&
              this.regionId.equals(other.getRegionId())));
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
        if (getBranchId() != null) {
            _hashCode += getBranchId().hashCode();
        }
        if (getAgencyId() != null) {
            _hashCode += getAgencyId().hashCode();
        }
        if (getRegionId() != null) {
            _hashCode += getRegionId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MinBankInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MinBankInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BranchId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BranchId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgencyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgencyId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegionId_Type"));
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
