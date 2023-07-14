/**
 * SecurityMarker_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Маркер ценной бумаги
 */
public class SecurityMarker_Type  implements java.io.Serializable {
    /* Остаток, кол-во ценных бумаг данного вида */
    private long remainder;

    /* Информация о ценной бумаге */
    private java.lang.String markerDescription;

    public SecurityMarker_Type() {
    }

    public SecurityMarker_Type(
           long remainder,
           java.lang.String markerDescription) {
           this.remainder = remainder;
           this.markerDescription = markerDescription;
    }


    /**
     * Gets the remainder value for this SecurityMarker_Type.
     * 
     * @return remainder   * Остаток, кол-во ценных бумаг данного вида
     */
    public long getRemainder() {
        return remainder;
    }


    /**
     * Sets the remainder value for this SecurityMarker_Type.
     * 
     * @param remainder   * Остаток, кол-во ценных бумаг данного вида
     */
    public void setRemainder(long remainder) {
        this.remainder = remainder;
    }


    /**
     * Gets the markerDescription value for this SecurityMarker_Type.
     * 
     * @return markerDescription   * Информация о ценной бумаге
     */
    public java.lang.String getMarkerDescription() {
        return markerDescription;
    }


    /**
     * Sets the markerDescription value for this SecurityMarker_Type.
     * 
     * @param markerDescription   * Информация о ценной бумаге
     */
    public void setMarkerDescription(java.lang.String markerDescription) {
        this.markerDescription = markerDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecurityMarker_Type)) return false;
        SecurityMarker_Type other = (SecurityMarker_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.remainder == other.getRemainder() &&
            ((this.markerDescription==null && other.getMarkerDescription()==null) || 
             (this.markerDescription!=null &&
              this.markerDescription.equals(other.getMarkerDescription())));
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
        _hashCode += new Long(getRemainder()).hashCode();
        if (getMarkerDescription() != null) {
            _hashCode += getMarkerDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecurityMarker_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityMarker_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remainder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Remainder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("markerDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarkerDescription"));
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
