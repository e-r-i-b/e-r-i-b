/**
 * PaymentTemp_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о шаблоне платежа
 */
public class PaymentTemp_Type  implements java.io.Serializable {
    /* Идентификатор организации получателя */
    private java.lang.String recipientID;

    /* Наименование организации получателя */
    private java.lang.String recipientName;

    /* Идентификатор платежа */
    private java.lang.String paymentsID;

    public PaymentTemp_Type() {
    }

    public PaymentTemp_Type(
           java.lang.String recipientID,
           java.lang.String recipientName,
           java.lang.String paymentsID) {
           this.recipientID = recipientID;
           this.recipientName = recipientName;
           this.paymentsID = paymentsID;
    }


    /**
     * Gets the recipientID value for this PaymentTemp_Type.
     * 
     * @return recipientID   * Идентификатор организации получателя
     */
    public java.lang.String getRecipientID() {
        return recipientID;
    }


    /**
     * Sets the recipientID value for this PaymentTemp_Type.
     * 
     * @param recipientID   * Идентификатор организации получателя
     */
    public void setRecipientID(java.lang.String recipientID) {
        this.recipientID = recipientID;
    }


    /**
     * Gets the recipientName value for this PaymentTemp_Type.
     * 
     * @return recipientName   * Наименование организации получателя
     */
    public java.lang.String getRecipientName() {
        return recipientName;
    }


    /**
     * Sets the recipientName value for this PaymentTemp_Type.
     * 
     * @param recipientName   * Наименование организации получателя
     */
    public void setRecipientName(java.lang.String recipientName) {
        this.recipientName = recipientName;
    }


    /**
     * Gets the paymentsID value for this PaymentTemp_Type.
     * 
     * @return paymentsID   * Идентификатор платежа
     */
    public java.lang.String getPaymentsID() {
        return paymentsID;
    }


    /**
     * Sets the paymentsID value for this PaymentTemp_Type.
     * 
     * @param paymentsID   * Идентификатор платежа
     */
    public void setPaymentsID(java.lang.String paymentsID) {
        this.paymentsID = paymentsID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentTemp_Type)) return false;
        PaymentTemp_Type other = (PaymentTemp_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recipientID==null && other.getRecipientID()==null) || 
             (this.recipientID!=null &&
              this.recipientID.equals(other.getRecipientID()))) &&
            ((this.recipientName==null && other.getRecipientName()==null) || 
             (this.recipientName!=null &&
              this.recipientName.equals(other.getRecipientName()))) &&
            ((this.paymentsID==null && other.getPaymentsID()==null) || 
             (this.paymentsID!=null &&
              this.paymentsID.equals(other.getPaymentsID())));
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
        if (getRecipientID() != null) {
            _hashCode += getRecipientID().hashCode();
        }
        if (getRecipientName() != null) {
            _hashCode += getRecipientName().hashCode();
        }
        if (getPaymentsID() != null) {
            _hashCode += getPaymentsID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentTemp_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentTemp_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentsID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentsID"));
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
