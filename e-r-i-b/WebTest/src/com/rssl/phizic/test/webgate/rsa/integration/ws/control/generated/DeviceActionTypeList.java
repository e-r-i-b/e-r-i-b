/**
 * DeviceActionTypeList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class DeviceActionTypeList  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.ActionTypeList  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceActionType[] deviceActionTypes;

    public DeviceActionTypeList() {
    }

    public DeviceActionTypeList(
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceActionType[] deviceActionTypes) {
        this.deviceActionTypes = deviceActionTypes;
    }


    /**
     * Gets the deviceActionTypes value for this DeviceActionTypeList.
     * 
     * @return deviceActionTypes
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceActionType[] getDeviceActionTypes() {
        return deviceActionTypes;
    }


    /**
     * Sets the deviceActionTypes value for this DeviceActionTypeList.
     * 
     * @param deviceActionTypes
     */
    public void setDeviceActionTypes(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceActionType[] deviceActionTypes) {
        this.deviceActionTypes = deviceActionTypes;
    }

    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceActionType getDeviceActionTypes(int i) {
        return this.deviceActionTypes[i];
    }

    public void setDeviceActionTypes(int i, com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceActionType _value) {
        this.deviceActionTypes[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeviceActionTypeList)) return false;
        DeviceActionTypeList other = (DeviceActionTypeList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.deviceActionTypes==null && other.getDeviceActionTypes()==null) || 
             (this.deviceActionTypes!=null &&
              java.util.Arrays.equals(this.deviceActionTypes, other.getDeviceActionTypes())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDeviceActionTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeviceActionTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeviceActionTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeviceActionTypeList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceActionTypeList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceActionTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceActionTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceActionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
