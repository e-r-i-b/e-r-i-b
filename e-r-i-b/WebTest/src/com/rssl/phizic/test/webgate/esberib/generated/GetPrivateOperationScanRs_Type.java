/**
 * GetPrivateOperationScanRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип данных результата выполнения запроса на отправку email c ЭД
 * клиенту ФЛ перевод
 */
public class GetPrivateOperationScanRs_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время передачи сообщения */
    private java.lang.String rqTm;

    /* Идентификатор системы, отправившей запрос */
    private java.lang.String SPName;

    /* Информация о банке */
    private com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeBankInfo bankInfo;

    /* Статусное сообщение (результат выполнения операции) */
    private com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeStatus status;

    public GetPrivateOperationScanRs_Type() {
    }

    public GetPrivateOperationScanRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String SPName,
           com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeBankInfo bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeStatus status) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.SPName = SPName;
           this.bankInfo = bankInfo;
           this.status = status;
    }


    /**
     * Gets the rqUID value for this GetPrivateOperationScanRs_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this GetPrivateOperationScanRs_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this GetPrivateOperationScanRs_Type.
     * 
     * @return rqTm   * Дата и время передачи сообщения
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this GetPrivateOperationScanRs_Type.
     * 
     * @param rqTm   * Дата и время передачи сообщения
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the SPName value for this GetPrivateOperationScanRs_Type.
     * 
     * @return SPName   * Идентификатор системы, отправившей запрос
     */
    public java.lang.String getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this GetPrivateOperationScanRs_Type.
     * 
     * @param SPName   * Идентификатор системы, отправившей запрос
     */
    public void setSPName(java.lang.String SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the bankInfo value for this GetPrivateOperationScanRs_Type.
     * 
     * @return bankInfo   * Информация о банке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeBankInfo getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this GetPrivateOperationScanRs_Type.
     * 
     * @param bankInfo   * Информация о банке
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeBankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the status value for this GetPrivateOperationScanRs_Type.
     * 
     * @return status   * Статусное сообщение (результат выполнения операции)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this GetPrivateOperationScanRs_Type.
     * 
     * @param status   * Статусное сообщение (результат выполнения операции)
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeStatus status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetPrivateOperationScanRs_Type)) return false;
        GetPrivateOperationScanRs_Type other = (GetPrivateOperationScanRs_Type) obj;
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetPrivateOperationScanRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateOperationScanRs_Type"));
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateOperationScanRs_Type>BankInfo"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateOperationScanRs_Type>Status"));
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
