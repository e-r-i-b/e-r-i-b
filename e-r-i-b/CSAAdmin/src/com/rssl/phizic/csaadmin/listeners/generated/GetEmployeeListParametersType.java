/**
 * GetEmployeeListParametersType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class GetEmployeeListParametersType  implements java.io.Serializable {
    private com.rssl.phizic.csaadmin.listeners.generated.EmployeeListFilterParametersType filter;

    public GetEmployeeListParametersType() {
    }

    public GetEmployeeListParametersType(
           com.rssl.phizic.csaadmin.listeners.generated.EmployeeListFilterParametersType filter) {
           this.filter = filter;
    }


    /**
     * Gets the filter value for this GetEmployeeListParametersType.
     * 
     * @return filter
     */
    public com.rssl.phizic.csaadmin.listeners.generated.EmployeeListFilterParametersType getFilter() {
        return filter;
    }


    /**
     * Sets the filter value for this GetEmployeeListParametersType.
     * 
     * @param filter
     */
    public void setFilter(com.rssl.phizic.csaadmin.listeners.generated.EmployeeListFilterParametersType filter) {
        this.filter = filter;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetEmployeeListParametersType)) return false;
        GetEmployeeListParametersType other = (GetEmployeeListParametersType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.filter==null && other.getFilter()==null) || 
             (this.filter!=null &&
              this.filter.equals(other.getFilter())));
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
        if (getFilter() != null) {
            _hashCode += getFilter().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetEmployeeListParametersType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeListParametersType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "Filter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeListFilterParametersType"));
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
