/**
 * XsbExceptionItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class XsbExceptionItem  implements java.io.Serializable {
    private java.lang.String excMessage;

    private java.lang.String excName;

    public XsbExceptionItem() {
    }

    public XsbExceptionItem(
           java.lang.String excMessage,
           java.lang.String excName) {
           this.excMessage = excMessage;
           this.excName = excName;
    }


    /**
     * Gets the excMessage value for this XsbExceptionItem.
     * 
     * @return excMessage
     */
    public java.lang.String getExcMessage() {
        return excMessage;
    }


    /**
     * Sets the excMessage value for this XsbExceptionItem.
     * 
     * @param excMessage
     */
    public void setExcMessage(java.lang.String excMessage) {
        this.excMessage = excMessage;
    }


    /**
     * Gets the excName value for this XsbExceptionItem.
     * 
     * @return excName
     */
    public java.lang.String getExcName() {
        return excName;
    }


    /**
     * Sets the excName value for this XsbExceptionItem.
     * 
     * @param excName
     */
    public void setExcName(java.lang.String excName) {
        this.excName = excName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XsbExceptionItem)) return false;
        XsbExceptionItem other = (XsbExceptionItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.excMessage==null && other.getExcMessage()==null) || 
             (this.excMessage!=null &&
              this.excMessage.equals(other.getExcMessage()))) &&
            ((this.excName==null && other.getExcName()==null) || 
             (this.excName!=null &&
              this.excName.equals(other.getExcName())));
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
        if (getExcMessage() != null) {
            _hashCode += getExcMessage().hashCode();
        }
        if (getExcName() != null) {
            _hashCode += getExcName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XsbExceptionItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbExceptionItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "excMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "excName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
