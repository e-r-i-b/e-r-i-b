/**
 * ChallengeQuestionManagementRequestPayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the Challenge Question Management request
 */
public class ChallengeQuestionManagementRequestPayload  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionTypeList actionTypeList;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionConfig challengeQuestionConfig;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestionList;

    public ChallengeQuestionManagementRequestPayload() {
    }

    public ChallengeQuestionManagementRequestPayload(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionTypeList actionTypeList,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionConfig challengeQuestionConfig,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestionList) {
           this.actionTypeList = actionTypeList;
           this.challengeQuestionConfig = challengeQuestionConfig;
           this.challengeQuestionList = challengeQuestionList;
    }


    /**
     * Gets the actionTypeList value for this ChallengeQuestionManagementRequestPayload.
     * 
     * @return actionTypeList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionTypeList getActionTypeList() {
        return actionTypeList;
    }


    /**
     * Sets the actionTypeList value for this ChallengeQuestionManagementRequestPayload.
     * 
     * @param actionTypeList
     */
    public void setActionTypeList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionTypeList actionTypeList) {
        this.actionTypeList = actionTypeList;
    }


    /**
     * Gets the challengeQuestionConfig value for this ChallengeQuestionManagementRequestPayload.
     * 
     * @return challengeQuestionConfig
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionConfig getChallengeQuestionConfig() {
        return challengeQuestionConfig;
    }


    /**
     * Sets the challengeQuestionConfig value for this ChallengeQuestionManagementRequestPayload.
     * 
     * @param challengeQuestionConfig
     */
    public void setChallengeQuestionConfig(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionConfig challengeQuestionConfig) {
        this.challengeQuestionConfig = challengeQuestionConfig;
    }


    /**
     * Gets the challengeQuestionList value for this ChallengeQuestionManagementRequestPayload.
     * 
     * @return challengeQuestionList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] getChallengeQuestionList() {
        return challengeQuestionList;
    }


    /**
     * Sets the challengeQuestionList value for this ChallengeQuestionManagementRequestPayload.
     * 
     * @param challengeQuestionList
     */
    public void setChallengeQuestionList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] challengeQuestionList) {
        this.challengeQuestionList = challengeQuestionList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionManagementRequestPayload)) return false;
        ChallengeQuestionManagementRequestPayload other = (ChallengeQuestionManagementRequestPayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actionTypeList==null && other.getActionTypeList()==null) || 
             (this.actionTypeList!=null &&
              this.actionTypeList.equals(other.getActionTypeList()))) &&
            ((this.challengeQuestionConfig==null && other.getChallengeQuestionConfig()==null) || 
             (this.challengeQuestionConfig!=null &&
              this.challengeQuestionConfig.equals(other.getChallengeQuestionConfig()))) &&
            ((this.challengeQuestionList==null && other.getChallengeQuestionList()==null) || 
             (this.challengeQuestionList!=null &&
              java.util.Arrays.equals(this.challengeQuestionList, other.getChallengeQuestionList())));
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
        if (getActionTypeList() != null) {
            _hashCode += getActionTypeList().hashCode();
        }
        if (getChallengeQuestionConfig() != null) {
            _hashCode += getChallengeQuestionConfig().hashCode();
        }
        if (getChallengeQuestionList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChallengeQuestionList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChallengeQuestionList(), i);
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
        new org.apache.axis.description.TypeDesc(ChallengeQuestionManagementRequestPayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementRequestPayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionTypeList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "actionTypeList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionActionTypeList"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionConfig");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionConfig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionConfig"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionList"));
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
