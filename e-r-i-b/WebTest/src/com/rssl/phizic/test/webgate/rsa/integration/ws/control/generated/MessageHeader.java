/**
 * MessageHeader.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This header provides general information about the request
 */
public class MessageHeader  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.APIType apiType;

    private java.lang.String requestId;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RequestType requestType;

    private java.lang.String timeStamp;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageVersion version;

    public MessageHeader() {
    }

    public MessageHeader(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.APIType apiType,
           java.lang.String requestId,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RequestType requestType,
           java.lang.String timeStamp,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageVersion version) {
           this.apiType = apiType;
           this.requestId = requestId;
           this.requestType = requestType;
           this.timeStamp = timeStamp;
           this.version = version;
    }


    /**
     * Gets the apiType value for this MessageHeader.
     * 
     * @return apiType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.APIType getApiType() {
        return apiType;
    }


    /**
     * Sets the apiType value for this MessageHeader.
     * 
     * @param apiType
     */
    public void setApiType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.APIType apiType) {
        this.apiType = apiType;
    }


    /**
     * Gets the requestId value for this MessageHeader.
     * 
     * @return requestId
     */
    public java.lang.String getRequestId() {
        return requestId;
    }


    /**
     * Sets the requestId value for this MessageHeader.
     * 
     * @param requestId
     */
    public void setRequestId(java.lang.String requestId) {
        this.requestId = requestId;
    }


    /**
     * Gets the requestType value for this MessageHeader.
     * 
     * @return requestType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RequestType getRequestType() {
        return requestType;
    }


    /**
     * Sets the requestType value for this MessageHeader.
     * 
     * @param requestType
     */
    public void setRequestType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RequestType requestType) {
        this.requestType = requestType;
    }


    /**
     * Gets the timeStamp value for this MessageHeader.
     * 
     * @return timeStamp
     */
    public java.lang.String getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this MessageHeader.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.lang.String timeStamp) {
        this.timeStamp = timeStamp;
    }


    /**
     * Gets the version value for this MessageHeader.
     * 
     * @return version
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageVersion getVersion() {
        return version;
    }


    /**
     * Sets the version value for this MessageHeader.
     * 
     * @param version
     */
    public void setVersion(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageVersion version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessageHeader)) return false;
        MessageHeader other = (MessageHeader) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.apiType==null && other.getApiType()==null) || 
             (this.apiType!=null &&
              this.apiType.equals(other.getApiType()))) &&
            ((this.requestId==null && other.getRequestId()==null) || 
             (this.requestId!=null &&
              this.requestId.equals(other.getRequestId()))) &&
            ((this.requestType==null && other.getRequestType()==null) || 
             (this.requestType!=null &&
              this.requestType.equals(other.getRequestType()))) &&
            ((this.timeStamp==null && other.getTimeStamp()==null) || 
             (this.timeStamp!=null &&
              this.timeStamp.equals(other.getTimeStamp()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion())));
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
        if (getApiType() != null) {
            _hashCode += getApiType().hashCode();
        }
        if (getRequestId() != null) {
            _hashCode += getRequestId().hashCode();
        }
        if (getRequestType() != null) {
            _hashCode += getRequestType().hashCode();
        }
        if (getTimeStamp() != null) {
            _hashCode += getTimeStamp().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessageHeader.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MessageHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apiType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "apiType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "APIType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "requestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "requestType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RequestType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "timeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MessageVersion"));
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
