/**
 * CredentialDataList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class CredentialDataList  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialRequestList  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionData challengeQuestionData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailData oobEmailData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneData oobPhoneData;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthenticationRequestData acspAuthenticationRequestData;

    public CredentialDataList() {
    }

    public CredentialDataList(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionData challengeQuestionData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailData oobEmailData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneData oobPhoneData,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthenticationRequestData acspAuthenticationRequestData) {
        this.challengeQuestionData = challengeQuestionData;
        this.oobEmailData = oobEmailData;
        this.oobPhoneData = oobPhoneData;
        this.acspAuthenticationRequestData = acspAuthenticationRequestData;
    }


    /**
     * Gets the challengeQuestionData value for this CredentialDataList.
     * 
     * @return challengeQuestionData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionData getChallengeQuestionData() {
        return challengeQuestionData;
    }


    /**
     * Sets the challengeQuestionData value for this CredentialDataList.
     * 
     * @param challengeQuestionData
     */
    public void setChallengeQuestionData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionData challengeQuestionData) {
        this.challengeQuestionData = challengeQuestionData;
    }


    /**
     * Gets the oobEmailData value for this CredentialDataList.
     * 
     * @return oobEmailData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailData getOobEmailData() {
        return oobEmailData;
    }


    /**
     * Sets the oobEmailData value for this CredentialDataList.
     * 
     * @param oobEmailData
     */
    public void setOobEmailData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailData oobEmailData) {
        this.oobEmailData = oobEmailData;
    }


    /**
     * Gets the oobPhoneData value for this CredentialDataList.
     * 
     * @return oobPhoneData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneData getOobPhoneData() {
        return oobPhoneData;
    }


    /**
     * Sets the oobPhoneData value for this CredentialDataList.
     * 
     * @param oobPhoneData
     */
    public void setOobPhoneData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneData oobPhoneData) {
        this.oobPhoneData = oobPhoneData;
    }


    /**
     * Gets the acspAuthenticationRequestData value for this CredentialDataList.
     * 
     * @return acspAuthenticationRequestData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthenticationRequestData getAcspAuthenticationRequestData() {
        return acspAuthenticationRequestData;
    }


    /**
     * Sets the acspAuthenticationRequestData value for this CredentialDataList.
     * 
     * @param acspAuthenticationRequestData
     */
    public void setAcspAuthenticationRequestData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthenticationRequestData acspAuthenticationRequestData) {
        this.acspAuthenticationRequestData = acspAuthenticationRequestData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialDataList)) return false;
        CredentialDataList other = (CredentialDataList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionData==null && other.getChallengeQuestionData()==null) || 
             (this.challengeQuestionData!=null &&
              this.challengeQuestionData.equals(other.getChallengeQuestionData()))) &&
            ((this.oobEmailData==null && other.getOobEmailData()==null) || 
             (this.oobEmailData!=null &&
              this.oobEmailData.equals(other.getOobEmailData()))) &&
            ((this.oobPhoneData==null && other.getOobPhoneData()==null) || 
             (this.oobPhoneData!=null &&
              this.oobPhoneData.equals(other.getOobPhoneData()))) &&
            ((this.acspAuthenticationRequestData==null && other.getAcspAuthenticationRequestData()==null) || 
             (this.acspAuthenticationRequestData!=null &&
              this.acspAuthenticationRequestData.equals(other.getAcspAuthenticationRequestData())));
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
        if (getChallengeQuestionData() != null) {
            _hashCode += getChallengeQuestionData().hashCode();
        }
        if (getOobEmailData() != null) {
            _hashCode += getOobEmailData().hashCode();
        }
        if (getOobPhoneData() != null) {
            _hashCode += getOobPhoneData().hashCode();
        }
        if (getAcspAuthenticationRequestData() != null) {
            _hashCode += getAcspAuthenticationRequestData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialDataList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialDataList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobEmailData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobEmailData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobPhoneData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobPhoneData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspAuthenticationRequestData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspAuthenticationRequestData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthenticationRequestData"));
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
