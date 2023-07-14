/**
 * KBAChallengeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba;

public class KBAChallengeResponse  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspChallengeResponse  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Question[] questions;

    public KBAChallengeResponse() {
    }

    public KBAChallengeResponse(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Question[] questions) {
        this.questions = questions;
    }


    /**
     * Gets the questions value for this KBAChallengeResponse.
     * 
     * @return questions
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Question[] getQuestions() {
        return questions;
    }


    /**
     * Sets the questions value for this KBAChallengeResponse.
     * 
     * @param questions
     */
    public void setQuestions(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Question[] questions) {
        this.questions = questions;
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Question getQuestions(int i) {
        return this.questions[i];
    }

    public void setQuestions(int i, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Question _value) {
        this.questions[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof KBAChallengeResponse)) return false;
        KBAChallengeResponse other = (KBAChallengeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.questions==null && other.getQuestions()==null) || 
             (this.questions!=null &&
              java.util.Arrays.equals(this.questions, other.getQuestions())));
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
        if (getQuestions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQuestions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQuestions(), i);
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
        new org.apache.axis.description.TypeDesc(KBAChallengeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAChallengeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "questions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Question"));
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
