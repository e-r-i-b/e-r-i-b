/**
 * CardContract_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о карте
 */
public class CardContract_Type  implements java.io.Serializable {
    /* Тип карты */
    private long cardType;

    /* Категория клиента */
    private long clientCategory;

    /* Код валюты */
    private java.lang.String currency;

    /* Информация о банке */
    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    /* Эмбоссированный текст в формате "NAME/SURNAME/" */
    private java.lang.String embossedText;

    /* Бонус-программа */
    private java.lang.String bonusProgram;

    /* Номер участника */
    private java.lang.String participantNumber;

    /* Тип тарифа за обслуживания */
    private com.rssl.phizic.test.webgate.esberib.generated.ServiceTarif_Type serviceTarif;

    /* Если указан тип тарифа "Индивидуальный", то тариф за 1й год
     * обслуживания */
    private java.lang.Long tarifFirst;

    /* Если указан тип тарифа "Индивидуальный", то тариф за последующие
     * года обслуживания */
    private java.lang.Long tarifNext;

    /* Признак инсайдера */
    private boolean isInsider;

    /* Признак наличия БИО-приложения на карте */
    private boolean hasBIOData;

    /* Признак выдачи карты с ПИН-конвертом */
    private boolean isPINEnvelope;

    /* Признак владельца счета */
    private boolean isOwner;

    public CardContract_Type() {
    }

    public CardContract_Type(
           long cardType,
           long clientCategory,
           java.lang.String currency,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           java.lang.String embossedText,
           java.lang.String bonusProgram,
           java.lang.String participantNumber,
           com.rssl.phizic.test.webgate.esberib.generated.ServiceTarif_Type serviceTarif,
           java.lang.Long tarifFirst,
           java.lang.Long tarifNext,
           boolean isInsider,
           boolean hasBIOData,
           boolean isPINEnvelope,
           boolean isOwner) {
           this.cardType = cardType;
           this.clientCategory = clientCategory;
           this.currency = currency;
           this.bankInfo = bankInfo;
           this.embossedText = embossedText;
           this.bonusProgram = bonusProgram;
           this.participantNumber = participantNumber;
           this.serviceTarif = serviceTarif;
           this.tarifFirst = tarifFirst;
           this.tarifNext = tarifNext;
           this.isInsider = isInsider;
           this.hasBIOData = hasBIOData;
           this.isPINEnvelope = isPINEnvelope;
           this.isOwner = isOwner;
    }


    /**
     * Gets the cardType value for this CardContract_Type.
     * 
     * @return cardType   * Тип карты
     */
    public long getCardType() {
        return cardType;
    }


    /**
     * Sets the cardType value for this CardContract_Type.
     * 
     * @param cardType   * Тип карты
     */
    public void setCardType(long cardType) {
        this.cardType = cardType;
    }


    /**
     * Gets the clientCategory value for this CardContract_Type.
     * 
     * @return clientCategory   * Категория клиента
     */
    public long getClientCategory() {
        return clientCategory;
    }


    /**
     * Sets the clientCategory value for this CardContract_Type.
     * 
     * @param clientCategory   * Категория клиента
     */
    public void setClientCategory(long clientCategory) {
        this.clientCategory = clientCategory;
    }


    /**
     * Gets the currency value for this CardContract_Type.
     * 
     * @return currency   * Код валюты
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this CardContract_Type.
     * 
     * @param currency   * Код валюты
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the bankInfo value for this CardContract_Type.
     * 
     * @return bankInfo   * Информация о банке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this CardContract_Type.
     * 
     * @param bankInfo   * Информация о банке
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the embossedText value for this CardContract_Type.
     * 
     * @return embossedText   * Эмбоссированный текст в формате "NAME/SURNAME/"
     */
    public java.lang.String getEmbossedText() {
        return embossedText;
    }


    /**
     * Sets the embossedText value for this CardContract_Type.
     * 
     * @param embossedText   * Эмбоссированный текст в формате "NAME/SURNAME/"
     */
    public void setEmbossedText(java.lang.String embossedText) {
        this.embossedText = embossedText;
    }


    /**
     * Gets the bonusProgram value for this CardContract_Type.
     * 
     * @return bonusProgram   * Бонус-программа
     */
    public java.lang.String getBonusProgram() {
        return bonusProgram;
    }


    /**
     * Sets the bonusProgram value for this CardContract_Type.
     * 
     * @param bonusProgram   * Бонус-программа
     */
    public void setBonusProgram(java.lang.String bonusProgram) {
        this.bonusProgram = bonusProgram;
    }


    /**
     * Gets the participantNumber value for this CardContract_Type.
     * 
     * @return participantNumber   * Номер участника
     */
    public java.lang.String getParticipantNumber() {
        return participantNumber;
    }


    /**
     * Sets the participantNumber value for this CardContract_Type.
     * 
     * @param participantNumber   * Номер участника
     */
    public void setParticipantNumber(java.lang.String participantNumber) {
        this.participantNumber = participantNumber;
    }


    /**
     * Gets the serviceTarif value for this CardContract_Type.
     * 
     * @return serviceTarif   * Тип тарифа за обслуживания
     */
    public com.rssl.phizic.test.webgate.esberib.generated.ServiceTarif_Type getServiceTarif() {
        return serviceTarif;
    }


    /**
     * Sets the serviceTarif value for this CardContract_Type.
     * 
     * @param serviceTarif   * Тип тарифа за обслуживания
     */
    public void setServiceTarif(com.rssl.phizic.test.webgate.esberib.generated.ServiceTarif_Type serviceTarif) {
        this.serviceTarif = serviceTarif;
    }


    /**
     * Gets the tarifFirst value for this CardContract_Type.
     * 
     * @return tarifFirst   * Если указан тип тарифа "Индивидуальный", то тариф за 1й год
     * обслуживания
     */
    public java.lang.Long getTarifFirst() {
        return tarifFirst;
    }


    /**
     * Sets the tarifFirst value for this CardContract_Type.
     * 
     * @param tarifFirst   * Если указан тип тарифа "Индивидуальный", то тариф за 1й год
     * обслуживания
     */
    public void setTarifFirst(java.lang.Long tarifFirst) {
        this.tarifFirst = tarifFirst;
    }


    /**
     * Gets the tarifNext value for this CardContract_Type.
     * 
     * @return tarifNext   * Если указан тип тарифа "Индивидуальный", то тариф за последующие
     * года обслуживания
     */
    public java.lang.Long getTarifNext() {
        return tarifNext;
    }


    /**
     * Sets the tarifNext value for this CardContract_Type.
     * 
     * @param tarifNext   * Если указан тип тарифа "Индивидуальный", то тариф за последующие
     * года обслуживания
     */
    public void setTarifNext(java.lang.Long tarifNext) {
        this.tarifNext = tarifNext;
    }


    /**
     * Gets the isInsider value for this CardContract_Type.
     * 
     * @return isInsider   * Признак инсайдера
     */
    public boolean isIsInsider() {
        return isInsider;
    }


    /**
     * Sets the isInsider value for this CardContract_Type.
     * 
     * @param isInsider   * Признак инсайдера
     */
    public void setIsInsider(boolean isInsider) {
        this.isInsider = isInsider;
    }


    /**
     * Gets the hasBIOData value for this CardContract_Type.
     * 
     * @return hasBIOData   * Признак наличия БИО-приложения на карте
     */
    public boolean isHasBIOData() {
        return hasBIOData;
    }


    /**
     * Sets the hasBIOData value for this CardContract_Type.
     * 
     * @param hasBIOData   * Признак наличия БИО-приложения на карте
     */
    public void setHasBIOData(boolean hasBIOData) {
        this.hasBIOData = hasBIOData;
    }


    /**
     * Gets the isPINEnvelope value for this CardContract_Type.
     * 
     * @return isPINEnvelope   * Признак выдачи карты с ПИН-конвертом
     */
    public boolean isIsPINEnvelope() {
        return isPINEnvelope;
    }


    /**
     * Sets the isPINEnvelope value for this CardContract_Type.
     * 
     * @param isPINEnvelope   * Признак выдачи карты с ПИН-конвертом
     */
    public void setIsPINEnvelope(boolean isPINEnvelope) {
        this.isPINEnvelope = isPINEnvelope;
    }


    /**
     * Gets the isOwner value for this CardContract_Type.
     * 
     * @return isOwner   * Признак владельца счета
     */
    public boolean isIsOwner() {
        return isOwner;
    }


    /**
     * Sets the isOwner value for this CardContract_Type.
     * 
     * @param isOwner   * Признак владельца счета
     */
    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardContract_Type)) return false;
        CardContract_Type other = (CardContract_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.cardType == other.getCardType() &&
            this.clientCategory == other.getClientCategory() &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.embossedText==null && other.getEmbossedText()==null) || 
             (this.embossedText!=null &&
              this.embossedText.equals(other.getEmbossedText()))) &&
            ((this.bonusProgram==null && other.getBonusProgram()==null) || 
             (this.bonusProgram!=null &&
              this.bonusProgram.equals(other.getBonusProgram()))) &&
            ((this.participantNumber==null && other.getParticipantNumber()==null) || 
             (this.participantNumber!=null &&
              this.participantNumber.equals(other.getParticipantNumber()))) &&
            ((this.serviceTarif==null && other.getServiceTarif()==null) || 
             (this.serviceTarif!=null &&
              this.serviceTarif.equals(other.getServiceTarif()))) &&
            ((this.tarifFirst==null && other.getTarifFirst()==null) || 
             (this.tarifFirst!=null &&
              this.tarifFirst.equals(other.getTarifFirst()))) &&
            ((this.tarifNext==null && other.getTarifNext()==null) || 
             (this.tarifNext!=null &&
              this.tarifNext.equals(other.getTarifNext()))) &&
            this.isInsider == other.isIsInsider() &&
            this.hasBIOData == other.isHasBIOData() &&
            this.isPINEnvelope == other.isIsPINEnvelope() &&
            this.isOwner == other.isIsOwner();
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
        _hashCode += new Long(getCardType()).hashCode();
        _hashCode += new Long(getClientCategory()).hashCode();
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getEmbossedText() != null) {
            _hashCode += getEmbossedText().hashCode();
        }
        if (getBonusProgram() != null) {
            _hashCode += getBonusProgram().hashCode();
        }
        if (getParticipantNumber() != null) {
            _hashCode += getParticipantNumber().hashCode();
        }
        if (getServiceTarif() != null) {
            _hashCode += getServiceTarif().hashCode();
        }
        if (getTarifFirst() != null) {
            _hashCode += getTarifFirst().hashCode();
        }
        if (getTarifNext() != null) {
            _hashCode += getTarifNext().hashCode();
        }
        _hashCode += (isIsInsider() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isHasBIOData() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIsPINEnvelope() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIsOwner() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardContract_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardContract_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("embossedText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmbossedText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusProgram");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusProgram"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("participantNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ParticipantNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceTarif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceTarif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceTarif_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tarifFirst");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifFirst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tarifNext");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifNext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isInsider");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsInsider"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasBIOData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "HasBIOData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPINEnvelope");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsPINEnvelope"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isOwner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsOwner"));
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
