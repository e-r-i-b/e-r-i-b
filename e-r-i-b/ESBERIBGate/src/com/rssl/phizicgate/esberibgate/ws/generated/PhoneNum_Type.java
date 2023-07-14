/**
 * PhoneNum_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class PhoneNum_Type  implements java.io.Serializable {
    /* Тип телефона. Если не задан, то считается Mobile */
    private java.lang.String phoneType;

    /* Код оператора связи */
    private java.lang.String phoneCode;

    /* Наименование оператора связи */
    private java.lang.String phoneOperName;

    /* Номер телефона */
    private java.lang.String phone;

    public PhoneNum_Type() {
    }

    public PhoneNum_Type(
           java.lang.String phoneType,
           java.lang.String phoneCode,
           java.lang.String phoneOperName,
           java.lang.String phone) {
           this.phoneType = phoneType;
           this.phoneCode = phoneCode;
           this.phoneOperName = phoneOperName;
           this.phone = phone;
    }


    /**
     * Gets the phoneType value for this PhoneNum_Type.
     * 
     * @return phoneType   * Тип телефона. Если не задан, то считается Mobile
     */
    public java.lang.String getPhoneType() {
        return phoneType;
    }


    /**
     * Sets the phoneType value for this PhoneNum_Type.
     * 
     * @param phoneType   * Тип телефона. Если не задан, то считается Mobile
     */
    public void setPhoneType(java.lang.String phoneType) {
        this.phoneType = phoneType;
    }


    /**
     * Gets the phoneCode value for this PhoneNum_Type.
     * 
     * @return phoneCode   * Код оператора связи
     */
    public java.lang.String getPhoneCode() {
        return phoneCode;
    }


    /**
     * Sets the phoneCode value for this PhoneNum_Type.
     * 
     * @param phoneCode   * Код оператора связи
     */
    public void setPhoneCode(java.lang.String phoneCode) {
        this.phoneCode = phoneCode;
    }


    /**
     * Gets the phoneOperName value for this PhoneNum_Type.
     * 
     * @return phoneOperName   * Наименование оператора связи
     */
    public java.lang.String getPhoneOperName() {
        return phoneOperName;
    }


    /**
     * Sets the phoneOperName value for this PhoneNum_Type.
     * 
     * @param phoneOperName   * Наименование оператора связи
     */
    public void setPhoneOperName(java.lang.String phoneOperName) {
        this.phoneOperName = phoneOperName;
    }


    /**
     * Gets the phone value for this PhoneNum_Type.
     * 
     * @return phone   * Номер телефона
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this PhoneNum_Type.
     * 
     * @param phone   * Номер телефона
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PhoneNum_Type)) return false;
        PhoneNum_Type other = (PhoneNum_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.phoneType==null && other.getPhoneType()==null) || 
             (this.phoneType!=null &&
              this.phoneType.equals(other.getPhoneType()))) &&
            ((this.phoneCode==null && other.getPhoneCode()==null) || 
             (this.phoneCode!=null &&
              this.phoneCode.equals(other.getPhoneCode()))) &&
            ((this.phoneOperName==null && other.getPhoneOperName()==null) || 
             (this.phoneOperName!=null &&
              this.phoneOperName.equals(other.getPhoneOperName()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone())));
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
        if (getPhoneType() != null) {
            _hashCode += getPhoneType().hashCode();
        }
        if (getPhoneCode() != null) {
            _hashCode += getPhoneCode().hashCode();
        }
        if (getPhoneOperName() != null) {
            _hashCode += getPhoneOperName().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PhoneNum_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNum_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneOperName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneOperName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
