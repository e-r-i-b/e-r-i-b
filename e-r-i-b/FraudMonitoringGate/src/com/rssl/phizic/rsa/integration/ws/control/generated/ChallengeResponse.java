/**
 * ChallengeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class ChallengeResponse  extends com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialChallengeList credentialChallengeList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse;

    public ChallengeResponse() {
    }

    public ChallengeResponse(
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceResult deviceResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.StatusHeader statusHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialChallengeList credentialChallengeList,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse) {
        super(
            deviceResult,
            identificationData,
            messageHeader,
            statusHeader);
        this.credentialChallengeList = credentialChallengeList;
        this.deviceManagementResponse = deviceManagementResponse;
    }


    /**
     * Gets the credentialChallengeList value for this ChallengeResponse.
     * 
     * @return credentialChallengeList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialChallengeList getCredentialChallengeList() {
        return credentialChallengeList;
    }


    /**
     * Sets the credentialChallengeList value for this ChallengeResponse.
     * 
     * @param credentialChallengeList
     */
    public void setCredentialChallengeList(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialChallengeList credentialChallengeList) {
        this.credentialChallengeList = credentialChallengeList;
    }


    /**
     * Gets the deviceManagementResponse value for this ChallengeResponse.
     * 
     * @return deviceManagementResponse
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload getDeviceManagementResponse() {
        return deviceManagementResponse;
    }


    /**
     * Sets the deviceManagementResponse value for this ChallengeResponse.
     * 
     * @param deviceManagementResponse
     */
    public void setDeviceManagementResponse(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse) {
        this.deviceManagementResponse = deviceManagementResponse;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeResponse)) return false;
        ChallengeResponse other = (ChallengeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.credentialChallengeList==null && other.getCredentialChallengeList()==null) || 
             (this.credentialChallengeList!=null &&
              this.credentialChallengeList.equals(other.getCredentialChallengeList()))) &&
            ((this.deviceManagementResponse==null && other.getDeviceManagementResponse()==null) || 
             (this.deviceManagementResponse!=null &&
              this.deviceManagementResponse.equals(other.getDeviceManagementResponse())));
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
        if (getCredentialChallengeList() != null) {
            _hashCode += getCredentialChallengeList().hashCode();
        }
        if (getDeviceManagementResponse() != null) {
            _hashCode += getDeviceManagementResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialChallengeList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialChallengeList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialChallengeList"));
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
