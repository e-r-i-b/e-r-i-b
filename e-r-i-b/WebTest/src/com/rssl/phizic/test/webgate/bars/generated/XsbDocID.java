/**
 * XsbDocID.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.bars.generated;

public class XsbDocID  implements java.io.Serializable {
    private java.util.Calendar date;

    private java.lang.String docSubType;

    private java.lang.String docType;

    private int number;

    private java.lang.String originator;

    public XsbDocID() {
    }

    public XsbDocID(
           java.util.Calendar date,
           java.lang.String docSubType,
           java.lang.String docType,
           int number,
           java.lang.String originator) {
           this.date = date;
           this.docSubType = docSubType;
           this.docType = docType;
           this.number = number;
           this.originator = originator;
    }


    /**
     * Gets the date value for this XsbDocID.
     * 
     * @return date
     */
    public java.util.Calendar getDate() {
        return date;
    }


    /**
     * Sets the date value for this XsbDocID.
     * 
     * @param date
     */
    public void setDate(java.util.Calendar date) {
        this.date = date;
    }


    /**
     * Gets the docSubType value for this XsbDocID.
     * 
     * @return docSubType
     */
    public java.lang.String getDocSubType() {
        return docSubType;
    }


    /**
     * Sets the docSubType value for this XsbDocID.
     * 
     * @param docSubType
     */
    public void setDocSubType(java.lang.String docSubType) {
        this.docSubType = docSubType;
    }


    /**
     * Gets the docType value for this XsbDocID.
     * 
     * @return docType
     */
    public java.lang.String getDocType() {
        return docType;
    }


    /**
     * Sets the docType value for this XsbDocID.
     * 
     * @param docType
     */
    public void setDocType(java.lang.String docType) {
        this.docType = docType;
    }


    /**
     * Gets the number value for this XsbDocID.
     * 
     * @return number
     */
    public int getNumber() {
        return number;
    }


    /**
     * Sets the number value for this XsbDocID.
     * 
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }


    /**
     * Gets the originator value for this XsbDocID.
     * 
     * @return originator
     */
    public java.lang.String getOriginator() {
        return originator;
    }


    /**
     * Sets the originator value for this XsbDocID.
     * 
     * @param originator
     */
    public void setOriginator(java.lang.String originator) {
        this.originator = originator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XsbDocID)) return false;
        XsbDocID other = (XsbDocID) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.docSubType==null && other.getDocSubType()==null) || 
             (this.docSubType!=null &&
              this.docSubType.equals(other.getDocSubType()))) &&
            ((this.docType==null && other.getDocType()==null) || 
             (this.docType!=null &&
              this.docType.equals(other.getDocType()))) &&
            this.number == other.getNumber() &&
            ((this.originator==null && other.getOriginator()==null) || 
             (this.originator!=null &&
              this.originator.equals(other.getOriginator())));
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
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getDocSubType() != null) {
            _hashCode += getDocSubType().hashCode();
        }
        if (getDocType() != null) {
            _hashCode += getDocType().hashCode();
        }
        _hashCode += getNumber();
        if (getOriginator() != null) {
            _hashCode += getOriginator().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XsbDocID.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbDocID"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docSubType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "docSubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "docType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("", "number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originator");
        elemField.setXmlName(new javax.xml.namespace.QName("", "originator"));
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
