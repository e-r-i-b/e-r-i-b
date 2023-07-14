/**
 * BankInfoExt_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Расширенная информация о банке
 */
public class BankInfoExt_Type  implements java.io.Serializable {
    /* Код подразделения Сбербанка */
    private java.lang.String bankId;

    /* Наименование банка */
    private java.lang.String name;

    /* Контактная информация (адрес, телефон) */
    private com.rssl.phizicgate.esberibgate.ws.generated.ContactInfoSec_Type contactInfo;

    /* Платежные реквизиты */
    private com.rssl.phizicgate.esberibgate.ws.generated.PaymentDetails_Type paymentDetails;

    public BankInfoExt_Type() {
    }

    public BankInfoExt_Type(
           java.lang.String bankId,
           java.lang.String name,
           com.rssl.phizicgate.esberibgate.ws.generated.ContactInfoSec_Type contactInfo,
           com.rssl.phizicgate.esberibgate.ws.generated.PaymentDetails_Type paymentDetails) {
           this.bankId = bankId;
           this.name = name;
           this.contactInfo = contactInfo;
           this.paymentDetails = paymentDetails;
    }


    /**
     * Gets the bankId value for this BankInfoExt_Type.
     * 
     * @return bankId   * Код подразделения Сбербанка
     */
    public java.lang.String getBankId() {
        return bankId;
    }


    /**
     * Sets the bankId value for this BankInfoExt_Type.
     * 
     * @param bankId   * Код подразделения Сбербанка
     */
    public void setBankId(java.lang.String bankId) {
        this.bankId = bankId;
    }


    /**
     * Gets the name value for this BankInfoExt_Type.
     * 
     * @return name   * Наименование банка
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this BankInfoExt_Type.
     * 
     * @param name   * Наименование банка
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the contactInfo value for this BankInfoExt_Type.
     * 
     * @return contactInfo   * Контактная информация (адрес, телефон)
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.ContactInfoSec_Type getContactInfo() {
        return contactInfo;
    }


    /**
     * Sets the contactInfo value for this BankInfoExt_Type.
     * 
     * @param contactInfo   * Контактная информация (адрес, телефон)
     */
    public void setContactInfo(com.rssl.phizicgate.esberibgate.ws.generated.ContactInfoSec_Type contactInfo) {
        this.contactInfo = contactInfo;
    }


    /**
     * Gets the paymentDetails value for this BankInfoExt_Type.
     * 
     * @return paymentDetails   * Платежные реквизиты
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.PaymentDetails_Type getPaymentDetails() {
        return paymentDetails;
    }


    /**
     * Sets the paymentDetails value for this BankInfoExt_Type.
     * 
     * @param paymentDetails   * Платежные реквизиты
     */
    public void setPaymentDetails(com.rssl.phizicgate.esberibgate.ws.generated.PaymentDetails_Type paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankInfoExt_Type)) return false;
        BankInfoExt_Type other = (BankInfoExt_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bankId==null && other.getBankId()==null) || 
             (this.bankId!=null &&
              this.bankId.equals(other.getBankId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.contactInfo==null && other.getContactInfo()==null) || 
             (this.contactInfo!=null &&
              this.contactInfo.equals(other.getContactInfo()))) &&
            ((this.paymentDetails==null && other.getPaymentDetails()==null) || 
             (this.paymentDetails!=null &&
              this.paymentDetails.equals(other.getPaymentDetails())));
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
        if (getBankId() != null) {
            _hashCode += getBankId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getContactInfo() != null) {
            _hashCode += getContactInfo().hashCode();
        }
        if (getPaymentDetails() != null) {
            _hashCode += getPaymentDetails().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankInfoExt_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoExt_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfoSec_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentDetails_Type"));
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
