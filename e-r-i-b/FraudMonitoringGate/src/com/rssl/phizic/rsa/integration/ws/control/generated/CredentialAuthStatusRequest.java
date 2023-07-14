/**
 * CredentialAuthStatusRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class CredentialAuthStatusRequest  extends com.rssl.phizic.rsa.integration.ws.control.generated.CredentialRequestList  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusRequest challengeQuestionAuthStatusRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthStatusRequest oobEmailAuthStatusRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthStatusRequest oobPhoneAuthStatusRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthStatusRequestData acspAuthStatusRequestData;

    public CredentialAuthStatusRequest() {
    }

    public CredentialAuthStatusRequest(
           com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusRequest challengeQuestionAuthStatusRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthStatusRequest oobEmailAuthStatusRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthStatusRequest oobPhoneAuthStatusRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthStatusRequestData acspAuthStatusRequestData) {
        this.challengeQuestionAuthStatusRequest = challengeQuestionAuthStatusRequest;
        this.oobEmailAuthStatusRequest = oobEmailAuthStatusRequest;
        this.oobPhoneAuthStatusRequest = oobPhoneAuthStatusRequest;
        this.acspAuthStatusRequestData = acspAuthStatusRequestData;
    }


    /**
     * Gets the challengeQuestionAuthStatusRequest value for this CredentialAuthStatusRequest.
     * 
     * @return challengeQuestionAuthStatusRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusRequest getChallengeQuestionAuthStatusRequest() {
        return challengeQuestionAuthStatusRequest;
    }


    /**
     * Sets the challengeQuestionAuthStatusRequest value for this CredentialAuthStatusRequest.
     * 
     * @param challengeQuestionAuthStatusRequest
     */
    public void setChallengeQuestionAuthStatusRequest(com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusRequest challengeQuestionAuthStatusRequest) {
        this.challengeQuestionAuthStatusRequest = challengeQuestionAuthStatusRequest;
    }


    /**
     * Gets the oobEmailAuthStatusRequest value for this CredentialAuthStatusRequest.
     * 
     * @return oobEmailAuthStatusRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthStatusRequest getOobEmailAuthStatusRequest() {
        return oobEmailAuthStatusRequest;
    }


    /**
     * Sets the oobEmailAuthStatusRequest value for this CredentialAuthStatusRequest.
     * 
     * @param oobEmailAuthStatusRequest
     */
    public void setOobEmailAuthStatusRequest(com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthStatusRequest oobEmailAuthStatusRequest) {
        this.oobEmailAuthStatusRequest = oobEmailAuthStatusRequest;
    }


    /**
     * Gets the oobPhoneAuthStatusRequest value for this CredentialAuthStatusRequest.
     * 
     * @return oobPhoneAuthStatusRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthStatusRequest getOobPhoneAuthStatusRequest() {
        return oobPhoneAuthStatusRequest;
    }


    /**
     * Sets the oobPhoneAuthStatusRequest value for this CredentialAuthStatusRequest.
     * 
     * @param oobPhoneAuthStatusRequest
     */
    public void setOobPhoneAuthStatusRequest(com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthStatusRequest oobPhoneAuthStatusRequest) {
        this.oobPhoneAuthStatusRequest = oobPhoneAuthStatusRequest;
    }


    /**
     * Gets the acspAuthStatusRequestData value for this CredentialAuthStatusRequest.
     * 
     * @return acspAuthStatusRequestData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthStatusRequestData getAcspAuthStatusRequestData() {
        return acspAuthStatusRequestData;
    }


    /**
     * Sets the acspAuthStatusRequestData value for this CredentialAuthStatusRequest.
     * 
     * @param acspAuthStatusRequestData
     */
    public void setAcspAuthStatusRequestData(com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthStatusRequestData acspAuthStatusRequestData) {
        this.acspAuthStatusRequestData = acspAuthStatusRequestData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialAuthStatusRequest)) return false;
        CredentialAuthStatusRequest other = (CredentialAuthStatusRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionAuthStatusRequest==null && other.getChallengeQuestionAuthStatusRequest()==null) || 
             (this.challengeQuestionAuthStatusRequest!=null &&
              this.challengeQuestionAuthStatusRequest.equals(other.getChallengeQuestionAuthStatusRequest()))) &&
            ((this.oobEmailAuthStatusRequest==null && other.getOobEmailAuthStatusRequest()==null) || 
             (this.oobEmailAuthStatusRequest!=null &&
              this.oobEmailAuthStatusRequest.equals(other.getOobEmailAuthStatusRequest()))) &&
            ((this.oobPhoneAuthStatusRequest==null && other.getOobPhoneAuthStatusRequest()==null) || 
             (this.oobPhoneAuthStatusRequest!=null &&
              this.oobPhoneAuthStatusRequest.equals(other.getOobPhoneAuthStatusRequest()))) &&
            ((this.acspAuthStatusRequestData==null && other.getAcspAuthStatusRequestData()==null) || 
             (this.acspAuthStatusRequestData!=null &&
              this.acspAuthStatusRequestData.equals(other.getAcspAuthStatusRequestData())));
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
        if (getChallengeQuestionAuthStatusRequest() != null) {
            _hashCode += getChallengeQuestionAuthStatusRequest().hashCode();
        }
        if (getOobEmailAuthStatusRequest() != null) {
            _hashCode += getOobEmailAuthStatusRequest().hashCode();
        }
        if (getOobPhoneAuthStatusRequest() != null) {
            _hashCode += getOobPhoneAuthStatusRequest().hashCode();
        }
        if (getAcspAuthStatusRequestData() != null) {
            _hashCode += getAcspAuthStatusRequestData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialAuthStatusRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthStatusRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionAuthStatusRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionAuthStatusRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionAuthStatusRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobEmailAuthStatusRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobEmailAuthStatusRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailAuthStatusRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobPhoneAuthStatusRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobPhoneAuthStatusRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneAuthStatusRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspAuthStatusRequestData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspAuthStatusRequestData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthStatusRequestData"));
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
