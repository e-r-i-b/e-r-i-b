/**
 * BankInfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;


/**
 * Информация о подразделении, в рамках которого выполняется запрос
 */
public class BankInfoType  implements java.io.Serializable {
    /* Номер филиала (ВСП) */
    private java.lang.String branchId;

    /* Номер подразделения (ОСБ) */
    private java.lang.String agencyId;

    /* Номер тербанка (ТБ) */
    private java.lang.String regionId;

    public BankInfoType() {
    }

    public BankInfoType(
           java.lang.String branchId,
           java.lang.String agencyId,
           java.lang.String regionId) {
           this.branchId = branchId;
           this.agencyId = agencyId;
           this.regionId = regionId;
    }


    /**
     * Gets the branchId value for this BankInfoType.
     * 
     * @return branchId   * Номер филиала (ВСП)
     */
    public java.lang.String getBranchId() {
        return branchId;
    }


    /**
     * Sets the branchId value for this BankInfoType.
     * 
     * @param branchId   * Номер филиала (ВСП)
     */
    public void setBranchId(java.lang.String branchId) {
        this.branchId = branchId;
    }


    /**
     * Gets the agencyId value for this BankInfoType.
     * 
     * @return agencyId   * Номер подразделения (ОСБ)
     */
    public java.lang.String getAgencyId() {
        return agencyId;
    }


    /**
     * Sets the agencyId value for this BankInfoType.
     * 
     * @param agencyId   * Номер подразделения (ОСБ)
     */
    public void setAgencyId(java.lang.String agencyId) {
        this.agencyId = agencyId;
    }


    /**
     * Gets the regionId value for this BankInfoType.
     * 
     * @return regionId   * Номер тербанка (ТБ)
     */
    public java.lang.String getRegionId() {
        return regionId;
    }


    /**
     * Sets the regionId value for this BankInfoType.
     * 
     * @param regionId   * Номер тербанка (ТБ)
     */
    public void setRegionId(java.lang.String regionId) {
        this.regionId = regionId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankInfoType)) return false;
        BankInfoType other = (BankInfoType) obj;
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
        new org.apache.axis.description.TypeDesc(BankInfoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "BankInfoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "BranchId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "AgencyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RegionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
