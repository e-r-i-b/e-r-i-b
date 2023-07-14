/**
 * BlankPackage_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Пакет бланков ценных бумаг
 */
public class BlankPackage_Type  implements java.io.Serializable {
    /* Тип бланка */
    private java.lang.String blankType;

    /* Уникальный номер первого бланка в пакете */
    private java.lang.String serialNum;

    /* Уникальный номер последнего бланка в пакете */
    private java.lang.String serialNumLast;

    public BlankPackage_Type() {
    }

    public BlankPackage_Type(
           java.lang.String blankType,
           java.lang.String serialNum,
           java.lang.String serialNumLast) {
           this.blankType = blankType;
           this.serialNum = serialNum;
           this.serialNumLast = serialNumLast;
    }


    /**
     * Gets the blankType value for this BlankPackage_Type.
     * 
     * @return blankType   * Тип бланка
     */
    public java.lang.String getBlankType() {
        return blankType;
    }


    /**
     * Sets the blankType value for this BlankPackage_Type.
     * 
     * @param blankType   * Тип бланка
     */
    public void setBlankType(java.lang.String blankType) {
        this.blankType = blankType;
    }


    /**
     * Gets the serialNum value for this BlankPackage_Type.
     * 
     * @return serialNum   * Уникальный номер первого бланка в пакете
     */
    public java.lang.String getSerialNum() {
        return serialNum;
    }


    /**
     * Sets the serialNum value for this BlankPackage_Type.
     * 
     * @param serialNum   * Уникальный номер первого бланка в пакете
     */
    public void setSerialNum(java.lang.String serialNum) {
        this.serialNum = serialNum;
    }


    /**
     * Gets the serialNumLast value for this BlankPackage_Type.
     * 
     * @return serialNumLast   * Уникальный номер последнего бланка в пакете
     */
    public java.lang.String getSerialNumLast() {
        return serialNumLast;
    }


    /**
     * Sets the serialNumLast value for this BlankPackage_Type.
     * 
     * @param serialNumLast   * Уникальный номер последнего бланка в пакете
     */
    public void setSerialNumLast(java.lang.String serialNumLast) {
        this.serialNumLast = serialNumLast;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BlankPackage_Type)) return false;
        BlankPackage_Type other = (BlankPackage_Type) obj;
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
              this.serialNum.equals(other.getSerialNum()))) &&
            ((this.serialNumLast==null && other.getSerialNumLast()==null) || 
             (this.serialNumLast!=null &&
              this.serialNumLast.equals(other.getSerialNumLast())));
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
        if (getSerialNumLast() != null) {
            _hashCode += getSerialNumLast().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BlankPackage_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankPackage_Type"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNumLast");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SerialNumLast"));
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
