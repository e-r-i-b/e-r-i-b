/**
 * DeviceData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines device information
 */
public class DeviceData  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.BindingType bindingType;

    private java.lang.String deviceTokenCookie;

    private java.lang.String deviceTokenFSO;

    private java.lang.String lookupLabel;

    private java.lang.String newLabel;

    public DeviceData() {
    }

    public DeviceData(
           com.rssl.phizic.rsa.integration.ws.control.generated.BindingType bindingType,
           java.lang.String deviceTokenCookie,
           java.lang.String deviceTokenFSO,
           java.lang.String lookupLabel,
           java.lang.String newLabel) {
           this.bindingType = bindingType;
           this.deviceTokenCookie = deviceTokenCookie;
           this.deviceTokenFSO = deviceTokenFSO;
           this.lookupLabel = lookupLabel;
           this.newLabel = newLabel;
    }


    /**
     * Gets the bindingType value for this DeviceData.
     * 
     * @return bindingType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.BindingType getBindingType() {
        return bindingType;
    }


    /**
     * Sets the bindingType value for this DeviceData.
     * 
     * @param bindingType
     */
    public void setBindingType(com.rssl.phizic.rsa.integration.ws.control.generated.BindingType bindingType) {
        this.bindingType = bindingType;
    }


    /**
     * Gets the deviceTokenCookie value for this DeviceData.
     * 
     * @return deviceTokenCookie
     */
    public java.lang.String getDeviceTokenCookie() {
        return deviceTokenCookie;
    }


    /**
     * Sets the deviceTokenCookie value for this DeviceData.
     * 
     * @param deviceTokenCookie
     */
    public void setDeviceTokenCookie(java.lang.String deviceTokenCookie) {
        this.deviceTokenCookie = deviceTokenCookie;
    }


    /**
     * Gets the deviceTokenFSO value for this DeviceData.
     * 
     * @return deviceTokenFSO
     */
    public java.lang.String getDeviceTokenFSO() {
        return deviceTokenFSO;
    }


    /**
     * Sets the deviceTokenFSO value for this DeviceData.
     * 
     * @param deviceTokenFSO
     */
    public void setDeviceTokenFSO(java.lang.String deviceTokenFSO) {
        this.deviceTokenFSO = deviceTokenFSO;
    }


    /**
     * Gets the lookupLabel value for this DeviceData.
     * 
     * @return lookupLabel
     */
    public java.lang.String getLookupLabel() {
        return lookupLabel;
    }


    /**
     * Sets the lookupLabel value for this DeviceData.
     * 
     * @param lookupLabel
     */
    public void setLookupLabel(java.lang.String lookupLabel) {
        this.lookupLabel = lookupLabel;
    }


    /**
     * Gets the newLabel value for this DeviceData.
     * 
     * @return newLabel
     */
    public java.lang.String getNewLabel() {
        return newLabel;
    }


    /**
     * Sets the newLabel value for this DeviceData.
     * 
     * @param newLabel
     */
    public void setNewLabel(java.lang.String newLabel) {
        this.newLabel = newLabel;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeviceData)) return false;
        DeviceData other = (DeviceData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bindingType==null && other.getBindingType()==null) || 
             (this.bindingType!=null &&
              this.bindingType.equals(other.getBindingType()))) &&
            ((this.deviceTokenCookie==null && other.getDeviceTokenCookie()==null) || 
             (this.deviceTokenCookie!=null &&
              this.deviceTokenCookie.equals(other.getDeviceTokenCookie()))) &&
            ((this.deviceTokenFSO==null && other.getDeviceTokenFSO()==null) || 
             (this.deviceTokenFSO!=null &&
              this.deviceTokenFSO.equals(other.getDeviceTokenFSO()))) &&
            ((this.lookupLabel==null && other.getLookupLabel()==null) || 
             (this.lookupLabel!=null &&
              this.lookupLabel.equals(other.getLookupLabel()))) &&
            ((this.newLabel==null && other.getNewLabel()==null) || 
             (this.newLabel!=null &&
              this.newLabel.equals(other.getNewLabel())));
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
        if (getBindingType() != null) {
            _hashCode += getBindingType().hashCode();
        }
        if (getDeviceTokenCookie() != null) {
            _hashCode += getDeviceTokenCookie().hashCode();
        }
        if (getDeviceTokenFSO() != null) {
            _hashCode += getDeviceTokenFSO().hashCode();
        }
        if (getLookupLabel() != null) {
            _hashCode += getLookupLabel().hashCode();
        }
        if (getNewLabel() != null) {
            _hashCode += getNewLabel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeviceData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bindingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "bindingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "BindingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceTokenCookie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceTokenCookie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceTokenFSO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceTokenFSO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lookupLabel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "lookupLabel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newLabel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "newLabel"));
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
