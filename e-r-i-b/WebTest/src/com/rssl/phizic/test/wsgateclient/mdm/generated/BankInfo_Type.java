/**
 * BankInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mdm.generated;


/**
 * Информация о банке <BankInfo>
 */
public class BankInfo_Type  implements java.io.Serializable {
    /* Номер филиала. */
    private java.lang.String branchId;

    /* Номер отделения. */
    private java.lang.String agencyId;

    /* Номер террбанка. */
    private java.lang.String regionId;

    /* 8-ный код территориального банка, в котором открыт счет МБК,
     * по которой клиент произвел идентификацию на УС */
    private java.lang.String rbTbBrchId;

    /* 6-ный код территориального банка, в котором ведется продукт
     * клиента (кредит, вклад, ОМС, карты) */
    private java.lang.String rbBrchId;

    public BankInfo_Type() {
    }

    public BankInfo_Type(
           java.lang.String branchId,
           java.lang.String agencyId,
           java.lang.String regionId,
           java.lang.String rbTbBrchId,
           java.lang.String rbBrchId) {
           this.branchId = branchId;
           this.agencyId = agencyId;
           this.regionId = regionId;
           this.rbTbBrchId = rbTbBrchId;
           this.rbBrchId = rbBrchId;
    }


    /**
     * Gets the branchId value for this BankInfo_Type.
     * 
     * @return branchId   * Номер филиала.
     */
    public java.lang.String getBranchId() {
        return branchId;
    }


    /**
     * Sets the branchId value for this BankInfo_Type.
     * 
     * @param branchId   * Номер филиала.
     */
    public void setBranchId(java.lang.String branchId) {
        this.branchId = branchId;
    }


    /**
     * Gets the agencyId value for this BankInfo_Type.
     * 
     * @return agencyId   * Номер отделения.
     */
    public java.lang.String getAgencyId() {
        return agencyId;
    }


    /**
     * Sets the agencyId value for this BankInfo_Type.
     * 
     * @param agencyId   * Номер отделения.
     */
    public void setAgencyId(java.lang.String agencyId) {
        this.agencyId = agencyId;
    }


    /**
     * Gets the regionId value for this BankInfo_Type.
     * 
     * @return regionId   * Номер террбанка.
     */
    public java.lang.String getRegionId() {
        return regionId;
    }


    /**
     * Sets the regionId value for this BankInfo_Type.
     * 
     * @param regionId   * Номер террбанка.
     */
    public void setRegionId(java.lang.String regionId) {
        this.regionId = regionId;
    }


    /**
     * Gets the rbTbBrchId value for this BankInfo_Type.
     * 
     * @return rbTbBrchId   * 8-ный код территориального банка, в котором открыт счет МБК,
     * по которой клиент произвел идентификацию на УС
     */
    public java.lang.String getRbTbBrchId() {
        return rbTbBrchId;
    }


    /**
     * Sets the rbTbBrchId value for this BankInfo_Type.
     * 
     * @param rbTbBrchId   * 8-ный код территориального банка, в котором открыт счет МБК,
     * по которой клиент произвел идентификацию на УС
     */
    public void setRbTbBrchId(java.lang.String rbTbBrchId) {
        this.rbTbBrchId = rbTbBrchId;
    }


    /**
     * Gets the rbBrchId value for this BankInfo_Type.
     * 
     * @return rbBrchId   * 6-ный код территориального банка, в котором ведется продукт
     * клиента (кредит, вклад, ОМС, карты)
     */
    public java.lang.String getRbBrchId() {
        return rbBrchId;
    }


    /**
     * Sets the rbBrchId value for this BankInfo_Type.
     * 
     * @param rbBrchId   * 6-ный код территориального банка, в котором ведется продукт
     * клиента (кредит, вклад, ОМС, карты)
     */
    public void setRbBrchId(java.lang.String rbBrchId) {
        this.rbBrchId = rbBrchId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankInfo_Type)) return false;
        BankInfo_Type other = (BankInfo_Type) obj;
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
              this.regionId.equals(other.getRegionId()))) &&
            ((this.rbTbBrchId==null && other.getRbTbBrchId()==null) || 
             (this.rbTbBrchId!=null &&
              this.rbTbBrchId.equals(other.getRbTbBrchId()))) &&
            ((this.rbBrchId==null && other.getRbBrchId()==null) || 
             (this.rbBrchId!=null &&
              this.rbBrchId.equals(other.getRbBrchId())));
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
        if (getRbTbBrchId() != null) {
            _hashCode += getRbTbBrchId().hashCode();
        }
        if (getRbBrchId() != null) {
            _hashCode += getRbBrchId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "BankInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "BranchId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "BranchId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "AgencyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "AgencyId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "RegionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "RegionId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rbTbBrchId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "RbTbBrchId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "RbTbBrchType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rbBrchId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "RbBrchId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.srb.ru/erib/mdm", "RbBrchType"));
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
