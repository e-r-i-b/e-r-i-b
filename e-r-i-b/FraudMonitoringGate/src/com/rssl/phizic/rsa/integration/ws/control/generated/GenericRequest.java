/**
 * GenericRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines the contents of an abstract Generic Request
 */
public abstract class GenericRequest  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.GenericActionTypeList actionTypeList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ConfigurationHeader configurationHeader;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest deviceRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData;

    private com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader;

    private com.rssl.phizic.rsa.integration.ws.control.generated.SecurityHeader securityHeader;

    public GenericRequest() {
    }

    public GenericRequest(
           com.rssl.phizic.rsa.integration.ws.control.generated.GenericActionTypeList actionTypeList,
           com.rssl.phizic.rsa.integration.ws.control.generated.ConfigurationHeader configurationHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest deviceRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.SecurityHeader securityHeader) {
           this.actionTypeList = actionTypeList;
           this.configurationHeader = configurationHeader;
           this.deviceRequest = deviceRequest;
           this.identificationData = identificationData;
           this.messageHeader = messageHeader;
           this.securityHeader = securityHeader;
    }


    /**
     * Gets the actionTypeList value for this GenericRequest.
     * 
     * @return actionTypeList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.GenericActionTypeList getActionTypeList() {
        return actionTypeList;
    }


    /**
     * Sets the actionTypeList value for this GenericRequest.
     * 
     * @param actionTypeList
     */
    public void setActionTypeList(com.rssl.phizic.rsa.integration.ws.control.generated.GenericActionTypeList actionTypeList) {
        this.actionTypeList = actionTypeList;
    }


    /**
     * Gets the configurationHeader value for this GenericRequest.
     * 
     * @return configurationHeader
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ConfigurationHeader getConfigurationHeader() {
        return configurationHeader;
    }


    /**
     * Sets the configurationHeader value for this GenericRequest.
     * 
     * @param configurationHeader
     */
    public void setConfigurationHeader(com.rssl.phizic.rsa.integration.ws.control.generated.ConfigurationHeader configurationHeader) {
        this.configurationHeader = configurationHeader;
    }


    /**
     * Gets the deviceRequest value for this GenericRequest.
     * 
     * @return deviceRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest getDeviceRequest() {
        return deviceRequest;
    }


    /**
     * Sets the deviceRequest value for this GenericRequest.
     * 
     * @param deviceRequest
     */
    public void setDeviceRequest(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest deviceRequest) {
        this.deviceRequest = deviceRequest;
    }


    /**
     * Gets the identificationData value for this GenericRequest.
     * 
     * @return identificationData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData getIdentificationData() {
        return identificationData;
    }


    /**
     * Sets the identificationData value for this GenericRequest.
     * 
     * @param identificationData
     */
    public void setIdentificationData(com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData) {
        this.identificationData = identificationData;
    }


    /**
     * Gets the messageHeader value for this GenericRequest.
     * 
     * @return messageHeader
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader getMessageHeader() {
        return messageHeader;
    }


    /**
     * Sets the messageHeader value for this GenericRequest.
     * 
     * @param messageHeader
     */
    public void setMessageHeader(com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }


    /**
     * Gets the securityHeader value for this GenericRequest.
     * 
     * @return securityHeader
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.SecurityHeader getSecurityHeader() {
        return securityHeader;
    }


    /**
     * Sets the securityHeader value for this GenericRequest.
     * 
     * @param securityHeader
     */
    public void setSecurityHeader(com.rssl.phizic.rsa.integration.ws.control.generated.SecurityHeader securityHeader) {
        this.securityHeader = securityHeader;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GenericRequest)) return false;
        GenericRequest other = (GenericRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actionTypeList==null && other.getActionTypeList()==null) || 
             (this.actionTypeList!=null &&
              this.actionTypeList.equals(other.getActionTypeList()))) &&
            ((this.configurationHeader==null && other.getConfigurationHeader()==null) || 
             (this.configurationHeader!=null &&
              this.configurationHeader.equals(other.getConfigurationHeader()))) &&
            ((this.deviceRequest==null && other.getDeviceRequest()==null) || 
             (this.deviceRequest!=null &&
              this.deviceRequest.equals(other.getDeviceRequest()))) &&
            ((this.identificationData==null && other.getIdentificationData()==null) || 
             (this.identificationData!=null &&
              this.identificationData.equals(other.getIdentificationData()))) &&
            ((this.messageHeader==null && other.getMessageHeader()==null) || 
             (this.messageHeader!=null &&
              this.messageHeader.equals(other.getMessageHeader()))) &&
            ((this.securityHeader==null && other.getSecurityHeader()==null) || 
             (this.securityHeader!=null &&
              this.securityHeader.equals(other.getSecurityHeader())));
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
        if (getActionTypeList() != null) {
            _hashCode += getActionTypeList().hashCode();
        }
        if (getConfigurationHeader() != null) {
            _hashCode += getConfigurationHeader().hashCode();
        }
        if (getDeviceRequest() != null) {
            _hashCode += getDeviceRequest().hashCode();
        }
        if (getIdentificationData() != null) {
            _hashCode += getIdentificationData().hashCode();
        }
        if (getMessageHeader() != null) {
            _hashCode += getMessageHeader().hashCode();
        }
        if (getSecurityHeader() != null) {
            _hashCode += getSecurityHeader().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GenericRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GenericRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionTypeList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "actionTypeList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GenericActionTypeList"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configurationHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "configurationHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ConfigurationHeader"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceRequest"));
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
        elemField.setFieldName("securityHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "securityHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SecurityHeader"));
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
