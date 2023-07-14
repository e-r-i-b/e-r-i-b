/**
 * SecuritiesInfoInqRs_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Тип Ответ на запрос реквизитов ценных бумаг
 */
public class SecuritiesInfoInqRs_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время передачи сообщения */
    private java.lang.String rqTm;

    /* Идентификатор системы, предоставляющей услуги (провайдер сервиса) */
    private com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName;

    /* Идентификатор системы, инициировавший запрос */
    private java.lang.String systemId;

    /* Информация о банке для целей маршрутизации */
    private com.rssl.phizicgate.esberibgate.ws.generated.BankInfoESB_Type bankInfo;

    /* Статус ответа */
    private com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status;

    /* Информационная запись по ценной бумаге */
    private com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRec_Type[] securitiesRec;

    public SecuritiesInfoInqRs_Type() {
    }

    public SecuritiesInfoInqRs_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName,
           java.lang.String systemId,
           com.rssl.phizicgate.esberibgate.ws.generated.BankInfoESB_Type bankInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status,
           com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRec_Type[] securitiesRec) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.SPName = SPName;
           this.systemId = systemId;
           this.bankInfo = bankInfo;
           this.status = status;
           this.securitiesRec = securitiesRec;
    }


    /**
     * Gets the rqUID value for this SecuritiesInfoInqRs_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this SecuritiesInfoInqRs_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this SecuritiesInfoInqRs_Type.
     * 
     * @return rqTm   * Дата и время передачи сообщения
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this SecuritiesInfoInqRs_Type.
     * 
     * @param rqTm   * Дата и время передачи сообщения
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the SPName value for this SecuritiesInfoInqRs_Type.
     * 
     * @return SPName   * Идентификатор системы, предоставляющей услуги (провайдер сервиса)
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this SecuritiesInfoInqRs_Type.
     * 
     * @param SPName   * Идентификатор системы, предоставляющей услуги (провайдер сервиса)
     */
    public void setSPName(com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the systemId value for this SecuritiesInfoInqRs_Type.
     * 
     * @return systemId   * Идентификатор системы, инициировавший запрос
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this SecuritiesInfoInqRs_Type.
     * 
     * @param systemId   * Идентификатор системы, инициировавший запрос
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the bankInfo value for this SecuritiesInfoInqRs_Type.
     * 
     * @return bankInfo   * Информация о банке для целей маршрутизации
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.BankInfoESB_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this SecuritiesInfoInqRs_Type.
     * 
     * @param bankInfo   * Информация о банке для целей маршрутизации
     */
    public void setBankInfo(com.rssl.phizicgate.esberibgate.ws.generated.BankInfoESB_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the status value for this SecuritiesInfoInqRs_Type.
     * 
     * @return status   * Статус ответа
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this SecuritiesInfoInqRs_Type.
     * 
     * @param status   * Статус ответа
     */
    public void setStatus(com.rssl.phizicgate.esberibgate.ws.generated.Status_Type status) {
        this.status = status;
    }


    /**
     * Gets the securitiesRec value for this SecuritiesInfoInqRs_Type.
     * 
     * @return securitiesRec   * Информационная запись по ценной бумаге
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRec_Type[] getSecuritiesRec() {
        return securitiesRec;
    }


    /**
     * Sets the securitiesRec value for this SecuritiesInfoInqRs_Type.
     * 
     * @param securitiesRec   * Информационная запись по ценной бумаге
     */
    public void setSecuritiesRec(com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRec_Type[] securitiesRec) {
        this.securitiesRec = securitiesRec;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRec_Type getSecuritiesRec(int i) {
        return this.securitiesRec[i];
    }

    public void setSecuritiesRec(int i, com.rssl.phizicgate.esberibgate.ws.generated.SecuritiesRec_Type _value) {
        this.securitiesRec[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecuritiesInfoInqRs_Type)) return false;
        SecuritiesInfoInqRs_Type other = (SecuritiesInfoInqRs_Type) obj;
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
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.securitiesRec==null && other.getSecuritiesRec()==null) || 
             (this.securitiesRec!=null &&
              java.util.Arrays.equals(this.securitiesRec, other.getSecuritiesRec())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSecuritiesRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSecuritiesRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSecuritiesRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecuritiesInfoInqRs_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfoInqRs_Type"));
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
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securitiesRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesRec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
