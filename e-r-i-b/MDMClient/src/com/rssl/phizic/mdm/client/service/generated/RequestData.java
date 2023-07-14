/**
 * RequestData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.mdm.client.service.generated;


/**
 * Тип данных для данных запроса
 */
public class RequestData  implements java.io.Serializable {
    private com.rssl.phizic.mdm.client.service.generated.GetStoredMDMIdParametersType getStoredMDMIdRq;

    private com.rssl.phizic.mdm.client.service.generated.GetExternalSystemMDMIdParametersType getExternalSystemMDMIdRq;

    public RequestData() {
    }

    public RequestData(
           com.rssl.phizic.mdm.client.service.generated.GetStoredMDMIdParametersType getStoredMDMIdRq,
           com.rssl.phizic.mdm.client.service.generated.GetExternalSystemMDMIdParametersType getExternalSystemMDMIdRq) {
           this.getStoredMDMIdRq = getStoredMDMIdRq;
           this.getExternalSystemMDMIdRq = getExternalSystemMDMIdRq;
    }


    /**
     * Gets the getStoredMDMIdRq value for this RequestData.
     * 
     * @return getStoredMDMIdRq
     */
    public com.rssl.phizic.mdm.client.service.generated.GetStoredMDMIdParametersType getGetStoredMDMIdRq() {
        return getStoredMDMIdRq;
    }


    /**
     * Sets the getStoredMDMIdRq value for this RequestData.
     * 
     * @param getStoredMDMIdRq
     */
    public void setGetStoredMDMIdRq(com.rssl.phizic.mdm.client.service.generated.GetStoredMDMIdParametersType getStoredMDMIdRq) {
        this.getStoredMDMIdRq = getStoredMDMIdRq;
    }


    /**
     * Gets the getExternalSystemMDMIdRq value for this RequestData.
     * 
     * @return getExternalSystemMDMIdRq
     */
    public com.rssl.phizic.mdm.client.service.generated.GetExternalSystemMDMIdParametersType getGetExternalSystemMDMIdRq() {
        return getExternalSystemMDMIdRq;
    }


    /**
     * Sets the getExternalSystemMDMIdRq value for this RequestData.
     * 
     * @param getExternalSystemMDMIdRq
     */
    public void setGetExternalSystemMDMIdRq(com.rssl.phizic.mdm.client.service.generated.GetExternalSystemMDMIdParametersType getExternalSystemMDMIdRq) {
        this.getExternalSystemMDMIdRq = getExternalSystemMDMIdRq;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestData)) return false;
        RequestData other = (RequestData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getStoredMDMIdRq==null && other.getGetStoredMDMIdRq()==null) || 
             (this.getStoredMDMIdRq!=null &&
              this.getStoredMDMIdRq.equals(other.getGetStoredMDMIdRq()))) &&
            ((this.getExternalSystemMDMIdRq==null && other.getGetExternalSystemMDMIdRq()==null) || 
             (this.getExternalSystemMDMIdRq!=null &&
              this.getExternalSystemMDMIdRq.equals(other.getGetExternalSystemMDMIdRq())));
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
        if (getGetStoredMDMIdRq() != null) {
            _hashCode += getGetStoredMDMIdRq().hashCode();
        }
        if (getGetExternalSystemMDMIdRq() != null) {
            _hashCode += getGetExternalSystemMDMIdRq().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "RequestData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getStoredMDMIdRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetStoredMDMIdRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetStoredMDMIdParametersType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getExternalSystemMDMIdRq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetExternalSystemMDMIdRq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetExternalSystemMDMIdParametersType"));
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
