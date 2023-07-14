/**
 * ChallengeQuestionActionTypeList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines a list of challenge question action types
 */
public class ChallengeQuestionActionTypeList  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ActionTypeList  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionType[] challengeQuestionActionType;

    public ChallengeQuestionActionTypeList() {
    }

    public ChallengeQuestionActionTypeList(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionType[] challengeQuestionActionType) {
        this.challengeQuestionActionType = challengeQuestionActionType;
    }


    /**
     * Gets the challengeQuestionActionType value for this ChallengeQuestionActionTypeList.
     * 
     * @return challengeQuestionActionType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionType[] getChallengeQuestionActionType() {
        return challengeQuestionActionType;
    }


    /**
     * Sets the challengeQuestionActionType value for this ChallengeQuestionActionTypeList.
     * 
     * @param challengeQuestionActionType
     */
    public void setChallengeQuestionActionType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionType[] challengeQuestionActionType) {
        this.challengeQuestionActionType = challengeQuestionActionType;
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionType getChallengeQuestionActionType(int i) {
        return this.challengeQuestionActionType[i];
    }

    public void setChallengeQuestionActionType(int i, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionActionType _value) {
        this.challengeQuestionActionType[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionActionTypeList)) return false;
        ChallengeQuestionActionTypeList other = (ChallengeQuestionActionTypeList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.challengeQuestionActionType==null && other.getChallengeQuestionActionType()==null) || 
             (this.challengeQuestionActionType!=null &&
              java.util.Arrays.equals(this.challengeQuestionActionType, other.getChallengeQuestionActionType())));
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
        if (getChallengeQuestionActionType() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChallengeQuestionActionType());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChallengeQuestionActionType(), i);
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
        new org.apache.axis.description.TypeDesc(ChallengeQuestionActionTypeList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionActionTypeList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("challengeQuestionActionType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestionActionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionActionType"));
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
