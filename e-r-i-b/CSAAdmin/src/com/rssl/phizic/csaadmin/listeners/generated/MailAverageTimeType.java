/**
 * MailAverageTimeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;


/**
 * Среднее время обработки письма сотрудником
 */
public class MailAverageTimeType  implements java.io.Serializable {
    private long milliseconds;

    private long count;

    public MailAverageTimeType() {
    }

    public MailAverageTimeType(
           long milliseconds,
           long count) {
           this.milliseconds = milliseconds;
           this.count = count;
    }


    /**
     * Gets the milliseconds value for this MailAverageTimeType.
     * 
     * @return milliseconds
     */
    public long getMilliseconds() {
        return milliseconds;
    }


    /**
     * Sets the milliseconds value for this MailAverageTimeType.
     * 
     * @param milliseconds
     */
    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }


    /**
     * Gets the count value for this MailAverageTimeType.
     * 
     * @return count
     */
    public long getCount() {
        return count;
    }


    /**
     * Sets the count value for this MailAverageTimeType.
     * 
     * @param count
     */
    public void setCount(long count) {
        this.count = count;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MailAverageTimeType)) return false;
        MailAverageTimeType other = (MailAverageTimeType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.milliseconds == other.getMilliseconds() &&
            this.count == other.getCount();
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
        _hashCode += new Long(getMilliseconds()).hashCode();
        _hashCode += new Long(getCount()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MailAverageTimeType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailAverageTimeType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("milliseconds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "milliseconds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("count");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "count"));
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
