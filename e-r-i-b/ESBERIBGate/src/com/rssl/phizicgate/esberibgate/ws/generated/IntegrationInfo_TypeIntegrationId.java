/**
 * IntegrationInfo_TypeIntegrationId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;

public class IntegrationInfo_TypeIntegrationId  implements java.io.Serializable {
    /* Код системы, в которой будут проведены изменения */
    private java.lang.String ISCode;

    /* Идентификатор клиента */
    private java.lang.String ISCustId;

    public IntegrationInfo_TypeIntegrationId() {
    }

    public IntegrationInfo_TypeIntegrationId(
           java.lang.String ISCode,
           java.lang.String ISCustId) {
           this.ISCode = ISCode;
           this.ISCustId = ISCustId;
    }


    /**
     * Gets the ISCode value for this IntegrationInfo_TypeIntegrationId.
     * 
     * @return ISCode   * Код системы, в которой будут проведены изменения
     */
    public java.lang.String getISCode() {
        return ISCode;
    }


    /**
     * Sets the ISCode value for this IntegrationInfo_TypeIntegrationId.
     * 
     * @param ISCode   * Код системы, в которой будут проведены изменения
     */
    public void setISCode(java.lang.String ISCode) {
        this.ISCode = ISCode;
    }


    /**
     * Gets the ISCustId value for this IntegrationInfo_TypeIntegrationId.
     * 
     * @return ISCustId   * Идентификатор клиента
     */
    public java.lang.String getISCustId() {
        return ISCustId;
    }


    /**
     * Sets the ISCustId value for this IntegrationInfo_TypeIntegrationId.
     * 
     * @param ISCustId   * Идентификатор клиента
     */
    public void setISCustId(java.lang.String ISCustId) {
        this.ISCustId = ISCustId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IntegrationInfo_TypeIntegrationId)) return false;
        IntegrationInfo_TypeIntegrationId other = (IntegrationInfo_TypeIntegrationId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ISCode==null && other.getISCode()==null) || 
             (this.ISCode!=null &&
              this.ISCode.equals(other.getISCode()))) &&
            ((this.ISCustId==null && other.getISCustId()==null) || 
             (this.ISCustId!=null &&
              this.ISCustId.equals(other.getISCustId())));
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
        if (getISCode() != null) {
            _hashCode += getISCode().hashCode();
        }
        if (getISCustId() != null) {
            _hashCode += getISCustId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IntegrationInfo_TypeIntegrationId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">IntegrationInfo_Type>IntegrationId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ISCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ISCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ISCustId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ISCustId"));
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
