/**
 * IMSBalance_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип баланса по ОМС
 */
public class IMSBalance_Type  implements java.io.Serializable {
    /* Остаток в граммах */
    private java.math.BigDecimal amt;

    /* Остаток в рублях */
    private java.math.BigDecimal amtRub;

    public IMSBalance_Type() {
    }

    public IMSBalance_Type(
           java.math.BigDecimal amt,
           java.math.BigDecimal amtRub) {
           this.amt = amt;
           this.amtRub = amtRub;
    }


    /**
     * Gets the amt value for this IMSBalance_Type.
     * 
     * @return amt   * Остаток в граммах
     */
    public java.math.BigDecimal getAmt() {
        return amt;
    }


    /**
     * Sets the amt value for this IMSBalance_Type.
     * 
     * @param amt   * Остаток в граммах
     */
    public void setAmt(java.math.BigDecimal amt) {
        this.amt = amt;
    }


    /**
     * Gets the amtRub value for this IMSBalance_Type.
     * 
     * @return amtRub   * Остаток в рублях
     */
    public java.math.BigDecimal getAmtRub() {
        return amtRub;
    }


    /**
     * Sets the amtRub value for this IMSBalance_Type.
     * 
     * @param amtRub   * Остаток в рублях
     */
    public void setAmtRub(java.math.BigDecimal amtRub) {
        this.amtRub = amtRub;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IMSBalance_Type)) return false;
        IMSBalance_Type other = (IMSBalance_Type) obj;
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
            ((this.amtRub==null && other.getAmtRub()==null) || 
             (this.amtRub!=null &&
              this.amtRub.equals(other.getAmtRub())));
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
        if (getAmtRub() != null) {
            _hashCode += getAmtRub().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IMSBalance_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSBalance_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amtRub");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AmtRub"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
