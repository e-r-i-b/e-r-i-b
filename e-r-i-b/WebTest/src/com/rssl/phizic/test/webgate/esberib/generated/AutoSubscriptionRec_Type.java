/**
 * AutoSubscriptionRec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип Запись о подписке автоплатежа
 */
public class AutoSubscriptionRec_Type  implements java.io.Serializable {
    /* Идентификационная информация о подписке */
    private com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionId;

    /* Информация о подписке */
    private com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionInfo_Type autoSubscriptionInfo;

    /* Информация о шаблоне автоплатежа */
    private com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentTemplate_Type autoPaymentTemplate;

    public AutoSubscriptionRec_Type() {
    }

    public AutoSubscriptionRec_Type(
           com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionId,
           com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionInfo_Type autoSubscriptionInfo,
           com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentTemplate_Type autoPaymentTemplate) {
           this.autoSubscriptionId = autoSubscriptionId;
           this.autoSubscriptionInfo = autoSubscriptionInfo;
           this.autoPaymentTemplate = autoPaymentTemplate;
    }


    /**
     * Gets the autoSubscriptionId value for this AutoSubscriptionRec_Type.
     * 
     * @return autoSubscriptionId   * Идентификационная информация о подписке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type getAutoSubscriptionId() {
        return autoSubscriptionId;
    }


    /**
     * Sets the autoSubscriptionId value for this AutoSubscriptionRec_Type.
     * 
     * @param autoSubscriptionId   * Идентификационная информация о подписке
     */
    public void setAutoSubscriptionId(com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type autoSubscriptionId) {
        this.autoSubscriptionId = autoSubscriptionId;
    }


    /**
     * Gets the autoSubscriptionInfo value for this AutoSubscriptionRec_Type.
     * 
     * @return autoSubscriptionInfo   * Информация о подписке
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionInfo_Type getAutoSubscriptionInfo() {
        return autoSubscriptionInfo;
    }


    /**
     * Sets the autoSubscriptionInfo value for this AutoSubscriptionRec_Type.
     * 
     * @param autoSubscriptionInfo   * Информация о подписке
     */
    public void setAutoSubscriptionInfo(com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionInfo_Type autoSubscriptionInfo) {
        this.autoSubscriptionInfo = autoSubscriptionInfo;
    }


    /**
     * Gets the autoPaymentTemplate value for this AutoSubscriptionRec_Type.
     * 
     * @return autoPaymentTemplate   * Информация о шаблоне автоплатежа
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentTemplate_Type getAutoPaymentTemplate() {
        return autoPaymentTemplate;
    }


    /**
     * Sets the autoPaymentTemplate value for this AutoSubscriptionRec_Type.
     * 
     * @param autoPaymentTemplate   * Информация о шаблоне автоплатежа
     */
    public void setAutoPaymentTemplate(com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentTemplate_Type autoPaymentTemplate) {
        this.autoPaymentTemplate = autoPaymentTemplate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AutoSubscriptionRec_Type)) return false;
        AutoSubscriptionRec_Type other = (AutoSubscriptionRec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.autoSubscriptionId==null && other.getAutoSubscriptionId()==null) || 
             (this.autoSubscriptionId!=null &&
              this.autoSubscriptionId.equals(other.getAutoSubscriptionId()))) &&
            ((this.autoSubscriptionInfo==null && other.getAutoSubscriptionInfo()==null) || 
             (this.autoSubscriptionInfo!=null &&
              this.autoSubscriptionInfo.equals(other.getAutoSubscriptionInfo()))) &&
            ((this.autoPaymentTemplate==null && other.getAutoPaymentTemplate()==null) || 
             (this.autoPaymentTemplate!=null &&
              this.autoPaymentTemplate.equals(other.getAutoPaymentTemplate())));
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
        if (getAutoSubscriptionId() != null) {
            _hashCode += getAutoSubscriptionId().hashCode();
        }
        if (getAutoSubscriptionInfo() != null) {
            _hashCode += getAutoSubscriptionInfo().hashCode();
        }
        if (getAutoPaymentTemplate() != null) {
            _hashCode += getAutoPaymentTemplate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AutoSubscriptionRec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionRec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoSubscriptionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionId_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoSubscriptionInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionInfo_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoPaymentTemplate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentTemplate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentTemplate_Type"));
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
