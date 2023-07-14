/**
 * GetStoredMDMIdParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.mdm.web.service.generated;


/**
 * Тип данных для данных запроса поиска mdm_id в БД
 */
public class GetStoredMDMIdParametersType  implements java.io.Serializable {
    private java.lang.Long innerId;

    public GetStoredMDMIdParametersType() {
    }

    public GetStoredMDMIdParametersType(
           java.lang.Long innerId) {
           this.innerId = innerId;
    }


    /**
     * Gets the innerId value for this GetStoredMDMIdParametersType.
     * 
     * @return innerId
     */
    public java.lang.Long getInnerId() {
        return innerId;
    }


    /**
     * Sets the innerId value for this GetStoredMDMIdParametersType.
     * 
     * @param innerId
     */
    public void setInnerId(java.lang.Long innerId) {
        this.innerId = innerId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetStoredMDMIdParametersType)) return false;
        GetStoredMDMIdParametersType other = (GetStoredMDMIdParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.innerId==null && other.getInnerId()==null) || 
             (this.innerId!=null &&
              this.innerId.equals(other.getInnerId())));
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
        if (getInnerId() != null) {
            _hashCode += getInnerId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetStoredMDMIdParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "GetStoredMDMIdParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("innerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://com.rssl.phizic.mdm.app/erib/adapter", "innerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
