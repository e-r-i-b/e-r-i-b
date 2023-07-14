/**
 * GetAutoPaymentListRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип сообщения-запроса для интерфейса GAPL - получение списка платежей
 * по подписке
 */
public class GetAutoPaymentListRq_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    /* Идентификационная информация о подписке */
    private com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionId;

    /* Список возможных состояний автоплатежа. Если не передано, то
     * фильтрация по состояниям  не осуществляется */
    private com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type[] paymentStatusList;

    /* Период времени за который были исполнены платежи.
     * Если элемент отсутствует, то фильтра по дате исполнения нет и возвращается
     * 10 последних платежей по подписке */
    private com.rssl.phizic.test.webgate.esberib.generated.SelRangeDtTm_Type selRangeDtTm;

    public GetAutoPaymentListRq_Type() {
    }

    public GetAutoPaymentListRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionId,
           com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type[] paymentStatusList,
           com.rssl.phizic.test.webgate.esberib.generated.SelRangeDtTm_Type selRangeDtTm) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.bankInfo = bankInfo;
           this.autoSubscriptionId = autoSubscriptionId;
           this.paymentStatusList = paymentStatusList;
           this.selRangeDtTm = selRangeDtTm;
    }


    /**
     * Gets the rqUID value for this GetAutoPaymentListRq_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this GetAutoPaymentListRq_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this GetAutoPaymentListRq_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this GetAutoPaymentListRq_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this GetAutoPaymentListRq_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this GetAutoPaymentListRq_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this GetAutoPaymentListRq_Type.
     * 
     * @return SPName
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this GetAutoPaymentListRq_Type.
     * 
     * @param SPName
     */
    public void setSPName(com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the bankInfo value for this GetAutoPaymentListRq_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this GetAutoPaymentListRq_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the autoSubscriptionId value for this GetAutoPaymentListRq_Type.
     * 
     * @return autoSubscriptionId   * Идентификационная информация о подписке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type getAutoSubscriptionId() {
        return autoSubscriptionId;
    }


    /**
     * Sets the autoSubscriptionId value for this GetAutoPaymentListRq_Type.
     * 
     * @param autoSubscriptionId   * Идентификационная информация о подписке
     */
    public void setAutoSubscriptionId(com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionId) {
        this.autoSubscriptionId = autoSubscriptionId;
    }


    /**
     * Gets the paymentStatusList value for this GetAutoPaymentListRq_Type.
     * 
     * @return paymentStatusList   * Список возможных состояний автоплатежа. Если не передано, то
     * фильтрация по состояниям  не осуществляется
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type[] getPaymentStatusList() {
        return paymentStatusList;
    }


    /**
     * Sets the paymentStatusList value for this GetAutoPaymentListRq_Type.
     * 
     * @param paymentStatusList   * Список возможных состояний автоплатежа. Если не передано, то
     * фильтрация по состояниям  не осуществляется
     */
    public void setPaymentStatusList(com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type[] paymentStatusList) {
        this.paymentStatusList = paymentStatusList;
    }


    /**
     * Gets the selRangeDtTm value for this GetAutoPaymentListRq_Type.
     * 
     * @return selRangeDtTm   * Период времени за который были исполнены платежи.
     * Если элемент отсутствует, то фильтра по дате исполнения нет и возвращается
     * 10 последних платежей по подписке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SelRangeDtTm_Type getSelRangeDtTm() {
        return selRangeDtTm;
    }


    /**
     * Sets the selRangeDtTm value for this GetAutoPaymentListRq_Type.
     * 
     * @param selRangeDtTm   * Период времени за который были исполнены платежи.
     * Если элемент отсутствует, то фильтра по дате исполнения нет и возвращается
     * 10 последних платежей по подписке
     */
    public void setSelRangeDtTm(com.rssl.phizic.test.webgate.esberib.generated.SelRangeDtTm_Type selRangeDtTm) {
        this.selRangeDtTm = selRangeDtTm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAutoPaymentListRq_Type)) return false;
        GetAutoPaymentListRq_Type other = (GetAutoPaymentListRq_Type) obj;
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
            ((this.autoSubscriptionId==null && other.getAutoSubscriptionId()==null) || 
             (this.autoSubscriptionId!=null &&
              this.autoSubscriptionId.equals(other.getAutoSubscriptionId()))) &&
            ((this.paymentStatusList==null && other.getPaymentStatusList()==null) || 
             (this.paymentStatusList!=null &&
              java.util.Arrays.equals(this.paymentStatusList, other.getPaymentStatusList()))) &&
            ((this.selRangeDtTm==null && other.getSelRangeDtTm()==null) || 
             (this.selRangeDtTm!=null &&
              this.selRangeDtTm.equals(other.getSelRangeDtTm())));
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
        if (getAutoSubscriptionId() != null) {
            _hashCode += getAutoSubscriptionId().hashCode();
        }
        if (getPaymentStatusList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaymentStatusList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaymentStatusList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSelRangeDtTm() != null) {
            _hashCode += getSelRangeDtTm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetAutoPaymentListRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoPaymentListRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID_Type"));
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
        elemField.setFieldName("autoSubscriptionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentStatusList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusASAP_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatus"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selRangeDtTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SelRangeDtTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SelRangeDtTm_Type"));
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
