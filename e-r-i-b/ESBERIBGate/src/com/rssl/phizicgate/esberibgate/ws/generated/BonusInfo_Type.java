/**
 * BonusInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Информация об участии в бонусной программе
 */
public class BonusInfo_Type  implements java.io.Serializable {
    /* Код бонусной программы */
    private java.lang.String bonusCode;

    /* Номер участника программы для кобрэндинговых карт «Аэрофлот
     * Бонус», «МТС Бонус», «Подари Жизнь», «Золотая Маска» */
    private java.lang.String fieldValue;

    public BonusInfo_Type() {
    }

    public BonusInfo_Type(
           java.lang.String bonusCode,
           java.lang.String fieldValue) {
           this.bonusCode = bonusCode;
           this.fieldValue = fieldValue;
    }


    /**
     * Gets the bonusCode value for this BonusInfo_Type.
     * 
     * @return bonusCode   * Код бонусной программы
     */
    public java.lang.String getBonusCode() {
        return bonusCode;
    }


    /**
     * Sets the bonusCode value for this BonusInfo_Type.
     * 
     * @param bonusCode   * Код бонусной программы
     */
    public void setBonusCode(java.lang.String bonusCode) {
        this.bonusCode = bonusCode;
    }


    /**
     * Gets the fieldValue value for this BonusInfo_Type.
     * 
     * @return fieldValue   * Номер участника программы для кобрэндинговых карт «Аэрофлот
     * Бонус», «МТС Бонус», «Подари Жизнь», «Золотая Маска»
     */
    public java.lang.String getFieldValue() {
        return fieldValue;
    }


    /**
     * Sets the fieldValue value for this BonusInfo_Type.
     * 
     * @param fieldValue   * Номер участника программы для кобрэндинговых карт «Аэрофлот
     * Бонус», «МТС Бонус», «Подари Жизнь», «Золотая Маска»
     */
    public void setFieldValue(java.lang.String fieldValue) {
        this.fieldValue = fieldValue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BonusInfo_Type)) return false;
        BonusInfo_Type other = (BonusInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bonusCode==null && other.getBonusCode()==null) || 
             (this.bonusCode!=null &&
              this.bonusCode.equals(other.getBonusCode()))) &&
            ((this.fieldValue==null && other.getFieldValue()==null) || 
             (this.fieldValue!=null &&
              this.fieldValue.equals(other.getFieldValue())));
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
        if (getBonusCode() != null) {
            _hashCode += getBonusCode().hashCode();
        }
        if (getFieldValue() != null) {
            _hashCode += getFieldValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BonusInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldValue"));
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
