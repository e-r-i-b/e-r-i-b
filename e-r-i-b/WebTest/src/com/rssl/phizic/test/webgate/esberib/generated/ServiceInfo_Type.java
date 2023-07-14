/**
 * ServiceInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class ServiceInfo_Type  implements java.io.Serializable {
    /* Тип продукта */
    private java.lang.String prodType;

    private java.lang.String regionNum;

    private java.lang.String branchNum;

    private java.lang.String agencyNum;

    /* Номер заявления на подключение к услуге */
    private java.lang.String agreementNum;

    /* Дата подключения к услуге */
    private java.lang.String startDt;

    /* Дата отключения от услуги */
    private java.lang.String endDt;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private java.lang.String status;

    public ServiceInfo_Type() {
    }

    public ServiceInfo_Type(
           java.lang.String prodType,
           java.lang.String regionNum,
           java.lang.String branchNum,
           java.lang.String agencyNum,
           java.lang.String agreementNum,
           java.lang.String startDt,
           java.lang.String endDt,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           java.lang.String status) {
           this.prodType = prodType;
           this.regionNum = regionNum;
           this.branchNum = branchNum;
           this.agencyNum = agencyNum;
           this.agreementNum = agreementNum;
           this.startDt = startDt;
           this.endDt = endDt;
           this.bankInfo = bankInfo;
           this.status = status;
    }


    /**
     * Gets the prodType value for this ServiceInfo_Type.
     * 
     * @return prodType   * Тип продукта
     */
    public java.lang.String getProdType() {
        return prodType;
    }


    /**
     * Sets the prodType value for this ServiceInfo_Type.
     * 
     * @param prodType   * Тип продукта
     */
    public void setProdType(java.lang.String prodType) {
        this.prodType = prodType;
    }


    /**
     * Gets the regionNum value for this ServiceInfo_Type.
     * 
     * @return regionNum
     */
    public java.lang.String getRegionNum() {
        return regionNum;
    }


    /**
     * Sets the regionNum value for this ServiceInfo_Type.
     * 
     * @param regionNum
     */
    public void setRegionNum(java.lang.String regionNum) {
        this.regionNum = regionNum;
    }


    /**
     * Gets the branchNum value for this ServiceInfo_Type.
     * 
     * @return branchNum
     */
    public java.lang.String getBranchNum() {
        return branchNum;
    }


    /**
     * Sets the branchNum value for this ServiceInfo_Type.
     * 
     * @param branchNum
     */
    public void setBranchNum(java.lang.String branchNum) {
        this.branchNum = branchNum;
    }


    /**
     * Gets the agencyNum value for this ServiceInfo_Type.
     * 
     * @return agencyNum
     */
    public java.lang.String getAgencyNum() {
        return agencyNum;
    }


    /**
     * Sets the agencyNum value for this ServiceInfo_Type.
     * 
     * @param agencyNum
     */
    public void setAgencyNum(java.lang.String agencyNum) {
        this.agencyNum = agencyNum;
    }


    /**
     * Gets the agreementNum value for this ServiceInfo_Type.
     * 
     * @return agreementNum   * Номер заявления на подключение к услуге
     */
    public java.lang.String getAgreementNum() {
        return agreementNum;
    }


    /**
     * Sets the agreementNum value for this ServiceInfo_Type.
     * 
     * @param agreementNum   * Номер заявления на подключение к услуге
     */
    public void setAgreementNum(java.lang.String agreementNum) {
        this.agreementNum = agreementNum;
    }


    /**
     * Gets the startDt value for this ServiceInfo_Type.
     * 
     * @return startDt   * Дата подключения к услуге
     */
    public java.lang.String getStartDt() {
        return startDt;
    }


    /**
     * Sets the startDt value for this ServiceInfo_Type.
     * 
     * @param startDt   * Дата подключения к услуге
     */
    public void setStartDt(java.lang.String startDt) {
        this.startDt = startDt;
    }


    /**
     * Gets the endDt value for this ServiceInfo_Type.
     * 
     * @return endDt   * Дата отключения от услуги
     */
    public java.lang.String getEndDt() {
        return endDt;
    }


    /**
     * Sets the endDt value for this ServiceInfo_Type.
     * 
     * @param endDt   * Дата отключения от услуги
     */
    public void setEndDt(java.lang.String endDt) {
        this.endDt = endDt;
    }


    /**
     * Gets the bankInfo value for this ServiceInfo_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this ServiceInfo_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the status value for this ServiceInfo_Type.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ServiceInfo_Type.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceInfo_Type)) return false;
        ServiceInfo_Type other = (ServiceInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.prodType==null && other.getProdType()==null) || 
             (this.prodType!=null &&
              this.prodType.equals(other.getProdType()))) &&
            ((this.regionNum==null && other.getRegionNum()==null) || 
             (this.regionNum!=null &&
              this.regionNum.equals(other.getRegionNum()))) &&
            ((this.branchNum==null && other.getBranchNum()==null) || 
             (this.branchNum!=null &&
              this.branchNum.equals(other.getBranchNum()))) &&
            ((this.agencyNum==null && other.getAgencyNum()==null) || 
             (this.agencyNum!=null &&
              this.agencyNum.equals(other.getAgencyNum()))) &&
            ((this.agreementNum==null && other.getAgreementNum()==null) || 
             (this.agreementNum!=null &&
              this.agreementNum.equals(other.getAgreementNum()))) &&
            ((this.startDt==null && other.getStartDt()==null) || 
             (this.startDt!=null &&
              this.startDt.equals(other.getStartDt()))) &&
            ((this.endDt==null && other.getEndDt()==null) || 
             (this.endDt!=null &&
              this.endDt.equals(other.getEndDt()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getProdType() != null) {
            _hashCode += getProdType().hashCode();
        }
        if (getRegionNum() != null) {
            _hashCode += getRegionNum().hashCode();
        }
        if (getBranchNum() != null) {
            _hashCode += getBranchNum().hashCode();
        }
        if (getAgencyNum() != null) {
            _hashCode += getAgencyNum().hashCode();
        }
        if (getAgreementNum() != null) {
            _hashCode += getAgreementNum().hashCode();
        }
        if (getStartDt() != null) {
            _hashCode += getStartDt().hashCode();
        }
        if (getEndDt() != null) {
            _hashCode += getEndDt().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regionNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegionNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BranchNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgencyNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agreementNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreementNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
