/**
 * CredentialChallengeList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class CredentialChallengeList  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialResponseList  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionChallenge challengeQuestionChallenge;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailChallenge oobEmailChallenge;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneChallenge oobPhoneChallenge;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspChallengeResponseData acspChallengeResponseData;

    public CredentialChallengeList() {
    }

    public CredentialChallengeList(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionChallenge challengeQuestionChallenge,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailChallenge oobEmailChallenge,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneChallenge oobPhoneChallenge,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspChallengeResponseData acspChallengeResponseData) {
        this.challengeQuestionChallenge = challengeQuestionChallenge;
        this.oobEmailChallenge = oobEmailChallenge;
        this.oobPhoneChallenge = oobPhoneChallenge;
        this.acspChallengeResponseData = acspChallengeResponseData;
    }


    /**
     * Gets the challengeQuestionChallenge value for this CredentialChallengeList.
     * 
     * @return challengeQuestionChallenge
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionChallenge getChallengeQuestionChallenge() {
        return challengeQuestionChallenge;
    }


    /**
     * Sets the challengeQuestionChallenge value for this CredentialChallengeList.
     * 
     * @param challengeQuestionChallenge
     */
    public void setChallengeQuestionChallenge(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionChallenge challengeQuestionChallenge) {
        this.challengeQuestionChallenge = challengeQuestionChallenge;
    }


    /**
     * Gets the oobEmailChallenge value for this CredentialChallengeList.
     * 
     * @return oobEmailChallenge
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailChallenge getOobEmailChallenge() {
        return oobEmailChallenge;
    }


    /**
     * Sets the oobEmailChallenge value for this CredentialChallengeList.
     * 
     * @param oobEmailChallenge
     */
    public void setOobEmailChallenge(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailChallenge oobEmailChallenge) {
        this.oobEmailChallenge = oobEmailChallenge;
    }


    /**
     * Gets the oobPhoneChallenge value for this CredentialChallengeList.
     * 
     * @return oobPhoneChallenge
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneChallenge getOobPhoneChallenge() {
        return oobPhoneChallenge;
    }


    /**
     * Sets the oobPhoneChallenge value for this CredentialChallengeList.
     * 
     * @param oobPhoneChallenge
     */
    public void setOobPhoneChallenge(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneChallenge oobPhoneChallenge) {
        this.oobPhoneChallenge = oobPhoneChallenge;
    }


    /**
     * Gets the acspChallengeResponseData value for this CredentialChallengeList.
     * 
     * @return acspChallengeResponseData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspChallengeResponseData getAcspChallengeResponseData() {
        return acspChallengeResponseData;
    }


    /**
     * Sets the acspChallengeResponseData value for this CredentialChallengeList.
     * 
     * @param acspChallengeResponseData
     */
    public void setAcspChallengeResponseData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspChallengeResponseData acspChallengeResponseData) {
        this.acspChallengeResponseData = acspChallengeResponseData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialChallengeList)) return false;
        CredentialChallengeList other = (CredentialChallengeList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionChallenge==null && other.getChallengeQuestionChallenge()==null) || 
             (this.challengeQuestionChallenge!=null &&
              this.challengeQuestionChallenge.equals(other.getChallengeQuestionChallenge()))) &&
            ((this.oobEmailChallenge==null && other.getOobEmailChallenge()==null) || 
             (this.oobEmailChallenge!=null &&
              this.oobEmailChallenge.equals(other.getOobEmailChallenge()))) &&
            ((this.oobPhoneChallenge==null && other.getOobPhoneChallenge()==null) || 
             (this.oobPhoneChallenge!=null &&
              this.oobPhoneChallenge.equals(other.getOobPhoneChallenge()))) &&
            ((this.acspChallengeResponseData==null && other.getAcspChallengeResponseData()==null) || 
             (this.acspChallengeResponseData!=null &&
              this.acspChallengeResponseData.equals(other.getAcspChallengeResponseData())));
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
        if (getChallengeQuestionChallenge() != null) {
            _hashCode += getChallengeQuestionChallenge().hashCode();
        }
        if (getOobEmailChallenge() != null) {
            _hashCode += getOobEmailChallenge().hashCode();
        }
        if (getOobPhoneChallenge() != null) {
            _hashCode += getOobPhoneChallenge().hashCode();
        }
        if (getAcspChallengeResponseData() != null) {
            _hashCode += getAcspChallengeResponseData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialChallengeList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialChallengeList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionChallenge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionChallenge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionChallenge"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobEmailChallenge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobEmailChallenge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailChallenge"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobPhoneChallenge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobPhoneChallenge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneChallenge"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspChallengeResponseData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspChallengeResponseData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspChallengeResponseData"));
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
