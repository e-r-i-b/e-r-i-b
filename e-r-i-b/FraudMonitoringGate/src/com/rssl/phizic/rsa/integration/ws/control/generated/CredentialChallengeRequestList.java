/**
 * CredentialChallengeRequestList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class CredentialChallengeRequestList  extends com.rssl.phizic.rsa.integration.ws.control.generated.CredentialRequestList  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionChallengeRequest challengeQuestionChallengeRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailChallengeRequest oobEmailChallengeRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneChallengeRequest oobPhoneChallengeRequest;

    private com.rssl.phizic.rsa.integration.ws.control.generated.AcspChallengeRequestData acspChallengeRequestData;

    public CredentialChallengeRequestList() {
    }

    public CredentialChallengeRequestList(
           com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionChallengeRequest challengeQuestionChallengeRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailChallengeRequest oobEmailChallengeRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneChallengeRequest oobPhoneChallengeRequest,
           com.rssl.phizic.rsa.integration.ws.control.generated.AcspChallengeRequestData acspChallengeRequestData) {
        this.challengeQuestionChallengeRequest = challengeQuestionChallengeRequest;
        this.oobEmailChallengeRequest = oobEmailChallengeRequest;
        this.oobPhoneChallengeRequest = oobPhoneChallengeRequest;
        this.acspChallengeRequestData = acspChallengeRequestData;
    }


    /**
     * Gets the challengeQuestionChallengeRequest value for this CredentialChallengeRequestList.
     * 
     * @return challengeQuestionChallengeRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionChallengeRequest getChallengeQuestionChallengeRequest() {
        return challengeQuestionChallengeRequest;
    }


    /**
     * Sets the challengeQuestionChallengeRequest value for this CredentialChallengeRequestList.
     * 
     * @param challengeQuestionChallengeRequest
     */
    public void setChallengeQuestionChallengeRequest(com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionChallengeRequest challengeQuestionChallengeRequest) {
        this.challengeQuestionChallengeRequest = challengeQuestionChallengeRequest;
    }


    /**
     * Gets the oobEmailChallengeRequest value for this CredentialChallengeRequestList.
     * 
     * @return oobEmailChallengeRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailChallengeRequest getOobEmailChallengeRequest() {
        return oobEmailChallengeRequest;
    }


    /**
     * Sets the oobEmailChallengeRequest value for this CredentialChallengeRequestList.
     * 
     * @param oobEmailChallengeRequest
     */
    public void setOobEmailChallengeRequest(com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailChallengeRequest oobEmailChallengeRequest) {
        this.oobEmailChallengeRequest = oobEmailChallengeRequest;
    }


    /**
     * Gets the oobPhoneChallengeRequest value for this CredentialChallengeRequestList.
     * 
     * @return oobPhoneChallengeRequest
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneChallengeRequest getOobPhoneChallengeRequest() {
        return oobPhoneChallengeRequest;
    }


    /**
     * Sets the oobPhoneChallengeRequest value for this CredentialChallengeRequestList.
     * 
     * @param oobPhoneChallengeRequest
     */
    public void setOobPhoneChallengeRequest(com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneChallengeRequest oobPhoneChallengeRequest) {
        this.oobPhoneChallengeRequest = oobPhoneChallengeRequest;
    }


    /**
     * Gets the acspChallengeRequestData value for this CredentialChallengeRequestList.
     * 
     * @return acspChallengeRequestData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AcspChallengeRequestData getAcspChallengeRequestData() {
        return acspChallengeRequestData;
    }


    /**
     * Sets the acspChallengeRequestData value for this CredentialChallengeRequestList.
     * 
     * @param acspChallengeRequestData
     */
    public void setAcspChallengeRequestData(com.rssl.phizic.rsa.integration.ws.control.generated.AcspChallengeRequestData acspChallengeRequestData) {
        this.acspChallengeRequestData = acspChallengeRequestData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialChallengeRequestList)) return false;
        CredentialChallengeRequestList other = (CredentialChallengeRequestList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionChallengeRequest==null && other.getChallengeQuestionChallengeRequest()==null) || 
             (this.challengeQuestionChallengeRequest!=null &&
              this.challengeQuestionChallengeRequest.equals(other.getChallengeQuestionChallengeRequest()))) &&
            ((this.oobEmailChallengeRequest==null && other.getOobEmailChallengeRequest()==null) || 
             (this.oobEmailChallengeRequest!=null &&
              this.oobEmailChallengeRequest.equals(other.getOobEmailChallengeRequest()))) &&
            ((this.oobPhoneChallengeRequest==null && other.getOobPhoneChallengeRequest()==null) || 
             (this.oobPhoneChallengeRequest!=null &&
              this.oobPhoneChallengeRequest.equals(other.getOobPhoneChallengeRequest()))) &&
            ((this.acspChallengeRequestData==null && other.getAcspChallengeRequestData()==null) || 
             (this.acspChallengeRequestData!=null &&
              this.acspChallengeRequestData.equals(other.getAcspChallengeRequestData())));
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
        if (getChallengeQuestionChallengeRequest() != null) {
            _hashCode += getChallengeQuestionChallengeRequest().hashCode();
        }
        if (getOobEmailChallengeRequest() != null) {
            _hashCode += getOobEmailChallengeRequest().hashCode();
        }
        if (getOobPhoneChallengeRequest() != null) {
            _hashCode += getOobPhoneChallengeRequest().hashCode();
        }
        if (getAcspChallengeRequestData() != null) {
            _hashCode += getAcspChallengeRequestData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialChallengeRequestList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialChallengeRequestList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionChallengeRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionChallengeRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionChallengeRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobEmailChallengeRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobEmailChallengeRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailChallengeRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobPhoneChallengeRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobPhoneChallengeRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneChallengeRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspChallengeRequestData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspChallengeRequestData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspChallengeRequestData"));
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
