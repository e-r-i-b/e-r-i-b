/**
 * AddBillBasketInfoRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип сообщения-запроса для интерфейса "Передача информации о задолженности
 * клиента"
 */
public class AddBillBasketInfoRq_Type  implements java.io.Serializable {
    /* Уникальный идентификатор запроса */
    private java.lang.String rqUID;

    /* Дата и время передачи сообщения.Данные должны передаваться
     * в формате yyyy-mm-ddThh:zz:ss */
    private java.lang.String rqTm;

    /* Идентификатор операции */
    private java.lang.String operUID;

    /* Идентификатор системы сформировавшей запрос */
    private com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName;

    /* Идентификатор системы назначения */
    private java.lang.String systemId;

    /* Идентификационная информация об услуге «Автопоиск счетов к
     * оплате» */
    private com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionID;

    /* Список задолженностей по АП */
    private com.rssl.phizic.test.webgate.esberib.generated.PaymentList_Type[] payment;

    public AddBillBasketInfoRq_Type() {
    }

    public AddBillBasketInfoRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName,
           java.lang.String systemId,
           com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionID,
           com.rssl.phizic.test.webgate.esberib.generated.PaymentList_Type[] payment) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.systemId = systemId;
           this.autoSubscriptionID = autoSubscriptionID;
           this.payment = payment;
    }


    /**
     * Gets the rqUID value for this AddBillBasketInfoRq_Type.
     * 
     * @return rqUID   * Уникальный идентификатор запроса
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this AddBillBasketInfoRq_Type.
     * 
     * @param rqUID   * Уникальный идентификатор запроса
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this AddBillBasketInfoRq_Type.
     * 
     * @return rqTm   * Дата и время передачи сообщения.Данные должны передаваться
     * в формате yyyy-mm-ddThh:zz:ss
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this AddBillBasketInfoRq_Type.
     * 
     * @param rqTm   * Дата и время передачи сообщения.Данные должны передаваться
     * в формате yyyy-mm-ddThh:zz:ss
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this AddBillBasketInfoRq_Type.
     * 
     * @return operUID   * Идентификатор операции
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this AddBillBasketInfoRq_Type.
     * 
     * @param operUID   * Идентификатор операции
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this AddBillBasketInfoRq_Type.
     * 
     * @return SPName   * Идентификатор системы сформировавшей запрос
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this AddBillBasketInfoRq_Type.
     * 
     * @param SPName   * Идентификатор системы сформировавшей запрос
     */
    public void setSPName(com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the systemId value for this AddBillBasketInfoRq_Type.
     * 
     * @return systemId   * Идентификатор системы назначения
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this AddBillBasketInfoRq_Type.
     * 
     * @param systemId   * Идентификатор системы назначения
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the autoSubscriptionID value for this AddBillBasketInfoRq_Type.
     * 
     * @return autoSubscriptionID   * Идентификационная информация об услуге «Автопоиск счетов к
     * оплате»
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type getAutoSubscriptionID() {
        return autoSubscriptionID;
    }


    /**
     * Sets the autoSubscriptionID value for this AddBillBasketInfoRq_Type.
     * 
     * @param autoSubscriptionID   * Идентификационная информация об услуге «Автопоиск счетов к
     * оплате»
     */
    public void setAutoSubscriptionID(com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionID) {
        this.autoSubscriptionID = autoSubscriptionID;
    }


    /**
     * Gets the payment value for this AddBillBasketInfoRq_Type.
     * 
     * @return payment   * Список задолженностей по АП
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PaymentList_Type[] getPayment() {
        return payment;
    }


    /**
     * Sets the payment value for this AddBillBasketInfoRq_Type.
     * 
     * @param payment   * Список задолженностей по АП
     */
    public void setPayment(com.rssl.phizic.test.webgate.esberib.generated.PaymentList_Type[] payment) {
        this.payment = payment;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.PaymentList_Type getPayment(int i) {
        return this.payment[i];
    }

    public void setPayment(int i, com.rssl.phizic.test.webgate.esberib.generated.PaymentList_Type _value) {
        this.payment[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddBillBasketInfoRq_Type)) return false;
        AddBillBasketInfoRq_Type other = (AddBillBasketInfoRq_Type) obj;
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
            ((this.autoSubscriptionID==null && other.getAutoSubscriptionID()==null) || 
             (this.autoSubscriptionID!=null &&
              this.autoSubscriptionID.equals(other.getAutoSubscriptionID()))) &&
            ((this.payment==null && other.getPayment()==null) || 
             (this.payment!=null &&
              java.util.Arrays.equals(this.payment, other.getPayment())));
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
        if (getAutoSubscriptionID() != null) {
            _hashCode += getAutoSubscriptionID().hashCode();
        }
        if (getPayment() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPayment());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPayment(), i);
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
        new org.apache.axis.description.TypeDesc(AddBillBasketInfoRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddBillBasketInfoRq_Type"));
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoSubscriptionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Payment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentList_Type"));
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
