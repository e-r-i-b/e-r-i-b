/**
 * VariantInterestPayment_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Элементы для передачи информации о порядке уплаты процентов
 */
public class VariantInterestPayment_Type  implements java.io.Serializable {
    /* Вариант уплаты процентов */
    private java.lang.String isInterestToCard;

    /* Номер банковской карты */
    private java.lang.String cardNumber;

    public VariantInterestPayment_Type() {
    }

    public VariantInterestPayment_Type(
           java.lang.String isInterestToCard,
           java.lang.String cardNumber) {
           this.isInterestToCard = isInterestToCard;
           this.cardNumber = cardNumber;
    }


    /**
     * Gets the isInterestToCard value for this VariantInterestPayment_Type.
     * 
     * @return isInterestToCard   * Вариант уплаты процентов
     */
    public java.lang.String getIsInterestToCard() {
        return isInterestToCard;
    }


    /**
     * Sets the isInterestToCard value for this VariantInterestPayment_Type.
     * 
     * @param isInterestToCard   * Вариант уплаты процентов
     */
    public void setIsInterestToCard(java.lang.String isInterestToCard) {
        this.isInterestToCard = isInterestToCard;
    }


    /**
     * Gets the cardNumber value for this VariantInterestPayment_Type.
     * 
     * @return cardNumber   * Номер банковской карты
     */
    public java.lang.String getCardNumber() {
        return cardNumber;
    }


    /**
     * Sets the cardNumber value for this VariantInterestPayment_Type.
     * 
     * @param cardNumber   * Номер банковской карты
     */
    public void setCardNumber(java.lang.String cardNumber) {
        this.cardNumber = cardNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VariantInterestPayment_Type)) return false;
        VariantInterestPayment_Type other = (VariantInterestPayment_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.isInterestToCard==null && other.getIsInterestToCard()==null) || 
             (this.isInterestToCard!=null &&
              this.isInterestToCard.equals(other.getIsInterestToCard()))) &&
            ((this.cardNumber==null && other.getCardNumber()==null) || 
             (this.cardNumber!=null &&
              this.cardNumber.equals(other.getCardNumber())));
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
        if (getIsInterestToCard() != null) {
            _hashCode += getIsInterestToCard().hashCode();
        }
        if (getCardNumber() != null) {
            _hashCode += getCardNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VariantInterestPayment_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VariantInterestPayment_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isInterestToCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IsInterestToCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
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
