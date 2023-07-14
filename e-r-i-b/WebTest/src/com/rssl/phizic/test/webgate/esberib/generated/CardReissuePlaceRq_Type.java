/**
 * CardReissuePlaceRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип запроса для перевыпуска карт (в т.ч. не по месту ведения счета)
 */
public class CardReissuePlaceRq_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время передачи сообщения */
    private java.lang.String rqTm;

    /* Идентификатор системы, инициирующей запрос - 
     * 'BP_ERIB', 'BP_SOFIA', 'BP_XBANK', 'BP_ASFS' */
    private com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName;

    /* Идентификатор системы назначения запроса */
    private java.lang.String systemId;

    /* Информация о банке, куда посылаем запрос */
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfoMod1_Type bankInfo;

    /* Номер пластиковой карты. */
    private java.lang.String cardNum;

    /* Тип пластиковой карты */
    private java.lang.String cardType;

    /* Код отделения доставки */
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type destination;

    /* Идентификатор канала обслуживания */
    private com.rssl.phizic.test.webgate.esberib.generated.SourceId_Type source;

    /* Код причины перевыпуска карты */
    private java.lang.String reasonCode;

    /* Признак взимания комиссии за перевыпуск карты */
    private boolean commission;

    public CardReissuePlaceRq_Type() {
    }

    public CardReissuePlaceRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName,
           java.lang.String systemId,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfoMod1_Type bankInfo,
           java.lang.String cardNum,
           java.lang.String cardType,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type destination,
           com.rssl.phizic.test.webgate.esberib.generated.SourceId_Type source,
           java.lang.String reasonCode,
           boolean commission) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.SPName = SPName;
           this.systemId = systemId;
           this.bankInfo = bankInfo;
           this.cardNum = cardNum;
           this.cardType = cardType;
           this.destination = destination;
           this.source = source;
           this.reasonCode = reasonCode;
           this.commission = commission;
    }


    /**
     * Gets the rqUID value for this CardReissuePlaceRq_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this CardReissuePlaceRq_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this CardReissuePlaceRq_Type.
     * 
     * @return rqTm   * Дата и время передачи сообщения
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this CardReissuePlaceRq_Type.
     * 
     * @param rqTm   * Дата и время передачи сообщения
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the SPName value for this CardReissuePlaceRq_Type.
     * 
     * @return SPName   * Идентификатор системы, инициирующей запрос - 
     * 'BP_ERIB', 'BP_SOFIA', 'BP_XBANK', 'BP_ASFS'
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this CardReissuePlaceRq_Type.
     * 
     * @param SPName   * Идентификатор системы, инициирующей запрос - 
     * 'BP_ERIB', 'BP_SOFIA', 'BP_XBANK', 'BP_ASFS'
     */
    public void setSPName(com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the systemId value for this CardReissuePlaceRq_Type.
     * 
     * @return systemId   * Идентификатор системы назначения запроса
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this CardReissuePlaceRq_Type.
     * 
     * @param systemId   * Идентификатор системы назначения запроса
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the bankInfo value for this CardReissuePlaceRq_Type.
     * 
     * @return bankInfo   * Информация о банке, куда посылаем запрос
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfoMod1_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this CardReissuePlaceRq_Type.
     * 
     * @param bankInfo   * Информация о банке, куда посылаем запрос
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfoMod1_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the cardNum value for this CardReissuePlaceRq_Type.
     * 
     * @return cardNum   * Номер пластиковой карты.
     */
    public java.lang.String getCardNum() {
        return cardNum;
    }


    /**
     * Sets the cardNum value for this CardReissuePlaceRq_Type.
     * 
     * @param cardNum   * Номер пластиковой карты.
     */
    public void setCardNum(java.lang.String cardNum) {
        this.cardNum = cardNum;
    }


    /**
     * Gets the cardType value for this CardReissuePlaceRq_Type.
     * 
     * @return cardType   * Тип пластиковой карты
     */
    public java.lang.String getCardType() {
        return cardType;
    }


    /**
     * Sets the cardType value for this CardReissuePlaceRq_Type.
     * 
     * @param cardType   * Тип пластиковой карты
     */
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }


    /**
     * Gets the destination value for this CardReissuePlaceRq_Type.
     * 
     * @return destination   * Код отделения доставки
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type getDestination() {
        return destination;
    }


    /**
     * Sets the destination value for this CardReissuePlaceRq_Type.
     * 
     * @param destination   * Код отделения доставки
     */
    public void setDestination(com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type destination) {
        this.destination = destination;
    }


    /**
     * Gets the source value for this CardReissuePlaceRq_Type.
     * 
     * @return source   * Идентификатор канала обслуживания
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SourceId_Type getSource() {
        return source;
    }


    /**
     * Sets the source value for this CardReissuePlaceRq_Type.
     * 
     * @param source   * Идентификатор канала обслуживания
     */
    public void setSource(com.rssl.phizic.test.webgate.esberib.generated.SourceId_Type source) {
        this.source = source;
    }


    /**
     * Gets the reasonCode value for this CardReissuePlaceRq_Type.
     * 
     * @return reasonCode   * Код причины перевыпуска карты
     */
    public java.lang.String getReasonCode() {
        return reasonCode;
    }


    /**
     * Sets the reasonCode value for this CardReissuePlaceRq_Type.
     * 
     * @param reasonCode   * Код причины перевыпуска карты
     */
    public void setReasonCode(java.lang.String reasonCode) {
        this.reasonCode = reasonCode;
    }


    /**
     * Gets the commission value for this CardReissuePlaceRq_Type.
     * 
     * @return commission   * Признак взимания комиссии за перевыпуск карты
     */
    public boolean isCommission() {
        return commission;
    }


    /**
     * Sets the commission value for this CardReissuePlaceRq_Type.
     * 
     * @param commission   * Признак взимания комиссии за перевыпуск карты
     */
    public void setCommission(boolean commission) {
        this.commission = commission;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardReissuePlaceRq_Type)) return false;
        CardReissuePlaceRq_Type other = (CardReissuePlaceRq_Type) obj;
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
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.cardNum==null && other.getCardNum()==null) || 
             (this.cardNum!=null &&
              this.cardNum.equals(other.getCardNum()))) &&
            ((this.cardType==null && other.getCardType()==null) || 
             (this.cardType!=null &&
              this.cardType.equals(other.getCardType()))) &&
            ((this.destination==null && other.getDestination()==null) || 
             (this.destination!=null &&
              this.destination.equals(other.getDestination()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.reasonCode==null && other.getReasonCode()==null) || 
             (this.reasonCode!=null &&
              this.reasonCode.equals(other.getReasonCode()))) &&
            this.commission == other.isCommission();
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
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getCardNum() != null) {
            _hashCode += getCardNum().hashCode();
        }
        if (getCardType() != null) {
            _hashCode += getCardType().hashCode();
        }
        if (getDestination() != null) {
            _hashCode += getDestination().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getReasonCode() != null) {
            _hashCode += getReasonCode().hashCode();
        }
        _hashCode += (isCommission() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardReissuePlaceRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardReissuePlaceRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID_Type"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoMod1_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Destination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoLeadZero_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReasonCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commission");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Commission"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
