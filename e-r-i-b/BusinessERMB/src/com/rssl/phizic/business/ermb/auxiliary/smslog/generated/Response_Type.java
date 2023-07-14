/**
 * Response_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.ermb.auxiliary.smslog.generated;


/**
 * Запись в журнале сообщений
 */
public class Response_Type  implements java.io.Serializable {
    private com.rssl.phizic.business.ermb.auxiliary.smslog.generated.LogEntry_Type[] logEntries;

    private java.lang.Boolean hasMoreMessages;

    private java.lang.String sessionID;

    public Response_Type() {
    }

    public Response_Type(
           com.rssl.phizic.business.ermb.auxiliary.smslog.generated.LogEntry_Type[] logEntries,
           java.lang.Boolean hasMoreMessages,
           java.lang.String sessionID) {
           this.logEntries = logEntries;
           this.hasMoreMessages = hasMoreMessages;
           this.sessionID = sessionID;
    }


    /**
     * Gets the logEntries value for this Response_Type.
     * 
     * @return logEntries
     */
    public com.rssl.phizic.business.ermb.auxiliary.smslog.generated.LogEntry_Type[] getLogEntries() {
        return logEntries;
    }


    /**
     * Sets the logEntries value for this Response_Type.
     * 
     * @param logEntries
     */
    public void setLogEntries(com.rssl.phizic.business.ermb.auxiliary.smslog.generated.LogEntry_Type[] logEntries) {
        this.logEntries = logEntries;
    }


    /**
     * Gets the hasMoreMessages value for this Response_Type.
     * 
     * @return hasMoreMessages
     */
    public java.lang.Boolean getHasMoreMessages() {
        return hasMoreMessages;
    }


    /**
     * Sets the hasMoreMessages value for this Response_Type.
     * 
     * @param hasMoreMessages
     */
    public void setHasMoreMessages(java.lang.Boolean hasMoreMessages) {
        this.hasMoreMessages = hasMoreMessages;
    }


    /**
     * Gets the sessionID value for this Response_Type.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this Response_Type.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Response_Type)) return false;
        Response_Type other = (Response_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.logEntries==null && other.getLogEntries()==null) || 
             (this.logEntries!=null &&
              java.util.Arrays.equals(this.logEntries, other.getLogEntries()))) &&
            ((this.hasMoreMessages==null && other.getHasMoreMessages()==null) || 
             (this.hasMoreMessages!=null &&
              this.hasMoreMessages.equals(other.getHasMoreMessages()))) &&
            ((this.sessionID==null && other.getSessionID()==null) || 
             (this.sessionID!=null &&
              this.sessionID.equals(other.getSessionID())));
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
        if (getLogEntries() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLogEntries());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLogEntries(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getHasMoreMessages() != null) {
            _hashCode += getHasMoreMessages().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Response_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "response_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logEntries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "logEntries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "logEntry_Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "logEntry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasMoreMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "hasMoreMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/ermb/auxiliary/sms-log", "sessionID"));
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
