/**
 * QueryAuthStatusRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class QueryAuthStatusRequest  extends com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthStatusRequest credentialAuthStatusRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator;

    private java.lang.String clientDefinedChannelIndicator;

    public QueryAuthStatusRequest() {
    }

    public QueryAuthStatusRequest(
           com.rssl.phizic.rsa.integration.ws.control.generated.GenericActionTypeList actionTypeList,
           com.rssl.phizic.rsa.integration.ws.control.generated.ConfigurationHeader configurationHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest deviceRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.SecurityHeader securityHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthStatusRequest credentialAuthStatusRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator,
           java.lang.String clientDefinedChannelIndicator) {
        super(
            actionTypeList,
            configurationHeader,
            deviceRequest,
            identificationData,
            messageHeader,
            securityHeader);
        this.credentialAuthStatusRequest = credentialAuthStatusRequest;
        this.channelIndicator = channelIndicator;
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }


    /**
     * Gets the credentialAuthStatusRequest value for this QueryAuthStatusRequest.
     * 
     * @return credentialAuthStatusRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthStatusRequest getCredentialAuthStatusRequest() {
        return credentialAuthStatusRequest;
    }


    /**
     * Sets the credentialAuthStatusRequest value for this QueryAuthStatusRequest.
     * 
     * @param credentialAuthStatusRequest
     */
    public void setCredentialAuthStatusRequest(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthStatusRequest credentialAuthStatusRequest) {
        this.credentialAuthStatusRequest = credentialAuthStatusRequest;
    }


    /**
     * Gets the channelIndicator value for this QueryAuthStatusRequest.
     * 
     * @return channelIndicator
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType getChannelIndicator() {
        return channelIndicator;
    }


    /**
     * Sets the channelIndicator value for this QueryAuthStatusRequest.
     * 
     * @param channelIndicator
     */
    public void setChannelIndicator(com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType channelIndicator) {
        this.channelIndicator = channelIndicator;
    }


    /**
     * Gets the clientDefinedChannelIndicator value for this QueryAuthStatusRequest.
     * 
     * @return clientDefinedChannelIndicator
     */
    public java.lang.String getClientDefinedChannelIndicator() {
        return clientDefinedChannelIndicator;
    }


    /**
     * Sets the clientDefinedChannelIndicator value for this QueryAuthStatusRequest.
     * 
     * @param clientDefinedChannelIndicator
     */
    public void setClientDefinedChannelIndicator(java.lang.String clientDefinedChannelIndicator) {
        this.clientDefinedChannelIndicator = clientDefinedChannelIndicator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryAuthStatusRequest)) return false;
        QueryAuthStatusRequest other = (QueryAuthStatusRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.credentialAuthStatusRequest==null && other.getCredentialAuthStatusRequest()==null) || 
             (this.credentialAuthStatusRequest!=null &&
              this.credentialAuthStatusRequest.equals(other.getCredentialAuthStatusRequest()))) &&
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
        if (getCredentialAuthStatusRequest() != null) {
            _hashCode += getCredentialAuthStatusRequest().hashCode();
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
        new org.apache.axis.description.TypeDesc(QueryAuthStatusRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryAuthStatusRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialAuthStatusRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialAuthStatusRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthStatusRequest"));
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
