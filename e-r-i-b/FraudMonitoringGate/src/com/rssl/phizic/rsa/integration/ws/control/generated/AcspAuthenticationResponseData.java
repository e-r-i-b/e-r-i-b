/**
 * AcspAuthenticationResponseData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This type defines the Credential Auth Result Payload
 */
public class AcspAuthenticationResponseData  implements java.io.Serializable {
    private java.lang.String acspAccountId;

    private com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus;

    private com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponse payload;

    public AcspAuthenticationResponseData() {
    }

    public AcspAuthenticationResponseData(
           java.lang.String acspAccountId,
           com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus,
           com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponse payload) {
           this.acspAccountId = acspAccountId;
           this.callStatus = callStatus;
           this.payload = payload;
    }


    /**
     * Gets the acspAccountId value for this AcspAuthenticationResponseData.
     * 
     * @return acspAccountId
     */
    public java.lang.String getAcspAccountId() {
        return acspAccountId;
    }


    /**
     * Sets the acspAccountId value for this AcspAuthenticationResponseData.
     * 
     * @param acspAccountId
     */
    public void setAcspAccountId(java.lang.String acspAccountId) {
        this.acspAccountId = acspAccountId;
    }


    /**
     * Gets the callStatus value for this AcspAuthenticationResponseData.
     * 
     * @return callStatus
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus getCallStatus() {
        return callStatus;
    }


    /**
     * Sets the callStatus value for this AcspAuthenticationResponseData.
     * 
     * @param callStatus
     */
    public void setCallStatus(com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus) {
        this.callStatus = callStatus;
    }


    /**
     * Gets the payload value for this AcspAuthenticationResponseData.
     * 
     * @return payload
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponse getPayload() {
        return payload;
    }


    /**
     * Sets the payload value for this AcspAuthenticationResponseData.
     * 
     * @param payload
     */
    public void setPayload(com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponse payload) {
        this.payload = payload;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AcspAuthenticationResponseData)) return false;
        AcspAuthenticationResponseData other = (AcspAuthenticationResponseData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acspAccountId==null && other.getAcspAccountId()==null) || 
             (this.acspAccountId!=null &&
              this.acspAccountId.equals(other.getAcspAccountId()))) &&
            ((this.callStatus==null && other.getCallStatus()==null) || 
             (this.callStatus!=null &&
              this.callStatus.equals(other.getCallStatus()))) &&
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
        if (getAcspAccountId() != null) {
            _hashCode += getAcspAccountId().hashCode();
        }
        if (getCallStatus() != null) {
            _hashCode += getCallStatus().hashCode();
        }
        if (getPayload() != null) {
            _hashCode += getPayload().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AcspAuthenticationResponseData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthenticationResponseData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspAccountId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspAccountId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("payload");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "payload"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthenticationResponse"));
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
