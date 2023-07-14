/**
 * KBAAuthenticationRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba;

public class KBAAuthenticationRequest  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspAuthenticationRequest  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Answer[] answers;

    public KBAAuthenticationRequest() {
    }

    public KBAAuthenticationRequest(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Answer[] answers) {
        this.answers = answers;
    }


    /**
     * Gets the answers value for this KBAAuthenticationRequest.
     * 
     * @return answers
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Answer[] getAnswers() {
        return answers;
    }


    /**
     * Sets the answers value for this KBAAuthenticationRequest.
     * 
     * @param answers
     */
    public void setAnswers(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Answer[] answers) {
        this.answers = answers;
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Answer getAnswers(int i) {
        return this.answers[i];
    }

    public void setAnswers(int i, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.Answer _value) {
        this.answers[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof KBAAuthenticationRequest)) return false;
        KBAAuthenticationRequest other = (KBAAuthenticationRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.answers==null && other.getAnswers()==null) || 
             (this.answers!=null &&
              java.util.Arrays.equals(this.answers, other.getAnswers())));
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
        if (getAnswers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAnswers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAnswers(), i);
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
        new org.apache.axis.description.TypeDesc(KBAAuthenticationRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAAuthenticationRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("answers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "answers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Answer"));
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
