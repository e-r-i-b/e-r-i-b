/**
 * ContactInfoSec_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Контактная информация МДО формат (проект сберсертификаты)
 */
public class ContactInfoSec_Type  implements java.io.Serializable {
    /* Телефонный номер */
    private java.lang.String phoneNum;

    /* Полный адрес */
    private com.rssl.phizic.test.webgate.esberib.generated.PostAddr_Type[] postAddr;

    /* Тип телефона */
    private java.lang.String phoneType;

    public ContactInfoSec_Type() {
    }

    public ContactInfoSec_Type(
           java.lang.String phoneNum,
           com.rssl.phizic.test.webgate.esberib.generated.PostAddr_Type[] postAddr,
           java.lang.String phoneType) {
           this.phoneNum = phoneNum;
           this.postAddr = postAddr;
           this.phoneType = phoneType;
    }


    /**
     * Gets the phoneNum value for this ContactInfoSec_Type.
     * 
     * @return phoneNum   * Телефонный номер
     */
    public java.lang.String getPhoneNum() {
        return phoneNum;
    }


    /**
     * Sets the phoneNum value for this ContactInfoSec_Type.
     * 
     * @param phoneNum   * Телефонный номер
     */
    public void setPhoneNum(java.lang.String phoneNum) {
        this.phoneNum = phoneNum;
    }


    /**
     * Gets the postAddr value for this ContactInfoSec_Type.
     * 
     * @return postAddr   * Полный адрес
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PostAddr_Type[] getPostAddr() {
        return postAddr;
    }


    /**
     * Sets the postAddr value for this ContactInfoSec_Type.
     * 
     * @param postAddr   * Полный адрес
     */
    public void setPostAddr(com.rssl.phizic.test.webgate.esberib.generated.PostAddr_Type[] postAddr) {
        this.postAddr = postAddr;
    }

    public com.rssl.phizic.test.webgate.esberib.generated.PostAddr_Type getPostAddr(int i) {
        return this.postAddr[i];
    }

    public void setPostAddr(int i, com.rssl.phizic.test.webgate.esberib.generated.PostAddr_Type _value) {
        this.postAddr[i] = _value;
    }


    /**
     * Gets the phoneType value for this ContactInfoSec_Type.
     * 
     * @return phoneType   * Тип телефона
     */
    public java.lang.String getPhoneType() {
        return phoneType;
    }


    /**
     * Sets the phoneType value for this ContactInfoSec_Type.
     * 
     * @param phoneType   * Тип телефона
     */
    public void setPhoneType(java.lang.String phoneType) {
        this.phoneType = phoneType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContactInfoSec_Type)) return false;
        ContactInfoSec_Type other = (ContactInfoSec_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.phoneNum==null && other.getPhoneNum()==null) || 
             (this.phoneNum!=null &&
              this.phoneNum.equals(other.getPhoneNum()))) &&
            ((this.postAddr==null && other.getPostAddr()==null) || 
             (this.postAddr!=null &&
              java.util.Arrays.equals(this.postAddr, other.getPostAddr()))) &&
            ((this.phoneType==null && other.getPhoneType()==null) || 
             (this.phoneType!=null &&
              this.phoneType.equals(other.getPhoneType())));
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
        if (getPhoneNum() != null) {
            _hashCode += getPhoneNum().hashCode();
        }
        if (getPostAddr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPostAddr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPostAddr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPhoneType() != null) {
            _hashCode += getPhoneType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContactInfoSec_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfoSec_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PostAddr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PostAddr_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
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
