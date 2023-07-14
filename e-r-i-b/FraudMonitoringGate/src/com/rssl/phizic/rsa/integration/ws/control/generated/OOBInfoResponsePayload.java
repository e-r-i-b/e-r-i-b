/**
 * OOBInfoResponsePayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines the OOB Info Response payload for authenticate, queryAuthStatus,
 * and analyze
 */
public class OOBInfoResponsePayload  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationResult authenticationResult;

    private com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus;

    private java.lang.String channelStatus;

    private java.lang.String reason;

    private java.lang.String token;

    public OOBInfoResponsePayload() {
    }

    public OOBInfoResponsePayload(
           com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationResult authenticationResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus,
           java.lang.String channelStatus,
           java.lang.String reason,
           java.lang.String token) {
           this.authenticationResult = authenticationResult;
           this.callStatus = callStatus;
           this.channelStatus = channelStatus;
           this.reason = reason;
           this.token = token;
    }


    /**
     * Gets the authenticationResult value for this OOBInfoResponsePayload.
     * 
     * @return authenticationResult
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationResult getAuthenticationResult() {
        return authenticationResult;
    }


    /**
     * Sets the authenticationResult value for this OOBInfoResponsePayload.
     * 
     * @param authenticationResult
     */
    public void setAuthenticationResult(com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationResult authenticationResult) {
        this.authenticationResult = authenticationResult;
    }


    /**
     * Gets the callStatus value for this OOBInfoResponsePayload.
     * 
     * @return callStatus
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus getCallStatus() {
        return callStatus;
    }


    /**
     * Sets the callStatus value for this OOBInfoResponsePayload.
     * 
     * @param callStatus
     */
    public void setCallStatus(com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus) {
        this.callStatus = callStatus;
    }


    /**
     * Gets the channelStatus value for this OOBInfoResponsePayload.
     * 
     * @return channelStatus
     */
    public java.lang.String getChannelStatus() {
        return channelStatus;
    }


    /**
     * Sets the channelStatus value for this OOBInfoResponsePayload.
     * 
     * @param channelStatus
     */
    public void setChannelStatus(java.lang.String channelStatus) {
        this.channelStatus = channelStatus;
    }


    /**
     * Gets the reason value for this OOBInfoResponsePayload.
     * 
     * @return reason
     */
    public java.lang.String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this OOBInfoResponsePayload.
     * 
     * @param reason
     */
    public void setReason(java.lang.String reason) {
        this.reason = reason;
    }


    /**
     * Gets the token value for this OOBInfoResponsePayload.
     * 
     * @return token
     */
    public java.lang.String getToken() {
        return token;
    }


    /**
     * Sets the token value for this OOBInfoResponsePayload.
     * 
     * @param token
     */
    public void setToken(java.lang.String token) {
        this.token = token;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OOBInfoResponsePayload)) return false;
        OOBInfoResponsePayload other = (OOBInfoResponsePayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authenticationResult==null && other.getAuthenticationResult()==null) || 
             (this.authenticationResult!=null &&
              this.authenticationResult.equals(other.getAuthenticationResult()))) &&
            ((this.callStatus==null && other.getCallStatus()==null) || 
             (this.callStatus!=null &&
              this.callStatus.equals(other.getCallStatus()))) &&
            ((this.channelStatus==null && other.getChannelStatus()==null) || 
             (this.channelStatus!=null &&
              this.channelStatus.equals(other.getChannelStatus()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken())));
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
        if (getAuthenticationResult() != null) {
            _hashCode += getAuthenticationResult().hashCode();
        }
        if (getCallStatus() != null) {
            _hashCode += getCallStatus().hashCode();
        }
        if (getChannelStatus() != null) {
            _hashCode += getChannelStatus().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OOBInfoResponsePayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBInfoResponsePayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticationResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "authenticationResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticationResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "callStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CallStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "channelStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "token"));
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
