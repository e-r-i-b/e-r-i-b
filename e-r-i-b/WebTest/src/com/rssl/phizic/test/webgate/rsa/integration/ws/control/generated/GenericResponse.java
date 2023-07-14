/**
 * GenericResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the contents of a Generic Response
 */
public abstract class GenericResponse  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceResult deviceResult;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData identificationData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader messageHeader;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.StatusHeader statusHeader;

    public GenericResponse() {
    }

    public GenericResponse(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceResult deviceResult,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.StatusHeader statusHeader) {
           this.deviceResult = deviceResult;
           this.identificationData = identificationData;
           this.messageHeader = messageHeader;
           this.statusHeader = statusHeader;
    }


    /**
     * Gets the deviceResult value for this GenericResponse.
     * 
     * @return deviceResult
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceResult getDeviceResult() {
        return deviceResult;
    }


    /**
     * Sets the deviceResult value for this GenericResponse.
     * 
     * @param deviceResult
     */
    public void setDeviceResult(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceResult deviceResult) {
        this.deviceResult = deviceResult;
    }


    /**
     * Gets the identificationData value for this GenericResponse.
     * 
     * @return identificationData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData getIdentificationData() {
        return identificationData;
    }


    /**
     * Sets the identificationData value for this GenericResponse.
     * 
     * @param identificationData
     */
    public void setIdentificationData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData identificationData) {
        this.identificationData = identificationData;
    }


    /**
     * Gets the messageHeader value for this GenericResponse.
     * 
     * @return messageHeader
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader getMessageHeader() {
        return messageHeader;
    }


    /**
     * Sets the messageHeader value for this GenericResponse.
     * 
     * @param messageHeader
     */
    public void setMessageHeader(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }


    /**
     * Gets the statusHeader value for this GenericResponse.
     * 
     * @return statusHeader
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.StatusHeader getStatusHeader() {
        return statusHeader;
    }


    /**
     * Sets the statusHeader value for this GenericResponse.
     * 
     * @param statusHeader
     */
    public void setStatusHeader(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.StatusHeader statusHeader) {
        this.statusHeader = statusHeader;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GenericResponse)) return false;
        GenericResponse other = (GenericResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.deviceResult==null && other.getDeviceResult()==null) || 
             (this.deviceResult!=null &&
              this.deviceResult.equals(other.getDeviceResult()))) &&
            ((this.identificationData==null && other.getIdentificationData()==null) || 
             (this.identificationData!=null &&
              this.identificationData.equals(other.getIdentificationData()))) &&
            ((this.messageHeader==null && other.getMessageHeader()==null) || 
             (this.messageHeader!=null &&
              this.messageHeader.equals(other.getMessageHeader()))) &&
            ((this.statusHeader==null && other.getStatusHeader()==null) || 
             (this.statusHeader!=null &&
              this.statusHeader.equals(other.getStatusHeader())));
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
        if (getDeviceResult() != null) {
            _hashCode += getDeviceResult().hashCode();
        }
        if (getIdentificationData() != null) {
            _hashCode += getIdentificationData().hashCode();
        }
        if (getMessageHeader() != null) {
            _hashCode += getMessageHeader().hashCode();
        }
        if (getStatusHeader() != null) {
            _hashCode += getStatusHeader().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GenericResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GenericResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificationData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "identificationData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "IdentificationData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "messageHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MessageHeader"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "statusHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StatusHeader"));
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
