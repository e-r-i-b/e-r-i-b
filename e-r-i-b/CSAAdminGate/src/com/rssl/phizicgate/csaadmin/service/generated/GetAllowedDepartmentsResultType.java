/**
 * GetAllowedDepartmentsResultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.csaadmin.service.generated;

public class GetAllowedDepartmentsResultType  implements java.io.Serializable {
    private com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] allowedDepartments;

    public GetAllowedDepartmentsResultType() {
    }

    public GetAllowedDepartmentsResultType(
           com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] allowedDepartments) {
           this.allowedDepartments = allowedDepartments;
    }


    /**
     * Gets the allowedDepartments value for this GetAllowedDepartmentsResultType.
     * 
     * @return allowedDepartments
     */
    public com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] getAllowedDepartments() {
        return allowedDepartments;
    }


    /**
     * Sets the allowedDepartments value for this GetAllowedDepartmentsResultType.
     * 
     * @param allowedDepartments
     */
    public void setAllowedDepartments(com.rssl.phizicgate.csaadmin.service.generated.DepartmentType[] allowedDepartments) {
        this.allowedDepartments = allowedDepartments;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAllowedDepartmentsResultType)) return false;
        GetAllowedDepartmentsResultType other = (GetAllowedDepartmentsResultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allowedDepartments==null && other.getAllowedDepartments()==null) || 
             (this.allowedDepartments!=null &&
              java.util.Arrays.equals(this.allowedDepartments, other.getAllowedDepartments())));
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
        if (getAllowedDepartments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllowedDepartments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllowedDepartments(), i);
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
        new org.apache.axis.description.TypeDesc(GetAllowedDepartmentsResultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAllowedDepartmentsResultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowedDepartments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AllowedDepartments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DepartmentType"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item"));
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
