/**
 * ChallengeQuestionConfig.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the configuration data for challenge questions
 */
public class ChallengeQuestionConfig  implements java.io.Serializable {
    private java.lang.String[] excludeQuestionList;

    private java.lang.Boolean excludeUserQuestions;

    private java.lang.Integer groupCount;

    private java.lang.Boolean includeRetired;

    private java.lang.Integer questionCount;

    public ChallengeQuestionConfig() {
    }

    public ChallengeQuestionConfig(
           java.lang.String[] excludeQuestionList,
           java.lang.Boolean excludeUserQuestions,
           java.lang.Integer groupCount,
           java.lang.Boolean includeRetired,
           java.lang.Integer questionCount) {
           this.excludeQuestionList = excludeQuestionList;
           this.excludeUserQuestions = excludeUserQuestions;
           this.groupCount = groupCount;
           this.includeRetired = includeRetired;
           this.questionCount = questionCount;
    }


    /**
     * Gets the excludeQuestionList value for this ChallengeQuestionConfig.
     * 
     * @return excludeQuestionList
     */
    public java.lang.String[] getExcludeQuestionList() {
        return excludeQuestionList;
    }


    /**
     * Sets the excludeQuestionList value for this ChallengeQuestionConfig.
     * 
     * @param excludeQuestionList
     */
    public void setExcludeQuestionList(java.lang.String[] excludeQuestionList) {
        this.excludeQuestionList = excludeQuestionList;
    }


    /**
     * Gets the excludeUserQuestions value for this ChallengeQuestionConfig.
     * 
     * @return excludeUserQuestions
     */
    public java.lang.Boolean getExcludeUserQuestions() {
        return excludeUserQuestions;
    }


    /**
     * Sets the excludeUserQuestions value for this ChallengeQuestionConfig.
     * 
     * @param excludeUserQuestions
     */
    public void setExcludeUserQuestions(java.lang.Boolean excludeUserQuestions) {
        this.excludeUserQuestions = excludeUserQuestions;
    }


    /**
     * Gets the groupCount value for this ChallengeQuestionConfig.
     * 
     * @return groupCount
     */
    public java.lang.Integer getGroupCount() {
        return groupCount;
    }


    /**
     * Sets the groupCount value for this ChallengeQuestionConfig.
     * 
     * @param groupCount
     */
    public void setGroupCount(java.lang.Integer groupCount) {
        this.groupCount = groupCount;
    }


    /**
     * Gets the includeRetired value for this ChallengeQuestionConfig.
     * 
     * @return includeRetired
     */
    public java.lang.Boolean getIncludeRetired() {
        return includeRetired;
    }


    /**
     * Sets the includeRetired value for this ChallengeQuestionConfig.
     * 
     * @param includeRetired
     */
    public void setIncludeRetired(java.lang.Boolean includeRetired) {
        this.includeRetired = includeRetired;
    }


    /**
     * Gets the questionCount value for this ChallengeQuestionConfig.
     * 
     * @return questionCount
     */
    public java.lang.Integer getQuestionCount() {
        return questionCount;
    }


    /**
     * Sets the questionCount value for this ChallengeQuestionConfig.
     * 
     * @param questionCount
     */
    public void setQuestionCount(java.lang.Integer questionCount) {
        this.questionCount = questionCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionConfig)) return false;
        ChallengeQuestionConfig other = (ChallengeQuestionConfig) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.excludeQuestionList==null && other.getExcludeQuestionList()==null) || 
             (this.excludeQuestionList!=null &&
              java.util.Arrays.equals(this.excludeQuestionList, other.getExcludeQuestionList()))) &&
            ((this.excludeUserQuestions==null && other.getExcludeUserQuestions()==null) || 
             (this.excludeUserQuestions!=null &&
              this.excludeUserQuestions.equals(other.getExcludeUserQuestions()))) &&
            ((this.groupCount==null && other.getGroupCount()==null) || 
             (this.groupCount!=null &&
              this.groupCount.equals(other.getGroupCount()))) &&
            ((this.includeRetired==null && other.getIncludeRetired()==null) || 
             (this.includeRetired!=null &&
              this.includeRetired.equals(other.getIncludeRetired()))) &&
            ((this.questionCount==null && other.getQuestionCount()==null) || 
             (this.questionCount!=null &&
              this.questionCount.equals(other.getQuestionCount())));
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
        if (getExcludeQuestionList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExcludeQuestionList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExcludeQuestionList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExcludeUserQuestions() != null) {
            _hashCode += getExcludeUserQuestions().hashCode();
        }
        if (getGroupCount() != null) {
            _hashCode += getGroupCount().hashCode();
        }
        if (getIncludeRetired() != null) {
            _hashCode += getIncludeRetired().hashCode();
        }
        if (getQuestionCount() != null) {
            _hashCode += getQuestionCount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeQuestionConfig.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionConfig"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excludeQuestionList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "excludeQuestionList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "questionId"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excludeUserQuestions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "excludeUserQuestions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "groupCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("includeRetired");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "includeRetired"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "questionCount"));
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
