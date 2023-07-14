/**
 * GetAutoSubscriptionListRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип сообщения-запроса для интерфейса GASL - получение списка подписок
 * по платежным инструментам
 */
public class GetAutoSubscriptionListRq_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.lang.String rqTm;

    private java.lang.String operUID;

    private com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName;

    private com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo;

    private com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type[] bankAcctRec;

    /* Список возможных состояний подписки. Если не передано, то фильтрация
     * по состояниям  не осуществляется */
    private com.rssl.phizic.test.webgate.esberib.generated.AutopayStatus_Type[] autopayStatusList;

    /* Список типов Автоплатежей */
    private com.rssl.phizic.test.webgate.esberib.generated.AutopayTypeList_Type[] autopayTypeList;

    public GetAutoSubscriptionListRq_Type() {
    }

    public GetAutoSubscriptionListRq_Type(
           java.lang.String rqUID,
           java.lang.String rqTm,
           java.lang.String operUID,
           com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName,
           com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo,
           com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type[] bankAcctRec,
           com.rssl.phizic.test.webgate.esberib.generated.AutopayStatus_Type[] autopayStatusList,
           com.rssl.phizic.test.webgate.esberib.generated.AutopayTypeList_Type[] autopayTypeList) {
           this.rqUID = rqUID;
           this.rqTm = rqTm;
           this.operUID = operUID;
           this.SPName = SPName;
           this.bankInfo = bankInfo;
           this.bankAcctRec = bankAcctRec;
           this.autopayStatusList = autopayStatusList;
           this.autopayTypeList = autopayTypeList;
    }


    /**
     * Gets the rqUID value for this GetAutoSubscriptionListRq_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this GetAutoSubscriptionListRq_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTm value for this GetAutoSubscriptionListRq_Type.
     * 
     * @return rqTm
     */
    public java.lang.String getRqTm() {
        return rqTm;
    }


    /**
     * Sets the rqTm value for this GetAutoSubscriptionListRq_Type.
     * 
     * @param rqTm
     */
    public void setRqTm(java.lang.String rqTm) {
        this.rqTm = rqTm;
    }


    /**
     * Gets the operUID value for this GetAutoSubscriptionListRq_Type.
     * 
     * @return operUID
     */
    public java.lang.String getOperUID() {
        return operUID;
    }


    /**
     * Sets the operUID value for this GetAutoSubscriptionListRq_Type.
     * 
     * @param operUID
     */
    public void setOperUID(java.lang.String operUID) {
        this.operUID = operUID;
    }


    /**
     * Gets the SPName value for this GetAutoSubscriptionListRq_Type.
     * 
     * @return SPName
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this GetAutoSubscriptionListRq_Type.
     * 
     * @param SPName
     */
    public void setSPName(com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }


    /**
     * Gets the bankInfo value for this GetAutoSubscriptionListRq_Type.
     * 
     * @return bankInfo
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type getBankInfo() {
        return bankInfo;
    }


    /**
     * Sets the bankInfo value for this GetAutoSubscriptionListRq_Type.
     * 
     * @param bankInfo
     */
    public void setBankInfo(com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type bankInfo) {
        this.bankInfo = bankInfo;
    }


    /**
     * Gets the bankAcctRec value for this GetAutoSubscriptionListRq_Type.
     * 
     * @return bankAcctRec
     */
    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type[] getBankAcctRec() {
        return bankAcctRec;
    }


    /**
     * Sets the bankAcctRec value for this GetAutoSubscriptionListRq_Type.
     * 
     * @param bankAcctRec
     */
    public void setBankAcctRec(com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type[] bankAcctRec) {
        this.bankAcctRec = bankAcctRec;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type getBankAcctRec(int i) {
        return this.bankAcctRec[i];
    }

    public void setBankAcctRec(int i, com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type _value) {
        this.bankAcctRec[i] = _value;
    }


    /**
     * Gets the autopayStatusList value for this GetAutoSubscriptionListRq_Type.
     * 
     * @return autopayStatusList   * Список возможных состояний подписки. Если не передано, то фильтрация
     * по состояниям  не осуществляется
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutopayStatus_Type[] getAutopayStatusList() {
        return autopayStatusList;
    }


    /**
     * Sets the autopayStatusList value for this GetAutoSubscriptionListRq_Type.
     * 
     * @param autopayStatusList   * Список возможных состояний подписки. Если не передано, то фильтрация
     * по состояниям  не осуществляется
     */
    public void setAutopayStatusList(com.rssl.phizic.test.webgate.esberib.generated.AutopayStatus_Type[] autopayStatusList) {
        this.autopayStatusList = autopayStatusList;
    }


    /**
     * Gets the autopayTypeList value for this GetAutoSubscriptionListRq_Type.
     * 
     * @return autopayTypeList   * Список типов Автоплатежей
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutopayTypeList_Type[] getAutopayTypeList() {
        return autopayTypeList;
    }


    /**
     * Sets the autopayTypeList value for this GetAutoSubscriptionListRq_Type.
     * 
     * @param autopayTypeList   * Список типов Автоплатежей
     */
    public void setAutopayTypeList(com.rssl.phizic.test.webgate.esberib.generated.AutopayTypeList_Type[] autopayTypeList) {
        this.autopayTypeList = autopayTypeList;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.AutopayTypeList_Type getAutopayTypeList(int i) {
        return this.autopayTypeList[i];
    }

    public void setAutopayTypeList(int i, com.rssl.phizic.test.webgate.esberib.generated.AutopayTypeList_Type _value) {
        this.autopayTypeList[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAutoSubscriptionListRq_Type)) return false;
        GetAutoSubscriptionListRq_Type other = (GetAutoSubscriptionListRq_Type) obj;
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
            ((this.bankAcctRec==null && other.getBankAcctRec()==null) || 
             (this.bankAcctRec!=null &&
              java.util.Arrays.equals(this.bankAcctRec, other.getBankAcctRec()))) &&
            ((this.autopayStatusList==null && other.getAutopayStatusList()==null) || 
             (this.autopayStatusList!=null &&
              java.util.Arrays.equals(this.autopayStatusList, other.getAutopayStatusList()))) &&
            ((this.autopayTypeList==null && other.getAutopayTypeList()==null) || 
             (this.autopayTypeList!=null &&
              java.util.Arrays.equals(this.autopayTypeList, other.getAutopayTypeList())));
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
        if (getBankAcctRec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBankAcctRec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBankAcctRec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAutopayStatusList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAutopayStatusList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAutopayStatusList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAutopayTypeList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAutopayTypeList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAutopayTypeList(), i);
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
        new org.apache.axis.description.TypeDesc(GetAutoSubscriptionListRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoSubscriptionListRq_Type"));
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
        elemField.setFieldName("bankAcctRec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autopayStatusList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatusList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autopayTypeList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayTypeList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayTypeList_Type"));
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
