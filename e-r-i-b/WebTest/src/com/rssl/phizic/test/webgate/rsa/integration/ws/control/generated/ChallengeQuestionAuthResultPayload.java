/**
 * ChallengeQuestionAuthResultPayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This returns the results of the challenge question authentication
 */
public class ChallengeQuestionAuthResultPayload  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AuthenticationResult authenticationResult;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus callStatus;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionMatchResult challengeQuestionMatchResult;

    public ChallengeQuestionAuthResultPayload() {
    }

    public ChallengeQuestionAuthResultPayload(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AuthenticationResult authenticationResult,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus callStatus,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionMatchResult challengeQuestionMatchResult) {
           this.authenticationResult = authenticationResult;
           this.callStatus = callStatus;
           this.challengeQuestionMatchResult = challengeQuestionMatchResult;
    }


    /**
     * Gets the authenticationResult value for this ChallengeQuestionAuthResultPayload.
     * 
     * @return authenticationResult
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AuthenticationResult getAuthenticationResult() {
        return authenticationResult;
    }


    /**
     * Sets the authenticationResult value for this ChallengeQuestionAuthResultPayload.
     * 
     * @param authenticationResult
     */
    public void setAuthenticationResult(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AuthenticationResult authenticationResult) {
        this.authenticationResult = authenticationResult;
    }


    /**
     * Gets the callStatus value for this ChallengeQuestionAuthResultPayload.
     * 
     * @return callStatus
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus getCallStatus() {
        return callStatus;
    }


    /**
     * Sets the callStatus value for this ChallengeQuestionAuthResultPayload.
     * 
     * @param callStatus
     */
    public void setCallStatus(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus callStatus) {
        this.callStatus = callStatus;
    }


    /**
     * Gets the challengeQuestionMatchResult value for this ChallengeQuestionAuthResultPayload.
     * 
     * @return challengeQuestionMatchResult
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionMatchResult getChallengeQuestionMatchResult() {
        return challengeQuestionMatchResult;
    }


    /**
     * Sets the challengeQuestionMatchResult value for this ChallengeQuestionAuthResultPayload.
     * 
     * @param challengeQuestionMatchResult
     */
    public void setChallengeQuestionMatchResult(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionMatchResult challengeQuestionMatchResult) {
        this.challengeQuestionMatchResult = challengeQuestionMatchResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionAuthResultPayload)) return false;
        ChallengeQuestionAuthResultPayload other = (ChallengeQuestionAuthResultPayload) obj;
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
            ((this.challengeQuestionMatchResult==null && other.getChallengeQuestionMatchResult()==null) || 
             (this.challengeQuestionMatchResult!=null &&
              this.challengeQuestionMatchResult.equals(other.getChallengeQuestionMatchResult())));
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
        if (getChallengeQuestionMatchResult() != null) {
            _hashCode += getChallengeQuestionMatchResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeQuestionAuthResultPayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionAuthResultPayload"));
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
        elemField.setFieldName("challengeQuestionMatchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionMatchResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionMatchResult"));
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
