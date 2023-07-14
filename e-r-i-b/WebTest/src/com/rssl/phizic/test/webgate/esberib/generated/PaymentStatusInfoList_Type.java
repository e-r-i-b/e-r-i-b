/**
 * PaymentStatusInfoList_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Список задолженностей по АП
 */
public class PaymentStatusInfoList_Type  implements java.io.Serializable {
    /* Идентификационная информация о задолженности */
    private com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentId_Type autoPaymentId;

    /* Результат обработки платежа */
    private com.rssl.phizic.test.webgate.esberib.generated.Status_Type status;

    public PaymentStatusInfoList_Type() {
    }

    public PaymentStatusInfoList_Type(
           com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentId_Type autoPaymentId,
           com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
           this.autoPaymentId = autoPaymentId;
           this.status = status;
    }


    /**
     * Gets the autoPaymentId value for this PaymentStatusInfoList_Type.
     * 
     * @return autoPaymentId   * Идентификационная информация о задолженности
     */
    public com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentId_Type getAutoPaymentId() {
        return autoPaymentId;
    }


    /**
     * Sets the autoPaymentId value for this PaymentStatusInfoList_Type.
     * 
     * @param autoPaymentId   * Идентификационная информация о задолженности
     */
    public void setAutoPaymentId(com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentId_Type autoPaymentId) {
        this.autoPaymentId = autoPaymentId;
    }


    /**
     * Gets the status value for this PaymentStatusInfoList_Type.
     * 
     * @return status   * Результат обработки платежа
     */
    public com.rssl.phizic.test.webgate.esberib.generated.Status_Type getStatus() {
        return status;
    }


    /**
     * Sets the status value for this PaymentStatusInfoList_Type.
     * 
     * @param status   * Результат обработки платежа
     */
    public void setStatus(com.rssl.phizic.test.webgate.esberib.generated.Status_Type status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentStatusInfoList_Type)) return false;
        PaymentStatusInfoList_Type other = (PaymentStatusInfoList_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.autoPaymentId==null && other.getAutoPaymentId()==null) || 
             (this.autoPaymentId!=null &&
              this.autoPaymentId.equals(other.getAutoPaymentId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getAutoPaymentId() != null) {
            _hashCode += getAutoPaymentId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentStatusInfoList_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusInfoList_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoPaymentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentId_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type"));
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
