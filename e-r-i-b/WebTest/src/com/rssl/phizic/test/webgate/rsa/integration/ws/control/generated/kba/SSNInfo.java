/**
 * SSNInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba;


/**
 * This object defines the ssn information for a user
 */
public class SSNInfo  implements java.io.Serializable {
    private java.lang.String ssn;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.SSNType ssnType;

    public SSNInfo() {
    }

    public SSNInfo(
           java.lang.String ssn,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.SSNType ssnType) {
           this.ssn = ssn;
           this.ssnType = ssnType;
    }


    /**
     * Gets the ssn value for this SSNInfo.
     * 
     * @return ssn
     */
    public java.lang.String getSsn() {
        return ssn;
    }


    /**
     * Sets the ssn value for this SSNInfo.
     * 
     * @param ssn
     */
    public void setSsn(java.lang.String ssn) {
        this.ssn = ssn;
    }


    /**
     * Gets the ssnType value for this SSNInfo.
     * 
     * @return ssnType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.SSNType getSsnType() {
        return ssnType;
    }


    /**
     * Sets the ssnType value for this SSNInfo.
     * 
     * @param ssnType
     */
    public void setSsnType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.kba.SSNType ssnType) {
        this.ssnType = ssnType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SSNInfo)) return false;
        SSNInfo other = (SSNInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ssn==null && other.getSsn()==null) || 
             (this.ssn!=null &&
              this.ssn.equals(other.getSsn()))) &&
            ((this.ssnType==null && other.getSsnType()==null) || 
             (this.ssnType!=null &&
              this.ssnType.equals(other.getSsnType())));
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
        if (getSsn() != null) {
            _hashCode += getSsn().hashCode();
        }
        if (getSsnType() != null) {
            _hashCode += getSsnType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SSNInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "SSNInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ssn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "ssn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ssnType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "ssnType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "SSNType"));
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
