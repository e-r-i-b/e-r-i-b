/**
 * SPDefFieldShort_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Поля, определяемые ПУ <SPDefField>
 */
public class SPDefFieldShort_Type  implements java.io.Serializable {
    /* Код ТБ+индекс ОСБ (информация используется ЭСК) */
    private long fieldData1;

    /* Номер */
    private long fieldNum;

    public SPDefFieldShort_Type() {
    }

    public SPDefFieldShort_Type(
           long fieldData1,
           long fieldNum) {
           this.fieldData1 = fieldData1;
           this.fieldNum = fieldNum;
    }


    /**
     * Gets the fieldData1 value for this SPDefFieldShort_Type.
     * 
     * @return fieldData1   * Код ТБ+индекс ОСБ (информация используется ЭСК)
     */
    public long getFieldData1() {
        return fieldData1;
    }


    /**
     * Sets the fieldData1 value for this SPDefFieldShort_Type.
     * 
     * @param fieldData1   * Код ТБ+индекс ОСБ (информация используется ЭСК)
     */
    public void setFieldData1(long fieldData1) {
        this.fieldData1 = fieldData1;
    }


    /**
     * Gets the fieldNum value for this SPDefFieldShort_Type.
     * 
     * @return fieldNum   * Номер
     */
    public long getFieldNum() {
        return fieldNum;
    }


    /**
     * Sets the fieldNum value for this SPDefFieldShort_Type.
     * 
     * @param fieldNum   * Номер
     */
    public void setFieldNum(long fieldNum) {
        this.fieldNum = fieldNum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SPDefFieldShort_Type)) return false;
        SPDefFieldShort_Type other = (SPDefFieldShort_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.fieldData1 == other.getFieldData1() &&
            this.fieldNum == other.getFieldNum();
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
        _hashCode += new Long(getFieldData1()).hashCode();
        _hashCode += new Long(getFieldNum()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SPDefFieldShort_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPDefFieldShort_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldData1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldData1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
