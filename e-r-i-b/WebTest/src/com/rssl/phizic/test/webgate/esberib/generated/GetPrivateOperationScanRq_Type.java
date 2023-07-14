/**
 * GetPrivateOperationScanRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип данных запроса на отправку email c ЭД клиенту ФЛ
 */
public class GetPrivateOperationScanRq_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время передачи сообщения */
    private java.lang.String rqTm;

    /* Идентификатор системы, отправившей запрос */
    private java.lang.String SPName;

    /* Информация о банке */
    private com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeBankInfo bankInfo;

    /* Информация об операции в ВСП */
    private com.rssl.phizic.test.webgate.esberib.generated.VSPOperation_Type VSPOperationInfo;

    /* Контактная информация */
    private com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeContactInfo contactInfo;

    public GetPrivateOperationScanRq_Type() {
    }

    public GetPrivateOperationScanRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String SPName,
           com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeBankInfo bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.VSPOperation_Type VSPOperationInfo,
           com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeContactInfo contactInfo) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.SPName = SPName;
           this.bankInfo = bankInfo;
           this.VSPOperationInfo = VSPOperationInfo;
           this.contactInfo = contactInfo;
    }


    /**
     * Gets the rqUID value for this GetPrivateOperationScanRq_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this GetPrivateOperationScanRq_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this GetPrivateOperationScanRq_Type.
     * 
     * @return rqTm   * Дата и время передачи сообщения
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this GetPrivateOperationScanRq_Type.
     * 
     * @param rqTm   * Дата и время передачи сообщения
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the SPName value for this GetPrivateOperationScanRq_Type.
     * 
     * @return SPName   * Идентификатор системы, отправившей запрос
     */
    public java.lang.String getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this GetPrivateOperationScanRq_Type.
     * 
     * @param SPName   * Идентификатор системы, отправившей запрос
     */
    public void setSPName(java.lang.String SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the bankInfo value for this GetPrivateOperationScanRq_Type.
     * 
     * @return bankInfo   * Информация о банке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeBankInfo getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this GetPrivateOperationScanRq_Type.
     * 
     * @param bankInfo   * Информация о банке
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeBankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the VSPOperationInfo value for this GetPrivateOperationScanRq_Type.
     * 
     * @return VSPOperationInfo   * Информация об операции в ВСП
     */
    public com.rssl.phizic.test.webgate.esberib.generated.VSPOperation_Type getVSPOperationInfo() {
        return VSPOperationInfo;
    }


    /**
     * Sets the VSPOperationInfo value for this GetPrivateOperationScanRq_Type.
     * 
     * @param VSPOperationInfo   * Информация об операции в ВСП
     */
    public void setVSPOperationInfo(com.rssl.phizic.test.webgate.esberib.generated.VSPOperation_Type VSPOperationInfo) {
        this.VSPOperationInfo = VSPOperationInfo;
    }


    /**
     * Gets the contactInfo value for this GetPrivateOperationScanRq_Type.
     * 
     * @return contactInfo   * Контактная информация
     */
    public com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeContactInfo getContactInfo() {
        return contactInfo;
    }


    /**
     * Sets the contactInfo value for this GetPrivateOperationScanRq_Type.
     * 
     * @param contactInfo   * Контактная информация
     */
    public void setContactInfo(com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetPrivateOperationScanRq_Type)) return false;
        GetPrivateOperationScanRq_Type other = (GetPrivateOperationScanRq_Type) obj;
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
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.VSPOperationInfo==null && other.getVSPOperationInfo()==null) || 
             (this.VSPOperationInfo!=null &&
              this.VSPOperationInfo.equals(other.getVSPOperationInfo()))) &&
            ((this.contactInfo==null && other.getContactInfo()==null) || 
             (this.contactInfo!=null &&
              this.contactInfo.equals(other.getContactInfo())));
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
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getVSPOperationInfo() != null) {
            _hashCode += getVSPOperationInfo().hashCode();
        }
        if (getContactInfo() != null) {
            _hashCode += getContactInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetPrivateOperationScanRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateOperationScanRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UUID"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateOperationScanRq_Type>BankInfo"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VSPOperationInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VSPOperationInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VSPOperation_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateOperationScanRq_Type>ContactInfo"));
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
