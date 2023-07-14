/**
 * PfrResult_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип для возврата результата о состоянии выписки при запросе ПФР
 */
public class PfrResult_Type  implements java.io.Serializable {
    /* Признак наличия выписки в ПФР */
    private boolean responseExists;

    /* Дата и время отправки запроса в ПФР */
    private java.lang.String sendTime;

    /* Дата и время ответа из ПФР.Обязателен в случае, если ResponseExists
     * = true */
    private java.lang.String receiveTime;

    public PfrResult_Type() {
    }

    public PfrResult_Type(
           boolean responseExists,
           java.lang.String sendTime,
           java.lang.String receiveTime) {
           this.responseExists = responseExists;
           this.sendTime = sendTime;
           this.receiveTime = receiveTime;
    }


    /**
     * Gets the responseExists value for this PfrResult_Type.
     * 
     * @return responseExists   * Признак наличия выписки в ПФР
     */
    public boolean isResponseExists() {
        return responseExists;
    }


    /**
     * Sets the responseExists value for this PfrResult_Type.
     * 
     * @param responseExists   * Признак наличия выписки в ПФР
     */
    public void setResponseExists(boolean responseExists) {
        this.responseExists = responseExists;
    }


    /**
     * Gets the sendTime value for this PfrResult_Type.
     * 
     * @return sendTime   * Дата и время отправки запроса в ПФР
     */
    public java.lang.String getSendTime() {
        return sendTime;
    }


    /**
     * Sets the sendTime value for this PfrResult_Type.
     * 
     * @param sendTime   * Дата и время отправки запроса в ПФР
     */
    public void setSendTime(java.lang.String sendTime) {
        this.sendTime = sendTime;
    }


    /**
     * Gets the receiveTime value for this PfrResult_Type.
     * 
     * @return receiveTime   * Дата и время ответа из ПФР.Обязателен в случае, если ResponseExists
     * = true
     */
    public java.lang.String getReceiveTime() {
        return receiveTime;
    }


    /**
     * Sets the receiveTime value for this PfrResult_Type.
     * 
     * @param receiveTime   * Дата и время ответа из ПФР.Обязателен в случае, если ResponseExists
     * = true
     */
    public void setReceiveTime(java.lang.String receiveTime) {
        this.receiveTime = receiveTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PfrResult_Type)) return false;
        PfrResult_Type other = (PfrResult_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.responseExists == other.isResponseExists() &&
            ((this.sendTime==null && other.getSendTime()==null) || 
             (this.sendTime!=null &&
              this.sendTime.equals(other.getSendTime()))) &&
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
        _hashCode += (isResponseExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSendTime() != null) {
            _hashCode += getSendTime().hashCode();
        }
        if (getReceiveTime() != null) {
            _hashCode += getReceiveTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PfrResult_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PfrResult_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseExists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ResponseExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SendTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiveTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReceiveTime"));
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
