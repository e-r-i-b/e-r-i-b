/**
 * SelRangeDtTm_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Тип для временного периода (дата и время)
 */
public class SelRangeDtTm_Type  implements java.io.Serializable {
    /* Начальная дата и время периода */
    private java.lang.String startDtTm;

    /* Последняя дата и время периода */
    private java.lang.String endDtTm;

    public SelRangeDtTm_Type() {
    }

    public SelRangeDtTm_Type(
           java.lang.String startDtTm,
           java.lang.String endDtTm) {
           this.startDtTm = startDtTm;
           this.endDtTm = endDtTm;
    }


    /**
     * Gets the startDtTm value for this SelRangeDtTm_Type.
     * 
     * @return startDtTm   * Начальная дата и время периода
     */
    public java.lang.String getStartDtTm() {
        return startDtTm;
    }


    /**
     * Sets the startDtTm value for this SelRangeDtTm_Type.
     * 
     * @param startDtTm   * Начальная дата и время периода
     */
    public void setStartDtTm(java.lang.String startDtTm) {
        this.startDtTm = startDtTm;
    }


    /**
     * Gets the endDtTm value for this SelRangeDtTm_Type.
     * 
     * @return endDtTm   * Последняя дата и время периода
     */
    public java.lang.String getEndDtTm() {
        return endDtTm;
    }


    /**
     * Sets the endDtTm value for this SelRangeDtTm_Type.
     * 
     * @param endDtTm   * Последняя дата и время периода
     */
    public void setEndDtTm(java.lang.String endDtTm) {
        this.endDtTm = endDtTm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SelRangeDtTm_Type)) return false;
        SelRangeDtTm_Type other = (SelRangeDtTm_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.startDtTm==null && other.getStartDtTm()==null) || 
             (this.startDtTm!=null &&
              this.startDtTm.equals(other.getStartDtTm()))) &&
            ((this.endDtTm==null && other.getEndDtTm()==null) || 
             (this.endDtTm!=null &&
              this.endDtTm.equals(other.getEndDtTm())));
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
        if (getStartDtTm() != null) {
            _hashCode += getStartDtTm().hashCode();
        }
        if (getEndDtTm() != null) {
            _hashCode += getEndDtTm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SelRangeDtTm_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SelRangeDtTm_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDtTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StartDtTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDtTm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDtTm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
