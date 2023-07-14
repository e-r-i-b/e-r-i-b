/**
 * DeviceResult_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.gate.monitoring.fraud.ws.generated;


/**
 * Returns the results of device credential authentication
 */
public class DeviceResult_Type  implements java.io.Serializable {
    private com.rssl.phizic.gate.monitoring.fraud.ws.generated.DeviceData_Type deviceData;

    public DeviceResult_Type() {
    }

    public DeviceResult_Type(
           com.rssl.phizic.gate.monitoring.fraud.ws.generated.DeviceData_Type deviceData) {
           this.deviceData = deviceData;
    }


    /**
     * Gets the deviceData value for this DeviceResult_Type.
     * 
     * @return deviceData
     */
    public com.rssl.phizic.gate.monitoring.fraud.ws.generated.DeviceData_Type getDeviceData() {
        return deviceData;
    }


    /**
     * Sets the deviceData value for this DeviceResult_Type.
     * 
     * @param deviceData
     */
    public void setDeviceData(com.rssl.phizic.gate.monitoring.fraud.ws.generated.DeviceData_Type deviceData) {
        this.deviceData = deviceData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeviceResult_Type)) return false;
        DeviceResult_Type other = (DeviceResult_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.deviceData==null && other.getDeviceData()==null) || 
             (this.deviceData!=null &&
              this.deviceData.equals(other.getDeviceData())));
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
        if (getDeviceData() != null) {
            _hashCode += getDeviceData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeviceResult_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "DeviceResult_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "deviceData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://async-interaction/fraud/monitoring/back/gate", "DeviceData_Type"));
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
