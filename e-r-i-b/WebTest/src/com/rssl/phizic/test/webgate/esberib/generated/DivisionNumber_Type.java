/**
 * DivisionNumber_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип и номер раздела
 */
public class DivisionNumber_Type  implements java.io.Serializable {
    /* Тип раздела. Должен представлять собой текст на русском языке
     * начинающийся с большой буквы, готовый для показа пользователю. */
    private java.lang.String type;

    /* Номер раздела. */
    private java.lang.String number;

    public DivisionNumber_Type() {
    }

    public DivisionNumber_Type(
           java.lang.String type,
           java.lang.String number) {
           this.type = type;
           this.number = number;
    }


    /**
     * Gets the type value for this DivisionNumber_Type.
     * 
     * @return type   * Тип раздела. Должен представлять собой текст на русском языке
     * начинающийся с большой буквы, готовый для показа пользователю.
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this DivisionNumber_Type.
     * 
     * @param type   * Тип раздела. Должен представлять собой текст на русском языке
     * начинающийся с большой буквы, готовый для показа пользователю.
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the number value for this DivisionNumber_Type.
     * 
     * @return number   * Номер раздела.
     */
    public java.lang.String getNumber() {
        return number;
    }


    /**
     * Sets the number value for this DivisionNumber_Type.
     * 
     * @param number   * Номер раздела.
     */
    public void setNumber(java.lang.String number) {
        this.number = number;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DivisionNumber_Type)) return false;
        DivisionNumber_Type other = (DivisionNumber_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DivisionNumber_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DivisionNumber_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Number"));
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
