/**
 * AlterMinRemainder_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Элементы для изменения неснижаемого остатка
 */
public class AlterMinRemainder_Type  implements java.io.Serializable {
    /* Новый неснижаемый остаток счета */
    private java.math.BigDecimal newMinRemainder;

    /* Новая процентная ставка по вкладу */
    private java.math.BigDecimal newInterestRate;

    public AlterMinRemainder_Type() {
    }

    public AlterMinRemainder_Type(
           java.math.BigDecimal newMinRemainder,
           java.math.BigDecimal newInterestRate) {
           this.newMinRemainder = newMinRemainder;
           this.newInterestRate = newInterestRate;
    }


    /**
     * Gets the newMinRemainder value for this AlterMinRemainder_Type.
     * 
     * @return newMinRemainder   * Новый неснижаемый остаток счета
     */
    public java.math.BigDecimal getNewMinRemainder() {
        return newMinRemainder;
    }


    /**
     * Sets the newMinRemainder value for this AlterMinRemainder_Type.
     * 
     * @param newMinRemainder   * Новый неснижаемый остаток счета
     */
    public void setNewMinRemainder(java.math.BigDecimal newMinRemainder) {
        this.newMinRemainder = newMinRemainder;
    }


    /**
     * Gets the newInterestRate value for this AlterMinRemainder_Type.
     * 
     * @return newInterestRate   * Новая процентная ставка по вкладу
     */
    public java.math.BigDecimal getNewInterestRate() {
        return newInterestRate;
    }


    /**
     * Sets the newInterestRate value for this AlterMinRemainder_Type.
     * 
     * @param newInterestRate   * Новая процентная ставка по вкладу
     */
    public void setNewInterestRate(java.math.BigDecimal newInterestRate) {
        this.newInterestRate = newInterestRate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AlterMinRemainder_Type)) return false;
        AlterMinRemainder_Type other = (AlterMinRemainder_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.newMinRemainder==null && other.getNewMinRemainder()==null) || 
             (this.newMinRemainder!=null &&
              this.newMinRemainder.equals(other.getNewMinRemainder()))) &&
            ((this.newInterestRate==null && other.getNewInterestRate()==null) || 
             (this.newInterestRate!=null &&
              this.newInterestRate.equals(other.getNewInterestRate())));
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
        if (getNewMinRemainder() != null) {
            _hashCode += getNewMinRemainder().hashCode();
        }
        if (getNewInterestRate() != null) {
            _hashCode += getNewInterestRate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AlterMinRemainder_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AlterMinRemainder_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newMinRemainder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NewMinRemainder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newInterestRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NewInterestRate"));
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
