/**
 * StatusHeader.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This reports the status of the call
 */
public class StatusHeader  implements java.io.Serializable {
    private java.lang.Integer reasonCode;

    private java.lang.String reasonDescription;

    private java.lang.Integer statusCode;

    public StatusHeader() {
    }

    public StatusHeader(
           java.lang.Integer reasonCode,
           java.lang.String reasonDescription,
           java.lang.Integer statusCode) {
           this.reasonCode = reasonCode;
           this.reasonDescription = reasonDescription;
           this.statusCode = statusCode;
    }


    /**
     * Gets the reasonCode value for this StatusHeader.
     * 
     * @return reasonCode
     */
    public java.lang.Integer getReasonCode() {
        return reasonCode;
    }


    /**
     * Sets the reasonCode value for this StatusHeader.
     * 
     * @param reasonCode
     */
    public void setReasonCode(java.lang.Integer reasonCode) {
        this.reasonCode = reasonCode;
    }


    /**
     * Gets the reasonDescription value for this StatusHeader.
     * 
     * @return reasonDescription
     */
    public java.lang.String getReasonDescription() {
        return reasonDescription;
    }


    /**
     * Sets the reasonDescription value for this StatusHeader.
     * 
     * @param reasonDescription
     */
    public void setReasonDescription(java.lang.String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }


    /**
     * Gets the statusCode value for this StatusHeader.
     * 
     * @return statusCode
     */
    public java.lang.Integer getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this StatusHeader.
     * 
     * @param statusCode
     */
    public void setStatusCode(java.lang.Integer statusCode) {
        this.statusCode = statusCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatusHeader)) return false;
        StatusHeader other = (StatusHeader) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reasonCode==null && other.getReasonCode()==null) || 
             (this.reasonCode!=null &&
              this.reasonCode.equals(other.getReasonCode()))) &&
            ((this.reasonDescription==null && other.getReasonDescription()==null) || 
             (this.reasonDescription!=null &&
              this.reasonDescription.equals(other.getReasonDescription()))) &&
            ((this.statusCode==null && other.getStatusCode()==null) || 
             (this.statusCode!=null &&
              this.statusCode.equals(other.getStatusCode())));
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
        if (getReasonCode() != null) {
            _hashCode += getReasonCode().hashCode();
        }
        if (getReasonDescription() != null) {
            _hashCode += getReasonDescription().hashCode();
        }
        if (getStatusCode() != null) {
            _hashCode += getStatusCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatusHeader.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StatusHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "reasonCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "reasonDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "statusCode"));
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
