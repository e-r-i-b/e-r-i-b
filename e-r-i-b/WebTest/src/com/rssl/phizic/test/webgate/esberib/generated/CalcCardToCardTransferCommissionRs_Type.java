/**
 * CalcCardToCardTransferCommissionRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип сообщения-ответа для интерфейса XXX___123 – получение суммы
 * комиссии, взимаемой банком за осуществление перевода денежных средств
 * с карты на карту
 */
public class CalcCardToCardTransferCommissionRs_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время формирования запроса */
    private java.lang.String rqTm;

    /* Идентификатор операции */
    private java.lang.String operUID;

    /* Идентификатор системы */
    private com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName;

    /* Результат выполнения запроса */
    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    /* Сумма комиссии */
    private java.math.BigDecimal commission;

    /* Валюта комиссии */
    private java.lang.String commissionCur;

    /* Информация об авторизации операции */
    private com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type cardAuthorization;

    public CalcCardToCardTransferCommissionRs_Type() {
    }

    public CalcCardToCardTransferCommissionRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status,
           java.math.BigDecimal commission,
           java.lang.String commissionCur,
           com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type cardAuthorization) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.status = status;
           this.commission = commission;
           this.commissionCur = commissionCur;
           this.cardAuthorization = cardAuthorization;
    }


    /**
     * Gets the rqUID value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @return rqTm   * Дата и время формирования запроса
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @param rqTm   * Дата и время формирования запроса
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @return operUID   * Идентификатор операции
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @param operUID   * Идентификатор операции
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @return SPName   * Идентификатор системы
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @param SPName   * Идентификатор системы
     */
    public void setSPName(com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the status value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @return status   * Результат выполнения запроса
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @param status   * Результат выполнения запроса
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the commission value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @return commission   * Сумма комиссии
     */
    public java.math.BigDecimal getCommission() {
        return commission;
    }


    /**
     * Sets the commission value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @param commission   * Сумма комиссии
     */
    public void setCommission(java.math.BigDecimal commission) {
        this.commission = commission;
    }


    /**
     * Gets the commissionCur value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @return commissionCur   * Валюта комиссии
     */
    public java.lang.String getCommissionCur() {
        return commissionCur;
    }


    /**
     * Sets the commissionCur value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @param commissionCur   * Валюта комиссии
     */
    public void setCommissionCur(java.lang.String commissionCur) {
        this.commissionCur = commissionCur;
    }


    /**
     * Gets the cardAuthorization value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @return cardAuthorization   * Информация об авторизации операции
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type getCardAuthorization() {
        return cardAuthorization;
    }


    /**
     * Sets the cardAuthorization value for this CalcCardToCardTransferCommissionRs_Type.
     * 
     * @param cardAuthorization   * Информация об авторизации операции
     */
    public void setCardAuthorization(com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type cardAuthorization) {
        this.cardAuthorization = cardAuthorization;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CalcCardToCardTransferCommissionRs_Type)) return false;
        CalcCardToCardTransferCommissionRs_Type other = (CalcCardToCardTransferCommissionRs_Type) obj;
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
            ((this.operUID==null && other.getOperUID()==null) || 
             (this.operUID!=null &&
              this.operUID.equals(other.getOperUID()))) &&
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.commission==null && other.getCommission()==null) || 
             (this.commission!=null &&
              this.commission.equals(other.getCommission()))) &&
            ((this.commissionCur==null && other.getCommissionCur()==null) || 
             (this.commissionCur!=null &&
              this.commissionCur.equals(other.getCommissionCur()))) &&
            ((this.cardAuthorization==null && other.getCardAuthorization()==null) || 
             (this.cardAuthorization!=null &&
              this.cardAuthorization.equals(other.getCardAuthorization())));
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
        if (getOperUID() != null) {
            _hashCode += getOperUID().hashCode();
        }
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getCommission() != null) {
            _hashCode += getCommission().hashCode();
        }
        if (getCommissionCur() != null) {
            _hashCode += getCommissionCur().hashCode();
        }
        if (getCardAuthorization() != null) {
            _hashCode += getCardAuthorization().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CalcCardToCardTransferCommissionRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CalcCardToCardTransferCommissionRs_Type"));
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
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UUID"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commission");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Commission"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commissionCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CommissionCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAuthorization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization_Type"));
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
