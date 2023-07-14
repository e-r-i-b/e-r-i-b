/**
 * ResponseData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.mdm.client.service.generated;


/**
 * Тип данных для данных ответа
 */
public class ResponseData  implements java.io.Serializable {
    private com.rssl.phizic.mdm.client.service.generated.GetStoredMDMIdResultType getStoredMDMIdRs;

    private com.rssl.phizic.mdm.client.service.generated.GetExternalSystemMDMIdResultType getExternalSystemMDMIdRs;

    public ResponseData() {
    }

    public ResponseData(
           com.rssl.phizic.mdm.client.service.generated.GetStoredMDMIdResultType getStoredMDMIdRs,
           com.rssl.phizic.mdm.client.service.generated.GetExternalSystemMDMIdResultType getExternalSystemMDMIdRs) {
           this.getStoredMDMIdRs = getStoredMDMIdRs;
           this.getExternalSystemMDMIdRs = getExternalSystemMDMIdRs;
    }


    /**
     * Gets the getStoredMDMIdRs value for this ResponseData.
     * 
     * @return getStoredMDMIdRs
     */
    public com.rssl.phizic.mdm.client.service.generated.GetStoredMDMIdResultType getGetStoredMDMIdRs() {
        return getStoredMDMIdRs;
    }


    /**
     * Sets the getStoredMDMIdRs value for this ResponseData.
     * 
     * @param getStoredMDMIdRs
     */
    public void setGetStoredMDMIdRs(com.rssl.phizic.mdm.client.service.generated.GetStoredMDMIdResultType getStoredMDMIdRs) {
        this.getStoredMDMIdRs = getStoredMDMIdRs;
    }


    /**
     * Gets the getExternalSystemMDMIdRs value for this ResponseData.
     * 
     * @return getExternalSystemMDMIdRs
     */
    public com.rssl.phizic.mdm.client.service.generated.GetExternalSystemMDMIdResultType getGetExternalSystemMDMIdRs() {
        return getExternalSystemMDMIdRs;
    }


    /**
     * Sets the getExternalSystemMDMIdRs value for this ResponseData.
     * 
     * @param getExternalSystemMDMIdRs
     */
    public void setGetExternalSystemMDMIdRs(com.rssl.phizic.mdm.client.service.generated.GetExternalSystemMDMIdResultType getExternalSystemMDMIdRs) {
        this.getExternalSystemMDMIdRs = getExternalSystemMDMIdRs;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseData)) return false;
        ResponseData other = (ResponseData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getStoredMDMIdRs==null && other.getGetStoredMDMIdRs()==null) || 
             (this.getStoredMDMIdRs!=null &&
              this.getStoredMDMIdRs.equals(other.getGetStoredMDMIdRs()))) &&
            ((this.getExternalSystemMDMIdRs==null && other.getGetExternalSystemMDMIdRs()==null) || 
             (this.getExternalSystemMDMIdRs!=null &&
              this.getExternalSystemMDMIdRs.equals(other.getGetExternalSystemMDMIdRs())));
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
        if (getGetStoredMDMIdRs() != null) {
            _hashCode += getGetStoredMDMIdRs().hashCode();
        }
        if (getGetExternalSystemMDMIdRs() != null) {
            _hashCode += getGetExternalSystemMDMIdRs().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "ResponseData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getStoredMDMIdRs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetStoredMDMIdRs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetStoredMDMIdResultType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getExternalSystemMDMIdRs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetExternalSystemMDMIdRs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetExternalSystemMDMIdResultType"));
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
