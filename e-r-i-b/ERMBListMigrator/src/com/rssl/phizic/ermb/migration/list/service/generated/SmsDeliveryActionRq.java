/**
 * SmsDeliveryActionRq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ermb.migration.list.service.generated;

public class SmsDeliveryActionRq  implements java.io.Serializable {
    private java.lang.String[] sendsSegment;

    private java.lang.String[] notSendsSegment;

    private java.lang.String smsText;

    public SmsDeliveryActionRq() {
    }

    public SmsDeliveryActionRq(
           java.lang.String[] sendsSegment,
           java.lang.String[] notSendsSegment,
           java.lang.String smsText) {
           this.sendsSegment = sendsSegment;
           this.notSendsSegment = notSendsSegment;
           this.smsText = smsText;
    }


    /**
     * Gets the sendsSegment value for this SmsDeliveryActionRq.
     * 
     * @return sendsSegment
     */
    public java.lang.String[] getSendsSegment() {
        return sendsSegment;
    }


    /**
     * Sets the sendsSegment value for this SmsDeliveryActionRq.
     * 
     * @param sendsSegment
     */
    public void setSendsSegment(java.lang.String[] sendsSegment) {
        this.sendsSegment = sendsSegment;
    }


    /**
     * Gets the notSendsSegment value for this SmsDeliveryActionRq.
     * 
     * @return notSendsSegment
     */
    public java.lang.String[] getNotSendsSegment() {
        return notSendsSegment;
    }


    /**
     * Sets the notSendsSegment value for this SmsDeliveryActionRq.
     * 
     * @param notSendsSegment
     */
    public void setNotSendsSegment(java.lang.String[] notSendsSegment) {
        this.notSendsSegment = notSendsSegment;
    }


    /**
     * Gets the smsText value for this SmsDeliveryActionRq.
     * 
     * @return smsText
     */
    public java.lang.String getSmsText() {
        return smsText;
    }


    /**
     * Sets the smsText value for this SmsDeliveryActionRq.
     * 
     * @param smsText
     */
    public void setSmsText(java.lang.String smsText) {
        this.smsText = smsText;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SmsDeliveryActionRq)) return false;
        SmsDeliveryActionRq other = (SmsDeliveryActionRq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sendsSegment==null && other.getSendsSegment()==null) || 
             (this.sendsSegment!=null &&
              java.util.Arrays.equals(this.sendsSegment, other.getSendsSegment()))) &&
            ((this.notSendsSegment==null && other.getNotSendsSegment()==null) || 
             (this.notSendsSegment!=null &&
              java.util.Arrays.equals(this.notSendsSegment, other.getNotSendsSegment()))) &&
            ((this.smsText==null && other.getSmsText()==null) || 
             (this.smsText!=null &&
              this.smsText.equals(other.getSmsText())));
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
        if (getSendsSegment() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSendsSegment());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSendsSegment(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNotSendsSegment() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotSendsSegment());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotSendsSegment(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSmsText() != null) {
            _hashCode += getSmsText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SmsDeliveryActionRq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.task.sbrf.ru", ">SmsDeliveryActionRq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendsSegment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://service.task.sbrf.ru", "sendsSegment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://service.task.sbrf.ru", "segment1"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notSendsSegment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://service.task.sbrf.ru", "notSendsSegment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://service.task.sbrf.ru", "segment2"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smsText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://service.task.sbrf.ru", "smsText"));
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
