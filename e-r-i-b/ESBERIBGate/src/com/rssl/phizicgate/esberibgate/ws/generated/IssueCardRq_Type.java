/**
 * IssueCardRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Запрос  на  первичеый  выпуск  карты
 */
public class IssueCardRq_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время передачи сообщения */
    private java.lang.String rqTm;

    /* Уникальный идентификатор операции */
    private java.lang.String operUID;

    /* Идентификатор системы, инициировавший запрос */
    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    /* Идентификатор системы, предоставляющей услуги */
    private java.lang.String systemId;

    /* Идентификатор пользователя, инициировавший заявку на выпуск
     * карты */
    private java.lang.String userName;

    /* Наименование операции (PersonalCardESB) */
    private java.lang.String operName;

    /* Информация  о  банке */
    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo;

    /* Идентификатор карточного счета */
    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId;

    /* Информация по карте */
    private com.rssl.phizicgate.esberibgate.ws.generated.CardAcctInfoType cardAcctInfo;

    public IssueCardRq_Type() {
    }

    public IssueCardRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           java.lang.String systemId,
           java.lang.String userName,
           java.lang.String operName,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId,
           com.rssl.phizicgate.esberibgate.ws.generated.CardAcctInfoType cardAcctInfo) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.systemId = systemId;
           this.userName = userName;
           this.operName = operName;
           this.bankInfo = bankInfo;
           this.cardAcctId = cardAcctId;
           this.cardAcctInfo = cardAcctInfo;
    }


    /**
     * Gets the rqUID value for this IssueCardRq_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this IssueCardRq_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this IssueCardRq_Type.
     * 
     * @return rqTm   * Дата и время передачи сообщения
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this IssueCardRq_Type.
     * 
     * @param rqTm   * Дата и время передачи сообщения
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this IssueCardRq_Type.
     * 
     * @return operUID   * Уникальный идентификатор операции
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this IssueCardRq_Type.
     * 
     * @param operUID   * Уникальный идентификатор операции
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this IssueCardRq_Type.
     * 
     * @return SPName   * Идентификатор системы, инициировавший запрос
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this IssueCardRq_Type.
     * 
     * @param SPName   * Идентификатор системы, инициировавший запрос
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the systemId value for this IssueCardRq_Type.
     * 
     * @return systemId   * Идентификатор системы, предоставляющей услуги
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this IssueCardRq_Type.
     * 
     * @param systemId   * Идентификатор системы, предоставляющей услуги
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the userName value for this IssueCardRq_Type.
     * 
     * @return userName   * Идентификатор пользователя, инициировавший заявку на выпуск
     * карты
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this IssueCardRq_Type.
     * 
     * @param userName   * Идентификатор пользователя, инициировавший заявку на выпуск
     * карты
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the operName value for this IssueCardRq_Type.
     * 
     * @return operName   * Наименование операции (PersonalCardESB)
     */
    public java.lang.String getOperName() {
        return operName;
    }


    /**
     * Sets the operName value for this IssueCardRq_Type.
     * 
     * @param operName   * Наименование операции (PersonalCardESB)
     */
    public void setOperName(java.lang.String operName) {
        this.operName = operName;
    }


    /**
     * Gets the bankInfo value for this IssueCardRq_Type.
     * 
     * @return bankInfo   * Информация  о  банке
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this IssueCardRq_Type.
     * 
     * @param bankInfo   * Информация  о  банке
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the cardAcctId value for this IssueCardRq_Type.
     * 
     * @return cardAcctId   * Идентификатор карточного счета
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type getCardAcctId() {
        return cardAcctId;
    }


    /**
     * Sets the cardAcctId value for this IssueCardRq_Type.
     * 
     * @param cardAcctId   * Идентификатор карточного счета
     */
    public void setCardAcctId(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type cardAcctId) {
        this.cardAcctId = cardAcctId;
    }


    /**
     * Gets the cardAcctInfo value for this IssueCardRq_Type.
     * 
     * @return cardAcctInfo   * Информация по карте
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.CardAcctInfoType getCardAcctInfo() {
        return cardAcctInfo;
    }


    /**
     * Sets the cardAcctInfo value for this IssueCardRq_Type.
     * 
     * @param cardAcctInfo   * Информация по карте
     */
    public void setCardAcctInfo(com.rssl.phizicgate.esberibgate.ws.generated.CardAcctInfoType cardAcctInfo) {
        this.cardAcctInfo = cardAcctInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IssueCardRq_Type)) return false;
        IssueCardRq_Type other = (IssueCardRq_Type) obj;
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
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.operName==null && other.getOperName()==null) || 
             (this.operName!=null &&
              this.operName.equals(other.getOperName()))) &&
            ((this.bankInfo==null && other.getBankInfo()==null) || 
             (this.bankInfo!=null &&
              this.bankInfo.equals(other.getBankInfo()))) &&
            ((this.cardAcctId==null && other.getCardAcctId()==null) || 
             (this.cardAcctId!=null &&
              this.cardAcctId.equals(other.getCardAcctId()))) &&
            ((this.cardAcctInfo==null && other.getCardAcctInfo()==null) || 
             (this.cardAcctInfo!=null &&
              this.cardAcctInfo.equals(other.getCardAcctInfo())));
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
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getOperName() != null) {
            _hashCode += getOperName().hashCode();
        }
        if (getBankInfo() != null) {
            _hashCode += getBankInfo().hashCode();
        }
        if (getCardAcctId() != null) {
            _hashCode += getCardAcctId().hashCode();
        }
        if (getCardAcctInfo() != null) {
            _hashCode += getCardAcctInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IssueCardRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IssueCardRq_Type"));
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
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardAcctInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctInfoType"));
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
