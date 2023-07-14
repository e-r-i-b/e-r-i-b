/**
 * OobPhoneManagementRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This type defines the OOB Phone MANAGEMENT Request Payload
 */
public class OobPhoneManagementRequest  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.CredentialProvisioningStatus credentialProvisioningStatus;

    private com.rssl.phizic.rsa.integration.ws.control.generated.PhoneManagementRequestPayload payload;

    public OobPhoneManagementRequest() {
    }

    public OobPhoneManagementRequest(
           com.rssl.phizic.rsa.integration.ws.control.generated.CredentialProvisioningStatus credentialProvisioningStatus,
           com.rssl.phizic.rsa.integration.ws.control.generated.PhoneManagementRequestPayload payload) {
           this.credentialProvisioningStatus = credentialProvisioningStatus;
           this.payload = payload;
    }


    /**
     * Gets the credentialProvisioningStatus value for this OobPhoneManagementRequest.
     * 
     * @return credentialProvisioningStatus
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CredentialProvisioningStatus getCredentialProvisioningStatus() {
        return credentialProvisioningStatus;
    }


    /**
     * Sets the credentialProvisioningStatus value for this OobPhoneManagementRequest.
     * 
     * @param credentialProvisioningStatus
     */
    public void setCredentialProvisioningStatus(com.rssl.phizic.rsa.integration.ws.control.generated.CredentialProvisioningStatus credentialProvisioningStatus) {
        this.credentialProvisioningStatus = credentialProvisioningStatus;
    }


    /**
     * Gets the payload value for this OobPhoneManagementRequest.
     * 
     * @return payload
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.PhoneManagementRequestPayload getPayload() {
        return payload;
    }


    /**
     * Sets the payload value for this OobPhoneManagementRequest.
     * 
     * @param payload
     */
    public void setPayload(com.rssl.phizic.rsa.integration.ws.control.generated.PhoneManagementRequestPayload payload) {
        this.payload = payload;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OobPhoneManagementRequest)) return false;
        OobPhoneManagementRequest other = (OobPhoneManagementRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.credentialProvisioningStatus==null && other.getCredentialProvisioningStatus()==null) || 
             (this.credentialProvisioningStatus!=null &&
              this.credentialProvisioningStatus.equals(other.getCredentialProvisioningStatus()))) &&
            ((this.payload==null && other.getPayload()==null) || 
             (this.payload!=null &&
              this.payload.equals(other.getPayload())));
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
        if (getCredentialProvisioningStatus() != null) {
            _hashCode += getCredentialProvisioningStatus().hashCode();
        }
        if (getPayload() != null) {
            _hashCode += getPayload().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OobPhoneManagementRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneManagementRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialProvisioningStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialProvisioningStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialProvisioningStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payload");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "payload"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PhoneManagementRequestPayload"));
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