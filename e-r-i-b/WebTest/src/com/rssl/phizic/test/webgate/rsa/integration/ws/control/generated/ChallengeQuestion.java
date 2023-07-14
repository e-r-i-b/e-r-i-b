/**
 * ChallengeQuestion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines challenge question metadata
 */
public class ChallengeQuestion  implements java.io.Serializable {
    private java.lang.String actualAnswer;

    private java.lang.String actualAnswerOnFile;

    private java.lang.String questionId;

    private java.lang.String questionText;

    private java.lang.String userAnswer;

    public ChallengeQuestion() {
    }

    public ChallengeQuestion(
           java.lang.String actualAnswer,
           java.lang.String actualAnswerOnFile,
           java.lang.String questionId,
           java.lang.String questionText,
           java.lang.String userAnswer) {
           this.actualAnswer = actualAnswer;
           this.actualAnswerOnFile = actualAnswerOnFile;
           this.questionId = questionId;
           this.questionText = questionText;
           this.userAnswer = userAnswer;
    }


    /**
     * Gets the actualAnswer value for this ChallengeQuestion.
     * 
     * @return actualAnswer
     */
    public java.lang.String getActualAnswer() {
        return actualAnswer;
    }


    /**
     * Sets the actualAnswer value for this ChallengeQuestion.
     * 
     * @param actualAnswer
     */
    public void setActualAnswer(java.lang.String actualAnswer) {
        this.actualAnswer = actualAnswer;
    }


    /**
     * Gets the actualAnswerOnFile value for this ChallengeQuestion.
     * 
     * @return actualAnswerOnFile
     */
    public java.lang.String getActualAnswerOnFile() {
        return actualAnswerOnFile;
    }


    /**
     * Sets the actualAnswerOnFile value for this ChallengeQuestion.
     * 
     * @param actualAnswerOnFile
     */
    public void setActualAnswerOnFile(java.lang.String actualAnswerOnFile) {
        this.actualAnswerOnFile = actualAnswerOnFile;
    }


    /**
     * Gets the questionId value for this ChallengeQuestion.
     * 
     * @return questionId
     */
    public java.lang.String getQuestionId() {
        return questionId;
    }


    /**
     * Sets the questionId value for this ChallengeQuestion.
     * 
     * @param questionId
     */
    public void setQuestionId(java.lang.String questionId) {
        this.questionId = questionId;
    }


    /**
     * Gets the questionText value for this ChallengeQuestion.
     * 
     * @return questionText
     */
    public java.lang.String getQuestionText() {
        return questionText;
    }


    /**
     * Sets the questionText value for this ChallengeQuestion.
     * 
     * @param questionText
     */
    public void setQuestionText(java.lang.String questionText) {
        this.questionText = questionText;
    }


    /**
     * Gets the userAnswer value for this ChallengeQuestion.
     * 
     * @return userAnswer
     */
    public java.lang.String getUserAnswer() {
        return userAnswer;
    }


    /**
     * Sets the userAnswer value for this ChallengeQuestion.
     * 
     * @param userAnswer
     */
    public void setUserAnswer(java.lang.String userAnswer) {
        this.userAnswer = userAnswer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestion)) return false;
        ChallengeQuestion other = (ChallengeQuestion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actualAnswer==null && other.getActualAnswer()==null) || 
             (this.actualAnswer!=null &&
              this.actualAnswer.equals(other.getActualAnswer()))) &&
            ((this.actualAnswerOnFile==null && other.getActualAnswerOnFile()==null) || 
             (this.actualAnswerOnFile!=null &&
              this.actualAnswerOnFile.equals(other.getActualAnswerOnFile()))) &&
            ((this.questionId==null && other.getQuestionId()==null) || 
             (this.questionId!=null &&
              this.questionId.equals(other.getQuestionId()))) &&
            ((this.questionText==null && other.getQuestionText()==null) || 
             (this.questionText!=null &&
              this.questionText.equals(other.getQuestionText()))) &&
            ((this.userAnswer==null && other.getUserAnswer()==null) || 
             (this.userAnswer!=null &&
              this.userAnswer.equals(other.getUserAnswer())));
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
        if (getActualAnswer() != null) {
            _hashCode += getActualAnswer().hashCode();
        }
        if (getActualAnswerOnFile() != null) {
            _hashCode += getActualAnswerOnFile().hashCode();
        }
        if (getQuestionId() != null) {
            _hashCode += getQuestionId().hashCode();
        }
        if (getQuestionText() != null) {
            _hashCode += getQuestionText().hashCode();
        }
        if (getUserAnswer() != null) {
            _hashCode += getUserAnswer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeQuestion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualAnswer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "actualAnswer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualAnswerOnFile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "actualAnswerOnFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "questionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "questionText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userAnswer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userAnswer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
