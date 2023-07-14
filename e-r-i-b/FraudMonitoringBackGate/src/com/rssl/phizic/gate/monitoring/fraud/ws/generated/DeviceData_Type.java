/**
 * DeviceData_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.gate.monitoring.fraud.ws.generated;


/**
 * This defines device information
 */
public class DeviceData_Type  implements java.io.Serializable {
    private java.lang.String deviceTokenCookie;

    private java.lang.String deviceTokenFSO;

    public DeviceData_Type() {
    }

    public DeviceData_Type(
           java.lang.String deviceTokenCookie,
           java.lang.String deviceTokenFSO) {
           this.deviceTokenCookie = deviceTokenCookie;
           this.deviceTokenFSO = deviceTokenFSO;
    }


    /**
     * Gets the deviceTokenCookie value for this DeviceData_Type.
     * 
     * @return deviceTokenCookie
     */
    public java.lang.String getDeviceTokenCookie() {
        return deviceTokenCookie;
    }


    /**
     * Sets the deviceTokenCookie value for this DeviceData_Type.
     * 
     * @param deviceTokenCookie
     */
    public void setDeviceTokenCookie(java.lang.String deviceTokenCookie) {
        this.deviceTokenCookie = deviceTokenCookie;
    }


    /**
     * Gets the deviceTokenFSO value for this DeviceData_Type.
     * 
     * @return deviceTokenFSO
     */
    public java.lang.String getDeviceTokenFSO() {
        return deviceTokenFSO;
    }


    /**
     * Sets the deviceTokenFSO value for this DeviceData_Type.
     * 
     * @param deviceTokenFSO
     */
    public void setDeviceTokenFSO(java.lang.String deviceTokenFSO) {
        this.deviceTokenFSO = deviceTokenFSO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeviceData_Type)) return false;
        DeviceData_Type other = (DeviceData_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.deviceTokenCookie==null && other.getDeviceTokenCookie()==null) || 
             (this.deviceTokenCookie!=null &&
              this.deviceTokenCookie.equals(other.getDeviceTokenCookie()))) &&
            ((this.deviceTokenFSO==null && other.getDeviceTokenFSO()==null) || 
             (this.deviceTokenFSO!=null &&
              this.deviceTokenFSO.equals(other.getDeviceTokenFSO())));
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
        if (getDeviceTokenCookie() != null) {
            _hashCode += getDeviceTokenCookie().hashCode();
        }
        if (getDeviceTokenFSO() != null) {
            _hashCode += getDeviceTokenFSO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeviceData_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "DeviceData_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceTokenCookie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "deviceTokenCookie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceTokenFSO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "deviceTokenFSO"));
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
