/**
 * DeptId_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Идентификатор долга.
 */
public class DeptId_Type  implements java.io.Serializable {
    /* Номер выставленного счета */
    private java.lang.String recNumber;

    /* Дата выставления счета */
    private java.lang.String effDt;

    public DeptId_Type() {
    }

    public DeptId_Type(
           java.lang.String recNumber,
           java.lang.String effDt) {
           this.recNumber = recNumber;
           this.effDt = effDt;
    }


    /**
     * Gets the recNumber value for this DeptId_Type.
     * 
     * @return recNumber   * Номер выставленного счета
     */
    public java.lang.String getRecNumber() {
        return recNumber;
    }


    /**
     * Sets the recNumber value for this DeptId_Type.
     * 
     * @param recNumber   * Номер выставленного счета
     */
    public void setRecNumber(java.lang.String recNumber) {
        this.recNumber = recNumber;
    }


    /**
     * Gets the effDt value for this DeptId_Type.
     * 
     * @return effDt   * Дата выставления счета
     */
    public java.lang.String getEffDt() {
        return effDt;
    }


    /**
     * Sets the effDt value for this DeptId_Type.
     * 
     * @param effDt   * Дата выставления счета
     */
    public void setEffDt(java.lang.String effDt) {
        this.effDt = effDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeptId_Type)) return false;
        DeptId_Type other = (DeptId_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recNumber==null && other.getRecNumber()==null) || 
             (this.recNumber!=null &&
              this.recNumber.equals(other.getRecNumber()))) &&
            ((this.effDt==null && other.getEffDt()==null) || 
             (this.effDt!=null &&
              this.effDt.equals(other.getEffDt())));
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
        if (getRecNumber() != null) {
            _hashCode += getRecNumber().hashCode();
        }
        if (getEffDt() != null) {
            _hashCode += getEffDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeptId_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeptId_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EffDt"));
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
