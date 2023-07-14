/**
 * CalcCardToCardTransferCommissionRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип сообщения-запроса для интерфейса XXX___123 – получение суммы
 * комиссии, взимаемой банком за осуществление перевода денежных средств
 * с карты на карту
 */
public class CalcCardToCardTransferCommissionRq_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время формирования запроса */
    private java.lang.String rqTm;

    /* Идентификатор операции */
    private java.lang.String operUID;

    /* Идентификатор системы-инициатора запроса */
    private com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName;

    /* Информация о банке */
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    /* Регистрационный номер документа */
    private java.lang.String RRN;

    /* Информация по карте списания */
    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom;

    /* Информация по карте зачисления */
    private com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo;

    /* Источник запроса */
    private com.rssl.phizic.test.webgate.esberib.generated.Source_Type source;

    /* Сумма перевода */
    private java.math.BigDecimal curAmt;

    /* Валюта перевода (в поле передаётся RUR) */
    private java.lang.String acctCur;

    public CalcCardToCardTransferCommissionRq_Type() {
    }

    public CalcCardToCardTransferCommissionRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           java.lang.String RRN,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom,
           com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo,
           com.rssl.phizic.test.webgate.esberib.generated.Source_Type source,
           java.math.BigDecimal curAmt,
           java.lang.String acctCur) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.bankInfo = bankInfo;
           this.RRN = RRN;
           this.cardAcctIdFrom = cardAcctIdFrom;
           this.cardAcctIdTo = cardAcctIdTo;
           this.source = source;
           this.curAmt = curAmt;
           this.acctCur = acctCur;
    }


    /**
     * Gets the rqUID value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return rqTm   * Дата и время формирования запроса
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param rqTm   * Дата и время формирования запроса
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return operUID   * Идентификатор операции
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param operUID   * Идентификатор операции
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return SPName   * Идентификатор системы-инициатора запроса
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param SPName   * Идентификатор системы-инициатора запроса
     */
    public void setSPName(com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the bankInfo value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return bankInfo   * Информация о банке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param bankInfo   * Информация о банке
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the RRN value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return RRN   * Регистрационный номер документа
     */
    public java.lang.String getRRN() {
        return RRN;
    }


    /**
     * Sets the RRN value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param RRN   * Регистрационный номер документа
     */
    public void setRRN(java.lang.String RRN) {
        this.RRN = RRN;
    }


    /**
     * Gets the cardAcctIdFrom value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return cardAcctIdFrom   * Информация по карте списания
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctIdFrom() {
        return cardAcctIdFrom;
    }


    /**
     * Sets the cardAcctIdFrom value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param cardAcctIdFrom   * Информация по карте списания
     */
    public void setCardAcctIdFrom(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdFrom) {
        this.cardAcctIdFrom = cardAcctIdFrom;
    }


    /**
     * Gets the cardAcctIdTo value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return cardAcctIdTo   * Информация по карте зачисления
     */
    public com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type getCardAcctIdTo() {
        return cardAcctIdTo;
    }


    /**
     * Sets the cardAcctIdTo value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param cardAcctIdTo   * Информация по карте зачисления
     */
    public void setCardAcctIdTo(com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type cardAcctIdTo) {
        this.cardAcctIdTo = cardAcctIdTo;
    }


    /**
     * Gets the source value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return source   * Источник запроса
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Source_Type getSource() {
        return source;
    }


    /**
     * Sets the source value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param source   * Источник запроса
     */
    public void setSource(com.rssl.phizic.test.webgate.esberib.generated.Source_Type source) {
        this.source = source;
    }


    /**
     * Gets the curAmt value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return curAmt   * Сумма перевода
     */
    public java.math.BigDecimal getCurAmt() {
        return curAmt;
    }


    /**
     * Sets the curAmt value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param curAmt   * Сумма перевода
     */
    public void setCurAmt(java.math.BigDecimal curAmt) {
        this.curAmt = curAmt;
    }


    /**
     * Gets the acctCur value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @return acctCur   * Валюта перевода (в поле передаётся RUR)
     */
    public java.lang.String getAcctCur() {
        return acctCur;
    }


    /**
     * Sets the acctCur value for this CalcCardToCardTransferCommissionRq_Type.
     * 
     * @param acctCur   * Валюта перевода (в поле передаётся RUR)
     */
    public void setAcctCur(java.lang.String acctCur) {
        this.acctCur = acctCur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CalcCardToCardTransferCommissionRq_Type)) return false;
        CalcCardToCardTransferCommissionRq_Type other = (CalcCardToCardTransferCommissionRq_Type) obj;
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
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.RRN==null && other.getRRN()==null) || 
             (this.RRN!=null &&
              this.RRN.equals(other.getRRN()))) &&
            ((this.cardAcctIdFrom==null && other.getCardAcctIdFrom()==null) || 
             (this.cardAcctIdFrom!=null &&
              this.cardAcctIdFrom.equals(other.getCardAcctIdFrom()))) &&
            ((this.cardAcctIdTo==null && other.getCardAcctIdTo()==null) || 
             (this.cardAcctIdTo!=null &&
              this.cardAcctIdTo.equals(other.getCardAcctIdTo()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.curAmt==null && other.getCurAmt()==null) || 
             (this.curAmt!=null &&
              this.curAmt.equals(other.getCurAmt()))) &&
            ((this.acctCur==null && other.getAcctCur()==null) || 
             (this.acctCur!=null &&
              this.acctCur.equals(other.getAcctCur())));
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
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getRRN() != null) {
            _hashCode += getRRN().hashCode();
        }
        if (getCardAcctIdFrom() != null) {
            _hashCode += getCardAcctIdFrom().hashCode();
        }
        if (getCardAcctIdTo() != null) {
            _hashCode += getCardAcctIdTo().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getCurAmt() != null) {
            _hashCode += getCurAmt().hashCode();
        }
        if (getAcctCur() != null) {
            _hashCode += getAcctCur().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CalcCardToCardTransferCommissionRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CalcCardToCardTransferCommissionRq_Type"));
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RRN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RRN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctIdFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctIdFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctIdTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctIdTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Source_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("curAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
