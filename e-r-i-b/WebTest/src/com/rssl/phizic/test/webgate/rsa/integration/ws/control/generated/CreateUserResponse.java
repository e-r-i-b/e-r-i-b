/**
 * CreateUserResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class CreateUserResponse  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GenericResponse  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RiskResult riskResult;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Credential[] systemCredentials;

    public CreateUserResponse() {
    }

    public CreateUserResponse(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceResult deviceResult,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.StatusHeader statusHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RiskResult riskResult,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Credential[] systemCredentials) {
        super(
            deviceResult,
            identificationData,
            messageHeader,
            statusHeader);
        this.credentialManagementResponseList = credentialManagementResponseList;
        this.deviceManagementResponse = deviceManagementResponse;
        this.riskResult = riskResult;
        this.systemCredentials = systemCredentials;
    }


    /**
     * Gets the credentialManagementResponseList value for this CreateUserResponse.
     * 
     * @return credentialManagementResponseList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementResponseList getCredentialManagementResponseList() {
        return credentialManagementResponseList;
    }


    /**
     * Sets the credentialManagementResponseList value for this CreateUserResponse.
     * 
     * @param credentialManagementResponseList
     */
    public void setCredentialManagementResponseList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList) {
        this.credentialManagementResponseList = credentialManagementResponseList;
    }


    /**
     * Gets the deviceManagementResponse value for this CreateUserResponse.
     * 
     * @return deviceManagementResponse
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementResponsePayload getDeviceManagementResponse() {
        return deviceManagementResponse;
    }


    /**
     * Sets the deviceManagementResponse value for this CreateUserResponse.
     * 
     * @param deviceManagementResponse
     */
    public void setDeviceManagementResponse(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse) {
        this.deviceManagementResponse = deviceManagementResponse;
    }


    /**
     * Gets the riskResult value for this CreateUserResponse.
     * 
     * @return riskResult
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RiskResult getRiskResult() {
        return riskResult;
    }


    /**
     * Sets the riskResult value for this CreateUserResponse.
     * 
     * @param riskResult
     */
    public void setRiskResult(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RiskResult riskResult) {
        this.riskResult = riskResult;
    }


    /**
     * Gets the systemCredentials value for this CreateUserResponse.
     * 
     * @return systemCredentials
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Credential[] getSystemCredentials() {
        return systemCredentials;
    }


    /**
     * Sets the systemCredentials value for this CreateUserResponse.
     * 
     * @param systemCredentials
     */
    public void setSystemCredentials(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Credential[] systemCredentials) {
        this.systemCredentials = systemCredentials;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateUserResponse)) return false;
        CreateUserResponse other = (CreateUserResponse) obj;
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
              this.riskResult.equals(other.getRiskResult()))) &&
            ((this.systemCredentials==null && other.getSystemCredentials()==null) || 
             (this.systemCredentials!=null &&
              java.util.Arrays.equals(this.systemCredentials, other.getSystemCredentials())));
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
        if (getSystemCredentials() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSystemCredentials());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSystemCredentials(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateUserResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CreateUserResponse"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemCredentials");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "systemCredentials"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Credential"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credential"));
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
