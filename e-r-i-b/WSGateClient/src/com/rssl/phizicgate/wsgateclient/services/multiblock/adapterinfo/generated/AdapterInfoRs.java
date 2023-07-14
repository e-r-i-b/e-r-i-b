/**
 * AdapterInfoRs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated;


/**
 * Ответ на запрос информации по платежу с использованием идентификатора
 * платежа и заказа
 */
public class AdapterInfoRs  implements java.io.Serializable {
    private java.lang.String address;

    private com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.StatusType status;

    public AdapterInfoRs() {
    }

    public AdapterInfoRs(
           java.lang.String address,
           com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.StatusType status) {
           this.address = address;
           this.status = status;
    }


    /**
     * Gets the address value for this AdapterInfoRs.
     * 
     * @return address
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this AdapterInfoRs.
     * 
     * @param address
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }


    /**
     * Gets the status value for this AdapterInfoRs.
     * 
     * @return status
     */
    public com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.StatusType getStatus() {
        return status;
    }


    /**
     * Sets the status value for this AdapterInfoRs.
     * 
     * @param status
     */
    public void setStatus(com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.generated.StatusType status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdapterInfoRs)) return false;
        AdapterInfoRs other = (AdapterInfoRs) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AdapterInfoRs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "AdapterInfoRs"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.adapterinfo.multiblock.phizic.rssl.com", "StatusType"));
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
