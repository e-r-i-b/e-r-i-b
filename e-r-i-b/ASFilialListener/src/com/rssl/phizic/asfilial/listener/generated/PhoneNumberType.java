/**
 * PhoneNumberType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;

public class PhoneNumberType  implements java.io.Serializable {
    /* Номер в международном формате без знака "+"
     *                         REGEXP: \d{11} */
    private java.lang.String phoneNumberN;

    /* Мобильный Оператор */
    private java.lang.String mobilePhoneOperator;

    public PhoneNumberType() {
    }

    public PhoneNumberType(
           java.lang.String phoneNumberN,
           java.lang.String mobilePhoneOperator) {
           this.phoneNumberN = phoneNumberN;
           this.mobilePhoneOperator = mobilePhoneOperator;
    }


    /**
     * Gets the phoneNumberN value for this PhoneNumberType.
     * 
     * @return phoneNumberN   * Номер в международном формате без знака "+"
     *                         REGEXP: \d{11}
     */
    public java.lang.String getPhoneNumberN() {
        return phoneNumberN;
    }


    /**
     * Sets the phoneNumberN value for this PhoneNumberType.
     * 
     * @param phoneNumberN   * Номер в международном формате без знака "+"
     *                         REGEXP: \d{11}
     */
    public void setPhoneNumberN(java.lang.String phoneNumberN) {
        this.phoneNumberN = phoneNumberN;
    }


    /**
     * Gets the mobilePhoneOperator value for this PhoneNumberType.
     * 
     * @return mobilePhoneOperator   * Мобильный Оператор
     */
    public java.lang.String getMobilePhoneOperator() {
        return mobilePhoneOperator;
    }


    /**
     * Sets the mobilePhoneOperator value for this PhoneNumberType.
     * 
     * @param mobilePhoneOperator   * Мобильный Оператор
     */
    public void setMobilePhoneOperator(java.lang.String mobilePhoneOperator) {
        this.mobilePhoneOperator = mobilePhoneOperator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PhoneNumberType)) return false;
        PhoneNumberType other = (PhoneNumberType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.phoneNumberN==null && other.getPhoneNumberN()==null) || 
             (this.phoneNumberN!=null &&
              this.phoneNumberN.equals(other.getPhoneNumberN()))) &&
            ((this.mobilePhoneOperator==null && other.getMobilePhoneOperator()==null) || 
             (this.mobilePhoneOperator!=null &&
              this.mobilePhoneOperator.equals(other.getMobilePhoneOperator())));
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
        if (getPhoneNumberN() != null) {
            _hashCode += getPhoneNumberN().hashCode();
        }
        if (getMobilePhoneOperator() != null) {
            _hashCode += getMobilePhoneOperator().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PhoneNumberType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PhoneNumberType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNumberN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PhoneNumberN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobilePhoneOperator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobilePhoneOperator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
