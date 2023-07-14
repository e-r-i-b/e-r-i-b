/**
 * SaveAccessSchemeParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class SaveAccessSchemeParametersType  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType accessScheme;

    public SaveAccessSchemeParametersType() {
    }

    public SaveAccessSchemeParametersType(
           com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType accessScheme) {
           this.accessScheme = accessScheme;
    }


    /**
     * Gets the accessScheme value for this SaveAccessSchemeParametersType.
     * 
     * @return accessScheme
     */
    public com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType getAccessScheme() {
        return accessScheme;
    }


    /**
     * Sets the accessScheme value for this SaveAccessSchemeParametersType.
     * 
     * @param accessScheme
     */
    public void setAccessScheme(com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType accessScheme) {
        this.accessScheme = accessScheme;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SaveAccessSchemeParametersType)) return false;
        SaveAccessSchemeParametersType other = (SaveAccessSchemeParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accessScheme==null && other.getAccessScheme()==null) || 
             (this.accessScheme!=null &&
              this.accessScheme.equals(other.getAccessScheme())));
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
        if (getAccessScheme() != null) {
            _hashCode += getAccessScheme().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SaveAccessSchemeParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAccessSchemeParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessScheme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AccessScheme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AccessSchemeType"));
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
