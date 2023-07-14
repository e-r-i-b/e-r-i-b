/**
 * AnalyzeRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class AnalyzeRequest  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GenericRequest  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceIdentifier channel;

    private java.lang.Boolean autoCreateUserFlag;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ClientReturnData clientReturnData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionRequest collectionRequest;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialDataList credentialDataList;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.EventData[] eventDataList;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RunRiskType runRiskType;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserData userData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator;

    private java.lang.String clientDefinedChannelIndicator;

    public AnalyzeRequest() {
    }

    public AnalyzeRequest(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GenericActionTypeList actionTypeList,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ConfigurationHeader configurationHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceRequest deviceRequest,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.SecurityHeader securityHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceIdentifier channel,
           java.lang.Boolean autoCreateUserFlag,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ClientReturnData clientReturnData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionRequest collectionRequest,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialDataList credentialDataList,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.EventData[] eventDataList,
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
        this.channel = channel;
        this.autoCreateUserFlag = autoCreateUserFlag;
        this.clientReturnData = clientReturnData;
        this.collectionRequest = collectionRequest;
        this.credentialDataList = credentialDataList;
        this.deviceManagementRequest = deviceManagementRequest;
        this.eventDataList = eventDataList;
        this.runRiskType = runRiskType;
        this.userData = userData;
        this.channelIndicator = channelIndicator;
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }


    /**
     * Gets the channel value for this AnalyzeRequest.
     * 
     * @return channel
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceIdentifier getChannel() {
        return channel;
    }


    /**
     * Sets the channel value for this AnalyzeRequest.
     * 
     * @param channel
     */
    public void setChannel(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceIdentifier channel) {
        this.channel = channel;
    }


    /**
     * Gets the autoCreateUserFlag value for this AnalyzeRequest.
     * 
     * @return autoCreateUserFlag
     */
    public java.lang.Boolean getAutoCreateUserFlag() {
        return autoCreateUserFlag;
    }


    /**
     * Sets the autoCreateUserFlag value for this AnalyzeRequest.
     * 
     * @param autoCreateUserFlag
     */
    public void setAutoCreateUserFlag(java.lang.Boolean autoCreateUserFlag) {
        this.autoCreateUserFlag = autoCreateUserFlag;
    }


    /**
     * Gets the clientReturnData value for this AnalyzeRequest.
     * 
     * @return clientReturnData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ClientReturnData getClientReturnData() {
        return clientReturnData;
    }


    /**
     * Sets the clientReturnData value for this AnalyzeRequest.
     * 
     * @param clientReturnData
     */
    public void setClientReturnData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ClientReturnData clientReturnData) {
        this.clientReturnData = clientReturnData;
    }


    /**
     * Gets the collectionRequest value for this AnalyzeRequest.
     * 
     * @return collectionRequest
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionRequest getCollectionRequest() {
        return collectionRequest;
    }


    /**
     * Sets the collectionRequest value for this AnalyzeRequest.
     * 
     * @param collectionRequest
     */
    public void setCollectionRequest(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CollectionRequest collectionRequest) {
        this.collectionRequest = collectionRequest;
    }


    /**
     * Gets the credentialDataList value for this AnalyzeRequest.
     * 
     * @return credentialDataList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialDataList getCredentialDataList() {
        return credentialDataList;
    }


    /**
     * Sets the credentialDataList value for this AnalyzeRequest.
     * 
     * @param credentialDataList
     */
    public void setCredentialDataList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialDataList credentialDataList) {
        this.credentialDataList = credentialDataList;
    }


    /**
     * Gets the deviceManagementRequest value for this AnalyzeRequest.
     * 
     * @return deviceManagementRequest
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementRequestPayload getDeviceManagementRequest() {
        return deviceManagementRequest;
    }


    /**
     * Sets the deviceManagementRequest value for this AnalyzeRequest.
     * 
     * @param deviceManagementRequest
     */
    public void setDeviceManagementRequest(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest) {
        this.deviceManagementRequest = deviceManagementRequest;
    }


    /**
     * Gets the eventDataList value for this AnalyzeRequest.
     * 
     * @return eventDataList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.EventData[] getEventDataList() {
        return eventDataList;
    }


    /**
     * Sets the eventDataList value for this AnalyzeRequest.
     * 
     * @param eventDataList
     */
    public void setEventDataList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.EventData[] eventDataList) {
        this.eventDataList = eventDataList;
    }


    /**
     * Gets the runRiskType value for this AnalyzeRequest.
     * 
     * @return runRiskType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RunRiskType getRunRiskType() {
        return runRiskType;
    }


    /**
     * Sets the runRiskType value for this AnalyzeRequest.
     * 
     * @param runRiskType
     */
    public void setRunRiskType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.RunRiskType runRiskType) {
        this.runRiskType = runRiskType;
    }


    /**
     * Gets the userData value for this AnalyzeRequest.
     * 
     * @return userData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserData getUserData() {
        return userData;
    }


    /**
     * Sets the userData value for this AnalyzeRequest.
     * 
     * @param userData
     */
    public void setUserData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.UserData userData) {
        this.userData = userData;
    }


    /**
     * Gets the channelIndicator value for this AnalyzeRequest.
     * 
     * @return channelIndicator
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChannelIndicatorType getChannelIndicator() {
        return channelIndicator;
    }


    /**
     * Sets the channelIndicator value for this AnalyzeRequest.
     * 
     * @param channelIndicator
     */
    public void setChannelIndicator(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator) {
        this.channelIndicator = channelIndicator;
    }


    /**
     * Gets the clientDefinedChannelIndicator value for this AnalyzeRequest.
     * 
     * @return clientDefinedChannelIndicator
     */
    public java.lang.String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }


    /**
     * Sets the clientDefinedChannelIndicator value for this AnalyzeRequest.
     * 
     * @param clientDefinedChannelIndicator
     */
    public void setClientDefinedChannelIndicator(java.lang.String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AnalyzeRequest)) return false;
        AnalyzeRequest other = (AnalyzeRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.channel==null && other.getChannel()==null) || 
             (this.channel!=null &&
              this.channel.equals(other.getChannel()))) &&
            ((this.autoCreateUserFlag==null && other.getAutoCreateUserFlag()==null) || 
             (this.autoCreateUserFlag!=null &&
              this.autoCreateUserFlag.equals(other.getAutoCreateUserFlag()))) &&
            ((this.clientReturnData==null && other.getClientReturnData()==null) || 
             (this.clientReturnData!=null &&
              this.clientReturnData.equals(other.getClientReturnData()))) &&
            ((this.collectionRequest==null && other.getCollectionRequest()==null) || 
             (this.collectionRequest!=null &&
              this.collectionRequest.equals(other.getCollectionRequest()))) &&
            ((this.credentialDataList==null && other.getCredentialDataList()==null) || 
             (this.credentialDataList!=null &&
              this.credentialDataList.equals(other.getCredentialDataList()))) &&
            ((this.deviceManagementRequest==null && other.getDeviceManagementRequest()==null) || 
             (this.deviceManagementRequest!=null &&
              this.deviceManagementRequest.equals(other.getDeviceManagementRequest()))) &&
            ((this.eventDataList==null && other.getEventDataList()==null) || 
             (this.eventDataList!=null &&
              java.util.Arrays.equals(this.eventDataList, other.getEventDataList()))) &&
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
        if (getChannel() != null) {
            _hashCode += getChannel().hashCode();
        }
        if (getAutoCreateUserFlag() != null) {
            _hashCode += getAutoCreateUserFlag().hashCode();
        }
        if (getClientReturnData() != null) {
            _hashCode += getClientReturnData().hashCode();
        }
        if (getCollectionRequest() != null) {
            _hashCode += getCollectionRequest().hashCode();
        }
        if (getCredentialDataList() != null) {
            _hashCode += getCredentialDataList().hashCode();
        }
        if (getDeviceManagementRequest() != null) {
            _hashCode += getDeviceManagementRequest().hashCode();
        }
        if (getEventDataList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEventDataList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEventDataList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        new org.apache.axis.description.TypeDesc(AnalyzeRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AnalyzeRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "channel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceIdentifier"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoCreateUserFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "autoCreateUserFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientReturnData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientReturnData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientReturnData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "collectionRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialDataList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialDataList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialDataList"));
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
        elemField.setFieldName("eventDataList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "eventDataList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EventData"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "eventData"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("runRiskType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "runRiskType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RunRiskType"));
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
