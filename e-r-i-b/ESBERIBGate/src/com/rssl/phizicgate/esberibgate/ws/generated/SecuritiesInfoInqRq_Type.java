/**
 * SecuritiesInfoInqRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Запроса реквизитов ценных бумаг
 */
public class SecuritiesInfoInqRq_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время передачи сообщения */
    private java.lang.String rqTm;

    /* Идентификатор системы инициирующей запрос */
    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    /* Идентификатор системы назначения запроса */
    private java.lang.String systemId;

    /* Информация о банке для целей маршрутизации */
    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfoESB_Type bankInfo;

    /* Наименование операции
     * "SrvBSGetInfo" */
    private java.lang.String operName;

    /* Бланк ценной бумаги */
    private com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo;

    /* Пакет бланков ценных бумаг */
    private com.rssl.phizicgate.esberibgate.ws.generated.BlankPackage_Type blankPackage;

    public SecuritiesInfoInqRq_Type() {
    }

    public SecuritiesInfoInqRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           java.lang.String systemId,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfoESB_Type bankInfo,
           java.lang.String operName,
           com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.BlankPackage_Type blankPackage) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.SPName = SPName;
           this.systemId = systemId;
           this.bankInfo = bankInfo;
           this.operName = operName;
           this.blankInfo = blankInfo;
           this.blankPackage = blankPackage;
    }


    /**
     * Gets the rqUID value for this SecuritiesInfoInqRq_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this SecuritiesInfoInqRq_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this SecuritiesInfoInqRq_Type.
     * 
     * @return rqTm   * Дата и время передачи сообщения
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this SecuritiesInfoInqRq_Type.
     * 
     * @param rqTm   * Дата и время передачи сообщения
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the SPName value for this SecuritiesInfoInqRq_Type.
     * 
     * @return SPName   * Идентификатор системы инициирующей запрос
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this SecuritiesInfoInqRq_Type.
     * 
     * @param SPName   * Идентификатор системы инициирующей запрос
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the systemId value for this SecuritiesInfoInqRq_Type.
     * 
     * @return systemId   * Идентификатор системы назначения запроса
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this SecuritiesInfoInqRq_Type.
     * 
     * @param systemId   * Идентификатор системы назначения запроса
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the bankInfo value for this SecuritiesInfoInqRq_Type.
     * 
     * @return bankInfo   * Информация о банке для целей маршрутизации
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfoESB_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this SecuritiesInfoInqRq_Type.
     * 
     * @param bankInfo   * Информация о банке для целей маршрутизации
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfoESB_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the operName value for this SecuritiesInfoInqRq_Type.
     * 
     * @return operName   * Наименование операции
     * "SrvBSGetInfo"
     */
    public java.lang.String getOperName() {
        return operName;
    }


    /**
     * Sets the operName value for this SecuritiesInfoInqRq_Type.
     * 
     * @param operName   * Наименование операции
     * "SrvBSGetInfo"
     */
    public void setOperName(java.lang.String operName) {
        this.operName = operName;
    }


    /**
     * Gets the blankInfo value for this SecuritiesInfoInqRq_Type.
     * 
     * @return blankInfo   * Бланк ценной бумаги
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type getBlankInfo() {
        return blankInfo;
    }


    /**
     * Sets the blankInfo value for this SecuritiesInfoInqRq_Type.
     * 
     * @param blankInfo   * Бланк ценной бумаги
     */
    public void setBlankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BlankInfo_Type blankInfo) {
        this.blankInfo = blankInfo;
    }


    /**
     * Gets the blankPackage value for this SecuritiesInfoInqRq_Type.
     * 
     * @return blankPackage   * Пакет бланков ценных бумаг
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BlankPackage_Type getBlankPackage() {
        return blankPackage;
    }


    /**
     * Sets the blankPackage value for this SecuritiesInfoInqRq_Type.
     * 
     * @param blankPackage   * Пакет бланков ценных бумаг
     */
    public void setBlankPackage(com.rssl.phizicgate.esberibgate.ws.generated.BlankPackage_Type blankPackage) {
        this.blankPackage = blankPackage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecuritiesInfoInqRq_Type)) return false;
        SecuritiesInfoInqRq_Type other = (SecuritiesInfoInqRq_Type) obj;
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
            ((this.operName==null && other.getOperName()==null) || 
             (this.operName!=null &&
              this.operName.equals(other.getOperName()))) &&
            ((this.blankInfo==null && other.getBlankInfo()==null) || 
             (this.blankInfo!=null &&
              this.blankInfo.equals(other.getBlankInfo()))) &&
            ((this.blankPackage==null && other.getBlankPackage()==null) || 
             (this.blankPackage!=null &&
              this.blankPackage.equals(other.getBlankPackage())));
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
        if (getOperName() != null) {
            _hashCode += getOperName().hashCode();
        }
        if (getBlankInfo() != null) {
            _hashCode += getBlankInfo().hashCode();
        }
        if (getBlankPackage() != null) {
            _hashCode += getBlankPackage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecuritiesInfoInqRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfoInqRq_Type"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoESB_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blankInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blankPackage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankPackage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankPackage_Type"));
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
