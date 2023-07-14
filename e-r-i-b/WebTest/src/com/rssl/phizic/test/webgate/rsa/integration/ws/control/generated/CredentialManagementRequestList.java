/**
 * CredentialManagementRequestList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class CredentialManagementRequestList  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CredentialRequestList  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionManagementRequest challengeQuestionManagementRequest;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailManagementRequest oobEmailManagementRequest;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneManagementRequest oobPhoneManagementRequest;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspManagementRequestData acspManagementRequestData;

    public CredentialManagementRequestList() {
    }

    public CredentialManagementRequestList(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionManagementRequest challengeQuestionManagementRequest,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailManagementRequest oobEmailManagementRequest,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneManagementRequest oobPhoneManagementRequest,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspManagementRequestData acspManagementRequestData) {
        this.challengeQuestionManagementRequest = challengeQuestionManagementRequest;
        this.oobEmailManagementRequest = oobEmailManagementRequest;
        this.oobPhoneManagementRequest = oobPhoneManagementRequest;
        this.acspManagementRequestData = acspManagementRequestData;
    }


    /**
     * Gets the challengeQuestionManagementRequest value for this CredentialManagementRequestList.
     * 
     * @return challengeQuestionManagementRequest
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionManagementRequest getChallengeQuestionManagementRequest() {
        return challengeQuestionManagementRequest;
    }


    /**
     * Sets the challengeQuestionManagementRequest value for this CredentialManagementRequestList.
     * 
     * @param challengeQuestionManagementRequest
     */
    public void setChallengeQuestionManagementRequest(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionManagementRequest challengeQuestionManagementRequest) {
        this.challengeQuestionManagementRequest = challengeQuestionManagementRequest;
    }


    /**
     * Gets the oobEmailManagementRequest value for this CredentialManagementRequestList.
     * 
     * @return oobEmailManagementRequest
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailManagementRequest getOobEmailManagementRequest() {
        return oobEmailManagementRequest;
    }


    /**
     * Sets the oobEmailManagementRequest value for this CredentialManagementRequestList.
     * 
     * @param oobEmailManagementRequest
     */
    public void setOobEmailManagementRequest(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobEmailManagementRequest oobEmailManagementRequest) {
        this.oobEmailManagementRequest = oobEmailManagementRequest;
    }


    /**
     * Gets the oobPhoneManagementRequest value for this CredentialManagementRequestList.
     * 
     * @return oobPhoneManagementRequest
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneManagementRequest getOobPhoneManagementRequest() {
        return oobPhoneManagementRequest;
    }


    /**
     * Sets the oobPhoneManagementRequest value for this CredentialManagementRequestList.
     * 
     * @param oobPhoneManagementRequest
     */
    public void setOobPhoneManagementRequest(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.OobPhoneManagementRequest oobPhoneManagementRequest) {
        this.oobPhoneManagementRequest = oobPhoneManagementRequest;
    }


    /**
     * Gets the acspManagementRequestData value for this CredentialManagementRequestList.
     * 
     * @return acspManagementRequestData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspManagementRequestData getAcspManagementRequestData() {
        return acspManagementRequestData;
    }


    /**
     * Sets the acspManagementRequestData value for this CredentialManagementRequestList.
     * 
     * @param acspManagementRequestData
     */
    public void setAcspManagementRequestData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspManagementRequestData acspManagementRequestData) {
        this.acspManagementRequestData = acspManagementRequestData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialManagementRequestList)) return false;
        CredentialManagementRequestList other = (CredentialManagementRequestList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionManagementRequest==null && other.getChallengeQuestionManagementRequest()==null) || 
             (this.challengeQuestionManagementRequest!=null &&
              this.challengeQuestionManagementRequest.equals(other.getChallengeQuestionManagementRequest()))) &&
            ((this.oobEmailManagementRequest==null && other.getOobEmailManagementRequest()==null) || 
             (this.oobEmailManagementRequest!=null &&
              this.oobEmailManagementRequest.equals(other.getOobEmailManagementRequest()))) &&
            ((this.oobPhoneManagementRequest==null && other.getOobPhoneManagementRequest()==null) || 
             (this.oobPhoneManagementRequest!=null &&
              this.oobPhoneManagementRequest.equals(other.getOobPhoneManagementRequest()))) &&
            ((this.acspManagementRequestData==null && other.getAcspManagementRequestData()==null) || 
             (this.acspManagementRequestData!=null &&
              this.acspManagementRequestData.equals(other.getAcspManagementRequestData())));
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
        if (getChallengeQuestionManagementRequest() != null) {
            _hashCode += getChallengeQuestionManagementRequest().hashCode();
        }
        if (getOobEmailManagementRequest() != null) {
            _hashCode += getOobEmailManagementRequest().hashCode();
        }
        if (getOobPhoneManagementRequest() != null) {
            _hashCode += getOobPhoneManagementRequest().hashCode();
        }
        if (getAcspManagementRequestData() != null) {
            _hashCode += getAcspManagementRequestData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialManagementRequestList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialManagementRequestList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionManagementRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionManagementRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobEmailManagementRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobEmailManagementRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailManagementRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobPhoneManagementRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobPhoneManagementRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneManagementRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspManagementRequestData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspManagementRequestData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspManagementRequestData"));
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
