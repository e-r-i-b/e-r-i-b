/**
 * SendPaymentSmsRq_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.ermb.messaging.paymentSms.generated;


/**
 * Сообщение об успешном выполнении платежа
 */
public class SendPaymentSmsRq_Type  implements java.io.Serializable {
    private java.lang.String rqUID;

    private java.util.Calendar rqTime;

    private java.lang.String mbOperCode;

    private java.lang.String text;

    public SendPaymentSmsRq_Type() {
    }

    public SendPaymentSmsRq_Type(
           java.lang.String rqUID,
           java.util.Calendar rqTime,
           java.lang.String mbOperCode,
           java.lang.String text) {
           this.rqUID = rqUID;
           this.rqTime = rqTime;
           this.mbOperCode = mbOperCode;
           this.text = text;
    }


    /**
     * Gets the rqUID value for this SendPaymentSmsRq_Type.
     * 
     * @return rqUID
     */
    public java.lang.String getRqUID() {
        return rqUID;
    }


    /**
     * Sets the rqUID value for this SendPaymentSmsRq_Type.
     * 
     * @param rqUID
     */
    public void setRqUID(java.lang.String rqUID) {
        this.rqUID = rqUID;
    }


    /**
     * Gets the rqTime value for this SendPaymentSmsRq_Type.
     * 
     * @return rqTime
     */
    public java.util.Calendar getRqTime() {
        return rqTime;
    }


    /**
     * Sets the rqTime value for this SendPaymentSmsRq_Type.
     * 
     * @param rqTime
     */
    public void setRqTime(java.util.Calendar rqTime) {
        this.rqTime = rqTime;
    }


    /**
     * Gets the mbOperCode value for this SendPaymentSmsRq_Type.
     * 
     * @return mbOperCode
     */
    public java.lang.String getMbOperCode() {
        return mbOperCode;
    }


    /**
     * Sets the mbOperCode value for this SendPaymentSmsRq_Type.
     * 
     * @param mbOperCode
     */
    public void setMbOperCode(java.lang.String mbOperCode) {
        this.mbOperCode = mbOperCode;
    }


    /**
     * Gets the text value for this SendPaymentSmsRq_Type.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this SendPaymentSmsRq_Type.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SendPaymentSmsRq_Type)) return false;
        SendPaymentSmsRq_Type other = (SendPaymentSmsRq_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqUID==null && other.getRqUID()==null) || 
             (this.rqUID!=null &&
              this.rqUID.equals(other.getRqUID()))) &&
            ((this.rqTime==null && other.getRqTime()==null) || 
             (this.rqTime!=null &&
              this.rqTime.equals(other.getRqTime()))) &&
            ((this.mbOperCode==null && other.getMbOperCode()==null) || 
             (this.mbOperCode!=null &&
              this.mbOperCode.equals(other.getMbOperCode()))) &&
            ((this.text==null && other.getText()==null) || 
             (this.text!=null &&
              this.text.equals(other.getText())));
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
        if (getRqUID() != null) {
            _hashCode += getRqUID().hashCode();
        }
        if (getRqTime() != null) {
            _hashCode += getRqTime().hashCode();
        }
        if (getMbOperCode() != null) {
            _hashCode += getMbOperCode().hashCode();
        }
        if (getText() != null) {
            _hashCode += getText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SendPaymentSmsRq_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "SendPaymentSmsRq_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "rqUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "rqTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mbOperCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "mbOperCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("text");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/messaging/payment-sms", "text"));
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
