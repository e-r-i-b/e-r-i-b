/**
 * MessageOut_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.ermb.smslog.generated;


/**
 * Информация об исходящем сообщении Приходит, если type=out или both
 */
public class MessageOut_Type  implements java.io.Serializable {
    private java.lang.String rqID;

    private java.lang.String phone;

    private java.lang.String text;

    private java.util.Calendar createTime;

    private java.util.Calendar sendTime;

    private java.lang.String sendStatus;

    private java.math.BigInteger smsCount;

    private java.util.Calendar deliverTime;

    private java.lang.String deliverStatus;

    private com.rssl.phizic.test.ermb.smslog.generated.ResourceId_Type resource;

    public MessageOut_Type() {
    }

    public MessageOut_Type(
           java.lang.String rqID,
           java.lang.String phone,
           java.lang.String text,
           java.util.Calendar createTime,
           java.util.Calendar sendTime,
           java.lang.String sendStatus,
           java.math.BigInteger smsCount,
           java.util.Calendar deliverTime,
           java.lang.String deliverStatus,
           com.rssl.phizic.test.ermb.smslog.generated.ResourceId_Type resource) {
           this.rqID = rqID;
           this.phone = phone;
           this.text = text;
           this.createTime = createTime;
           this.sendTime = sendTime;
           this.sendStatus = sendStatus;
           this.smsCount = smsCount;
           this.deliverTime = deliverTime;
           this.deliverStatus = deliverStatus;
           this.resource = resource;
    }


    /**
     * Gets the rqID value for this MessageOut_Type.
     * 
     * @return rqID
     */
    public java.lang.String getRqID() {
        return rqID;
    }


    /**
     * Sets the rqID value for this MessageOut_Type.
     * 
     * @param rqID
     */
    public void setRqID(java.lang.String rqID) {
        this.rqID = rqID;
    }


    /**
     * Gets the phone value for this MessageOut_Type.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this MessageOut_Type.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the text value for this MessageOut_Type.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this MessageOut_Type.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }


    /**
     * Gets the createTime value for this MessageOut_Type.
     * 
     * @return createTime
     */
    public java.util.Calendar getCreateTime() {
        return createTime;
    }


    /**
     * Sets the createTime value for this MessageOut_Type.
     * 
     * @param createTime
     */
    public void setCreateTime(java.util.Calendar createTime) {
        this.createTime = createTime;
    }


    /**
     * Gets the sendTime value for this MessageOut_Type.
     * 
     * @return sendTime
     */
    public java.util.Calendar getSendTime() {
        return sendTime;
    }


    /**
     * Sets the sendTime value for this MessageOut_Type.
     * 
     * @param sendTime
     */
    public void setSendTime(java.util.Calendar sendTime) {
        this.sendTime = sendTime;
    }


    /**
     * Gets the sendStatus value for this MessageOut_Type.
     * 
     * @return sendStatus
     */
    public java.lang.String getSendStatus() {
        return sendStatus;
    }


    /**
     * Sets the sendStatus value for this MessageOut_Type.
     * 
     * @param sendStatus
     */
    public void setSendStatus(java.lang.String sendStatus) {
        this.sendStatus = sendStatus;
    }


    /**
     * Gets the smsCount value for this MessageOut_Type.
     * 
     * @return smsCount
     */
    public java.math.BigInteger getSmsCount() {
        return smsCount;
    }


    /**
     * Sets the smsCount value for this MessageOut_Type.
     * 
     * @param smsCount
     */
    public void setSmsCount(java.math.BigInteger smsCount) {
        this.smsCount = smsCount;
    }


    /**
     * Gets the deliverTime value for this MessageOut_Type.
     * 
     * @return deliverTime
     */
    public java.util.Calendar getDeliverTime() {
        return deliverTime;
    }


    /**
     * Sets the deliverTime value for this MessageOut_Type.
     * 
     * @param deliverTime
     */
    public void setDeliverTime(java.util.Calendar deliverTime) {
        this.deliverTime = deliverTime;
    }


    /**
     * Gets the deliverStatus value for this MessageOut_Type.
     * 
     * @return deliverStatus
     */
    public java.lang.String getDeliverStatus() {
        return deliverStatus;
    }


    /**
     * Sets the deliverStatus value for this MessageOut_Type.
     * 
     * @param deliverStatus
     */
    public void setDeliverStatus(java.lang.String deliverStatus) {
        this.deliverStatus = deliverStatus;
    }


    /**
     * Gets the resource value for this MessageOut_Type.
     * 
     * @return resource
     */
    public com.rssl.phizic.test.ermb.smslog.generated.ResourceId_Type getResource() {
        return resource;
    }


    /**
     * Sets the resource value for this MessageOut_Type.
     * 
     * @param resource
     */
    public void setResource(com.rssl.phizic.test.ermb.smslog.generated.ResourceId_Type resource) {
        this.resource = resource;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessageOut_Type)) return false;
        MessageOut_Type other = (MessageOut_Type) obj;
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
            ((this.createTime==null && other.getCreateTime()==null) || 
             (this.createTime!=null &&
              this.createTime.equals(other.getCreateTime()))) &&
            ((this.sendTime==null && other.getSendTime()==null) || 
             (this.sendTime!=null &&
              this.sendTime.equals(other.getSendTime()))) &&
            ((this.sendStatus==null && other.getSendStatus()==null) || 
             (this.sendStatus!=null &&
              this.sendStatus.equals(other.getSendStatus()))) &&
            ((this.smsCount==null && other.getSmsCount()==null) || 
             (this.smsCount!=null &&
              this.smsCount.equals(other.getSmsCount()))) &&
            ((this.deliverTime==null && other.getDeliverTime()==null) || 
             (this.deliverTime!=null &&
              this.deliverTime.equals(other.getDeliverTime()))) &&
            ((this.deliverStatus==null && other.getDeliverStatus()==null) || 
             (this.deliverStatus!=null &&
              this.deliverStatus.equals(other.getDeliverStatus()))) &&
            ((this.resource==null && other.getResource()==null) || 
             (this.resource!=null &&
              this.resource.equals(other.getResource())));
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
        if (getCreateTime() != null) {
            _hashCode += getCreateTime().hashCode();
        }
        if (getSendTime() != null) {
            _hashCode += getSendTime().hashCode();
        }
        if (getSendStatus() != null) {
            _hashCode += getSendStatus().hashCode();
        }
        if (getSmsCount() != null) {
            _hashCode += getSmsCount().hashCode();
        }
        if (getDeliverTime() != null) {
            _hashCode += getDeliverTime().hashCode();
        }
        if (getDeliverStatus() != null) {
            _hashCode += getDeliverStatus().hashCode();
        }
        if (getResource() != null) {
            _hashCode += getResource().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessageOut_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "messageOut_Type"));
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
        elemField.setFieldName("createTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "createTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "sendTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "sendStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smsCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "smsCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliverTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "deliverTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliverStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "deliverStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resource");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "resource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "ResourceId_Type"));
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
