/**
 * ChallengeQuestionManagementResponsePayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the Challenge Question Management response
 */
public class ChallengeQuestionManagementResponsePayload  implements java.io.Serializable {
    private java.lang.String acspAccountId;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionGroup[] browsableChallQuesGroupList;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus callStatus;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] userChallQuesDataList;

    public ChallengeQuestionManagementResponsePayload() {
    }

    public ChallengeQuestionManagementResponsePayload(
           java.lang.String acspAccountId,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionGroup[] browsableChallQuesGroupList,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus callStatus,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] userChallQuesDataList) {
           this.acspAccountId = acspAccountId;
           this.browsableChallQuesGroupList = browsableChallQuesGroupList;
           this.callStatus = callStatus;
           this.userChallQuesDataList = userChallQuesDataList;
    }


    /**
     * Gets the acspAccountId value for this ChallengeQuestionManagementResponsePayload.
     * 
     * @return acspAccountId
     */
    public java.lang.String getAcspAccountId() {
        return acspAccountId;
    }


    /**
     * Sets the acspAccountId value for this ChallengeQuestionManagementResponsePayload.
     * 
     * @param acspAccountId
     */
    public void setAcspAccountId(java.lang.String acspAccountId) {
        this.acspAccountId = acspAccountId;
    }


    /**
     * Gets the browsableChallQuesGroupList value for this ChallengeQuestionManagementResponsePayload.
     * 
     * @return browsableChallQuesGroupList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionGroup[] getBrowsableChallQuesGroupList() {
        return browsableChallQuesGroupList;
    }


    /**
     * Sets the browsableChallQuesGroupList value for this ChallengeQuestionManagementResponsePayload.
     * 
     * @param browsableChallQuesGroupList
     */
    public void setBrowsableChallQuesGroupList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestionGroup[] browsableChallQuesGroupList) {
        this.browsableChallQuesGroupList = browsableChallQuesGroupList;
    }


    /**
     * Gets the callStatus value for this ChallengeQuestionManagementResponsePayload.
     * 
     * @return callStatus
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus getCallStatus() {
        return callStatus;
    }


    /**
     * Sets the callStatus value for this ChallengeQuestionManagementResponsePayload.
     * 
     * @param callStatus
     */
    public void setCallStatus(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.CallStatus callStatus) {
        this.callStatus = callStatus;
    }


    /**
     * Gets the userChallQuesDataList value for this ChallengeQuestionManagementResponsePayload.
     * 
     * @return userChallQuesDataList
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] getUserChallQuesDataList() {
        return userChallQuesDataList;
    }


    /**
     * Sets the userChallQuesDataList value for this ChallengeQuestionManagementResponsePayload.
     * 
     * @param userChallQuesDataList
     */
    public void setUserChallQuesDataList(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ChallengeQuestion[] userChallQuesDataList) {
        this.userChallQuesDataList = userChallQuesDataList;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionManagementResponsePayload)) return false;
        ChallengeQuestionManagementResponsePayload other = (ChallengeQuestionManagementResponsePayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acspAccountId==null && other.getAcspAccountId()==null) || 
             (this.acspAccountId!=null &&
              this.acspAccountId.equals(other.getAcspAccountId()))) &&
            ((this.browsableChallQuesGroupList==null && other.getBrowsableChallQuesGroupList()==null) || 
             (this.browsableChallQuesGroupList!=null &&
              java.util.Arrays.equals(this.browsableChallQuesGroupList, other.getBrowsableChallQuesGroupList()))) &&
            ((this.callStatus==null && other.getCallStatus()==null) || 
             (this.callStatus!=null &&
              this.callStatus.equals(other.getCallStatus()))) &&
            ((this.userChallQuesDataList==null && other.getUserChallQuesDataList()==null) || 
             (this.userChallQuesDataList!=null &&
              java.util.Arrays.equals(this.userChallQuesDataList, other.getUserChallQuesDataList())));
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
        if (getAcspAccountId() != null) {
            _hashCode += getAcspAccountId().hashCode();
        }
        if (getBrowsableChallQuesGroupList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBrowsableChallQuesGroupList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBrowsableChallQuesGroupList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCallStatus() != null) {
            _hashCode += getCallStatus().hashCode();
        }
        if (getUserChallQuesDataList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserChallQuesDataList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUserChallQuesDataList(), i);
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
        new org.apache.axis.description.TypeDesc(ChallengeQuestionManagementResponsePayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementResponsePayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspAccountId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspAccountId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("browsableChallQuesGroupList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "browsableChallQuesGroupList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionGroup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "questionGroup"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "callStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CallStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userChallQuesDataList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userChallQuesDataList"));
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
