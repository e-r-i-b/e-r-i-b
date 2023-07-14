/**
 * AttributeLength_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Описание длины поля
 */
public class AttributeLength_Type  implements java.io.Serializable {
    /* Максимальная длина поля */
    private java.math.BigInteger maxLength;

    /* Минимальная длина поля */
    private java.math.BigInteger minLength;

    public AttributeLength_Type() {
    }

    public AttributeLength_Type(
           java.math.BigInteger maxLength,
           java.math.BigInteger minLength) {
           this.maxLength = maxLength;
           this.minLength = minLength;
    }


    /**
     * Gets the maxLength value for this AttributeLength_Type.
     * 
     * @return maxLength   * Максимальная длина поля
     */
    public java.math.BigInteger getMaxLength() {
        return maxLength;
    }


    /**
     * Sets the maxLength value for this AttributeLength_Type.
     * 
     * @param maxLength   * Максимальная длина поля
     */
    public void setMaxLength(java.math.BigInteger maxLength) {
        this.maxLength = maxLength;
    }


    /**
     * Gets the minLength value for this AttributeLength_Type.
     * 
     * @return minLength   * Минимальная длина поля
     */
    public java.math.BigInteger getMinLength() {
        return minLength;
    }


    /**
     * Sets the minLength value for this AttributeLength_Type.
     * 
     * @param minLength   * Минимальная длина поля
     */
    public void setMinLength(java.math.BigInteger minLength) {
        this.minLength = minLength;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttributeLength_Type)) return false;
        AttributeLength_Type other = (AttributeLength_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.maxLength==null && other.getMaxLength()==null) || 
             (this.maxLength!=null &&
              this.maxLength.equals(other.getMaxLength()))) &&
            ((this.minLength==null && other.getMinLength()==null) || 
             (this.minLength!=null &&
              this.minLength.equals(other.getMinLength())));
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
        if (getMaxLength() != null) {
            _hashCode += getMaxLength().hashCode();
        }
        if (getMinLength() != null) {
            _hashCode += getMinLength().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttributeLength_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AttributeLength_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxLength");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MaxLength"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minLength");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MinLength"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
