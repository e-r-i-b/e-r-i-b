/**
 * CredentialAuthResultList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class CredentialAuthResultList  extends com.rssl.phizic.rsa.integration.ws.control.generated.CredentialResponseList  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthResult challengeQuestionAuthResult;

    private com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthResult oobEmailAuthResult;

    private com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthResult oobPhoneAuthResult;

    private com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponseData acspAuthenticationResponseData;

    public CredentialAuthResultList() {
    }

    public CredentialAuthResultList(
           com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthResult challengeQuestionAuthResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthResult oobEmailAuthResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthResult oobPhoneAuthResult,
           com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponseData acspAuthenticationResponseData) {
        this.challengeQuestionAuthResult = challengeQuestionAuthResult;
        this.oobEmailAuthResult = oobEmailAuthResult;
        this.oobPhoneAuthResult = oobPhoneAuthResult;
        this.acspAuthenticationResponseData = acspAuthenticationResponseData;
    }


    /**
     * Gets the challengeQuestionAuthResult value for this CredentialAuthResultList.
     * 
     * @return challengeQuestionAuthResult
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthResult getChallengeQuestionAuthResult() {
        return challengeQuestionAuthResult;
    }


    /**
     * Sets the challengeQuestionAuthResult value for this CredentialAuthResultList.
     * 
     * @param challengeQuestionAuthResult
     */
    public void setChallengeQuestionAuthResult(com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthResult challengeQuestionAuthResult) {
        this.challengeQuestionAuthResult = challengeQuestionAuthResult;
    }


    /**
     * Gets the oobEmailAuthResult value for this CredentialAuthResultList.
     * 
     * @return oobEmailAuthResult
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthResult getOobEmailAuthResult() {
        return oobEmailAuthResult;
    }


    /**
     * Sets the oobEmailAuthResult value for this CredentialAuthResultList.
     * 
     * @param oobEmailAuthResult
     */
    public void setOobEmailAuthResult(com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthResult oobEmailAuthResult) {
        this.oobEmailAuthResult = oobEmailAuthResult;
    }


    /**
     * Gets the oobPhoneAuthResult value for this CredentialAuthResultList.
     * 
     * @return oobPhoneAuthResult
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthResult getOobPhoneAuthResult() {
        return oobPhoneAuthResult;
    }


    /**
     * Sets the oobPhoneAuthResult value for this CredentialAuthResultList.
     * 
     * @param oobPhoneAuthResult
     */
    public void setOobPhoneAuthResult(com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthResult oobPhoneAuthResult) {
        this.oobPhoneAuthResult = oobPhoneAuthResult;
    }


    /**
     * Gets the acspAuthenticationResponseData value for this CredentialAuthResultList.
     * 
     * @return acspAuthenticationResponseData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponseData getAcspAuthenticationResponseData() {
        return acspAuthenticationResponseData;
    }


    /**
     * Sets the acspAuthenticationResponseData value for this CredentialAuthResultList.
     * 
     * @param acspAuthenticationResponseData
     */
    public void setAcspAuthenticationResponseData(com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponseData acspAuthenticationResponseData) {
        this.acspAuthenticationResponseData = acspAuthenticationResponseData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialAuthResultList)) return false;
        CredentialAuthResultList other = (CredentialAuthResultList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionAuthResult==null && other.getChallengeQuestionAuthResult()==null) || 
             (this.challengeQuestionAuthResult!=null &&
              this.challengeQuestionAuthResult.equals(other.getChallengeQuestionAuthResult()))) &&
            ((this.oobEmailAuthResult==null && other.getOobEmailAuthResult()==null) || 
             (this.oobEmailAuthResult!=null &&
              this.oobEmailAuthResult.equals(other.getOobEmailAuthResult()))) &&
            ((this.oobPhoneAuthResult==null && other.getOobPhoneAuthResult()==null) || 
             (this.oobPhoneAuthResult!=null &&
              this.oobPhoneAuthResult.equals(other.getOobPhoneAuthResult()))) &&
            ((this.acspAuthenticationResponseData==null && other.getAcspAuthenticationResponseData()==null) || 
             (this.acspAuthenticationResponseData!=null &&
              this.acspAuthenticationResponseData.equals(other.getAcspAuthenticationResponseData())));
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
        if (getChallengeQuestionAuthResult() != null) {
            _hashCode += getChallengeQuestionAuthResult().hashCode();
        }
        if (getOobEmailAuthResult() != null) {
            _hashCode += getOobEmailAuthResult().hashCode();
        }
        if (getOobPhoneAuthResult() != null) {
            _hashCode += getOobPhoneAuthResult().hashCode();
        }
        if (getAcspAuthenticationResponseData() != null) {
            _hashCode += getAcspAuthenticationResponseData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialAuthResultList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthResultList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionAuthResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionAuthResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionAuthResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobEmailAuthResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobEmailAuthResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailAuthResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobPhoneAuthResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobPhoneAuthResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneAuthResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspAuthenticationResponseData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspAuthenticationResponseData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthenticationResponseData"));
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
