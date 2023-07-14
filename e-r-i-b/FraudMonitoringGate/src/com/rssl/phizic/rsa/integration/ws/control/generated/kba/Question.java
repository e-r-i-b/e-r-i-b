/**
 * Question.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated.kba;


/**
 * This object defines the questions for a user
 */
public class Question  implements java.io.Serializable {
    private java.lang.Long questionId;

    private java.lang.String text;

    private com.rssl.phizic.rsa.integration.ws.control.generated.kba.Choice[] choices;

    public Question() {
    }

    public Question(
           java.lang.Long questionId,
           java.lang.String text,
           com.rssl.phizic.rsa.integration.ws.control.generated.kba.Choice[] choices) {
           this.questionId = questionId;
           this.text = text;
           this.choices = choices;
    }


    /**
     * Gets the questionId value for this Question.
     * 
     * @return questionId
     */
    public java.lang.Long getQuestionId() {
        return questionId;
    }


    /**
     * Sets the questionId value for this Question.
     * 
     * @param questionId
     */
    public void setQuestionId(java.lang.Long questionId) {
        this.questionId = questionId;
    }


    /**
     * Gets the text value for this Question.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this Question.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }


    /**
     * Gets the choices value for this Question.
     * 
     * @return choices
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.kba.Choice[] getChoices() {
        return choices;
    }


    /**
     * Sets the choices value for this Question.
     * 
     * @param choices
     */
    public void setChoices(com.rssl.phizic.rsa.integration.ws.control.generated.kba.Choice[] choices) {
        this.choices = choices;
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.kba.Choice getChoices(int i) {
        return this.choices[i];
    }

    public void setChoices(int i, com.rssl.phizic.rsa.integration.ws.control.generated.kba.Choice _value) {
        this.choices[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Question)) return false;
        Question other = (Question) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.questionId==null && other.getQuestionId()==null) || 
             (this.questionId!=null &&
              this.questionId.equals(other.getQuestionId()))) &&
            ((this.text==null && other.getText()==null) || 
             (this.text!=null &&
              this.text.equals(other.getText()))) &&
            ((this.choices==null && other.getChoices()==null) || 
             (this.choices!=null &&
              java.util.Arrays.equals(this.choices, other.getChoices())));
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
        if (getQuestionId() != null) {
            _hashCode += getQuestionId().hashCode();
        }
        if (getText() != null) {
            _hashCode += getText().hashCode();
        }
        if (getChoices() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChoices());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChoices(), i);
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
        new org.apache.axis.description.TypeDesc(Question.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Question"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "questionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("text");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "text"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("choices");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "choices"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Choice"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
