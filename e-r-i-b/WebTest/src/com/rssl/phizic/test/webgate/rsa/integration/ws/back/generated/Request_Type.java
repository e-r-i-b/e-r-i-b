/**
 * Request_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated;


/**
 * This defines the contents of a Request
 */
public class Request_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.IdentificationData_Type identificationData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type statusHeader;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.DeviceResult_Type deviceResult;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.RiskResult_Type riskResult;

    public Request_Type() {
    }

    public Request_Type(
           com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.IdentificationData_Type identificationData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type statusHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.DeviceResult_Type deviceResult,
           com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.RiskResult_Type riskResult) {
           this.identificationData = identificationData;
           this.statusHeader = statusHeader;
           this.deviceResult = deviceResult;
           this.riskResult = riskResult;
    }


    /**
     * Gets the identificationData value for this Request_Type.
     * 
     * @return identificationData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.IdentificationData_Type getIdentificationData() {
        return identificationData;
    }


    /**
     * Sets the identificationData value for this Request_Type.
     * 
     * @param identificationData
     */
    public void setIdentificationData(com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.IdentificationData_Type identificationData) {
        this.identificationData = identificationData;
    }


    /**
     * Gets the statusHeader value for this Request_Type.
     * 
     * @return statusHeader
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type getStatusHeader() {
        return statusHeader;
    }


    /**
     * Sets the statusHeader value for this Request_Type.
     * 
     * @param statusHeader
     */
    public void setStatusHeader(com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.StatusHeader_Type statusHeader) {
        this.statusHeader = statusHeader;
    }


    /**
     * Gets the deviceResult value for this Request_Type.
     * 
     * @return deviceResult
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.DeviceResult_Type getDeviceResult() {
        return deviceResult;
    }


    /**
     * Sets the deviceResult value for this Request_Type.
     * 
     * @param deviceResult
     */
    public void setDeviceResult(com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.DeviceResult_Type deviceResult) {
        this.deviceResult = deviceResult;
    }


    /**
     * Gets the riskResult value for this Request_Type.
     * 
     * @return riskResult
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.RiskResult_Type getRiskResult() {
        return riskResult;
    }


    /**
     * Sets the riskResult value for this Request_Type.
     * 
     * @param riskResult
     */
    public void setRiskResult(com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.RiskResult_Type riskResult) {
        this.riskResult = riskResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request_Type)) return false;
        Request_Type other = (Request_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identificationData==null && other.getIdentificationData()==null) || 
             (this.identificationData!=null &&
              this.identificationData.equals(other.getIdentificationData()))) &&
            ((this.statusHeader==null && other.getStatusHeader()==null) || 
             (this.statusHeader!=null &&
              this.statusHeader.equals(other.getStatusHeader()))) &&
            ((this.deviceResult==null && other.getDeviceResult()==null) || 
             (this.deviceResult!=null &&
              this.deviceResult.equals(other.getDeviceResult()))) &&
            ((this.riskResult==null && other.getRiskResult()==null) || 
             (this.riskResult!=null &&
              this.riskResult.equals(other.getRiskResult())));
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
        if (getIdentificationData() != null) {
            _hashCode += getIdentificationData().hashCode();
        }
        if (getStatusHeader() != null) {
            _hashCode += getStatusHeader().hashCode();
        }
        if (getDeviceResult() != null) {
            _hashCode += getDeviceResult().hashCode();
        }
        if (getRiskResult() != null) {
            _hashCode += getRiskResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "Request_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificationData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "identificationData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "IdentificationData_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "statusHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "StatusHeader_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "deviceResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "DeviceResult_Type"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "riskResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "RiskResult_Type"));
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
