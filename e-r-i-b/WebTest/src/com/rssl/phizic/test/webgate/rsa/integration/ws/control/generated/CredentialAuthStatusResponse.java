/**
 * CredentialAuthStatusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class CredentialAuthStatusResponse  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialResponseList  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusResponse challengeQuestionAuthStatusResponse;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailAuthStatusResponse oobEmailAuthStatusResponse;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneAuthStatusResponse oobPhoneAuthStatusResponse;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthStatusResponseData acspAuthStatusResponseData;

    public CredentialAuthStatusResponse() {
    }

    public CredentialAuthStatusResponse(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusResponse challengeQuestionAuthStatusResponse,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailAuthStatusResponse oobEmailAuthStatusResponse,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneAuthStatusResponse oobPhoneAuthStatusResponse,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthStatusResponseData acspAuthStatusResponseData) {
        this.challengeQuestionAuthStatusResponse = challengeQuestionAuthStatusResponse;
        this.oobEmailAuthStatusResponse = oobEmailAuthStatusResponse;
        this.oobPhoneAuthStatusResponse = oobPhoneAuthStatusResponse;
        this.acspAuthStatusResponseData = acspAuthStatusResponseData;
    }


    /**
     * Gets the challengeQuestionAuthStatusResponse value for this CredentialAuthStatusResponse.
     * 
     * @return challengeQuestionAuthStatusResponse
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusResponse getChallengeQuestionAuthStatusResponse() {
        return challengeQuestionAuthStatusResponse;
    }


    /**
     * Sets the challengeQuestionAuthStatusResponse value for this CredentialAuthStatusResponse.
     * 
     * @param challengeQuestionAuthStatusResponse
     */
    public void setChallengeQuestionAuthStatusResponse(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusResponse challengeQuestionAuthStatusResponse) {
        this.challengeQuestionAuthStatusResponse = challengeQuestionAuthStatusResponse;
    }


    /**
     * Gets the oobEmailAuthStatusResponse value for this CredentialAuthStatusResponse.
     * 
     * @return oobEmailAuthStatusResponse
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailAuthStatusResponse getOobEmailAuthStatusResponse() {
        return oobEmailAuthStatusResponse;
    }


    /**
     * Sets the oobEmailAuthStatusResponse value for this CredentialAuthStatusResponse.
     * 
     * @param oobEmailAuthStatusResponse
     */
    public void setOobEmailAuthStatusResponse(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailAuthStatusResponse oobEmailAuthStatusResponse) {
        this.oobEmailAuthStatusResponse = oobEmailAuthStatusResponse;
    }


    /**
     * Gets the oobPhoneAuthStatusResponse value for this CredentialAuthStatusResponse.
     * 
     * @return oobPhoneAuthStatusResponse
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneAuthStatusResponse getOobPhoneAuthStatusResponse() {
        return oobPhoneAuthStatusResponse;
    }


    /**
     * Sets the oobPhoneAuthStatusResponse value for this CredentialAuthStatusResponse.
     * 
     * @param oobPhoneAuthStatusResponse
     */
    public void setOobPhoneAuthStatusResponse(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneAuthStatusResponse oobPhoneAuthStatusResponse) {
        this.oobPhoneAuthStatusResponse = oobPhoneAuthStatusResponse;
    }


    /**
     * Gets the acspAuthStatusResponseData value for this CredentialAuthStatusResponse.
     * 
     * @return acspAuthStatusResponseData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthStatusResponseData getAcspAuthStatusResponseData() {
        return acspAuthStatusResponseData;
    }


    /**
     * Sets the acspAuthStatusResponseData value for this CredentialAuthStatusResponse.
     * 
     * @param acspAuthStatusResponseData
     */
    public void setAcspAuthStatusResponseData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthStatusResponseData acspAuthStatusResponseData) {
        this.acspAuthStatusResponseData = acspAuthStatusResponseData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialAuthStatusResponse)) return false;
        CredentialAuthStatusResponse other = (CredentialAuthStatusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionAuthStatusResponse==null && other.getChallengeQuestionAuthStatusResponse()==null) || 
             (this.challengeQuestionAuthStatusResponse!=null &&
              this.challengeQuestionAuthStatusResponse.equals(other.getChallengeQuestionAuthStatusResponse()))) &&
            ((this.oobEmailAuthStatusResponse==null && other.getOobEmailAuthStatusResponse()==null) || 
             (this.oobEmailAuthStatusResponse!=null &&
              this.oobEmailAuthStatusResponse.equals(other.getOobEmailAuthStatusResponse()))) &&
            ((this.oobPhoneAuthStatusResponse==null && other.getOobPhoneAuthStatusResponse()==null) || 
             (this.oobPhoneAuthStatusResponse!=null &&
              this.oobPhoneAuthStatusResponse.equals(other.getOobPhoneAuthStatusResponse()))) &&
            ((this.acspAuthStatusResponseData==null && other.getAcspAuthStatusResponseData()==null) || 
             (this.acspAuthStatusResponseData!=null &&
              this.acspAuthStatusResponseData.equals(other.getAcspAuthStatusResponseData())));
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
        if (getChallengeQuestionAuthStatusResponse() != null) {
            _hashCode += getChallengeQuestionAuthStatusResponse().hashCode();
        }
        if (getOobEmailAuthStatusResponse() != null) {
            _hashCode += getOobEmailAuthStatusResponse().hashCode();
        }
        if (getOobPhoneAuthStatusResponse() != null) {
            _hashCode += getOobPhoneAuthStatusResponse().hashCode();
        }
        if (getAcspAuthStatusResponseData() != null) {
            _hashCode += getAcspAuthStatusResponseData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialAuthStatusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthStatusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionAuthStatusResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionAuthStatusResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionAuthStatusResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobEmailAuthStatusResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobEmailAuthStatusResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailAuthStatusResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobPhoneAuthStatusResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobPhoneAuthStatusResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneAuthStatusResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspAuthStatusResponseData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspAuthStatusResponseData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthStatusResponseData"));
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
