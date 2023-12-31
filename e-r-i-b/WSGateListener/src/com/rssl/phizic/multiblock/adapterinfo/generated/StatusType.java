/**
 * StatusType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.multiblock.adapterinfo.generated;

public class StatusType  implements java.io.Serializable {
    private long statusCode;

    private java.lang.String statusDescription;

    public StatusType() {
    }

    public StatusType(
           long statusCode,
           java.lang.String statusDescription) {
           this.statusCode = statusCode;
           this.statusDescription = statusDescription;
    }


    /**
     * Gets the statusCode value for this StatusType.
     * 
     * @return statusCode
     */
    public long getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this StatusType.
     * 
     * @param statusCode
     */
    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the statusDescription value for this StatusType.
     * 
     * @return statusDescription
     */
    public java.lang.String getStatusDescription() {
        return statusDescription;
    }


    /**
     * Sets the statusDescription value for this StatusType.
     * 
     * @param statusDescription
     */
    public void setStatusDescription(java.lang.String statusDescription) {
        this.statusDescription = statusDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatusType)) return false;
        StatusType other = (StatusType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.statusCode == other.getStatusCode() &&
            ((this.statusDescription==null && other.getStatusDescription()==null) || 
             (this.statusDescription!=null &&
              this.statusDescription.equals(other.getStatusDescription())));
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
        _hashCode += new Long(getStatusCode()).hashCode();
        if (getStatusDescription() != null) {
            _hashCode += getStatusDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatusType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "StatusType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "StatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "LongType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "StatusDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
