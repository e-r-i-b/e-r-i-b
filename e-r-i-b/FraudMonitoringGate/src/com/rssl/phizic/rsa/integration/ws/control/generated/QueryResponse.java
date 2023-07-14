/**
 * QueryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class QueryResponse  extends com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse  implements java.io.Serializable {
    private java.lang.String[] browsableGroupNames;

    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Credential[] systemCredentials;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Credential[] userCredentials;

    private com.rssl.phizic.rsa.integration.ws.control.generated.UserPreference userPreference;

    public QueryResponse() {
    }

    public QueryResponse(
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceResult deviceResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.rsa.integration.ws.control.generated.StatusHeader statusHeader,
           java.lang.String[] browsableGroupNames,
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse,
           com.rssl.phizic.rsa.integration.ws.control.generated.Credential[] systemCredentials,
           com.rssl.phizic.rsa.integration.ws.control.generated.Credential[] userCredentials,
           com.rssl.phizic.rsa.integration.ws.control.generated.UserPreference userPreference) {
        super(
            deviceResult,
            identificationData,
            messageHeader,
            statusHeader);
        this.browsableGroupNames = browsableGroupNames;
        this.credentialManagementResponseList = credentialManagementResponseList;
        this.deviceManagementResponse = deviceManagementResponse;
        this.systemCredentials = systemCredentials;
        this.userCredentials = userCredentials;
        this.userPreference = userPreference;
    }


    /**
     * Gets the browsableGroupNames value for this QueryResponse.
     * 
     * @return browsableGroupNames
     */
    public java.lang.String[] getBrowsableGroupNames() {
        return browsableGroupNames;
    }


    /**
     * Sets the browsableGroupNames value for this QueryResponse.
     * 
     * @param browsableGroupNames
     */
    public void setBrowsableGroupNames(java.lang.String[] browsableGroupNames) {
        this.browsableGroupNames = browsableGroupNames;
    }

    public java.lang.String getBrowsableGroupNames(int i) {
        return this.browsableGroupNames[i];
    }

    public void setBrowsableGroupNames(int i, java.lang.String _value) {
        this.browsableGroupNames[i] = _value;
    }


    /**
     * Gets the credentialManagementResponseList value for this QueryResponse.
     * 
     * @return credentialManagementResponseList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialManagementResponseList getCredentialManagementResponseList() {
        return credentialManagementResponseList;
    }


    /**
     * Sets the credentialManagementResponseList value for this QueryResponse.
     * 
     * @param credentialManagementResponseList
     */
    public void setCredentialManagementResponseList(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialManagementResponseList credentialManagementResponseList) {
        this.credentialManagementResponseList = credentialManagementResponseList;
    }


    /**
     * Gets the deviceManagementResponse value for this QueryResponse.
     * 
     * @return deviceManagementResponse
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload getDeviceManagementResponse() {
        return deviceManagementResponse;
    }


    /**
     * Sets the deviceManagementResponse value for this QueryResponse.
     * 
     * @param deviceManagementResponse
     */
    public void setDeviceManagementResponse(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload deviceManagementResponse) {
        this.deviceManagementResponse = deviceManagementResponse;
    }


    /**
     * Gets the systemCredentials value for this QueryResponse.
     * 
     * @return systemCredentials
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Credential[] getSystemCredentials() {
        return systemCredentials;
    }


    /**
     * Sets the systemCredentials value for this QueryResponse.
     * 
     * @param systemCredentials
     */
    public void setSystemCredentials(com.rssl.phizic.rsa.integration.ws.control.generated.Credential[] systemCredentials) {
        this.systemCredentials = systemCredentials;
    }


    /**
     * Gets the userCredentials value for this QueryResponse.
     * 
     * @return userCredentials
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Credential[] getUserCredentials() {
        return userCredentials;
    }


    /**
     * Sets the userCredentials value for this QueryResponse.
     * 
     * @param userCredentials
     */
    public void setUserCredentials(com.rssl.phizic.rsa.integration.ws.control.generated.Credential[] userCredentials) {
        this.userCredentials = userCredentials;
    }


    /**
     * Gets the userPreference value for this QueryResponse.
     * 
     * @return userPreference
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.UserPreference getUserPreference() {
        return userPreference;
    }


    /**
     * Sets the userPreference value for this QueryResponse.
     * 
     * @param userPreference
     */
    public void setUserPreference(com.rssl.phizic.rsa.integration.ws.control.generated.UserPreference userPreference) {
        this.userPreference = userPreference;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryResponse)) return false;
        QueryResponse other = (QueryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.browsableGroupNames==null && other.getBrowsableGroupNames()==null) || 
             (this.browsableGroupNames!=null &&
              java.util.Arrays.equals(this.browsableGroupNames, other.getBrowsableGroupNames()))) &&
            ((this.credentialManagementResponseList==null && other.getCredentialManagementResponseList()==null) || 
             (this.credentialManagementResponseList!=null &&
              this.credentialManagementResponseList.equals(other.getCredentialManagementResponseList()))) &&
            ((this.deviceManagementResponse==null && other.getDeviceManagementResponse()==null) || 
             (this.deviceManagementResponse!=null &&
              this.deviceManagementResponse.equals(other.getDeviceManagementResponse()))) &&
            ((this.systemCredentials==null && other.getSystemCredentials()==null) || 
             (this.systemCredentials!=null &&
              java.util.Arrays.equals(this.systemCredentials, other.getSystemCredentials()))) &&
            ((this.userCredentials==null && other.getUserCredentials()==null) || 
             (this.userCredentials!=null &&
              java.util.Arrays.equals(this.userCredentials, other.getUserCredentials()))) &&
            ((this.userPreference==null && other.getUserPreference()==null) || 
             (this.userPreference!=null &&
              this.userPreference.equals(other.getUserPreference())));
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
        if (getBrowsableGroupNames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBrowsableGroupNames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBrowsableGroupNames(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCredentialManagementResponseList() != null) {
            _hashCode += getCredentialManagementResponseList().hashCode();
        }
        if (getDeviceManagementResponse() != null) {
            _hashCode += getDeviceManagementResponse().hashCode();
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
        if (getUserCredentials() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserCredentials());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUserCredentials(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUserPreference() != null) {
            _hashCode += getUserPreference().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("browsableGroupNames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "browsableGroupNames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("systemCredentials");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "systemCredentials"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Credential"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credential"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userCredentials");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userCredentials"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Credential"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credential"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userPreference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userPreference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserPreference"));
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
