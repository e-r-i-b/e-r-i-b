/**
 * MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo  implements java.io.Serializable {
    /* БИК банка получателя */
    private java.lang.String BIC;

    /* Корсчет банка получателя */
    private java.lang.String corrAcctId;

    public MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo() {
    }

    public MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo(
           java.lang.String BIC,
           java.lang.String corrAcctId) {
           this.BIC = BIC;
           this.corrAcctId = corrAcctId;
    }


    /**
     * Gets the BIC value for this MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo.
     * 
     * @return BIC   * БИК банка получателя
     */
    public java.lang.String getBIC() {
        return BIC;
    }


    /**
     * Sets the BIC value for this MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo.
     * 
     * @param BIC   * БИК банка получателя
     */
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }


    /**
     * Gets the corrAcctId value for this MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo.
     * 
     * @return corrAcctId   * Корсчет банка получателя
     */
    public java.lang.String getCorrAcctId() {
        return corrAcctId;
    }


    /**
     * Sets the corrAcctId value for this MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo.
     * 
     * @param corrAcctId   * Корсчет банка получателя
     */
    public void setCorrAcctId(java.lang.String corrAcctId) {
        this.corrAcctId = corrAcctId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo)) return false;
        MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo other = (MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.BIC==null && other.getBIC()==null) || 
             (this.BIC!=null &&
              this.BIC.equals(other.getBIC()))) &&
            ((this.corrAcctId==null && other.getCorrAcctId()==null) || 
             (this.corrAcctId!=null &&
              this.corrAcctId.equals(other.getCorrAcctId())));
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
        if (getBIC() != null) {
            _hashCode += getBIC().hashCode();
        }
        if (getCorrAcctId() != null) {
            _hashCode += getCorrAcctId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MDMPaymentTemplUpdateRq_TypePayInfoToBankInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>MDMPaymentTemplUpdateRq_Type>PayInfo>ToBankInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrAcctId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrAcctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
