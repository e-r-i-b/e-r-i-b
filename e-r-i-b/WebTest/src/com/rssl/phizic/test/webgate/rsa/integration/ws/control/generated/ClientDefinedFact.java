/**
 * ClientDefinedFact.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class ClientDefinedFact  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.NamedValue  implements java.io.Serializable {
    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DataType dataType;

    public ClientDefinedFact() {
    }

    public ClientDefinedFact(
           java.lang.String name,
           java.lang.String value,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DataType dataType) {
        super(
            name,
            value);
        this.dataType = dataType;
    }


    /**
     * Gets the dataType value for this ClientDefinedFact.
     * 
     * @return dataType
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DataType getDataType() {
        return dataType;
    }


    /**
     * Sets the dataType value for this ClientDefinedFact.
     * 
     * @param dataType
     */
    public void setDataType(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DataType dataType) {
        this.dataType = dataType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientDefinedFact)) return false;
        ClientDefinedFact other = (ClientDefinedFact) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataType==null && other.getDataType()==null) || 
             (this.dataType!=null &&
              this.dataType.equals(other.getDataType())));
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
        if (getDataType() != null) {
            _hashCode += getDataType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientDefinedFact.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientDefinedFact"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "dataType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DataType"));
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
