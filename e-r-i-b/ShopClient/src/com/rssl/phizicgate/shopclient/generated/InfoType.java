/**
 * InfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.shopclient.generated;

public class InfoType  implements java.io.Serializable {
    private com.rssl.phizicgate.shopclient.generated.RegRqDocumentType document;

    private com.rssl.phizicgate.shopclient.generated.StatusType status;

    public InfoType() {
    }

    public InfoType(
           com.rssl.phizicgate.shopclient.generated.RegRqDocumentType document,
           com.rssl.phizicgate.shopclient.generated.StatusType status) {
           this.document = document;
           this.status = status;
    }


    /**
     * Gets the document value for this InfoType.
     * 
     * @return document
     */
    public com.rssl.phizicgate.shopclient.generated.RegRqDocumentType getDocument() {
        return document;
    }


    /**
     * Sets the document value for this InfoType.
     * 
     * @param document
     */
    public void setDocument(com.rssl.phizicgate.shopclient.generated.RegRqDocumentType document) {
        this.document = document;
    }


    /**
     * Gets the status value for this InfoType.
     * 
     * @return status
     */
    public com.rssl.phizicgate.shopclient.generated.StatusType getStatus() {
        return status;
    }


    /**
     * Sets the status value for this InfoType.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.shopclient.generated.StatusType status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InfoType)) return false;
        InfoType other = (InfoType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.document==null && other.getDocument()==null) || 
             (this.document!=null &&
              this.document.equals(other.getDocument()))) &&
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
        if (getDocument() != null) {
            _hashCode += getDocument().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InfoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "InfoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("document");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Document"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "RegRqDocumentType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "StatusType"));
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
