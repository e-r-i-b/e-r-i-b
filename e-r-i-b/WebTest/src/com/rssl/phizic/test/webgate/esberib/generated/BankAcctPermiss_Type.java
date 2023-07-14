/**
 * BankAcctPermiss_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип "Права доступа к счету или карте"
 */
public class BankAcctPermiss_Type  implements java.io.Serializable {
    /* Тип доступа (сейчас только на просмотр - значение View) */
    private com.rssl.phizic.test.webgate.esberib.generated.PermissType_Type permissType;

    /* Признак отсутствия указанного типа доступа (false - доступ
     * есть) */
    private boolean permissValue;

    /* Код системы, для которой проставляется право. Используется
     * два значения BP_ES (для УС),BP_ERIB (для СББОЛ) */
    private com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName;

    public BankAcctPermiss_Type() {
    }

    public BankAcctPermiss_Type(
           com.rssl.phizic.test.webgate.esberib.generated.PermissType_Type permissType,
           boolean permissValue,
           com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
           this.permissType = permissType;
           this.permissValue = permissValue;
           this.SPName = SPName;
    }


    /**
     * Gets the permissType value for this BankAcctPermiss_Type.
     * 
     * @return permissType   * Тип доступа (сейчас только на просмотр - значение View)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.PermissType_Type getPermissType() {
        return permissType;
    }


    /**
     * Sets the permissType value for this BankAcctPermiss_Type.
     * 
     * @param permissType   * Тип доступа (сейчас только на просмотр - значение View)
     */
    public void setPermissType(com.rssl.phizic.test.webgate.esberib.generated.PermissType_Type permissType) {
        this.permissType = permissType;
    }


    /**
     * Gets the permissValue value for this BankAcctPermiss_Type.
     * 
     * @return permissValue   * Признак отсутствия указанного типа доступа (false - доступ
     * есть)
     */
    public boolean isPermissValue() {
        return permissValue;
    }


    /**
     * Sets the permissValue value for this BankAcctPermiss_Type.
     * 
     * @param permissValue   * Признак отсутствия указанного типа доступа (false - доступ
     * есть)
     */
    public void setPermissValue(boolean permissValue) {
        this.permissValue = permissValue;
    }


    /**
     * Gets the SPName value for this BankAcctPermiss_Type.
     * 
     * @return SPName   * Код системы, для которой проставляется право. Используется
     * два значения BP_ES (для УС),BP_ERIB (для СББОЛ)
     */
    public com.rssl.phizic.test.webgate.esberib.generated.SPName_Type getSPName() {
        return SPName;
    }


    /**
     * Sets the SPName value for this BankAcctPermiss_Type.
     * 
     * @param SPName   * Код системы, для которой проставляется право. Используется
     * два значения BP_ES (для УС),BP_ERIB (для СББОЛ)
     */
    public void setSPName(com.rssl.phizic.test.webgate.esberib.generated.SPName_Type SPName) {
        this.SPName = SPName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankAcctPermiss_Type)) return false;
        BankAcctPermiss_Type other = (BankAcctPermiss_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.permissType==null && other.getPermissType()==null) || 
             (this.permissType!=null &&
              this.permissType.equals(other.getPermissType()))) &&
            this.permissValue == other.isPermissValue() &&
            ((this.SPName==null && other.getSPName()==null) || 
             (this.SPName!=null &&
              this.SPName.equals(other.getSPName())));
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
        if (getPermissType() != null) {
            _hashCode += getPermissType().hashCode();
        }
        _hashCode += (isPermissValue() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSPName() != null) {
            _hashCode += getSPName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankAcctPermiss_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctPermiss_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permissType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PermissType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PermissType_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permissValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PermissValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type"));
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
