/**
 * PolicyDetails_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Описание реквизитов договора страхования: Серия, номер и дата выдачи
 * полиса
 */
public class PolicyDetails_Type  implements java.io.Serializable {
    /* Серия */
    private java.lang.String series;

    /* Номер */
    private java.lang.String num;

    /* Дата */
    private java.lang.String issueDt;

    public PolicyDetails_Type() {
    }

    public PolicyDetails_Type(
           java.lang.String series,
           java.lang.String num,
           java.lang.String issueDt) {
           this.series = series;
           this.num = num;
           this.issueDt = issueDt;
    }


    /**
     * Gets the series value for this PolicyDetails_Type.
     * 
     * @return series   * Серия
     */
    public java.lang.String getSeries() {
        return series;
    }


    /**
     * Sets the series value for this PolicyDetails_Type.
     * 
     * @param series   * Серия
     */
    public void setSeries(java.lang.String series) {
        this.series = series;
    }


    /**
     * Gets the num value for this PolicyDetails_Type.
     * 
     * @return num   * Номер
     */
    public java.lang.String getNum() {
        return num;
    }


    /**
     * Sets the num value for this PolicyDetails_Type.
     * 
     * @param num   * Номер
     */
    public void setNum(java.lang.String num) {
        this.num = num;
    }


    /**
     * Gets the issueDt value for this PolicyDetails_Type.
     * 
     * @return issueDt   * Дата
     */
    public java.lang.String getIssueDt() {
        return issueDt;
    }


    /**
     * Sets the issueDt value for this PolicyDetails_Type.
     * 
     * @param issueDt   * Дата
     */
    public void setIssueDt(java.lang.String issueDt) {
        this.issueDt = issueDt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PolicyDetails_Type)) return false;
        PolicyDetails_Type other = (PolicyDetails_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.series==null && other.getSeries()==null) || 
             (this.series!=null &&
              this.series.equals(other.getSeries()))) &&
            ((this.num==null && other.getNum()==null) || 
             (this.num!=null &&
              this.num.equals(other.getNum()))) &&
            ((this.issueDt==null && other.getIssueDt()==null) || 
             (this.issueDt!=null &&
              this.issueDt.equals(other.getIssueDt())));
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
        if (getSeries() != null) {
            _hashCode += getSeries().hashCode();
        }
        if (getNum() != null) {
            _hashCode += getNum().hashCode();
        }
        if (getIssueDt() != null) {
            _hashCode += getIssueDt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PolicyDetails_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PolicyDetails_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("series");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Series"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Num"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issueDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IssueDt"));
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
