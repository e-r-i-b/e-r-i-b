/**
 * DeviceManagementResponsePayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines the device management response payload
 */
public class DeviceManagementResponsePayload  implements java.io.Serializable {
    private java.lang.String acspAccountId;

    private com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData[] deviceData;

    public DeviceManagementResponsePayload() {
    }

    public DeviceManagementResponsePayload(
           java.lang.String acspAccountId,
           com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData[] deviceData) {
           this.acspAccountId = acspAccountId;
           this.callStatus = callStatus;
           this.deviceData = deviceData;
    }


    /**
     * Gets the acspAccountId value for this DeviceManagementResponsePayload.
     * 
     * @return acspAccountId
     */
    public java.lang.String getAcspAccountId() {
        return acspAccountId;
    }


    /**
     * Sets the acspAccountId value for this DeviceManagementResponsePayload.
     * 
     * @param acspAccountId
     */
    public void setAcspAccountId(java.lang.String acspAccountId) {
        this.acspAccountId = acspAccountId;
    }


    /**
     * Gets the callStatus value for this DeviceManagementResponsePayload.
     * 
     * @return callStatus
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus getCallStatus() {
        return callStatus;
    }


    /**
     * Sets the callStatus value for this DeviceManagementResponsePayload.
     * 
     * @param callStatus
     */
    public void setCallStatus(com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus callStatus) {
        this.callStatus = callStatus;
    }


    /**
     * Gets the deviceData value for this DeviceManagementResponsePayload.
     * 
     * @return deviceData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData[] getDeviceData() {
        return deviceData;
    }


    /**
     * Sets the deviceData value for this DeviceManagementResponsePayload.
     * 
     * @param deviceData
     */
    public void setDeviceData(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData[] deviceData) {
        this.deviceData = deviceData;
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData getDeviceData(int i) {
        return this.deviceData[i];
    }

    public void setDeviceData(int i, com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData _value) {
        this.deviceData[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeviceManagementResponsePayload)) return false;
        DeviceManagementResponsePayload other = (DeviceManagementResponsePayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acspAccountId==null && other.getAcspAccountId()==null) || 
             (this.acspAccountId!=null &&
              this.acspAccountId.equals(other.getAcspAccountId()))) &&
            ((this.callStatus==null && other.getCallStatus()==null) || 
             (this.callStatus!=null &&
              this.callStatus.equals(other.getCallStatus()))) &&
            ((this.deviceData==null && other.getDeviceData()==null) || 
             (this.deviceData!=null &&
              java.util.Arrays.equals(this.deviceData, other.getDeviceData())));
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
        if (getAcspAccountId() != null) {
            _hashCode += getAcspAccountId().hashCode();
        }
        if (getCallStatus() != null) {
            _hashCode += getCallStatus().hashCode();
        }
        if (getDeviceData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeviceData());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeviceData(), i);
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
        new org.apache.axis.description.TypeDesc(DeviceManagementResponsePayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceManagementResponsePayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acspAccountId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "acspAccountId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "callStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CallStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceData"));
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
