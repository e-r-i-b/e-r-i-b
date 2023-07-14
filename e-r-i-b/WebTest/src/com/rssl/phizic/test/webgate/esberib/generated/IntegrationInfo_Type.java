/**
 * IntegrationInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Идентификаторы клиента в ИС <IntegrationInfo>
 */
public class IntegrationInfo_Type  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.esberib.generated.IntegrationInfo_TypeIntegrationId integrationId;

    public IntegrationInfo_Type() {
    }

    public IntegrationInfo_Type(
           com.rssl.phizic.test.webgate.esberib.generated.IntegrationInfo_TypeIntegrationId integrationId) {
           this.integrationId = integrationId;
    }


    /**
     * Gets the integrationId value for this IntegrationInfo_Type.
     * 
     * @return integrationId
     */
    public com.rssl.phizic.test.webgate.esberib.generated.IntegrationInfo_TypeIntegrationId getIntegrationId() {
        return integrationId;
    }


    /**
     * Sets the integrationId value for this IntegrationInfo_Type.
     * 
     * @param integrationId
     */
    public void setIntegrationId(com.rssl.phizic.test.webgate.esberib.generated.IntegrationInfo_TypeIntegrationId integrationId) {
        this.integrationId = integrationId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IntegrationInfo_Type)) return false;
        IntegrationInfo_Type other = (IntegrationInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.integrationId==null && other.getIntegrationId()==null) ||
             (this.integrationId!=null &&
              this.integrationId.equals(other.getIntegrationId())));
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
        if (getIntegrationId() != null) {
            _hashCode += getIntegrationId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IntegrationInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IntegrationInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("integrationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IntegrationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">IntegrationInfo_Type>IntegrationId"));
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
