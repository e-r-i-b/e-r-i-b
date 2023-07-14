/**
 * ChallengeQuestionChallengeRequestPayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This requests the results of the challenge question challenge method
 */
public class ChallengeQuestionChallengeRequestPayload  implements java.io.Serializable {
    private java.lang.String[] excludeQuestions;

    private java.lang.Integer numberOfQuestion;

    public ChallengeQuestionChallengeRequestPayload() {
    }

    public ChallengeQuestionChallengeRequestPayload(
           java.lang.String[] excludeQuestions,
           java.lang.Integer numberOfQuestion) {
           this.excludeQuestions = excludeQuestions;
           this.numberOfQuestion = numberOfQuestion;
    }


    /**
     * Gets the excludeQuestions value for this ChallengeQuestionChallengeRequestPayload.
     * 
     * @return excludeQuestions
     */
    public java.lang.String[] getExcludeQuestions() {
        return excludeQuestions;
    }


    /**
     * Sets the excludeQuestions value for this ChallengeQuestionChallengeRequestPayload.
     * 
     * @param excludeQuestions
     */
    public void setExcludeQuestions(java.lang.String[] excludeQuestions) {
        this.excludeQuestions = excludeQuestions;
    }


    /**
     * Gets the numberOfQuestion value for this ChallengeQuestionChallengeRequestPayload.
     * 
     * @return numberOfQuestion
     */
    public java.lang.Integer getNumberOfQuestion() {
        return numberOfQuestion;
    }


    /**
     * Sets the numberOfQuestion value for this ChallengeQuestionChallengeRequestPayload.
     * 
     * @param numberOfQuestion
     */
    public void setNumberOfQuestion(java.lang.Integer numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionChallengeRequestPayload)) return false;
        ChallengeQuestionChallengeRequestPayload other = (ChallengeQuestionChallengeRequestPayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.excludeQuestions==null && other.getExcludeQuestions()==null) || 
             (this.excludeQuestions!=null &&
              java.util.Arrays.equals(this.excludeQuestions, other.getExcludeQuestions()))) &&
            ((this.numberOfQuestion==null && other.getNumberOfQuestion()==null) || 
             (this.numberOfQuestion!=null &&
              this.numberOfQuestion.equals(other.getNumberOfQuestion())));
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
        if (getExcludeQuestions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExcludeQuestions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExcludeQuestions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumberOfQuestion() != null) {
            _hashCode += getNumberOfQuestion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeQuestionChallengeRequestPayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionChallengeRequestPayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excludeQuestions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "excludeQuestions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "questionId"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfQuestion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "numberOfQuestion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
