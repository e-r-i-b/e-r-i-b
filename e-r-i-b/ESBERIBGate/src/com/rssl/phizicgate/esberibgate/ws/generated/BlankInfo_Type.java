/**
 * BlankInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Бланк ценной бумаги
 */
public class BlankInfo_Type  implements java.io.Serializable {
    /* Тип бланка */
    private java.lang.String blankType;

    /* Уникальный номер бланка */
    private java.lang.String serialNum;

    public BlankInfo_Type() {
    }

    public BlankInfo_Type(
           java.lang.String blankType,
           java.lang.String serialNum) {
           this.blankType = blankType;
           this.serialNum = serialNum;
    }


    /**
     * Gets the blankType value for this BlankInfo_Type.
     * 
     * @return blankType   * Тип бланка
     */
    public java.lang.String getBlankType() {
        return blankType;
    }


    /**
     * Sets the blankType value for this BlankInfo_Type.
     * 
     * @param blankType   * Тип бланка
     */
    public void setBlankType(java.lang.String blankType) {
        this.blankType = blankType;
    }


    /**
     * Gets the serialNum value for this BlankInfo_Type.
     * 
     * @return serialNum   * Уникальный номер бланка
     */
    public java.lang.String getSerialNum() {
        return serialNum;
    }


    /**
     * Sets the serialNum value for this BlankInfo_Type.
     * 
     * @param serialNum   * Уникальный номер бланка
     */
    public void setSerialNum(java.lang.String serialNum) {
        this.serialNum = serialNum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BlankInfo_Type)) return false;
        BlankInfo_Type other = (BlankInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.blankType==null && other.getBlankType()==null) || 
             (this.blankType!=null &&
              this.blankType.equals(other.getBlankType()))) &&
            ((this.serialNum==null && other.getSerialNum()==null) || 
             (this.serialNum!=null &&
              this.serialNum.equals(other.getSerialNum())));
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
        if (getBlankType() != null) {
            _hashCode += getBlankType().hashCode();
        }
        if (getSerialNum() != null) {
            _hashCode += getSerialNum().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BlankInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blankType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SerialNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
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
