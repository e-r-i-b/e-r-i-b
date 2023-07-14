/**
 * CreateUserRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class CreateUserRequest  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GenericRequest  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementRequestList credentialManagementRequestList;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RunRiskType runRiskType;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserData userData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator;

    private java.lang.String clientDefinedChannelIndicator;

    public CreateUserRequest() {
    }

    public CreateUserRequest(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GenericActionTypeList actionTypeList,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ConfigurationHeader configurationHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceRequest deviceRequest,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SecurityHeader securityHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementRequestList credentialManagementRequestList,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RunRiskType runRiskType,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserData userData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator,
           java.lang.String clientDefinedChannelIndicator) {
        super(
            actionTypeList,
            configurationHeader,
            deviceRequest,
            identificationData,
            messageHeader,
            securityHeader);
        this.credentialManagementRequestList = credentialManagementRequestList;
        this.deviceManagementRequest = deviceManagementRequest;
        this.runRiskType = runRiskType;
        this.userData = userData;
        this.channelIndicator = channelIndicator;
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }


    /**
     * Gets the credentialManagementRequestList value for this CreateUserRequest.
     * 
     * @return credentialManagementRequestList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementRequestList getCredentialManagementRequestList() {
        return credentialManagementRequestList;
    }


    /**
     * Sets the credentialManagementRequestList value for this CreateUserRequest.
     * 
     * @param credentialManagementRequestList
     */
    public void setCredentialManagementRequestList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialManagementRequestList credentialManagementRequestList) {
        this.credentialManagementRequestList = credentialManagementRequestList;
    }


    /**
     * Gets the deviceManagementRequest value for this CreateUserRequest.
     * 
     * @return deviceManagementRequest
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementRequestPayload getDeviceManagementRequest() {
        return deviceManagementRequest;
    }


    /**
     * Sets the deviceManagementRequest value for this CreateUserRequest.
     * 
     * @param deviceManagementRequest
     */
    public void setDeviceManagementRequest(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest) {
        this.deviceManagementRequest = deviceManagementRequest;
    }


    /**
     * Gets the runRiskType value for this CreateUserRequest.
     * 
     * @return runRiskType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RunRiskType getRunRiskType() {
        return runRiskType;
    }


    /**
     * Sets the runRiskType value for this CreateUserRequest.
     * 
     * @param runRiskType
     */
    public void setRunRiskType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RunRiskType runRiskType) {
        this.runRiskType = runRiskType;
    }


    /**
     * Gets the userData value for this CreateUserRequest.
     * 
     * @return userData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserData getUserData() {
        return userData;
    }


    /**
     * Sets the userData value for this CreateUserRequest.
     * 
     * @param userData
     */
    public void setUserData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserData userData) {
        this.userData = userData;
    }


    /**
     * Gets the channelIndicator value for this CreateUserRequest.
     * 
     * @return channelIndicator
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChannelIndicatorType getChannelIndicator() {
        return channelIndicator;
    }


    /**
     * Sets the channelIndicator value for this CreateUserRequest.
     * 
     * @param channelIndicator
     */
    public void setChannelIndicator(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator) {
        this.channelIndicator = channelIndicator;
    }


    /**
     * Gets the clientDefinedChannelIndicator value for this CreateUserRequest.
     * 
     * @return clientDefinedChannelIndicator
     */
    public java.lang.String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }


    /**
     * Sets the clientDefinedChannelIndicator value for this CreateUserRequest.
     * 
     * @param clientDefinedChannelIndicator
     */
    public void setClientDefinedChannelIndicator(java.lang.String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateUserRequest)) return false;
        CreateUserRequest other = (CreateUserRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.credentialManagementRequestList==null && other.getCredentialManagementRequestList()==null) || 
             (this.credentialManagementRequestList!=null &&
              this.credentialManagementRequestList.equals(other.getCredentialManagementRequestList()))) &&
            ((this.deviceManagementRequest==null && other.getDeviceManagementRequest()==null) || 
             (this.deviceManagementRequest!=null &&
              this.deviceManagementRequest.equals(other.getDeviceManagementRequest()))) &&
            ((this.runRiskType==null && other.getRunRiskType()==null) || 
             (this.runRiskType!=null &&
              this.runRiskType.equals(other.getRunRiskType()))) &&
            ((this.userData==null && other.getUserData()==null) || 
             (this.userData!=null &&
              this.userData.equals(other.getUserData()))) &&
            ((this.channelIndicator==null && other.getChannelIndicator()==null) || 
             (this.channelIndicator!=null &&
              this.channelIndicator.equals(other.getChannelIndicator()))) &&
            ((this.clientDefinedChannelIndicator==null && other.getClientDefinedChannelIndicator()==null) || 
             (this.clientDefinedChannelIndicator!=null &&
              this.clientDefinedChannelIndicator.equals(other.getClientDefinedChannelIndicator())));
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
        if (getCredentialManagementRequestList() != null) {
            _hashCode += getCredentialManagementRequestList().hashCode();
        }
        if (getDeviceManagementRequest() != null) {
            _hashCode += getDeviceManagementRequest().hashCode();
        }
        if (getRunRiskType() != null) {
            _hashCode += getRunRiskType().hashCode();
        }
        if (getUserData() != null) {
            _hashCode += getUserData().hashCode();
        }
        if (getChannelIndicator() != null) {
            _hashCode += getChannelIndicator().hashCode();
        }
        if (getClientDefinedChannelIndicator() != null) {
            _hashCode += getClientDefinedChannelIndicator().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateUserRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CreateUserRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialManagementRequestList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialManagementRequestList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialManagementRequestList"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceManagementRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceManagementRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceManagementRequestPayload"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("runRiskType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "runRiskType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RunRiskType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "channelIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChannelIndicatorType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDefinedChannelIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientDefinedChannelIndicator"));
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
