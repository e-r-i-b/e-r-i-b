/**
 * RegExp_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Регулярное выражение
 */
public class RegExp_Type  implements java.io.Serializable {
    /* Регулярное выражение, для проверки введенных данных */
    private java.lang.String exp;

    /* Сообщение для отображения пользавателю, в случае не выполнения
     * рег. Выражения. */
    private java.lang.String mess;

    public RegExp_Type() {
    }

    public RegExp_Type(
           java.lang.String exp,
           java.lang.String mess) {
           this.exp = exp;
           this.mess = mess;
    }


    /**
     * Gets the exp value for this RegExp_Type.
     * 
     * @return exp   * Регулярное выражение, для проверки введенных данных
     */
    public java.lang.String getExp() {
        return exp;
    }


    /**
     * Sets the exp value for this RegExp_Type.
     * 
     * @param exp   * Регулярное выражение, для проверки введенных данных
     */
    public void setExp(java.lang.String exp) {
        this.exp = exp;
    }


    /**
     * Gets the mess value for this RegExp_Type.
     * 
     * @return mess   * Сообщение для отображения пользавателю, в случае не выполнения
     * рег. Выражения.
     */
    public java.lang.String getMess() {
        return mess;
    }


    /**
     * Sets the mess value for this RegExp_Type.
     * 
     * @param mess   * Сообщение для отображения пользавателю, в случае не выполнения
     * рег. Выражения.
     */
    public void setMess(java.lang.String mess) {
        this.mess = mess;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegExp_Type)) return false;
        RegExp_Type other = (RegExp_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.exp==null && other.getExp()==null) || 
             (this.exp!=null &&
              this.exp.equals(other.getExp()))) &&
            ((this.mess==null && other.getMess()==null) || 
             (this.mess!=null &&
              this.mess.equals(other.getMess())));
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
        if (getExp() != null) {
            _hashCode += getExp().hashCode();
        }
        if (getMess() != null) {
            _hashCode += getMess().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegExp_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegExp_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Exp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Mess"));
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
