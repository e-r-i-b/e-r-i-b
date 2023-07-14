/**
 * ChallengeQuestionGroup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines challenge question group information
 */
public class ChallengeQuestionGroup  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestion;

    private java.lang.String groupName;

    private java.lang.Boolean retired;

    public ChallengeQuestionGroup() {
    }

    public ChallengeQuestionGroup(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestion,
           java.lang.String groupName,
           java.lang.Boolean retired) {
           this.challengeQuestion = challengeQuestion;
           this.groupName = groupName;
           this.retired = retired;
    }


    /**
     * Gets the challengeQuestion value for this ChallengeQuestionGroup.
     * 
     * @return challengeQuestion
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] getChallengeQuestion() {
        return challengeQuestion;
    }


    /**
     * Sets the challengeQuestion value for this ChallengeQuestionGroup.
     * 
     * @param challengeQuestion
     */
    public void setChallengeQuestion(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestion) {
        this.challengeQuestion = challengeQuestion;
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion getChallengeQuestion(int i) {
        return this.challengeQuestion[i];
    }

    public void setChallengeQuestion(int i, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion _value) {
        this.challengeQuestion[i] = _value;
    }


    /**
     * Gets the groupName value for this ChallengeQuestionGroup.
     * 
     * @return groupName
     */
    public java.lang.String getGroupName() {
        return groupName;
    }


    /**
     * Sets the groupName value for this ChallengeQuestionGroup.
     * 
     * @param groupName
     */
    public void setGroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }


    /**
     * Gets the retired value for this ChallengeQuestionGroup.
     * 
     * @return retired
     */
    public java.lang.Boolean getRetired() {
        return retired;
    }


    /**
     * Sets the retired value for this ChallengeQuestionGroup.
     * 
     * @param retired
     */
    public void setRetired(java.lang.Boolean retired) {
        this.retired = retired;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionGroup)) return false;
        ChallengeQuestionGroup other = (ChallengeQuestionGroup) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.challengeQuestion==null && other.getChallengeQuestion()==null) || 
             (this.challengeQuestion!=null &&
              java.util.Arrays.equals(this.challengeQuestion, other.getChallengeQuestion()))) &&
            ((this.groupName==null && other.getGroupName()==null) || 
             (this.groupName!=null &&
              this.groupName.equals(other.getGroupName()))) &&
            ((this.retired==null && other.getRetired()==null) || 
             (this.retired!=null &&
              this.retired.equals(other.getRetired())));
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
        if (getChallengeQuestion() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChallengeQuestion());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChallengeQuestion(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGroupName() != null) {
            _hashCode += getGroupName().hashCode();
        }
        if (getRetired() != null) {
            _hashCode += getRetired().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeQuestionGroup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionGroup"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "groupName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retired");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "retired"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
