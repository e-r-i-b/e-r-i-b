/**
 * LogEntry_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.ermb.smslog.generated;


/**
 * Запись в журнале сообщений
 */
public class LogEntry_Type  implements java.io.Serializable {
    private long logNum;

    private java.lang.String messageType;

    private com.rssl.phizic.test.ermb.smslog.generated.MessageIn_Type messageIn;

    private com.rssl.phizic.test.ermb.smslog.generated.MessageOut_Type messageOut;

    private com.rssl.phizic.test.ermb.smslog.generated.ClientRs_Type client;

    private java.lang.String system;

    private java.lang.String extinfo;

    public LogEntry_Type() {
    }

    public LogEntry_Type(
           long logNum,
           java.lang.String messageType,
           com.rssl.phizic.test.ermb.smslog.generated.MessageIn_Type messageIn,
           com.rssl.phizic.test.ermb.smslog.generated.MessageOut_Type messageOut,
           com.rssl.phizic.test.ermb.smslog.generated.ClientRs_Type client,
           java.lang.String system,
           java.lang.String extinfo) {
           this.logNum = logNum;
           this.messageType = messageType;
           this.messageIn = messageIn;
           this.messageOut = messageOut;
           this.client = client;
           this.system = system;
           this.extinfo = extinfo;
    }


    /**
     * Gets the logNum value for this LogEntry_Type.
     * 
     * @return logNum
     */
    public long getLogNum() {
        return logNum;
    }


    /**
     * Sets the logNum value for this LogEntry_Type.
     * 
     * @param logNum
     */
    public void setLogNum(long logNum) {
        this.logNum = logNum;
    }


    /**
     * Gets the messageType value for this LogEntry_Type.
     * 
     * @return messageType
     */
    public java.lang.String getMessageType() {
        return messageType;
    }


    /**
     * Sets the messageType value for this LogEntry_Type.
     * 
     * @param messageType
     */
    public void setMessageType(java.lang.String messageType) {
        this.messageType = messageType;
    }


    /**
     * Gets the messageIn value for this LogEntry_Type.
     * 
     * @return messageIn
     */
    public com.rssl.phizic.test.ermb.smslog.generated.MessageIn_Type getMessageIn() {
        return messageIn;
    }


    /**
     * Sets the messageIn value for this LogEntry_Type.
     * 
     * @param messageIn
     */
    public void setMessageIn(com.rssl.phizic.test.ermb.smslog.generated.MessageIn_Type messageIn) {
        this.messageIn = messageIn;
    }


    /**
     * Gets the messageOut value for this LogEntry_Type.
     * 
     * @return messageOut
     */
    public com.rssl.phizic.test.ermb.smslog.generated.MessageOut_Type getMessageOut() {
        return messageOut;
    }


    /**
     * Sets the messageOut value for this LogEntry_Type.
     * 
     * @param messageOut
     */
    public void setMessageOut(com.rssl.phizic.test.ermb.smslog.generated.MessageOut_Type messageOut) {
        this.messageOut = messageOut;
    }


    /**
     * Gets the client value for this LogEntry_Type.
     * 
     * @return client
     */
    public com.rssl.phizic.test.ermb.smslog.generated.ClientRs_Type getClient() {
        return client;
    }


    /**
     * Sets the client value for this LogEntry_Type.
     * 
     * @param client
     */
    public void setClient(com.rssl.phizic.test.ermb.smslog.generated.ClientRs_Type client) {
        this.client = client;
    }


    /**
     * Gets the system value for this LogEntry_Type.
     * 
     * @return system
     */
    public java.lang.String getSystem() {
        return system;
    }


    /**
     * Sets the system value for this LogEntry_Type.
     * 
     * @param system
     */
    public void setSystem(java.lang.String system) {
        this.system = system;
    }


    /**
     * Gets the extinfo value for this LogEntry_Type.
     * 
     * @return extinfo
     */
    public java.lang.String getExtinfo() {
        return extinfo;
    }


    /**
     * Sets the extinfo value for this LogEntry_Type.
     * 
     * @param extinfo
     */
    public void setExtinfo(java.lang.String extinfo) {
        this.extinfo = extinfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogEntry_Type)) return false;
        LogEntry_Type other = (LogEntry_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.logNum == other.getLogNum() &&
            ((this.messageType==null && other.getMessageType()==null) || 
             (this.messageType!=null &&
              this.messageType.equals(other.getMessageType()))) &&
            ((this.messageIn==null && other.getMessageIn()==null) || 
             (this.messageIn!=null &&
              this.messageIn.equals(other.getMessageIn()))) &&
            ((this.messageOut==null && other.getMessageOut()==null) || 
             (this.messageOut!=null &&
              this.messageOut.equals(other.getMessageOut()))) &&
            ((this.client==null && other.getClient()==null) || 
             (this.client!=null &&
              this.client.equals(other.getClient()))) &&
            ((this.system==null && other.getSystem()==null) || 
             (this.system!=null &&
              this.system.equals(other.getSystem()))) &&
            ((this.extinfo==null && other.getExtinfo()==null) || 
             (this.extinfo!=null &&
              this.extinfo.equals(other.getExtinfo())));
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
        _hashCode += new Long(getLogNum()).hashCode();
        if (getMessageType() != null) {
            _hashCode += getMessageType().hashCode();
        }
        if (getMessageIn() != null) {
            _hashCode += getMessageIn().hashCode();
        }
        if (getMessageOut() != null) {
            _hashCode += getMessageOut().hashCode();
        }
        if (getClient() != null) {
            _hashCode += getClient().hashCode();
        }
        if (getSystem() != null) {
            _hashCode += getSystem().hashCode();
        }
        if (getExtinfo() != null) {
            _hashCode += getExtinfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogEntry_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "logEntry_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "logNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "messageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageIn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "messageIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "messageIn_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageOut");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "messageOut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "messageOut_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("client");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "client"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "clientRs_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("system");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "system"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extinfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "extinfo"));
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
