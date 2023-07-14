/**
 * ResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.pfr.generated;

public class ResultType  implements java.io.Serializable {
    private boolean responseExists;

    private java.lang.String sendTime;

    private java.lang.String receiveTime;

    private java.lang.String operationId;

    public ResultType() {
    }

    public ResultType(
           boolean responseExists,
           java.lang.String sendTime,
           java.lang.String receiveTime,
           java.lang.String operationId) {
           this.responseExists = responseExists;
           this.sendTime = sendTime;
           this.receiveTime = receiveTime;
           this.operationId = operationId;
    }


    /**
     * Gets the responseExists value for this ResultType.
     * 
     * @return responseExists
     */
    public boolean isResponseExists() {
        return responseExists;
    }


    /**
     * Sets the responseExists value for this ResultType.
     * 
     * @param responseExists
     */
    public void setResponseExists(boolean responseExists) {
        this.responseExists = responseExists;
    }


    /**
     * Gets the sendTime value for this ResultType.
     * 
     * @return sendTime
     */
    public java.lang.String getSendTime() {
        return sendTime;
    }


    /**
     * Sets the sendTime value for this ResultType.
     * 
     * @param sendTime
     */
    public void setSendTime(java.lang.String sendTime) {
        this.sendTime = sendTime;
    }


    /**
     * Gets the receiveTime value for this ResultType.
     * 
     * @return receiveTime
     */
    public java.lang.String getReceiveTime() {
        return receiveTime;
    }


    /**
     * Sets the receiveTime value for this ResultType.
     * 
     * @param receiveTime
     */
    public void setReceiveTime(java.lang.String receiveTime) {
        this.receiveTime = receiveTime;
    }


    /**
     * Gets the operationId value for this ResultType.
     * 
     * @return operationId
     */
    public java.lang.String getOperationId() {
        return operationId;
    }


    /**
     * Sets the operationId value for this ResultType.
     * 
     * @param operationId
     */
    public void setOperationId(java.lang.String operationId) {
        this.operationId = operationId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultType)) return false;
        ResultType other = (ResultType) obj;
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
              this.receiveTime.equals(other.getReceiveTime()))) &&
            ((this.operationId==null && other.getOperationId()==null) || 
             (this.operationId!=null &&
              this.operationId.equals(other.getOperationId())));
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
        if (getOperationId() != null) {
            _hashCode += getOperationId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.pfr.esberiblistener.ws.phizic.rssl.com", "ResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseExists");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ResponseExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SendTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiveTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ReceiveTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OperationId"));
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
