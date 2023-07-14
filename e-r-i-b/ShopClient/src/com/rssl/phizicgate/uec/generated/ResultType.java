/**
 * ResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.uec.generated;

public class ResultType  implements java.io.Serializable {
    private java.lang.String docUID;

    private com.rssl.phizicgate.uec.generated.StatusType status;

    public ResultType() {
    }

    public ResultType(
           java.lang.String docUID,
           com.rssl.phizicgate.uec.generated.StatusType status) {
           this.docUID = docUID;
           this.status = status;
    }


    /**
     * Gets the docUID value for this ResultType.
     * 
     * @return docUID
     */
    public java.lang.String getDocUID() {
        return docUID;
    }


    /**
     * Sets the docUID value for this ResultType.
     * 
     * @param docUID
     */
    public void setDocUID(java.lang.String docUID) {
        this.docUID = docUID;
    }


    /**
     * Gets the status value for this ResultType.
     * 
     * @return status
     */
    public com.rssl.phizicgate.uec.generated.StatusType getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ResultType.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.uec.generated.StatusType status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultType)) return false;
        ResultType other = (ResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docUID==null && other.getDocUID()==null) || 
             (this.docUID!=null &&
              this.docUID.equals(other.getDocUID()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getDocUID() != null) {
            _hashCode += getDocUID().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "ResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "DocUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/e-invoicing/uec/1", "StatusType"));
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
