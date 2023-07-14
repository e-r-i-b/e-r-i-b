/**
 * MessageIn_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.ermb.smslog.generated;


/**
 * Информация о входящем сообщении Приходит, если type=in или both
 */
public class MessageIn_Type  implements java.io.Serializable {
    private java.lang.String rqID;

    private java.lang.String phone;

    private java.lang.String text;

    private java.util.Calendar receiveTime;

    public MessageIn_Type() {
    }

    public MessageIn_Type(
           java.lang.String rqID,
           java.lang.String phone,
           java.lang.String text,
           java.util.Calendar receiveTime) {
           this.rqID = rqID;
           this.phone = phone;
           this.text = text;
           this.receiveTime = receiveTime;
    }


    /**
     * Gets the rqID value for this MessageIn_Type.
     * 
     * @return rqID
     */
    public java.lang.String getRqID() {
        return rqID;
    }


    /**
     * Sets the rqID value for this MessageIn_Type.
     * 
     * @param rqID
     */
    public void setRqID(java.lang.String rqID) {
        this.rqID = rqID;
    }


    /**
     * Gets the phone value for this MessageIn_Type.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this MessageIn_Type.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the text value for this MessageIn_Type.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this MessageIn_Type.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }


    /**
     * Gets the receiveTime value for this MessageIn_Type.
     * 
     * @return receiveTime
     */
    public java.util.Calendar getReceiveTime() {
        return receiveTime;
    }


    /**
     * Sets the receiveTime value for this MessageIn_Type.
     * 
     * @param receiveTime
     */
    public void setReceiveTime(java.util.Calendar receiveTime) {
        this.receiveTime = receiveTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessageIn_Type)) return false;
        MessageIn_Type other = (MessageIn_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rqID==null && other.getRqID()==null) || 
             (this.rqID!=null &&
              this.rqID.equals(other.getRqID()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.text==null && other.getText()==null) || 
             (this.text!=null &&
              this.text.equals(other.getText()))) &&
            ((this.receiveTime==null && other.getReceiveTime()==null) || 
             (this.receiveTime!=null &&
              this.receiveTime.equals(other.getReceiveTime())));
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
        if (getRqID() != null) {
            _hashCode += getRqID().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getText() != null) {
            _hashCode += getText().hashCode();
        }
        if (getReceiveTime() != null) {
            _hashCode += getReceiveTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessageIn_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "messageIn_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rqID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "rqID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("text");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "text"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiveTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "receiveTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
