/**
 * ChallengeQuestionMatchResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class ChallengeQuestionMatchResult  implements java.io.Serializable {
    private java.lang.Integer failCount;

    private java.lang.Integer matchCount;

    public ChallengeQuestionMatchResult() {
    }

    public ChallengeQuestionMatchResult(
           java.lang.Integer failCount,
           java.lang.Integer matchCount) {
           this.failCount = failCount;
           this.matchCount = matchCount;
    }


    /**
     * Gets the failCount value for this ChallengeQuestionMatchResult.
     * 
     * @return failCount
     */
    public java.lang.Integer getFailCount() {
        return failCount;
    }


    /**
     * Sets the failCount value for this ChallengeQuestionMatchResult.
     * 
     * @param failCount
     */
    public void setFailCount(java.lang.Integer failCount) {
        this.failCount = failCount;
    }


    /**
     * Gets the matchCount value for this ChallengeQuestionMatchResult.
     * 
     * @return matchCount
     */
    public java.lang.Integer getMatchCount() {
        return matchCount;
    }


    /**
     * Sets the matchCount value for this ChallengeQuestionMatchResult.
     * 
     * @param matchCount
     */
    public void setMatchCount(java.lang.Integer matchCount) {
        this.matchCount = matchCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionMatchResult)) return false;
        ChallengeQuestionMatchResult other = (ChallengeQuestionMatchResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.failCount==null && other.getFailCount()==null) || 
             (this.failCount!=null &&
              this.failCount.equals(other.getFailCount()))) &&
            ((this.matchCount==null && other.getMatchCount()==null) || 
             (this.matchCount!=null &&
              this.matchCount.equals(other.getMatchCount())));
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
        if (getFailCount() != null) {
            _hashCode += getFailCount().hashCode();
        }
        if (getMatchCount() != null) {
            _hashCode += getMatchCount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeQuestionMatchResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionMatchResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("failCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "failCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matchCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "matchCount"));
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
