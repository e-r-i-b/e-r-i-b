/**
 * ClientPhonesType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;


/**
 * Все телефоны клиента
 */
public class ClientPhonesType  implements java.io.Serializable {
    private com.rssl.phizic.asfilial.listener.generated.PhoneNumberType phone;

    private java.lang.String confirmCode;

    public ClientPhonesType() {
    }

    public ClientPhonesType(
           com.rssl.phizic.asfilial.listener.generated.PhoneNumberType phone,
           java.lang.String confirmCode) {
           this.phone = phone;
           this.confirmCode = confirmCode;
    }


    /**
     * Gets the phone value for this ClientPhonesType.
     * 
     * @return phone
     */
    public com.rssl.phizic.asfilial.listener.generated.PhoneNumberType getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this ClientPhonesType.
     * 
     * @param phone
     */
    public void setPhone(com.rssl.phizic.asfilial.listener.generated.PhoneNumberType phone) {
        this.phone = phone;
    }


    /**
     * Gets the confirmCode value for this ClientPhonesType.
     * 
     * @return confirmCode
     */
    public java.lang.String getConfirmCode() {
        return confirmCode;
    }


    /**
     * Sets the confirmCode value for this ClientPhonesType.
     * 
     * @param confirmCode
     */
    public void setConfirmCode(java.lang.String confirmCode) {
        this.confirmCode = confirmCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientPhonesType)) return false;
        ClientPhonesType other = (ClientPhonesType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.confirmCode==null && other.getConfirmCode()==null) || 
             (this.confirmCode!=null &&
              this.confirmCode.equals(other.getConfirmCode())));
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
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getConfirmCode() != null) {
            _hashCode += getConfirmCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientPhonesType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientPhonesType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "Phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PhoneNumberType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("confirmCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ConfirmCode"));
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
