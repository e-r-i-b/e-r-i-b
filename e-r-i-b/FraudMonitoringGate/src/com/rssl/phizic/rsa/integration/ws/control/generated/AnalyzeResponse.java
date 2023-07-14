/**
 * AnalyzeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class AnalyzeResponse  extends com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.CollectableCredential[] collectableCredentialList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList credentialAuthResultList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse;

    private com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[] requiredCredentialList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.RiskResult riskResult;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ServerRedirectData serverRedirectData;

    public AnalyzeResponse() {
    }

    public AnalyzeResponse(
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceResult deviceResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.StatusHeader statusHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.CollectableCredential[] collectableCredentialList,
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList credentialAuthResultList,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse,
           com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[] requiredCredentialList,
           com.rssl.phizic.rsa.integration.ws.control.generated.RiskResult riskResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.ServerRedirectData serverRedirectData) {
        super(
            deviceResult,
            identificationData,
            messageHeader,
            statusHeader);
        this.collectableCredentialList = collectableCredentialList;
        this.credentialAuthResultList = credentialAuthResultList;
        this.deviceManagementResponse = deviceManagementResponse;
        this.requiredCredentialList = requiredCredentialList;
        this.riskResult = riskResult;
        this.serverRedirectData = serverRedirectData;
    }


    /**
     * Gets the collectableCredentialList value for this AnalyzeResponse.
     * 
     * @return collectableCredentialList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CollectableCredential[] getCollectableCredentialList() {
        return collectableCredentialList;
    }


    /**
     * Sets the collectableCredentialList value for this AnalyzeResponse.
     * 
     * @param collectableCredentialList
     */
    public void setCollectableCredentialList(com.rssl.phizic.rsa.integration.ws.control.generated.CollectableCredential[] collectableCredentialList) {
        this.collectableCredentialList = collectableCredentialList;
    }


    /**
     * Gets the credentialAuthResultList value for this AnalyzeResponse.
     * 
     * @return credentialAuthResultList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList getCredentialAuthResultList() {
        return credentialAuthResultList;
    }


    /**
     * Sets the credentialAuthResultList value for this AnalyzeResponse.
     * 
     * @param credentialAuthResultList
     */
    public void setCredentialAuthResultList(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList credentialAuthResultList) {
        this.credentialAuthResultList = credentialAuthResultList;
    }


    /**
     * Gets the deviceManagementResponse value for this AnalyzeResponse.
     * 
     * @return deviceManagementResponse
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload getDeviceManagementResponse() {
        return deviceManagementResponse;
    }


    /**
     * Sets the deviceManagementResponse value for this AnalyzeResponse.
     * 
     * @param deviceManagementResponse
     */
    public void setDeviceManagementResponse(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse) {
        this.deviceManagementResponse = deviceManagementResponse;
    }


    /**
     * Gets the requiredCredentialList value for this AnalyzeResponse.
     * 
     * @return requiredCredentialList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[] getRequiredCredentialList() {
        return requiredCredentialList;
    }


    /**
     * Sets the requiredCredentialList value for this AnalyzeResponse.
     * 
     * @param requiredCredentialList
     */
    public void setRequiredCredentialList(com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[] requiredCredentialList) {
        this.requiredCredentialList = requiredCredentialList;
    }


    /**
     * Gets the riskResult value for this AnalyzeResponse.
     * 
     * @return riskResult
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.RiskResult getRiskResult() {
        return riskResult;
    }


    /**
     * Sets the riskResult value for this AnalyzeResponse.
     * 
     * @param riskResult
     */
    public void setRiskResult(com.rssl.phizic.rsa.integration.ws.control.generated.RiskResult riskResult) {
        this.riskResult = riskResult;
    }


    /**
     * Gets the serverRedirectData value for this AnalyzeResponse.
     * 
     * @return serverRedirectData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ServerRedirectData getServerRedirectData() {
        return serverRedirectData;
    }


    /**
     * Sets the serverRedirectData value for this AnalyzeResponse.
     * 
     * @param serverRedirectData
     */
    public void setServerRedirectData(com.rssl.phizic.rsa.integration.ws.control.generated.ServerRedirectData serverRedirectData) {
        this.serverRedirectData = serverRedirectData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AnalyzeResponse)) return false;
        AnalyzeResponse other = (AnalyzeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.collectableCredentialList==null && other.getCollectableCredentialList()==null) || 
             (this.collectableCredentialList!=null &&
              java.util.Arrays.equals(this.collectableCredentialList, other.getCollectableCredentialList()))) &&
            ((this.credentialAuthResultList==null && other.getCredentialAuthResultList()==null) || 
             (this.credentialAuthResultList!=null &&
              this.credentialAuthResultList.equals(other.getCredentialAuthResultList()))) &&
            ((this.deviceManagementResponse==null && other.getDeviceManagementResponse()==null) || 
             (this.deviceManagementResponse!=null &&
              this.deviceManagementResponse.equals(other.getDeviceManagementResponse()))) &&
            ((this.requiredCredentialList==null && other.getRequiredCredentialList()==null) || 
             (this.requiredCredentialList!=null &&
              java.util.Arrays.equals(this.requiredCredentialList, other.getRequiredCredentialList()))) &&
            ((this.riskResult==null && other.getRiskResult()==null) || 
             (this.riskResult!=null &&
              this.riskResult.equals(other.getRiskResult()))) &&
            ((this.serverRedirectData==null && other.getServerRedirectData()==null) || 
             (this.serverRedirectData!=null &&
              this.serverRedirectData.equals(other.getServerRedirectData())));
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
        if (getCollectableCredentialList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCollectableCredentialList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCollectableCredentialList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCredentialAuthResultList() != null) {
            _hashCode += getCredentialAuthResultList().hashCode();
        }
        if (getDeviceManagementResponse() != null) {
            _hashCode += getDeviceManagementResponse().hashCode();
        }
        if (getRequiredCredentialList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequiredCredentialList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequiredCredentialList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRiskResult() != null) {
            _hashCode += getRiskResult().hashCode();
        }
        if (getServerRedirectData() != null) {
            _hashCode += getServerRedirectData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AnalyzeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AnalyzeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectableCredentialList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "collectableCredentialList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectableCredential"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "collectableCredential"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialAuthResultList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialAuthResultList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthResultList"));
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
        elemField.setFieldName("requiredCredentialList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "requiredCredentialList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RequiredCredential"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "requiredCredential"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riskResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "riskResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RiskResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverRedirectData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "serverRedirectData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ServerRedirectData"));
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
