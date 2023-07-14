/**
 * Answer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba;


/**
 * This object defines the answer for a user
 */
public class Answer  implements java.io.Serializable {
    private java.lang.Long questionId;

    private long[] choiceIds;

    public Answer() {
    }

    public Answer(
           java.lang.Long questionId,
           long[] choiceIds) {
           this.questionId = questionId;
           this.choiceIds = choiceIds;
    }


    /**
     * Gets the questionId value for this Answer.
     * 
     * @return questionId
     */
    public java.lang.Long getQuestionId() {
        return questionId;
    }


    /**
     * Sets the questionId value for this Answer.
     * 
     * @param questionId
     */
    public void setQuestionId(java.lang.Long questionId) {
        this.questionId = questionId;
    }


    /**
     * Gets the choiceIds value for this Answer.
     * 
     * @return choiceIds
     */
    public long[] getChoiceIds() {
        return choiceIds;
    }


    /**
     * Sets the choiceIds value for this Answer.
     * 
     * @param choiceIds
     */
    public void setChoiceIds(long[] choiceIds) {
        this.choiceIds = choiceIds;
    }

    public long getChoiceIds(int i) {
        return this.choiceIds[i];
    }

    public void setChoiceIds(int i, long _value) {
        this.choiceIds[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Answer)) return false;
        Answer other = (Answer) obj;
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
            ((this.choiceIds==null && other.getChoiceIds()==null) || 
             (this.choiceIds!=null &&
              java.util.Arrays.equals(this.choiceIds, other.getChoiceIds())));
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
        if (getChoiceIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChoiceIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChoiceIds(), i);
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
        new org.apache.axis.description.TypeDesc(Answer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Answer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "questionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("choiceIds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "choiceIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
