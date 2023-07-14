/**
 * ChallengeQuestionManagementResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class ChallengeQuestionManagementResponse  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponsePayload payload;

    public ChallengeQuestionManagementResponse() {
    }

    public ChallengeQuestionManagementResponse(
           com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponsePayload payload) {
           this.payload = payload;
    }


    /**
     * Gets the payload value for this ChallengeQuestionManagementResponse.
     * 
     * @return payload
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponsePayload getPayload() {
        return payload;
    }


    /**
     * Sets the payload value for this ChallengeQuestionManagementResponse.
     * 
     * @param payload
     */
    public void setPayload(com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponsePayload payload) {
        this.payload = payload;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChallengeQuestionManagementResponse)) return false;
        ChallengeQuestionManagementResponse other = (ChallengeQuestionManagementResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.payload==null && other.getPayload()==null) || 
             (this.payload!=null &&
              this.payload.equals(other.getPayload())));
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
        if (getPayload() != null) {
            _hashCode += getPayload().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChallengeQuestionManagementResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payload");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "payload"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementResponsePayload"));
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
