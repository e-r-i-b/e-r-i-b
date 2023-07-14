/**
 * QueryAuthStatusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class QueryAuthStatusResponse  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GenericResponse  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialAuthStatusResponse credentialAuthStatusResponse;

    public QueryAuthStatusResponse() {
    }

    public QueryAuthStatusResponse(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceResult deviceResult,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData identificationData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.MessageHeader messageHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.StatusHeader statusHeader,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialAuthStatusResponse credentialAuthStatusResponse) {
        super(
            deviceResult,
            identificationData,
            messageHeader,
            statusHeader);
        this.credentialAuthStatusResponse = credentialAuthStatusResponse;
    }


    /**
     * Gets the credentialAuthStatusResponse value for this QueryAuthStatusResponse.
     * 
     * @return credentialAuthStatusResponse
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialAuthStatusResponse getCredentialAuthStatusResponse() {
        return credentialAuthStatusResponse;
    }


    /**
     * Sets the credentialAuthStatusResponse value for this QueryAuthStatusResponse.
     * 
     * @param credentialAuthStatusResponse
     */
    public void setCredentialAuthStatusResponse(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialAuthStatusResponse credentialAuthStatusResponse) {
        this.credentialAuthStatusResponse = credentialAuthStatusResponse;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryAuthStatusResponse)) return false;
        QueryAuthStatusResponse other = (QueryAuthStatusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.credentialAuthStatusResponse==null && other.getCredentialAuthStatusResponse()==null) || 
             (this.credentialAuthStatusResponse!=null &&
              this.credentialAuthStatusResponse.equals(other.getCredentialAuthStatusResponse())));
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
        if (getCredentialAuthStatusResponse() != null) {
            _hashCode += getCredentialAuthStatusResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryAuthStatusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryAuthStatusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentialAuthStatusResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credentialAuthStatusResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthStatusResponse"));
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
