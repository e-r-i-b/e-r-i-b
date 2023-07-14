/**
 * OperInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип <Информация об операции>
 */
public class OperInfo_Type  implements java.io.Serializable {
    /* Дата поручения */
    private java.lang.String documentDate;

    /* Номер поручения */
    private long documentNumber;

    public OperInfo_Type() {
    }

    public OperInfo_Type(
           java.lang.String documentDate,
           long documentNumber) {
           this.documentDate = documentDate;
           this.documentNumber = documentNumber;
    }


    /**
     * Gets the documentDate value for this OperInfo_Type.
     * 
     * @return documentDate   * Дата поручения
     */
    public java.lang.String getDocumentDate() {
        return documentDate;
    }


    /**
     * Sets the documentDate value for this OperInfo_Type.
     * 
     * @param documentDate   * Дата поручения
     */
    public void setDocumentDate(java.lang.String documentDate) {
        this.documentDate = documentDate;
    }


    /**
     * Gets the documentNumber value for this OperInfo_Type.
     * 
     * @return documentNumber   * Номер поручения
     */
    public long getDocumentNumber() {
        return documentNumber;
    }


    /**
     * Sets the documentNumber value for this OperInfo_Type.
     * 
     * @param documentNumber   * Номер поручения
     */
    public void setDocumentNumber(long documentNumber) {
        this.documentNumber = documentNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperInfo_Type)) return false;
        OperInfo_Type other = (OperInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.documentDate==null && other.getDocumentDate()==null) || 
             (this.documentDate!=null &&
              this.documentDate.equals(other.getDocumentDate()))) &&
            this.documentNumber == other.getDocumentNumber();
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
        if (getDocumentDate() != null) {
            _hashCode += getDocumentDate().hashCode();
        }
        _hashCode += new Long(getDocumentNumber()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocumentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocumentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
