/**
 * AuthenticateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class AuthenticateResponse  extends com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList credentialAuthResultList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse;

    private com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[] requiredCredentialList;

    public AuthenticateResponse() {
    }

    public AuthenticateResponse(
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceResult deviceResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.StatusHeader statusHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList credentialAuthResultList,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse,
           com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[] requiredCredentialList) {
        super(
            deviceResult,
            identificationData,
            messageHeader,
            statusHeader);
        this.credentialAuthResultList = credentialAuthResultList;
        this.deviceManagementResponse = deviceManagementResponse;
        this.requiredCredentialList = requiredCredentialList;
    }


    /**
     * Gets the credentialAuthResultList value for this AuthenticateResponse.
     * 
     * @return credentialAuthResultList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList getCredentialAuthResultList() {
        return credentialAuthResultList;
    }


    /**
     * Sets the credentialAuthResultList value for this AuthenticateResponse.
     * 
     * @param credentialAuthResultList
     */
    public void setCredentialAuthResultList(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList credentialAuthResultList) {
        this.credentialAuthResultList = credentialAuthResultList;
    }


    /**
     * Gets the deviceManagementResponse value for this AuthenticateResponse.
     * 
     * @return deviceManagementResponse
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload getDeviceManagementResponse() {
        return deviceManagementResponse;
    }


    /**
     * Sets the deviceManagementResponse value for this AuthenticateResponse.
     * 
     * @param deviceManagementResponse
     */
    public void setDeviceManagementResponse(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse) {
        this.deviceManagementResponse = deviceManagementResponse;
    }


    /**
     * Gets the requiredCredentialList value for this AuthenticateResponse.
     * 
     * @return requiredCredentialList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[] getRequiredCredentialList() {
        return requiredCredentialList;
    }


    /**
     * Sets the requiredCredentialList value for this AuthenticateResponse.
     * 
     * @param requiredCredentialList
     */
    public void setRequiredCredentialList(com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[] requiredCredentialList) {
        this.requiredCredentialList = requiredCredentialList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AuthenticateResponse)) return false;
        AuthenticateResponse other = (AuthenticateResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.credentialAuthResultList==null && other.getCredentialAuthResultList()==null) || 
             (this.credentialAuthResultList!=null &&
              this.credentialAuthResultList.equals(other.getCredentialAuthResultList()))) &&
            ((this.deviceManagementResponse==null && other.getDeviceManagementResponse()==null) || 
             (this.deviceManagementResponse!=null &&
              this.deviceManagementResponse.equals(other.getDeviceManagementResponse()))) &&
            ((this.requiredCredentialList==null && other.getRequiredCredentialList()==null) || 
             (this.requiredCredentialList!=null &&
              java.util.Arrays.equals(this.requiredCredentialList, other.getRequiredCredentialList())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuthenticateResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticateResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
