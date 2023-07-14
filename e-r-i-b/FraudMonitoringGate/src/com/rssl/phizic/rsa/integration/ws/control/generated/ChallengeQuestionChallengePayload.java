/**
 * ChallengeQuestionChallengePayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This returns the results of the challenge question challenge method
 */
public class ChallengeQuestionChallengePayload  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestions;

    public ChallengeQuestionChallengePayload() {
    }

    public ChallengeQuestionChallengePayload(
           com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus,
           com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestions) {
           this.callStatus = callStatus;
           this.challengeQuestions = challengeQuestions;
    }


    /**
     * Gets the callStatus value for this ChallengeQuestionChallengePayload.
     * 
     * @return callStatus
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus getCallStatus() {
        return callStatus;
    }


    /**
     * Sets the callStatus value for this ChallengeQuestionChallengePayload.
     * 
     * @param callStatus
     */
    public void setCallStatus(com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus) {
        this.callStatus = callStatus;
    }


    /**
     * Gets the challengeQuestions value for this ChallengeQuestionChallengePayload.
     * 
     * @return challengeQuestions
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestion[] getChallengeQuestions() {
        return challengeQuestions;
    }


    /**
     * Sets the challengeQuestions value for this ChallengeQuestionChallengePayload.
     * 
     * @param challengeQuestions
     */
    public void setChallengeQuestions(com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestions) {
        this.challengeQuestions = challengeQuestions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionChallengePayload)) return false;
        ChallengeQuestionChallengePayload other = (ChallengeQuestionChallengePayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.callStatus==null && other.getCallStatus()==null) || 
             (this.callStatus!=null &&
              this.callStatus.equals(other.getCallStatus()))) &&
            ((this.challengeQuestions==null && other.getChallengeQuestions()==null) || 
             (this.challengeQuestions!=null &&
              java.util.Arrays.equals(this.challengeQuestions, other.getChallengeQuestions())));
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
        if (getCallStatus() != null) {
            _hashCode += getCallStatus().hashCode();
        }
        if (getChallengeQuestions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChallengeQuestions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChallengeQuestions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeQuestionChallengePayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionChallengePayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "callStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CallStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestion"));
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
