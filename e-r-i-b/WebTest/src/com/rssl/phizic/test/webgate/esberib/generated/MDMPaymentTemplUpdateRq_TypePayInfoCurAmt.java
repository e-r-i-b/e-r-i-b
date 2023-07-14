/**
 * MDMPaymentTemplUpdateRq_TypePayInfoCurAmt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class MDMPaymentTemplUpdateRq_TypePayInfoCurAmt  implements java.io.Serializable {
    /* Сумма */
    private java.math.BigDecimal amt;

    /* Валюта */
    private java.lang.String amtCur;

    public MDMPaymentTemplUpdateRq_TypePayInfoCurAmt() {
    }

    public MDMPaymentTemplUpdateRq_TypePayInfoCurAmt(
           java.math.BigDecimal amt,
           java.lang.String amtCur) {
           this.amt = amt;
           this.amtCur = amtCur;
    }


    /**
     * Gets the amt value for this MDMPaymentTemplUpdateRq_TypePayInfoCurAmt.
     * 
     * @return amt   * Сумма
     */
    public java.math.BigDecimal getAmt() {
        return amt;
    }


    /**
     * Sets the amt value for this MDMPaymentTemplUpdateRq_TypePayInfoCurAmt.
     * 
     * @param amt   * Сумма
     */
    public void setAmt(java.math.BigDecimal amt) {
        this.amt = amt;
    }


    /**
     * Gets the amtCur value for this MDMPaymentTemplUpdateRq_TypePayInfoCurAmt.
     * 
     * @return amtCur   * Валюта
     */
    public java.lang.String getAmtCur() {
        return amtCur;
    }


    /**
     * Sets the amtCur value for this MDMPaymentTemplUpdateRq_TypePayInfoCurAmt.
     * 
     * @param amtCur   * Валюта
     */
    public void setAmtCur(java.lang.String amtCur) {
        this.amtCur = amtCur;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MDMPaymentTemplUpdateRq_TypePayInfoCurAmt)) return false;
        MDMPaymentTemplUpdateRq_TypePayInfoCurAmt other = (MDMPaymentTemplUpdateRq_TypePayInfoCurAmt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.amt==null && other.getAmt()==null) || 
             (this.amt!=null &&
              this.amt.equals(other.getAmt()))) &&
            ((this.amtCur==null && other.getAmtCur()==null) || 
             (this.amtCur!=null &&
              this.amtCur.equals(other.getAmtCur())));
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
        if (getAmt() != null) {
            _hashCode += getAmt().hashCode();
        }
        if (getAmtCur() != null) {
            _hashCode += getAmtCur().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MDMPaymentTemplUpdateRq_TypePayInfoCurAmt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>MDMPaymentTemplUpdateRq_Type>PayInfo>CurAmt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amtCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AmtCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
