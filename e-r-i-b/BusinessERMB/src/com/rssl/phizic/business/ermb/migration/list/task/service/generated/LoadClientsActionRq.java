/**
 * LoadClientsActionRq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.business.ermb.migration.list.task.service.generated;

public class LoadClientsActionRq  implements java.io.Serializable {
    private java.lang.String filePath;

    private int nonActivePeriod;

    public LoadClientsActionRq() {
    }

    public LoadClientsActionRq(
           java.lang.String filePath,
           int nonActivePeriod) {
           this.filePath = filePath;
           this.nonActivePeriod = nonActivePeriod;
    }


    /**
     * Gets the filePath value for this LoadClientsActionRq.
     * 
     * @return filePath
     */
    public java.lang.String getFilePath() {
        return filePath;
    }


    /**
     * Sets the filePath value for this LoadClientsActionRq.
     * 
     * @param filePath
     */
    public void setFilePath(java.lang.String filePath) {
        this.filePath = filePath;
    }


    /**
     * Gets the nonActivePeriod value for this LoadClientsActionRq.
     * 
     * @return nonActivePeriod
     */
    public int getNonActivePeriod() {
        return nonActivePeriod;
    }


    /**
     * Sets the nonActivePeriod value for this LoadClientsActionRq.
     * 
     * @param nonActivePeriod
     */
    public void setNonActivePeriod(int nonActivePeriod) {
        this.nonActivePeriod = nonActivePeriod;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoadClientsActionRq)) return false;
        LoadClientsActionRq other = (LoadClientsActionRq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.filePath==null && other.getFilePath()==null) || 
             (this.filePath!=null &&
              this.filePath.equals(other.getFilePath()))) &&
            this.nonActivePeriod == other.getNonActivePeriod();
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
        if (getFilePath() != null) {
            _hashCode += getFilePath().hashCode();
        }
        _hashCode += getNonActivePeriod();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoadClientsActionRq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.task.sbrf.ru", ">loadClientsActionRq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filePath");
        elemField.setXmlName(new javax.xml.namespace.QName("http://service.task.sbrf.ru", "filePath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nonActivePeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://service.task.sbrf.ru", "nonActivePeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
