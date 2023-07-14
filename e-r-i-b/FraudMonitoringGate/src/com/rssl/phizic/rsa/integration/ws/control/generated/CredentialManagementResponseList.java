/**
 * CredentialManagementResponseList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class CredentialManagementResponseList  extends com.rssl.phizic.rsa.integration.ws.control.generated.CredentialResponseList  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponse challengeQuestionManagementResponse;

    private com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailManagementResponse oobEmailManagementResponse;

    private com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneManagementResponse oobPhoneManagementResponse;

    private com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementResponseData acspManagementResponseData;

    public CredentialManagementResponseList() {
    }

    public CredentialManagementResponseList(
           com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponse challengeQuestionManagementResponse,
           com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailManagementResponse oobEmailManagementResponse,
           com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneManagementResponse oobPhoneManagementResponse,
           com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementResponseData acspManagementResponseData) {
        this.challengeQuestionManagementResponse = challengeQuestionManagementResponse;
        this.oobEmailManagementResponse = oobEmailManagementResponse;
        this.oobPhoneManagementResponse = oobPhoneManagementResponse;
        this.acspManagementResponseData = acspManagementResponseData;
    }


    /**
     * Gets the challengeQuestionManagementResponse value for this CredentialManagementResponseList.
     * 
     * @return challengeQuestionManagementResponse
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponse getChallengeQuestionManagementResponse() {
        return challengeQuestionManagementResponse;
    }


    /**
     * Sets the challengeQuestionManagementResponse value for this CredentialManagementResponseList.
     * 
     * @param challengeQuestionManagementResponse
     */
    public void setChallengeQuestionManagementResponse(com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponse challengeQuestionManagementResponse) {
        this.challengeQuestionManagementResponse = challengeQuestionManagementResponse;
    }


    /**
     * Gets the oobEmailManagementResponse value for this CredentialManagementResponseList.
     * 
     * @return oobEmailManagementResponse
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailManagementResponse getOobEmailManagementResponse() {
        return oobEmailManagementResponse;
    }


    /**
     * Sets the oobEmailManagementResponse value for this CredentialManagementResponseList.
     * 
     * @param oobEmailManagementResponse
     */
    public void setOobEmailManagementResponse(com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailManagementResponse oobEmailManagementResponse) {
        this.oobEmailManagementResponse = oobEmailManagementResponse;
    }


    /**
     * Gets the oobPhoneManagementResponse value for this CredentialManagementResponseList.
     * 
     * @return oobPhoneManagementResponse
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneManagementResponse getOobPhoneManagementResponse() {
        return oobPhoneManagementResponse;
    }


    /**
     * Sets the oobPhoneManagementResponse value for this CredentialManagementResponseList.
     * 
     * @param oobPhoneManagementResponse
     */
    public void setOobPhoneManagementResponse(com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneManagementResponse oobPhoneManagementResponse) {
        this.oobPhoneManagementResponse = oobPhoneManagementResponse;
    }


    /**
     * Gets the acspManagementResponseData value for this CredentialManagementResponseList.
     * 
     * @return acspManagementResponseData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementResponseData getAcspManagementResponseData() {
        return acspManagementResponseData;
    }


    /**
     * Sets the acspManagementResponseData value for this CredentialManagementResponseList.
     * 
     * @param acspManagementResponseData
     */
    public void setAcspManagementResponseData(com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementResponseData acspManagementResponseData) {
        this.acspManagementResponseData = acspManagementResponseData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CredentialManagementResponseList)) return false;
        CredentialManagementResponseList other = (CredentialManagementResponseList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionManagementResponse==null && other.getChallengeQuestionManagementResponse()==null) || 
             (this.challengeQuestionManagementResponse!=null &&
              this.challengeQuestionManagementResponse.equals(other.getChallengeQuestionManagementResponse()))) &&
            ((this.oobEmailManagementResponse==null && other.getOobEmailManagementResponse()==null) || 
             (this.oobEmailManagementResponse!=null &&
              this.oobEmailManagementResponse.equals(other.getOobEmailManagementResponse()))) &&
            ((this.oobPhoneManagementResponse==null && other.getOobPhoneManagementResponse()==null) || 
             (this.oobPhoneManagementResponse!=null &&
              this.oobPhoneManagementResponse.equals(other.getOobPhoneManagementResponse()))) &&
            ((this.acspManagementResponseData==null && other.getAcspManagementResponseData()==null) || 
             (this.acspManagementResponseData!=null &&
              this.acspManagementResponseData.equals(other.getAcspManagementResponseData())));
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
        if (getChallengeQuestionManagementResponse() != null) {
            _hashCode += getChallengeQuestionManagementResponse().hashCode();
        }
        if (getOobEmailManagementResponse() != null) {
            _hashCode += getOobEmailManagementResponse().hashCode();
        }
        if (getOobPhoneManagementResponse() != null) {
            _hashCode += getOobPhoneManagementResponse().hashCode();
        }
        if (getAcspManagementResponseData() != null) {
            _hashCode += getAcspManagementResponseData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CredentialManagementResponseList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialManagementResponseList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionManagementResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionManagementResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobEmailManagementResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobEmailManagementResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailManagementResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oobPhoneManagementResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "oobPhoneManagementResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneManagementResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspManagementResponseData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspManagementResponseData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspManagementResponseData"));
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
