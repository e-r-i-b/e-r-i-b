/**
 * KBAChallengeRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba;

public class KBAChallengeRequest  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.AcspChallengeRequest  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.PersonInfo personInfo;

    public KBAChallengeRequest() {
    }

    public KBAChallengeRequest(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.PersonInfo personInfo) {
        this.personInfo = personInfo;
    }


    /**
     * Gets the personInfo value for this KBAChallengeRequest.
     * 
     * @return personInfo
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.PersonInfo getPersonInfo() {
        return personInfo;
    }


    /**
     * Sets the personInfo value for this KBAChallengeRequest.
     * 
     * @param personInfo
     */
    public void setPersonInfo(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof KBAChallengeRequest)) return false;
        KBAChallengeRequest other = (KBAChallengeRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.personInfo==null && other.getPersonInfo()==null) || 
             (this.personInfo!=null &&
              this.personInfo.equals(other.getPersonInfo())));
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
        if (getPersonInfo() != null) {
            _hashCode += getPersonInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(KBAChallengeRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAChallengeRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "personInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "PersonInfo"));
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
