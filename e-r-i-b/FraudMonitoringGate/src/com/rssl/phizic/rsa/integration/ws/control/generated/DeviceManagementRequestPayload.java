/**
 * DeviceManagementRequestPayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines the device management request payload
 */
public class DeviceManagementRequestPayload  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceActionTypeList actionTypeList;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData deviceData;

    public DeviceManagementRequestPayload() {
    }

    public DeviceManagementRequestPayload(
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceActionTypeList actionTypeList,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData deviceData) {
           this.actionTypeList = actionTypeList;
           this.deviceData = deviceData;
    }


    /**
     * Gets the actionTypeList value for this DeviceManagementRequestPayload.
     * 
     * @return actionTypeList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceActionTypeList getActionTypeList() {
        return actionTypeList;
    }


    /**
     * Sets the actionTypeList value for this DeviceManagementRequestPayload.
     * 
     * @param actionTypeList
     */
    public void setActionTypeList(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceActionTypeList actionTypeList) {
        this.actionTypeList = actionTypeList;
    }


    /**
     * Gets the deviceData value for this DeviceManagementRequestPayload.
     * 
     * @return deviceData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData getDeviceData() {
        return deviceData;
    }


    /**
     * Sets the deviceData value for this DeviceManagementRequestPayload.
     * 
     * @param deviceData
     */
    public void setDeviceData(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData deviceData) {
        this.deviceData = deviceData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeviceManagementRequestPayload)) return false;
        DeviceManagementRequestPayload other = (DeviceManagementRequestPayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actionTypeList==null && other.getActionTypeList()==null) || 
             (this.actionTypeList!=null &&
              this.actionTypeList.equals(other.getActionTypeList()))) &&
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
        if (getActionTypeList() != null) {
            _hashCode += getActionTypeList().hashCode();
        }
        if (getDeviceData() != null) {
            _hashCode += getDeviceData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeviceManagementRequestPayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceManagementRequestPayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionTypeList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "actionTypeList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceActionTypeList"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceData"));
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
