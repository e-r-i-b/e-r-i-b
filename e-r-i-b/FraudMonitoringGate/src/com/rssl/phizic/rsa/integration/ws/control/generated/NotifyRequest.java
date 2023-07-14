/**
 * NotifyRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines the contents of NOTIFY request
 */
public class NotifyRequest  extends com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest  implements java.io.Serializable {
    private java.lang.Boolean autoCreateUserFlag;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.EventData[] eventDataList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.UserData userData;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator;

    private java.lang.String clientDefinedChannelIndicator;

    public NotifyRequest() {
    }

    public NotifyRequest(
           com.rssl.phizic.rsa.integration.ws.control.generated.GenericActionTypeList actionTypeList,
           com.rssl.phizic.rsa.integration.ws.control.generated.ConfigurationHeader configurationHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest deviceRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.SecurityHeader securityHeader,
           java.lang.Boolean autoCreateUserFlag,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.EventData[] eventDataList,
           com.rssl.phizic.rsa.integration.ws.control.generated.UserData userData,
           com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator,
           java.lang.String clientDefinedChannelIndicator) {
        super(
            actionTypeList,
            configurationHeader,
            deviceRequest,
            identificationData,
            messageHeader,
            securityHeader);
        this.autoCreateUserFlag = autoCreateUserFlag;
        this.deviceManagementRequest = deviceManagementRequest;
        this.eventDataList = eventDataList;
        this.userData = userData;
        this.channelIndicator = channelIndicator;
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }


    /**
     * Gets the autoCreateUserFlag value for this NotifyRequest.
     * 
     * @return autoCreateUserFlag
     */
    public java.lang.Boolean getAutoCreateUserFlag() {
        return autoCreateUserFlag;
    }


    /**
     * Sets the autoCreateUserFlag value for this NotifyRequest.
     * 
     * @param autoCreateUserFlag
     */
    public void setAutoCreateUserFlag(java.lang.Boolean autoCreateUserFlag) {
        this.autoCreateUserFlag = autoCreateUserFlag;
    }


    /**
     * Gets the deviceManagementRequest value for this NotifyRequest.
     * 
     * @return deviceManagementRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementRequestPayload getDeviceManagementRequest() {
        return deviceManagementRequest;
    }


    /**
     * Sets the deviceManagementRequest value for this NotifyRequest.
     * 
     * @param deviceManagementRequest
     */
    public void setDeviceManagementRequest(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementRequestPayload deviceManagementRequest) {
        this.deviceManagementRequest = deviceManagementRequest;
    }


    /**
     * Gets the eventDataList value for this NotifyRequest.
     * 
     * @return eventDataList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.EventData[] getEventDataList() {
        return eventDataList;
    }


    /**
     * Sets the eventDataList value for this NotifyRequest.
     * 
     * @param eventDataList
     */
    public void setEventDataList(com.rssl.phizic.rsa.integration.ws.control.generated.EventData[] eventDataList) {
        this.eventDataList = eventDataList;
    }


    /**
     * Gets the userData value for this NotifyRequest.
     * 
     * @return userData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.UserData getUserData() {
        return userData;
    }


    /**
     * Sets the userData value for this NotifyRequest.
     * 
     * @param userData
     */
    public void setUserData(com.rssl.phizic.rsa.integration.ws.control.generated.UserData userData) {
        this.userData = userData;
    }


    /**
     * Gets the channelIndicator value for this NotifyRequest.
     * 
     * @return channelIndicator
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType getChannelIndicator() {
        return channelIndicator;
    }


    /**
     * Sets the channelIndicator value for this NotifyRequest.
     * 
     * @param channelIndicator
     */
    public void setChannelIndicator(com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator) {
        this.channelIndicator = channelIndicator;
    }


    /**
     * Gets the clientDefinedChannelIndicator value for this NotifyRequest.
     * 
     * @return clientDefinedChannelIndicator
     */
    public java.lang.String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }


    /**
     * Sets the clientDefinedChannelIndicator value for this NotifyRequest.
     * 
     * @param clientDefinedChannelIndicator
     */
    public void setClientDefinedChannelIndicator(java.lang.String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NotifyRequest)) return false;
        NotifyRequest other = (NotifyRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.autoCreateUserFlag==null && other.getAutoCreateUserFlag()==null) || 
             (this.autoCreateUserFlag!=null &&
              this.autoCreateUserFlag.equals(other.getAutoCreateUserFlag()))) &&
            ((this.deviceManagementRequest==null && other.getDeviceManagementRequest()==null) || 
             (this.deviceManagementRequest!=null &&
              this.deviceManagementRequest.equals(other.getDeviceManagementRequest()))) &&
            ((this.eventDataList==null && other.getEventDataList()==null) || 
             (this.eventDataList!=null &&
              java.util.Arrays.equals(this.eventDataList, other.getEventDataList()))) &&
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
        if (getAutoCreateUserFlag() != null) {
            _hashCode += getAutoCreateUserFlag().hashCode();
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
        new org.apache.axis.description.TypeDesc(NotifyRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "NotifyRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoCreateUserFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "autoCreateUserFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
