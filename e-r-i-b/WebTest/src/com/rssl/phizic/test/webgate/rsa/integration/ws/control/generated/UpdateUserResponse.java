/**
 * UpdateUserResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class UpdateUserResponse  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GenericResponse  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RiskResult riskResult;

    public UpdateUserResponse() {
    }

    public UpdateUserResponse(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceResult deviceResult,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.StatusHeader statusHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RiskResult riskResult) {
        super(
            deviceResult,
            identificationData,
            messageHeader,
            statusHeader);
        this.credentialManagementResponseList = credentialManagementResponseList;
        this.deviceManagementResponse = deviceManagementResponse;
        this.riskResult = riskResult;
    }


    /**
     * Gets the credentialManagementResponseList value for this UpdateUserResponse.
     * 
     * @return credentialManagementResponseList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementResponseList getCredentialManagementResponseList() {
        return credentialManagementResponseList;
    }


    /**
     * Sets the credentialManagementResponseList value for this UpdateUserResponse.
     * 
     * @param credentialManagementResponseList
     */
    public void setCredentialManagementResponseList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList) {
        this.credentialManagementResponseList = credentialManagementResponseList;
    }


    /**
     * Gets the deviceManagementResponse value for this UpdateUserResponse.
     * 
     * @return deviceManagementResponse
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementResponsePayload getDeviceManagementResponse() {
        return deviceManagementResponse;
    }


    /**
     * Sets the deviceManagementResponse value for this UpdateUserResponse.
     * 
     * @param deviceManagementResponse
     */
    public void setDeviceManagementResponse(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse) {
        this.deviceManagementResponse = deviceManagementResponse;
    }


    /**
     * Gets the riskResult value for this UpdateUserResponse.
     * 
     * @return riskResult
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RiskResult getRiskResult() {
        return riskResult;
    }


    /**
     * Sets the riskResult value for this UpdateUserResponse.
     * 
     * @param riskResult
     */
    public void setRiskResult(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RiskResult riskResult) {
        this.riskResult = riskResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateUserResponse)) return false;
        UpdateUserResponse other = (UpdateUserResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.credentialManagementResponseList==null && other.getCredentialManagementResponseList()==null) || 
             (this.credentialManagementResponseList!=null &&
              this.credentialManagementResponseList.equals(other.getCredentialManagementResponseList()))) &&
            ((this.deviceManagementResponse==null && other.getDeviceManagementResponse()==null) || 
             (this.deviceManagementResponse!=null &&
              this.deviceManagementResponse.equals(other.getDeviceManagementResponse()))) &&
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
        int _hashCode = super.hashCode();
        if (getCredentialManagementResponseList() != null) {
            _hashCode += getCredentialManagementResponseList().hashCode();
        }
        if (getDeviceManagementResponse() != null) {
            _hashCode += getDeviceManagementResponse().hashCode();
        }
        if (getRiskResult() != null) {
            _hashCode += getRiskResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateUserResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UpdateUserResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialManagementResponseList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialManagementResponseList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialManagementResponseList"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceManagementResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceManagementResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceManagementResponsePayload"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "riskResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RiskResult"));
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
